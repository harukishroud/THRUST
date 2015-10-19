package bean;

import java.sql.Date;

public class QuoteBean {
    private int n;
    private String quote_for;
    private Date date;
    private int client_id;
    private boolean status;

    // <editor-fold desc="GET and SET" defaultstate="collapsed">
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getQuote_for() {
        return quote_for;
    }

    public void setQuote_for(String quote_for) {
        this.quote_for = quote_for;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    // </editor-fold>
    
}
