package pl.media30.zamowieniapubliczne.Models.SingleElement;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Adrian on 2015-07-17.
 */
public class BaseClass implements Serializable {

    @SerializedName("object")
    public ObjectClass objectClass;
}