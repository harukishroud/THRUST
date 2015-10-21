package service;

import bean.LogBean;
import dao.ExceptionDAO;
import dao.LogDAO;
import java.sql.SQLException;

public class LogService {

    ////////////////////////////////////////////////////////////////////////////
    // 01 - newLog()
    //      @DAO
    //      Adiciona um novo registro de atividade no banco de dados.
    public void newLog(LogBean log) throws SQLException, ExceptionDAO {
        /* Inicia DAO */
        LogDAO logDAO = new LogDAO();

        try {
            /* Executa DAO 'newLog' */
            logDAO.newLog(log);
        } catch (SQLException ex) {
            /* Em caso de erros */
            System.out.println("[SERVICE][LOG][newLog] ERRO: Não foi possível registrar a atividade no banco de dados.");
        }
        
        ////////////////////////////////////////////////////////////////////////
    }

}
