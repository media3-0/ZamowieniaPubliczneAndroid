package pl.media30.zamowieniapubliczne.Models.SingleElement;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Adrian on 2015-07-23.
 */
public class DetailsClass {
    @SerializedName("data_start")
    public String data_start;

    @SerializedName("data_stop")
    public String data_stop;

    @SerializedName("oferty_godz")
    public String oferty_godz;

    @SerializedName("czas_miesiace")
    public String czas_miesiace;

    @SerializedName("czas_dni")
    public String czas_dni;

    @SerializedName("oferty_liczba_dni")
    public String oferty_liczba_dni;

    @SerializedName("le_adres_aukcja")
    public String le_adres_aukcja;

    @SerializedName("le_adres_opis")
    public String le_adres_opis;

    @SerializedName("le_data_skl")
    public String le_data_skl;

    @SerializedName("le_godz_skl")
    public String le_godz_skl;

    @SerializedName("le_term_otw")
    public String le_term_otw;

    @SerializedName("le_term_war_zam")
    public String le_term_war_zam;

    @SerializedName("kryteria")
    public List<SourcesClass> kryteria;

    @SerializedName("le_wymagania")
    public String le_wymagania;

    @SerializedName("le_postapien")
    public String le_postapien;

    @SerializedName("le_miejsce_skl")
    public String le_miejsce_skl;

    @SerializedName("przedmiot")
    public String przedmiot;

    @SerializedName("uprawnienie")
    public String uprawnienie;

    @SerializedName("wiedza")
    public String wiedza;

    @SerializedName("potencjal")
    public String potencjal;

    @SerializedName("osoby_zdolne")
    public String osoby_zdolne;

    @SerializedName("sytuacja_ekonomiczna")
    public String sytuacja_ekonomiczna;

    @SerializedName("zal_pprawna")
    public String zal_pprawna;

    @SerializedName("zal_uzasadnienie")
    public String zal_uzasadnienie;

    @SerializedName("zamowienie_uzupelniajace")
    public String zamowienie_uzupelniajace;

    @SerializedName("wadium")
    public String wadium;

    @SerializedName("wybor_wykonawcow")
    public String wybor_wykonawcow;

    @SerializedName("zmieniona_umowa")
    public String zmieniona_umowa;

    @SerializedName("aukcja_www")
    public String aukcja_www;

    @SerializedName("siwz_www")
    public String siwz_www;

    @SerializedName("siwz_adres")
    public String siwz_adres;

    @SerializedName("dk_potrzeby")
    public String dk_potrzeby;

    @SerializedName("dk_nagrody")
    public String dk_nagrody;

    @SerializedName("oferty_miejsce")
    public String oferty_miejsce;

    @SerializedName("umowa_zabezpieczenia")
    public String umowa_zabezpieczenia;

    @SerializedName("umowa_istotne_postanowienia")
    public String umowa_istotne_postanowienia;

    @SerializedName("info")
    public String info;

    @SerializedName("inne_dokumenty")
    public String inne_dokumenty;

    @SerializedName("inne_dok_potw")
    public String inne_dok_potw;

    @SerializedName("zal_pprawna_hid")
    public String zal_pprawna_hid;

    @SerializedName("zamowienie_pprawna")
    public String zamowienie_pprawna;

    @SerializedName("zamowienie_pprawna_hid")
    public String zamowienie_pprawna_hid;

    @SerializedName("zamowienie_uzasadnienie")
    public String zamowienie_uzasadnienie;

}
