package controller;

import bean.ContainerBean;
import dao.ExceptionDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import service.ContainerService;

@ManagedBean(name = "containerController")
@ViewScoped

public class ContainerController {
    
    // VARIAVEIS
    // Armazena Containers
    private List<ContainerBean> containers = new ArrayList<ContainerBean>();
    // Armazena Tipos de Containers
    private List<SelectItem> containerTypes = new ArrayList<SelectItem>();
    
    // SERVIÇOS
    ContainerService containerService = new ContainerService();
    
    
    // MÉTODOS
    // 01 - loadContainers()
    //      Carrega todos os containers existentes no banco de dados.
    public void loadContainers() throws SQLException, ExceptionDAO {
        setContainers(containerService.loadAllContainers());
    }
    
    // CONSTRUTOR
    public ContainerController() throws SQLException, ExceptionDAO {
        loadContainers();
        
        // Adiciona tipos de containers à lista
        containerTypes.add(new SelectItem(""));
        containerTypes.add(new SelectItem("CONTAINER PLÁSTICO"));
        containerTypes.add(new SelectItem("CAIXA DE PAPELÃO"));
        containerTypes.add(new SelectItem("SEPARADOR PLÁSTICO"));
        containerTypes.add(new SelectItem("PRATELEIRA"));
        containerTypes.add(new SelectItem("CAIXA ARQUIVO"));
        containerTypes.add(new SelectItem("OUTROS"));
    }
    

    public List<ContainerBean> getContainers() {
        return containers;
    }

    public void setContainers(List<ContainerBean> containers) {
        this.containers = containers;
    }

    public List<SelectItem> getContainerTypes() {
        return containerTypes;
    }

    public void setContainerTypes(List<SelectItem> containerTypes) {
        this.containerTypes = containerTypes;
    }
    
    
    
}
