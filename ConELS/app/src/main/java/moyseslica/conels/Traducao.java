package moyseslica.conels;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Traducao extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_favoritos:
                    intent = new Intent(getBaseContext(),Favoritos.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_traduzir:

                    return true;
                case R.id.navigation_ultimos:
                    intent = new Intent(getBaseContext(),Ultimos.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };

    public void EsconderItens(){

        TextView tituloPagina = (TextView) findViewById(R.id.message);
        EditText texto = (EditText) findViewById(R.id.editText);
        Button botao = (Button) findViewById(R.id.button2);
        ImageView imagem = (ImageView) findViewById(R.id.imageView);
        ProgressBar loading = (ProgressBar) findViewById(R.id.loading);

        tituloPagina.setVisibility(View.GONE);
        texto.setVisibility(View.GONE);
        botao.setVisibility(View.GONE);
        imagem.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

    }

    public void MostrarItens(){

        TextView tituloPagina = (TextView) findViewById(R.id.message);
        EditText texto = (EditText) findViewById(R.id.editText);
        Button botao = (Button) findViewById(R.id.button2);
        ImageView imagem = (ImageView) findViewById(R.id.imageView);
        ProgressBar loading = (ProgressBar) findViewById(R.id.loading);

        tituloPagina.setVisibility(View.VISIBLE);
        texto.setVisibility(View.VISIBLE);
        botao.setVisibility(View.VISIBLE);
        imagem.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);

    }

   @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traducao);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_traduzir);

        EsconderItens();

        MostrarItens();



        /*AQUI SE VERIFICA A ULTIMA VERSÃO DO BANCO DE IMAGENS*/

       EsconderItens();

       StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://monitoriacastanhal.ufpa.br/iuri.php",
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       alertaBasico(response);
                       MostrarItens();
                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       alertaBasico(error.toString()+"-"+error.networkResponse.toString());
                       MostrarItens();
                   }
               }){

               @Override
               protected Map<String,String> getParams(){
                   Map<String,String> params = new HashMap<String, String>();
                   params.put("Acao","VerificarVersao");
                   return params;
               }

               @Override
               public Map<String, String> getHeaders() throws AuthFailureError {
                   Map<String,String> params = new HashMap<String, String>();
                   params.put("Content-Type","application/x-www-form-urlencoded");
                   return params;
               }

       };

       RequestQueue requestQueue = Volley.newRequestQueue(this);
       requestQueue.add(stringRequest);


   }

    public void alertaBasico(String mensagem){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(Traducao.this);
        builder1.setMessage(mensagem);
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "Fechar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

}
