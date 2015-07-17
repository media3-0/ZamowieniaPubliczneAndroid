package pl.media30.zamowieniapubliczne;

import pl.media30.zamowieniapubliczne.Models.Zamowienie;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Adrian on 2015-07-16.
 */
public interface MojePanstwoService {
    @GET("/zamowienia_publiczne/{id}.json")
    void listOrders(@Path("id") int id, Callback<Zamowienie> cb);
}
