package pe.mpc.muni.Interface;

import pe.mpc.muni.Model.Correo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CorreoApi {
    @FormUrlEncoded
    @POST("wsSendMailReporteTransporte.php")
    Call<Correo> enviarCorreo(
            @Field("nDocumento") String nDocumento,
            @Field("nombre") String nombre,
            @Field("nPlaca") String nPlaca,
            @Field("descripcion") String descripcion,
            @Field("empresa") String empresa,
            @Field("propietario") String propietario,
            @Field("conductor") String conductor,
            @Field("ruta") String ruta,
            @Field("imagenUrl") String imagenUrl

    );
}
