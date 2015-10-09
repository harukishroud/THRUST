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
    // Armazena Cores de Containers
    private List<SelectItem> containerColors = new ArrayList<SelectItem>();
    // Armazena Tipos de Containers
    private List<SelectItem> containerTypes = new ArrayList<SelectItem>();
    // Armazena Container para função
    private ContainerBean containerBean = new ContainerBean();
    
    // SERVIÇOS
    ContainerService containerService = new ContainerService();
    
    
    // MÉTODOS
    // 01 - loadContainers()
    //      Carrega todos os containers existentes no banco de dados.
    public void loadContainers() throws SQLException, ExceptionDAO {
        setContainers(containerService.loadAllContainers());
    }
    
    // 02 - setEditContainer()
    //      Define o container a ser editado no inventário.
    public void setEditContainer(ContainerBean container) {
        System.out.println("[SYSTEM][CONTAINERCONTROLLER] Container selecionado para edição: '" + container.getAlias() + "'.");
        containerBean = container;
    }
    
    // 03 - updateContainer()
    //      Atualiza o container editado.
    public void updateContainer() {
        if (containerService.updateContainer(containerBean) != null) {
            containerBean = new ContainerBean();
            System.out.println("[SYSTEM][CONTAINERCONTROLLER] Container atualizado com sucesso!");
        } else {
            System.out.println("[SYSTEM][CONTAINERCONTROLLER] ERRO: Falha ao atualizar o Container!");
        }
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
        
        // Adiciona cores de containers à lista
        containerColors.add(new SelectItem("AMARELO"));
        containerColors.add(new SelectItem("AZUL"));
        containerColors.add(new SelectItem("AZUL CLARO"));
        containerColors.add(new SelectItem("AZUL ESCURO"));
        containerColors.add(new SelectItem("CINZA"));
        containerColors.add(new SelectItem("LARANJA"));
        containerColors.add(new SelectItem("MARROM"));
        containerColors.add(new SelectItem("PRETO"));
        containerColors.add(new SelectItem("ROXO"));
        containerColors.add(new SelectItem("VERDE"));
        containerColors.add(new SelectItem("VERMELHO"));
        
        
    }
    
    // <editor-fold desc="GET and SET" defaultstate="collapsed">
    public List<ContainerBean> getContainers() {
        return containers;
    }

    public void setContainers(List<ContainerBean> containers) {
        this.containers = containers;
    }

    public ContainerBean getContainerBean() {
        return containerBean;
    }

    public void setContainerBean(ContainerBean containerBean) {
        this.containerBean = containerBean;
    }

    public List<SelectItem> getContainerColors() {
        return containerColors;
    }

    public void setContainerColors(List<SelectItem> containerColors) {
        this.containerColors = containerColors;
    }

    public List<SelectItem> getContainerTypes() {
        return containerTypes;
    }

    public void setContainerTypes(List<SelectItem> containerTypes) {
        this.containerTypes = containerTypes;
    }
    // </editor-fold>
    
    
}
