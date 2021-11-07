
package es.upm.miw.calidadairecolegiosvallecas.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Pm25 {

    @SerializedName("v")
    @Expose
    private Integer v;

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
