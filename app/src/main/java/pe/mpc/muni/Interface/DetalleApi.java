package pe.mpc.muni.Interface;

import java.util.List;

import pe.mpc.muni.Model.Detalle;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DetalleApi {
    @FormUrlEncoded
    @POST("getDetalleMuni.php")
    Call<List<Detalle>> getDetalle(
            @Field("key") String key
    );
}
