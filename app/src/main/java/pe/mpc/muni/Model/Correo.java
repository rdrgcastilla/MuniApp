package pe.mpc.muni.Model;

import com.google.gson.annotations.SerializedName;

public class Correo {
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    @SerializedName("from")
    private String from;
    @SerializedName("destino")
    private String destino;
    @SerializedName("asunto")
    private String asunto;
    @SerializedName("mensaje")
    private String mensaje;
    @SerializedName("value")
    private int value;


}
