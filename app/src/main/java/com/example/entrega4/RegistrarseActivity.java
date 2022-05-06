package com.example.entrega4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarseActivity extends AppCompatActivity {

    private EditText firstname,lastname,email,pass,confirm_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        confirm_pass = (EditText) findViewById(R.id.confirm_pass);
    }
    public boolean buscar(int token){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String fname = firstname.getText().toString();
        String lname = lastname.getText().toString();
        String nombre = fname + " " + lname;
        Boolean encontrado = false;


        Cursor fila = BaseDeDatos.rawQuery("select token from usuarios where token ="+token,null);
        if(fila.moveToFirst()){
            encontrado = true;
            BaseDeDatos.close();
        }else{
            Toast.makeText(this, "No se encontró este usuario",Toast.LENGTH_SHORT).show();
            BaseDeDatos.close();
        }
        return encontrado;

    }
    public int token = 0;
    //metodo para registrar usuario, crearemos un token para poder registrarlo en la BD
    public void registar(View view) {
        Intent toLogin = new Intent(this,LoginActivity.class);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        //abrir base de datos en modo L/E
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String fname = firstname.getText().toString();
        String lname = lastname.getText().toString();
        String mail = email.getText().toString();
        String pass1 = pass.getText().toString();
        String pass2 = confirm_pass.getText().toString();
        String nombre = fname + " " + lname;
        if(!fname.isEmpty()&&!lname.isEmpty()&&!mail.isEmpty()&&!pass1.isEmpty()&&!pass2.isEmpty()){

            if(pass1.equals(pass2)){
                ContentValues registro = new ContentValues();
                registro.put("token",token);
                registro.put("nombre",nombre);
                registro.put("email",mail);
                registro.put("password",pass1);
                //insertar en la tabla
                BaseDeDatos.insert("usuarios",null, registro);
                BaseDeDatos.close();
                firstname.setText(" ");
                lastname.setText(" ");
                email.setText(" ");
                confirm_pass.setText(" ");
                pass.setText(" ");
                token +=1;
                Toast.makeText(this,"Registro correcto",Toast.LENGTH_SHORT).show();
                startActivity(toLogin);
            }else{
                Toast.makeText(this,"Contraseña no coincide",Toast.LENGTH_SHORT).show();
            }


        }

    }
}