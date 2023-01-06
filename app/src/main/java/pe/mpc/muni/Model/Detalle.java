package pe.mpc.muni.Model;

import com.google.gson.annotations.SerializedName;

public class Detalle {
    public String getAcercaDetalle() {
        return acercaDetalle;
    }

    public void setAcercaDetalle(String acercaDetalle) {
        this.acercaDetalle = acercaDetalle;
    }

    public String getDireccionDetalle() {
        return direccionDetalle;
    }

    public void setDireccionDetalle(String direccionDetalle) {
        this.direccionDetalle = direccionDetalle;
    }

    public String getHorarioDetalle() {
        return horarioDetalle;
    }

    public void setHorarioDetalle(String horarioDetalle) {
        this.horarioDetalle = horarioDetalle;
    }

    public String getTelefonoDetalle() {
        return telefonoDetalle;
    }

    public void setTelefonoDetalle(String telefonoDetalle) {
        this.telefonoDetalle = telefonoDetalle;
    }

    public String getCelularDetalle() {
        return celularDetalle;
    }

    public void setCelularDetalle(String celularDetalle) {
        this.celularDetalle = celularDetalle;
    }

    public String getUrlUbicacion() {
        return urlUbicacion;
    }

    public void setUrlUbicacion(String urlUbicacion) {
        this.urlUbicacion = urlUbicacion;
    }

    @SerializedName("acercaDetalle")
    private String acercaDetalle;
    @SerializedName("direccionDetalle")
    private String direccionDetalle;
    @SerializedName("horarioDetalle")
    private String horarioDetalle;
    @SerializedName("telefonoDetalle")
    private String telefonoDetalle;
    @SerializedName("celularDetalle")
    private String celularDetalle;
    @SerializedName("urlUbicacion")
    private String urlUbicacion;

}
