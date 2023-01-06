package pe.mpc.muni.ModuloEventos;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pe.mpc.muni.Interface.EventoApi;
import pe.mpc.muni.Model.ApiClient;
import pe.mpc.muni.Model.Check;
import pe.mpc.muni.Model.Evento;
import pe.mpc.muni.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventoShowActivity extends AppCompatActivity {
    private String eventId;
    private EventoApi eventoApi;
    private ImageButton detail_back_btn;
    private ImageView image_evento;
    private TextView name_evento, descripcion_evento, hora_evento, fecha_evento, direccion_evento;
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_evento_show);
        inicializations();
        String url = getResources().getString(R.string.url_eventos)+"events/";
        eventoApi= ApiClient.getApiClient(url).create(EventoApi.class);
        Intent intent = getIntent();
        eventId = intent.getStringExtra("eventID");
        new Task().execute();
    }
    private void cargarEvento(String eventId){
        Call<Evento> call=eventoApi.show("47dd063d9d306bb9053f1e60664bcd771137a2c1",eventId);
        call.enqueue(new Callback<Evento>() {
            @Override
            public void onResponse(Call<Evento> call, Response<Evento> response) {
                name_evento.setText(response.body().getName().toString());
                descripcion_evento.setText(response.body().getDescripcion().toString());
                hora_evento.setText(response.body().getHora().toString());
                fecha_evento.setText(response.body().getFecha().toString());
                direccion_evento.setText(response.body().getLugar().toString());
                String imgenUrl = getResources().getString(R.string.url_eventos)+response.body().getImagen();
                try {
                    Picasso.with(getApplicationContext()).load(imgenUrl).into(image_evento);
                    image_evento.setBackground(null);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Evento> call, Throwable t) {
               finish();
            }
        });

    }
    private class Task extends AsyncTask<Void,Integer,Boolean> {
        private Dialog dialog;
        private boolean isOnline;

        @Override
        protected void onPreExecute() {
            cargarEvento(eventId);
            dialog = new Dialog(EventoShowActivity.this);
            Check.inflaterLoading(dialog,EventoShowActivity.this,true);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            //Verificar conexion a internet
            isOnline=Check.isOnline(EventoShowActivity.this);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Check.inflaterLoading(dialog,EventoShowActivity.this,false);
            if(!isOnline){
                Check.inflaterSinConexion(EventoShowActivity.this);
            }

        }
    }
    private void inicializations(){
        name_evento = findViewById(R.id.name_evento);
        image_evento = findViewById(R.id.image_evento);
        descripcion_evento = findViewById(R.id.descripcion_evento);
        hora_evento = findViewById(R.id.hora_evento);
        fecha_evento = findViewById(R.id.fecha_evento);
        direccion_evento = findViewById(R.id.direccion_evento);
        detail_back_btn = findViewById(R.id.detail_back_btn);
        detail_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
