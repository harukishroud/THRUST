package bean;

import java.sql.Date;

public class PackingBean {
    private int n;
    private Date date;
    private int quote_ref;
    private boolean status;

    // <editor-fold desc="GET and SET" defaultstate="collapsed">
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuote_ref() {
        return quote_ref;
    }

    public void setQuote_ref(int quote_ref) {
        this.quote_ref = quote_ref;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    // </editor-fold>
    
}
