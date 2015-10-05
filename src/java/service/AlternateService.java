package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bean.AlternateBean;
import dao.ExceptionDAO;
import dao.AlternateDAO;

public class AlternateService {
    
    // SERVICE 01 - loadAlternatePNs()
    //              Executa DAO que pesquisa PNs alternados para o PN seleciona-
    //              ado.
    public List<AlternateBean> loadAlternatePNs(String PN) throws SQLException, ExceptionDAO {
        AlternateDAO alternateDAO = new AlternateDAO();
        List<AlternateBean> alternatePNs = new ArrayList<AlternateBean>();
        
        try {
            alternatePNs = alternateDAO.loadAlternatePNs(PN);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][ALTERNATESERVICE] ERRO: Não foi possível carregar"
                    + "as informações de PNs alternados para '" + PN + "'.");
        }
        
        return alternatePNs;
    }
}
