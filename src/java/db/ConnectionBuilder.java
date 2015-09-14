/*
    THURST - DATABASE PACKAGE
    File: CONNECTIONBUILDER.JAVA | Last Major Update: 14.09.2015
    Developer: Kevin Raian
    IDINALOG REBORN © 2015
*/

package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionBuilder {

    public static Connection con = null;

    public Connection getConnection() {
        System.out.println("[DATABASE] Conectando ao banco de dados...");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrust_db", "thrustadmin", "478403");
            System.out.println("[DATABASE] Conectado.");
        } catch (ClassNotFoundException ex) {
            System.out.println("[DATABASE] ERRO: Classe não encontrada, adicione o driver correspondente às bibliotecas do sistema.");
            Logger.getLogger(ConnectionBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }

        return con;
    }

    public static void main(String[] args) {
        ConnectionBuilder conexao = new ConnectionBuilder();
        conexao.getConnection();
    }

}
