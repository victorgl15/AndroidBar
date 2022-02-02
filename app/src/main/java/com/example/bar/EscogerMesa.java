package com.example.bar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EscogerMesa extends AppCompatActivity {

    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escoger_mesa);
        sp = findViewById(R.id.spinner);
        /*try {
            fillSpinner();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
    }
    
    public void fillSpinner() throws SQLException {
        Database db = new Database();
        Connection conn = db.getExtraConnection();
        Statement st = conn.createStatement();
        String query = "SELECT mesa_id FROM mesa";
        ResultSet rs = st.executeQuery(query);
        if(rs != null){
            rs.last();
            String[] values = new String[rs.getRow()];
            rs.first();
            int i = 0;
            while(rs.next()){
                values[i] = rs.getString(1);
                i++;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,values);
            sp.setAdapter(adapter);
        }
    }

    public void tomarNota(View v){
        Intent i = new Intent();
        startActivity(i);
    }

}