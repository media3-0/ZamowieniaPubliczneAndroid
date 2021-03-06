package pl.media30.zamowieniapubliczne.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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

import pl.media30.zamowieniapubliczne.R;

/**
 * Created by Andreas on 2015-07-24.
 * Aktywność umożliwiająca wyszukiwanie przetargów po podaniu szczegółowego kryterium.
 */
public class SearchActivity extends Activity {


    String[] wojewodztwa = {"ignoruj kryterium","dolnośląskie", "kujawsko-pomorskie", "lubelskie", "lubuskie", "łódzkie", "małopolskie", "mazowieckie", "opolskie", "podkarpackie", "podlaskie", "pomorskie", "śląskie", "świętokrzyskie", "warmińsko-mazurskie", "wielkopolskie", "zachodniopomorskie"};

    Spinner spinner;
    EditText editText;
    EditText editText3;
    EditText editText4;
    EditText editTextREGON;
    EditText editTextWWW;
    EditText editTextEmail;
    Button button;
    boolean usuwanie = false;

    public String parseText(String str) {
        if (str.length() >= 2) {
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
        intent.putExtra("wartoscMiasto", "*");
        intent.putExtra("wartoscWoj", "*");
        intent.putExtra("wartoscKod", "*");
        intent.putExtra("wartoscNazwa", "*");
        intent.putExtra("wartoscREGON", "*");
        intent.putExtra("wartoscWWW", "*");
        intent.putExtra("wartoscEmail", "*");
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

        editTextREGON = (EditText) findViewById(R.id.editTextREGON);
        editTextWWW = (EditText) findViewById(R.id.editTextWWW);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        button = (Button) findViewById(R.id.button);
        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_48dp, 0, 0, 0);
        button.setOnClickListener(myhandler1);


        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText3.length() == 2 && usuwanie==false){
                    editText3.setText(editText3.getText()+"-");
                    editText3.setSelection(3);
                    usuwanie = true;
                }else if (editText3.length()  == 2 ){
                    usuwanie = false;
                    editText3.setText(editText3.getText().toString().substring(0, editText3.length()-1));
                    editText3.setSelection(1);
                }
            }
        });
    }

    View.OnClickListener myhandler1 = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            if (!(editText.getText().toString().equals(""))) {
                intent.putExtra("wartoscMiasto", parseText(editText.getText().toString()));
            } else {
                intent.putExtra("wartoscMiasto", "*");
            }

            if (!(spinner.getSelectedItem().toString().equals("ignoruj kryterium"))) {
                intent.putExtra("wartoscWoj", (spinner.getSelectedItem().toString()));
            } else {
                intent.putExtra("wartoscWoj","*");
            }

            if (!(editText3.getText().toString().equals(""))) {
                intent.putExtra("wartoscKod", (editText3.getText().toString()));
            } else {
                intent.putExtra("wartoscKod","*");
            }

            if (!(editText4.getText().toString().equals(""))) {
                intent.putExtra("wartoscNazwa", (editText4.getText().toString()));
            } else {
                intent.putExtra("wartoscNazwa","*");
            }




            if (!(editTextREGON.getText().toString().equals(""))) {
                intent.putExtra("wartoscREGON", (editTextREGON.getText().toString()));
            } else {
                intent.putExtra("wartoscREGON","*");
            }

            if (!(editTextWWW.getText().toString().equals(""))) {
                intent.putExtra("wartoscWWW", (editTextWWW.getText().toString()));
            } else {
                intent.putExtra("wartoscWWW","*");
            }

            if (!(editTextEmail.getText().toString().equals(""))) {
                intent.putExtra("wartoscEmail", (editTextEmail.getText().toString()));
            } else {
                intent.putExtra("wartoscEmail","*");
            }

            setResult(RESULT_OK, intent);
            finish();
        }
    };
}
