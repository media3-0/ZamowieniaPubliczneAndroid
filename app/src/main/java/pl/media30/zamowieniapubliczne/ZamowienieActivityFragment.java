package pl.media30.zamowieniapubliczne;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.List;

import com.google.gson.Gson;
import com.pnikosis.materialishprogress.ProgressWheel;

import pl.media30.zamowieniapubliczne.Models.DownloadList.DataObjectClass;
import pl.media30.zamowieniapubliczne.Models.SingleElement.BaseClass;
import pl.media30.zamowieniapubliczne.Models.SingleElement.ObjectClass;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static java.lang.Integer.parseInt;

/**
 * A placeholder fragment containing a simple view.
 */
public class ZamowienieActivityFragment extends Activity {
    TextView textViewZamawiajacyTelefon;
    TextView textViewZamawiajacyWWW;
    TextView textViewZamawiajacyEmail;
    CardView cardViewZamawiajacyTelefon;

    String telefon;
    String mail;
    String www;

    Button button;
    boolean ulubione = false;
    int id;

    public boolean writeRecordsToFile(List<ObjectClass> records) {
        FileOutputStream fos;
        ObjectOutputStream oos = null;
        try {
            fos = getApplicationContext().openFileOutput("media30", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(records);
            oos.close();
            return true;
        } catch (Exception e) {
            Log.e("Dont work", "Cant save records" + e.getMessage());
            return false;
        } finally {
            if (oos != null)
                try {
                    oos.close();
                } catch (Exception e) {
                    Log.e("dont work", "Error while closing stream " + e.getMessage());
                }
        }
    }

    public boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public ZamowienieActivityFragment() {
    }

    @Override
    protected void onResume(){
        super.onResume();

        Log.d("ddf", getIntent().getStringExtra("Activity")+"");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_zamowienie);
        String jsonMyObject = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("myObject");
        }
        DataObjectClass myObject = new Gson().fromJson(jsonMyObject, DataObjectClass.class);
        int stronaUlub = RepositoryClass.getInstance().getStronaUlub();
        if (stronaUlub >= 0) {
            id = parseInt(RepositoryClass.getInstance().getListaUlubionych().get(stronaUlub).dataClass.id);
        } else {
            id = parseInt(myObject.id);
        }
        button = (Button) findViewById(R.id.button2);
        //dostep do layers
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://api.mojepanstwo.pl/dane/").build();
        MojePanstwoService service = restAdapter.create(MojePanstwoService.class);
        final ProgressWheel progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        progressWheel.setBarColor(Color.BLACK);
        progressWheel.spin();

        final RelativeLayout mainRelativeLayout = (RelativeLayout) findViewById(R.id.MainRelativeLayout);
        final RelativeLayout wheelRelativeLayout = (RelativeLayout) findViewById(R.id.WheelRelativeLayout);
        mainRelativeLayout.setVisibility(View.GONE);

        service.singleOrder(id, new Callback<BaseClass>() {
                    @Override
                    public void success(final BaseClass baseClass, Response response) {
                        try{
                            for (int i = 0; i < RepositoryClass.getInstance().getListaUlubionych().size(); i++) {
                                if (RepositoryClass.getInstance().getListaUlubionych().get(i).dataClass.id.equals(baseClass.objectClass.dataClass.id)) {
                                    button.setText("Usuń  z  obserwowanych");
                                    button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visibility_off_black_48dp, 0, 0, 0);
                                    ulubione = true;
                                    break;
                                }
                            }
                        }catch(Exception e){}

                        if (ulubione == false)
                            button.setText("Dodaj do obserwowanych");
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visibility_black_48dp, 0, 0, 0);


                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean usunieto = false;
                                for (int i = 0; i < RepositoryClass.getInstance().getListaUlubionych().size(); i++) {
                                    if (RepositoryClass.getInstance().getListaUlubionych().get(i).dataClass.id.equals(baseClass.objectClass.dataClass.id)) {
                                        RepositoryClass.getInstance().removeListaUlubionych(i);
                                        usunieto = true;
                                        button.setText("Dodaj do obserwowanych");
                                        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visibility_black_48dp, 0, 0, 0);
                                        Log.d("ZAF", "usuwanie z ulub.");

                                        break;
                                    }
                                }
                                if (usunieto == false) {
                                    RepositoryClass.getInstance().addListaUlubionych(baseClass.objectClass);
                                    Toast.makeText(getApplicationContext(), "Dodano do obserwowanych", Toast.LENGTH_LONG);
                                    button.setText("Usuń  z  obserwowanych");
                                    button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visibility_off_black_48dp, 0, 0, 0);
                                    Log.d("ZAF", "Dodano do listy");
                                }
                                try {
                                    writeRecordsToFile(RepositoryClass.getInstance().getListaUlubionych());
                                }catch(Exception e){

                                }

                            }
                        });

                        TextView zamawiajacyNazwa = (TextView) findViewById(R.id.textViewZamawiajacyNazwa);
                        zamawiajacyNazwa.setText(baseClass.objectClass.dataClass.zamawiajacy_nazwa.toString());

                        TextView zamawiajacyID = (TextView) findViewById(R.id.textViewZamawiajacyID);
                        zamawiajacyID.setText(baseClass.objectClass.dataClass.zamawiajacy_id.toString());

                        TextView zamawiajacyRodzaj = (TextView) findViewById(R.id.textViewZamawiajacyRodzaj);
                        zamawiajacyRodzaj.setText(baseClass.objectClass.dataClass.zamawiajacy_rodzaj.toString());

                        TextView zamawiajacyRegon = (TextView) findViewById(R.id.textViewZamawiajacyRegon);
                        zamawiajacyRegon.setText(baseClass.objectClass.dataClass.zamawiajacy_regon.toString());


                        TextView zamawiajacyWojewodztwo = (TextView) findViewById(R.id.textViewZamawiajacyWojewodztwo);
                        zamawiajacyWojewodztwo.setText(baseClass.objectClass.dataClass.zamawiajacy_wojewodztwo.toString());

                        TextView zamawiajacyIDWojewodztwa = (TextView) findViewById(R.id.textViewZamawiajacyIDWojewodztwa);
                        zamawiajacyIDWojewodztwa.setText(baseClass.objectClass.dataClass.wojewodztwo_id.toString());

                        TextView zamawiajacyIDPowiatu = (TextView) findViewById(R.id.textViewZamawiajacyIDPowiatu);
                        zamawiajacyIDPowiatu.setText(baseClass.objectClass.dataClass.powiat_id.toString());

                        TextView zamawiajacyIDGminy = (TextView) findViewById(R.id.textViewZamawiajacyIDGminy);
                        zamawiajacyIDGminy.setText(baseClass.objectClass.dataClass.gmina_id.toString());

                        TextView zamawiajacyMiejscowosc = (TextView) findViewById(R.id.textViewZamawiajacyMiejscowosc);
                        zamawiajacyMiejscowosc.setText(baseClass.objectClass.dataClass.zamawiajacy_miejscowosc.toString());

                        TextView zamawiajacyKodPocztowy = (TextView) findViewById(R.id.textViewZamawiajacyKodPocztowy);
                        zamawiajacyKodPocztowy.setText(baseClass.objectClass.dataClass.zamawiajacy_kod_poczt.toString());

                        TextView zamawiajacyIDKoduPocztowego = (TextView) findViewById(R.id.textViewZamawiajacyIDKoduPocztowego);
                        zamawiajacyIDKoduPocztowego.setText(baseClass.objectClass.dataClass.zamawiajacyKod_pocztowy_id.toString());

                        TextView zamawiajacyUlica = (TextView) findViewById(R.id.textViewZamawiajacyUlica);
                        zamawiajacyUlica.setText(baseClass.objectClass.dataClass.zamawiajacy_ulica.toString());

                        TextView zamawiajacyNrDomu = (TextView) findViewById(R.id.textViewZamawiajacyNrDomu);
                        zamawiajacyNrDomu.setText(baseClass.objectClass.dataClass.zamawiajacy_nr_domu.toString());

                        TextView zamawiajacyNrMIeszkania = (TextView) findViewById(R.id.textViewZamawiajacyNrMieszkania);
                        zamawiajacyNrMIeszkania.setText(baseClass.objectClass.dataClass.zamawiajacy_nr_miesz.toString());


                        TextView zamawiajacyEmail = (TextView) findViewById(R.id.textViewZamawiajacyEmail);
                        zamawiajacyEmail.setText(baseClass.objectClass.dataClass.zamawiajacy_email.toString());
                        mail = baseClass.objectClass.dataClass.zamawiajacy_email.toString().trim();

                        TextView zamawiajacyTelefon = (TextView) findViewById(R.id.textViewZamawiajacyTelefon);
                        zamawiajacyTelefon.setText(baseClass.objectClass.dataClass.zamawiajacy_tel.toString());
                        telefon = baseClass.objectClass.dataClass.zamawiajacy_tel.toString().trim();

                        TextView zamawiajacyFax = (TextView) findViewById(R.id.textViewZamawiajacyFax);
                        zamawiajacyFax.setText(baseClass.objectClass.dataClass.zamawiajacy_fax.toString());

                        TextView zamawiajacyWWW = (TextView) findViewById(R.id.textViewZamawiajacyWWW);
                        zamawiajacyWWW.setText(baseClass.objectClass.dataClass.zamawiajacy_www.toString());
                        www = baseClass.objectClass.dataClass.zamawiajacy_www.toString().trim();


                        TextView zamowienieNazwa = (TextView) findViewById(R.id.textViewZamowienieNazwa);
                        zamowienieNazwa.setText(baseClass.objectClass.dataClass.nazwa.toString());

                        TextView zamowienieTyp = (TextView) findViewById(R.id.textViewZamowienieTyp);
                        zamowienieTyp.setText(baseClass.objectClass.dataClass.typyNazwa.toString());

                        TextView zamowienieTypSymbol = (TextView) findViewById(R.id.textViewZamowienieTypSymbol);
                        zamowienieTypSymbol.setText(baseClass.objectClass.dataClass.typySymbol.toString());

                        TextView zamowienieRodzaj = (TextView) findViewById(R.id.textViewZamowienieRodzaj);
                        zamowienieRodzaj.setText(baseClass.objectClass.dataClass.rodzajeNazwa.toString());

                        TextView zamowienieDataPublikacji = (TextView) findViewById(R.id.textViewZamowienieDataPublikacji);
                        zamowienieDataPublikacji.setText(baseClass.objectClass.dataClass.data_publikacji.toString());

                        TextView zamowienieUE = (TextView) findViewById(R.id.textViewZamowienieUE);
                        if (tryParseInt(baseClass.objectClass.dataClass.zamowienie_ue)) {
                            switch (Integer.parseInt(baseClass.objectClass.dataClass.zamowienie_ue)) {
                                case 0:
                                    zamowienieUE.setText("Nie");
                                    break;
                                case 1:
                                    zamowienieUE.setText("Tak");
                                    break;
                                default:
                                    zamowienieUE.setText(baseClass.objectClass.dataClass.zamowienie_ue.toString());
                            }
                        } else {
                            zamowienieUE.setText(baseClass.objectClass.dataClass.zamowienie_ue.toString());
                        }

                        DecimalFormat df = new DecimalFormat("#");
                        df.setMaximumFractionDigits(2);

                        TextView zamowienieSzacowanaWartosc = (TextView) findViewById(R.id.textViewZamowienieSzacowanaWartosc);
                        double zamowienieSzacowanaWartoscD = (baseClass.objectClass.dataClass.wartosc_szacowana);
                        zamowienieSzacowanaWartosc.setText(df.format(zamowienieSzacowanaWartoscD) + " PLN");

                        TextView zamowienieCenaWybranejOferty = (TextView) findViewById(R.id.textViewZamowienieCenaWybranejOferty);
                        double zamowienieCenaWybranejOfertyD = (baseClass.objectClass.dataClass.wartosc_cena);
                        zamowienieCenaWybranejOferty.setText(df.format(zamowienieCenaWybranejOfertyD) + " PLN");

                        TextView zamowienieNajtanszaOferta = (TextView) findViewById(R.id.textViewZamowienieNajtanszaOferta);
                        double zamowienieNajtanszaOfertaD = (Double.parseDouble(baseClass.objectClass.dataClass.wartosc_cena_min));
                        zamowienieNajtanszaOferta.setText(df.format(zamowienieNajtanszaOfertaD) + " PLN");

                        TextView zamowienieNajdrozszaOferta = (TextView) findViewById(R.id.textViewZamowienieNajdrozszaOferta);
                        double zamowienieNajdrozszaOfertaD = (baseClass.objectClass.dataClass.wartosc_cena_max);
                        if (zamowienieNajdrozszaOfertaD == 0.0)
                        {
                            zamowienieNajdrozszaOferta.setText(df.format(zamowienieCenaWybranejOfertyD) + " PLN");
                        }
                        else
                        {
                            zamowienieNajdrozszaOferta.setText(df.format(zamowienieNajdrozszaOfertaD) + " PLN");
                        }

                        try {
                            TextView zamowieniePrzedmiot = (TextView) findViewById(R.id.textViewZamowieniePrzedmiot);
                            if (baseClass.objectClass.layers.detailsClass.przedmiot == "") {
                                zamowieniePrzedmiot.setText("Dane nie zostały wprowadzone");
                            } else {
                                zamowieniePrzedmiot.setText(baseClass.objectClass.layers.detailsClass.przedmiot.toString());
                            }
                        } catch (NullPointerException e) {
                            TextView zamowieniePrzedmiot = (TextView) findViewById(R.id.textViewZamowieniePrzedmiot);
                            zamowieniePrzedmiot.setText("Dane nie zostały wprowadzone");
                        }

                        try {
                            TextView zamowienieUprawnienie = (TextView) findViewById(R.id.textViewZamowienieUprawnienie);
                            if (baseClass.objectClass.layers.detailsClass.uprawnienie == "") {
                                zamowienieUprawnienie.setText("Dane nie zostały wprowadzone");
                            } else {
                                zamowienieUprawnienie.setText(baseClass.objectClass.layers.detailsClass.uprawnienie.toString());
                            }
                        } catch (NullPointerException e) {
                            TextView zamowienieUprawnienie = (TextView) findViewById(R.id.textViewZamowienieUprawnienie);
                            zamowienieUprawnienie.setText("Dane nie zostały wprowadzone");
                        }

                        try {
                            TextView zamowienieWiedza = (TextView) findViewById(R.id.textViewZamowienieWiedza);
                            if (baseClass.objectClass.layers.detailsClass.wiedza == "") {
                                zamowienieWiedza.setText("Dane nie zostały wprowadzone");
                            } else {
                                zamowienieWiedza.setText(baseClass.objectClass.layers.detailsClass.wiedza.toString());
                            }
                        } catch (NullPointerException e) {
                            TextView zamowienieWiedza = (TextView) findViewById(R.id.textViewZamowienieWiedza);
                            zamowienieWiedza.setText("Dane nie zostały wprowadzone");
                        }

                        try {
                            TextView zamowieniePotencjal = (TextView) findViewById(R.id.textViewZamowieniePotencjal);
                            if (baseClass.objectClass.layers.detailsClass.potencjal == "") {
                                zamowieniePotencjal.setText("Dane nie zostały wprowadzone");
                            } else {
                                zamowieniePotencjal.setText(baseClass.objectClass.layers.detailsClass.potencjal.toString());
                            }
                        } catch (NullPointerException e) {
                            TextView zamowieniePotencjal = (TextView) findViewById(R.id.textViewZamowieniePotencjal);
                            zamowieniePotencjal.setText("Dane nie zostały wprowadzone");
                        }

                        try {
                            TextView zamowienieOsobyZdolne = (TextView) findViewById(R.id.textViewZamowienieOsobyZdolne);
                            if (baseClass.objectClass.layers.detailsClass.osoby_zdolne == "") {
                                zamowienieOsobyZdolne.setText("Dane nie zostały wprowadzone");
                            } else {
                                zamowienieOsobyZdolne.setText(baseClass.objectClass.layers.detailsClass.osoby_zdolne.toString());
                            }
                        } catch (NullPointerException e) {
                            TextView zamowienieOsobyZdolne = (TextView) findViewById(R.id.textViewZamowienieOsobyZdolne);
                            zamowienieOsobyZdolne.setText("Dane nie zostały wprowadzone");
                        }

                        try {
                            TextView zamowienieSytuacjaEkonomiczna = (TextView) findViewById(R.id.textViewZamowienieSytuacjaEkonomiczna);
                            if (baseClass.objectClass.layers.detailsClass.sytuacja_ekonomiczna == "") {
                                zamowienieSytuacjaEkonomiczna.setText("Dane nie zostały wprowadzone");
                            } else {
                                zamowienieSytuacjaEkonomiczna.setText(baseClass.objectClass.layers.detailsClass.sytuacja_ekonomiczna.toString());
                            }
                        } catch (NullPointerException e) {
                            TextView zamowienieSytuacjaEkonomiczna = (TextView) findViewById(R.id.textViewZamowienieSytuacjaEkonomiczna);
                            zamowienieSytuacjaEkonomiczna.setText("Dane nie zostały wprowadzone");
                        }

                        //progressWheel.stopSpinning();
                        wheelRelativeLayout.setVisibility(View.GONE);
                        mainRelativeLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //                 progressWheel.stopSpinning();
                        if (getIntent().getStringExtra("Activity").equals("Ulubione")) {
                            Log.d("Stronabun", Integer.parseInt(getIntent().getStringExtra("strona")) + "");
                            final ObjectClass objectClass =
                                    RepositoryClass.getInstance().getListaUlubionych().get(Integer.parseInt(getIntent().getStringExtra("strona")));
                            button.setText("Jest w ulub.");

                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    boolean usunieto = false;
                                    for (int i = 0; i < RepositoryClass.getInstance().getListaUlubionych().size(); i++) {
                                        if (RepositoryClass.getInstance().getListaUlubionych().get(i).dataClass.id.equals(objectClass.dataClass.id)) {
                                            RepositoryClass.getInstance().removeListaUlubionych(i);
                                            usunieto = true;
                                            button.setText("Dodaj do ulub.");
                                            Log.d("ZAF", "usuwanie z ulub.");

                                            break;
                                        }
                                    }
                                    if (usunieto == false) {
                                        RepositoryClass.getInstance().addListaUlubionych(objectClass);
                                        Toast.makeText(getApplicationContext(), "Dodano do ulubionych", Toast.LENGTH_LONG);
                                        button.setText("Usun z ulub.");
                                        Log.d("ZAF", "Dodano do listy");
                                    }
                                    try {
                                        writeRecordsToFile(RepositoryClass.getInstance().getListaUlubionych());
                                    } catch (Exception e) {

                                    }

                                }
                            });
                            TextView zamawiajacyNazwa = (TextView) findViewById(R.id.textViewZamawiajacyNazwa);
                            zamawiajacyNazwa.setText(objectClass.dataClass.zamawiajacy_nazwa.toString());

                            TextView zamawiajacyID = (TextView) findViewById(R.id.textViewZamawiajacyID);
                            zamawiajacyID.setText(objectClass.dataClass.zamawiajacy_id.toString());

                            TextView zamawiajacyRodzaj = (TextView) findViewById(R.id.textViewZamawiajacyRodzaj);
                            zamawiajacyRodzaj.setText(objectClass.dataClass.zamawiajacy_rodzaj.toString());

                            TextView zamawiajacyRegon = (TextView) findViewById(R.id.textViewZamawiajacyRegon);
                            zamawiajacyRegon.setText(objectClass.dataClass.zamawiajacy_regon.toString());


                            TextView zamawiajacyWojewodztwo = (TextView) findViewById(R.id.textViewZamawiajacyWojewodztwo);
                            zamawiajacyWojewodztwo.setText(objectClass.dataClass.zamawiajacy_wojewodztwo.toString());

                            TextView zamawiajacyIDWojewodztwa = (TextView) findViewById(R.id.textViewZamawiajacyIDWojewodztwa);
                            zamawiajacyIDWojewodztwa.setText(objectClass.dataClass.wojewodztwo_id.toString());

                            TextView zamawiajacyIDPowiatu = (TextView) findViewById(R.id.textViewZamawiajacyIDPowiatu);
                            zamawiajacyIDPowiatu.setText(objectClass.dataClass.powiat_id.toString());

                            TextView zamawiajacyIDGminy = (TextView) findViewById(R.id.textViewZamawiajacyIDGminy);
                            zamawiajacyIDGminy.setText(objectClass.dataClass.gmina_id.toString());

                            TextView zamawiajacyMiejscowosc = (TextView) findViewById(R.id.textViewZamawiajacyMiejscowosc);
                            zamawiajacyMiejscowosc.setText(objectClass.dataClass.zamawiajacy_miejscowosc.toString());

                            TextView zamawiajacyKodPocztowy = (TextView) findViewById(R.id.textViewZamawiajacyKodPocztowy);
                            zamawiajacyKodPocztowy.setText(objectClass.dataClass.zamawiajacy_kod_poczt.toString());

                            TextView zamawiajacyIDKoduPocztowego = (TextView) findViewById(R.id.textViewZamawiajacyIDKoduPocztowego);
                            zamawiajacyIDKoduPocztowego.setText(objectClass.dataClass.zamawiajacyKod_pocztowy_id.toString());

                            TextView zamawiajacyUlica = (TextView) findViewById(R.id.textViewZamawiajacyUlica);
                            zamawiajacyUlica.setText(objectClass.dataClass.zamawiajacy_ulica.toString());

                            TextView zamawiajacyNrDomu = (TextView) findViewById(R.id.textViewZamawiajacyNrDomu);
                            zamawiajacyNrDomu.setText(objectClass.dataClass.zamawiajacy_nr_domu.toString());

                            TextView zamawiajacyNrMIeszkania = (TextView) findViewById(R.id.textViewZamawiajacyNrMieszkania);
                            zamawiajacyNrMIeszkania.setText(objectClass.dataClass.zamawiajacy_nr_miesz.toString());


                            TextView zamawiajacyEmail = (TextView) findViewById(R.id.textViewZamawiajacyEmail);
                            zamawiajacyEmail.setText(objectClass.dataClass.zamawiajacy_email.toString());
                            mail = objectClass.dataClass.zamawiajacy_email.toString().trim();

                            TextView zamawiajacyTelefon = (TextView) findViewById(R.id.textViewZamawiajacyTelefon);
                            zamawiajacyTelefon.setText(objectClass.dataClass.zamawiajacy_tel.toString());
                            telefon = objectClass.dataClass.zamawiajacy_tel.toString().trim();

                            TextView zamawiajacyFax = (TextView) findViewById(R.id.textViewZamawiajacyFax);
                            zamawiajacyFax.setText(objectClass.dataClass.zamawiajacy_fax.toString());

                            TextView zamawiajacyWWW = (TextView) findViewById(R.id.textViewZamawiajacyWWW);
                            zamawiajacyWWW.setText(objectClass.dataClass.zamawiajacy_www.toString());
                            www = objectClass.dataClass.zamawiajacy_www.toString().trim();


                            TextView zamowienieNazwa = (TextView) findViewById(R.id.textViewZamowienieNazwa);
                            zamowienieNazwa.setText(objectClass.dataClass.nazwa.toString());

                            TextView zamowienieTyp = (TextView) findViewById(R.id.textViewZamowienieTyp);
                            zamowienieTyp.setText(objectClass.dataClass.typyNazwa.toString());

                            TextView zamowienieTypSymbol = (TextView) findViewById(R.id.textViewZamowienieTypSymbol);
                            zamowienieTypSymbol.setText(objectClass.dataClass.typySymbol.toString());

                            TextView zamowienieRodzaj = (TextView) findViewById(R.id.textViewZamowienieRodzaj);
                            zamowienieRodzaj.setText(objectClass.dataClass.rodzajeNazwa.toString());

                            TextView zamowienieDataPublikacji = (TextView) findViewById(R.id.textViewZamowienieDataPublikacji);
                            zamowienieDataPublikacji.setText(objectClass.dataClass.data_publikacji.toString());

                            TextView zamowienieUE = (TextView) findViewById(R.id.textViewZamowienieUE);
                            if (tryParseInt(objectClass.dataClass.zamowienie_ue)) {
                                switch (Integer.parseInt(objectClass.dataClass.zamowienie_ue)) {
                                    case 0:
                                        zamowienieUE.setText("Nie");
                                        break;
                                    case 1:
                                        zamowienieUE.setText("Tak");
                                        break;
                                    default:
                                        zamowienieUE.setText(objectClass.dataClass.zamowienie_ue.toString());
                                }
                            } else {
                                zamowienieUE.setText(objectClass.dataClass.zamowienie_ue.toString());
                            }

                            DecimalFormat df = new DecimalFormat("#");
                            df.setMaximumFractionDigits(2);

                            TextView zamowienieSzacowanaWartosc = (TextView) findViewById(R.id.textViewZamowienieSzacowanaWartosc);
                            double zamowienieSzacowanaWartoscD = (objectClass.dataClass.wartosc_szacowana);
                            zamowienieSzacowanaWartosc.setText(df.format(zamowienieSzacowanaWartoscD) + " PLN");

                            TextView zamowienieCenaWybranejOferty = (TextView) findViewById(R.id.textViewZamowienieCenaWybranejOferty);
                            double zamowienieCenaWybranejOfertyD = (objectClass.dataClass.wartosc_cena);
                            zamowienieCenaWybranejOferty.setText(df.format(zamowienieCenaWybranejOfertyD) + " PLN");

                            TextView zamowienieNajtanszaOferta = (TextView) findViewById(R.id.textViewZamowienieNajtanszaOferta);
                            double zamowienieNajtanszaOfertaD = (Double.parseDouble(objectClass.dataClass.wartosc_cena_min));
                            zamowienieNajtanszaOferta.setText(df.format(zamowienieNajtanszaOfertaD) + " PLN");

                            TextView zamowienieNajdrozszaOferta = (TextView) findViewById(R.id.textViewZamowienieNajdrozszaOferta);
                            double zamowienieNajdrozszaOfertaD = (objectClass.dataClass.wartosc_cena_max);
                            zamowienieNajdrozszaOferta.setText(df.format(zamowienieNajdrozszaOfertaD) + " PLN");


                            try {
                                TextView zamowieniePrzedmiot = (TextView) findViewById(R.id.textViewZamowieniePrzedmiot);
                                if (objectClass.layers.detailsClass.przedmiot == "") {
                                    zamowieniePrzedmiot.setText("Dane nie zostały wprowadzone");
                                } else {
                                    zamowieniePrzedmiot.setText(objectClass.layers.detailsClass.przedmiot.toString());
                                }
                            } catch (NullPointerException e) {
                                TextView zamowieniePrzedmiot = (TextView) findViewById(R.id.textViewZamowieniePrzedmiot);
                                zamowieniePrzedmiot.setText("Dane nie zostały wprowadzone");
                            }

                            try {
                                TextView zamowienieUprawnienie = (TextView) findViewById(R.id.textViewZamowienieUprawnienie);
                                if (objectClass.layers.detailsClass.uprawnienie == "") {
                                    zamowienieUprawnienie.setText("Dane nie zostały wprowadzone");
                                } else {
                                    zamowienieUprawnienie.setText(objectClass.layers.detailsClass.uprawnienie.toString());
                                }
                            } catch (NullPointerException e) {
                                TextView zamowienieUprawnienie = (TextView) findViewById(R.id.textViewZamowienieUprawnienie);
                                zamowienieUprawnienie.setText("Dane nie zostały wprowadzone");
                            }

                            try {
                                TextView zamowienieWiedza = (TextView) findViewById(R.id.textViewZamowienieWiedza);
                                if (objectClass.layers.detailsClass.wiedza == "") {
                                    zamowienieWiedza.setText("Dane nie zostały wprowadzone");
                                } else {
                                    zamowienieWiedza.setText(objectClass.layers.detailsClass.wiedza.toString());
                                }
                            } catch (NullPointerException e) {
                                TextView zamowienieWiedza = (TextView) findViewById(R.id.textViewZamowienieWiedza);
                                zamowienieWiedza.setText("Dane nie zostały wprowadzone");
                            }

                            try {
                                TextView zamowieniePotencjal = (TextView) findViewById(R.id.textViewZamowieniePotencjal);
                                if (objectClass.layers.detailsClass.potencjal == "") {
                                    zamowieniePotencjal.setText("Dane nie zostały wprowadzone");
                                } else {
                                    zamowieniePotencjal.setText(objectClass.layers.detailsClass.potencjal.toString());
                                }
                            } catch (NullPointerException e) {
                                TextView zamowieniePotencjal = (TextView) findViewById(R.id.textViewZamowieniePotencjal);
                                zamowieniePotencjal.setText("Dane nie zostały wprowadzone");
                            }

                            try {
                                TextView zamowienieOsobyZdolne = (TextView) findViewById(R.id.textViewZamowienieOsobyZdolne);
                                if (objectClass.layers.detailsClass.osoby_zdolne == "") {
                                    zamowienieOsobyZdolne.setText("Dane nie zostały wprowadzone");
                                } else {
                                    zamowienieOsobyZdolne.setText(objectClass.layers.detailsClass.osoby_zdolne.toString());
                                }
                            } catch (NullPointerException e) {
                                TextView zamowienieOsobyZdolne = (TextView) findViewById(R.id.textViewZamowienieOsobyZdolne);
                                zamowienieOsobyZdolne.setText("Dane nie zostały wprowadzone");
                            }

                            try {
                                TextView zamowienieSytuacjaEkonomiczna = (TextView) findViewById(R.id.textViewZamowienieSytuacjaEkonomiczna);
                                if (objectClass.layers.detailsClass.sytuacja_ekonomiczna == "") {
                                    zamowienieSytuacjaEkonomiczna.setText("Dane nie zostały wprowadzone");
                                } else {
                                    zamowienieSytuacjaEkonomiczna.setText(objectClass.layers.detailsClass.sytuacja_ekonomiczna.toString());
                                }
                            } catch (NullPointerException e) {
                                TextView zamowienieSytuacjaEkonomiczna = (TextView) findViewById(R.id.textViewZamowienieSytuacjaEkonomiczna);
                                zamowienieSytuacjaEkonomiczna.setText("Dane nie zostały wprowadzone");
                            }
                            wheelRelativeLayout.setVisibility(View.GONE);
                            mainRelativeLayout.setVisibility(View.VISIBLE);
                        }else{
                            Toast.makeText(getApplicationContext(), "Błąd. Sprawdź połączenie z internetem", Toast.LENGTH_SHORT).show();
                            wheelRelativeLayout.setVisibility(View.GONE);                        }
                    }
                }
        );

        CardView cardViewZamawiajacyNazwa = (CardView) findViewById(R.id.card_viewZamawiajacyNazwa);
        cardViewZamawiajacyNazwa.setVisibility(View.GONE);

        CardView cardViewZamawiajacyID = (CardView) findViewById(R.id.card_viewZamawiajacyID);
        cardViewZamawiajacyID.setVisibility(View.GONE);

        CardView cardViewZamawiajacyRodzaj = (CardView) findViewById(R.id.card_viewZamawiajacyRodzaj);
        cardViewZamawiajacyRodzaj.setVisibility(View.GONE);

        CardView cardViewZamawiajacyRegon = (CardView) findViewById(R.id.card_viewZamawiajacyRegon);
        cardViewZamawiajacyRegon.setVisibility(View.GONE);


        CardView cardViewZamawiajacyWojewodztwo = (CardView) findViewById(R.id.card_viewZamawiajacyWojewodztwo);
        cardViewZamawiajacyWojewodztwo.setVisibility(View.GONE);

        CardView cardViewZamawiajacyWojewodztwoID = (CardView) findViewById(R.id.card_viewZamawiajacyWojewodztwoID);
        cardViewZamawiajacyWojewodztwoID.setVisibility(View.GONE);

        CardView cardViewZamawiajacyPowiatID = (CardView) findViewById(R.id.card_viewZamawiajacyPowiatID);
        cardViewZamawiajacyPowiatID.setVisibility(View.GONE);

        CardView cardViewZamawiajacyGminaID = (CardView) findViewById(R.id.card_viewZamawiajacyGminaID);
        cardViewZamawiajacyGminaID.setVisibility(View.GONE);

        CardView cardViewZamawiajacyMiejscowosc = (CardView) findViewById(R.id.card_viewZamawiajacyMiejscowosc);
        cardViewZamawiajacyMiejscowosc.setVisibility(View.GONE);

        CardView cardViewZamawiajacyKodPocztowy = (CardView) findViewById(R.id.card_viewZamawiajacyKodPocztowy);
        cardViewZamawiajacyKodPocztowy.setVisibility(View.GONE);

        CardView cardViewZamawiajacyKodPocztowyID = (CardView) findViewById(R.id.card_viewZamawiajacyKodPocztowyID);
        cardViewZamawiajacyKodPocztowyID.setVisibility(View.GONE);

        CardView cardViewZamawiajacyUlica = (CardView) findViewById(R.id.card_viewZamawiajacyUlica);
        cardViewZamawiajacyUlica.setVisibility(View.GONE);

        CardView cardViewZamawiajacyNrDomu = (CardView) findViewById(R.id.card_viewZamawiajacyNrDomu);
        cardViewZamawiajacyNrDomu.setVisibility(View.GONE);

        CardView cardViewZamawiajacyNrMieszkania = (CardView) findViewById(R.id.card_viewZamawiajacyNrMieszkania);
        cardViewZamawiajacyNrMieszkania.setVisibility(View.GONE);

        CardView cardViewZamawiajacyEmail = (CardView) findViewById(R.id.card_viewZamawiajacyEmail);
        cardViewZamawiajacyEmail.setVisibility(View.GONE);

        cardViewZamawiajacyTelefon = (CardView) findViewById(R.id.card_viewZamawiajacyTelefon);
        cardViewZamawiajacyTelefon.setVisibility(View.GONE);

        CardView cardViewZamawiajacyFax = (CardView) findViewById(R.id.card_viewZamawiajacyFax);
        cardViewZamawiajacyFax.setVisibility(View.GONE);

        CardView cardViewZamawiajacyWWW = (CardView) findViewById(R.id.card_viewZamawiajacyWWW);
        cardViewZamawiajacyWWW.setVisibility(View.GONE);


        CardView cardViewZamowienieNazwa = (CardView) findViewById(R.id.card_viewZamowienieNazwa);
        cardViewZamowienieNazwa.setVisibility(View.GONE);

        CardView cardViewZamowienieTyp = (CardView) findViewById(R.id.card_viewZamowienieTyp);
        cardViewZamowienieTyp.setVisibility(View.GONE);

        CardView cardViewZamowienieTypSymbol = (CardView) findViewById(R.id.card_viewZamowienieTypSymbol);
        cardViewZamowienieTypSymbol.setVisibility(View.GONE);

        CardView cardViewZamowienieRodzaj = (CardView) findViewById(R.id.card_viewZamowienieRodzaj);
        cardViewZamowienieRodzaj.setVisibility(View.GONE);

        CardView cardViewZamowienieDataPublikacji = (CardView) findViewById(R.id.card_viewZamowienieDataPublikacji);
        cardViewZamowienieDataPublikacji.setVisibility(View.GONE);

        CardView cardViewZamowienieUE = (CardView) findViewById(R.id.card_viewZamowienieUE);
        cardViewZamowienieUE.setVisibility(View.GONE);

        CardView cardViewZamowienieSzacowanaWartosc = (CardView) findViewById(R.id.card_viewZamowienieSzacowanaWartosc);
        cardViewZamowienieSzacowanaWartosc.setVisibility(View.GONE);

        CardView cardViewZamowienieCenaWybranejOferty = (CardView) findViewById(R.id.card_viewZamowienieCenaWybranejOferty);
        cardViewZamowienieCenaWybranejOferty.setVisibility(View.GONE);

        CardView cardViewZamowienieNajtanszaOferta = (CardView) findViewById(R.id.card_viewZamowienieNajtanszaOferta);
        cardViewZamowienieNajtanszaOferta.setVisibility(View.GONE);

        CardView cardViewZamowienieNajdrozszaOferta = (CardView) findViewById(R.id.card_viewZamowienieNajdrozszaOferta);
        cardViewZamowienieNajdrozszaOferta.setVisibility(View.GONE);


        TextView textViewZamowieniePrzedmiotLabel = (TextView) findViewById(R.id.textViewZamowieniePrzedmiotLabel);
        textViewZamowieniePrzedmiotLabel.setVisibility(View.GONE);
        TextView textViewZamowieniePrzedmiot = (TextView) findViewById(R.id.textViewZamowieniePrzedmiot);
        textViewZamowieniePrzedmiot.setVisibility(View.GONE);
        CardView cardViewZamowieniePrzedmiot = (CardView) findViewById(R.id.card_viewZamowieniePrzedmiot);
        cardViewZamowieniePrzedmiot.setVisibility(View.GONE);

        TextView textViewZamowienieUprawnienieLabel = (TextView) findViewById(R.id.textViewZamowienieUprawnienieLabel);
        textViewZamowienieUprawnienieLabel.setVisibility(View.GONE);
        TextView textViewZamowienieUprawnienie = (TextView) findViewById(R.id.textViewZamowienieUprawnienie);
        textViewZamowienieUprawnienie.setVisibility(View.GONE);
        CardView cardViewZamowienieUprawnienia = (CardView) findViewById(R.id.card_viewZamowienieUprawnienia);
        cardViewZamowienieUprawnienia.setVisibility(View.GONE);

        TextView textViewZamowienieWiedzaLabel = (TextView) findViewById(R.id.textViewZamowienieWiedzaLabel);
        textViewZamowienieWiedzaLabel.setVisibility(View.GONE);
        TextView textViewZamowienieWiedza = (TextView) findViewById(R.id.textViewZamowienieWiedza);
        textViewZamowienieWiedza.setVisibility(View.GONE);
        CardView cardViewZamowienieWiedza = (CardView) findViewById(R.id.card_viewZamowienieWiedza);
        cardViewZamowienieWiedza.setVisibility(View.GONE);

        TextView textViewZamowieniePotencjalLabel = (TextView) findViewById(R.id.textViewZamowieniePotencjalLabel);
        textViewZamowieniePotencjalLabel.setVisibility(View.GONE);
        TextView textViewZamowieniePotencjal = (TextView) findViewById(R.id.textViewZamowieniePotencjal);
        textViewZamowieniePotencjal.setVisibility(View.GONE);
        CardView cardViewZamowieniePotencjal = (CardView) findViewById(R.id.card_viewZamowieniePotencjal);
        cardViewZamowieniePotencjal.setVisibility(View.GONE);

        TextView textViewZamowienieOsobyZdolneLabel = (TextView) findViewById(R.id.textViewZamowienieOsobyZdolneLabel);
        textViewZamowienieOsobyZdolneLabel.setVisibility(View.GONE);
        TextView textViewZamowienieOsobyZdolne = (TextView) findViewById(R.id.textViewZamowienieOsobyZdolne);
        textViewZamowienieOsobyZdolne.setVisibility(View.GONE);
        CardView cardViewZamowienieOsobyZdolne = (CardView) findViewById(R.id.card_viewZamowienieOsobyZdolne);
        cardViewZamowienieOsobyZdolne.setVisibility(View.GONE);

        TextView textViewZamowienieSytuacjaEkonomicznaLabel = (TextView) findViewById(R.id.textViewZamowienieSytuacjaEkonomicznaLabel);
        textViewZamowienieSytuacjaEkonomicznaLabel.setVisibility(View.GONE);
        TextView textViewZamowienieSytuacjaEkonomiczna = (TextView) findViewById(R.id.textViewZamowienieSytuacjaEkonomiczna);
        textViewZamowienieSytuacjaEkonomiczna.setVisibility(View.GONE);
        CardView cardViewZamowienieSytuacjaEkonomiczna = (CardView) findViewById(R.id.card_viewZamowienieSytuacjaEkonomiczna);
        cardViewZamowienieSytuacjaEkonomiczna.setVisibility(View.GONE);

    }


    public void chowanieIdentyfikatory(View v) {
        CardView cardViewZamawiajacyNazwa = (CardView) findViewById(R.id.card_viewZamawiajacyNazwa);
        cardViewZamawiajacyNazwa.setVisibility(cardViewZamawiajacyNazwa.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamawiajacyID = (CardView) findViewById(R.id.card_viewZamawiajacyID);
        cardViewZamawiajacyID.setVisibility(cardViewZamawiajacyID.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamawiajacyRodzaj = (CardView) findViewById(R.id.card_viewZamawiajacyRodzaj);
        cardViewZamawiajacyRodzaj.setVisibility(cardViewZamawiajacyRodzaj.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamawiajacyRegon = (CardView) findViewById(R.id.card_viewZamawiajacyRegon);
        cardViewZamawiajacyRegon.setVisibility(cardViewZamawiajacyRegon.isShown() ? View.GONE : View.VISIBLE);
    }

    public void chowanieDaneAdresowe(View v) {
        CardView cardViewZamawiajacyWojewodztwo = (CardView) findViewById(R.id.card_viewZamawiajacyWojewodztwo);
        cardViewZamawiajacyWojewodztwo.setVisibility(cardViewZamawiajacyWojewodztwo.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamawiajacyWojewodztwoID = (CardView) findViewById(R.id.card_viewZamawiajacyWojewodztwoID);
        cardViewZamawiajacyWojewodztwoID.setVisibility(cardViewZamawiajacyWojewodztwoID.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamawiajacyPowiatID = (CardView) findViewById(R.id.card_viewZamawiajacyPowiatID);
        cardViewZamawiajacyPowiatID.setVisibility(cardViewZamawiajacyPowiatID.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamawiajacyGminaID = (CardView) findViewById(R.id.card_viewZamawiajacyGminaID);
        cardViewZamawiajacyGminaID.setVisibility(cardViewZamawiajacyGminaID.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamawiajacyMiejscowosc = (CardView) findViewById(R.id.card_viewZamawiajacyMiejscowosc);
        cardViewZamawiajacyMiejscowosc.setVisibility(cardViewZamawiajacyMiejscowosc.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamawiajacyKodPocztowy = (CardView) findViewById(R.id.card_viewZamawiajacyKodPocztowy);
        cardViewZamawiajacyKodPocztowy.setVisibility(cardViewZamawiajacyKodPocztowy.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamawiajacyKodPocztowyID = (CardView) findViewById(R.id.card_viewZamawiajacyKodPocztowyID);
        cardViewZamawiajacyKodPocztowyID.setVisibility(cardViewZamawiajacyKodPocztowyID.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamawiajacyUlica = (CardView) findViewById(R.id.card_viewZamawiajacyUlica);
        cardViewZamawiajacyUlica.setVisibility(cardViewZamawiajacyUlica.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamawiajacyNrDomu = (CardView) findViewById(R.id.card_viewZamawiajacyNrDomu);
        cardViewZamawiajacyNrDomu.setVisibility(cardViewZamawiajacyNrDomu.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamawiajacyNrMieszkania = (CardView) findViewById(R.id.card_viewZamawiajacyNrMieszkania);
        cardViewZamawiajacyNrMieszkania.setVisibility(cardViewZamawiajacyNrMieszkania.isShown() ? View.GONE : View.VISIBLE);
    }

    public void chowanieDaneKontaktowe(View v) {
        CardView cardViewZamawiajacyEmail = (CardView) findViewById(R.id.card_viewZamawiajacyEmail);
        cardViewZamawiajacyEmail.setVisibility(cardViewZamawiajacyEmail.isShown() ? View.GONE : View.VISIBLE);

        cardViewZamawiajacyTelefon = (CardView) findViewById(R.id.card_viewZamawiajacyTelefon);
        cardViewZamawiajacyTelefon.setVisibility(cardViewZamawiajacyTelefon.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamawiajacyFax = (CardView) findViewById(R.id.card_viewZamawiajacyFax);
        cardViewZamawiajacyFax.setVisibility(cardViewZamawiajacyFax.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamawiajacyWWW = (CardView) findViewById(R.id.card_viewZamawiajacyWWW);
        cardViewZamawiajacyWWW.setVisibility(cardViewZamawiajacyWWW.isShown() ? View.GONE : View.VISIBLE);
    }

    public void chowanieInformacjeOZamowieniu(View v) {

        CardView cardViewZamowienieNazwa = (CardView) findViewById(R.id.card_viewZamowienieNazwa);
        cardViewZamowienieNazwa.setVisibility(cardViewZamowienieNazwa.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamowienieTyp = (CardView) findViewById(R.id.card_viewZamowienieTyp);
        cardViewZamowienieTyp.setVisibility(cardViewZamowienieTyp.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamowienieTypSymbol = (CardView) findViewById(R.id.card_viewZamowienieTypSymbol);
        cardViewZamowienieTypSymbol.setVisibility(cardViewZamowienieTypSymbol.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamowienieRodzaj = (CardView) findViewById(R.id.card_viewZamowienieRodzaj);
        cardViewZamowienieRodzaj.setVisibility(cardViewZamowienieRodzaj.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamowienieDataPublikacji = (CardView) findViewById(R.id.card_viewZamowienieDataPublikacji);
        cardViewZamowienieDataPublikacji.setVisibility(cardViewZamowienieDataPublikacji.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamowienieUE = (CardView) findViewById(R.id.card_viewZamowienieUE);
        cardViewZamowienieUE.setVisibility(cardViewZamowienieUE.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamowienieSzacowanaWartosc = (CardView) findViewById(R.id.card_viewZamowienieSzacowanaWartosc);
        cardViewZamowienieSzacowanaWartosc.setVisibility(cardViewZamowienieSzacowanaWartosc.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamowienieCenaWybranejOferty = (CardView) findViewById(R.id.card_viewZamowienieCenaWybranejOferty);
        cardViewZamowienieCenaWybranejOferty.setVisibility(cardViewZamowienieCenaWybranejOferty.isShown() ? View.GONE : View.VISIBLE);

        CardView cardViewZamowienieNajtanszaOferta = (CardView) findViewById(R.id.card_viewZamowienieNajtanszaOferta);
        cardViewZamowienieNajtanszaOferta.setVisibility(cardViewZamowienieNajtanszaOferta.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamowienieNajdrozszaOferta = (CardView) findViewById(R.id.card_viewZamowienieNajdrozszaOferta);
        cardViewZamowienieNajdrozszaOferta.setVisibility(cardViewZamowienieNajdrozszaOferta.isShown() ? View.GONE : View.VISIBLE);
    }

    public void chowanieSzczegolowZamowienia(View v) {
        TextView textViewZamowieniePrzedmiotLabel = (TextView) findViewById(R.id.textViewZamowieniePrzedmiotLabel);
        textViewZamowieniePrzedmiotLabel.setVisibility(textViewZamowieniePrzedmiotLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamowieniePrzedmiot = (TextView) findViewById(R.id.textViewZamowieniePrzedmiot);
        textViewZamowieniePrzedmiot.setVisibility(textViewZamowieniePrzedmiot.isShown() ? View.GONE : View.GONE);
        CardView cardViewZamowieniePrzedmiot = (CardView) findViewById(R.id.card_viewZamowieniePrzedmiot);
        cardViewZamowieniePrzedmiot.setVisibility(cardViewZamowieniePrzedmiot.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamowienieUprawnienieLabel = (TextView) findViewById(R.id.textViewZamowienieUprawnienieLabel);
        textViewZamowienieUprawnienieLabel.setVisibility(textViewZamowienieUprawnienieLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamowienieUprawnienie = (TextView) findViewById(R.id.textViewZamowienieUprawnienie);
        textViewZamowienieUprawnienie.setVisibility(textViewZamowienieUprawnienie.isShown() ? View.GONE : View.GONE);
        CardView cardViewZamowienieUprawnienia = (CardView) findViewById(R.id.card_viewZamowienieUprawnienia);
        cardViewZamowienieUprawnienia.setVisibility(cardViewZamowienieUprawnienia.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamowienieWiedzaLabel = (TextView) findViewById(R.id.textViewZamowienieWiedzaLabel);
        textViewZamowienieWiedzaLabel.setVisibility(textViewZamowienieWiedzaLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamowienieWiedza = (TextView) findViewById(R.id.textViewZamowienieWiedza);
        textViewZamowienieWiedza.setVisibility(textViewZamowienieWiedza.isShown() ? View.GONE : View.GONE);
        CardView cardViewZamowienieWiedza = (CardView) findViewById(R.id.card_viewZamowienieWiedza);
        cardViewZamowienieWiedza.setVisibility(cardViewZamowienieWiedza.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamowieniePotencjalLabel = (TextView) findViewById(R.id.textViewZamowieniePotencjalLabel);
        textViewZamowieniePotencjalLabel.setVisibility(textViewZamowieniePotencjalLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamowieniePotencjal = (TextView) findViewById(R.id.textViewZamowieniePotencjal);
        textViewZamowieniePotencjal.setVisibility(textViewZamowieniePotencjal.isShown() ? View.GONE : View.GONE);
        CardView cardViewZamowieniePotencjal = (CardView) findViewById(R.id.card_viewZamowieniePotencjal);
        cardViewZamowieniePotencjal.setVisibility(cardViewZamowieniePotencjal.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamowienieOsobyZdolneLabel = (TextView) findViewById(R.id.textViewZamowienieOsobyZdolneLabel);
        textViewZamowienieOsobyZdolneLabel.setVisibility(textViewZamowienieOsobyZdolneLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamowienieOsobyZdolne = (TextView) findViewById(R.id.textViewZamowienieOsobyZdolne);
        textViewZamowienieOsobyZdolne.setVisibility(textViewZamowienieOsobyZdolne.isShown() ? View.GONE : View.GONE);
        CardView cardViewZamowienieOsobyZdolne = (CardView) findViewById(R.id.card_viewZamowienieOsobyZdolne);
        cardViewZamowienieOsobyZdolne.setVisibility(cardViewZamowienieOsobyZdolne.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamowienieSytuacjaEkonomicznaLabel = (TextView) findViewById(R.id.textViewZamowienieSytuacjaEkonomicznaLabel);
        textViewZamowienieSytuacjaEkonomicznaLabel.setVisibility(textViewZamowienieSytuacjaEkonomicznaLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamowienieSytuacjaEkonomiczna = (TextView) findViewById(R.id.textViewZamowienieSytuacjaEkonomiczna);
        textViewZamowienieSytuacjaEkonomiczna.setVisibility(textViewZamowienieSytuacjaEkonomiczna.isShown() ? View.GONE : View.GONE);
        CardView cardViewZamowienieSytuacjaEkonomiczna = (CardView) findViewById(R.id.card_viewZamowienieSytuacjaEkonomiczna);
        cardViewZamowienieSytuacjaEkonomiczna.setVisibility(cardViewZamowienieSytuacjaEkonomiczna.isShown() ? View.GONE : View.VISIBLE);
    }


    public void chowanieZamowieniePrzedmiot(View v) {
        TextView textViewZamowieniePrzedmiot = (TextView) findViewById(R.id.textViewZamowieniePrzedmiot);
        textViewZamowieniePrzedmiot.setVisibility(textViewZamowieniePrzedmiot.isShown() ? View.GONE : View.VISIBLE);
       }

    public void chowanieZamowienieUprawnienie(View v) {
        TextView textViewZamowienieUprawnienie = (TextView) findViewById(R.id.textViewZamowienieUprawnienie);
        textViewZamowienieUprawnienie.setVisibility(textViewZamowienieUprawnienie.isShown() ? View.GONE : View.VISIBLE);
        }

    public void chowanieZamowienieWiedza(View v) {
        TextView textViewZamowienieWiedza = (TextView) findViewById(R.id.textViewZamowienieWiedza);
        textViewZamowienieWiedza.setVisibility(textViewZamowienieWiedza.isShown() ? View.GONE : View.VISIBLE);
      }

    public void chowanieZamowieniePotencjal(View v) {
        TextView textViewZamowieniePotencjal = (TextView) findViewById(R.id.textViewZamowieniePotencjal);
        textViewZamowieniePotencjal.setVisibility(textViewZamowieniePotencjal.isShown() ? View.GONE : View.VISIBLE);
    }

    public void chowanieZamowienieOsobyZdolne(View v) {
        TextView textViewZamowienieOsobyZdolne = (TextView) findViewById(R.id.textViewZamowienieOsobyZdolne);
        textViewZamowienieOsobyZdolne.setVisibility(textViewZamowienieOsobyZdolne.isShown() ? View.GONE : View.VISIBLE);
     }

    public void chowanieZamowienieSytuacjaEkonomiczna(View v) {
        TextView textViewZamowienieSytuacjaEkonomiczna = (TextView) findViewById(R.id.textViewZamowienieSytuacjaEkonomiczna);
        textViewZamowienieSytuacjaEkonomiczna.setVisibility(textViewZamowienieSytuacjaEkonomiczna.isShown() ? View.GONE : View.VISIBLE);
       }

    public void zadzwon(View v) {
        try {
            String number = "tel:" + telefon;
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
            startActivity(callIntent);
        } catch (Exception e) {
            Log.d("zadzwon", e.getMessage());
        }
    }

    public void otworzStrone(View v) {
        try {
            String strona = "http://" + www;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strona));
            startActivity(browserIntent);
        } catch (Exception e) {
            Log.d("otworzStrone", e.getMessage());
        }
    }

    public void napiszEmail(View v) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            String email = mail;
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(Intent.createChooser(intent, ""));
        } catch (Exception e) {
            Log.d("napiszEmail", e.getMessage());
        }
    }


}


