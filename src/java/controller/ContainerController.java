package controller;

import bean.ContainerBean;
import dao.ExceptionDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.omnifaces.util.Ajax;
import org.primefaces.context.RequestContext;
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
    // Armazena dados de novo Container
    private ContainerBean newContainerBean = new ContainerBean();
    // Armazena resultado da verificação de existência do Container
    private boolean containerCheckStatus;

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
    public void setEditContainer(ContainerBean container) throws InterruptedException {
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

    // 04 - newContainer()
    //      Cria novo container no banco de dados.
    public void newContainer() throws SQLException, ExceptionDAO {
        // Verifica existência do Container
        containerCheckStatus = containerService.checkContainerExistance(newContainerBean.getAlias());
        // Adiciona Container caso o mesmo não exista no banco de dados
        if (containerCheckStatus == false) {
            containerService.newContainer(newContainerBean);
            System.out.println("[SYSTEM][CONTAINERCONTROLLER] Container inserido com sucesso no banco de dados!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "O Container '" + newContainerBean.getAlias() + "' foi adicionado com sucesso!"));
            // Cancela o processo e exibe erro caso já exista
        } else {
            System.out.println("[SYSTEM][CONTAINERCONTROLLER] O Container informado já existe!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "O Container '" + newContainerBean.getAlias() + "' já existe no banco de dados."));
        }
        // Limpa Bean de Novo Container e encerra o processo
        newContainerBean = new ContainerBean();
        System.out.println("[SYSTEM][CONTAINERCONTROLLER] Processo finalizado.");
    }

    // 05 - removeContainer()
    //      Remove o container selecionado do banco de dados.
    public void removeContainer() throws SQLException, ExceptionDAO {
        if (containerBean.getItemsTotal() == 0) {
            containerService.removeContainer(containerBean.getAlias());
            System.out.println("[SYSTEM][CONTAINERCONTROLLER] Container removido com sucesso do banco de dados!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "O Container '" + containerBean.getAlias() + "' foi removido permamentemente."));
            containerBean = new ContainerBean();
        } else {
            System.out.println("[SYSTEM][CONTAINERCONTROLLER] O Container informado possuí items e não pode ser removido!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "O Container '" + containerBean.getAlias() + "' não pode ser removido pois há "
                    + containerBean.getItemsTotal() + " items armazenados."));
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

    public ContainerBean getNewContainerBean() {
        return newContainerBean;
    }

    public void setNewContainerBean(ContainerBean newContainerBean) {
        this.newContainerBean = newContainerBean;
    }

    public boolean isContainerCheckStatus() {
        return containerCheckStatus;
    }

    public void setContainerCheckStatus(boolean containerCheckStatus) {
        this.containerCheckStatus = containerCheckStatus;
    }

    public List<SelectItem> getContainerTypes() {
        return containerTypes;
    }

    public void setContainerTypes(List<SelectItem> containerTypes) {
        this.containerTypes = containerTypes;
    }
    // </editor-fold>

}
