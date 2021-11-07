package es.upm.miw.colegiosmadridaqi.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Colegio.TABLA)
public class Colegio {
    static public final String TABLA = "colegios";

    @PrimaryKey(autoGenerate = true)
    protected int uid;
    private String nombre;
    private Float latitud;
    private Float longitud;

    public Colegio(String nombre, Float latitud, Float longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }
}

