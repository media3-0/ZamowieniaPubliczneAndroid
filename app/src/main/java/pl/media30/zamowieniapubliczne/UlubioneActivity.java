package pl.media30.zamowieniapubliczne;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.squareup.otto.Subscribe;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import pl.media30.zamowieniapubliczne.Adapters.MyAdapter;
import pl.media30.zamowieniapubliczne.Adapters.UlubioneAdapter;
import pl.media30.zamowieniapubliczne.Models.SingleElement.ObjectClass;


public class UlubioneActivity extends ActionBarActivity {

    UltimateRecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    UlubioneAdapter mAdapter;
    Bundle bundle = new Bundle();
    Button button;



    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ActivityResultEvent event) {
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
            ActivityResultBus.getInstance().register(mActivityResultSubscriber);
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
        ActivityResultBus.getInstance().unregister(mActivityResultSubscriber);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ulubione, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
