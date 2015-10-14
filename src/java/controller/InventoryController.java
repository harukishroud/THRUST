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
import bean.MoveAllFromToBean;
import java.io.IOException;
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
    private ItemBean PN = new ItemBean();
    // Armazena informações do PN a ser adicionado no banco de dados
    private ItemBean newPN = new ItemBean();
    // Armazena dados de Mover A-B
    private MoveAllFromToBean moveInfo = new MoveAllFromToBean();
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
    private List<AlternateBean> alternatePNs = new ArrayList<AlternateBean>();
    // Armazena items a serem movidos
    private List<InventoryBean> inventoryMoveList = new ArrayList<InventoryBean>();
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
        PN = itemService.loadPNDetails(inventoryItem.getPn());
        loadAlternatePNs(inventoryItem);
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Dados do PN carregados.");
    }

    // 09 - loadAlternatePNs()
    //      Pesquisa PNs alternados para o PN selecionado.
    public void loadAlternatePNs(InventoryBean inventoryItem) throws SQLException, ExceptionDAO {
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] PN selecionado para exibição de alternados: '" + inventoryItem.getPn() + "'.");
        setAlternatePNs(alternateService.loadAlternatePNs(inventoryItem.getPn()));
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Dados de PNs alternados para '" + inventoryItem.getPn() + "' carregados.");
    }

    // 10 - addInventoryItem()
    //      Adiciona novo item ao inventário.
    public void addInventoryItem() throws SQLException, ExceptionDAO {
        // # 01 - PN
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Iniciando verificação do PN...");
        // Verifica a existência do PN informado no banco de dados
        itemCheckStatus = itemService.checkPNExistance(newInventoryItem.getPn());
        // Caso o PN não exista o mesmo é adicionado ao banco de dados
        if (itemCheckStatus == false) {
            System.out.println("[SYSTEM][INVENTORYCONTROLLER] Preparando para adicionar novo PN ao banco de dados...");
            newPN.setPn(newInventoryItem.getPn());
            newPN.setDescription(newInventoryItem.getDescription());
            itemService.addPN(newPN);
            System.out.println("[SYSTEM][INVENTORYCONTROLLER] PN inserido com sucesso no banco de dados!");
        // Caso o PN exista o processo de novo PN é anulado
        } else {
            System.out.println("[SYSTEM][INVENTORYCONTROLLER] O PN informado já existe. Pulando inserção do PN informado...");
        }
        // # 02 - INVENTÁRIO
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Iniciando verificação do item...");
        // Verifica a existência do PN informado no inventário
        inventoryItemCheckStatus = inventoryService.checkInventoryItemExistance(newInventoryItem);
        // Caso o PN não exista no inventário o mesmo é adicionado com os dados informados
        if (inventoryItemCheckStatus == false) {
            System.out.println("[SYSTEM][INVENTORYCONTROLLER] Preparando para adicionar novo item ao banco de dados...");
            inventoryService.addNewInventoryItem(newInventoryItem);
            System.out.println("[SYSTEM][INVENTORYCONTROLLER] Item inserido com sucesso no inventário!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "O item de PN '" + newInventoryItem.getPn() + "' e condição '" + newInventoryItem.getCondition() + "' foi adicionado ao inventário com sucesso!"));
        // Caso o PN exista no inventário uma mensagem de erro é exibida e o processo anulado
        } else {
           System.out.println("[SYSTEM][INVENTORYCONTROLLER] O item informado já existe no inventário.");
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "O item de PN '" + newInventoryItem.getPn() + "' e condição '" + newInventoryItem.getCondition() + "' já existe no inventário."));
        }
        
        // Limpa Beans
        newPN = new ItemBean();
        newInventoryItem = new InventoryBean();            
        
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Processo finalizado.");
    }
    
    // 11 - moveAllFromTo()
    //      Move items de um Container A para um Container B
    public void moveAllFromTo() throws SQLException, ExceptionDAO {
        inventoryService.moveAllFromTo(moveInfo);
    }
    
    // 12 - addToMove()
    //      Adiciona item selecionado à lista de mover
    public void addToMove(InventoryBean inventoryItem) throws ExceptionDAO {
        boolean selectedItemCheckStatus = false;     
        
        for (int i = 0; i < inventoryMoveList.size(); i++) {              
            if (inventoryMoveList.get(i).getPn().equals(inventoryItem.getPn())) {                
                System.out.println("[SYSTEM][INVENTORYCONTROLLER] O Item '" + inventoryItem.getPn() + "' já existe na lista de mudança.");
                selectedItemCheckStatus = true;
            }            
        }
        
        if (selectedItemCheckStatus == false) {        
          inventoryMoveList.add(inventoryItem); 
          System.out.println("[SYSTEM][INVENTORYCONTROLLER] Item '" + inventoryItem.getPn() + "' adicionado à lista de mudança.");
        }
        
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

    public ItemBean getPN() {
        return PN;
    }

    public void setPN(ItemBean PN) {
        this.PN = PN;
    }

    public List<AlternateBean> getAlternatePNs() {
        return alternatePNs;
    }

    public void setAlternatePNs(List<AlternateBean> alternatePNs) {
        this.alternatePNs = alternatePNs;
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

    public ItemBean getNewPN() {
        return newPN;
    }

    public void setNewPN(ItemBean newPN) {
        this.newPN = newPN;
    }

    public MoveAllFromToBean getMoveInfo() {
        return moveInfo;
    }

    public void setMoveInfo(MoveAllFromToBean moveInfo) {
        this.moveInfo = moveInfo;
    }

    public boolean isInventoryItemCheckStatus() {
        return inventoryItemCheckStatus;
    }

    public void setInventoryItemCheckStatus(boolean inventoryItemCheckStatus) {
        this.inventoryItemCheckStatus = inventoryItemCheckStatus;
    }

    public List<InventoryBean> getInventoryMoveList() {
        return inventoryMoveList;
    }

    public void setInventoryMoveList(List<InventoryBean> inventoryMoveList) {
        this.inventoryMoveList = inventoryMoveList;
    }

    public List<SelectItem> getConditionFilterOptions() {
        return conditionFilterOptions;
    }

    public void setConditionFilterOptions(List<SelectItem> conditionFilterOptions) {
        this.conditionFilterOptions = conditionFilterOptions;
    }
    // </editor-fold>
}
