package pe.mpc.muni.Interface;

import java.util.List;

import pe.mpc.muni.Model.Documento;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DocumentoApi {
    //?format=json&nexp=202000001
    @GET("api_tramite.php")
    Call<List<Documento>> consultaTramiteDocumento(
            @Query("format") String format,
            @Query("nexp") String nexp
    );
}
