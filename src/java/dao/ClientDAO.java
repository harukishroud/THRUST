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
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            /* Cria e limpa bean 'client' que armazena dados do cliente */
            ClientBean client = new ClientBean();
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
            client.setCnpj(rs.getString("CNPJ"));
            client.setInsc_est(rs.getString("INSC_EST"));
            client.setInsc_mun(rs.getString("INSC_MUN"));
            client.setObs(rs.getString("OBS"));
            client.setContact_mail(rs.getString("CONTACT_MAIL"));
            client.setCep(rs.getString("CEP"));
            client.setDelivery_address(rs.getString("DELIVERY_ADDRESS"));
            client.setDelivery_city(rs.getString("DELIVERY_CITY"));
            client.setDelivery_comp(rs.getString("DELIVERY_COMP"));
            client.setDelivery_district(rs.getString("DELIVERY_DISTRICT"));
            client.setDelivery_receiver(rs.getString("DELIVERY_RECEIVER"));
            client.setDelivery_state(rs.getString("DELIVERY_STATE"));
            client.setDelivery_cep(rs.getString("DELIVERY_CEP"));
            /* Adiciona cliente à lista 'clientList' */
            clientList.add(client);
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
        String sql = "INSERT INTO client (COMPANY,ADDRESS,CITY,DISTRICT,COUNTRY,STATE,COMP,CONTACT_NAME,CONTACT_NO,CONTACT_MAIL,CNPJ,INSC_EST,INSC_MUN,OBS"
                + ",CEP,DELIVERY_ADDRESS,DELIVERY_CITY,DELIVERY_STATE,DELIVERY_RECEIVER,DELIVERY_DISTRICT,DELIVERY_COMP,DELIVERY_CEP)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";       
        
        PreparedStatement ps = conn.prepareStatement(sql);
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
        ps.setString(11, client.getCnpj());
        ps.setString(12, client.getInsc_est());
        ps.setString(13, client.getInsc_mun());
        ps.setString(14, client.getObs());
        ps.setString(15, client.getCep());
        ps.setString(16, client.getDelivery_address());
        ps.setString(17, client.getDelivery_city());
        ps.setString(18, client.getDelivery_state());
        ps.setString(19, client.getDelivery_receiver());
        ps.setString(20, client.getDelivery_district());
        ps.setString(21, client.getDelivery_comp());
        ps.setString(22, client.getDelivery_cep());
        System.out.println(ps);

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
            client.setCnpj(rs.getString("CNPJ"));
            client.setInsc_est(rs.getString("INSC_EST"));
            client.setInsc_mun(rs.getString("INSC_MUN"));
            client.setObs(rs.getString("OBS"));
            client.setContact_mail(rs.getString("CONTACT_MAIL"));
            client.setCep(rs.getString("CEP"));
            client.setDelivery_address(rs.getString("DELIVERY_ADDRESS"));
            client.setDelivery_city(rs.getString("DELIVERY_CITY"));
            client.setDelivery_comp(rs.getString("DELIVERY_COMP"));
            client.setDelivery_district(rs.getString("DELIVERY_DISTRICT"));
            client.setDelivery_receiver(rs.getString("DELIVERY_RECEIVER"));
            client.setDelivery_state(rs.getString("DELIVERY_STATE"));
            client.setDelivery_cep(rs.getString("DELIVERY_CEP"));

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
                + ",CEP = ?,DELIVERY_ADDRESS = ?,DELIVERY_CITY = ?,DELIVERY_STATE = ?,DELIVERY_RECEIVER = ?,DELIVERY_DISTRICT = ?,DELIVERY_COMP = ?,DELIVERY_CEP = ?"
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
        ps.setString(11, updatedClient.getCnpj());
        ps.setString(12, updatedClient.getInsc_est());
        ps.setString(13, updatedClient.getInsc_mun());
        ps.setString(14, updatedClient.getObs());
        ps.setString(15, updatedClient.getCep());
        ps.setString(16, updatedClient.getDelivery_address());
        ps.setString(17, updatedClient.getDelivery_city());
        ps.setString(18, updatedClient.getDelivery_state());
        ps.setString(19, updatedClient.getDelivery_receiver());
        ps.setString(20, updatedClient.getDelivery_district());
        ps.setString(21, updatedClient.getDelivery_comp());
        ps.setString(22, updatedClient.getDelivery_cep());
        ps.setInt(23, updatedClient.getId());

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
