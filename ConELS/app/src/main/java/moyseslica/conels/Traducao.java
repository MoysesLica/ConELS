package moyseslica.conels;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Traducao extends AppCompatActivity {

    // Progress Dialog
    ProgressDialog mProgressDialog;

    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;

    // File url to download
    String URL;

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

        /*AQUI FAZ-SE A VERIFICAÇÃO DA CONEXÃO DE INTERNET DO USUÁRIO*/

       ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

       NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
       boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

       if(isConnected){

            /*AQUI SE VERIFICA A ULTIMA VERSÃO DO BANCO DE IMAGENS*/

           EsconderItens();

           StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://monitoriacastanhal.ufpa.br/iuri/iuri.php",
                   new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {

                           /*AQUI EU COMPARO A VERSÃO NA WEB COM A VERSÃO LOCAL*/

                           SharedPreferences sharedPref = Traducao.this.getPreferences(Context.MODE_PRIVATE);
                           int versao = sharedPref.getInt("VersaoDicionario", 0);

                           String[] linhas = response.split(System.getProperty("line.separator"));

                           try{

                               if(versao < Integer.parseInt(linhas[0])){

                                   confirmacaoBasica("Seu dicionário está desatualizado, deseja baixar um novo?", linhas[1]);

                               }

                           }catch(NumberFormatException e){

                               alertaBasico(e.toString());

                           }

                           MostrarItens();
                       }
                   },
                   new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           alertaBasico(error.toString());
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

    public void confirmacaoBasica(String mensagem, final String dados){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(Traducao.this);
        builder1.setMessage(mensagem);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Sim",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new DownloadFile().execute(dados.split(",")[0], dados.split(",")[1]);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Não",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    // DownloadFile AsyncTask
    private class DownloadFile extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create progress dialog
            mProgressDialog = new ProgressDialog(Traducao.this);
            // Set your progress dialog Title
            mProgressDialog.setTitle("Download Iniciado!");
            // Set your progress dialog Message
            mProgressDialog.setMessage("Baixando Arquivos, Por Favor Aguarde!");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // Show progress dialog
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... Url) {

            String diretorio = null;
            
            try {
                URL url = new URL(Url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                // Detect the file lenghth
                int fileLength = connection.getContentLength();

                // Locate storage location
                String filepath = Environment.getExternalStorageDirectory()
                        .getPath();

                // Download the file
                InputStream input = new BufferedInputStream(url.openStream());

                //Creating Folder if Not Exist

                File folder = new File(Environment.getExternalStorageDirectory() +
                        File.separator + "ConELS");
                boolean success = true;
                if (!folder.exists()) {
                    success = folder.mkdirs();
                }

                diretorio = filepath + File.separator + "ConELS"+ File.separator;

                // Save the downloaded file
                OutputStream output = new FileOutputStream(diretorio + Url[1]);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // Publish the progress
                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

                // Close connection
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            unpackZip(diretorio, Url[1]);

            File deleted = new File(diretorio, Url[1]);
            deleted.delete();

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // Update the progress dialog
            mProgressDialog.setProgress(progress[0]);
            // Dismiss the progress dialog
            if(progress[0] == 100){
                mProgressDialog.dismiss();
                alertaBasico("Dicionário Atualizado!");
                                
            }
        }
    }

    private boolean unpackZip(String path, String zipname)
    {
        InputStream is;
        ZipInputStream zis;
        try
        {
            String filename;
            is = new FileInputStream(path + zipname);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null)
            {
                // zapis do souboru
                filename = ze.getName();

                // Need to create directories if not exists, or
                // it will generate an Exception...
                if (ze.isDirectory()) {
                    File fmd = new File(path + filename);
                    fmd.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(path + filename);

                // cteni zipu a zapis
                while ((count = zis.read(buffer)) != -1)
                {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
