package util;

public class CotacaoBean {

    public String data;
    public float taxacompra;
    public float taxavenda;
    
    public CotacaoBean() {
        
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getTaxacompra() {
        return taxacompra;
    }

    public void setTaxacompra(float taxacompra) {
        this.taxacompra = taxacompra;
    }

    public float getTaxavenda() {
        return taxavenda;
    }

    public void setTaxavenda(float taxavenda) {
        this.taxavenda = taxavenda;
    } 
    
}