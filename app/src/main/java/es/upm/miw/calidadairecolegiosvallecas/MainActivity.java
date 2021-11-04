package es.upm.miw.calidadairecolegiosvallecas;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import es.upm.miw.calidadairecolegiosvallecas.models.ColegioContaminacion;
import es.upm.miw.calidadairecolegiosvallecas.models.Colegios;
import es.upm.miw.calidadairecolegiosvallecas.models.Contaminacion;
import es.upm.miw.calidadairecolegiosvallecas.models.Graph;
import es.upm.miw.calidadairecolegiosvallecas.room.Colegio;
import es.upm.miw.calidadairecolegiosvallecas.room.ColegioViewModel;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final static String LOG_TAG = "MiW";

    private static final String API_STATIC_BASE_URL = "https://datos.madrid.es/egob/catalogo/";

    private static final String API_DYNAMIC_BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String API_DYNAMIC_KEY = "54c07990ccb655e3e2e98f7fba2e2438";

    private IColegiosRESTAPIService apiServiceColegios;
    private IContaminacionRESTAPIService apiServiceContaminacion;
    private ColegioListAdapter adapter;
    private ColegioViewModel colegioViewModel;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 2018;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofitDynamic = new Retrofit.Builder()
                .baseUrl(API_DYNAMIC_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Retrofit retrofitStatic = new Retrofit.Builder()
                .baseUrl(API_STATIC_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiServiceContaminacion = retrofitDynamic.create(IContaminacionRESTAPIService.class);
        apiServiceColegios = retrofitStatic.create(IColegiosRESTAPIService.class);
        colegioViewModel = new ViewModelProvider(this).get(ColegioViewModel.class);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new ColegioListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchColegios();
        colegioViewModel.getAllColegios().observe(this, this::fetchContaminacion);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //      mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void fetchColegios() {
        // Retrofit call
        Call<Colegios> call_async = apiServiceColegios.getColegios();
        call_async.enqueue(new Callback<Colegios>() {

            @Override
            public void onResponse(Call<Colegios> call, Response<Colegios> response) {
                assert response.body() != null;
                List<Graph> colegios = response.body().getGraph();
                if (colegioViewModel.getAllColegios().getValue() != null &&
                        colegioViewModel.getAllColegios().getValue().isEmpty()) {
                    for (Graph colegioGraph : colegios) {
                        Log.i(LOG_TAG, colegioGraph.getTitle());
                        Colegio colegio = new Colegio(colegioGraph.getTitle(),
                                colegioGraph.getLocation().getLatitude().floatValue(),
                                colegioGraph.getLocation().getLongitude().floatValue());
                        colegioViewModel.insert(colegio);
                    }
                }
            }

            @Override
            public void onFailure(Call<Colegios> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        "ERROR: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

    private void fetchContaminacion(List<Colegio> colegios) {
        // Retrofit call
        List<ColegioContaminacion> colegioContaminacionList = new ArrayList<>();
        for (Colegio colegio : colegios) {
            Call<Contaminacion> call_async = apiServiceContaminacion
                    .getContaminacion(colegio.getLatitud().toString(),
                            colegio.getLongitud().toString(),
                            API_DYNAMIC_KEY);

            call_async.enqueue(new Callback<Contaminacion>() {
                @Override
                public void onResponse(Call<Contaminacion> call, Response<Contaminacion> response) {
                    assert response.body() != null;
                    es.upm.miw.calidadairecolegiosvallecas.models.List contaminacion = response.body().getList().get(0);
                    ColegioContaminacion cc = new ColegioContaminacion(
                            colegio.getNombre(),
                            contaminacion.getMain().getAqi(),
                            contaminacion.getComponents()
                    );
                    colegioContaminacionList.add(cc);
                    adapter.setColegioContaminacionList(colegioContaminacionList);
                }

                @Override
                public void onFailure(Call<Contaminacion> call, Throwable t) {
                    Toast.makeText(
                            getApplicationContext(),
                            "ERROR: " + t.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                    Log.e(LOG_TAG, t.getMessage());
                }
            });
        }
    }
}