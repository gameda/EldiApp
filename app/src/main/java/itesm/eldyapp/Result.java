package itesm.eldyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Result extends AppCompatActivity {
    private ProgressDialog progressDialog;
    String sStatus="";
    String sResultado="";
    String sExetime="";
    TextView statusTV, resultTV, exetimeTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        statusTV = (TextView) findViewById(R.id.statusTV);
        resultTV = (TextView) findViewById(R.id.resultTV);
        exetimeTV = (TextView) findViewById(R.id.exetimeTV);
        final String sAct = getIntent().getStringExtra("from");

        if(sAct.equals("Read")){
            Intent intent = this.getIntent();
            Bundle datos = intent.getExtras();
            String sJson = datos.getString("json");
            new LoadData().execute(sJson);
        }
        else{
            Intent intent = this.getIntent();
            Bundle datos = intent.getExtras();
            String sJson = datos.getString("json");
            new LoadData().execute(sJson);
        }

    }

    private class LoadData extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute(){
            //Lo que muestra miestras carga
            progressDialog = new ProgressDialog(Result.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();

        }
        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            String sData = params[0];
            String sText = "";
            BufferedReader bfReader=null;
            // Send data
            try
            {
                // Defined URL  where to send data
                URL url = new URL("http://lagwy.com/example.php");
                //URL url = new URL("http://lagwy.com/example.php10.2.145.43:7777/compile?program=algo");

                // Send POST data reques

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.connect();

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( sData );
                wr.flush();
                wr.close ();

                // Get the server response

                bfReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line="";

                // Read Server Response
                while((line = bfReader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line);
                }
                sText = sb.toString();
                jsonObject = new JSONObject(sText);

            } catch(Exception ex) {

            }
            finally {
                try {
                    bfReader.close();

                } catch(Exception e) {}
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject){

            try {

                sStatus=jsonObject.getString("status");
                sResultado=jsonObject.getString("resultado");
                sExetime=jsonObject.getString("exetime");
                progressDialog.dismiss();
                statusTV.setText("Estatus: " + sStatus);
                resultTV.setText("Resultado: \n" + sResultado);
                exetimeTV.setText("Tiempo Ejecución: " + sExetime);

            } catch (JSONException e) {

                e.printStackTrace();
            }
            super.onPostExecute(jsonObject);



        }
    }
}
