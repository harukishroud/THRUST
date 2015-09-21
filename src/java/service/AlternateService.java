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
    
    // SERVICE 01 - loadAlternateItems()
    //              Executa DAO que pesquisa PNs alternados para o PN seleciona-
    //              ado.
    public List<AlternateBean> loadAlternateItems(String pn) throws SQLException, ExceptionDAO {
        AlternateDAO alternateDAO = new AlternateDAO();
        List<AlternateBean> alternateItems = new ArrayList<AlternateBean>();
        
        try {
            alternateItems = alternateDAO.loadAlternateItems(pn);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][CONTAINERSERVICE] ERRO: Não foi possível carregar"
                    + "as informações de PNs alternados.");
        }
        
        return alternateItems;
    }
}
