package itesm.eldyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button crearBttn = (Button) findViewById(R.id.crearBttn);
        final Button cargarBttn = (Button) findViewById(R.id.cargarBttn);
        final Button estandarBttn = (Button) findViewById(R.id.estandarBttn);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (crearBttn.isPressed()){
                    intent = new Intent(MainActivity.this, Program.class);
                    startActivity(intent.putExtra("from", "Main"));
                }
                if (cargarBttn.isPressed()){
                    intent = new Intent(MainActivity.this, Archivos.class);
                    startActivity(intent);
                }
                if (estandarBttn.isPressed()){
                    intent = new Intent(MainActivity.this, Estandar.class);
                    startActivity(intent);
                }

            }
        };
        crearBttn.setOnClickListener(listener);
        cargarBttn.setOnClickListener(listener);
        estandarBttn.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
