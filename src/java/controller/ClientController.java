package controller;

import bean.ClientBean;
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
import service.ClientService;
import service.LogService;

@ManagedBean(name = "clientController")
@ViewScoped

public class ClientController {

    // VARIAVEIS GERAIS
    /* HTTP Session */
    private HttpSession session;

    // BEANS
    /* Armazena detalhes de LOG */
    private LogBean log = new LogBean();
    /* Armazena cliente para função */
    private ClientBean client = new ClientBean();
    /* Armazena dado de novo cliente */
    private ClientBean newClient = new ClientBean();

    // LISTAS
    /* Armazena lista de clientes */
    private List<ClientBean> clients = new ArrayList<ClientBean>();

    // SERVIÇOS
    /* Inicia serviços */
    private ClientService clientService = new ClientService();
    private LogService logService = new LogService();
    
    // MÉTODOS
    
    ////////////////////////////////////////////////////////////////////////////
    // 01 - loadAllClients()
    //      Carrega todos os clientes existentes no banco de dados.
    public void loadAllClients() throws SQLException, ExceptionDAO {
        System.out.println("[CONTROLLER][CLIENT][loadAllClients] Carregando lista de clientes...");
        clients = clientService.loadAllClients();
        ////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////
    // 02 - setUpdateClient()
    //      Define o cliente a ser atualizado ao iniciar função.
    public void setUpdateClient(ClientBean selectedClient) {
        System.out.println("[CONTROLLER][CLIENT][setUpdateClient] Cliente '" + selectedClient.getCompany() + "' selecionado para edição.");
        client = selectedClient;
        ////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////
    // 03 - updateClient()
    //      Atualiza dados do cliente informado no banco de dados.
    public void updateClient() throws ExceptionDAO, SQLException {
        if (clientService.updateClient(client) != null) {
            /* Registra LOG */
            newLog("Alteração", "Alteração do cliente de ID " + client.getId() + ".", "Nome: " + client.getCompany() + " | "
                    + "Endereço: " + client.getAddress() + " | Cidade: " + client.getCity() + " | Estado: " + client.getState()
                    + " | Bairro: " + client.getDistrict() + " | Complemento: " + client.getComp() + " | País: " + client.getCountry()
                    + " | Nome Contato: " + client.getContact_name() + " | Número Contato: " + client.getContact_no() + " |"
                    + " CPNJ: " + client.getCnpj() + " | Inscrição Estadual: " + client.getInsc_est() + " | Inscrição Muncipal: "
                    + client.getInsc_mun() + " | Email Contato: " + client.getContact_mail() + " | OBS: " + client.getObs() +".");

            System.out.println("[CONTROLLER][CLIENT][updateClient] Cliente '" + client.getCompany() + "' atualizado com sucesso!");

            /* Limpa bean 'client' para novo uso */
            client = new ClientBean();
        } else {
            System.out.println("[CONTROLLER][CLIENT][updateClient] ERRO: Falha ao atualizar cliente '" + client.getCompany() + "'.");
        }
        ////////////////////////////////////////////////////////////////////////
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // 04 - newClient()
    //      Cadastra novo cliente no banco de dados.
    public void newClient() throws SQLException, ExceptionDAO {
        clientService.newClient(newClient);
        /* Registra LOG */
        newLog("Inserção","Cadastro do cliente '" + newClient.getCompany() + "'.","Nome: " + newClient.getCompany() + " | "
                    + "Endereço: " + newClient.getAddress() + " | Cidade: " + newClient.getCity() + " | Estado: " + newClient.getState()
                    + " | Bairro: " + newClient.getDistrict() + " | Complemento: " + newClient.getComp() + " | País: " + newClient.getCountry()
                    + " | Nome Contato: " + newClient.getContact_name() + " | Número Contato: " + newClient.getContact_no() + " |"
                    + " CPNJ: " + newClient.getCnpj() + " | Inscrição Estadual: " + newClient.getInsc_est() + " | Inscrição Muncipal: "
                    + newClient.getInsc_mun() + " | Email Contato: " + newClient.getContact_mail() + " | OBS: " + newClient.getObs() +".");
        
        System.out.println("[CONTROLLER][CLIENT][newClient] Cliente '" + newClient.getCompany() + "' cadastrado com sucesso!");
        /* Feedback */
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "O Cliente '" + newClient.getCompany() + "' foi cadastrado com sucesso!"));
        /* Limpa bean 'newClient' para novo uso */
        newClient = new ClientBean();
        ////////////////////////////////////////////////////////////////////////
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // 05 - removeClient()
    //      Remove cliente informado do banco de dados permanentemente.
    public void removeClient() throws SQLException, ExceptionDAO {
        clientService.removeClient(client.getId());
        /* Registra LOG */
        newLog("Exclusão","Exclusão do cliente '" + client.getCompany() + "'.","Nome: " + client.getCompany() + " | "
                    + "Endereço: " + client.getAddress() + " | Cidade: " + client.getCity() + " | Estado: " + client.getState()
                    + " | Bairro: " + client.getDistrict() + " | Complemento: " + client.getComp() + " | País: " + client.getCountry()
                    + " | Nome Contato: " + client.getContact_name() + " | Número Contato: " + client.getContact_no() + " |"
                    + " CPNJ: " + client.getCnpj() + " | Inscrição Estadual: " + client.getInsc_est() + " | Inscrição Muncipal: "
                    + client.getInsc_mun() + " | Email Contato: " + client.getContact_mail() + " | OBS: " + client.getObs() +".");
    
        System.out.println("[CONTROLLER][CLIENT][removeClient] Cliente '" + client.getCompany() + "' removido com sucesso!");
        /* Limpa bean 'client' para novo uso */
        client = new ClientBean();
        ////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////
    // ## - newLog()
    //      Insere um novo registro de atividade no banco de dados.
    public void newLog(String type, String header, String detail) throws ExceptionDAO, SQLException {
        /* Prepara bean 'log' para novo registro */
        log = new LogBean();
        /* Define dados gerais do log */
        log.setSession_id((int) session.getAttribute("currentSessionID"));
        log.setUser_id((int) session.getAttribute("currentActiveUserID"));
        log.setTime(new Date());
        /* Define tipo, cabeçalho e detalhes do log */
        log.setType(type);
        log.setHeader(header);
        log.setDetails(detail);
        /* Insere log no banco de dados */
        logService.newLog(log);
    }

    // CONSTRUTOR
    public ClientController() throws SQLException, ExceptionDAO {

        /* Carrega lista de clientes */
        loadAllClients();
        
        /* Carrega dados da session */
        FacesContext ctx = FacesContext.getCurrentInstance();
        session = (HttpSession) ctx.getExternalContext().getSession(true);
    }

    

    // <editor-fold desc="GET and SET" defaultstate="collasped">
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

    public ClientBean getClient() {
        return client;
    }

    public void setClient(ClientBean client) {
        this.client = client;
    }

    public ClientBean getNewClient() {
        return newClient;
    }

    public void setNewClient(ClientBean newClient) {
        this.newClient = newClient;
    }

    public List<ClientBean> getClients() {
        return clients;
    }

    public void setClients(List<ClientBean> clients) {
        this.clients = clients;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }
    // </editor-fold>   

}
