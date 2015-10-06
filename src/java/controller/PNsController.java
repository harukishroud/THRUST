package controller;

import bean.AlternateBean;
import bean.ItemBean;
import dao.ExceptionDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import service.AlternateService;
import service.ItemService;

@ManagedBean(name = "pnsController")
@ViewScoped

public class PNsController {

    // VARIAVEIS
    // Armazena lista de PNs
    private List<ItemBean> PNs = new ArrayList<ItemBean>();
    // Armazena dados de PN para edição ou visualização
    private ItemBean PN = new ItemBean();
    // Armazena PN a ser adicionado
    private ItemBean newPN = new ItemBean();
    // Armazena Alternado a ser adicionado
    private AlternateBean newAlt = new AlternateBean();
    // Armazena resultado da verificação de existência do PN no banco de dados
    private boolean pnCheckStatus;
    // Armazena resultado da verificação de existência do PN Alternado no banco de dados
    private boolean alternatePNCheckStatus;

    // SERVIÇOS
    ItemService itemService = new ItemService();
    AlternateService alternateService = new AlternateService();

    // MÉTODOS
    // 01 - loadPNsList()
    //      Carrega todos os PNs existentes no banco de dados.
    public void loadPNsList() throws SQLException, ExceptionDAO {
        setPNs(itemService.loadPNs());
    }

    // 02 - addPN()
    //      Adiciona novo PN ao banco de dados.
    public void addPN() throws SQLException, ExceptionDAO {
        // Verifica existência do PN
        pnCheckStatus = itemService.checkPNExistance(newPN.getPn());
        // Adiciona PN caso o mesmo não exista no banco de dados
        if (pnCheckStatus == false) {
            itemService.addPN(newPN);
            System.out.println("[SYSTEM][PNSCONTROLLER] PN inserido com sucesso no banco de dados!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "O item de PN '" + newPN.getPn() + "' foi adicionado com sucesso!"));
        // Cancela o processo e exibe erro caso o PN já exista
        } else {
            System.out.println("[SYSTEM][PNSCONTROLLER] O PN informado já existe!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "O item de PN '" + newPN.getPn() + "' já existe no banco de dados."));
        }
        // Limpa Bean do PN e encerra o processo
        newPN = new ItemBean();
        System.out.println("[SYSTEM][PNSCONTROLLER] Processo finalizado.");
    }
    
    // 03 - setEditPN()
    //      Define o PN a ser editado antes do método 04 ser executado.    
    public void setEditPN(ItemBean selectedPN) {
        System.out.println("[SYSTEM][PNSCONTROLLER] PN selecionado para edição: '" + selectedPN.getPn() + "'.");
        PN = selectedPN;
    }
        
    // 04 - updatePN()
    //      Atualiza o PN selecionado.
    public void updatePN() {
        if (itemService.updatePN(PN) != null) {
            PN = new ItemBean();
            System.out.println("[SYSTEM][PNSCONTROLLER] PN atualizado com sucesso!");
        } else {
            System.out.println("[SYSTEM][PNSCONTROLLER] ERRO: Falha ao atualizar o PN!");
        }
    }
    
    // 05 - addAlternatePN()
    //      Adiciona PN alternado ao banco de dados.
    public void addAlternatePN() throws SQLException, ExceptionDAO {
        // Verifica existência do PN alternado no banco de dados
        alternatePNCheckStatus = alternateService.checkAlternatePNExistance(newAlt);
        // Adiciona PN Alternado caso o mesmo não exista no banco de dados para o PN informado
        if (alternatePNCheckStatus == false) {
            alternateService.addAlternatePN(newAlt);
            System.out.println("[SYSTEM][PNSCONTROLLER] PN Alternado inserido com sucesso no banco de dados!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "O PN Alternado '" + newAlt.getAlt_pn() + "' foi adicionado com sucesso!"));
        // Cancela o processo e exibe erro caso o PN Alternado já exista para o PN informado
        } else {
            System.out.println("[SYSTEM][PNSCONTROLLER] O PN informado já existe!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "O PN Alternado '" + newAlt.getAlt_pn() + "' já existe no banco de dados para o PN informado."));
        }
        // Limpa Bean do PN Alternado e encerra o processo
        newAlt = new AlternateBean();
        System.out.println("[SYSTEM][PNSCONTROLLER] Processo finalizado.");
        
    }
    

    // CONSTRUTOR
    public PNsController() throws SQLException, ExceptionDAO {
        loadPNsList();
    }

    // <editor-fold desc="GET and SET" defaultstate="collapsed">
    public List<ItemBean> getPNs() {
        return PNs;
    }

    public void setPNs(List<ItemBean> PNs) {
        this.PNs = PNs;
    }
    public AlternateBean getNewAlt() {
        return newAlt;
    }

    public boolean isAlternatePNCheckStatus() {
        return alternatePNCheckStatus;
    }

    public void setAlternatePNCheckStatus(boolean alternatePNCheckStatus) {
        this.alternatePNCheckStatus = alternatePNCheckStatus;
    }

    public void setNewAlt(AlternateBean newAlt) {
        this.newAlt = newAlt;
    }
    
    
    public ItemBean getPN() {
        return PN;
    }

    public void setPN(ItemBean PN) {
        this.PN = PN;
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
