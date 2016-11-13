package itesm.eldyapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Archivos extends AppCompatActivity {

    List<File> ListFiles = new ArrayList<File>();
    List<String> ListaNombres = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivos);

        File dirFileObj = new File(getFilesDir(),"");
        ListFiles = getListFiles(dirFileObj);
        getNames();


        ListView filesLV = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getApplicationContext(), R.layout.row, ListaNombres);
        filesLV.setAdapter(adapter);


        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                File filePress = ListFiles.get(position);
                String sContent = readFile(view, ListaNombres.get(position));
                Intent intent = new Intent(Archivos.this, Program.class);
                intent.putExtra("nombre", ListaNombres.get(position));
                intent.putExtra("content", sContent);
                intent.putExtra("from", "Archivos");
                startActivity(intent);

            }
        };
        filesLV.setOnItemClickListener(itemListener);



    }

    //Obtiene la lista de archivos del internal storage
    List<File> getListFiles(File parentDir) {

        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                ListFiles.addAll(getListFiles(file));
            } else {
                ListFiles.add(file);
            }
        }
        return ListFiles;
    }

    public void getNames(){
        for(int i=0; i< ListFiles.size(); i++){
            File fFile = ListFiles.get(i);
            String sName = fFile.getName();
            ListaNombres.add(sName);

        }
    }

    public String readFile(View view, String sFile){
        StringBuffer sb = new StringBuffer();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        String line = "";
        BufferedReader bufferedReader = null;
        try {
            fis = new FileInputStream("/data/data/itesm.eldyapp/files/" + sFile);
            isr = new InputStreamReader(fis);
            bufferedReader = new BufferedReader(isr);


            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }

        }  catch (IOException e) {
            e.printStackTrace();
        }
        String sContenido = sb.toString();
        return sContenido;

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_archivos, menu);
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
