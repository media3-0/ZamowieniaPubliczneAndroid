package pl.media30.zamowieniapubliczne.Models.SingleElement;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Adrian on 2015-07-23.
 */
public class CzesciClass {

    @SerializedName("id")
    public int id;

    @SerializedName("nazwa")
    public String nazwa;

    @SerializedName("opis")
    public String opis;

    @SerializedName("numer")
    public String numer;

    @SerializedName("slownik")
    public String slownik;

    @SerializedName("czas")
    public String czas;

    @SerializedName("czas_mies")
    public String czas_mies;

    @SerializedName("data_rozpoczecia")
    public String data_rozpoczecia;

    @SerializedName("data_zakonczenia")
    public String data_zakonczenia;

    @SerializedName("kryterium")
    public String kryterium;

    @SerializedName("kryteria")
    public List<KryteriaClass> kryteria;

}
