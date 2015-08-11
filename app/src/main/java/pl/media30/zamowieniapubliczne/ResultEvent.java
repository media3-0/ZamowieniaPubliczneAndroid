package pl.media30.zamowieniapubliczne;

/**
 * Created by Andreas on 2015-07-27.
 */
import android.content.Intent;
import android.util.Log;

/**
 * Created by nuuneoi on 3/12/2015.
 */
public class ResultEvent {

    private int requestCode;
    private int resultCode;
    private Intent data;

    public ResultEvent(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Intent getData() {
        return data;
    }

    public void setData(Intent data) {
        this.data = data;
    }
}