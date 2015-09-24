package controller;

import bean.AlternateBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import dao.ExceptionDAO;
import bean.InventoryBean;
import bean.ContainerBean;
import bean.ItemBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.icefaces.ace.component.textentry.TextEntry;
import org.primefaces.context.RequestContext;
import service.InventoryService;
import service.ContainerService;
import service.ItemService;
import service.AlternateService;

@ManagedBean(name = "inventoryController")
@ViewScoped

public class InventoryController {

    // VARIAVEIS    
    // Total de itens no inventário
    private int inventoryTotal;
    // Total de itens em falta no inventário
    private int inventoryFaultTotal;
    // Armazena total de itens no container selecionado
    private int containerTotal;
    // Salva tipo de arquivo a ser exportado
    private String exportType;
    // Armazena container selecionado
    private String selectedContainer;
    // Armazena resultado da verificação de existência do PN no banco de dados
    private boolean itemCheckStatus;
    // Armazena resultado da verificação de existência do item no inventário
    private boolean inventoryItemCheckStatus;
    // Armazena item do inventário
    private InventoryBean inventoryBean = new InventoryBean();
    // Armazena item a ser adicionado no inventário
    private InventoryBean newInventoryItem = new InventoryBean();
    // Armazena informações do container selecionado
    private ContainerBean containerBean = new ContainerBean();
    // Armazena informações do PN selecionado
    private ItemBean itemBean = new ItemBean();
    // Armazena informações do PN a ser adicionado no banco de dados
    private ItemBean newItem = new ItemBean();
    // Armazena inventário
    private List<InventoryBean> inventory = new ArrayList<InventoryBean>();
    // Armazena inventário filtrado
    private List<InventoryBean> filteredInventory = new ArrayList<InventoryBean>();
    // Armazena containers existentes no inventário
    private List<InventoryBean> inventoryContainerList = new ArrayList<InventoryBean>();
    // Armazena proprietários existentes no inventário
    private List<InventoryBean> inventoryOwnerList = new ArrayList<InventoryBean>();
    // Armazena container com espaço disponível no inventário
    private List<ContainerBean> inventoryAvailableContainerList = new ArrayList<ContainerBean>();
    // Armazena PNs Alternados para o PN selecionado
    private List<AlternateBean> alternateItems = new ArrayList<AlternateBean>();
    // Lista de condições para filtro do inventário
    private List<SelectItem> conditionFilterOptions = new ArrayList<SelectItem>();
    // Lista de containers existentes no inventário para o filtro
    private List<SelectItem> containerFilterOptions = new ArrayList<SelectItem>();
    // Lista de proprietários existentes no inventário para o filtro
    private List<SelectItem> ownerFilterOptions = new ArrayList<SelectItem>();
    // Lista de containers com armazenamento disponível
    private List<SelectItem> availableContainers = new ArrayList<SelectItem>();

    // SERVIÇOS    
    InventoryService inventoryService = new InventoryService();
    ContainerService containerService = new ContainerService();
    ItemService itemService = new ItemService();
    AlternateService alternateService = new AlternateService();

    // MÉTODOS    
    // 01 - loadAllInventory()
    //      Carrega todos os itens e inventários existentes no banco de dados.
    public void loadAllInventory() throws SQLException, ExceptionDAO {
        inventory = inventoryService.loadInventory();
        inventoryFaultTotal = inventoryService.countInventoryFaultItens().size();
        inventoryTotal = inventory.size();
        
         // Carrega lista de containers existentes
        updateContainerList();

        // Carrega lista de proprietários existentes
        updateOwnerList();

        // Carrega lista de containers com armazenamento disponível
        updateAvailableContainerList();
    }

    // 02 - setEditInventoryItem()
    //      Define o item a ser editado no inventário.
    public void setEditInventoryItem(InventoryBean inventoryItem) {
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Item selecionado para edição: '" + inventoryItem.getPn() + "'.");
        inventoryBean = inventoryItem;
    }

    // 03 - updateInventoryItem()
    //      Atualiza o item no inventário.
    public void updateInventoryItem() {
        if (inventoryService.updateInventoryItem(inventoryBean) != null) {
            inventoryBean = new InventoryBean();
            System.out.println("[SYSTEM][INVENTORYCONTROLLER] Item atualizado com sucesso!");
        } else {
            System.out.println("[SYSTEM][INVENTORYCONTROLLER] ERRO: Falha ao atualizar item!");
        }
    }

    // 04 - updateContainerList()
    //      Executa a procura por containers existentes no inventário e atualiza
    //      a lista.
    public void updateContainerList() throws ExceptionDAO {
        setInventoryContainerList(inventoryService.createListInventoryContainers());

        // Limpa filtro de containers
        containerFilterOptions = new ArrayList<SelectItem>();

        // Fix que permite que todos os containers sejam exibidos
        containerFilterOptions.add(new SelectItem(""));

        // Atualiza opções do filtro        
        for (int i = 0; i < inventoryContainerList.size(); i++) {
            getContainerFilterOptions().add(new SelectItem(inventoryContainerList.get(i).getContainerAlias()));
        }

        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Lista de containers existentes carregada.");

    }

    // 05 - loadContainerDetails()
    //      Carrega os detalhes do container selecionado.
    public void loadContainerDetails(InventoryBean inventoryItem) throws SQLException, ExceptionDAO {
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Container selecionado para exibição de detalhes: '" + inventoryItem.getContainerAlias() + "'.");
        containerBean = containerService.loadContainerInfo(inventoryItem.getContainerAlias());
        containerTotal = containerService.countContainerTotalItens(inventoryItem.getContainerAlias());
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Dados do container carregados.");
    }

    // 06 - updateOwnerList()
    //      Executa a procura por proprietários existentes no inventário e atualiza
    //      a lista.
    public void updateOwnerList() throws ExceptionDAO {
        setInventoryOwnerList(inventoryService.createListInventoryOwners());

        // Limpa filtro de proprietários
        ownerFilterOptions = new ArrayList<SelectItem>();

        // Fix que permite que todos os proprietários sejam exibidos
        ownerFilterOptions.add(new SelectItem(""));

        // Atualiza opções do filtro        
        for (int i = 0; i < inventoryOwnerList.size(); i++) {
            getOwnerFilterOptions().add(new SelectItem(inventoryOwnerList.get(i).getFrom()));
        }

        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Lista de proprietários existentes carregada.");

    }

    // 07 - updateAvailableContainerList()
    //      Executa a procura por containers com armazenamento disponível no in-
    //      ventário e atualiza a lista.
    public void updateAvailableContainerList() throws ExceptionDAO {
        setInventoryAvailableContainerList(containerService.createListAvailableContainers());

        // Limpa filtro de containers
        availableContainers = new ArrayList<SelectItem>();

        // Fix que permite que todos os containers sejam exibidos
        availableContainers.add(new SelectItem(""));

        // Atualiza opções do filtro        
        for (int i = 0; i < inventoryAvailableContainerList.size(); i++) {
            getAvailableContainers().add(new SelectItem(inventoryAvailableContainerList.get(i).getAlias()));
        }

        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Lista de containers com armazenamento disponível carregada.");

    }

    // 08 - loadItemDetails()
    //      Carrega os detalhes do PN selecionado.
    public void loadItemDetails(InventoryBean inventoryItem) throws SQLException, ExceptionDAO {
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] PN selecionado para exibição de detalhes: '" + inventoryItem.getPn() + "'.");
        itemBean = itemService.loadItemInfo(inventoryItem.getPn());
        loadAlternateItems(inventoryItem);
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Dados do PN carregados.");
    }

    // 09 - loadAlternateItems()
    //      Pesquisa PNs alternados para o PN selecionado.
    public void loadAlternateItems(InventoryBean inventoryItem) throws SQLException, ExceptionDAO {
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] PN selecionado para exibição de alternados: '" + inventoryItem.getPn() + "'.");
        setAlternateItems(alternateService.loadAlternateItems(inventoryItem.getPn()));
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Dados de PNs alternados carregados.");
    }

    // 10 - addInventoryItem()
    //      Adiciona novo item ao inventário.
    public void addInventoryItem() throws SQLException, ExceptionDAO {
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Iniciando verificação do PN...");
        itemCheckStatus = itemService.checkItemExistance(newInventoryItem.getPn());
        if (itemCheckStatus == false) {
            System.out.println("[SYSTEM][INVENTORYCONTROLLER] Preparando para adicionar novo PN ao banco de dados...");
            newItem.setPn(newInventoryItem.getPn());
            newItem.setDescription(newInventoryItem.getDescription());
            itemService.addNewItem(newItem);
            System.out.println("[SYSTEM][INVENTORYCONTROLLER] PN inserido com sucesso no banco de dados!");

        } else {
            System.out.println("[SYSTEM][INVENTORYCONTROLLER] O PN informado já existe. Pulando inserção do PN informado...");

        }
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Iniciando verificação do item...");
        inventoryItemCheckStatus = inventoryService.checkInventoryItemExistance(newInventoryItem.getPn());
        if (inventoryItemCheckStatus == false) {
            System.out.println("[SYSTEM][INVENTORYCONTROLLER] Preparando para adicionar novo item ao banco de dados...");
            inventoryService.addNewInventoryItem(newInventoryItem);
            System.out.println("[SYSTEM][INVENTORYCONTROLLER] Item inserido com sucesso no inventário!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "O item de PN '" + newInventoryItem.getPn() + "' foi adicionado ao inventário com sucesso!"));
        } else {
            System.out.println("[SYSTEM][INVENTORYCONTROLLER] O item informado já existe no inventário.");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "O item de PN '" + newInventoryItem.getPn() + "' já existe no inventário."));
        }

        newItem = new ItemBean();
        newInventoryItem = new InventoryBean();     
        
        
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Processo finalizado.");
    }

    // CONSTRUTOR
    public InventoryController() throws SQLException, ExceptionDAO {

        // Executa e carrega tabela de inventário
        loadAllInventory();       

        // Define o tipo de exportação padrão do inventário atual
        this.exportType = "csv";

        // Define as opções de filtro para a coluna 'Condição' do inventário
        conditionFilterOptions.add(new SelectItem(""));      // ALL
        conditionFilterOptions.add(new SelectItem("OH"));    // OVERHAULED
        conditionFilterOptions.add(new SelectItem("PMA"));   // PARTS MANUFACTURE ALTERNATIVE
        conditionFilterOptions.add(new SelectItem("AR"));    // AS-REMOVED
        conditionFilterOptions.add(new SelectItem("NEW"));   // NEW
        conditionFilterOptions.add(new SelectItem("SV"));    // SERVICABLE
        conditionFilterOptions.add(new SelectItem("RP"));    // REPAIRED
        conditionFilterOptions.add(new SelectItem("RB"));    // REBUILT

    }

    // LISTENERS
    // <editor-fold desc="GET and SET" defaultstate="collapsed">
    public InventoryBean getInventoryBean() {
        return inventoryBean;
    }

    public void setInventoryBean(InventoryBean inventoryBean) {
        this.inventoryBean = inventoryBean;
    }

    public List<InventoryBean> getInventory() {
        return inventory;
    }

    public void setInventory(List<InventoryBean> inventory) {
        this.inventory = inventory;
    }

    public List<InventoryBean> getFilteredInventory() {
        return filteredInventory;
    }

    public void setFilteredInventory(List<InventoryBean> filteredInventory) {
        this.filteredInventory = filteredInventory;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public List<InventoryBean> getInventoryContainerList() {
        return inventoryContainerList;
    }

    public void setInventoryContainerList(List<InventoryBean> inventoryContainerList) {
        this.inventoryContainerList = inventoryContainerList;
    }

    public List<SelectItem> getContainerFilterOptions() {
        return containerFilterOptions;
    }

    public void setContainerFilterOptions(List<SelectItem> containerFilterOptions) {
        this.containerFilterOptions = containerFilterOptions;
    }

    public int getInventoryTotal() {
        return inventoryTotal;
    }

    public void setInventoryTotal(int inventoryTotal) {
        this.inventoryTotal = inventoryTotal;
    }

    public int getInventoryFaultTotal() {
        return inventoryFaultTotal;
    }

    public void setInventoryFaultTotal(int inventoryFaultTotal) {
        this.inventoryFaultTotal = inventoryFaultTotal;
    }

    public String getSelectedContainer() {
        return selectedContainer;
    }

    public void setSelectedContainer(String selectedContainer) {
        this.selectedContainer = selectedContainer;
    }

    public ContainerBean getContainerBean() {
        return containerBean;
    }

    public void setContainerBean(ContainerBean containerBean) {
        this.containerBean = containerBean;
    }

    public int getContainerTotal() {
        return containerTotal;
    }

    public void setContainerTotal(int containerTotal) {
        this.containerTotal = containerTotal;
    }

    public List<InventoryBean> getInventoryOwnerList() {
        return inventoryOwnerList;
    }

    public void setInventoryOwnerList(List<InventoryBean> inventoryOwnerList) {
        this.inventoryOwnerList = inventoryOwnerList;
    }

    public List<SelectItem> getOwnerFilterOptions() {
        return ownerFilterOptions;
    }

    public void setOwnerFilterOptions(List<SelectItem> ownerFilterOptions) {
        this.ownerFilterOptions = ownerFilterOptions;
    }

    public List<SelectItem> getAvailableContainers() {
        return availableContainers;
    }

    public void setAvailableContainers(List<SelectItem> availableContainers) {
        this.availableContainers = availableContainers;
    }

    public List<ContainerBean> getInventoryAvailableContainerList() {
        return inventoryAvailableContainerList;
    }

    public void setInventoryAvailableContainerList(List<ContainerBean> inventoryAvailableContainerList) {
        this.inventoryAvailableContainerList = inventoryAvailableContainerList;
    }

    public ItemBean getItemBean() {
        return itemBean;
    }

    public void setItemBean(ItemBean itemBean) {
        this.itemBean = itemBean;
    }

    public List<AlternateBean> getAlternateItems() {
        return alternateItems;
    }

    public void setAlternateItems(List<AlternateBean> alternateItems) {
        this.alternateItems = alternateItems;
    }

    public InventoryBean getNewInventoryItem() {
        return newInventoryItem;
    }

    public void setNewInventoryItem(InventoryBean newInventoryItem) {
        this.newInventoryItem = newInventoryItem;
    }

    public boolean isItemCheckStatus() {
        return itemCheckStatus;
    }

    public void setItemCheckStatus(boolean itemCheckStatus) {
        this.itemCheckStatus = itemCheckStatus;
    }

    public ItemBean getNewItem() {
        return newItem;
    }

    public void setNewItem(ItemBean newItem) {
        this.newItem = newItem;
    }

    public boolean isInventoryItemCheckStatus() {
        return inventoryItemCheckStatus;
    }

    public void setInventoryItemCheckStatus(boolean inventoryItemCheckStatus) {
        this.inventoryItemCheckStatus = inventoryItemCheckStatus;
    }

    public List<SelectItem> getConditionFilterOptions() {
        return conditionFilterOptions;
    }

    public void setConditionFilterOptions(List<SelectItem> conditionFilterOptions) {
        this.conditionFilterOptions = conditionFilterOptions;
    }
    // </editor-fold>
}
