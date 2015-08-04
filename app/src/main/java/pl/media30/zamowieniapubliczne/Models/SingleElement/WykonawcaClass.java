package pl.media30.zamowieniapubliczne.Models.SingleElement;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Adrian on 2015-07-30.
 */
public class WykonawcaClass implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("nazwa")
    public String nazwa;

    @SerializedName("miejscowosc")
    public String miejscowosc;

}
