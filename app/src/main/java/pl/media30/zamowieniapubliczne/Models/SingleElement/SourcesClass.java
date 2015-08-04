package pl.media30.zamowieniapubliczne.Models.SingleElement;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Adrian on 2015-07-23.
 */
public class SourcesClass implements Serializable {

    @SerializedName("numer")
    public String numer;
    @SerializedName("data")
    public String data;


}
