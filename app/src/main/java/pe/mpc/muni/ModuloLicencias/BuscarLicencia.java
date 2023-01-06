package pe.mpc.muni.ModuloLicencias;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import pe.mpc.muni.MainActivity;
import pe.mpc.muni.Model.ApiClient;
import pe.mpc.muni.Model.Check;
import pe.mpc.muni.Model.Licencia;
import pe.mpc.muni.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarLicencia extends AppCompatActivity {
    private boolean status = true;
    private LicenciaApi licenciaApi;
    private EditText etNroDNI;
    private Button btnBuscarLicencia, btn_NuevaConsulta, btn_inicio;
    private ImageView imageView;
    private TextView txtDni,nombre, fecha_emision, fecha_revalidacion, nlicencia;
    private String token;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_buscar_licencia);
        licenciaApi = ApiClient.getApiClient(getResources().getString(R.string.url_buscar_tramite_documentario)).create(LicenciaApi.class);

        inicializations();
    }
    private void inicializations(){
        btn_inicio = (Button)findViewById(R.id.btn_inicio);
        btn_NuevaConsulta = (Button)findViewById(R.id.btn_NuevaConsulta);
        btnBuscarLicencia = (Button)findViewById(R.id.btnBuscarLicencia);
        imageView = (ImageView)findViewById(R.id.imagePerson);
        etNroDNI = (EditText)findViewById(R.id.etNroDNI);
        txtDni = (TextView)findViewById(R.id.txtDni);
        nombre = (TextView)findViewById(R.id.nombre);
        fecha_emision = (TextView)findViewById(R.id.fecha_emision);
        fecha_revalidacion = (TextView)findViewById(R.id.fecha_revalidación);
        nlicencia = (TextView)findViewById(R.id.nlicencia);
        btnBuscarLicencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNroDNI.getText().toString().isEmpty()){
                    etNroDNI.setError("Ingrese un número de DNI");
                }else{
                    new Task().execute();
                    //generarToken();
                    buscarLicencia(token);
                }
            }
        });
        btn_NuevaConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuscarLicencia.this, BuscarLicencia.class);
                startActivity(intent);
            }
        });
        btn_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuscarLicencia.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void generarToken(){
        Call<Licencia> call = licenciaApi.generateToken("none");
        call.enqueue(new Callback<Licencia>() {
            @Override
            public void onResponse(Call<Licencia> call, Response<Licencia> response) {
                if (response.body()!=null){
                    token = response.body().getMensaje().toString();
                    buscarLicencia(token);
                }
            }
            @Override
            public void onFailure(Call<Licencia> call, Throwable t) {
                status = false;
                Check.inflaterAlerta(BuscarLicencia.this, R.layout.inflater_error_server);
                //Check.menssage("Acceso denegado, algo salió mal"+t.getMessage().toString(),BuscarLicencia.this);
            }
        });
    }

    private void buscarLicencia(String token){
        Call<Licencia> call = licenciaApi.buscar_test(etNroDNI.getText().toString());
        call.enqueue(new Callback<Licencia>() {
            @Override

            public void onResponse(Call<Licencia> call, Response<Licencia> response) {
                System.out.println("Hola " + response.body().getEstado());
                if (response.body()!=null){
                    switch (response.body().getEstado()){
                        case "200":
                            txtDni.setText(response.body().getNdni());
                            nombre.setText(response.body().getNombre()+"\n"+response.body().getApellido());
                            fecha_emision.setText(response.body().getFecha_emision());
                            fecha_revalidacion.setText(response.body().getFecha_revalidacion());
                            nlicencia.setText("MPC-"+response.body().getNlicencia());

                            /**Cargar imagen*/
                            Bitmap foto=getFotoFromString(response.body().getFot());
                            foto=Bitmap.createScaledBitmap(foto,foto.getWidth()*3,foto.getHeight()*3,true);
                            imageView.setImageBitmap(foto);

                            // ..\/sistema\/Fotos\/17625.jpg
                            //  https://www.municanete.gob.pe/munivirtual/transporte/licencias   ../sistema/Fotos/17625.jpg
                            break;
                        case "404":
                            //Check.menssage("No se encontraron resultados",BuscarLicencia.this);
                            Check.inflaterAlerta(BuscarLicencia.this, R.layout.inflater_sin_resultados);
                            break;
                        case "408":
                            Check.menssage("Tiempo de espera agotado. Intente nuevamente",BuscarLicencia.this);
                            break;
                        case "500":
                            Check.menssage("Al parecer tenemos problemas con nuestro servidor. Lo solucionaremos pronto.",BuscarLicencia.this);
                            break;
                        case "503":
                            Check.menssage("Al parecer tenemos problemas con nuestro servidor. Lo solucionaremos pronto.",BuscarLicencia.this);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Licencia> call, Throwable t) {
                status = false;
                System.out.println("Puma: "+t.getMessage().toString());
                Check.inflaterAlerta(BuscarLicencia.this, R.layout.inflater_error_server);
            }
        });
    }

    private Bitmap getFotoFromString(String imagenBase64){
        String data="data:image/jpg;base64,/9j/4AAQSkZJRgABAQEDnwOfAAD//gA7Q1JFQVRPUjogZ2QtanBlZyB2MS4wICh1c2luZyBJSkcgSlBFRyB2OTApLCBxdWFsaXR5ID0gMTAK/9sAQwBQNzxGPDJQRkFGWlVQX3jIgnhubnj1r7mRyP///////////////////////////////////////////////////9sAQwFVWlp4aXjrgoLr/////////////////////////////////////////////////////////////////////////8AAEQgCAAIAAwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh4uPk5ebn6Onq8fLz9PX29/j5+v/EAB8BAAMBAQEBAQEBAQEAAAAAAAABAgMEBQYHCAkKC//EALURAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQkjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm5+jp6vLz9PX29/j5+v/aAAwDAQACEQMRAD8ApUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFKAT0pwiY9eKAGUVMIlHXmngAdBinYCAIx7GnCE9yKmoosBGIR3JpfLX0p9FAhNq/3R+VLRRTAKKKKACiiigApNq/3R+VLRQAzy19KQwjsTUlFICEwnsRTSjDsasUUWGVaKskA9RmmGJT04osBDRTzEw6c00jHWkAlFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFKqljxUqxAdeTQBEqlugqRYh/EfyqWinYQgAHQYpaKKYBRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUhAPUZpaKAImiH8J/OoypXqKs0UrAVaKmaIHpwaiZSp5pDEooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiinKhb2HrQAgGelSLF3b8qeqhelOp2AQDHSloopiCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKCM9aKKAImi7r+VREY61aprKG60rDK9FOZCvuPWm0gCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKUAk4FTJHt5PWgBqRd2/KpaKKYgooopgFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAVE8XdfyqWikBVoqd493I6/zqEgg4NIYlFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFKAScCgAk4FTogUe9AAiBR706iiqEFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUoBPQE0oic/wmgBtFSeTJ6frR5D+350gI6Kk8h/b86PJk9P1oAjopxicfwmkII6gimAlFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAU10DD3p1FAFYgg4NJVh0DD3qAgg4NSMSiiigAooooAKKKKACiiigAooooAKKKKACiiigApQCTgUlTxptGT1/lQAqIFHvTqKKoQUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUU5EZzgD8aAG09YnboOPU1OkKr15PvUlK4EK24H3jmpBGi9FFOopDCiiigAooooAKKKKACiiigBpjRuqio2twfunFTUUAVGideo49RTKvVG8Kt7H2p3EVaKc6MhwR+NNpgFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFNdAw96dRQBWIIOD1pKnkTcMjr/OoKkYUUUUAFFFFABRRRQAUUUUAFFFFABRRTkXc3tQA+JP4j+FS0UUxBRRRTAKKKKACiiigAooooAKKKKACiiigAoop0aF2x270ALFGXPoKtABRgDAoAAGB0pakYUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFACEBhgjIqrLGUPqKt0hAIwelAFKinSIUbHbtTaoQUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAVFKn8Q/GpaKQFWinOu1vbtTaQwooooAKKKKACiiigAooooAUDJxU6LtXFMiX+I/hUtNAFFFFMQUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAVaiTYnuetQQrukHoOat0mAUUUUhhRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAEcqb09x0qrV6qky7ZD6HmmhDKKKKYBRRRQAUUUUAFFFFABRRRQAUUUUAFFFFADXXcuKgIwcVZqKVf4h+NJjIqKKKQBRRRQAUUUUAFKq7mxSVNEuFz3NAEg4GKKKKoQUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAT2w4J/Cp6jgGIh71JUjCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAqC5HAP4VPUc4zEfagCrRRRVCCiiigAooooAKKKKACiiigAooooAKKKKACkIyMUtFAFZlKnBpKmlXK57ioakYUUUUAFFFFACqNzAVZqKFeC1S00AUUUUxBRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQBbh/wBUtPqOH/VLUlSMKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACmTf6pqfUc3+qagCrRRRVCCiiigAooooAKKKKACiiigAooooAKKKKACiiigAqsw2sRVmopl4DUmBFRRRSGFKBk4pKfEMvn0oAmAwAKWiiqEFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQBah/1S1JUcP+qWpKkYUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFRzf6pqkqOb/VNQBVoooqhBRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUhGQRS0UAViMHBpKfKMPn1plSMKmiGFz61DVkDAApoBaKKKYgooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKALUP+qWpKjh/1S1JUjCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAqOb/VNUlRzf6pqAKtFFFUIKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigCOUZXPpUNWSMgiq1JjHIMuKsVDCPmJ9qmoQBRRRTEFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRQAScDrQBah/1S1JTY12oAe1OqRhRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAVHN/qmqSmyLuQgd6AKdFBBBwetFUIKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAqu4w5qxUMw+YH1pMY6H7pPvUlMi+4KfQIKKKKYBRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAVPbp1Y/QVBVuIYiWkwH0UUUhhRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAQXCdGH0NQVblGYmqpTQgooopgFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABUcw+UH3qSmS/cNIBy/cH0paKKYBRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFW4/9Wv0qpVuP/Vr9KTAfRRRSGFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAyT/Vt9KqVbk/1bfSqlNCCiiimAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFI33D9KWigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAq3H/q1+lVKtx/6tfpSYD6KKKQwooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAGSf6tvpVSrcn+rb6VUpoQUUUUwCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKtxHMS1Uqe3fqp+opMCeiiikMKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigBkpxE1VKnuH6KPqagpoQUUUUwCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKRfuD6UtABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFAJByOtFFAFqKUPweGqSqcR2yKauUhhRRRSAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKiklC8Dk024c5Cg/WoKYASScnrRRRTEFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUE4GaKRvuH6UANi+4KfUcJ+Uj3qSkAUUUUwCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKuI25Aap1PbN1X8aTAnooopDCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKinbamO5oAru25ifWkooqhBRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABTJfuGn1HMflA96QDYT8xHrU1V0OHFWKEMKKKKYgooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAClRtrA+lJRQBdByMilqG3fK7T1FTVIwooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACqkr73J7dqmnfamB1NVqaEFFFFMAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAqGY/MB6VNVdzlzSYxtWQcgGq1TRHK49KEBJRRRTEFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAKjFWBFXAQwBHQ1SqaB8Haeh6UmBYooopDCiiigAooooAKKKKACiiigAooooAKKKKACkJAGT0FLVeeTJ2joOtAEbsXYk02iiqEFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFACE4BPpVappThcetQ0mMKfEcPj1plFIC1RSA5ANLVCCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigC1C+9eeo61JUFt/F+FT1IwooooAKKKKACiiigAooooAKKKKACiiigCKaTYMDqarVLc/fH0qKmIKKKKYBRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRSE4BNAEMpy+PSmUUVIwooooAlhPUVLVZTtYGrNNAFFFFMQUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQBPbfxfhU9QW38X4VPSGFFFFIAooooAKKKKACiiigAooooAKKKKAK1z98fSoqlufvj6VFTEFFFFMAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAqOZuAtSVWY7mJpMYlFFFIAooooAKmibK47ioaVTtYGgCzRSA5GaWqEFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAT238X4VPUNsPlJ9TU1SMKKKKACiiigAooooAKKKKACiiigAooooArXP3x9Kiqa5HKmoaYgooopgFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUE4GaAI5WwuPWoaVmLHJpKkYUUUUAFFFFABRRRQBLE38J/CparA4OanRty5poB1FFFMQUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFAGTgUdaswxbfmbr/ACpAPRdqAelOoopDCiiigAooooAKKKKACiiigAooooAKKKKAGSrvQjv1FVKvVXmi53L+IpoCGiiimIKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAqKVv4R+NPdtq5qAnJzSYxKKKKQBRRRQAUUUUAFFFFABTkbafam0UAWqKhifHympqYgooopgFFFFABRRRQAUUUUAFFFFABRRTkjZ+g49aAG1IkLPz0HvUyQqnPU+pqSlcBiRqnQc+tPoopDCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAjeFW5HBqu8bJ1HHqKuUUwKNFWXhVunBqB42TqOPUUCG0UUUwCiiigAooooAKKKKACiiigAooooAKKKilf+EfjSAY7bj7U2iikMKKKKACiiigAooooAKKKKACiiigAqaN8jB61DRQBaopqOGHvTqoQUUUUAFFFFABRRSqpY4AzQAlKqM5+UVMkAHL8+1TAADA4pXAjSBV5bk1LRRSGFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUARPCrdODUDxsnUceoq5RTAo0VZeFW6cGoHjZOo49RQIbRRRTAKKKKACiiigAoopruFHvQAkj7Rgdf5VBSk5OaSpGFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFACgkHIqdHDD3qvSgkHIoAs0U1HDD3p1UIKACTgDNSRwluTwKsKioPlFICFID1fj2qdVCjAGKWikMKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAInhVunBqB42TqOPUVcopgUaKsvCrdODUDoyHkfjQIbRRTXcKPemAO4Ue9QEknJoJJOTSVIwooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAq5aujHDn5u2e//ANeqdFAGxRVKC7x8svTH3v8AGrgIIyOQaAFooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigApCARg8igkAZPAFU57vPyxdMfe/wAKAEuSsbYRgT/KqxOetJRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABUsNw8XA5X0NRUUAakUySj5Tz6HrUlZAJU5UkH1FW4bz+GX/vqgC5RSAhhlSCPUUtABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFISFGWIA9TQAtRyzJEPmPPoOtV5rzHEQz/ALRqoSWOWJJ9TQBJNcPLweF9BUVFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAPjkaJsqce3Y1civEbAf5T69v8/5zVCigDXBBGQcg0tZSSvH9xiPbtVqO9B4kXB9R0/z+dAFuimo6uMqwP0p1ABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUU13VBlmA+tADqQkAZJwKqyXoHEa5Pqen+fyqq8ryffYn27UAXJbxF4T5j69v8AP+c1TkkaVssc+3YUyigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigBQSpypIPqKnS7lXrhh71XooAvpexnG4FT+Y/z+FTo6uMqwP0rJooA2KKzFuJV6SH8ef51Kl64+8objtxQBeoqqt8mPmRgfbn/AAp4uoSPvY/A0AT0UzzY/wDnon/fQpwORkcg0ALRRRQAUUUUAFFITgZPAFN82P8A56J/30KAH0VAbqED72fwNMN6mOFYn3oAtUVRa+fPyooHvz/hUTXErdXP4cfyoA0XdUGWYD61A95Gv3csfyqhRQBYe7lbphR7VASWOWJJ9TSUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAH/9k=";
        //if (imagenBase64.length()>50 || imagenBase64!=null){data=imagenBase64;}
        data = data.substring (data.indexOf (",") + 1);
        byte [] decodedString = Base64.decode (data.getBytes (), Base64.DEFAULT);
        //
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    private class Task extends AsyncTask<Void,Integer,Boolean> {
        private Dialog dialog;
        private boolean isOnline;
        @Override
        protected void onPreExecute() {
            dialog = new Dialog(BuscarLicencia.this);
            Check.inflaterLoading(dialog,BuscarLicencia.this,true);
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            //Verificar conexion a internet
            isOnline=Check.isOnline(BuscarLicencia.this);
            try { Thread.sleep(1000);  }
            catch (InterruptedException e){ e.printStackTrace(); }
            return true;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Check.inflaterLoading(dialog,BuscarLicencia.this,false);
            if(!isOnline){ Check.inflaterSinConexion(BuscarLicencia.this);}
            if (status == false){finish();}
        }
    }


}
