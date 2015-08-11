package pl.media30.zamowieniapubliczne.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pl.media30.zamowieniapubliczne.Models.DownloadList.BaseListClass;
import pl.media30.zamowieniapubliczne.Models.DownloadList.DataObjectClass;
import pl.media30.zamowieniapubliczne.MojePanstwoService;
import pl.media30.zamowieniapubliczne.R;
import pl.media30.zamowieniapubliczne.RepositoryClass;
import retrofit.RestAdapter;

/**
 * Aktywność wyświetlająca wykres. W tej wersji wykres wyświetla największe zamówienia wg. podanych kryteriów.
 */


public class WykresyActivity extends Activity {

    boolean wczytajRaz;
    List<DataObjectClass> dataObjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Context context = this;
        wczytajRaz = true;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wykresy);
        final ArrayList<BarEntry> entries = new ArrayList<>();
        final ArrayList<String> labels = new ArrayList<String>();
        final BarChart chart = new BarChart(context);
        RepositoryClass.getInstance().setStronaUlub(-1);

        final ProgressDialog dialog =
                ProgressDialog.show(this, "Trwa wczytywanie danych", "Zaczekaj na wczytanie danych...");
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://api.mojepanstwo.pl/dane/").build();
                MojePanstwoService service = restAdapter.create(MojePanstwoService.class);
                try {
                    BaseListClass baseListClass = service.najwiekszeZamowienia(1, RepositoryClass.getInstance().getGlowneZapyt(), RepositoryClass.getInstance().getWyszukiwanieMiasta(), RepositoryClass.getInstance().getWyszukiwanieWojew(), RepositoryClass.getInstance().getWyszukiwanieKodowPoczt(), RepositoryClass.getInstance().getWyszukiwanieZamawNazwa(), RepositoryClass.getInstance().getWyszukiwanieZamawREGON(), RepositoryClass.getInstance().getWyszukiwanieZamawWWW(), RepositoryClass.getInstance().getWyszukiwanieZamawEmail());

                    if (RepositoryClass.getInstance().getGlowneZapyt() == null) {
                        dataObjectList = baseListClass.searchClass.dataobjects;
                    } else {
                        final int wszystko = baseListClass.searchClass.paginationClass.total;
                        final int iloscElemNaStr = baseListClass.searchClass.paginationClass.count;
                        int iloscZaladElem = RepositoryClass.getInstance().getDataObjectList().size();
                        dataObjectList = baseListClass.searchClass.dataobjects;
                        dataObjectList.clear();
                        for (int i = 0; i < (Math.ceil((double) iloscZaladElem / (double) iloscElemNaStr)); i++) {
                            RestAdapter restAdapterX = new RestAdapter.Builder().setEndpoint("https://api.mojepanstwo.pl/dane/").build();
                            MojePanstwoService serviceX = restAdapterX.create(MojePanstwoService.class);
                            BaseListClass baseListClass1 = serviceX.najwiekszeZamowienia(i + 1, RepositoryClass.getInstance().getGlowneZapyt(), RepositoryClass.getInstance().getWyszukiwanieMiasta(), RepositoryClass.getInstance().getWyszukiwanieWojew(), RepositoryClass.getInstance().getWyszukiwanieKodowPoczt(), RepositoryClass.getInstance().getWyszukiwanieZamawNazwa(), RepositoryClass.getInstance().getWyszukiwanieZamawREGON(), RepositoryClass.getInstance().getWyszukiwanieZamawWWW(), RepositoryClass.getInstance().getWyszukiwanieZamawEmail());
                            dataObjectList.addAll(baseListClass1.searchClass.dataobjects);
                        }
                    }
                } catch (Exception e) {
                }
            }
        });

        int rozmiarWykresu = 0;
        try {
            t1.start();
            t1.join();
            Collections.sort(dataObjectList, new Comparator<DataObjectClass>() {
                        @Override
                        public int compare(DataObjectClass lhs, DataObjectClass rhs) {
                            return Double.compare(rhs.dataClass.wartosc_cena, lhs.dataClass.wartosc_cena);
                        }
                    }
            );
            rozmiarWykresu = 50;
            if (dataObjectList.size() < 50)
                rozmiarWykresu = dataObjectList.size();

            for (int i = 0; i < rozmiarWykresu; i++) {
                entries.add(new BarEntry((float) (dataObjectList.get(i).dataClass.wartosc_cena), i));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {

        }

        BarDataSet dataset;
        if (RepositoryClass.getInstance().getGlowneZapyt() == null || RepositoryClass.getInstance().getGlowneZapyt() == "")
            dataset = new BarDataSet(entries, "Najwieksze zamowienia");
        else
            dataset = new BarDataSet(entries, "Aktualnie załadowane najwieksze zamowienia");
        for (int i = 0; i < rozmiarWykresu; i++) {
            labels.add(Integer.toString(i + 1));
        }

        setContentView(chart);

        BarData data = new BarData(labels, dataset);
        chart.setData(data);
        chart.setDescription("");
        chart.setScaleMinima(5f, 1f);
        chart.animateXY(4, 4);


        chart.setOnChartValueSelectedListener(
                new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                        if (wczytajRaz == true) {
                            Intent intent = new Intent(context, ZamowienieActivityFragment.class);
                            DataObjectClass dataObjectClass = dataObjectList.get(e.getXIndex());
                            String objToStr = new Gson().toJson(dataObjectClass);
                            Bundle objClass = new Bundle();
                            objClass.putString("myObject", objToStr);
                            intent.putExtras(objClass);
                            startActivity(intent);
                            wczytajRaz = false;
                            chart.dispatchSetSelected(false);
                        }
                    }
                    @Override
                    public void onNothingSelected() {
                    }
                }
        );
        dialog.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        wczytajRaz = true;
    }
}
