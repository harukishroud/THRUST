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
    
    // SERVICE 02 - checkAlternatePNExistance()
    //              Executa o DAO que busca pela existência do PN informado no -
    //              banco de dados.
    public boolean checkAlternatePNExistance(AlternateBean altPNInfo) throws SQLException {
        boolean checkStatus = false;
        AlternateDAO alternateDAO = new AlternateDAO();

        try {
            checkStatus = alternateDAO.checkAlternatePNExistance(altPNInfo);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][ALTERNATESERVICE] ERRO: Não foi possível executar"
                    + "a verificação de existência do PN Alternado'" + altPNInfo.getAlt_pn() + "'.");
        }

        return checkStatus;
    }
    
    // SERVICE 03 - addAlternatePN()
    //              Executa o DAO responsável por adicionar o novo Alternado ao
    //              banco de dados.
    public void addAlternatePN(AlternateBean newAltInfo) throws SQLException {
        AlternateDAO alternateDAO = new AlternateDAO();
        
        try {
            alternateDAO.addAlternatePN(newAltInfo);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][ITEMSERVICE] ERRO: Não foi possível adicionar"
                    + "o PN Alternado'" + newAltInfo.getAlt_pn() + "' ao banco de dados.");
        }
    }
}
