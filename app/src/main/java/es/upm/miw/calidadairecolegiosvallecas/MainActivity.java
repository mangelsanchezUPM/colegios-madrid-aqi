package es.upm.miw.calidadairecolegiosvallecas;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import es.upm.miw.calidadairecolegiosvallecas.models.ColegioContaminacion;
import es.upm.miw.calidadairecolegiosvallecas.models.Colegios;
import es.upm.miw.calidadairecolegiosvallecas.models.Contaminacion;
import es.upm.miw.calidadairecolegiosvallecas.models.Graph;
import es.upm.miw.calidadairecolegiosvallecas.models.Iaqi;
import es.upm.miw.calidadairecolegiosvallecas.room.Colegio;
import es.upm.miw.calidadairecolegiosvallecas.room.ColegioViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    final static String LOG_TAG = "MiW";
    private static final String API_STATIC_BASE_URL = "https://datos.madrid.es/egob/catalogo/";

    private static final String API_DYNAMIC_BASE_URL = "https://api.waqi.info/";
    private static final String API_DYNAMIC_KEY = "14a282117d56847c14ed1f76e885fc6e9078450a";

    private IColegiosRESTAPIService apiServiceColegios;
    private IContaminacionRESTAPIService apiServiceContaminacion;
    private ColegioListAdapter adapter;
    private ColegioViewModel colegioViewModel;

    private List<ColegioContaminacion> colegioContaminacionList;
    private Button btnBuscar;
    private EditText etNombreColegio;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 2018;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBuscar = findViewById(R.id.btnBuscar);
        etNombreColegio = findViewById(R.id.etNombreColegio);
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

    public void buscarColegio(View v) {
        String colegioNombre = this.etNombreColegio.getText().toString();
        if (colegioNombre.isEmpty()) {
            adapter.setColegioContaminacionList(colegioContaminacionList);
            return;
        }

        List<ColegioContaminacion> filteredList = new ArrayList<ColegioContaminacion>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            filteredList = colegioContaminacionList.stream()
                    .filter(colegioContaminacion ->
                            colegioContaminacion.getNombre().toLowerCase()
                                    .contains(colegioNombre.toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            filteredList = new ArrayList<ColegioContaminacion>();
            for (ColegioContaminacion colegioContaminacion : colegioContaminacionList) {
                if (colegioContaminacion.getNombre().toLowerCase()
                        .contains(colegioNombre.toLowerCase()))
                    filteredList.add(colegioContaminacion);
            }
        }
        adapter.setColegioContaminacionList(filteredList);
    }

    public void limpiarColegios(View v) {
        etNombreColegio.setText("");
        adapter.setColegioContaminacionList(colegioContaminacionList);
    }

    private void fetchContaminacion(List<Colegio> colegios) {
        // Retrofit call
        colegioContaminacionList = new ArrayList<>();

        for (Colegio colegio : colegios) {
            Call<Contaminacion> call_async = apiServiceContaminacion
                    .getContaminacion(colegio.getLatitud(),
                            colegio.getLongitud(),
                            API_DYNAMIC_KEY);

            call_async.enqueue(new Callback<Contaminacion>() {
                @Override
                public void onResponse(Call<Contaminacion> call, Response<Contaminacion> response) {
                    assert response.body() != null;
                    Integer aqi = response.body().getData().getAqi();
                    Iaqi iaqi = response.body().getData().getIaqi();
                    ColegioContaminacion cc = new ColegioContaminacion(colegio.getNombre(), aqi, iaqi);
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