package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.ClientBean;
import dao.ExceptionDAO;
import dao.ClientDAO;

public class ClientService {

    ////////////////////////////////////////////////////////////////////////////
    // 01 - loadAllClients()
    //      @DAO
    //      Carrega todos os clientes existentes no banco de dados.
    public List<ClientBean> loadAllClients() throws ExceptionDAO {
        /* Cria lista 'clientList' que armazena clientes encontrados */
        List<ClientBean> clientList = new ArrayList<ClientBean>();
        /* Inicia DAO */
        ClientDAO clientDAO = new ClientDAO();

        try {
            /* Executa DAO 'loadAllClients' */
            clientList = clientDAO.loadAllClients();
        } catch (SQLException ex) {
            /* Em caso de erros */
            System.out.println("[SERVICE][CLIENT][loadAllClients] ERRO: Não foi possível carregar clientes do banco de dados.");
        }

        /* Retorna lista 'clientList' com os clientes existentes */
        return clientList;
        ////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////
    // 02 - newClient()
    //      @DAO
    //      Cadastra novo cliente no banco de dados.
    public void newClient(ClientBean client) throws ExceptionDAO {
        /* Inicia DAO */
        ClientDAO clientDAO = new ClientDAO();

        try {
            /* Executa DAO 'newClient' */
            clientDAO.newClient(client);
        } catch (SQLException ex) {
            /* Em caso de erros */
            System.out.println("[SERVICE][CLIENT][newClient] ERRO: Não foi possível adicionar o cliente '" + client.getCompany() + "' ao banco de dados.");
        }

        ////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////
    // 03 - loadClient()
    //      @DAO
    //      Carrega dados do banco de dados para o cliente informado.
    public ClientBean loadClient(int clientId) throws ExceptionDAO {
        /* Cria bean 'client' que armazena dados do client informado */
        ClientBean client = new ClientBean();
        /* Inicia DAO */
        ClientDAO clientDAO = new ClientDAO();

        try {
            /* Executa DAO 'loadClient' */
            client = clientDAO.loadClient(clientId);
        } catch (SQLException ex) {
            /* Em caso de erros */
            System.out.println("[SERVICE][CLIENT][loadClient] ERRO: Não foi possível carregar dados do cliente de ID '" + clientId + "'.");
        }

        /* Retorna bean 'client' com os dados do cliente informado */
        return client;
        ////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////
    // 04 - updateClient()
    //      @DAO
    //      Atualiza dados do cliente informado no banco de dados.
    public ClientBean updateClient(ClientBean updatedClient) throws ExceptionDAO {
        /* Inicia DAO */
        ClientDAO clientDAO = new ClientDAO();

        try {
            /* Executa DAO 'updateClient' */
            return clientDAO.updateClient(updatedClient);
        } catch (SQLException ex) {
            /* Em caso de erros */
            System.out.println("[SERVICE][CLIENT][updatedClient] ERRO: Não foi possível atualizar o cliente de ID '" + updatedClient.getId() + "'.");
            return null;
        }

        ////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////
    // 05 - removeClient()
    //      @DAO
    //      Remove cliente informado do banco de dados permanentemente.
    public void removeClient(int clientId) throws ExceptionDAO {
        /* Inicia DAO */
        ClientDAO clientDAO = new ClientDAO();

        try {
            /* Executa DAO 'removeClient' */
            clientDAO.removeClient(clientId);
        } catch (SQLException ex) {
            /* Em caso de erros */
            System.out.println("[SERVICE][CLIENT][removeClient] ERRO: Não foi possível remover o cliente de ID '" + clientId + "'.");
        }

        ////////////////////////////////////////////////////////////////////////
    }

}
