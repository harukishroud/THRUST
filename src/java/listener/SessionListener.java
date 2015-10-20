package listener;

import dao.ExceptionDAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import service.SessionService;

public class SessionListener implements
        HttpSessionListener, Serializable {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("[SYSTEM][SESSIONLISTENER] Session iniciada '" + se.getSession().getId() + "'.");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Recupera session em aberto */
        HttpSession session = se.getSession();

        /* Recupera atributos da session */
        int currentActiveUserID = (int) session.getAttribute("currentActiveUserID");
        int currentSessionID = (int) session.getAttribute("currentSessionID");

        /* Define registro de encerramento da session para o banco de dados */
        Date endTime = new Date();

        /* Inicia Service */
        SessionService sessionService = new SessionService();

        try {
            /* Encerra session o banco de dados */
            sessionService.closeSession(currentActiveUserID, currentSessionID, endTime);
        } catch (SQLException ex) {
            System.out.println("[SYSTEM][SESSIONLISTENER] ERRO: Não foi possível encerrar a session de ID '" + currentSessionID + "' para o usuário de ID '" + currentActiveUserID + "'!");
        } catch (ExceptionDAO ex) {
            System.out.println("[SYSTEM][SESSIONLISTENER] ERRO: Não foi possível encerrar a session de ID '" + currentSessionID + "' para o usuário de ID '" + currentActiveUserID + "'!");
        }
        
        System.out.println("[SYSTEM][SESSIONLISTENER] Session '"
                + session.getId() + "' encerrada!");
        System.out.println("[SYSTEM][SESSIONLISTENER] Efetue o login novamente para utilizar o sistema.");
       
        
    }
}
