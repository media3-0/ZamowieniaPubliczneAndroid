package pl.media30.zamowieniapubliczne;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import pl.media30.zamowieniapubliczne.Models.SingleElement.ObjectClass;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    boolean searchAllow = true;
    MenuItem searchItem;

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
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_reorder_black_48dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        try {
            List<ObjectClass> lista = readRecordsFromFile();
            if (lista != null)
                RepositoryClass.getInstance().setListaUlubionych(lista);
        } catch (Exception e) {
            Log.d("nie dziala", e.getMessage());
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();
                Intent intent;

                switch (menuItem.getItemId()) {

                    case R.id.search:
                        intent = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivityForResult(intent, 2);
                        return true;
                    case R.id.show:
                        intent = new Intent(getApplicationContext(), UlubioneActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.action_settings:
                        intent = new Intent(getApplicationContext(), WykresyActivity.class);
                        startActivityForResult(intent, 1);
                        return true;
                    case R.id.refresh:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        RepositoryClass.getInstance().setWszystkieZapyt("");
                        finish();
                        startActivity(intent);
                        return true;
                    case R.id.info:
                        intent = new Intent(getApplicationContext(), InfoActivity.class);
                        startActivity(intent);
                        return true;
                    default:
                        return true;
                }
            }
        });
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

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        searchItem = menu.findItem(R.id.action_search);
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

                if (searchAllow == true) {
                    searchAllow = false;
                    MainActivityFragment fragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
                    fragment.nowePob = true;
                    if (query.length() >= 1 && !query.equals("*")) {
                        RepositoryClass.getInstance().setGlowneZapyt(query);
                        fragment.strona = 1;
                        Log.d("Jedno", "Stukniecie");
                    } else if (query.toString().equals("*")) {
                        RepositoryClass.getInstance().setGlowneZapyt(null);
                        fragment.strona = 1;
                    }
                    if (RepositoryClass.getInstance().getDataObjectList() != null)
                        RepositoryClass.getInstance().deleteDataObjectList();
                    fragment.query = query;
                    fragment.onStop();
                    fragment.onStart();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAllow = true;
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onKeyDown(int keycode, KeyEvent e) {
        switch (keycode) {
            case KeyEvent.KEYCODE_MENU:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT))
                    drawerLayout.closeDrawers();
                else
                    drawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }

        return super.onKeyDown(keycode, e);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

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

        if (id == R.id.refresh) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            RepositoryClass.getInstance().setWszystkieZapyt("");
            finish();
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
