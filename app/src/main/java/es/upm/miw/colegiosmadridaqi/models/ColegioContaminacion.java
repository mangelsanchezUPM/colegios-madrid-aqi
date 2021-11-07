package es.upm.miw.colegiosmadridaqi.models;

public class ColegioContaminacion {

    private String nombre;
    private Integer aqi;
    private Iaqi iaqi;

    public ColegioContaminacion(String nombre, Integer aqi, Iaqi iaqi) {
        this.nombre = nombre;
        this.aqi = aqi;
        this.iaqi = iaqi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    public Iaqi getIaqi() {
        return iaqi;
    }

    public void setIaqi(Iaqi iaqi) {
        this.iaqi = iaqi;
    }
}
