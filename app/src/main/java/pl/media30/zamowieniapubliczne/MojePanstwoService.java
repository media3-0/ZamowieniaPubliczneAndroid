package pl.media30.zamowieniapubliczne;

import pl.media30.zamowieniapubliczne.Models.SingleElement.BaseClass;
import pl.media30.zamowieniapubliczne.Models.DownloadList.BaseListClass;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Adrian on 2015-07-16.
 */
public interface MojePanstwoService {
    /*
    * ----------------------------------UWAGA!!!----------------------------------
    * Po każdej zmianie limitu załadowanych elementów na stronie, należy zmienić
    * ten parametr dla pozostałych linków. W przypadku różnych parametrów dot.
    * limitu elementów (?limit=n) aplikacja może nie działać poprawnie !!!
    * ----------------------------------UWAGA!!!----------------------------------
    * */
    @GET("/zamowienia_publiczne/{id}?layers=details,czesci,sources.json")
    void singleOrder(@Path("id") int id, Callback<BaseClass> cb);

    @GET("/dataset/zamowienia_publiczne/search?limit=10")
    void listOrders(@Query("page") int page,  Callback<BaseListClass> clb);

    @GET("/dataset/zamowienia_publiczne/search?limit=10")
    void listOrdersWithParameter(@Query("page") int page, @Query("conditions[zamowienia_publiczne.zamawiajacy_miejscowosc]") String miejscowosc, @Query("conditions[zamowienia_publiczne.zamawiajacy_wojewodztwo]") String wojewodztwo ,@Query("conditions[zamowienia_publiczne.zamawiajacy_kod_poczt]") String kod_pocztowy, @Query("conditions[zamowienia_publiczne.zamawiajacy_nazwa]") String nazwa, @Query("conditions[zamowienia_publiczne.zamawiajacy_regon]") String regon, @Query("conditions[zamowienia_publiczne.zamawiajacy_www]") String zamaw_www, @Query("conditions[zamowienia_publiczne.zamawiajacy_email]") String zamaw_email, @Query("q") String glowne_zapyt, Callback<BaseListClass> clb);

    @GET("/dataset/zamowienia_publiczne/search?order=zamowienia_publiczne.wartosc_cena%20desc&fields[]=zamowienia_publiczne.id&fields[]=zamowienia_publiczne.wartosc_cena&fields[]=zamowienia_publiczne.zamawiajacy_nazwa&limit=10")
    BaseListClass najwiekszeZamowienia(@Query("page") int page, @Query("q") String glowne_zapyt, @Query("conditions[zamowienia_publiczne.zamawiajacy_miejscowosc]") String miejscowosc, @Query("conditions[zamowienia_publiczne.zamawiajacy_wojewodztwo]") String wojewodztwo ,@Query("conditions[zamowienia_publiczne.zamawiajacy_kod_poczt]") String kod_pocztowy, @Query("conditions[zamowienia_publiczne.zamawiajacy_nazwa]") String nazwa, @Query("conditions[zamowienia_publiczne.zamawiajacy_regon]") String regon, @Query("conditions[zamowienia_publiczne.zamawiajacy_www]") String zamaw_www, @Query("conditions[zamowienia_publiczne.zamawiajacy_email]") String zamaw_email);
}
