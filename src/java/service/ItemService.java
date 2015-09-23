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

    // SERVICE 02 - checkItemExistance()
    //              Executa o DAO que busca pela existência do PN informado no -
    //              banco de dados.
    public boolean checkItemExistance(String pn) throws SQLException {
        boolean checkStatus = false;
        ItemDAO itemDAO = new ItemDAO();

        try {
            checkStatus = itemDAO.checkItemExistance(pn);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][ITEMSERVICE] ERRO: Não foi possível executar"
                    + "a checagem do PN informado.");
        }

        return checkStatus;
    }

    // SERVICE 03 - addNewItem()
    //              Executa o DAO responsável por adicionar novo item ao banco.
    public void addNewItem(ItemBean newItem) throws SQLException {
        ItemDAO itemDAO = new ItemDAO();
        
        try {
            itemDAO.addNewItem(newItem);
            System.out.println("[DATABASE][ITEMSERVICE] PN '" + newItem.getPn() + "' adicionado com succeso ao banco de dados!");
        } catch (SQLException ex) {
            System.out.println("[DATABASE][ITEMSERVICE] ERRO: Não foi possível adicionar"
                    + "o PN informado ao banco de dados.");
        }
    }
}
