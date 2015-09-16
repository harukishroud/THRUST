package bean;

public class InventoryBasicBean {
    
    private String pn;
    private String description;
    private int quantity;
    private String condition;
    private String containerAlias;

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
    
    
}
