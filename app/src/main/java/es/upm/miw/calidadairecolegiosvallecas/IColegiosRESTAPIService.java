package es.upm.miw.calidadairecolegiosvallecas;

import es.upm.miw.calidadairecolegiosvallecas.models.Colegios;
import retrofit2.Call;
import retrofit2.http.GET;

interface IColegiosRESTAPIService {
    @GET("202311-0-colegios-publicos.json?latitud=40.4&longitud=-3.4&distancia=3000")
    Call<Colegios> getColegios();
}
