package pl.media30.zamowieniapubliczne;

/**
 * Created by Andreas on 2015-07-27.
 */
import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by nuuneoi on 3/12/2015.
 */
public class ResultBus extends Bus {

    private static ResultBus instance;

    public static ResultBus getInstance() {
        if (instance == null)
            instance = new ResultBus();
        return instance;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public void postQueue(final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ResultBus.getInstance().post(obj);
            }
        });
    }

}