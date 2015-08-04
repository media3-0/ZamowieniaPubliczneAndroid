package pl.media30.zamowieniapubliczne.Models.SingleElement;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Adrian on 2015-07-23.
 */
public class KryteriaClass implements Serializable {
    @SerializedName("nazwa")
    public String nazwa;

    @SerializedName("punkty")
    public String punkty;
}
