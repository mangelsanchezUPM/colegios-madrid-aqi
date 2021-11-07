package es.upm.miw.calidadairecolegiosvallecas;

import es.upm.miw.calidadairecolegiosvallecas.models.Colegios;
import retrofit2.Call;
import retrofit2.http.GET;

interface IColegiosRESTAPIService {
    // URL devuelve todos los colegios de Madrid, los parametros de url se han puesto para evitar
    // invalidaciones de JSON (id duplicado)
    @GET("202311-0-colegios-publicos.json?latitud=40.47210978701385&longitud=-3.631007402473766&distancia=200000")
    Call<Colegios> getColegios();
}
