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
    
    // SERVICE 01 - loadItemInfo()
    //              Executa DAO que carrega as informações do PN selecionado.
    public ItemBean loadItemInfo(String pn) throws SQLException {
        ItemBean itemInfo = new ItemBean();
        ItemDAO itemDAO = new ItemDAO();
        
        try {
            itemInfo = itemDAO.loadItemInfo(pn);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][ITEMSERVICE] ERRO: Não foi possível carregar"
                    + "as informações do PN.");
        }
        
        return itemInfo;
    }
}
