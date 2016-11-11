package itesm.eldyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Daniel on 10/11/16.
 */

public class LoadData extends AsyncTask<String, Void, JSONObject> {

    private ProgressDialog progressDialog;
    private Context context;


    public LoadData() {

    }

    @Override
    protected void onPreExecute(){
        //Lo que muestra miestras carga
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        super.onPreExecute();

    }
    @Override
    protected JSONObject doInBackground(String... params) {
        String sData = params[0];
        String sText = "";
        BufferedReader bfReader=null;
        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("10.2.145.43:7777/compile?program=algo");

            // Send POST data reques

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

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
                sb.append(line + "\n");
            }


            sText = sb.toString();
        } catch(Exception ex) {

        }
        finally {
            try {

                bfReader.close();
            }

            catch(Exception e) {}
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject){


    }
}
