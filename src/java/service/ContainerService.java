package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bean.ContainerBean;
import dao.ExceptionDAO;
import dao.ContainerDAO;

public class ContainerService {

    // SERVICE 01 - loadContainerInfo()
    //              Executga o DAO que recupera as informações do container sel-
    //              ecionado.
    public ContainerBean loadContainerInfo(String containerAlias) throws SQLException {
        ContainerBean containerInfo = new ContainerBean();
        ContainerDAO containerDAO = new ContainerDAO();

        try {
            containerInfo = containerDAO.loadContainerInfo(containerAlias);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][CONTAINERSERVICE] ERRO: Não foi possível carregar"
                    + "as informações do container.");
        }
        
        return containerInfo;
    }

}
