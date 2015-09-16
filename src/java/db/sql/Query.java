/*
    THURST - DATABASE SQL PACKAGE
    File: QUERY.JAVA | Last Major Update: 16.09.2015
    Developer: Kevin Raian
    IDINALOG REBORN © 2015
*/

package db.sql;

public class Query {
    
    // VARIÁVEIS
    private String field;
    private String value;
    private QueryGender gender;
    private QueryType type;
    private QueryOperation operation;

    // <editor-fold defaultstate="collapsed" desc="GET and SET">
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public QueryGender getGender() {
        return gender;
    }

    public void setGender(QueryGender gender) {
        this.gender = gender;
    }

    public QueryType getType() {
        return type;
    }

    public void setType(QueryType type) {
        this.type = type;
    }

    public QueryOperation getOperation() {
        return operation;
    }

    public void setOperation(QueryOperation operation) {
        this.operation = operation;
    } 
    // </editor-fold>
    
    // MÉTODOS
    
    // Prepara consulta para ser enviada ao QueryBuilder e processada
    public void packQuery(String field, QueryGender gender, String value, QueryType type, QueryOperation operation) {
        this.setField(field);
        this.setValue(value);
        this.setGender(gender);
        this.setType(type);
        this.setOperation(operation);
    } 
    
}
