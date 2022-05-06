package com.example.entrega4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText user, password;
    private TextView myTextView;
    private CheckBox remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.nombre);
        password=(EditText) findViewById(R.id.contraseña);
        remember = (CheckBox)findViewById(R.id.remeber);

        myTextView = (TextView) findViewById(R.id.notmember);
        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = preferences.getString("checkbox","");

        if(checkbox.equals("true")){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }else if(checkbox.equals("false")){
            Toast.makeText(LoginActivity.this,"Porfavor, registrate",Toast.LENGTH_SHORT).show();
        }

        myTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrarse = new Intent(LoginActivity.this,RegistrarseActivity.class);
                startActivity(registrarse);
            }
        });
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("checkbox", "true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this,"checked",Toast.LENGTH_SHORT).show();
                    Log.e("","Se he seleccionado el checkbox");
                }else if(!compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("checkbox", "false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this,"unchecked",Toast.LENGTH_SHORT).show();
                    Log.e("","No se he seleccionado el checkbox");
                }
            }
        });
    }
    public void login(View view){
        //Intent principal = new Intent(this,MainActivity.class);
        Intent principal = new Intent(this,GetDatos.class);
        String name = user.getText().toString();
        String contra = password.getText().toString();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        if(!name.isEmpty() && !contra.isEmpty()){

            //startActivity(principal);
            //problema esta en la consulta,a partir del if
            Cursor fila = BaseDeDatos.rawQuery("select nombre,password from usuarios where nombre=?",new String[]{name},null);
            if(fila.moveToFirst()){
                if(name.equals(fila.getString(0))&&contra.equals(fila.getString(1))){
                    user.setText("");
                    password.setText("");
                    startActivity(principal);
                    BaseDeDatos.close();

                }else{
                    //  Toast.makeText(this,"Contraseña Incorrecta",Toast.LENGTH_SHORT).show();
                    BaseDeDatos.close();
                }
            }else{
                Toast.makeText(this,"Usuario no existe",Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }

        }else if(name.isEmpty() && !contra.isEmpty()){
            Toast.makeText(this,"Falta Nombre",Toast.LENGTH_SHORT).show();
        }else if(!name.isEmpty() && contra.isEmpty()){
            Toast.makeText(this,"Falta Contraseña",Toast.LENGTH_SHORT).show();
        }else if(name.isEmpty() && contra.isEmpty()){
            Toast.makeText(this,"Faltan Nombre y Contraseña",Toast.LENGTH_SHORT).show();
        }


    }
}