package controller;

import bean.InventoryBean;
import dao.ExceptionDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import service.InventoryService;

@ManagedBean(name = "quoteController")
@ViewScoped

public class QuoteController {

    // VARIAVEIS GERAIS
    /* HTTP Session */
    private HttpSession session;

    /* Armazena e define progresso do construtor de cotação */
    private int quoteProgress;

    // BEANS
    // LISTAS
    /* Armazena inventário */
    private List<InventoryBean> inventory = new ArrayList<InventoryBean>();
    /* Armazena lista de condições para filtro do inventário */
    private List<SelectItem> conditionFilterOptions = new ArrayList<SelectItem>();
    /* Armazena lista de items selecionados para cotação */
    private List<InventoryBean> selectedToQuote = new ArrayList<InventoryBean>();

    // SERVIÇOS
    InventoryService inventoryService = new InventoryService();

    // MÉTODOS
    ////////////////////////////////////////////////////////////////////////////
    // 01 - loadAvailableInventory()
    //      Carrega inventário existente no banco de dados.
    public void loadAvailableInventory() throws SQLException, ExceptionDAO {
        inventory = inventoryService.loadAvailableInventory();
    }

    ////////////////////////////////////////////////////////////////////////////
    // 02 - addToQuote()
    //      Adiciona item selecionado à lista de cotação.
    public void addToQuote(InventoryBean selectedItem) throws ExceptionDAO {
        /* Armazena item a ser adicionado à cotação caso o processo seja validado com sucesso */
        InventoryBean verifiedItem = new InventoryBean();
        /* Armazena verificação de existência do item na cotação */
        boolean selectedItemCheckStatus = false;
        /* Armazena verificação de quantidade do item selecionado */
        boolean selectedItemQuantityCheckStatus = false;

        /* Verifica toda lista de items selecionados pelo item informado */
        for (int i = 0; i < selectedToQuote.size(); i++) {
            /* Caso o item já exista na lista o processo é cancelado */
            if (selectedToQuote.get(i).getPn().equals(selectedItem.getPn()) && selectedToQuote.get(i).getCondition().equals(selectedItem.getCondition())) {
                System.out.println("[CONTROLLER][QUOTE][addToQuote] O Item '" + selectedItem.getPn() + "' já existe na lista de mudança.");
                selectedItemCheckStatus = true;
            }
        }

        /* Verifica disponibilidade para quantidade selecionada para o item */
        if (selectedItemCheckStatus == false) {
            if (selectedItem.getSelectedQuantity() > selectedItem.getQuantity()) {
                selectedItemQuantityCheckStatus = true;
                System.out.println("[CONTROLLER][QUOTE][addToQuote] A quantidade informada para o item '" + selectedItem.getPn() + "' não está disponível!");
                System.out.println("[CONTROLLER][QUOTE][addToQuote] Disponível: " + selectedItem.getQuantity() + ", Informada: " + selectedItem.getSelectedQuantity());
            }
        }

        /* Caso o item não seja encontrado na verificação e a quantidade esteja disponível o processo continua */
        if (selectedItemCheckStatus == false && selectedItemQuantityCheckStatus == false) {
            /* Tranfere dados do item selecionado para o bean de item verificado */
            verifiedItem = selectedItem;
            /* Define a quantidade do item verificado com a quantidade selecionada */
            verifiedItem.setQuantity(selectedItem.getSelectedQuantity());
            /* Adiciona item verificado à lista */
            selectedToQuote.add(verifiedItem);
            System.out.println("[SYSTEM][INVENTORYCONTROLLER] Item '" + selectedItem.getPn() + "' adicionado à lista de mudança.");
        }
        ////////////////////////////////////////////////////////////////////////
    }

    // CONSTRUTOR
    public QuoteController() {

        /* Cria lista de condições para filtro na tabela de inventário */
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
    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public int getQuoteProgress() {
        return quoteProgress;
    }

    public void setQuoteProgress(int quoteProgress) {
        this.quoteProgress = quoteProgress;
    }

    public List<InventoryBean> getInventory() {
        return inventory;
    }

    public void setInventory(List<InventoryBean> inventory) {
        this.inventory = inventory;
    }

    public List<SelectItem> getConditionFilterOptions() {
        return conditionFilterOptions;
    }

    public void setConditionFilterOptions(List<SelectItem> conditionFilterOptions) {
        this.conditionFilterOptions = conditionFilterOptions;
    }

    public List<InventoryBean> getSelectedToQuote() {
        return selectedToQuote;
    }

    public void setSelectedToQuote(List<InventoryBean> selectedToQuote) {
        this.selectedToQuote = selectedToQuote;
    }

    // </editor-fold>
}
