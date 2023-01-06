package pe.mpc.muni.Model;

import com.google.gson.annotations.SerializedName;

public class Reporte {
    @SerializedName("picture")
    private String picture;
    @SerializedName("value")
    private int value;
    @SerializedName("imageName")
    private String imageName;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


}
