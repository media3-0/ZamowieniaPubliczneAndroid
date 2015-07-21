package pl.media30.zamowieniapubliczne;

import android.app.Activity;
import android.app.RemoteInput;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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

            TextView rezultat = (TextView) findViewById(R.id.textView1);
            Bundle przekazanedane = getIntent().getExtras();
            String przekazanytekst = przekazanedane.getString("dane");

            rezultat.setText(przekazanytekst);
        }

    }


