package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bean.ItemBean;
import dao.ExceptionDAO;
import dao.ItemDAO;
import db.sql.QueryBuilder;
import db.sql.QueryGender;
import db.sql.QueryOperation;
import db.sql.QueryType;

public class ItemService {

    // SERVICE 01 - loadPNDetails()
    //              Executa DAO que carrega detalhes do PN selecionado.
    public ItemBean loadPNDetails(String PN) throws SQLException {
        ItemBean pnDetails = new ItemBean();
        ItemDAO itemDAO = new ItemDAO();

        try {
            pnDetails = itemDAO.loadPNDetails(PN);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][ITEMSERVICE] ERRO: Não foi possível carregar"
                    + "detalhes do PN '" + PN + "'.");
        }

        return pnDetails;
    }

    // SERVICE 02 - checkPNExistance()
    //              Executa o DAO que busca pela existência do PN informado no -
    //              banco de dados.
    public boolean checkPNExistance(String PN) throws SQLException {
        boolean checkStatus = false;
        ItemDAO itemDAO = new ItemDAO();

        try {
            checkStatus = itemDAO.checkPNExistance(PN);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][ITEMSERVICE] ERRO: Não foi possível executar"
                    + "a verificação de existência do PN '" + PN + "'.");
        }

        return checkStatus;
    }

    // SERVICE 03 - addPN()
    //              Executa o DAO responsável por adicionar novo item ao banco.
    public void addPN(ItemBean newPN) throws SQLException {
        ItemDAO itemDAO = new ItemDAO();
        
        try {
            itemDAO.addPN(newPN);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][ITEMSERVICE] ERRO: Não foi possível adicionar"
                    + "o PN '" + newPN.getPn() + "' ao banco de dados.");
        }
    }
    
    // SERVICE 04 - loadPNs()
    //              Executa DAO responsável por carregar todos os PNs do banco -
    //              de dados.
    public List<ItemBean> loadPNs() throws SQLException, ExceptionDAO {
        List<ItemBean> PNs = new ArrayList<ItemBean>();
        ItemDAO itemDAO = new ItemDAO();
        
        try {
            PNs = itemDAO.loadPNs();
        } catch (SQLException ex) {
            System.out.println("[DATABASE][ITEMSERVICE] ERRO: Não foi possível carregar"
                    + "a lista de PNs.");
        }
        
        return PNs;
    }

    // SERVICE 05 - updatePN()
    //              Executa DAO que atualiza as informações do PN editado.
    public ItemBean updatePN(ItemBean updatedPN){
        ItemDAO itemDAO = new ItemDAO();
        
        try {
            return itemDAO.updatePN(updatedPN);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][ITEMSERVICE] ERRO: Não foi possível atualizar"
                    + "as informações do PN '" + updatedPN.getPn() + "'.");
            return null;
        }
    }

}
