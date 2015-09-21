package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import dao.ExceptionDAO;
import bean.InventoryBean;
import bean.ContainerBean;
import service.InventoryService;
import service.ContainerService;

@ManagedBean(name = "inventoryController")
@ViewScoped

public class InventoryController {

    // VARIAVEIS
    // Total de itens no inventário
    private int inventoryTotal;
    // Total de itens em falta no inventário
    private int inventoryFaultTotal;
    // Salva tipo de arquivo a ser exportado
    private String exportType;
    // Armazena container selecionado
    private String selectedContainer;
    // Armazena item do inventário
    private InventoryBean inventoryBean = new InventoryBean();
    // Armazena informações do container selecionado
    private ContainerBean containerBean = new ContainerBean();
    // Armazena inventário
    private List<InventoryBean> inventory = new ArrayList<InventoryBean>();
    // Armazena inventário filtrado
    private List<InventoryBean> filteredInventory = new ArrayList<InventoryBean>();
    // Armazena containers existentes no inventário
    private List<InventoryBean> inventoryContainerList = new ArrayList<InventoryBean>();
    // Lista de condições para filtro do inventário
    private List<SelectItem> conditionFilterOptions = new ArrayList<SelectItem>();
    // Lista de containers existentes no inventário para o filtro
    private List<SelectItem> containerFilterOptions = new ArrayList<SelectItem>();

    // SERVIÇOS    
    InventoryService inventoryService = new InventoryService();
    
    ContainerService containerService = new ContainerService();
   

    // MÉTODOS    
    // 01 - loadAllInventory()
    //      Carrega todos os itens e inventários existentes no banco de dados.
    public void loadAllInventory() throws SQLException, ExceptionDAO {
        inventory = inventoryService.loadInventory();
        inventoryFaultTotal = inventoryService.countInventoryFaultItens().size();
        inventoryTotal = inventory.size();
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
    public void loadContainerDetails(InventoryBean inventoryItem) throws SQLException {
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Container selecionado para exibição de detalhes: '" + inventoryItem.getContainerAlias()+ "'.");
        containerBean = containerService.loadContainerInfo(inventoryItem.getContainerAlias());
        System.out.println("[SYSTEM][INVENTORYCONTROLLER] Dados do container carregados.");
    }

    // CONSTRUTOR
    public InventoryController() throws SQLException, ExceptionDAO {

        // Executa e carrega tabela de inventário
        loadAllInventory();

        // Carrega lista de containers existentes
        updateContainerList();

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

    // <editor-fold desc="GET and SET" defaultstate="collapsed">
    public InventoryBean getInventoryBasicBean() {
        return inventoryBean;
    }

    public void setInventoryBasicBean(InventoryBean inventoryBasicBean) {
        this.inventoryBean = inventoryBasicBean;
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

    public List<SelectItem> getConditionFilterOptions() {
        return conditionFilterOptions;
    }

    public void setConditionFilterOptions(List<SelectItem> conditionFilterOptions) {
        this.conditionFilterOptions = conditionFilterOptions;
    }
    // </editor-fold>
}
