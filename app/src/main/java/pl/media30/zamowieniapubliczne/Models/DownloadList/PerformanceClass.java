package pl.media30.zamowieniapubliczne.Models.DownloadList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Adrian on 2015-07-17.
 */
public class PerformanceClass implements Serializable {

    @SerializedName("took")
    public int took;
}
