package pl.media30.zamowieniapubliczne.Models.SingleElement;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import pl.media30.zamowieniapubliczne.Models.DownloadList.DataObjectClass;

/**
 * Created by Adrian on 2015-07-23.
 */
public class LayersClass implements Serializable {

    @SerializedName("details")
    public DetailsClass detailsClass;

    @SerializedName("sources")
    public List<SourcesClass> sources;

    @SerializedName("czesci")
    public List<CzesciClass> czesci;

    @SerializedName("dataset")
    public String dataset; // czy String




}
