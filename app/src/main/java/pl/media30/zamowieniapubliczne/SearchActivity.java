package pl.media30.zamowieniapubliczne;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Andreas on 2015-07-24.
 */
public class SearchActivity extends Activity {


    String[] wojewodztwa = {"Ignoruj kryterium","dolnośląskie", "kujawsko-pomorskie", "lubelskie", "lubuskie", "łódzkie", "małopolskie", "mazowieckie", "opolskie", "podkarpackie", "podlaskie", "pomorskie", "śląskie", "świętokrzyskie", "warmińsko-mazurskie", "wielkopolskie", "zachodniopomorskie"};

    Spinner spinner;
    EditText editText;
    EditText editText3;
    EditText editText4;
    Button button;




    public String parseText(String str) {
        if (str.length() >= 2) {
            str = str.toLowerCase();
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
            str.trim();
        }
        return str;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("wartoscPobrana", "*");
        intent.putExtra("woj","*");
        intent.putExtra("kodPoczt", "*");
        intent.putExtra("zamawNazwa", "*");
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, wojewodztwa);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        editText = (EditText) findViewById(R.id.editText);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(myhandler1);
    }

    View.OnClickListener myhandler1 = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), editText.getText().toString()+" "+"\n", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            if (!(editText.getText().toString().equals(""))) {
                intent.putExtra("wartoscPobrana", parseText(editText.getText().toString()));
            } else {
                intent.putExtra("wartoscPobrana", "*");
            }

            if (!(spinner.getSelectedItem().toString().equals("ignoruj kryterium"))) {
                intent.putExtra("woj", (spinner.getSelectedItem().toString()));
                Toast.makeText(getApplicationContext(), spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            } else {
                intent.putExtra("woj","*");
            }

            if (!(editText3.getText().toString().equals(""))) {
                intent.putExtra("kodPoczt", (editText3.getText().toString()));
                Toast.makeText(getApplicationContext(), editText3.getText().toString(), Toast.LENGTH_SHORT).show();
            } else {
                intent.putExtra("kodPoczt","*");
            }

            if (!(editText4.getText().toString().equals(""))) {
                intent.putExtra("zamawNazwa", (editText4.getText().toString()));
                Toast.makeText(getApplicationContext(), editText4.getText().toString(), Toast.LENGTH_SHORT).show();
            } else {
                intent.putExtra("zamawNazwa","*");
            }
            setResult(RESULT_OK, intent);
            finish();
        }
    };
}
