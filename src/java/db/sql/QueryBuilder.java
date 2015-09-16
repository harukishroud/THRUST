/*
    THURST - DATABASE SQL PACKAGE
    File: QUERYBUILDER.JAVA | Last Major Update: 16.09.2015
    Developer: Kevin Raian
    IDINALOG REBORN © 2015
*/

package db.sql;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

    // Cria lista de consultas e sua estrutura
    List<Query> queries = new ArrayList<Query>();
    List<String> fullQuery = new ArrayList<String>();

    // Adiciona consulta à lista de consultas
    public void addQuery(Query query) {
        System.out.println("[DATABASE][QUERY BUILDER] Adicionando nova consulta...");
        queries.add(query);
    }

    // Calcula extensão da consulta
    public String buildQuery() {
        System.out.println("[DATABASE][QUERY BUILDER] Calculando e construindo consulta...");
        String query = " ";
        for (int i = 0; i < fullQuery.size(); i++) {
            query = query + fullQuery.get(i);
        }
        return query;

    }
    
    // Adiciona consulta manualmente 
    public void addManualQuery(String query) {
        fullQuery.add(query);
    }
    
    // Adiciona consulta completa
    public void addQuery(QueryOperation operation, String field, QueryGender gender, String value, QueryType type) {
        
        System.out.println("[DATABASE][QUERY BUILDER] Preparando consulta...");
        
        // Constrói nova consulta
        Query currentQuery = new Query();
        
        // Define valores da consulta
        currentQuery.setField(field);
        currentQuery.setValue(value);
        currentQuery.setGender(gender);
        currentQuery.setType(type);
        currentQuery.setOperation(operation);
        
        // System.out.println("[QUERY BUILDER] New Query set with the current data for - Field: '" + field + "', Value: '" + value + "', Gender: '"
        //         + gender + "', Type: '" + type + "' and Operation: '" + operation + "'." );
        
		// Adiciona consulta à lista de consultas
        queries.add(currentQuery);

        // Verifica e configura tipo de operação da consulta
        
        // System.out.println("[QUERY BUILDER] Setting Query operation...");
        
        String buildOperation = "";
        if (operation.equals(operation.and)) {
            buildOperation = buildOperation + " and ";
        } else if (operation.equals(operation.or)) {
            buildOperation = buildOperation + " or ";
        } else if (operation.equals(operation.empty)) {
            buildOperation = buildOperation + " where ";
        }
        
        // System.out.println("[QUERY BUILDER] Current Query operation is: '" + operation + "'.");
        // System.out.println("[QUERY BUILDER] Current buildOperation: '" + buildOperation + "'.");

        // Verifica e configura tipo de dados da consulta
        
        // System.out.println("[QUERY BUILDER]  Defining Query data type...");
        String buildType = "";
        if (type.equals(QueryType.text)) {
            buildOperation = buildOperation + field ;
            buildType = "'" + value + "'";
        } else if (type.equals(QueryType.number)) {
            buildOperation = buildOperation + field;
            buildType = value;
        } else if (type.equals(QueryType.date)) {
            buildOperation = buildOperation + "trunc(" + field + ")";
            buildType = "trunc(to_date('" + value + "','dd/mm/yyyy)'))";
        }
        
        // System.out.println("[QUERY BUILDER] Current Query data type is: '" + type + "'.");
        // System.out.println("[QUERY BUILDER] Current buildOperation: '" + buildOperation + "'.");

        // Verifica e configura gênero e condição da consulta
        
        // System.out.println("[QUERY BUILDER] Defining Query gender...");
        
        if (gender.equals(gender.equal)) {
            buildOperation = buildOperation + " = " + buildType;
        } else if (gender.equals(gender.bigger)) {
            buildOperation = buildOperation + " > " + buildType;
        } else if (gender.equals(gender.smaller)) {
            buildOperation = buildOperation + " < " + buildType;
        } else if (gender.equals(gender.has)) {
            buildOperation = buildOperation + " like '%" + buildType.replaceAll("'", "").replaceAll(" ","%") + "%'";
        } else if (gender.equals(gender.different)) {
            buildOperation = buildOperation + " <> " + buildType;
        } else if (gender.equals(gender.isNull)) {
            buildOperation = buildOperation + " is null ";
        } else if (gender.equals(gender.isNotNull)) {
            buildOperation = buildOperation + " is not null ";
        } else if (gender.equals(gender.biggerEqual)) {
            buildOperation = buildOperation + " >= " + buildType;
        } else if (gender.equals(gender.smallerEqual)) {
            buildOperation = buildOperation + " <= " + buildType;
        }
        
        // System.out.println("[QUERY BUILDER] Current Query gender is: '" + gender + "'.");
        // System.out.println("[QUERY BUILDER] Current buildOperation: '" + buildOperation + "'.");
        
        // Constrói operação por completo e adiciona à estrutura principal
        fullQuery.add(buildOperation);
    }

    // <editor-fold desc="GET and SET" defaultstate="collapsed">
    public List<Query> queries() {
        return queries;
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

    public List<String> getFullQuery() {
        return fullQuery;
    }

    public void setFullQuery(List<String> cs) {
        this.fullQuery = fullQuery;
    }
    // </editor-fold>
    
}
