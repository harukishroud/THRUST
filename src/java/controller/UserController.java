package controller;

import bean.SessionBean;
import bean.UserBean;
import dao.ExceptionDAO;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import service.SessionService;
import service.UserService;

@ManagedBean(name = "userController")
@ViewScoped

public class UserController {

    // VARIÁVEIS
    /* Login */
    private String username;
    private String password;

    /* HTTP Session */
    private HttpSession session;

    // BEANS
    private UserBean userBean = new UserBean();
    private SessionBean sessionBean = new SessionBean();
    /* Armazena dados o usuário atual */
    private UserBean currentUser = new UserBean();
    /* Armazena dados da session atual */
    private SessionBean currentSession = new SessionBean();

    // MÉTODOS
    // 01 - doLogin ()
    //      Realiza login do usuário.
    public void doLogin() throws ExceptionDAO, UnknownHostException, SQLException, IOException {
        /* Limpa 'currentUser' e 'currentSession' */
        currentUser = new UserBean();
        currentSession = new SessionBean();

        /* Inicia Services */
        UserService userService = new UserService();
        SessionService sessionService = new SessionService();

        /* Inicia Session */
        FacesContext ctx = FacesContext.getCurrentInstance();
        session = (HttpSession) ctx.getExternalContext().getSession(false);

        /* Verifica usuário e guarda no Bean 'currentUser' */
        currentUser = userService.checkUserLogin(username, password);

        /* Valida dados do usuário */
        if (currentUser.getUsername() != null) {
            /* Define atributos do usuário */
            session.setAttribute("currentActiveUser", currentUser.getUsername());
            session.setAttribute("currentActiveUserID", currentUser.getId());
            session.setAttribute("currentActiveUserAccessLevel", currentUser.getAccesslevel());
            System.out.println("[SYSTEM][USERCONTROLLER] Login realizado com sucesso!");
            /* Verifica existência de session em aberto para o usuário */
            boolean checkStatus = sessionService.checkForOpenSession(currentUser.getId());
            
            /* Caso não exista session em aberto */
            if (checkStatus == false) {
                System.out.println("[SYSTEM][USERCONTROLLER] Criando nova session...");
                /* Prepara dados para nova session */
                currentSession.setStatus(false);
                currentSession.setUser_id(currentUser.getId());
                currentSession.setStartTime(new Date());
                /* Cria nova session no banco de dados e substitui dados do atual 'currentSession' pelo atualizado com SESSIONID */
                currentSession = sessionService.newSession(currentSession);
                /* Define atributos de session */
                session.setAttribute("currentSessionID", currentSession.getSession_id());
                /* Redireciona para 'index.xhtml' */
                FacesContext.getCurrentInstance().getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/index.xhtml");                
                System.out.println("[SYSTEM][USERCONTROLLER] Nova session criada com sucesso!");
            
            /* Caso não exista session em aberto */
            } else {
                System.out.println("[SYSTEM][USERCONTROLLER] Session em aberto encontrada! Recuperando dados...");
                /* Carrega dados da session em aberto */
                currentSession = sessionService.loadOpenSession(currentUser.getId());
                /* Define atributos de session */
                session.setAttribute("currentSessionID", currentSession.getSession_id());
                /* Redireciona para 'index.xhtml' */
                FacesContext.getCurrentInstance().getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/index.xhtml");
                System.out.println("[SYSTEM][USERCONTROLLER] Session recuperada com sucesso!");
            }

        } else {
            /* Se usuário não encontrado ou inválido */
            currentUser = new UserBean();
            currentSession = new SessionBean();
            System.out.println("[SYSTEM][USERCONTROLLER] ERRO: Usuário não encontrado. Verifique os dados de acesso.");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Usuário não encontrado! Verifique seus dados de acesso."));
        }
    }

    // CONSTRUTOR
    public UserController() {

    }

    // <editor-fold desc="GET and SET" defaultstate="collapsed">
    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public UserBean getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserBean currentUser) {
        this.currentUser = currentUser;
    }

    public SessionBean getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(SessionBean currentSession) {
        this.currentSession = currentSession;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    // </editor-fold>

}
