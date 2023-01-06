package pe.mpc.muni.Model;

import com.google.gson.annotations.SerializedName;

public class Licencia {

    @SerializedName("estado")
    private String estado;
    @SerializedName("mensaje")
    private String mensaje;
    @SerializedName("ndni")
    private String ndni;
    @SerializedName("nlicencia")
    private String nlicencia;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("apellido")
    private String apellido;
    @SerializedName("direccion")
    private String direccion;
    @SerializedName("distrito")
    private String distrito;
    @SerializedName("fecha_emision")
    private String fecha_emision;
    @SerializedName("fecha_revalidacion")
    private String fecha_revalidacion;
    @SerializedName("fot")
    private String fot;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNdni() {
        return ndni;
    }

    public void setNdni(String ndni) {
        this.ndni = ndni;
    }

    public String getNlicencia() {
        return nlicencia;
    }

    public void setNlicencia(String nlicencia) {
        this.nlicencia = nlicencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(String fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public String getFecha_revalidacion() {
        return fecha_revalidacion;
    }

    public void setFecha_revalidacion(String fecha_revalidacion) {
        this.fecha_revalidacion = fecha_revalidacion;
    }

    public String getFot() {
        return fot;
    }

    public void setFot(String fot) {
        this.fot = fot;
    }
}
