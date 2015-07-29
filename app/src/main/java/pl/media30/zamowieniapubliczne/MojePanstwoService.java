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

    @GET("/dataset/zamowienia_publiczne/search?limit=10")
    void listOrders(@Query("page") int page,  Callback<BaseListClass> clb);

    @GET("/dataset/zamowienia_publiczne/search?limit=10")
    void listOrdersWithParameter(@Query("page") int page, @Query("conditions[zamowienia_publiczne.zamawiajacy_miejscowosc]") String miejscowosc, @Query("conditions[zamowienia_publiczne.zamawiajacy_wojewodztwo]") String wojewodztwo ,@Query("conditions[zamowienia_publiczne.zamawiajacy_kod_poczt]") String kod_pocztowy, @Query("conditions[zamowienia_publiczne.zamawiajacy_nazwa]") String nazwa,  Callback<BaseListClass> clb);

    @GET("/dataset/zamowienia_publiczne/search?order=zamowienia_publiczne.wartosc_cena%20desc")
    void najwiekszeZamowienia(@Query("conditions[zamowienia_publiczne.zamawiajacy_miejscowosc]") String miejscowosc, @Query("conditions[zamowienia_publiczne.zamawiajacy_wojewodztwo]") String wojewodztwo ,@Query("conditions[zamowienia_publiczne.zamawiajacy_kod_poczt]") String kod_pocztowy, @Query("conditions[zamowienia_publiczne.zamawiajacy_nazwa]") String nazwa, Callback<BaseListClass> cbx);
}
