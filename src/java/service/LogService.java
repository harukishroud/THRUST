package service;

import bean.LogBean;
import dao.ExceptionDAO;
import dao.LogDAO;
import java.sql.SQLException;

public class LogService {

    // SERVICE 01 - newLog()
    //              Executa DAO responsável pelo registro de novo LOG no banco -
    //              de dados.
    public void newLog(LogBean log) throws SQLException, ExceptionDAO {
        LogDAO logDAO = new LogDAO();

        try {
            logDAO.newLog(log);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][LOGSERVICE] ERRO: Não foi possível registrar LOG!");
        }

    }

}
