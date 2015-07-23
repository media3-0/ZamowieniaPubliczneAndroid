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

    //https://api.mojepanstwo.pl/dane/dataset/zamowienia_publiczne/search?page=2.json
    @GET("/dataset/zamowienia_publiczne/search") //@Query("sort") String sort
    void listOrders(@Query("page") int page, Callback<BaseListClass> clb);
    // void getPositionByZip(@Query("address") String address, Callback<String> cb);
}
