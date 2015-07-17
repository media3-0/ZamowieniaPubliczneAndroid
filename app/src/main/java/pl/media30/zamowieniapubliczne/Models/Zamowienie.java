package pl.media30.zamowieniapubliczne.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Adrian on 2015-07-16.
 */
public class Zamowienie {
    int global_id;
    Zamowienie data;
    @SerializedName("zamowienia_publiczne.kod_pocztowy_id")
    public  String kod_pocztowy_id;
    @SerializedName("zamowienia_publiczne.zamawiajacy_www")
    public  String zamawiajacy_www;

}
