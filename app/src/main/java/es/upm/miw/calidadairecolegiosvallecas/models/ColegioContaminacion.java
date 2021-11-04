package es.upm.miw.calidadairecolegiosvallecas.models;

public class ColegioContaminacion {

    private String nombre;
    private Integer aqi;
    private Components components;

    public ColegioContaminacion(String nombre, Integer aqi, Components components) {
        this.nombre = nombre;
        this.aqi = aqi;
        this.components = components;
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

    public Components getComponents() {
        return components;
    }

    public void setComponents(Components components) {
        this.components = components;
    }
}
