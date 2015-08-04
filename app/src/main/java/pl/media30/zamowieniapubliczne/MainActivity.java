package pl.media30.zamowieniapubliczne;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import pl.media30.zamowieniapubliczne.Models.SingleElement.ObjectClass;

public class MainActivity extends AppCompatActivity {

    private List<ObjectClass> readRecordsFromFile() {
        FileInputStream fin;
        ObjectInputStream ois = null;
        try {
            fin = getApplicationContext().openFileInput("media30");
            ois = new ObjectInputStream(fin);
            List<ObjectClass> records = (List<ObjectClass>) ois.readObject();
            ois.close();
            Log.v("work", "Records read successfully");
            return records;
        } catch (Exception e) {
            Log.e("dont work", "Cant read saved records" + e.getMessage());
            return null;
        } finally {
            if (ois != null)
                try {
                    ois.close();
                } catch (Exception e) {
                    Log.e("dont work", "Error in closing stream while reading records" + e.getMessage());
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            RepositoryClass.getInstance().setListaUlubionych(readRecordsFromFile());
        } catch (Exception e) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityResultBus.getInstance().postQueue(
                new ActivityResultEvent(requestCode, resultCode, data));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        //------------------------------
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);


        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);


        SearchView searchView = null;


        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            searchView.setQueryHint("Szukaj...");
        }

        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                MainActivityFragment fragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
                fragment.glownaWyszukiwarka(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), WykresyActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }

        if (id == R.id.search) {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivityForResult(intent, 2);
            return true;
        }

        if (id == R.id.info) {
            Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.show) {
            Intent intent = new Intent(getApplicationContext(), UlubioneActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
