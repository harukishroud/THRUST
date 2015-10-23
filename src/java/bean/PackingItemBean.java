package bean;

public class PackingItemBean {
    private int packing_n;
    private String pn;
    private String description;
    private String condition;
    private boolean form_status;
    private int quantity; 

    // <editor-fold desc="GET and SET" defaultstate="collapsed">
    public int getPacking_n() {
        return packing_n;
    }

    public void setPacking_n(int packing_n) {
        this.packing_n = packing_n;
    }

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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isForm_status() {
        return form_status;
    }

    public void setForm_status(boolean form_status) {
        this.form_status = form_status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    // </editor-fold>
    
}
