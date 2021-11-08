package es.upm.miw.colegiosmadridaqi;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import colegiosmadridaqi.R;

public class DetallesActivity extends AppCompatActivity {
    final static String LOG_TAG = "MiW";

    private Button aqiBtn;
    private TextView nombreColegioTV;
    private Button guardarBtn;

    private Integer aqi;
    private String nombreColegio;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private final String DB_URL = "https://colegios-madrid-aq-default-rtdb.europe-west1.firebasedatabase.app/";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        nombreColegioTV = findViewById(R.id.tvNombreColegioDetalles);
        aqiBtn = findViewById(R.id.btnAqiDetalles);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            aqi = extras.getInt("aqi");
            aqiBtn.setText(String.valueOf(aqi));
            if (aqi < 33) aqiBtn.setBackgroundColor(getColor(R.color.very_good));
            else if (aqi > 33 && aqi < 66) aqiBtn.setBackgroundColor(getColor(R.color.good));
            else if (aqi > 66 && aqi < 100) aqiBtn.setBackgroundColor(getColor(R.color.fair));
            else if (aqi > 100 && aqi < 150) aqiBtn.setBackgroundColor(getColor(R.color.poor));
            else if (aqi > 150 && aqi < 200) aqiBtn.setBackgroundColor(getColor(R.color.very_poor));
            else if (aqi > 200) aqiBtn.setBackgroundColor(getColor(R.color.hazardous));
            nombreColegio = extras.getString("nombreColegio");
            nombreColegioTV.setText(nombreColegio);
            user = (FirebaseUser) extras.get("user");

            ((TextView) findViewById(R.id.h)).setText("H: " + extras.getDouble("h"));
            ((TextView) findViewById(R.id.no2)).setText("NO2: " + extras.getDouble("no2"));
            ((TextView) findViewById(R.id.p)).setText("P: " + extras.getDouble("p"));
            ((TextView) findViewById(R.id.t)).setText("T: " + extras.getDouble("t"));
            ((TextView) findViewById(R.id.pm10)).setText("PM10: " + extras.getInt("pm10"));
            ((TextView) findViewById(R.id.pm25)).setText("PM25: " + extras.getInt("pm25"));
            ((TextView) findViewById(R.id.w)).setText("W: " + extras.getDouble("w"));
            ((TextView) findViewById(R.id.wg)).setText("WG: " + extras.getDouble("wg"));

            database = FirebaseDatabase.getInstance(DB_URL);
            myRef = database.getReference("aqi-colegios");

            Button btnGuardar = findViewById(R.id.btnGuardar);
            if (user == null) btnGuardar.setVisibility(View.INVISIBLE);
            else {
                btnGuardar.setOnClickListener(view -> {
                    Map<String, Object> registro = new HashMap<>();
                    registro.put("usuario", user.getDisplayName());
                    registro.put("colegio", nombreColegio);
                    registro.put("aqi", aqi);
                    myRef.setValue(registro);
                    Toast.makeText(this.getApplicationContext(), "Registro insertado", Toast.LENGTH_SHORT).show();
                });
            }
        } else {
            finish();
        }
    }
}