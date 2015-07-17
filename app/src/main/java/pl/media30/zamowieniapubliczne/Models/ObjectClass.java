package pl.media30.zamowieniapubliczne.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Adrian on 2015-07-17.
 */
public class ObjectClass {
    @SerializedName("data")
    public Zamowienie zamowienie;
    @SerializedName("global_id")
    public String global_id;

}
