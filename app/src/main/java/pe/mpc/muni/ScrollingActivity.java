package pe.mpc.muni;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.github.ybq.android.spinkit.style.ChasingDots;
import pe.mpc.muni.Model.Check;

public class ScrollingActivity extends AppCompatActivity {

    private WebView browser;
    private ChasingDots mChasingDotsDrawable;
    private  ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        // Definimos el webView
        browser=(WebView)findViewById(R.id.webView);
        imageView = (ImageView) findViewById(R.id.imgLoading);
        mChasingDotsDrawable = new ChasingDots();
        mChasingDotsDrawable.setColor(getResources().getColor(R.color.paletaAmarillo));
        mChasingDotsDrawable.setBounds(6,8,6,8);
        imageView.setImageDrawable(mChasingDotsDrawable);
        mChasingDotsDrawable.start();
        new Task().execute();

    }
    void cargarweb(){


        // ocultamos
        //Habilitamos JavaScript
        browser.getSettings().setJavaScriptEnabled(true);

        //Desabilitamos los botones de Zoom
        browser.getSettings().setBuiltInZoomControls(false);
        browser.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        // Cargamos la web
        browser.loadUrl("http://www.municanete.gob.pe:81/tramite/cInterfaseUsuario_SITD/consultaTramite.php");
        //Sincronizamos la barra de progreso de la web

        browser.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, final int progress) {

                if (progress == 100) {
                    try {
                        System.out.println("lala 0");
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    imageView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    @Override
    public void onBackPressed()
    {
        if (browser.canGoBack()){
            browser.goBack();
        }
        else{
            super.onBackPressed();
        }
    }

    private class Task extends AsyncTask<Void,Integer,Boolean>{
        private boolean isOnline;

        @Override
        protected Boolean doInBackground(Void... voids) {
            //Verificar conexion a internet
            isOnline=Check.isOnline(ScrollingActivity.this);
            try {
                System.out.println("lala 0");
                Thread.sleep(2000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            //POST
            if(!isOnline){
                Check.inflaterSinConexion(ScrollingActivity.this);
            }else{
                cargarweb();
            }
        }
    }

}