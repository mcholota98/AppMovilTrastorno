package com.example.appmoviltrastorno;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmoviltrastorno.fragments.WebServices.Asynchtask;
import com.example.appmoviltrastorno.fragments.WebServices.ServicioTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    private TextView txtUsuario;
    private TextView txtClave;
    private ProgressDialog progDailog;
    public static JSONObject usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.txtUsuario = (TextView) findViewById(R.id.txt_usuario);
        this.txtClave = (TextView) findViewById(R.id.txt_contras);
    }
    public void login(View view) {
        //Intent newActivity = new Intent(MainActivity.this, menu.class);
        //startActivity(newActivity);
        try {
            JSONObject json_mensaje = new JSONObject();
            json_mensaje.put("usuario", txtUsuario.getText());
            json_mensaje.put("clave", txtClave.getText());
            ServicioTask servicioTask = new ServicioTask(this, "POST","https://wssecurity.herokuapp.com/api-usuario/login/", json_mensaje.toString(), this);
            txtUsuario.setText("");
            txtClave.setText("");
            servicioTask.execute();
            progDailog = new ProgressDialog(this);
            progDailog.setTitle("Verificando usuario");
            progDailog.setMessage("por favor, espere...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public static JSONObject getUsuario() {
        return usuario;
    }

    public void setUsuario(JSONObject usuario) {
        this.usuario = usuario;
    }

    @Override
    public void processFinish(String result) throws JSONException {
        try {
            JSONObject json_response = new JSONObject(result);
            progDailog.dismiss();
            if(json_response.has("usuario")){
                Intent newActivity = new Intent(MainActivity.this, menu.class);
                JSONArray json_array = json_response.getJSONArray("usuario");
                this.setUsuario(json_array.getJSONObject(0));
                Toast.makeText(this, "Welcome " + getUsuario().get("nombre").toString(), Toast.LENGTH_LONG).show();
                startActivity(newActivity);
            }else{
                Toast.makeText(this, json_response.get("mensaje").toString(), Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            progDailog.dismiss();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}