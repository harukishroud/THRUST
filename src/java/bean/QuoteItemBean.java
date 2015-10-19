package bean;

public class QuoteItemBean {
    private int quote_n;
    private int client_id;
    private String pn;
    private String description;
    private int quantity;
    private String condition;
    private float price;

    // <editor-fold desc="GET and SET" defaultstate="collapsed">
    public int getQuote_n() {
        return quote_n;
    }

    public void setQuote_n(int quote_n) {
        this.quote_n = quote_n;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    // </editor-fold>
    
}
