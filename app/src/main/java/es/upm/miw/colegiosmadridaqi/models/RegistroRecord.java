package es.upm.miw.colegiosmadridaqi.models;

public class RegistroRecord {
    private String user;
    private Long fecha;
    private Integer aqi;
    private String nombreColegio;

    public RegistroRecord(String user, Long fecha, Integer aqi, String nombreColegio) {
        this.user = user;
        this.fecha = fecha;
        this.aqi = aqi;
        this.nombreColegio = nombreColegio;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    public String getNombreColegio() {
        return nombreColegio;
    }

    public void setNombreColegio(String nombreColegio) {
        this.nombreColegio = nombreColegio;
    }
}
