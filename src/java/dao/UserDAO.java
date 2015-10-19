package dao;

import bean.UserBean;
import db.ConnectionBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // DAO 01 - checkUserLogin()
    //          Verifica a existência do usuário no banco de dados.
    public UserBean checkUserLogin(String username, String password) throws ExceptionDAO, SQLException {
        UserBean currentUser = new UserBean();
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][USERDAO] Iniciando...");

        String sql = "SELECT * FROM users WHERE USERNAME = ? AND PASSWORD = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);

        System.out.println("[DATABASE][USERDAO] Buscando pelo usuário '" + username + "'"
                + "no banco de dados com a senha informada...");    

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            currentUser.setId(rs.getInt("ID"));
            currentUser.setAccesslevel(rs.getInt("ACCESSLEVEL"));
            currentUser.setUsername(rs.getString("USERNAME"));
            currentUser.setPassword(rs.getString("PASSWORD"));
            currentUser.setMail(rs.getString("MAIL"));

            System.out.println("[DATABASE][USERDAO] Usuário encontrado!");
            
        }

        rs.close();
        ps.close();
        conn.close();

        return currentUser;
    }

}
