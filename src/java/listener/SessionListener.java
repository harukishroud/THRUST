package listener;

import bean.LogBean;
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
import service.LogService;
import service.SessionService;

public class SessionListener implements
        HttpSessionListener, Serializable {
    
    // VARIAVEIS    
    /* HTTP Session */
    private HttpSession session;
    /* Armazena detalhes de LOG */
    private LogBean log = new LogBean();
    
    // SERVIÇOS
    LogService logService = new LogService();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("[SYSTEM][SESSIONLISTENER] Session iniciada '" + se.getSession().getId() + "'.");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Recupera session em aberto */
        session = se.getSession();

        /* Verifica se existe usuário logado na session em aberto */
        if (session.getAttribute("currentActiveUserID") != null) {
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
                /* Registra LOG */
                newLog("Acesso", "Login encerrado. Seção encerrada.");
            } catch (SQLException ex) {
                System.out.println("[SYSTEM][SESSIONLISTENER] ERRO: Não foi possível encerrar a session de ID '" + currentSessionID + "' para o usuário de ID '" + currentActiveUserID + "'!");
            } catch (ExceptionDAO ex) {
                System.out.println("[SYSTEM][SESSIONLISTENER] ERRO: Não foi possível encerrar a session de ID '" + currentSessionID + "' para o usuário de ID '" + currentActiveUserID + "'!");
            }
        }

        System.out.println("[SYSTEM][SESSIONLISTENER] Session '"
                + session.getId() + "' encerrada!");
        System.out.println("[SYSTEM][SESSIONLISTENER] Efetue o login novamente para utilizar o sistema.");

    }
    
    // 01 - newLog()
    //      Insere um novo registro de atividade (log) no banco de dados.
    public void newLog(String type, String detail) throws ExceptionDAO, SQLException {
        /* Prepara bean 'log' para novo registro */
        log = new LogBean();
        /* Define dados gerais do log */
        log.setSession_id((int) session.getAttribute("currentSessionID"));
        log.setUser_id((int) session.getAttribute("currentActiveUserID"));
        log.setTime(new Date());
        /* Define tipo e detalhes do log */
        log.setType(type);
        log.setDetails(detail);
        /* Insere log no banco de dados */
        logService.newLog(log);
    }

    // <editor-fold desc="GET and SET" defaultstate="collapsed">
    public HttpSession getSession() {
        return session;
    }

    public LogBean getLog() {
        return log;
    }

    public void setLog(LogBean log) {
        this.log = log;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }
    // </editor-fold>
}
