package es.upm.miw.calidadairecolegiosvallecas;

import es.upm.miw.calidadairecolegiosvallecas.models.Contaminacion;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface IContaminacionRESTAPIService {
    @GET("feed/geo:{lat};{lon}/")
    Call<Contaminacion> getContaminacion(@Path("lat") Float lat,
                                         @Path("lon") Float lon,
                                         @Query(value = "token", encoded = true) String token);
}
