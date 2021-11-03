package es.upm.miw.calidadairecolegiosvallecas;

import android.app.Activity;
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
import java.util.List;
import java.util.Objects;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import es.upm.miw.calidadairecolegiosvallecas.models.Colegios;
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

    private IColegiosRESTAPIService apiServiceColegios;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ColegioViewModel colegioViewModel;
    private static final int RC_SIGN_IN = 2018;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofitStatic = new Retrofit.Builder()
                .baseUrl(API_STATIC_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();
        apiServiceColegios = retrofitStatic.create(IColegiosRESTAPIService.class);
        colegioViewModel = new ViewModelProvider(this).get(ColegioViewModel.class);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final ColegioListAdapter adapter = new ColegioListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getColegios();
        colegioViewModel.getAllColegios().observe(this, adapter::setColegios);
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

    private void getColegios() {
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


    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}