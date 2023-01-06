package pe.mpc.muni.ModuloEventos;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import pe.mpc.muni.Adapters.AdapterEvento;
import pe.mpc.muni.Interface.EventoApi;
import pe.mpc.muni.Model.ApiClient;
import pe.mpc.muni.Model.Check;
import pe.mpc.muni.Model.Evento;
import pe.mpc.muni.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventosActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    private EditText etFiltrarEvento;
    private Button btnBuscarEvento;
    private EventoApi eventoApi;
    private AdapterEvento adapter;
    private List<Evento> eventosList;
    private RecyclerView recyclerView;
    private boolean status = true;
    AdapterEvento.RecyclerViewClickListener listener;
    private Task taskLoading;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_eventos_all);
        inicializations();
        listener = new AdapterEvento.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {
                /**ESTA LINEA ES SOLO EL CONSTRUCTOR, NO LE PUDE DAR FUNCION*/
            }
        };

        String url = getResources().getString(R.string.url_eventos)+"events/";
        eventoApi= ApiClient.getApiClient(url).create(EventoApi.class);
        taskLoading = new Task();
        taskLoading.execute();
        listarEvents();
    }
    private void inicializations(){
        etFiltrarEvento = (EditText)findViewById(R.id.etFiltrarEvento);
        btnBuscarEvento = (Button)findViewById(R.id.btnBuscarEvento);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewEventos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        btnBuscarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarEvent();
            }
        });
    }
    private void buscarEvent(){
        if (etFiltrarEvento.getText().toString().isEmpty()){
            etFiltrarEvento.setError("Ingrese palabra clave");
        }else {
            new Task().execute();
            String filter = etFiltrarEvento.getText().toString();
            Call<List<Evento>> call = eventoApi.find("47dd063d9d306bb9053f1e60664bcd771137a2c1",filter);
            call.enqueue(new Callback<List<Evento>>() {
                @Override
                public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                    if (response.body() != null) {
                        switch (response.body().get(0).getStatus()) {
                            case "200":
                                status = true;
                                cargarRecycler(response);
                                break;
                            case "404":
                                status = true;
                                Check.menssage("No se encontraron resultados", EventosActivity.this);
                                break;
                            default:
                                status = false;
                                Check.menssage("Acceso denegado", EventosActivity.this);
                                break;
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<Evento>> call, Throwable t) {
                    status = false;
                    Check.inflaterAlerta(EventosActivity.this, R.layout.inflater_sin_resultados_event);

                }
            });
        }
    }
    private void listarEvents(){
        Call<List<Evento>> call=eventoApi.all("47dd063d9d306bb9053f1e60664bcd771137a2c1");
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                if (response.body()!=null){
                    switch (response.body().get(0).getStatus()){
                        case "200":
                            status = true;
                            cargarRecycler(response);
                            break;
                        case "404":
                            status = false;
                            Check.menssage("No se encontraron resultados",EventosActivity.this);
                            break;
                        default:
                            status = false;
                            Check.menssage("Acceso denegado",EventosActivity.this);
                            break;
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                status = false;
                Check.inflaterAlerta(EventosActivity.this, R.layout.inflater_sin_resultados_event);

            }
        });

    }
    private void cargarRecycler( Response<List<Evento>> response){
        eventosList = response.body();
        //Cargar Adapter
        try {
            adapter = new AdapterEvento(eventosList, EventosActivity.this, listener);
            recyclerView.setAdapter((RecyclerView.Adapter) adapter);
            adapter.notifyDataSetChanged();
        }catch (Exception  e){
            //inflaterError();
        }
    }
    private class Task extends AsyncTask<Void,Integer,Boolean> {
        private Dialog dialog;
        private boolean isOnline;

        @Override
        protected void onPreExecute() {
            dialog = new Dialog(EventosActivity.this);
            Check.inflaterLoading(dialog,EventosActivity.this,true);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            //Verificar conexion a internet
            isOnline=Check.isOnline(EventosActivity.this);
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
            Check.inflaterLoading(dialog,EventosActivity.this,false);
            if(!isOnline){
                Check.inflaterSinConexion(EventosActivity.this);
            }
            if (status == false){finish();}
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
