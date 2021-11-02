package es.upm.miw.calidadairecolegiosvallecas;

import es.upm.miw.calidadairecolegiosvallecas.models.Colegios;
import retrofit2.Call;
import retrofit2.http.GET;

interface IColegiosRESTAPIService {
    @GET("202311-0-colegios-publicos.json?distrito_nombre=PUENTE%20DE%20VALLECAS")
    Call<Colegios> getColegios();
}
