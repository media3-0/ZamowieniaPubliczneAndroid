package pl.media30.zamowieniapubliczne.Models.DownloadList;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Adrian on 2015-07-17.
 */
public class PerformanceClass {

    public PerformanceClass getPerformanceClass() {
        return performanceClass;
    }

    public void setPerformanceClass(PerformanceClass performanceClass) {
        this.performanceClass = performanceClass;
    }

    private PerformanceClass performanceClass;


    @SerializedName("took")
    public int took;
}
