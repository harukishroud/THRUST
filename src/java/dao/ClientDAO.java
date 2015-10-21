package dao;

import bean.ClientBean;
import db.ConnectionBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    ////////////////////////////////////////////////////////////////////////////
    // 01 - loadAllClients()
    //      Carrega todos os clientes existentes no banco de dados.
    public List<ClientBean> loadAllClients() throws SQLException, ExceptionDAO {
        /* Cria lista 'clientList' que armazena clientes encontrados */
        List<ClientBean> clientList = new ArrayList<ClientBean>();

        /* Inicia e configura conexão com banco de dados */
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DAO][CLIENT][loadAllClients] Preparando SQL para pesquisa de clientes...");

        /* Prepara SQL e carrega dados da VIEW 'client_standardview' */
        String sql = "SELECT * FROM client";
        PreparedStatement ps = conn.prepareStatement(sql);
        System.out.println("@@@@ PASS 01");
        ResultSet rs = ps.executeQuery();
        System.out.println("@@@@ PASS 02");
        while (rs.next()) {
            /* Cria e limpa bean 'client' que armazena dados do cliente */
            ClientBean client = new ClientBean();
            System.out.println("@@@@ PASS 03");
            /* Define dados do cliente */
            client.setId(rs.getInt("ID"));
            client.setCompany(rs.getString("COMPANY"));
            client.setAddress(rs.getString("ADDRESS"));
            client.setCity(rs.getString("CITY"));
            System.out.println("@@@@ PASS 04");
            client.setDistrict(rs.getString("DISTRICT"));
            client.setCountry(rs.getString("COUNTRY"));
            client.setState(rs.getString("STATE"));
            client.setComp(rs.getString("COMP"));
            System.out.println("@@@@ PASS 05");
            client.setContact_name(rs.getString("CONTACT_NAME"));
            client.setContact_no(rs.getString("CONTACT_NO"));
            client.setCnpj(rs.getLong("CNPJ"));
            client.setInsc_est(rs.getInt("INSC_EST"));
            System.out.println("@@@@ PASS 06");
            client.setInsc_mun(rs.getInt("INSC_MUN"));
            client.setObs(rs.getString("OBS"));
            client.setContact_mail(rs.getString("CONTACT_MAIL"));
            /* Adiciona cliente à lista 'clientList' */
            clientList.add(client);
            System.out.println("@@@@ PASS 07");
        }

        /* Encerra SQL e conexão */
        rs.close();
        ps.close();
        conn.close();

        System.out.println("[DAO][CLIENT][loadAllClients] Lista de clientes adquirida com sucesso!");

        /* Retorna lista de clientes encontrados 'clientList' */
        return clientList;
        ////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////
    // 02 - newClient()
    //      Cadastra novo cliente no banco de dados.
    public void newClient(ClientBean client) throws SQLException, ExceptionDAO {
        /* Inicia e configura conexão com banco de dados */
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DAO][CLIENT][newClient] Preparando SQL para inserir o cliente '" + client.getCompany() + "'...");

        /* Prepara SQL e define dados do novo cliente */
        String sql = "INSERT INTO client (COMPANY,ADDRESS,CITY,DISTRICT,COUNTRY,STATE,COMP,CONTACT_NAME,CONTACT_NO,CONTACT_MAIL,CNPJ,INSC_EST,INSC_MUN,OBS)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ps.setString(1, client.getCompany());
        ps.setString(2, client.getAddress());
        ps.setString(3, client.getCity());
        ps.setString(4, client.getDistrict());
        ps.setString(5, client.getCountry());
        ps.setString(6, client.getState());
        ps.setString(7, client.getComp());
        ps.setString(8, client.getContact_name());
        ps.setString(9, client.getContact_no());
        ps.setString(10, client.getContact_mail());
        ps.setLong(11, client.getCnpj());
        ps.setInt(12, client.getInsc_est());
        ps.setInt(13, client.getInsc_mun());
        ps.setString(14, client.getObs());

        /* Executa SQL */
        ps.execute();

        /* Encerra SQL e conexão */
        ps.close();
        conn.close();

        System.out.println("[DAO][CLIENT][newClient] Cliente '" + client.getCompany() + "' adicionado com sucesso ao banco de dados!");
        ////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////
    // 03 - loadClient()
    //      Carrega dados do banco de dados para o cliente informado.
    public ClientBean loadClient(int clientId) throws SQLException, ExceptionDAO {
        /* Cria bean 'client' que armazena dados do clientes solicitado */
        ClientBean client = new ClientBean();

        /* Inicia e configura conexão com banco de dados */
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DAO][CLIENT][loadClient] Preparando SQL para pesquisa de dados do cliente de ID '" + clientId + "' ...");

        /* Prepara SQL e carrega dados da VIEW 'client_standardview' */
        String sql = "SELECT * FROM client_standardview WHERE ID = " + clientId;
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            /* Define dados do cliente */
            client.setId(rs.getInt("ID"));
            client.setCompany(rs.getString("COMPANY"));
            client.setAddress(rs.getString("ADDRESS"));
            client.setCity(rs.getString("CITY"));
            client.setDistrict(rs.getString("DISTRICT"));
            client.setCountry(rs.getString("COUNTRY"));
            client.setState(rs.getString("STATE"));
            client.setComp(rs.getString("COMP"));
            client.setContact_name(rs.getString("CONTACT_NAME"));
            client.setContact_no(rs.getString("CONTACT_NO"));
            client.setCnpj(rs.getLong("CNPJ"));
            client.setInsc_est(rs.getInt("INSC_EST"));
            client.setInsc_mun(rs.getInt("INSC_MUN"));
            client.setObs(rs.getString("OBS"));
            client.setContact_mail(rs.getString("CONTACT_MAIL"));
        }

        /* Encerra SQL e conexão */
        rs.close();
        ps.close();
        conn.close();

        System.out.println("[DAO][CLIENT][loadClient] Dados do cliente de ID '" + clientId + "' carregados com sucesso!");

        /* Retorna bean 'client' com os dados carregados */
        return client;
        ////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////
    // 04 - updateClient()
    //      Atualiza dados do cliente informado no banco de dados.
    public ClientBean updateClient(ClientBean updatedClient) throws SQLException, ExceptionDAO {
        /* Inicia e configura conexão com banco de dados */
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DAO][CLIENT][updateClient] Preparando SQL para atualização do cliente '" + updatedClient.getCompany() + "'...");

        /* Prepara SQL e define dados a serem atualizados */
        String sql = "UPDATE client SET COMPANY = ?,ADDRESS = ?,CITY = ?,DISTRICT = ?,"
                + "COUNTRY = ?,STATE = ?,COMP = ?,CONTACT_NAME = ?,CONTACT_NO = ?,CONTACT_MAIL = ?,CNPJ = ?,INSC_EST = ?,INSC_MUN = ?,OBS = ?"
                + " WHERE ID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, updatedClient.getCompany());
        ps.setString(2, updatedClient.getAddress());
        ps.setString(3, updatedClient.getCity());
        ps.setString(4, updatedClient.getDistrict());
        ps.setString(5, updatedClient.getCountry());
        ps.setString(6, updatedClient.getState());
        ps.setString(7, updatedClient.getComp());
        ps.setString(8, updatedClient.getContact_name());
        ps.setString(9, updatedClient.getContact_no());        
        ps.setString(10, updatedClient.getContact_mail());
        ps.setLong(11, updatedClient.getCnpj());
        ps.setInt(12, updatedClient.getInsc_est());
        ps.setInt(13, updatedClient.getInsc_mun());
        ps.setString(14, updatedClient.getObs());
        ps.setInt(15, updatedClient.getId());

        /* Executa SQL */
        ps.execute();

        /* Encerra SQL e conexão */
        ps.close();
        conn.close();

        System.out.println("[DAO][CLIENT][updateClient] Cliente '" + updatedClient.getCompany() + "' atualizado com sucesso!");

        return updatedClient;
        ////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////
    // 05 - removeClient()
    //      Remove cliente informado do banco de dados permanentemente.
    public void removeClient(int clientId) throws SQLException, ExceptionDAO {
        /* Inicia e configura conexão com banco de dados */
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DAO][CLIENT][removeClient] Preparando SQL para remover cliente de ID '" + clientId + "'...");

        /* Prepara SQL e define dados a serem atualizados */
        String sql = "DELETE FROM client WHERE ID = " + clientId;
        PreparedStatement ps = conn.prepareStatement(sql);

        /* Executa SQL */
        ps.execute();

        /* Encerra SQL e conexão */
        ps.close();
        conn.close();

        System.out.println("[DAO][CLIENT][removeClient] Cliente de ID '" + clientId + "' removido com sucesso!");
        ////////////////////////////////////////////////////////////////////////
    }

}