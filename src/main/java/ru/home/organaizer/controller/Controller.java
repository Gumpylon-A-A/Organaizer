package ru.home.organaizer.controller;

import ru.home.organaizer.entity.Client;
import ru.home.organaizer.exceptions.OrganizerException;
import ru.home.organaizer.model.ClientModel;
import ru.home.organaizer.util.SortByField;

import java.util.List;

/**
 * Created by Gumpylon Arsalan
 */

public class Controller {

    private final ClientModel clientModel;

    public Controller(ClientModel clientModel) {
        this.clientModel = clientModel;
    }

    /*Вывод всех клиентов*/
    public List<Client> onShowAllClients() throws OrganizerException {
        return clientModel.retrieveAllClients();
    }

    /*Вывод клиентов в отсортированном виде*/
    public List<Client> onShowSortedAllClients(SortByField.Order field) throws OrganizerException {
        return clientModel.sortByField(field, clientModel.retrieveAllClients());
    }

    /*Создание клиента*/
    public void onCreateClient(Client client) throws OrganizerException {
        clientModel.createClient(client);
    }

    /*Удаление всех клиентов*/
    public void onDeleteAllClients() throws OrganizerException {
        clientModel.deleteAllClients();
    }

    /*Удаление клиента по ID*/
    public void onClientDelete(String id) throws OrganizerException {
        clientModel.deleteClientById(id);
    }

    /*Редактирование клиента по ID*/
    public void onUpdateClient(String updateClientId, Client client) throws OrganizerException {
        clientModel.updateClient(updateClientId, client);
    }

    /*Поиск клиента по номеру телефона*/
    public List<Client> onFindByPhoneNumber(String phoneNumber) throws OrganizerException {
        return clientModel.getClientByPhoneNumber(phoneNumber);
    }

    /*Проверка пустоты органайзера*/
    public boolean onCheckOrganizerIsEmpty() throws OrganizerException {
        return clientModel.checkOrganizerIsEmpty();
    }

    /*Поиск клиента по ID*/
    public Client onFindClient(String id) throws OrganizerException {
        return clientModel.getClientById(id);
    }

    /*Проверка существования ID*/
    public boolean onCheckIdForExistence(String id) throws OrganizerException {
        return clientModel.checkClientForExistence(id);
    }
}
