package pe.mpc.muni.Model;

import com.google.gson.annotations.SerializedName;

public class Evento {
    public String getId() { return id;  }

    public void setId(String id) {this.id = id;}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getHora() { return hora; }

    public void setHora(String hora) { this.hora = hora; }

    public String getFecha() { return fecha; }

    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagen() { return imagen;  }

    public void setImagen(String imagen) { this.imagen = imagen; }

    public String getLugar() { return lugar;  }

    public void setLugar(String lugar) { this.lugar = lugar; }

    public String getOrganizador() { return organizador; }

    public void setOrganizador(String organizador) { this.organizador = organizador; }

    public String getLink() { return link; }

    public void setLink(String link) {this.link = link; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("tipo")
    private String tipo;
    @SerializedName("hora")
    private String hora;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("imagen")
    private String imagen;
    @SerializedName("lugar")
    private String lugar;
    @SerializedName("organizador")
    private String organizador;
    @SerializedName("link")
    private String link;
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;


}
