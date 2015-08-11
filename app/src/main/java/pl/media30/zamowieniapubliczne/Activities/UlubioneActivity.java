package pl.media30.zamowieniapubliczne.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.squareup.otto.Subscribe;

import pl.media30.zamowieniapubliczne.ResultBus;
import pl.media30.zamowieniapubliczne.ResultEvent;
import pl.media30.zamowieniapubliczne.Adapters.UlubioneAdapter;
import pl.media30.zamowieniapubliczne.R;
import pl.media30.zamowieniapubliczne.RepositoryClass;

/**
 * Aktywność zawierająca wszystkie elementy obserwowane. Obiekty są dostępne także w trybie offline.
 */


public class UlubioneActivity extends ActionBarActivity {

    UltimateRecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    UlubioneAdapter mAdapter;
    Bundle bundle = new Bundle();
    Button button;



    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ResultEvent event) {
            int requestCode = event.getRequestCode();
            int resultCode = event.getResultCode();
            Intent data = event.getData();
            onActivityResult(requestCode, resultCode, data);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        try {
            ResultBus.getInstance().register(mActivityResultSubscriber);
        } catch (Exception e) {
        }
        mRecyclerView = (UltimateRecyclerView) findViewById(R.id.ultimate_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        try {
            mAdapter = new UlubioneAdapter(RepositoryClass.getInstance().getListaUlubionych());
            mRecyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        ResultBus.getInstance().unregister(mActivityResultSubscriber);
        bundle.putInt("getPosUlub", mLayoutManager.findFirstVisibleItemPosition());
        Log.d("STOP", mLayoutManager.findFirstVisibleItemPosition() + "");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("uPozycja", bundle.getInt("getPosUlub") + "");
        mLayoutManager.scrollToPosition(bundle.getInt("getPosUlub"));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ulubione);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
