package pl.media30.zamowieniapubliczne;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import pl.media30.zamowieniapubliczne.Models.DownloadList.BaseListClass;
import pl.media30.zamowieniapubliczne.Models.DownloadList.DataObjectClass;
import pl.media30.zamowieniapubliczne.Models.SingleElement.BaseClass;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static java.lang.Integer.parseInt;


public class WykresyActivity extends ActionBarActivity {

    boolean wczytajRaz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Context context = this;
        wczytajRaz = true;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wykresy);
        final ArrayList<BarEntry> entries = new ArrayList<>();
        final ArrayList<String> labels = new ArrayList<String>();
        final BarChart chart = new BarChart(context);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://api.mojepanstwo.pl/dane/").build();
        MojePanstwoService service = restAdapter.create(MojePanstwoService.class);
        final ProgressDialog dialog =
                ProgressDialog.show(this, "Trwa wczytywanie danych", "Zaczekaj na wczytanie danych...");
         service.najwiekszeZamowienia(RepositoryClass.getInstance().getWyszukiwanieMiasta(), RepositoryClass.getInstance().getWyszukiwanieWojew(), RepositoryClass.getInstance().getWyszukiwanieKodowPoczt(), RepositoryClass.getInstance().getWyszukiwanieZamawNazwa(), RepositoryClass.getInstance().getWyszukiwanieZamawREGON(), RepositoryClass.getInstance().getWyszukiwanieZamawWWW(), RepositoryClass.getInstance().getWyszukiwanieZamawEmail(), new Callback<BaseListClass>() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
            @Override
            public void success(BaseListClass baseListClass, Response response) {
                final List<DataObjectClass> dataObjectList = baseListClass.searchClass.dataobjects;

                for(int i=0;i<dataObjectList.size();i++){
                    entries.add(new BarEntry((float) (dataObjectList.get(i).dataClass.wartosc_cena),i));
                }

                BarDataSet dataset = new BarDataSet(entries, "Najwieksze zamowienia");
                for(int i=0;i<dataObjectList.size();i++) {
                    //labels.add(dataObjectList.get(i).slug);
                    labels.add(Integer.toString(i));
                }
                setContentView(chart);

                BarData data = new BarData(labels, dataset);
                chart.setData(data);
                chart.setDescription("");
                chart.setScaleMinima(5f, 1f);
                chart.animate();
                chart.animateXY(4,4);

                chart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Toast.makeText(getApplicationContext(),"Element: "+ chart.getId(), Toast.LENGTH_SHORT).show();

                    }
                });
                chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                        if(wczytajRaz==true){
                            String info = "Nazwa: "+ dataObjectList.get(e.getXIndex()).dataClass.nazwa;
                            Log.d("Nazwa prz: ", info);

                            Toast.makeText(getApplicationContext(), info+"", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(), "Element: " + e.getXIndex(), Toast.LENGTH_SHORT).show();
                            //  Toast.makeText(getApplicationContext(), "Element: " + e.getXIndex(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, ZamowienieActivityFragment.class);
                            DataObjectClass dataObjectClass = dataObjectList.get(e.getXIndex());
                            String objToStr = new Gson().toJson(dataObjectClass);
                            Bundle objClass = new Bundle();
                            objClass.putString("myObject", objToStr);
                            intent.putExtras(objClass);
                            startActivity(intent);
                            wczytajRaz=false;
                            chart.dispatchSetSelected(false);


                        }
                    }
                    @Override
                    public void onNothingSelected() {
                    }
                });
                dialog.dismiss();
            }
            @Override
            public void failure(RetrofitError error) {
                Log.d("ERROR", "ERROR w retroficie");
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Błąd. Sprawdź połączenie z internetem",Toast.LENGTH_SHORT).show();
                Log.d("Erroe to:", error.getMessage() + "");


            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wykresy, menu);
        return true;
    }
    @Override
    public void onResume(){
        super.onResume();
        wczytajRaz=true;
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
}
