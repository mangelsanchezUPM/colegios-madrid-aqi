
package es.upm.miw.calidadairecolegiosvallecas.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Debug {

    @SerializedName("sync")
    @Expose
    private String sync;

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

}
