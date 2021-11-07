
package es.upm.miw.colegiosmadridaqi.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Iaqi {

    @SerializedName("h")
    @Expose
    private H h;
    @SerializedName("no2")
    @Expose
    private No2 no2;
    @SerializedName("p")
    @Expose
    private P p;
    @SerializedName("pm10")
    @Expose
    private Pm10 pm10;
    @SerializedName("pm25")
    @Expose
    private Pm25 pm25;
    @SerializedName("t")
    @Expose
    private T t;
    @SerializedName("w")
    @Expose
    private W w;
    @SerializedName("wg")
    @Expose
    private Wg wg;

    public H getH() {
        return h;
    }

    public void setH(H h) {
        this.h = h;
    }

    public No2 getNo2() {
        return no2;
    }

    public void setNo2(No2 no2) {
        this.no2 = no2;
    }

    public P getP() {
        return p;
    }

    public void setP(P p) {
        this.p = p;
    }

    public Pm10 getPm10() {
        return pm10;
    }

    public void setPm10(Pm10 pm10) {
        this.pm10 = pm10;
    }

    public Pm25 getPm25() {
        return pm25;
    }

    public void setPm25(Pm25 pm25) {
        this.pm25 = pm25;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public W getW() {
        return w;
    }

    public void setW(W w) {
        this.w = w;
    }

    public Wg getWg() {
        return wg;
    }

    public void setWg(Wg wg) {
        this.wg = wg;
    }

}
