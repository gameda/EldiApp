package itesm.eldyapp;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Program extends AppCompatActivity {

    EditText codeET, nameET;
    String sCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        codeET = (EditText) findViewById(R.id.codeText);
        nameET = (EditText) findViewById(R.id.nameCode);
        final Button runBttn = (Button) findViewById(R.id.button);
        final Button saveBttn = (Button) findViewById(R.id.savebutton);
        //ComponentName cnName = getCallingActivity();
        final String sAct = getIntent().getStringExtra("from");


        if(sAct.equals("Archivos")){
            Intent intent = this.getIntent();
            Bundle datos = intent.getExtras();
            String sName = datos.getString("nombre");
            String sContenido = datos.getString("content");
            nameET.setText(sName);
            codeET.setText(sContenido);
        }






        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sCode = codeET.getText().toString();
                Intent intent;
                if (runBttn.isPressed()){
                    Intent intentRun;

                    if(sCode.contains("read();")) {
                        int iI, iFin;
                        String sSub;
                        String sAux = sCode + "\n";
                        sAux = sAux.replaceAll(" ", "");
                        String sVaribales[] = new String[10];
                        int n = 0;

                        while (!sAux.isEmpty()){
                            iFin = sAux.indexOf("\n");
                            sSub = sAux.substring(0, iFin+1);

                            if(sSub.contains("read();")){
                                iI = sSub.indexOf("=");
                                sVaribales[n] = sSub.substring(0, iI);
                                n++;



                            }
                            if(sSub.equals("\n")){
                                sAux = sAux.replaceFirst("\n", "");
                            }
                            else
                                sAux = sAux.replace(sSub, "");


                        }

                        intentRun = new Intent(Program.this, ReadValue.class);

                        intentRun.putExtra("code", sCode);
                        intentRun.putExtra("variables", sVaribales);
                        startActivity(intentRun);
                    }
                    else {
                        intentRun = new Intent(Program.this, Result.class);
                        String sJson = "{ \n" +
                                "\"code\":\"" + sCode + "\"" +
                                "\n}";

                        intentRun.putExtra("json", sJson);
                        intentRun.putExtra("from", "Program");
                        startActivity(intentRun);

                    }

                }
                if (saveBttn.isPressed()){
                    saveFile(view);

                }


            }
        };
        runBttn.setOnClickListener(listener);
        saveBttn.setOnClickListener(listener);
    }


    public void saveFile (View view) {
        sCode = codeET.getText().toString();
        String sName = nameET.getText().toString();
        try {
            FileOutputStream fileCodeText = openFileOutput(sName, MODE_PRIVATE);
            fileCodeText.write(sCode.getBytes());
            fileCodeText.close();
            Toast.makeText(getApplicationContext(), "Archivo '" + sName +"' Guardado", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_program, menu);
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