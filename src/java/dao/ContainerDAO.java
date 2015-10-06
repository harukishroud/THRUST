package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.ContainerBean;
import db.ConnectionBuilder;

public class ContainerDAO {

    // DAO 01 - loadContainerInfo()
    //          Carrega informações do container para exibição.
    public ContainerBean loadContainerInfo(String containerAlias) throws SQLException {
        ContainerBean containerInfo = new ContainerBean();
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][CONTAINERDAO] Iniciando...");

        String sql = "SELECT * FROM container WHERE ALIAS = '" + containerAlias + "'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("[DATABASE][INVENTORYDAO] Carregando informações do container...");

        while (rs.next()) {
            containerInfo.setAlias(rs.getString("ALIAS"));
            containerInfo.setColor(rs.getString("COLOR"));
            containerInfo.setFlag(rs.getInt("FLAG"));
            containerInfo.setFrom(rs.getString("FROM"));
            containerInfo.setFull_status(rs.getBoolean("FULL_STATUS"));
            containerInfo.setOldAlias(rs.getString("ALIAS"));
            containerInfo.setTo(rs.getString("TO"));
            containerInfo.setType(rs.getString("TYPE"));
        }

        System.out.println("[DATABASE][CONTAINERDAO] As informações foram carregadas com sucesso!");

        rs.close();
        ps.close();
        conn.close();

        return containerInfo;
    }

    // DAO 02 - countContainerTotalItens()
    //          Conta total de itens no inventário.
    public int countContainerTotalItens(String containerAlias) throws SQLException, ExceptionDAO {
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();
        int containerTotal = 0;

        System.out.println("[DATABASE][CONTAINERDAO] Iniciando contagem de itens...");

        String sql = "SELECT PN FROM inventory_standardview WHERE ALIAS = '" + containerAlias + "'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            containerTotal = containerTotal + 1;
        }

        System.out.println("[DATABASE][CONTAINERDAO] O total de itens no container '" + containerAlias + "' é de " + containerTotal + ".");

        rs.close();
        ps.close();
        conn.close();

        return containerTotal;
    }

    // DAO 03 - createListAvailableContainers()
    //          Cria lista de containers com armazenamento disponível.
    public List<ContainerBean> createListAvailableContainers() throws SQLException, ExceptionDAO {
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();
        List<ContainerBean> inventoryAvailableContainerList = new ArrayList<ContainerBean>();

        System.out.println("[DATABASE][INVENTORYDAO] Iniciando busca por containers com armazenamento disponível...");

        String sql = "SELECT DISTINCT ALIAS FROM container WHERE FULL_STATUS = 0";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ContainerBean inventoryAvailableContainer = new ContainerBean();
            inventoryAvailableContainer.setAlias(rs.getString("ALIAS"));
            inventoryAvailableContainerList.add(inventoryAvailableContainer);
        }

        rs.close();
        ps.close();
        conn.close();

        return inventoryAvailableContainerList;
    }

    // DAO 04 - loadAllContainers()
    //          Carrega todos os containers disponíveis no banco de dados.
    public List<ContainerBean> loadAllContainers() throws SQLException, ExceptionDAO {
        List<ContainerBean> containers = new ArrayList<ContainerBean>();
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();
        int containerTotal = 0;

        System.out.println("[DATABASE][CONTAINERDAO] Carregando todos os containers existentes no"
                + "banco de dados...");

        // Carrega dados da VIEW 'container_standardview'
        String sql = "SELECT * FROM container_standardview";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ContainerBean container = new ContainerBean();
            container.setAlias(rs.getString("ALIAS"));
            container.setColor(rs.getString("COLOR"));
            container.setFlag(rs.getInt("FLAG"));
            container.setFull_status(rs.getBoolean("FULL_STATUS"));
            container.setFrom(rs.getString("FROM"));
            container.setOldAlias(rs.getString("ALIAS"));
            container.setTo(rs.getString("TO"));
            container.setType(rs.getString("TYPE"));

            // Conta o número de items existentes no container carregado
            String sqlCount = "SELECT PN FROM inventory_standardview WHERE ALIAS = '" + container.getAlias() + "'";
            PreparedStatement psCount = conn.prepareStatement(sqlCount);
            ResultSet rsCount = psCount.executeQuery();

            while (rsCount.next()) {
                containerTotal = containerTotal + 1;
            }

            // Define total de items no container carregado
            container.setItemsTotal(containerTotal);

            // Zera o contador de items para o próximo container
            containerTotal = 0;

            containers.add(container);
        }

        System.out.println("[DATABASE][CONTAINERDAO] Containers carregados com sucesso!");

        rs.close();
        ps.close();
        conn.close();

        return containers;
    }

}
