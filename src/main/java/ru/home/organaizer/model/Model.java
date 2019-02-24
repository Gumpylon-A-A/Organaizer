package ru.home.organaizer.model;

import ru.home.organaizer.entity.Client;

import java.util.List;

/**
 * Created by Gumpylon Arsalan
 */

public interface Model {

    /**
     * @return - List
     */
    List<Client> retrieveAllClients();

    /**
     * @param client - Объект класса Client
     */
    void createClient(Client client);

    /**
     * Метод удаления
     */
    void deleteAllClients();

    /**
     * @param clientId - String UUID
     */
    void deleteClientById(String clientId);

    /**
     * @param updateClientId - String UUID
     * @param editedClient   - Объект класса Client
     */
    void updateClient(String updateClientId, Client editedClient);

    /**
     * @param id - String UUID
     * @return - boolean
     */
    boolean checkClientForExistence(String id);

    /**
     * @param phoneNumber - String matches regex "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"
     * @return - List<Client>
     */
    List<Client> getClientByPhoneNumber(String phoneNumber);

    /**
     * @param id - String UUID
     * @return - Объект класса Client
     */
    Client getClientById(String id);
}
