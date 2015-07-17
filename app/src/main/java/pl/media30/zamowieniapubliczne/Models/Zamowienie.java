package pl.media30.zamowieniapubliczne.Models;

/**
 * Created by Adrian on 2015-07-16.
 */
public class Zamowienie {
    int global_id;
    Zamowienie data;
    @Override
    public String toString() {
        return String.valueOf(global_id);
    }

}
