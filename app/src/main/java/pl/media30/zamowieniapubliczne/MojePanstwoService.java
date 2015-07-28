package pl.media30.zamowieniapubliczne;

import pl.media30.zamowieniapubliczne.Models.SingleElement.BaseClass;
import pl.media30.zamowieniapubliczne.Models.DownloadList.BaseListClass;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Adrian on 2015-07-16.
 */
public interface MojePanstwoService {
    @GET("/zamowienia_publiczne/{id}?layers=details,czesci,sources.json")
    void singleOrder(@Path("id") int id, Callback<BaseClass> cb);

    @GET("/dataset/zamowienia_publiczne/search?limit=100")
    void listOrders(@Query("page") int page,  Callback<BaseListClass> clb);

    @GET("/dataset/zamowienia_publiczne/search?limit=100")
    void listOrdersWithParameter(@Query("page") int page, @Query("conditions[zamowienia_publiczne.zamawiajacy_miejscowosc]") String zapytanie,  Callback<BaseListClass> clb);

    @GET("/dataset/zamowienia_publiczne/search?order=zamowienia_publiczne.wartosc_cena%20desc")
   // https://api.mojepanstwo.pl/dane/dataset/zamowienia_publiczne/search?limit=10&order=zamowienia_publiczne.wartosc_cena%20desc
    void najwiekszeZamowienia(Callback<BaseListClass> cbx);

    //https://api.mojepanstwo.pl/dane/dataset/zamowienia_publiczne/search?order=zamowienia_publiczne.wartosc_cena
}
// @Query("conditions[zamowienia_publiczne.zamawiajacy_miejscowosc]") String zapytanie,