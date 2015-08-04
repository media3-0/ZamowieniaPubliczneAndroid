package pl.media30.zamowieniapubliczne.Models.SingleElement;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Adrian on 2015-07-17.
 */
public class ObjectClass implements Serializable  {

    @SerializedName("data")
    public DataClass dataClass;
    @SerializedName("global_id")
    public String global_id;
    @SerializedName("layers")
    public LayersClass layers;

}
