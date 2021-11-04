
package es.upm.miw.calidadairecolegiosvallecas.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Contaminacion {

    @SerializedName("coord")
    @Expose
    private Coord coord;
    @SerializedName("list")
    @Expose
    private java.util.List<es.upm.miw.calidadairecolegiosvallecas.models.List> list = null;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public java.util.List<es.upm.miw.calidadairecolegiosvallecas.models.List> getList() {
        return list;
    }

    public void setList(java.util.List<es.upm.miw.calidadairecolegiosvallecas.models.List> list) {
        this.list = list;
    }

}
