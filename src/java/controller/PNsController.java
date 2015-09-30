package controller;

import bean.ItemBean;
import dao.ExceptionDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import service.ItemService;

@ManagedBean(name = "pnsController")
@ViewScoped

public class PNsController {

    // VARIAVEIS
    // Armazena lista de PNs
    private List<ItemBean> PNs = new ArrayList<ItemBean>();
    // Armazena PN a ser adicionado
    private ItemBean newPN = new ItemBean();
    // Armazena resultado da verificação de existência do PN no banco de dados
    private boolean pnCheckStatus;

    // SERVIÇOS
    ItemService itemService = new ItemService();

    // MÉTODOS
    // 01 - loadItems()
    //      Carrega todos os items existentes no banco de dados.
    public void loadItems() throws SQLException, ExceptionDAO {
        setPNs(itemService.loadItems());
    }

    // 02 - addPN()
    //      Adiciona novo PN ao banco de dados.
    public void addPN() throws SQLException, ExceptionDAO {
        // Verifica existência do PN
        pnCheckStatus = itemService.checkItemExistance(newPN.getPn());
        if (pnCheckStatus == false) {
            itemService.addNewItem(newPN);
            System.out.println("[SYSTEM][PNSCONTROLLER] PN inserido com sucesso no banco de dados!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "O item de PN '" + newPN.getPn() + "' foi adicionado com sucesso!"));

        } else {
            System.out.println("[SYSTEM][PNSCONTROLLER] O PN informado já existe!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "O item de PN '" + newPN.getPn() + "' já existe no banco de dados."));
        }

        newPN = new ItemBean();
        System.out.println("[SYSTEM][PNSCONTROLLER] Processo finalizado.");

    }

    // CONSTRUTOR
    public PNsController() throws SQLException, ExceptionDAO {
        loadItems();
    }

    // <editor-fold desc="GET and SET" defaultstate="collapsed">
    public List<ItemBean> getPNs() {
        return PNs;
    }

    public void setPNs(List<ItemBean> PNs) {
        this.PNs = PNs;
    }

    public boolean isPnCheckStatus() {
        return pnCheckStatus;
    }

    public void setPnCheckStatus(boolean pnCheckStatus) {
        this.pnCheckStatus = pnCheckStatus;
    }

    public ItemBean getNewPN() {
        return newPN;
    }

    public void setNewPN(ItemBean newPN) {
        this.newPN = newPN;
    }

    // </editor-fold>
}
