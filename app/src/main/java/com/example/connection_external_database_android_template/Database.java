package com.example.connection_external_database_android_template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Database extends Default implements Runnable {

    private Connection connection;
    private String host = "";
    private String db = "android";
    private int port = 2345;
    private String user = "android";
    private String password = "12345";
    private String url = "jdbc:postgresql://%s:%d/%s";

    public Database() {
        super();
        this.url = String.format(this.url,this.host, this.port, this.db);

        this.connect();
        this.disconnect();
    }

    @Override
    public void run() {
        try {
            Class.forName("seu driver");
            this.connection = DriverManager.getConnection(this.url,this.user,this.password);


        }catch (Exception e){
            this.mensagem = e.getMessage();
            this.status = false;
        }
    }

    private void connect(){
        Thread thread = new Thread(this);
        //start method run
        thread.start();

        //async and await
        try {
            thread.join();

        }catch (Exception e){
            this.mensagem = e.getMessage();
            this.status = false;
        }

    }

    private void disconnect(){
        if (this.connection != null){
            try {
                this.connection.close();
            }catch (Exception e){

            }finally {
                this.connection = null;
            }
        }
    }

    public ResultSet select(String query){
        this.connect();
        ResultSet resultSet = null;
        try {
            resultSet = new ExecuteDB(this.connection, query).execute().get();
        }catch (Exception e){
            this.mensagem = e.getMessage();
            this.status = false;
        }
        return resultSet;
    }

    public ResultSet execute(String query){
        this.connect();
        ResultSet resultSet = null;
        try {
            resultSet = new ExecuteDB(this.connection, query).execute().get();
        }catch (Exception e){
            this.mensagem = e.getMessage();
            this.status = false;
        }
        return resultSet;
    }
}
