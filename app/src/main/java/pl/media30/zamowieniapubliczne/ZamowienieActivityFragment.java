package pl.media30.zamowieniapubliczne;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import com.google.gson.Gson;

import pl.media30.zamowieniapubliczne.Models.DownloadList.DataObjectClass;
import pl.media30.zamowieniapubliczne.Models.SingleElement.BaseClass;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static java.lang.Integer.parseInt;

/**
 * A placeholder fragment containing a simple view.
 */
public class ZamowienieActivityFragment extends Activity
{

    TextView textViewZamawiajacyTelefon;
    TextView textViewZamawiajacyWWW;
    TextView textViewZamawiajacyEmail;
    Button button;
    boolean ulubione = false;
    int pozycjaUlub=-1;


    public boolean tryParseInt(String value)
    {
        try
        {
            Integer.parseInt(value);
            return true;
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
    }

    public ZamowienieActivityFragment(){}
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_zamowienie);
        String jsonMyObject = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            jsonMyObject = extras.getString("myObject");
        }
        DataObjectClass myObject = new Gson().fromJson(jsonMyObject, DataObjectClass.class);
        button = (Button) findViewById(R.id.button2);
        //dostep do layers
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://api.mojepanstwo.pl/dane/").build();
        MojePanstwoService service = restAdapter.create(MojePanstwoService.class);
        final ProgressDialog dialog = ProgressDialog.show(this, "Trwa wczytywanie danych", "Zaczekaj na wczytanie danych...");
        service.singleOrder(parseInt(myObject.id), new Callback<BaseClass>()
                {
                    @Override
                    public void success(final BaseClass baseClass, Response response)
                    {
                        for(int i =0;i<RepositoryClass.getInstance().getListaUlubionych().size();i++){
                            if(RepositoryClass.getInstance().getListaUlubionych().get(i).dataClass.id.equals(baseClass.objectClass.dataClass.id)){
                                button.setText("Jest w ulub.");
                                ulubione=true;
                                break;
                            }
                        }
                        if (ulubione==false)
                            button.setText("Dodaj do ulub.");

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean usunieto=false;
                                for(int i =0;i<RepositoryClass.getInstance().getListaUlubionych().size();i++) {
                                    if (RepositoryClass.getInstance().getListaUlubionych().get(i).dataClass.id.equals(baseClass.objectClass.dataClass.id)) {
                                        RepositoryClass.getInstance().removeListaUlubionych(i);
                                        usunieto = true;
                                        button.setText("Dodaj do ulub.");
                                        Log.d("ZAF", "usuwanie z ulub.");

                                        break;
                                    }
                                }
                                if (usunieto==false){
                                    RepositoryClass.getInstance().addListaUlubionych(baseClass.objectClass);
                                    Toast.makeText(getApplicationContext(), "Dodano do ulubionych", Toast.LENGTH_LONG);
                                    button.setText("Usun z ulub.");
                                    Log.d("ZAF", "Dodano do listy");

                                }

                            }
                        });

                        try
                        {
                            TextView zamowieniePrzedmiot = (TextView) findViewById(R.id.textViewZamowieniePrzedmiot);
                            if (baseClass.objectClass.layers.detailsClass.przedmiot == "")
                            {
                                zamowieniePrzedmiot.setText("Dane nie zostały wprowadzone");
                            }
                            else
                            {
                                zamowieniePrzedmiot.setText(baseClass.objectClass.layers.detailsClass.przedmiot.toString());
                            }
                        }
                        catch(NullPointerException e)
                        {
                            TextView zamowieniePrzedmiot = (TextView) findViewById(R.id.textViewZamowieniePrzedmiot);
                            zamowieniePrzedmiot.setText("Dane nie zostały wprowadzone");
                        }

                        try
                        {
                            TextView zamowienieUprawnienie = (TextView) findViewById(R.id.textViewZamowienieUprawnienie);
                            if (baseClass.objectClass.layers.detailsClass.uprawnienie == "")
                            {
                                zamowienieUprawnienie.setText("Dane nie zostały wprowadzone");
                            }
                            else
                            {
                                zamowienieUprawnienie.setText(baseClass.objectClass.layers.detailsClass.uprawnienie.toString());
                            }
                        }
                        catch(NullPointerException e)
                        {
                            TextView zamowienieUprawnienie = (TextView) findViewById(R.id.textViewZamowienieUprawnienie);
                            zamowienieUprawnienie.setText("Dane nie zostały wprowadzone");
                        }

                        try
                        {
                            TextView zamowienieWiedza = (TextView) findViewById(R.id.textViewZamowienieWiedza);
                            if (baseClass.objectClass.layers.detailsClass.wiedza == "")
                            {
                                zamowienieWiedza.setText("Dane nie zostały wprowadzone");
                            }
                            else
                            {
                                zamowienieWiedza.setText(baseClass.objectClass.layers.detailsClass.wiedza.toString());
                            }
                        }
                        catch(NullPointerException e)
                        {
                            TextView zamowienieWiedza = (TextView) findViewById(R.id.textViewZamowienieWiedza);
                            zamowienieWiedza.setText("Dane nie zostały wprowadzone");
                        }

                        try
                        {
                            TextView zamowieniePotencjal = (TextView) findViewById(R.id.textViewZamowieniePotencjal);
                            if (baseClass.objectClass.layers.detailsClass.potencjal == "")
                            {
                                zamowieniePotencjal.setText("Dane nie zostały wprowadzone");
                            }
                            else
                            {
                                zamowieniePotencjal.setText(baseClass.objectClass.layers.detailsClass.potencjal.toString());
                            }
                        }
                        catch(NullPointerException e)
                        {
                            TextView zamowieniePotencjal = (TextView) findViewById(R.id.textViewZamowieniePotencjal);
                            zamowieniePotencjal.setText("Dane nie zostały wprowadzone");
                        }

                        try
                        {
                            TextView zamowienieOsobyZdolne = (TextView) findViewById(R.id.textViewZamowienieOsobyZdolne);
                            if (baseClass.objectClass.layers.detailsClass.osoby_zdolne == "")
                            {
                                zamowienieOsobyZdolne.setText("Dane nie zostały wprowadzone");
                            }
                            else
                            {
                                zamowienieOsobyZdolne.setText(baseClass.objectClass.layers.detailsClass.osoby_zdolne.toString());
                            }
                        }
                        catch(NullPointerException e)
                        {
                            TextView zamowienieOsobyZdolne = (TextView) findViewById(R.id.textViewZamowienieOsobyZdolne);
                            zamowienieOsobyZdolne.setText("Dane nie zostały wprowadzone");
                        }

                        try
                        {
                            TextView zamowienieSytuacjaEkonomiczna = (TextView) findViewById(R.id.textViewZamowienieSytuacjaEkonomiczna);
                            if (baseClass.objectClass.layers.detailsClass.sytuacja_ekonomiczna == "")
                            {
                                zamowienieSytuacjaEkonomiczna.setText("Dane nie zostały wprowadzone");
                            }
                            else
                            {
                                zamowienieSytuacjaEkonomiczna.setText(baseClass.objectClass.layers.detailsClass.sytuacja_ekonomiczna.toString());
                            }
                        }
                        catch(NullPointerException e)
                        {
                            TextView zamowienieSytuacjaEkonomiczna = (TextView) findViewById(R.id.textViewZamowienieSytuacjaEkonomiczna);
                            zamowienieSytuacjaEkonomiczna.setText("Dane nie zostały wprowadzone");
                        }
/*
                        TextView zamowienieSzacowanaWartosc = (TextView) findViewById(R.id.textViewZamowienieSzacowanaWartosc);
                        zamowienieSzacowanaWartosc.setText
                                (baseClass.objectClass.layers.czesci.get(baseClass.objectClass.layers.czesci.size() - 1).wartosc
                                + " " +
                                baseClass.objectClass.layers.czesci.get(baseClass.objectClass.layers.czesci.size() - 1).waluta);

                        TextView zamowienieCenaWybranejOferty = (TextView) findViewById(R.id.textViewZamowienieCenaWybranejOferty);
                        zamowienieCenaWybranejOferty.setText
                                (baseClass.objectClass.layers.czesci.get(baseClass.objectClass.layers.czesci.size() - 1).cena
                                + " " +
                                baseClass.objectClass.layers.czesci.get(baseClass.objectClass.layers.czesci.size() - 1).waluta);

                        TextView zamowienieNajtanszaOferta = (TextView) findViewById(R.id.textViewZamowienieNajtanszaOferta);
                        zamowienieNajtanszaOferta.setText
                                (baseClass.objectClass.layers.czesci.get(baseClass.objectClass.layers.czesci.size() - 1).cena_min
                                + " " +
                                baseClass.objectClass.layers.czesci.get(baseClass.objectClass.layers.czesci.size() - 1).waluta);

                        TextView zamowienieNajdrozszaOferta = (TextView) findViewById(R.id.textViewZamowienieNajdrozszaOferta);
                        zamowienieNajdrozszaOferta.setText
                                (baseClass.objectClass.layers.czesci.get(baseClass.objectClass.layers.czesci.size() - 1).cena_max
                                + " " +
                                baseClass.objectClass.layers.czesci.get(baseClass.objectClass.layers.czesci.size() - 1).waluta);
*/
                        dialog.dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error)
                    {
                        Log.d("error: ", error.getMessage() + "");
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "BĹ‚Ä…d. SprawdĹş poĹ‚Ä…czenie z internetem", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        //koniec dostepu do layers

//REGION IDENTYFIKATORY ZAMAWIAJACEGO

        TextView zamawiajacyNazwa = (TextView) findViewById(R.id.textViewZamawiajacyNazwa);
        zamawiajacyNazwa.setText(myObject.dataClass.zamawiajacy_nazwa.toString());

        TextView zamawiajacyID = (TextView) findViewById(R.id.textViewZamawiajacyID);
        zamawiajacyID.setText(myObject.dataClass.zamawiajacy_id.toString());

        TextView zamawiajacyRodzaj = (TextView) findViewById(R.id.textViewZamawiajacyRodzaj);
        zamawiajacyRodzaj.setText(myObject.dataClass.zamawiajacy_rodzaj.toString());

        TextView zamawiajacyRegon = (TextView) findViewById(R.id.textViewZamawiajacyRegon);
        zamawiajacyRegon.setText(myObject.dataClass.zamawiajacy_regon.toString());

        //TextView textViewZamawiajacyNazwaLabel = (TextView) findViewById(R.id.textViewZamawiajacyNazwaLabel);
        //textViewZamawiajacyNazwaLabel.setVisibility(View.GONE);
        //TextView textViewZamawiajacyNazwa = (TextView) findViewById(R.id.textViewZamawiajacyNazwa);
        //textViewZamawiajacyNazwa.setVisibility(View.GONE);
        CardView cardViewZamawiajacyNazwa = (CardView) findViewById(R.id.card_viewZamawiajacyNazwa);
        cardViewZamawiajacyNazwa.setVisibility(View.GONE);

        //TextView textViewZamawiajacyIDLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDLabel);
        //textViewZamawiajacyIDLabel.setVisibility(View.GONE);
        //TextView textViewZamawiajacyID = (TextView) findViewById(R.id.textViewZamawiajacyID);
        //textViewZamawiajacyID.setVisibility(View.GONE);
        CardView cardViewZamawiajacyID = (CardView) findViewById(R.id.card_viewZamawiajacyID);
        cardViewZamawiajacyID.setVisibility(View.GONE);

        //TextView textViewZamawiajacyRodzajLabel = (TextView) findViewById(R.id.textViewZamawiajacyRodzajLabel);
        //textViewZamawiajacyRodzajLabel.setVisibility(View.GONE);
        //TextView textViewZamawiajacyRodzaj = (TextView) findViewById(R.id.textViewZamawiajacyRodzaj);
        //textViewZamawiajacyRodzaj.setVisibility(View.GONE);
        CardView cardViewZamawiajacyRodzaj = (CardView) findViewById(R.id.card_viewZamawiajacyRodzaj);
        cardViewZamawiajacyRodzaj.setVisibility(View.GONE);

        //TextView textViewZamawiajacyRegonLabel = (TextView) findViewById(R.id.textViewZamawiajacyRegonLabel);
        //textViewZamawiajacyRegonLabel.setVisibility(View.GONE);
        //TextView textViewZamawiajacyRegon = (TextView) findViewById(R.id.textViewZamawiajacyRegon);
        //textViewZamawiajacyRegon.setVisibility(View.GONE);
        CardView cardViewZamawiajacyRegon = (CardView) findViewById(R.id.card_viewZamawiajacyRegon);
        cardViewZamawiajacyRegon.setVisibility(View.GONE);

//ENDREGION
//REGION DANE ADRESOWE ZAMAWIAJACEGO

        TextView zamawiajacyWojewodztwo = (TextView) findViewById(R.id.textViewZamawiajacyWojewodztwo);
        zamawiajacyWojewodztwo.setText(myObject.dataClass.zamawiajacy_wojewodztwo.toString());

        TextView zamawiajacyIDWojewodztwa = (TextView) findViewById(R.id.textViewZamawiajacyIDWojewodztwa);
        zamawiajacyIDWojewodztwa.setText(myObject.dataClass.wojewodztwo_id.toString());

        TextView zamawiajacyIDPowiatu = (TextView) findViewById(R.id.textViewZamawiajacyIDPowiatu);
        zamawiajacyIDPowiatu.setText(myObject.dataClass.powiat_id.toString());

        TextView zamawiajacyIDGminy = (TextView) findViewById(R.id.textViewZamawiajacyIDGminy);
        zamawiajacyIDGminy.setText(myObject.dataClass.gmina_id.toString());

        TextView zamawiajacyMiejscowosc = (TextView) findViewById(R.id.textViewZamawiajacyMiejscowosc);
        zamawiajacyMiejscowosc.setText(myObject.dataClass.zamawiajacy_miejscowosc.toString());

        TextView zamawiajacyKodPocztowy = (TextView) findViewById(R.id.textViewZamawiajacyKodPocztowy);
        zamawiajacyKodPocztowy.setText(myObject.dataClass.zamawiajacy_kod_poczt.toString());

        TextView zamawiajacyIDKoduPocztowego = (TextView) findViewById(R.id.textViewZamawiajacyIDKoduPocztowego);
        zamawiajacyIDKoduPocztowego.setText(myObject.dataClass.zamawiajacyKod_pocztowy_id.toString());

        TextView zamawiajacyUlica = (TextView) findViewById(R.id.textViewZamawiajacyUlica);
        zamawiajacyUlica.setText(myObject.dataClass.zamawiajacy_ulica.toString());

        TextView zamawiajacyNrDomu = (TextView) findViewById(R.id.textViewZamawiajacyNrDomu);
        zamawiajacyNrDomu.setText(myObject.dataClass.zamawiajacy_nr_domu.toString());

        TextView zamawiajacyNrMIeszkania = (TextView) findViewById(R.id.textViewZamawiajacyNrMieszkania);
        zamawiajacyNrMIeszkania.setText(myObject.dataClass.zamawiajacy_nr_miesz.toString());

        //TextView textViewZamawiajacyWojewodztwoLabel = (TextView) findViewById(R.id.textViewZamawiajacyWojewodztwoLabel);
        //textViewZamawiajacyWojewodztwoLabel.setVisibility(View.GONE);
        //TextView textViewZamawiajacyWojewodztwo = (TextView) findViewById(R.id.textViewZamawiajacyWojewodztwo);
        //textViewZamawiajacyWojewodztwo.setVisibility(View.GONE);
        CardView cardViewZamawiajacyWojewodztwo = (CardView) findViewById(R.id.card_viewZamawiajacyWojewodztwo);
        cardViewZamawiajacyWojewodztwo.setVisibility(View.GONE);

        //TextView textViewZamawiajacyIDWojewodztwaLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDWojewodztwaLabel);
        //textViewZamawiajacyIDWojewodztwaLabel.setVisibility(View.GONE);
        //TextView textViewZamawiajacyIDWojewodztwa = (TextView) findViewById(R.id.textViewZamawiajacyIDWojewodztwa);
        //textViewZamawiajacyIDWojewodztwa.setVisibility(View.GONE);
        CardView cardViewZamawiajacyWojewodztwoID = (CardView) findViewById(R.id.card_viewZamawiajacyWojewodztwoID);
        cardViewZamawiajacyWojewodztwoID.setVisibility(View.GONE);

        //TextView textViewZamawiajacyIDPowiatuLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDPowiatuLabel);
        //textViewZamawiajacyIDPowiatuLabel.setVisibility(View.GONE);
        //TextView textViewZamawiajacyIDPowiatu = (TextView) findViewById(R.id.textViewZamawiajacyIDPowiatu);
        //textViewZamawiajacyIDPowiatu.setVisibility(View.GONE);
        CardView cardViewZamawiajacyPowiatID = (CardView) findViewById(R.id.card_viewZamawiajacyPowiatID);
        cardViewZamawiajacyPowiatID.setVisibility(View.GONE);

        //TextView textViewZamawiajacyIDGminyLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDGminyLabel);
        //textViewZamawiajacyIDGminyLabel.setVisibility(View.GONE);
        //TextView textViewZamawiajacyIDGminy = (TextView) findViewById(R.id.textViewZamawiajacyIDGminy);
        //textViewZamawiajacyIDGminy.setVisibility(View.GONE);
        CardView cardViewZamawiajacyGminaID = (CardView) findViewById(R.id.card_viewZamawiajacyGminaID);
        cardViewZamawiajacyGminaID.setVisibility(View.GONE);

        //TextView textViewZamawiajacyMiejscowoscLabel = (TextView) findViewById(R.id.textViewZamawiajacyMiejscowoscLabel);
        //textViewZamawiajacyMiejscowoscLabel.setVisibility(View.GONE);
        //TextView textViewZamawiajacyMiejscowosc = (TextView) findViewById(R.id.textViewZamawiajacyMiejscowosc);
        //textViewZamawiajacyMiejscowosc.setVisibility(View.GONE);
        CardView cardViewZamawiajacyMiejscowosc = (CardView) findViewById(R.id.card_viewZamawiajacyMiejscowosc);
        cardViewZamawiajacyMiejscowosc.setVisibility(View.GONE);

        //TextView textViewZamawiajacyKodPocztowyLabel = (TextView) findViewById(R.id.textViewZamawiajacyKodPocztowyLabel);
        //textViewZamawiajacyKodPocztowyLabel.setVisibility(View.GONE);
        //TextView textViewZamawiajacyKodPocztowy = (TextView) findViewById(R.id.textViewZamawiajacyKodPocztowy);
        //textViewZamawiajacyKodPocztowy.setVisibility(View.GONE);
        CardView cardViewZamawiajacyKodPocztowy = (CardView) findViewById(R.id.card_viewZamawiajacyKodPocztowy);
        cardViewZamawiajacyKodPocztowy.setVisibility(View.GONE);

        //TextView textViewZamawiajacyIDKoduPocztowegoLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDKoduPocztowegoLabel);
        //textViewZamawiajacyIDKoduPocztowegoLabel.setVisibility(View.GONE);
        //TextView textViewZamawiajacyIDKoduPocztowego = (TextView) findViewById(R.id.textViewZamawiajacyIDKoduPocztowego);
        //textViewZamawiajacyIDKoduPocztowego.setVisibility(View.GONE);
        CardView cardViewZamawiajacyKodPocztowyID = (CardView) findViewById(R.id.card_viewZamawiajacyKodPocztowyID);
        cardViewZamawiajacyKodPocztowyID.setVisibility(View.GONE);

        //TextView textViewZamawiajacyUlicaLabel = (TextView) findViewById(R.id.textViewZamawiajacyUlicaLabel);
        //textViewZamawiajacyUlicaLabel.setVisibility(View.GONE);
        //TextView textViewZamawiajacyUlica = (TextView) findViewById(R.id.textViewZamawiajacyUlica);
        //textViewZamawiajacyUlica.setVisibility(View.GONE);
        CardView cardViewZamawiajacyUlica = (CardView) findViewById(R.id.card_viewZamawiajacyUlica);
        cardViewZamawiajacyUlica.setVisibility(View.GONE);

        //TextView textViewZamawiajacyNrDomuLabel = (TextView) findViewById(R.id.textViewZamawiajacyNrDomuLabel);
        //textViewZamawiajacyNrDomuLabel.setVisibility(View.GONE);
        //TextView textViewZamawiajacyNrDomu = (TextView) findViewById(R.id.textViewZamawiajacyNrDomu);
        //textViewZamawiajacyNrDomu.setVisibility(View.GONE);
        CardView cardViewZamawiajacyNrDomu = (CardView) findViewById(R.id.card_viewZamawiajacyNrDomu);
        cardViewZamawiajacyNrDomu.setVisibility(View.GONE);

        //TextView textViewZamawiajacyNrMieszkaniaLabel = (TextView) findViewById(R.id.textViewZamawiajacyNrMieszkaniaLabel);
        //textViewZamawiajacyNrMieszkaniaLabel.setVisibility(View.GONE);
        //TextView textViewZamawiajacyNrMieszkania = (TextView) findViewById(R.id.textViewZamawiajacyNrMieszkania);
        //textViewZamawiajacyNrMieszkania.setVisibility(View.GONE);
        CardView cardViewZamawiajacyNrMieszkania = (CardView) findViewById(R.id.card_viewZamawiajacyNrMieszkania);
        cardViewZamawiajacyNrMieszkania.setVisibility(View.GONE);

//ENDREGION
//REGION DANE KONTAKTOWE ZAMAWIAJACEGO

        TextView zamawiajacyEmail = (TextView) findViewById(R.id.textViewZamawiajacyEmail);
        zamawiajacyEmail.setText(myObject.dataClass.zamawiajacy_email.toString());

        TextView zamawiajacyTelefon = (TextView) findViewById(R.id.textViewZamawiajacyTelefon);
        zamawiajacyTelefon.setText(myObject.dataClass.zamawiajacy_tel.toString());

        TextView zamawiajacyFax = (TextView) findViewById(R.id.textViewZamawiajacyFax);
        zamawiajacyFax.setText(myObject.dataClass.zamawiajacy_fax.toString());

        TextView zamawiajacyWWW = (TextView) findViewById(R.id.textViewZamawiajacyWWW);
        zamawiajacyWWW.setText(myObject.dataClass.zamawiajacy_www.toString());

        //TextView textViewZamawiajacyEmailLabel = (TextView) findViewById(R.id.textViewZamawiajacyEmailLabel);
        //textViewZamawiajacyEmailLabel.setVisibility(View.GONE);
        //textViewZamawiajacyEmail = (TextView) findViewById(R.id.textViewZamawiajacyEmail);
        //textViewZamawiajacyEmail.setVisibility(View.GONE);
        CardView cardViewZamawiajacyEmail = (CardView) findViewById(R.id.card_viewZamawiajacyEmail);
        cardViewZamawiajacyEmail.setVisibility(View.GONE);

        //TextView textViewZamawiajacyTelefonLabel = (TextView) findViewById(R.id.textViewZamawiajacyTelefonLabel);
        //textViewZamawiajacyTelefonLabel.setVisibility(View.GONE);
        //textViewZamawiajacyTelefon = (TextView) findViewById(R.id.textViewZamawiajacyTelefon);
        //textViewZamawiajacyTelefon.setVisibility(View.GONE);
        CardView cardViewZamawiajacyTelefon = (CardView) findViewById(R.id.card_viewZamawiajacyTelefon);
        cardViewZamawiajacyTelefon.setVisibility(View.GONE);

        //TextView textViewZamawiajacyFaxLabel = (TextView) findViewById(R.id.textViewZamawiajacyFaxLabel);
        //textViewZamawiajacyFaxLabel.setVisibility(View.GONE);
        //TextView textViewZamawiajacyFax = (TextView) findViewById(R.id.textViewZamawiajacyFax);
        //textViewZamawiajacyFax.setVisibility(View.GONE);
        CardView cardViewZamawiajacyFax = (CardView) findViewById(R.id.card_viewZamawiajacyFax);
        cardViewZamawiajacyFax.setVisibility(View.GONE);

        //TextView textViewZamawiajacyWWWLabel = (TextView) findViewById(R.id.textViewZamawiajacyWWWLabel);
        //textViewZamawiajacyWWWLabel.setVisibility(View.GONE);
        //textViewZamawiajacyWWW = (TextView) findViewById(R.id.textViewZamawiajacyWWW);
        //textViewZamawiajacyWWW.setVisibility(View.GONE);
        CardView cardViewZamawiajacyWWW = (CardView) findViewById(R.id.card_viewZamawiajacyWWW);
        cardViewZamawiajacyWWW.setVisibility(View.GONE);

//ENDREGION
//REGION INFORMACJE O ZAMOWIENIU

        TextView zamowienieNazwa = (TextView) findViewById(R.id.textViewZamowienieNazwa);
        zamowienieNazwa.setText(myObject.dataClass.nazwa.toString());

        TextView zamowienieTyp = (TextView) findViewById(R.id.textViewZamowienieTyp);
        zamowienieTyp.setText(myObject.dataClass.typyNazwa.toString());

        TextView zamowienieTypSymbol = (TextView) findViewById(R.id.textViewZamowienieTypSymbol);
        zamowienieTypSymbol.setText(myObject.dataClass.typySymbol.toString());

        TextView zamowienieRodzaj = (TextView) findViewById(R.id.textViewZamowienieRodzaj);
        zamowienieRodzaj.setText(myObject.dataClass.rodzajeNazwa.toString());

        TextView zamowienieDataPublikacji = (TextView) findViewById(R.id.textViewZamowienieDataPublikacji);
        zamowienieDataPublikacji.setText(myObject.dataClass.data_publikacji.toString());

        TextView zamowienieUE = (TextView) findViewById(R.id.textViewZamowienieUE);
        if(tryParseInt(myObject.dataClass.zamowienie_ue))
        {
            switch(Integer.parseInt(myObject.dataClass.zamowienie_ue))
            {
                case 0:
                    zamowienieUE.setText("Nie");
                    break;
                case 1:
                    zamowienieUE.setText("Tak");
                    break;
                default:
                    zamowienieUE.setText(myObject.dataClass.zamowienie_ue.toString());
            }
        }
        else
        {
            zamowienieUE.setText(myObject.dataClass.zamowienie_ue.toString());
        }

        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);

        TextView zamowienieSzacowanaWartosc = (TextView) findViewById(R.id.textViewZamowienieSzacowanaWartosc);
        double zamowienieSzacowanaWartoscD = (myObject.dataClass.wartosc_szacowana);
        zamowienieSzacowanaWartosc.setText(df.format(zamowienieSzacowanaWartoscD) + " PLN");

        TextView zamowienieCenaWybranejOferty = (TextView) findViewById(R.id.textViewZamowienieCenaWybranejOferty);
        double zamowienieCenaWybranejOfertyD = (myObject.dataClass.wartosc_cena);
        zamowienieCenaWybranejOferty.setText(df.format(zamowienieCenaWybranejOfertyD) + " PLN");

        TextView zamowienieNajtanszaOferta = (TextView) findViewById(R.id.textViewZamowienieNajtanszaOferta);
        double zamowienieNajtanszaOfertaD = (Double.parseDouble(myObject.dataClass.wartosc_cena_min));
        zamowienieNajtanszaOferta.setText(df.format(zamowienieNajtanszaOfertaD) + " PLN");

        TextView zamowienieNajdrozszaOferta = (TextView) findViewById(R.id.textViewZamowienieNajdrozszaOferta);
        double zamowienieNajdrozszaOfertaD = myObject.dataClass.wartosc_cena_max;
        zamowienieNajdrozszaOferta.setText(df.format(zamowienieNajdrozszaOfertaD) + " PLN");

        //TextView textViewZamowienieNazwaLabel = (TextView) findViewById(R.id.textViewZamowienieNazwaLabel);
        //textViewZamowienieNazwaLabel.setVisibility(View.GONE);
        //TextView textViewZamowienieNazwa = (TextView) findViewById(R.id.textViewZamowienieNazwa);
        //textViewZamowienieNazwa.setVisibility(View.GONE);
        CardView cardViewZamowienieNazwa = (CardView) findViewById(R.id.card_viewZamowienieNazwa);
        cardViewZamowienieNazwa.setVisibility(View.GONE);

        //TextView textViewZamowienieTypLabel = (TextView) findViewById(R.id.textViewZamowienieTypLabel);
        //textViewZamowienieTypLabel.setVisibility(View.GONE);
        //TextView textViewZamowienieTyp = (TextView) findViewById(R.id.textViewZamowienieTyp);
        //textViewZamowienieTyp.setVisibility(View.GONE);
        CardView cardViewZamowienieTyp = (CardView) findViewById(R.id.card_viewZamowienieTyp);
        cardViewZamowienieTyp.setVisibility(View.GONE);

        //TextView textViewZamowienieTypSymbolLabel = (TextView) findViewById(R.id.textViewZamowienieTypSymbolLabel);
        //textViewZamowienieTypSymbolLabel.setVisibility(View.GONE);
        //TextView textViewZamowienieTypSymbol = (TextView) findViewById(R.id.textViewZamowienieTypSymbol);
        //textViewZamowienieTypSymbol.setVisibility(View.GONE);
        CardView cardViewZamowienieTypSymbol = (CardView) findViewById(R.id.card_viewZamowienieTypSymbol);
        cardViewZamowienieTypSymbol.setVisibility(View.GONE);

        //TextView textViewZamowienieRodzajLabel = (TextView) findViewById(R.id.textViewZamowienieRodzajLabel);
        //textViewZamowienieRodzajLabel.setVisibility(View.GONE);
        //TextView textViewZamowienieRodzaj = (TextView) findViewById(R.id.textViewZamowienieRodzaj);
        //textViewZamowienieRodzaj.setVisibility(View.GONE);
        CardView cardViewZamowienieRodzaj = (CardView) findViewById(R.id.card_viewZamowienieRodzaj);
        cardViewZamowienieRodzaj.setVisibility(View.GONE);

        //TextView textViewZamowienieDataPublikacjiLabel = (TextView) findViewById(R.id.textViewZamowienieDataPublikacjiLabel);
        //textViewZamowienieDataPublikacjiLabel.setVisibility(View.GONE);
        //TextView textViewZamowienieDataPublikacji = (TextView) findViewById(R.id.textViewZamowienieDataPublikacji);
        //textViewZamowienieDataPublikacji.setVisibility(View.GONE);
        CardView cardViewZamowienieDataPublikacji = (CardView) findViewById(R.id.card_viewZamowienieDataPublikacji);
        cardViewZamowienieDataPublikacji.setVisibility(View.GONE);

        //TextView textViewZamowienieUELabel = (TextView) findViewById(R.id.textViewZamowienieUELabel);
        //textViewZamowienieUELabel.setVisibility(View.GONE);
        //TextView textViewZamowienieUE = (TextView) findViewById(R.id.textViewZamowienieUE);
        //textViewZamowienieUE.setVisibility(View.GONE);
        CardView cardViewZamowienieUE = (CardView) findViewById(R.id.card_viewZamowienieUE);
        cardViewZamowienieUE.setVisibility(View.GONE);
        
        //TextView textViewZamowienieSzacowanaWartoscLabel = (TextView) findViewById(R.id.textViewZamowienieSzacowanaWartoscLabel);
        //textViewZamowienieSzacowanaWartoscLabel.setVisibility(View.GONE);
        //TextView textViewZamowienieSzacowanaWartosc = (TextView) findViewById(R.id.textViewZamowienieSzacowanaWartosc);
        //textViewZamowienieSzacowanaWartosc.setVisibility(View.GONE);
        CardView cardViewZamowienieSzacowanaWartosc = (CardView) findViewById(R.id.card_viewZamowienieSzacowanaWartosc);
        cardViewZamowienieSzacowanaWartosc.setVisibility(View.GONE);

        //TextView textViewZamowienieCenaWybranejOfertyLabel = (TextView) findViewById(R.id.textViewZamowienieCenaWybranejOfertyLabel);
        //textViewZamowienieCenaWybranejOfertyLabel.setVisibility(View.GONE);
        //TextView textViewZamowienieCenaWybranejOferty = (TextView) findViewById(R.id.textViewZamowienieCenaWybranejOferty);
        //textViewZamowienieCenaWybranejOferty.setVisibility(View.GONE);
        CardView cardViewZamowienieCenaWybranejOferty = (CardView) findViewById(R.id.card_viewZamowienieCenaWybranejOferty);
        cardViewZamowienieCenaWybranejOferty.setVisibility(View.GONE);

        //TextView textViewZamowienieNajtanszaOfertaLabel = (TextView) findViewById(R.id.textViewZamowienieNajtanszaOfertaLabel);
        //textViewZamowienieNajtanszaOfertaLabel.setVisibility(View.GONE);
        //TextView textViewZamowienieNajtanszaOferta = (TextView) findViewById(R.id.textViewZamowienieNajtanszaOferta);
        //textViewZamowienieNajtanszaOferta.setVisibility(View.GONE);
        CardView cardViewZamowienieNajtanszaOferta = (CardView) findViewById(R.id.card_viewZamowienieNajtanszaOferta);
        cardViewZamowienieNajtanszaOferta.setVisibility(View.GONE);

        //TextView textViewZamowienieNajdrozszaOfertaLabel = (TextView) findViewById(R.id.textViewZamowienieNajdrozszaOfertaLabel);
        //textViewZamowienieNajdrozszaOfertaLabel.setVisibility(View.GONE);
        //TextView textViewZamowienieNajdrozszaOferta = (TextView) findViewById(R.id.textViewZamowienieNajdrozszaOferta);
        //textViewZamowienieNajdrozszaOferta.setVisibility(View.GONE);
        CardView cardViewZamowienieNajdrozszaOferta = (CardView) findViewById(R.id.card_viewZamowienieNajdrozszaOferta);
        cardViewZamowienieNajdrozszaOferta.setVisibility(View.GONE);

//ENDREGION
//REGION CHOWANIE SZCZEGOLOW ZAMOWIENIA

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
        
//ENDREGION
    }

//REGION CHOWANIE 1 STOPIEN
    
    public void chowanieIdentyfikatory(View v)
    {
        //TextView textViewZamawiajacyNazwaLabel = (TextView) findViewById(R.id.textViewZamawiajacyNazwaLabel);
        //textViewZamawiajacyNazwaLabel.setVisibility(textViewZamawiajacyNazwaLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyNazwa = (TextView) findViewById(R.id.textViewZamawiajacyNazwa);
        //textViewZamawiajacyNazwa.setVisibility(textViewZamawiajacyNazwa.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyNazwa = (CardView) findViewById(R.id.card_viewZamawiajacyNazwa);
        cardViewZamawiajacyNazwa.setVisibility(cardViewZamawiajacyNazwa.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamawiajacyIDLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDLabel);
        //textViewZamawiajacyIDLabel.setVisibility(textViewZamawiajacyIDLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyID = (TextView) findViewById(R.id.textViewZamawiajacyID);
        //textViewZamawiajacyID.setVisibility(textViewZamawiajacyID.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyID = (CardView) findViewById(R.id.card_viewZamawiajacyID);
        cardViewZamawiajacyID.setVisibility(cardViewZamawiajacyID.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamawiajacyRodzajLabel = (TextView) findViewById(R.id.textViewZamawiajacyRodzajLabel);
        //textViewZamawiajacyRodzajLabel.setVisibility(textViewZamawiajacyRodzajLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyRodzaj = (TextView) findViewById(R.id.textViewZamawiajacyRodzaj);
        //textViewZamawiajacyRodzaj.setVisibility(textViewZamawiajacyRodzaj.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyRodzaj = (CardView) findViewById(R.id.card_viewZamawiajacyRodzaj);
        cardViewZamawiajacyRodzaj.setVisibility(cardViewZamawiajacyRodzaj.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamawiajacyRegonLabel = (TextView) findViewById(R.id.textViewZamawiajacyRegonLabel);
        //textViewZamawiajacyRegonLabel.setVisibility(textViewZamawiajacyRegonLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyRegon = (TextView) findViewById(R.id.textViewZamawiajacyRegon);
        //textViewZamawiajacyRegon.setVisibility(textViewZamawiajacyRegon.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyRegon = (CardView) findViewById(R.id.card_viewZamawiajacyRegon);
        cardViewZamawiajacyRegon.setVisibility(cardViewZamawiajacyRegon.isShown() ? View.GONE : View.VISIBLE);
    }

    public void chowanieDaneAdresowe(View v)
    {
        //TextView textViewZamawiajacyWojewodztwoLabel = (TextView) findViewById(R.id.textViewZamawiajacyWojewodztwoLabel);
        //textViewZamawiajacyWojewodztwoLabel.setVisibility(textViewZamawiajacyWojewodztwoLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyWojewodztwo = (TextView) findViewById(R.id.textViewZamawiajacyWojewodztwo);
        //textViewZamawiajacyWojewodztwo.setVisibility(textViewZamawiajacyWojewodztwo.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyWojewodztwo = (CardView) findViewById(R.id.card_viewZamawiajacyWojewodztwo);
        cardViewZamawiajacyWojewodztwo.setVisibility(cardViewZamawiajacyWojewodztwo.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamawiajacyIDWojewodztwaLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDWojewodztwaLabel);
        //textViewZamawiajacyIDWojewodztwaLabel.setVisibility(textViewZamawiajacyIDWojewodztwaLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyIDWojewodztwa = (TextView) findViewById(R.id.textViewZamawiajacyIDWojewodztwa);
        //textViewZamawiajacyIDWojewodztwa.setVisibility(textViewZamawiajacyIDWojewodztwa.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyWojewodztwoID = (CardView) findViewById(R.id.card_viewZamawiajacyWojewodztwoID);
        cardViewZamawiajacyWojewodztwoID.setVisibility(cardViewZamawiajacyWojewodztwoID.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamawiajacyIDPowiatuLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDPowiatuLabel);
        //textViewZamawiajacyIDPowiatuLabel.setVisibility(textViewZamawiajacyIDPowiatuLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyIDPowiatu = (TextView) findViewById(R.id.textViewZamawiajacyIDPowiatu);
        //textViewZamawiajacyIDPowiatu.setVisibility(textViewZamawiajacyIDPowiatu.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyPowiatID = (CardView) findViewById(R.id.card_viewZamawiajacyPowiatID);
        cardViewZamawiajacyPowiatID.setVisibility(cardViewZamawiajacyPowiatID.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamawiajacyIDGminyLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDGminyLabel);
        //textViewZamawiajacyIDGminyLabel.setVisibility(textViewZamawiajacyIDGminyLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyIDGminy = (TextView) findViewById(R.id.textViewZamawiajacyIDGminy);
        //textViewZamawiajacyIDGminy.setVisibility(textViewZamawiajacyIDGminy.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyGminaID = (CardView) findViewById(R.id.card_viewZamawiajacyGminaID);
        cardViewZamawiajacyGminaID.setVisibility(cardViewZamawiajacyGminaID.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamawiajacyMiejscowoscLabel = (TextView) findViewById(R.id.textViewZamawiajacyMiejscowoscLabel);
        //textViewZamawiajacyMiejscowoscLabel.setVisibility(textViewZamawiajacyMiejscowoscLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyMiejscowosc = (TextView) findViewById(R.id.textViewZamawiajacyMiejscowosc);
        //textViewZamawiajacyMiejscowosc.setVisibility(textViewZamawiajacyMiejscowosc.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyMiejscowosc = (CardView) findViewById(R.id.card_viewZamawiajacyMiejscowosc);
        cardViewZamawiajacyMiejscowosc.setVisibility(cardViewZamawiajacyMiejscowosc.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamawiajacyKodPocztowyLabel = (TextView) findViewById(R.id.textViewZamawiajacyKodPocztowyLabel);
        //textViewZamawiajacyKodPocztowyLabel.setVisibility(textViewZamawiajacyKodPocztowyLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyKodPocztowy = (TextView) findViewById(R.id.textViewZamawiajacyKodPocztowy);
        //textViewZamawiajacyKodPocztowy.setVisibility(textViewZamawiajacyKodPocztowy.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyKodPocztowy = (CardView) findViewById(R.id.card_viewZamawiajacyKodPocztowy);
        cardViewZamawiajacyKodPocztowy.setVisibility(cardViewZamawiajacyKodPocztowy.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamawiajacyIDKoduPocztowegoLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDKoduPocztowegoLabel);
        //textViewZamawiajacyIDKoduPocztowegoLabel.setVisibility(textViewZamawiajacyIDKoduPocztowegoLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyIDKoduPocztowego = (TextView) findViewById(R.id.textViewZamawiajacyIDKoduPocztowego);
        //textViewZamawiajacyIDKoduPocztowego.setVisibility(textViewZamawiajacyIDKoduPocztowego.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyKodPocztowyID = (CardView) findViewById(R.id.card_viewZamawiajacyKodPocztowyID);
        cardViewZamawiajacyKodPocztowyID.setVisibility(cardViewZamawiajacyKodPocztowyID.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamawiajacyUlicaLabel = (TextView) findViewById(R.id.textViewZamawiajacyUlicaLabel);
        //textViewZamawiajacyUlicaLabel.setVisibility(textViewZamawiajacyUlicaLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyUlica = (TextView) findViewById(R.id.textViewZamawiajacyUlica);
        //textViewZamawiajacyUlica.setVisibility(textViewZamawiajacyUlica.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyUlica = (CardView) findViewById(R.id.card_viewZamawiajacyUlica);
        cardViewZamawiajacyUlica.setVisibility(cardViewZamawiajacyUlica.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamawiajacyNrDomuLabel = (TextView) findViewById(R.id.textViewZamawiajacyNrDomuLabel);
        //textViewZamawiajacyNrDomuLabel.setVisibility(textViewZamawiajacyNrDomuLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyNrDomu = (TextView) findViewById(R.id.textViewZamawiajacyNrDomu);
        //textViewZamawiajacyNrDomu.setVisibility(textViewZamawiajacyNrDomu.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyNrDomu = (CardView) findViewById(R.id.card_viewZamawiajacyNrDomu);
        cardViewZamawiajacyNrDomu.setVisibility(cardViewZamawiajacyNrDomu.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamawiajacyNrMieszkaniaLabel = (TextView) findViewById(R.id.textViewZamawiajacyNrMieszkaniaLabel);
        //textViewZamawiajacyNrMieszkaniaLabel.setVisibility(textViewZamawiajacyNrMieszkaniaLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyNrMieszkania = (TextView) findViewById(R.id.textViewZamawiajacyNrMieszkania);
        //textViewZamawiajacyNrMieszkania.setVisibility(textViewZamawiajacyNrMieszkania.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyNrMieszkania = (CardView) findViewById(R.id.card_viewZamawiajacyNrMieszkania);
        cardViewZamawiajacyNrMieszkania.setVisibility(cardViewZamawiajacyNrMieszkania.isShown() ? View.GONE : View.VISIBLE);
    }

    public void chowanieDaneKontaktowe(View v)
    {
        //TextView textViewZamawiajacyEmailLabel = (TextView) findViewById(R.id.textViewZamawiajacyEmailLabel);
        //textViewZamawiajacyEmailLabel.setVisibility(textViewZamawiajacyEmailLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyEmail = (TextView) findViewById(R.id.textViewZamawiajacyEmail);
        //textViewZamawiajacyEmail.setVisibility(textViewZamawiajacyEmail.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyEmail = (CardView) findViewById(R.id.card_viewZamawiajacyEmail);
        cardViewZamawiajacyEmail.setVisibility(cardViewZamawiajacyEmail.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamawiajacyTelefonLabel = (TextView) findViewById(R.id.textViewZamawiajacyTelefonLabel);
        //textViewZamawiajacyTelefonLabel.setVisibility(textViewZamawiajacyTelefonLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyTelefon = (TextView) findViewById(R.id.textViewZamawiajacyTelefon);
        //textViewZamawiajacyTelefon.setVisibility(textViewZamawiajacyTelefon.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyTelefon = (CardView) findViewById(R.id.card_viewZamawiajacyTelefon);
        cardViewZamawiajacyTelefon.setVisibility(cardViewZamawiajacyTelefon.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamawiajacyFaxLabel = (TextView) findViewById(R.id.textViewZamawiajacyFaxLabel);
        //textViewZamawiajacyFaxLabel.setVisibility(textViewZamawiajacyFaxLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyFax = (TextView) findViewById(R.id.textViewZamawiajacyFax);
        //textViewZamawiajacyFax.setVisibility(textViewZamawiajacyFax.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyFax = (CardView) findViewById(R.id.card_viewZamawiajacyFax);
        cardViewZamawiajacyFax.setVisibility(cardViewZamawiajacyFax.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamawiajacyWWWLabel = (TextView) findViewById(R.id.textViewZamawiajacyWWWLabel);
        //textViewZamawiajacyWWWLabel.setVisibility(textViewZamawiajacyWWWLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamawiajacyWWW = (TextView) findViewById(R.id.textViewZamawiajacyWWW);
        //textViewZamawiajacyWWW.setVisibility(textViewZamawiajacyWWW.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamawiajacyWWW = (CardView) findViewById(R.id.card_viewZamawiajacyWWW);
        cardViewZamawiajacyWWW.setVisibility(cardViewZamawiajacyWWW.isShown() ? View.GONE : View.VISIBLE);
    }

    public void chowanieInformacjeOZamowieniu(View v)
    {
        //TextView textViewZamowienieNazwaLabel = (TextView) findViewById(R.id.textViewZamowienieNazwaLabel);
        //textViewZamowienieNazwaLabel.setVisibility(textViewZamowienieNazwaLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamowienieNazwa = (TextView) findViewById(R.id.textViewZamowienieNazwa);
        //textViewZamowienieNazwa.setVisibility(textViewZamowienieNazwa.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamowienieNazwa = (CardView) findViewById(R.id.card_viewZamowienieNazwa);
        cardViewZamowienieNazwa.setVisibility(cardViewZamowienieNazwa.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamowienieTypLabel = (TextView) findViewById(R.id.textViewZamowienieTypLabel);
        //textViewZamowienieTypLabel.setVisibility(textViewZamowienieTypLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamowienieTyp = (TextView) findViewById(R.id.textViewZamowienieTyp);
        //textViewZamowienieTyp.setVisibility(textViewZamowienieTyp.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamowienieTyp = (CardView) findViewById(R.id.card_viewZamowienieTyp);
        cardViewZamowienieTyp.setVisibility(cardViewZamowienieTyp.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamowienieTypSymbolLabel = (TextView) findViewById(R.id.textViewZamowienieTypSymbolLabel);
        //textViewZamowienieTypSymbolLabel.setVisibility(textViewZamowienieTypSymbolLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamowienieTypSymbol = (TextView) findViewById(R.id.textViewZamowienieTypSymbol);
        //textViewZamowienieTypSymbol.setVisibility(textViewZamowienieTypSymbol.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamowienieTypSymbol = (CardView) findViewById(R.id.card_viewZamowienieTypSymbol);
        cardViewZamowienieTypSymbol.setVisibility(cardViewZamowienieTypSymbol.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamowienieRodzajLabel = (TextView) findViewById(R.id.textViewZamowienieRodzajLabel);
        //textViewZamowienieRodzajLabel.setVisibility(textViewZamowienieRodzajLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamowienieRodzaj = (TextView) findViewById(R.id.textViewZamowienieRodzaj);
        //textViewZamowienieRodzaj.setVisibility(textViewZamowienieRodzaj.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamowienieRodzaj = (CardView) findViewById(R.id.card_viewZamowienieRodzaj);
        cardViewZamowienieRodzaj.setVisibility(cardViewZamowienieRodzaj.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamowienieDataPublikacjiLabel = (TextView) findViewById(R.id.textViewZamowienieDataPublikacjiLabel);
        //textViewZamowienieDataPublikacjiLabel.setVisibility(textViewZamowienieDataPublikacjiLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamowienieDataPublikacji = (TextView) findViewById(R.id.textViewZamowienieDataPublikacji);
        //textViewZamowienieDataPublikacji.setVisibility(textViewZamowienieDataPublikacji.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamowienieDataPublikacji = (CardView) findViewById(R.id.card_viewZamowienieDataPublikacji);
        cardViewZamowienieDataPublikacji.setVisibility(cardViewZamowienieDataPublikacji.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamowienieUELabel = (TextView) findViewById(R.id.textViewZamowienieUELabel);
        //textViewZamowienieUELabel.setVisibility(textViewZamowienieUELabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamowienieUE = (TextView) findViewById(R.id.textViewZamowienieUE);
        //textViewZamowienieUE.setVisibility(textViewZamowienieUE.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamowienieUE = (CardView) findViewById(R.id.card_viewZamowienieUE);
        cardViewZamowienieUE.setVisibility(cardViewZamowienieUE.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamowienieSzacowanaWartoscLabel = (TextView) findViewById(R.id.textViewZamowienieSzacowanaWartoscLabel);
        //textViewZamowienieSzacowanaWartoscLabel.setVisibility(textViewZamowienieSzacowanaWartoscLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamowienieSzacowanaWartosc = (TextView) findViewById(R.id.textViewZamowienieSzacowanaWartosc);
        //textViewZamowienieSzacowanaWartosc.setVisibility(textViewZamowienieSzacowanaWartosc.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamowienieSzacowanaWartosc = (CardView) findViewById(R.id.card_viewZamowienieSzacowanaWartosc);
        cardViewZamowienieSzacowanaWartosc.setVisibility(cardViewZamowienieSzacowanaWartosc.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamowienieCenaWybranejOfertyLabel = (TextView) findViewById(R.id.textViewZamowienieCenaWybranejOfertyLabel);
        //textViewZamowienieCenaWybranejOfertyLabel.setVisibility(textViewZamowienieCenaWybranejOfertyLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamowienieCenaWybranejOferty = (TextView) findViewById(R.id.textViewZamowienieCenaWybranejOferty);
        //textViewZamowienieCenaWybranejOferty.setVisibility(textViewZamowienieCenaWybranejOferty.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamowienieCenaWybranejOferty = (CardView) findViewById(R.id.card_viewZamowienieCenaWybranejOferty);
        cardViewZamowienieCenaWybranejOferty.setVisibility(cardViewZamowienieCenaWybranejOferty.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamowienieNajtanszaOfertaLabel = (TextView) findViewById(R.id.textViewZamowienieNajtanszaOfertaLabel);
        //textViewZamowienieNajtanszaOfertaLabel.setVisibility(textViewZamowienieNajtanszaOfertaLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamowienieNajtanszaOferta = (TextView) findViewById(R.id.textViewZamowienieNajtanszaOferta);
        //textViewZamowienieNajtanszaOferta.setVisibility(textViewZamowienieNajtanszaOferta.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamowienieNajtanszaOferta = (CardView) findViewById(R.id.card_viewZamowienieNajtanszaOferta);
        cardViewZamowienieNajtanszaOferta.setVisibility(cardViewZamowienieNajtanszaOferta.isShown() ? View.GONE : View.VISIBLE);

        //TextView textViewZamowienieNajdrozszaOfertaLabel = (TextView) findViewById(R.id.textViewZamowienieNajdrozszaOfertaLabel);
        //textViewZamowienieNajdrozszaOfertaLabel.setVisibility(textViewZamowienieNajdrozszaOfertaLabel.isShown() ? View.GONE : View.VISIBLE);
        //TextView textViewZamowienieNajdrozszaOferta = (TextView) findViewById(R.id.textViewZamowienieNajdrozszaOferta);
        //textViewZamowienieNajdrozszaOferta.setVisibility(textViewZamowienieNajdrozszaOferta.isShown() ? View.GONE : View.VISIBLE);
        CardView cardViewZamowienieNajdrozszaOferta = (CardView) findViewById(R.id.card_viewZamowienieNajdrozszaOferta);
        cardViewZamowienieNajdrozszaOferta.setVisibility(cardViewZamowienieNajdrozszaOferta.isShown() ? View.GONE : View.VISIBLE);
    }
    
    public void chowanieSzczegolowZamowienia(View v)
    {
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

//ENDREGION
//REGION CHOWANIE 2 STOPNIEN

    public void chowanieZamowieniePrzedmiot(View v)
    {
        TextView textViewZamowieniePrzedmiot = (TextView) findViewById(R.id.textViewZamowieniePrzedmiot);
        textViewZamowieniePrzedmiot.setVisibility(textViewZamowieniePrzedmiot.isShown() ? View.GONE : View.VISIBLE);
        //CardView cardViewZamowieniePrzedmiot = (CardView) findViewById(R.id.card_viewZamowieniePrzedmiot);
        //cardViewZamowieniePrzedmiot.setVisibility(cardViewZamowieniePrzedmiot.isShown() ? View.GONE : View.VISIBLE);
    }

    public void chowanieZamowienieUprawnienie(View v)
    {
        TextView textViewZamowienieUprawnienie = (TextView) findViewById(R.id.textViewZamowienieUprawnienie);
        textViewZamowienieUprawnienie.setVisibility(textViewZamowienieUprawnienie.isShown() ? View.GONE : View.VISIBLE);
        //CardView cardViewZamowienieUprawnienia = (CardView) findViewById(R.id.card_viewZamowienieUprawnienia);
        //cardViewZamowienieUprawnienia.setVisibility(cardViewZamowienieUprawnienia.isShown() ? View.GONE : View.VISIBLE);
    }

    public void chowanieZamowienieWiedza(View v)
    {
        TextView textViewZamowienieWiedza = (TextView) findViewById(R.id.textViewZamowienieWiedza);
        textViewZamowienieWiedza.setVisibility(textViewZamowienieWiedza.isShown() ? View.GONE : View.VISIBLE);
        //CardView cardViewZamowienieWiedza = (CardView) findViewById(R.id.card_viewZamowienieWiedza);
        //cardViewZamowienieWiedza.setVisibility(cardViewZamowienieWiedza.isShown() ? View.GONE : View.VISIBLE);
    }

    public void chowanieZamowieniePotencjal(View v)
    {
        TextView textViewZamowieniePotencjal = (TextView) findViewById(R.id.textViewZamowieniePotencjal);
        textViewZamowieniePotencjal.setVisibility(textViewZamowieniePotencjal.isShown() ? View.GONE : View.VISIBLE);
        //CardView cardViewZamowieniePotencjal = (CardView) findViewById(R.id.card_viewZamowieniePotencjal);
        //cardViewZamowieniePotencjal.setVisibility(cardViewZamowieniePotencjal.isShown() ? View.GONE : View.VISIBLE);
    }

    public void chowanieZamowienieOsobyZdolne(View v)
    {
        TextView textViewZamowienieOsobyZdolne = (TextView) findViewById(R.id.textViewZamowienieOsobyZdolne);
        textViewZamowienieOsobyZdolne.setVisibility(textViewZamowienieOsobyZdolne.isShown() ? View.GONE : View.VISIBLE);
        //CardView cardViewZamowienieOsobyZdolne = (CardView) findViewById(R.id.card_viewZamowienieOsobyZdolne);
        //cardViewZamowienieOsobyZdolne.setVisibility(cardViewZamowienieOsobyZdolne.isShown() ? View.GONE : View.VISIBLE);
    }

    public void chowanieZamowienieSytuacjaEkonomiczna(View v)
    {
        TextView textViewZamowienieSytuacjaEkonomiczna = (TextView) findViewById(R.id.textViewZamowienieSytuacjaEkonomiczna);
        textViewZamowienieSytuacjaEkonomiczna.setVisibility(textViewZamowienieSytuacjaEkonomiczna.isShown() ? View.GONE : View.VISIBLE);
        //CardView cardViewZamowienieSytuacjaEkonomiczna = (CardView) findViewById(R.id.card_viewZamowienieSytuacjaEkonomiczna);
        //cardViewZamowienieSytuacjaEkonomiczna.setVisibility(cardViewZamowienieSytuacjaEkonomiczna.isShown() ? View.GONE : View.VISIBLE);
    }

//ENDREGION

    public void zadzwon(View v)
    {
        try
        {
            String number = "tel:" + textViewZamawiajacyTelefon.getText().toString().trim();
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
            startActivity(callIntent);
        }
        catch(Exception e)
        {
            Log.d("zadzwon", e.getMessage());
        }
    }

    public void otworzStrone(View v)
    {
        try
        {
            String www = "http://"+textViewZamawiajacyWWW.getText().toString().trim();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(www));
            startActivity(browserIntent);
        }
        catch(Exception e)
        {
            Log.d("otworzStrone", e.getMessage());
        }
    }

    public void napiszEmail(View v)
    {
        try
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            String email = textViewZamawiajacyEmail.getText().toString().trim();
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(Intent.createChooser(intent, ""));
        }
        catch(Exception e)
        {
            Log.d("napiszEmail", e.getMessage());
        }
    }


}


