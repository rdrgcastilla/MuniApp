package pe.mpc.muni.ModuloTramiteDoc;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.List;

import pe.mpc.muni.Interface.DocumentoApi;
import pe.mpc.muni.MainActivity;
import pe.mpc.muni.Model.ApiClient;
import pe.mpc.muni.Model.Check;
import pe.mpc.muni.Model.Documento;
import pe.mpc.muni.ModuloRentas.ConsultaArbitrios;
import pe.mpc.muni.ModuloTransporte.PublicoActivity;
import pe.mpc.muni.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultaDocumento extends AppCompatActivity {
    private DocumentoApi documentoApi;
    private TextView txtResult;
    private EditText etNroDocumetoTramite;
    private Documento documento;
    private TextView btnBuscarTramiteDocumentario;
    //Objetos del content
    private RelativeLayout layoutContentDatosTramiteDocumentario,layoutContentProgressBar;
    private TextView txtFechaRegistro;
    private TextView txtNTramite;
    private TextView txtTipoDocumento;
    private TextView txtNumDocumento;
    private TextView txtInstitucion;
    private TextView txtRemite;
    private TextView txtFolios;
    private TextView txtSumilla;
    private TextView txtObservaciones;
    private TextView txtOficinaAtencion;
    StateProgressBar stateProgressBar;
    private String[] descriptionData = {"Pendiente", "En proceso", "Finalizado"};
    private Button btn_inicio, btn_consulta;
    LinearLayout li_check;
    ImageView img_tick;
    boolean isSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_documento);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);

        stateProgressBar = (StateProgressBar) findViewById(R.id.Progres);
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/aileron_bold.otf");
        stateProgressBar.setStateNumberTypeface("fonts/aileron_bold.otf");

        documentoApi= ApiClient.getApiClient(getResources().getString(R.string.url_buscar_tramite_documentario)).create(DocumentoApi.class);
        etNroDocumetoTramite=(EditText)findViewById(R.id.etNroDocumetoTramite);
        etNroDocumetoTramite.requestFocus();
        btnBuscarTramiteDocumentario=(TextView) findViewById(R.id.btnBuscarTramiteDocumentario);
        //CASTEAR OBJETOS DE RESULTADO
        layoutContentDatosTramiteDocumentario=(RelativeLayout) findViewById(R.id.layoutContentDatosTramiteDocumentario);
        layoutContentProgressBar=(RelativeLayout) findViewById(R.id.layoutContentProgressBar);
        txtFechaRegistro=(TextView)findViewById(R.id.txtFechaRegistro);
        txtNTramite=(TextView)findViewById(R.id.txtNTramite);
        txtTipoDocumento=(TextView)findViewById(R.id.txtTipoDocumento);
        txtNumDocumento=(TextView)findViewById(R.id.txtNumDocumento);
        txtInstitucion=(TextView)findViewById(R.id.txtInstitucion);
        txtRemite=(TextView)findViewById(R.id.txtRemite);
        txtFolios=(TextView)findViewById(R.id.txtFolios);
        txtSumilla=(TextView)findViewById(R.id.txtSumilla);
        txtObservaciones=(TextView)findViewById(R.id.txtObservaciones);
        txtOficinaAtencion=(TextView)findViewById(R.id.txtOficinaAtencion);
        btn_inicio = (Button)findViewById(R.id.btn_inicio);
        btn_consulta = (Button)findViewById(R.id.btn_consulta);

        btnBuscarTramiteDocumentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNroDocumetoTramite.getText().toString().isEmpty()){
                    new Task().execute();
                }else {
                    Check.menssage("Ingrese el N° de su documento",ConsultaDocumento.this);
                }

            }
        });
        btn_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultaDocumento.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultaDocumento.this, ConsultaDocumento.class);
                startActivity(intent);
            }
        });

        //Check - TyC
        /*-------Status Color Code To Change--------*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.White));
        }
        li_check = findViewById(R.id.li_check);
        img_tick = findViewById(R.id.img_tick);
        li_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelected == false){
                    img_tick.setVisibility(View.VISIBLE);
                    isSelected = true;
                }else {
                    img_tick.setVisibility(View.GONE);
                    isSelected = false;
                }
            }
        });
    }


    private void buscarDocumento (final String nDocumento){
        Call<List<Documento> >call=documentoApi.consultaTramiteDocumento("json",nDocumento);
        call.enqueue(new Callback<List<Documento>>() {
            @Override
            public void onResponse(Call<List<Documento>> call, Response<List<Documento>> response) {
                //Si la busqueda es nula
                if(response.body().get(0).getError()!=null ){
                    Check.inflaterAlerta(ConsultaDocumento.this, R.layout.inflater_sin_resultados);
                }
                //Si la busqueda es correcta
                else if (response.body().get(0).getcCodificacion().toString().contains(nDocumento)){
                    documento=response.body().get(0);
                    cargarResultados();
                    Check.menssage("Cargando...",ConsultaDocumento.this);
                }
            }

            @Override
            public void onFailure(Call<List<Documento>> call, Throwable t) {
                //Check.menssage("RP: "+ t.getMessage().toString(), ConsultaDocumento.this);
                    Check.inflaterAlerta(ConsultaDocumento.this, R.layout.inflater_error_server);

            }
        });

    }
    private void cargarResultados(){
        /**
         este metodo se ejecuta para cargar los valores encontrados en la WS, se debera cargar cada objeto con el valor encontrado,
         el cual ha sido almacenado en el objeto documento para su facil manipulacion
         */
        layoutContentDatosTramiteDocumentario.setVisibility(View.VISIBLE);
        txtFechaRegistro.setText(documento.getfFecRegistro());
        txtNTramite.setText(documento.getcCodificacion());
        txtTipoDocumento.setText(documento.getcDescTipoDoc());
        txtNumDocumento.setText(documento.getcNroDocumento());
        txtInstitucion.setText(documento.getcNombre());
        txtRemite.setText(documento.getcNomRemite());
        txtFolios.setText(documento.getnNumFolio());
        txtSumilla.setText(documento.getcAsunto());
        txtObservaciones.setText(documento.getcObservaciones());
        txtOficinaAtencion.setText(documento.getcNomOficina());
        layoutContentProgressBar.setVisibility(View.VISIBLE);
        switch (documento.getnFlgEstado()){
            case "1":
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                break;
            case "2":
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                break;
            case "3":
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                break;
        }

    }
    private class Task extends AsyncTask<Void,Integer,Boolean> {
        private Dialog dialog;

        private boolean isOnline;

        @Override
        protected void onPreExecute() {
            buscarDocumento(etNroDocumetoTramite.getText().toString());
            dialog = new Dialog(ConsultaDocumento.this);

            Check.inflaterLoading(dialog,ConsultaDocumento.this,true);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            //Verificar conexion a internet
            isOnline=Check.isOnline(ConsultaDocumento.this);
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Check.inflaterLoading(dialog,ConsultaDocumento.this,false);
            if(!isOnline){
                Check.inflaterSinConexion(ConsultaDocumento.this);
            }

        }
    }
}