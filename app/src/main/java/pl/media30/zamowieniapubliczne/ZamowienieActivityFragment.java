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

            TextView nazwa = (TextView) findViewById(R.id.textViewNazwa);
            TextView zamawiajacyNazwa = (TextView) findViewById(R.id.textViewZamawiajacyNazwa);
            TextView zamawiajacyMiejscowosc = (TextView) findViewById(R.id.textViewZamawiajacyMiejscowosc);
            TextView zamawiajacyUlica = (TextView) findViewById(R.id.textViewZamawiajacyUlica);

            Bundle przekazanedane = getIntent().getExtras();
            String przekazanytekst = przekazanedane.getString("dane");

            nazwa.setText(przekazanytekst);

//parsowanie obj

            String jsonMyObject="";
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                jsonMyObject = extras.getString("myObject");
            }
            DataObjectClass myObject = new Gson().fromJson(jsonMyObject, DataObjectClass.class);

            nazwa.setText(myObject.dataClass.nazwa.toString());
            zamawiajacyNazwa.setText(myObject.dataClass.zamawiajacy_nazwa.toString());
            zamawiajacyMiejscowosc.setText(myObject.dataClass.zamawiajacy_miejscowosc.toString());
            zamawiajacyUlica.setText(myObject.dataClass.zamawiajacy_ulica.toString());
        }

    }


