
package es.upm.miw.calidadairecolegiosvallecas.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Forecast {

    @SerializedName("daily")
    @Expose
    private Daily daily;

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

}
