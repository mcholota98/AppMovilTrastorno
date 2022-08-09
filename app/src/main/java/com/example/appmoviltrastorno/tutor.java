package com.example.appmoviltrastorno;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmoviltrastorno.fragments.WebServices.ServicioTask;

import org.json.JSONObject;

import java.util.Calendar;

public class tutor extends AppCompatActivity {

    private ProgressDialog progDailog;
    private TextView txtnombre;
    private TextView txtapellido;
    private TextView txtcedula;
    private TextView txtfecha_nacimiento;
    private TextView txtuser;
    private TextView txtcontrasena;
    private TextView txtconfirmar;
    ImageView imagen;
    EditText etDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);
        this.txtnombre = (TextView) findViewById(R.id.nombres);
        this.txtapellido = (TextView) findViewById(R.id.apellidos);
        this.txtcedula = (TextView) findViewById(R.id.cedula);
        this.txtuser = (TextView) findViewById(R.id.usuario);
        this.txtcontrasena = (TextView) findViewById(R.id.txt_contras);
        this.txtconfirmar = (TextView) findViewById(R.id.confirmarcontra);

        imagen=(ImageView) findViewById(R.id.imagenUser);
        etDate=findViewById(R.id.txtCalendar);
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog= new DatePickerDialog(
                        tutor.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        etDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }

    public void cargarImg(View view){
        Intent in=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        in.setType("image/");
        startActivityForResult(in.createChooser(in,"Select the Application"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path=data.getData();
            imagen.setImageURI(path);
        }
    }

    public void registrar(View view) {
        try {
            JSONObject json_mensaje = new JSONObject();
            json_mensaje.put("nom_usuario", txtnombre.getText());
            json_mensaje.put("clave", txtcontrasena.getText());
            json_mensaje.put("persona__nombres", txtcontrasena.getText());
            json_mensaje.put("persona__apellidos", txtapellido.getText());
            json_mensaje.put("persona__cedula", txtcedula.getText());
            json_mensaje.put("persona__fecha_nacimiento", etDate.getText());
            json_mensaje.put("persona__foto_perfil", txtcedula.getText());
            //ServicioTask servicioTask = new ServicioTask(this, "POST","https://webservicestrastorno.herokuapp.com/persona/cuidador/", json_mensaje.toString(), this);
            txtnombre.setText("");
            txtcontrasena.setText("");
            txtcontrasena.setText("");
            txtapellido.setText("");
            txtcedula.setText("");
            etDate.setText("");
            txtconfirmar.setText("");
            //servicioTask.execute();
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
}