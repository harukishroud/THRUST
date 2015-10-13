package controller;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@SessionScoped
@ManagedBean(name = "mainController")

public class MainController {
    
    private String username = "";
    private String userpassword = "";
    
    // MÉTODOS
    
    // 01 - goTo()
    //      Navega entre páginas.
    public void goTo(String page) throws IOException {
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesContext.getCurrentInstance().getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + page);
    }
    
    public MainController() {
        
    }
    
}
