package controller;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "mainController")
@ViewScoped

public class MainController {
    
    // VARIÁVEIS
    /* HTTP Session */
    private HttpSession session;
    
    /* Armazena usuário atual */
    private String currentUser;
    
    // MÉTODOS
    
    // 01 - goTo()
    //      Navega entre páginas.
    public void goTo(String page) throws IOException {
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesContext.getCurrentInstance().getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + page);
    }
       
       
    public MainController() {   
        /* Recupera Session */
        FacesContext ctx = FacesContext.getCurrentInstance();
        session = (HttpSession) ctx.getExternalContext().getSession(true); 
        
        /* Carrega usuário atual */
        currentUser = (String) session.getAttribute("currentActiveUser");
    }

    // <editor-fold desc="GET and SET" defaultstate="collapsed">
    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
    // </editor-fold>

    
}
