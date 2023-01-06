package pe.mpc.muni;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import pe.mpc.muni.Model.Check;

public class InfoActivity extends AppCompatActivity {

    private String url_fb, url_yb, url_web, url_comousar, url_terminos, url_encuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ImageButton facebook = findViewById(R.id.btn_facebook);
        ImageButton youtube = findViewById(R.id.btn_youtube);
        ImageButton web = findViewById(R.id.btn_web);
        ImageButton telefono = findViewById(R.id.btn_telefono);
        ImageView comousar = findViewById(R.id.comousar);
        ImageView terminos = findViewById(R.id.terminos);
        Button inicio = findViewById(R.id.btn_inicio);
        Button encuesta = findViewById(R.id.encuesta);
        ImageView help = findViewById(R.id.help);

        url_fb="https://www.facebook.com/profile.php?id=100064916730949";
        url_yb = "https://www.youtube.com/channel/UCMxGUnPQCFw-ixRH60Xx--A";
        url_web = "https://municanete.gob.pe";
        url_comousar = "https://drive.google.com/file/d/1PQkeEbyyTCLcRqXUd3rE-oSp3PNxkWFP/view?usp=sharing";
        url_terminos = "https://drive.google.com/file/d/1fhPgy-0xb8d31QEA73oAzDhhm2wuNvIt/view?usp=sharing";
        url_encuesta = "https://forms.office.com/r/b5TrFziusj";

        encuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url_encuesta);
                Intent intent = new Intent (Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check.inflaterAlerta(InfoActivity.this, R.layout.inflater_help);
            }
        });

        terminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url_terminos);
                Intent intent = new Intent (Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        comousar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url_comousar);
                Intent intent = new Intent (Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url_fb);
                Intent intent = new Intent (Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url_yb);
                Intent intent = new Intent (Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url_web);
                Intent intent = new Intent (Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:(01)5812583"));
                startActivity(i);
            }
        });

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}