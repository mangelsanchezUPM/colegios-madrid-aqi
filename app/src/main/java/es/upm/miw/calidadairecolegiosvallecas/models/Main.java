
package es.upm.miw.calidadairecolegiosvallecas.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Main {

    @SerializedName("aqi")
    @Expose
    private Integer aqi;

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

}
