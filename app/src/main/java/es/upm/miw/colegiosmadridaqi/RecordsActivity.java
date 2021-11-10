package es.upm.miw.colegiosmadridaqi;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import colegiosmadridaqi.R;
import es.upm.miw.colegiosmadridaqi.models.RegistroRecord;
import io.reactivex.annotations.NonNull;

public class RecordsActivity extends AppCompatActivity {
    final static String LOG_TAG = "MiW";
    private final String DB_URL = "https://colegios-madrid-aq-default-rtdb.europe-west1.firebasedatabase.app/";
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance(DB_URL).getReference();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    finish();
                } else {
                    HashMap<Object, HashMap<String, Object>> value = (HashMap<Object, HashMap<String, Object>>) task.getResult().getValue();
                    List<RegistroRecord> registroRecords = new ArrayList<>();
                    value.forEach((object, registro) -> registroRecords.add(
                            new RegistroRecord(
                                    (String) registro.get("usuario"),
                                    (Long) registro.get("fecha"),
                                    Math.toIntExact((Long) registro.get("aqi")),
                                    (String) registro.get("colegio"))));

                    List<RegistroRecord> registrosTop = registroRecords.stream()
                            .sorted(Comparator.comparingInt(RegistroRecord::getAqi))
                            .limit(5)
                            .collect(Collectors.toList());

                    Date date = new Date();
                    date.setTime(registrosTop.get(0).getFecha());
                    ((Button) findViewById(R.id.r1aqi)).setText(String.valueOf(registrosTop.get(0).getAqi()));
                    ((TextView) findViewById(R.id.r1colegio)).setText(registrosTop.get(0).getNombreColegio());
                    ((TextView) findViewById(R.id.r1fecha)).setText(date.toString());
                    ((TextView) findViewById(R.id.r1usuario)).setText(registrosTop.get(0).getUser());

                    date.setTime(registrosTop.get(1).getFecha());
                    ((Button) findViewById(R.id.r2aqi)).setText(String.valueOf(registrosTop.get(1).getAqi()));
                    ((TextView) findViewById(R.id.r2colegio)).setText(registrosTop.get(1).getNombreColegio());
                    ((TextView) findViewById(R.id.r2fecha)).setText(date.toString());
                    ((TextView) findViewById(R.id.r2usuario)).setText(registrosTop.get(1).getUser());

                    date.setTime(registrosTop.get(2).getFecha());
                    ((Button) findViewById(R.id.r3aqi)).setText(String.valueOf(registrosTop.get(2).getAqi()));
                    ((TextView) findViewById(R.id.r3colegio)).setText(registrosTop.get(2).getNombreColegio());
                    ((TextView) findViewById(R.id.r3fecha)).setText(date.toString());
                    ((TextView) findViewById(R.id.r3usuario)).setText(registrosTop.get(2).getUser());

                    date.setTime(registrosTop.get(3).getFecha());
                    ((Button) findViewById(R.id.r4aqi)).setText(String.valueOf(registrosTop.get(3).getAqi()));
                    ((TextView) findViewById(R.id.r4colegio)).setText(registrosTop.get(3).getNombreColegio());
                    ((TextView) findViewById(R.id.r4fecha)).setText(date.toString());
                    ((TextView) findViewById(R.id.r4usuario)).setText(registrosTop.get(3).getUser());

                    date.setTime(registrosTop.get(4).getFecha());
                    ((Button) findViewById(R.id.r5aqi)).setText(String.valueOf(registrosTop.get(4).getAqi()));
                    ((TextView) findViewById(R.id.r5colegio)).setText(registrosTop.get(4).getNombreColegio());
                    ((TextView) findViewById(R.id.r5fecha)).setText(date.toString());
                    ((TextView) findViewById(R.id.r5usuario)).setText(registrosTop.get(4).getUser());
                }
            }
        });


    }
}