
package es.upm.miw.calidadairecolegiosvallecas.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Daily {

    @SerializedName("o3")
    @Expose
    private List<O3> o3 = null;
    @SerializedName("pm10")
    @Expose
    private List<Pm10__1> pm10 = null;
    @SerializedName("pm25")
    @Expose
    private List<Pm25__1> pm25 = null;
    @SerializedName("uvi")
    @Expose
    private List<Uvus> uvi = null;

    public List<O3> getO3() {
        return o3;
    }

    public void setO3(List<O3> o3) {
        this.o3 = o3;
    }

    public List<Pm10__1> getPm10() {
        return pm10;
    }

    public void setPm10(List<Pm10__1> pm10) {
        this.pm10 = pm10;
    }

    public List<Pm25__1> getPm25() {
        return pm25;
    }

    public void setPm25(List<Pm25__1> pm25) {
        this.pm25 = pm25;
    }

    public List<Uvus> getUvi() {
        return uvi;
    }

    public void setUvi(List<Uvus> uvi) {
        this.uvi = uvi;
    }

}
