package bean;

public class InventoryBean {

    private int id;
    private String pn;
    private String description;
    private int quantity;
    private String condition;
    private String containerAlias;
    private float price;
    private String status;
    private String from;
    private String obs;
    private boolean form_status;
    private String form_link;
    private boolean containerfull_status;
    
    // FUNCÕES
    private String oldpn;
    private String oldalias;
    private String movealias;
    private int selectedQuantity;

    // <editor-fold desc="GET and SET" defaultstate="collapsed">
    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getContainerAlias() {
        return containerAlias;
    }

    public void setContainerAlias(String containerAlias) {
        this.containerAlias = containerAlias;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public boolean isForm_status() {
        return form_status;
    }

    public void setForm_status(boolean form_status) {
        this.form_status = form_status;
    }

    public String getForm_link() {
        return form_link;
    }

    public void setForm_link(String form_link) {
        this.form_link = form_link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isContainerfull_status() {
        return containerfull_status;
    }

    public void setContainerfull_status(boolean containerfull_status) {
        this.containerfull_status = containerfull_status;
    }

    public String getOldpn() {
        return oldpn;
    }

    public void setOldpn(String oldpn) {
        this.oldpn = oldpn;
    }

    public String getMovealias() {
        return movealias;
    }

    public void setMovealias(String movealias) {
        this.movealias = movealias;
    }

    public int getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(int selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    public String getOldalias() {
        return oldalias;
    }

    public void setOldalias(String oldalias) {
        this.oldalias = oldalias;
    }
    // </editor-fold>

}
