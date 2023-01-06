package pe.mpc.muni.Model;

import com.google.gson.annotations.SerializedName;

public class Documento {
    public String getfFecRegistro() {
        return fFecRegistro;
    }

    public void setfFecRegistro(String fFecRegistro) {
        this.fFecRegistro = fFecRegistro;
    }

    public String getcCodificacion() {
        return cCodificacion;
    }

    public void setcCodificacion(String cCodificacion) {
        this.cCodificacion = cCodificacion;
    }

    public String getcDescTipoDoc() {
        return cDescTipoDoc;
    }

    public void setcDescTipoDoc(String cDescTipoDoc) {
        this.cDescTipoDoc = cDescTipoDoc;
    }

    public String getcNroDocumento() {
        return cNroDocumento;
    }

    public void setcNroDocumento(String cNroDocumento) {
        this.cNroDocumento = cNroDocumento;
    }

    public String getcNombre() {
        return cNombre;
    }

    public void setcNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getcNomRemite() {
        return cNomRemite;
    }

    public void setcNomRemite(String cNomRemite) {
        this.cNomRemite = cNomRemite;
    }

    public String getnNumFolio() {
        return nNumFolio;
    }

    public void setnNumFolio(String nNumFolio) {
        this.nNumFolio = nNumFolio;
    }

    public String getcAsunto() {
        return cAsunto;
    }

    public void setcAsunto(String cAsunto) {
        this.cAsunto = cAsunto;
    }

    public String getcObservaciones() {
        return cObservaciones;
    }

    public void setcObservaciones(String cObservaciones) {
        this.cObservaciones = cObservaciones;
    }

    public String getcNomOficina() {
        return cNomOficina;
    }

    public void setcNomOficina(String cNomOficina) {
        this.cNomOficina = cNomOficina;
    }

    public String getnFlgEstado() {
        return nFlgEstado;
    }

    public void setnFlgEstado(String nFlgEstado) {
        this.nFlgEstado = nFlgEstado;
    }
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @SerializedName("ERROR")
    private String error;
    @SerializedName("fFecRegistro")
    private String fFecRegistro;
    @SerializedName("cCodificacion")
    private String cCodificacion;
    @SerializedName("cDescTipoDoc")
    private String cDescTipoDoc;
    @SerializedName("cNroDocumento")
    private String cNroDocumento;
    @SerializedName("cNombre")
    private String cNombre;
    @SerializedName("cNomRemite")
    private String cNomRemite;
    @SerializedName("nNumFolio")
    private String nNumFolio;
    @SerializedName("cAsunto")
    private String cAsunto;
    @SerializedName("cObservaciones")
    private String cObservaciones;
    @SerializedName("cNomOficina")
    private String cNomOficina;
    @SerializedName("nFlgEstado")
    private String nFlgEstado;


}
