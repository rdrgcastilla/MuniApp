package pe.mpc.muni;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);

        goToFacebook();
        goToYoutube();
    }

    public void goToFacebook() {

        ImageButton btnFacebook = (ImageButton) findViewById(R.id.btnFacebook);

        btnFacebook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com/Municipalidad-Provincial-de-Ca%C3%B1ete-2019-2022-920695904797629/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    public void goToYoutube() {

        ImageButton btnYoutube = (ImageButton) findViewById(R.id.btnYoutube);

        btnYoutube.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.youtube.com/channel/UCMxGUnPQCFw-ixRH60Xx--A/featured");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
