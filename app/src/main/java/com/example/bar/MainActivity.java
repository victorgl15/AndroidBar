package com.example.bar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    EditText etusername,etpass;
    RadioButton rbremember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etusername=findViewById(R.id.etusername);
        etpass=findViewById(R.id.etpass);
        rbremember=findViewById(R.id.rbremember);
        String[] archivos = fileList();

        if (existe(archivos, "recuerdame.txt"))
            try {
                InputStreamReader archivo = new InputStreamReader(openFileInput("recuerdame.txt"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String[] info = linea.split("///");
                br.close();
                archivo.close();
                etusername.setText(info[0]);
                etpass.setText(info[1]);
            } catch (IOException e) {
            }


    }

    public void logIn(View v) throws SQLException {
        String username = etusername.getText().toString();
        String pass = etpass.getText().toString();
        Boolean remember = rbremember.isChecked();

        Database db = new Database();
        Connection conn = db.getExtraConnection();
        try {

            Statement st = conn.createStatement();
            String query = "SELECT username,passwd FROM usuario WHERE username='" + username + "'";
            ResultSet rs = st.executeQuery(query);
            if (rs.first() && pass.equals(rs.getString(2))) {
                if (remember) {
                    grabar(v, username, pass);
                }
                Intent i = new Intent(this, EscogerMesa.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Datos de inicio de sesion erroneos.", Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
            System.out.println("Exception occurred is "+e.getMessage());
        }
    }

    private boolean existe(String[] archivos, String archbusca) {
        for (int f = 0; f < archivos.length; f++)
            if (archbusca.equals(archivos[f]))
                return true;
        return false;
    }

    public void grabar(View v,String username,String pass) {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("recuerdame.txt", Activity.MODE_PRIVATE));
            archivo.write(username+"///"+pass);
            archivo.flush();
            archivo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}