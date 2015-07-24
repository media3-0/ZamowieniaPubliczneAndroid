package pl.media30.zamowieniapubliczne.Models.SingleElement;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pl.media30.zamowieniapubliczne.Models.DownloadList.DataObjectClass;

/**
 * Created by Adrian on 2015-07-23.
 */
public class LayersClass {

    @SerializedName("details")
    public DetailsClass detailsClass;

    @SerializedName("sources")
    public List<SourcesClass> sources;
/*
    @SerializedName("czesci")
    public List<CzesciClass> czesci; //Narazie klasa jest pusta, poniewaz nie moge znalezc przetargu w ktorym jest to wypelnione
*/
    @SerializedName("dataset")
    public String dataset; // czy String




}
