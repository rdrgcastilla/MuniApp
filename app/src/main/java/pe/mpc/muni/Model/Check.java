package pe.mpc.muni.Model;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.ChasingDots;

import java.net.URL;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import pe.mpc.muni.R;

public class Check {

    public static Boolean isConnectd(Context context){
        boolean connected = false;
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            // Si alguna red tiene conexión, se devuelve true

            if (redes[i].getState() == NetworkInfo.State.CONNECTED && redes[i].isAvailable()) {
                connected = true;
            }
        }
        return connected;
    }
    public static  void inflaterSinConexion(final Context context){
        Button btnCerrar;
        final Dialog miDialogo;

        miDialogo = new Dialog(context);
        miDialogo.setContentView(R.layout.inflater_sin_conexion);
        btnCerrar=miDialogo.findViewById(R.id.btnCloseInflater);
        miDialogo.setCancelable(false);
        miDialogo.show();
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miDialogo.dismiss();
                ((Activity) context).finish();

            }
        });
    }
    public static  void inflaterAlerta(final Context context, int layout){
        Button btnCerrar;
        final Dialog miDialogo;

        miDialogo = new Dialog(context);
        miDialogo.setContentView(layout);
        btnCerrar=miDialogo.findViewById(R.id.btnCloseInflater);
        miDialogo.setCancelable(false);
        miDialogo.show();
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miDialogo.dismiss();
            }
        });
    }
    public static void inflaterLoading(Dialog miDialogo, Context context, boolean estado){

        if (estado){
            miDialogo.setContentView(R.layout.inflater_loading);
            miDialogo.setCancelable(false);
            ProgressBar progressBar = (ProgressBar)miDialogo.findViewById(R.id.spin_kit);
            //progressBar.setBackgroundColor(context.getResources().getColor(R.color.paletaAzul));
            ChasingDots chasingDots = new ChasingDots();
            chasingDots.setColor(context.getResources().getColor(R.color.paletaAmarillo));
            progressBar.setIndeterminateDrawable(chasingDots);
            /**SIN FONDO*/

            miDialogo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            miDialogo.show();
        }else{
            miDialogo.dismiss();
        }
    }
    public static boolean isOnline(Context context){
        boolean conStatus = false;
        try {
            URL u = new URL("https://www.google.com");
            HttpsURLConnection huc = (HttpsURLConnection) u.openConnection();
            huc.connect();
            return conStatus = true;
        } catch (Exception e) {
            return  conStatus = false;
        }
    }
    public static void menssage(String mensaje,Context context){
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();

    }
    public static boolean email(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }



}