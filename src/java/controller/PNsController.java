package controller;

import bean.AlternateBean;
import bean.ItemBean;
import bean.LogBean;
import dao.ExceptionDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import service.AlternateService;
import service.ItemService;
import service.LogService;

@ManagedBean(name = "pnsController")
@ViewScoped

public class PNsController {

    // VARIAVEIS
    // HTTP Session
    private HttpSession session;
    // Armazena lista de PNs
    private List<ItemBean> PNs = new ArrayList<ItemBean>();
    // Armazena detalhes de LOG
    private LogBean log = new LogBean();
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
    LogService logService = new LogService();

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
            /* Registra LOG */
            newLog("Inserção", "Cadastro do PN '" + newPN.getPn() + "'.");
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
    public void updatePN() throws ExceptionDAO, SQLException {
        if (itemService.updatePN(PN) != null) {
            /* Registra LOG */
            newLog("Alteração", "Alteração do PN '" + PN.getPn() + "'. Descrição: '" + PN.getDescription() + "' | Peso: '"
                    + PN.getWeight() + "' | Altura: '" + PN.getSize_h()+ "' | Largura: '" + PN.getSize_w()+ "' |"
                    + " Profundidade: '" + PN.getSize_d()+ "'.");            
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
            /* Registra LOG */
            newLog("Inserção", "Cadastro do PN Alternado'" + newAlt.getAlt_pn()+ "' para o PN '" + newAlt.getItems_pn() + "'.");  
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

    // 06 - newLog()
    //      Insere um novo registro de atividade (log) no banco de dados.
    public void newLog(String type, String detail) throws ExceptionDAO, SQLException {
        /* Prepara bean 'log' para novo registro */
        setLog(new LogBean());
        /* Define dados gerais do log */
        getLog().setSession_id((int) getSession().getAttribute("currentSessionID"));
        getLog().setUser_id((int) getSession().getAttribute("currentActiveUserID"));
        getLog().setTime(new Date());
        /* Define tipo e detalhes do log */
        getLog().setType(type);
        getLog().setDetails(detail);
        /* Insere log no banco de dados */
        logService.newLog(getLog());
    }

    // CONSTRUTOR
    public PNsController() throws SQLException, ExceptionDAO {

        // Executa e carrega tabela de PNs
        loadPNsList();

        // Carrega dados da session
        FacesContext ctx = FacesContext.getCurrentInstance();
        session = (HttpSession) ctx.getExternalContext().getSession(true);

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

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public LogBean getLog() {
        return log;
    }

    public void setLog(LogBean log) {
        this.log = log;
    }

    public ItemBean getNewPN() {
        return newPN;
    }

    public void setNewPN(ItemBean newPN) {
        this.newPN = newPN;
    }

    // </editor-fold>
}
