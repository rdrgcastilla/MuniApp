package pe.mpc.muni.Interface;

import java.util.List;

import pe.mpc.muni.Model.Evento;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EventoApi {
    /**Listar todos los eventos*/
    @FormUrlEncoded
    @POST("all")
    Call<List<Evento>> all(
            @Field("PUBLIC-KEY") String publi_key
    );
    /**Obtener mas detalles de un evento*/
    @FormUrlEncoded
    @POST("show")
    Call<Evento> show(
            @Field("PUBLIC-KEY") String publi_key,
            @Field("eventId") String eventId
    );
    /**Buscar por nombre de evento*/
    @FormUrlEncoded
    @POST("find")
    Call<List<Evento>> find(
            @Field("PUBLIC-KEY") String publi_key,
            @Field("filter") String filter
    );
}
