package pl.media30.zamowieniapubliczne;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Context context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wykresy);
        final ArrayList<BarEntry> entries = new ArrayList<>();
        final ArrayList<String> labels = new ArrayList<String>();
        final BarChart chart = new BarChart(context);

        /*
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));
        */


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://api.mojepanstwo.pl/dane/").build();
        MojePanstwoService service = restAdapter.create(MojePanstwoService.class);
        final ProgressDialog dialog =
                ProgressDialog.show(this, "Trwa wczytywanie danych", "Please Wait...");
        service.najwiekszeZamowienia(new Callback<BaseListClass>() {
            @Override
            public void success(BaseListClass baseListClass, Response response) {
                final List<DataObjectClass> dataObjectList = baseListClass.searchClass.dataobjects;

                entries.add(new BarEntry((float) (dataObjectList.get(0).dataClass.wartosc_cena), 0));
                entries.add(new BarEntry((float) (dataObjectList.get(1).dataClass.wartosc_cena), 1));
                entries.add(new BarEntry((float) (dataObjectList.get(2).dataClass.wartosc_cena), 2));
                entries.add(new BarEntry((float) (dataObjectList.get(3).dataClass.wartosc_cena), 3));
                entries.add(new BarEntry((float) (dataObjectList.get(4).dataClass.wartosc_cena), 4));
                entries.add(new BarEntry((float) (dataObjectList.get(5).dataClass.wartosc_cena), 5));


                BarDataSet dataset = new BarDataSet(entries, "Najwieksze zamowienia");

                labels.add(dataObjectList.get(0).dataClass.nazwa);
                labels.add(dataObjectList.get(1).dataClass.nazwa);
                labels.add(dataObjectList.get(2).dataClass.nazwa);
                labels.add(dataObjectList.get(3).dataClass.nazwa);
                labels.add(dataObjectList.get(4).dataClass.nazwa);
                labels.add(dataObjectList.get(5).dataClass.nazwa);
                setContentView(chart);

                BarData data = new BarData(labels, dataset);
                chart.setData(data);
                chart.setDescription("");

                chart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Toast.makeText(getApplicationContext(),"Element: "+ chart.getId(), Toast.LENGTH_SHORT).show();

                    }
                });
                chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                        String info = "Nazwa: "+ dataObjectList.get(0).dataClass.nazwa;
Log.d("Nazwa prz: ", info);

                        Toast.makeText(getApplicationContext(), "Element: " + e.getXIndex(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Element: " + e.getXIndex(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, ZamowienieActivityFragment.class);
                        DataObjectClass dataObjectClass = dataObjectList.get(e.getXIndex());
                        String objToStr = new Gson().toJson(dataObjectClass);
                        Bundle objClass = new Bundle();
                        objClass.putString("myObject", objToStr);
                        intent.putExtras(objClass);
                        startActivity(intent);

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
