package pl.media30.zamowieniapubliczne;

import android.app.Activity;
import android.app.RemoteInput;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import pl.media30.zamowieniapubliczne.Models.DownloadList.DataObjectClass;
import pl.media30.zamowieniapubliczne.Models.SingleElement.BaseClass;
import pl.media30.zamowieniapubliczne.Models.SingleElement.DataClass;
import pl.media30.zamowieniapubliczne.Models.SingleElement.ObjectClass;


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

            //TextView nazwa = (TextView) findViewById(R.id.textViewNazwa);
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

            //Bundle przekazanedane = getIntent().getExtras();
            //String przekazanytekst = przekazanedane.getString("dane");

            //nazwa.setText(przekazanytekst);

//parsowanie obj

            String jsonMyObject="";
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                jsonMyObject = extras.getString("myObject");
            }
            DataObjectClass myObject = new Gson().fromJson(jsonMyObject, DataObjectClass.class);

            //nazwa.setText(myObject.dataClass.nazwa.toString());
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
        }

    }


