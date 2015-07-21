package pl.media30.zamowieniapubliczne;

import java.util.List;

import pl.media30.zamowieniapubliczne.Models.DownloadList.DataObjectClass;

/**
 * Created by Adrian on 2015-07-21.
 */
public class RepositoryClass {
    private List<DataObjectClass> mDataset;
    private static RepositoryClass instance = null;
    protected RepositoryClass() {
        // Exists only to defeat instantiation.
    }
    public static RepositoryClass getInstance() {
        if(instance == null) {
            instance = new RepositoryClass();
        }
        return instance;
    }

}
