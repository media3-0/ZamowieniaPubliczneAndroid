package pl.media30.zamowieniapubliczne;

import pl.media30.zamowieniapubliczne.Models.BaseClass;
import pl.media30.zamowieniapubliczne.Models.DownloadList.BaseListClass;
import pl.media30.zamowieniapubliczne.Models.Zamowienie;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Adrian on 2015-07-16.
 */
public interface MojePanstwoService {
    @GET("/zamowienia_publiczne/{id}.json")
    void singleOrder(@Path("id") int id, Callback<BaseClass> cb);

    @GET("/dataset/zamowienia_publiczne/search")
    void listOrders(Callback<BaseListClass> clb);
    //dataset/zamowienia_publiczne/search?page=3

}
