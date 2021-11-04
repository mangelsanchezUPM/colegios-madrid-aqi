package es.upm.miw.calidadairecolegiosvallecas;

import es.upm.miw.calidadairecolegiosvallecas.models.Contaminacion;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface IContaminacionRESTAPIService {
    @GET("air_pollution")
    Call<Contaminacion> getContaminacion(@Query(value = "lat", encoded = true) String lat,
                                         @Query(value = "lon", encoded = true) String lon,
                                         @Query(value = "apiKey", encoded = true) String apiKey);
}
