package itesm.eldyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ReadValue extends AppCompatActivity  {

    TextView varTV;
    String [] sValor = null;
    String sCode;
    int iVar = 0;
    EditText variablesET;
    String sJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_value);

        varTV = (TextView) findViewById(R.id.textView);
        variablesET = (EditText) findViewById(R.id.codeText);
        final Button subtBttn = (Button) findViewById(R.id.subtBttn);

        variables();
        sJson = "{ \n" +
                "\"code\":\"" + sCode + "\",\n";




        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                sValor = new String[iVar];
                if (subtBttn.isPressed()) {
                    String sValue = variablesET.getText().toString() + "\n";
                    for(int i = 0; i < iVar; i++){
                        int iS = sValue.indexOf("\n");
                        String sAux = sValue.substring(0, iS);
                        sValor[i] = sAux;
                        sValue = sValue.replaceFirst(sAux + "\n", "");


                    }
                    sJson += variablesJson(sValor, iVar);
                    //String url = "10.2.145.43:7777/compile?program=algo" + queryEncode(sJson);

                    intent = new Intent(ReadValue.this, Result.class);
                    intent.putExtra("json", sJson);
                    intent.putExtra("from", "Read");
                    startActivity(intent);

                }
            }
        };
        subtBttn.setOnClickListener(listener);
    }




    public void variables() {
        Intent intent = this.getIntent();
        Bundle datos = intent.getExtras();
        String[] sVar= datos.getStringArray("variables");
        sCode = datos.getString("code");

        for (int i = 0; i < sVar.length; i++){
            if(sVar[i]==null)
                break;
            else {
                varTV.append(sVar[i] + "\n");
                iVar++;
            }
        }


    }

    public String variablesJson(String sV[], int iN){
        String sJson = "\"variables\" : [\n";
        for(int i = 0; i < iN; i++){

            sJson += (i + 1 == iN)? "\"" + sV[i] + "\" \n" : "\"" + sV[i] + "\", \n";

        }
        sJson += "]\n}";
        return sJson;
    }

    public String queryEncode(String sJ){
        String sQuery = null;
        try {
            sQuery = URLEncoder.encode(sJ, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return sQuery;
    }

    




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read_value, menu);
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