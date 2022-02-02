package com.example.bar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Database {

    private Connection connection;

    // private final String host = "ssprojectinstance.csv2nbvvgbcb.us-east-2.rds.amazonaws.com"  // For Amazon Postgresql
    private final String host = "10.2.205.28";  // For Google Cloud Postgresql
    private final String database = "bar";
    private final int port = 5432;
    private final String user = "bar";
    private final String pass = "";
    private String url = "jdbc:postgresql://%s:%d/%s";
    private boolean status;
    private Connection conn;

    public Database() {
        this.url = String.format(this.url, this.host, this.port, this.database);
        connect(this);
        //this.disconnect();
        System.out.println("connection status:" + status);
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    private void connect(Database db) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
                    db.setConn(connection);
                    status = true;
                    System.out.println("connected:" + status);
                } catch (Exception e) {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
    }

    public Connection getExtraConnection(){
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return c;
    }

    public Connection getConnection() throws SQLException {
        return this.connection;
    }
}