package service;

import bean.UserBean;
import dao.ExceptionDAO;
import dao.UserDAO;
import java.sql.SQLException;

public class UserService {
    
    // SERVICE 01 - checkUserLogin()
    //              Executa DAO responsável pela checagem do usuário.
    public UserBean checkUserLogin(String username, String password) throws SQLException, ExceptionDAO {
        UserBean currentUser = new UserBean();
        UserDAO userDAO = new UserDAO();
        
        try {
            currentUser = userDAO.checkUserLogin(username, password);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][USERSERVICE] ERRO: Não foi possível realizar"
                    + "a verificação do usuário '" + username + "'.");
        }
        
        return currentUser;
    }
    
}
