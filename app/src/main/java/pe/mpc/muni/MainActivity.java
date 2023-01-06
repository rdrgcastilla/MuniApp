package pe.mpc.muni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.onesignal.OneSignal;

import pe.mpc.muni.ModuloEventos.EventosActivity;
import pe.mpc.muni.ModuloLicencias.BuscarLicencia;
import pe.mpc.muni.ModuloTramiteDoc.ConsultaDocumento;

/**
 * Class that initializes the Main Activity for the app.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_main);

        ImageButton museums_btn = (ImageButton) findViewById(R.id.museums_btn);
        ImageButton parks_btn = (ImageButton) findViewById(R.id.parks_btn);
        ImageButton semana_btn = (ImageButton) findViewById(R.id.semana_btn);
        ImageButton contact_btn = (ImageButton) findViewById(R.id.contact_btn);

        museums_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, ConsultaDocumento.class);
                startActivityForResult(intent, 0);
            }
        });

        parks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, BuscarLicencia.class);
                startActivityForResult(intent, 0);
            }
        });

        semana_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent (MainActivity.this, ContactActivity.class);
                Intent intent = new Intent (MainActivity.this, EventosActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        contact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent (MainActivity.this, ContactActivity.class);
                Intent intent = new Intent (MainActivity.this, InfoActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
