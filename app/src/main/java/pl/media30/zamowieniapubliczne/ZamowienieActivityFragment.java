package pl.media30.zamowieniapubliczne;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
public class ZamowienieActivityFragment extends Activity {
    public ZamowienieActivityFragment() {
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
        //dostêp do layers
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.mojepanstwo.pl/dane/")
                .build();
        MojePanstwoService service = restAdapter.create(MojePanstwoService.class);
        service.singleOrder(parseInt(myObject.dataClass.zamawiajacy_id), new Callback<BaseClass>() {
                    @Override
                    public void success(BaseClass baseClass, Response response) {
                        Toast.makeText(getApplicationContext(), baseClass.objectClass.layers.detailsClass.przedmiot, Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("error: ", error.getMessage() + "");

                    }
                }
        );

        //koniec dostêpu do layers

        TextView zamawiajacyNazwa = (TextView) findViewById(R.id.textViewZamawiajacyNazwa);
        TextView zamawiajacyMiejscowosc = (TextView) findViewById(R.id.textViewZamawiajacyMiejscowosc);
        TextView zamawiajacyUlica = (TextView) findViewById(R.id.textViewZamawiajacyUlica);
        TextView zamawiajacyWojewodztwo = (TextView) findViewById(R.id.textViewZamawiajacyWojewodztwo);
        TextView zamawiajacyID = (TextView) findViewById(R.id.textViewZamawiajacyID);
        TextView zamawiajacyRegon = (TextView) findViewById(R.id.textViewZamawiajacyRegon);
        TextView zamawiajacyKodPocztowy = (TextView) findViewById(R.id.textViewZamawiajacyKodPocztowy);
        TextView zamawiajacyIDKoduPocztowego = (TextView) findViewById(R.id.textViewZamawiajacyIDKoduPocztowego);
        TextView zamawiajacyNrDomu = (TextView) findViewById(R.id.textViewZamawiajacyNrDomu);
        TextView zamawiajacyNrMIeszkania = (TextView) findViewById(R.id.textViewZamawiajacyNrMieszkania);
        TextView zamawiajacyEmail = (TextView) findViewById(R.id.textViewZamawiajacyEmail);
        TextView zamawiajacyTelefon = (TextView) findViewById(R.id.textViewZamawiajacyTelefon);
        TextView zamawiajacyFax = (TextView) findViewById(R.id.textViewZamawiajacyFax);
        TextView zamawiajacyWWW = (TextView) findViewById(R.id.textViewZamawiajacyWWW);
        TextView zamawiajacyIDPowiatu = (TextView) findViewById(R.id.textViewZamawiajacyIDPowiatu);
        TextView zamawiajacyIDGminy = (TextView) findViewById(R.id.textViewZamawiajacyIDGminy);
        TextView zamawiajacyIDWojewodztwa = (TextView) findViewById(R.id.textViewZamawiajacyIDWojewodztwa);
        TextView zamawiajacyRodzaj = (TextView) findViewById(R.id.textViewZamawiajacyRodzaj);

        //Bundle przekazanedane = getIntent().getExtras();
        //String przekazanytekst = przekazanedane.getString("dane");
        //nazwa.setText(przekazanytekst);
        //parsowanie obj




        zamawiajacyNazwa.setText(myObject.dataClass.zamawiajacy_nazwa.toString());
        zamawiajacyMiejscowosc.setText(myObject.dataClass.zamawiajacy_miejscowosc.toString());
        zamawiajacyUlica.setText(myObject.dataClass.zamawiajacy_ulica.toString());
        zamawiajacyWojewodztwo.setText(myObject.dataClass.zamawiajacy_wojewodztwo.toString());
        zamawiajacyID.setText(myObject.dataClass.zamawiajacy_id.toString());
        zamawiajacyRegon.setText(myObject.dataClass.zamawiajacy_regon.toString());
        zamawiajacyKodPocztowy.setText(myObject.dataClass.zamawiajacy_kod_poczt.toString());
        zamawiajacyIDKoduPocztowego.setText(myObject.dataClass.zamawiajacyKod_pocztowy_id.toString());
        zamawiajacyNrDomu.setText(myObject.dataClass.zamawiajacy_nr_domu.toString());
        zamawiajacyNrMIeszkania.setText(myObject.dataClass.zamawiajacy_nr_miesz.toString());
        zamawiajacyEmail.setText(myObject.dataClass.zamawiajacy_email.toString());
        zamawiajacyTelefon.setText(myObject.dataClass.zamawiajacy_tel.toString());
        zamawiajacyFax.setText(myObject.dataClass.zamawiajacy_fax.toString());
        zamawiajacyWWW.setText(myObject.dataClass.zamawiajacy_www.toString());
        zamawiajacyIDPowiatu.setText(myObject.dataClass.powiat_id.toString());
        zamawiajacyIDGminy.setText(myObject.dataClass.gmina_id.toString());
        zamawiajacyIDWojewodztwa.setText(myObject.dataClass.wojewodztwo_id.toString());
        zamawiajacyRodzaj.setText(myObject.dataClass.zamawiajacy_rodzaj.toString());

//REGION CHOWANIE IDENTYFIKATOROW

        TextView textViewZamawiajacyNazwaLabel = (TextView) findViewById(R.id.textViewZamawiajacyNazwaLabel);
        textViewZamawiajacyNazwaLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyNazwa = (TextView) findViewById(R.id.textViewZamawiajacyNazwa);
        textViewZamawiajacyNazwa.setVisibility(View.GONE);

        TextView textViewZamawiajacyIDLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDLabel);
        textViewZamawiajacyIDLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyID = (TextView) findViewById(R.id.textViewZamawiajacyID);
        textViewZamawiajacyID.setVisibility(View.GONE);

        TextView textViewZamawiajacyRodzajLabel = (TextView) findViewById(R.id.textViewZamawiajacyRodzajLabel);
        textViewZamawiajacyRodzajLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyRodzaj = (TextView) findViewById(R.id.textViewZamawiajacyRodzaj);
        textViewZamawiajacyRodzaj.setVisibility(View.GONE);

        TextView textViewZamawiajacyRegonLabel = (TextView) findViewById(R.id.textViewZamawiajacyRegonLabel);
        textViewZamawiajacyRegonLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyRegon = (TextView) findViewById(R.id.textViewZamawiajacyRegon);
        textViewZamawiajacyRegon.setVisibility(View.GONE);

//ENDREGION
//REGION CHOWANIE DANYCH ADRESOWYCH

        TextView textViewZamawiajacyWojewodztwoLabel = (TextView) findViewById(R.id.textViewZamawiajacyWojewodztwoLabel);
        textViewZamawiajacyWojewodztwoLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyWojewodztwo = (TextView) findViewById(R.id.textViewZamawiajacyWojewodztwo);
        textViewZamawiajacyWojewodztwo.setVisibility(View.GONE);

        TextView textViewZamawiajacyIDWojewodztwaLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDWojewodztwaLabel);
        textViewZamawiajacyIDWojewodztwaLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyIDWojewodztwa = (TextView) findViewById(R.id.textViewZamawiajacyIDWojewodztwa);
        textViewZamawiajacyIDWojewodztwa.setVisibility(View.GONE);

        TextView textViewZamawiajacyIDPowiatuLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDPowiatuLabel);
        textViewZamawiajacyIDPowiatuLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyIDPowiatu = (TextView) findViewById(R.id.textViewZamawiajacyIDPowiatu);
        textViewZamawiajacyIDPowiatu.setVisibility(View.GONE);

        TextView textViewZamawiajacyIDGminyLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDGminyLabel);
        textViewZamawiajacyIDGminyLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyIDGminy = (TextView) findViewById(R.id.textViewZamawiajacyIDGminy);
        textViewZamawiajacyIDGminy.setVisibility(View.GONE);

        TextView textViewZamawiajacyMiejscowoscLabel = (TextView) findViewById(R.id.textViewZamawiajacyMiejscowoscLabel);
        textViewZamawiajacyMiejscowoscLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyMiejscowosc = (TextView) findViewById(R.id.textViewZamawiajacyMiejscowosc);
        textViewZamawiajacyMiejscowosc.setVisibility(View.GONE);

        TextView textViewZamawiajacyKodPocztowyLabel = (TextView) findViewById(R.id.textViewZamawiajacyKodPocztowyLabel);
        textViewZamawiajacyKodPocztowyLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyKodPocztowy = (TextView) findViewById(R.id.textViewZamawiajacyKodPocztowy);
        textViewZamawiajacyKodPocztowy.setVisibility(View.GONE);

        TextView textViewZamawiajacyIDKoduPocztowegoLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDKoduPocztowegoLabel);
        textViewZamawiajacyIDKoduPocztowegoLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyIDKoduPocztowego = (TextView) findViewById(R.id.textViewZamawiajacyIDKoduPocztowego);
        textViewZamawiajacyIDKoduPocztowego.setVisibility(View.GONE);

        TextView textViewZamawiajacyUlicaLabel = (TextView) findViewById(R.id.textViewZamawiajacyUlicaLabel);
        textViewZamawiajacyUlicaLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyUlica = (TextView) findViewById(R.id.textViewZamawiajacyUlica);
        textViewZamawiajacyUlica.setVisibility(View.GONE);

        TextView textViewZamawiajacyNrDomuLabel = (TextView) findViewById(R.id.textViewZamawiajacyNrDomuLabel);
        textViewZamawiajacyNrDomuLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyNrDomu = (TextView) findViewById(R.id.textViewZamawiajacyNrDomu);
        textViewZamawiajacyNrDomu.setVisibility(View.GONE);

        TextView textViewZamawiajacyNrMieszkaniaLabel = (TextView) findViewById(R.id.textViewZamawiajacyNrMieszkaniaLabel);
        textViewZamawiajacyNrMieszkaniaLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyNrMieszkania = (TextView) findViewById(R.id.textViewZamawiajacyNrMieszkania);
        textViewZamawiajacyNrMieszkania.setVisibility(View.GONE);

//ENDREGION
//REGION CHOWANIE DANYCH ADRESOWYCH

        TextView textViewZamawiajacyEmailLabel = (TextView) findViewById(R.id.textViewZamawiajacyEmailLabel);
        textViewZamawiajacyEmailLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyEmail = (TextView) findViewById(R.id.textViewZamawiajacyEmail);
        textViewZamawiajacyEmail.setVisibility(View.GONE);

        TextView textViewZamawiajacyTelefonLabel = (TextView) findViewById(R.id.textViewZamawiajacyTelefonLabel);
        textViewZamawiajacyTelefonLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyTelefon = (TextView) findViewById(R.id.textViewZamawiajacyTelefon);
        textViewZamawiajacyTelefon.setVisibility(View.GONE);

        TextView textViewZamawiajacyFaxLabel = (TextView) findViewById(R.id.textViewZamawiajacyFaxLabel);
        textViewZamawiajacyFaxLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyFax = (TextView) findViewById(R.id.textViewZamawiajacyFax);
        textViewZamawiajacyFax.setVisibility(View.GONE);

        TextView textViewZamawiajacyWWWLabel = (TextView) findViewById(R.id.textViewZamawiajacyWWWLabel);
        textViewZamawiajacyWWWLabel.setVisibility(View.GONE);
        TextView textViewZamawiajacyWWW = (TextView) findViewById(R.id.textViewZamawiajacyWWW);
        textViewZamawiajacyWWW.setVisibility(View.GONE);

//ENDREGION
    }

    public void chowanieIdentyfikatory(View v) {
        TextView textViewZamawiajacyNazwaLabel = (TextView) findViewById(R.id.textViewZamawiajacyNazwaLabel);
        textViewZamawiajacyNazwaLabel.setVisibility(textViewZamawiajacyNazwaLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyNazwa = (TextView) findViewById(R.id.textViewZamawiajacyNazwa);
        textViewZamawiajacyNazwa.setVisibility(textViewZamawiajacyNazwa.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamawiajacyIDLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDLabel);
        textViewZamawiajacyIDLabel.setVisibility(textViewZamawiajacyIDLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyID = (TextView) findViewById(R.id.textViewZamawiajacyID);
        textViewZamawiajacyID.setVisibility(textViewZamawiajacyID.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamawiajacyRodzajLabel = (TextView) findViewById(R.id.textViewZamawiajacyRodzajLabel);
        textViewZamawiajacyRodzajLabel.setVisibility(textViewZamawiajacyRodzajLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyRodzaj = (TextView) findViewById(R.id.textViewZamawiajacyRodzaj);
        textViewZamawiajacyRodzaj.setVisibility(textViewZamawiajacyRodzaj.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamawiajacyRegonLabel = (TextView) findViewById(R.id.textViewZamawiajacyRegonLabel);
        textViewZamawiajacyRegonLabel.setVisibility(textViewZamawiajacyRegonLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyRegon = (TextView) findViewById(R.id.textViewZamawiajacyRegon);
        textViewZamawiajacyRegon.setVisibility(textViewZamawiajacyRegon.isShown() ? View.GONE : View.VISIBLE);
    }

    public void chowanieDaneAdresowe(View v) {
        TextView textViewZamawiajacyWojewodztwoLabel = (TextView) findViewById(R.id.textViewZamawiajacyWojewodztwoLabel);
        textViewZamawiajacyWojewodztwoLabel.setVisibility(textViewZamawiajacyWojewodztwoLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyWojewodztwo = (TextView) findViewById(R.id.textViewZamawiajacyWojewodztwo);
        textViewZamawiajacyWojewodztwo.setVisibility(textViewZamawiajacyWojewodztwo.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamawiajacyIDWojewodztwaLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDWojewodztwaLabel);
        textViewZamawiajacyIDWojewodztwaLabel.setVisibility(textViewZamawiajacyIDWojewodztwaLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyIDWojewodztwa = (TextView) findViewById(R.id.textViewZamawiajacyIDWojewodztwa);
        textViewZamawiajacyIDWojewodztwa.setVisibility(textViewZamawiajacyIDWojewodztwa.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamawiajacyIDPowiatuLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDPowiatuLabel);
        textViewZamawiajacyIDPowiatuLabel.setVisibility(textViewZamawiajacyIDPowiatuLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyIDPowiatu = (TextView) findViewById(R.id.textViewZamawiajacyIDPowiatu);
        textViewZamawiajacyIDPowiatu.setVisibility(textViewZamawiajacyIDPowiatu.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamawiajacyIDGminyLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDGminyLabel);
        textViewZamawiajacyIDGminyLabel.setVisibility(textViewZamawiajacyIDGminyLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyIDGminy = (TextView) findViewById(R.id.textViewZamawiajacyIDGminy);
        textViewZamawiajacyIDGminy.setVisibility(textViewZamawiajacyIDGminy.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamawiajacyMiejscowoscLabel = (TextView) findViewById(R.id.textViewZamawiajacyMiejscowoscLabel);
        textViewZamawiajacyMiejscowoscLabel.setVisibility(textViewZamawiajacyMiejscowoscLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyMiejscowosc = (TextView) findViewById(R.id.textViewZamawiajacyMiejscowosc);
        textViewZamawiajacyMiejscowosc.setVisibility(textViewZamawiajacyMiejscowosc.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamawiajacyKodPocztowyLabel = (TextView) findViewById(R.id.textViewZamawiajacyKodPocztowyLabel);
        textViewZamawiajacyKodPocztowyLabel.setVisibility(textViewZamawiajacyKodPocztowyLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyKodPocztowy = (TextView) findViewById(R.id.textViewZamawiajacyKodPocztowy);
        textViewZamawiajacyKodPocztowy.setVisibility(textViewZamawiajacyKodPocztowy.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamawiajacyIDKoduPocztowegoLabel = (TextView) findViewById(R.id.textViewZamawiajacyIDKoduPocztowegoLabel);
        textViewZamawiajacyIDKoduPocztowegoLabel.setVisibility(textViewZamawiajacyIDKoduPocztowegoLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyIDKoduPocztowego = (TextView) findViewById(R.id.textViewZamawiajacyIDKoduPocztowego);
        textViewZamawiajacyIDKoduPocztowego.setVisibility(textViewZamawiajacyIDKoduPocztowego.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamawiajacyUlicaLabel = (TextView) findViewById(R.id.textViewZamawiajacyUlicaLabel);
        textViewZamawiajacyUlicaLabel.setVisibility(textViewZamawiajacyUlicaLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyUlica = (TextView) findViewById(R.id.textViewZamawiajacyUlica);
        textViewZamawiajacyUlica.setVisibility(textViewZamawiajacyUlica.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamawiajacyNrDomuLabel = (TextView) findViewById(R.id.textViewZamawiajacyNrDomuLabel);
        textViewZamawiajacyNrDomuLabel.setVisibility(textViewZamawiajacyNrDomuLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyNrDomu = (TextView) findViewById(R.id.textViewZamawiajacyNrDomu);
        textViewZamawiajacyNrDomu.setVisibility(textViewZamawiajacyNrDomu.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamawiajacyNrMieszkaniaLabel = (TextView) findViewById(R.id.textViewZamawiajacyNrMieszkaniaLabel);
        textViewZamawiajacyNrMieszkaniaLabel.setVisibility(textViewZamawiajacyNrMieszkaniaLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyNrMieszkania = (TextView) findViewById(R.id.textViewZamawiajacyNrMieszkania);
        textViewZamawiajacyNrMieszkania.setVisibility(textViewZamawiajacyNrMieszkania.isShown() ? View.GONE : View.VISIBLE);
    }

    public void chowanieDaneKontaktowe(View v) {
        TextView textViewZamawiajacyEmailLabel = (TextView) findViewById(R.id.textViewZamawiajacyEmailLabel);
        textViewZamawiajacyEmailLabel.setVisibility(textViewZamawiajacyEmailLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyEmail = (TextView) findViewById(R.id.textViewZamawiajacyEmail);
        textViewZamawiajacyEmail.setVisibility(textViewZamawiajacyEmail.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamawiajacyTelefonLabel = (TextView) findViewById(R.id.textViewZamawiajacyTelefonLabel);
        textViewZamawiajacyTelefonLabel.setVisibility(textViewZamawiajacyTelefonLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyTelefon = (TextView) findViewById(R.id.textViewZamawiajacyTelefon);
        textViewZamawiajacyTelefon.setVisibility(textViewZamawiajacyTelefon.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamawiajacyFaxLabel = (TextView) findViewById(R.id.textViewZamawiajacyFaxLabel);
        textViewZamawiajacyFaxLabel.setVisibility(textViewZamawiajacyFaxLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyFax = (TextView) findViewById(R.id.textViewZamawiajacyFax);
        textViewZamawiajacyFax.setVisibility(textViewZamawiajacyFax.isShown() ? View.GONE : View.VISIBLE);

        TextView textViewZamawiajacyWWWLabel = (TextView) findViewById(R.id.textViewZamawiajacyWWWLabel);
        textViewZamawiajacyWWWLabel.setVisibility(textViewZamawiajacyWWWLabel.isShown() ? View.GONE : View.VISIBLE);
        TextView textViewZamawiajacyWWW = (TextView) findViewById(R.id.textViewZamawiajacyWWW);
        textViewZamawiajacyWWW.setVisibility(textViewZamawiajacyWWW.isShown() ? View.GONE : View.VISIBLE);
    }
}


