package ru.home.organaizer.model;

import ru.home.organaizer.dao.DataSourceDao;
import ru.home.organaizer.entity.Client;
import ru.home.organaizer.exceptions.OrganizerException;
import ru.home.organaizer.util.SortByField;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gumpylon Arsalan
 */

public class ClientModel implements Model {
    private static Logger logger = LoggerFactory.getLogger(ClientModel.class);

    private List<Client> clients = new ArrayList<>();
    private final DataSourceDao json;

    public ClientModel(DataSourceDao json) {
        this.json = json;
    }

    /*Проверка органайзера на пустоту*/
    public boolean checkOrganizerIsEmpty() {
        return retrieveAllClients() == null;
    }

    /*Сортировка по полю*/
    public List<Client> sortByField(SortByField.Order field, List<Client> clients) {

        Objects.requireNonNull(field);
        Objects.requireNonNull(clients);

        try {
            SortByField comparator = new SortByField();
            comparator.setSortingBy(field);
            clients.sort(comparator);
            json.save(clients);
            return clients;
        } catch (Exception e) {
            logger.error("Ошибка при сортировке по " + field, e);
            throw new OrganizerException("Ошибка при сортировке по " + field);
        }
    }

    /*Проверка клиента на существование по ID*/
    @Override
    public boolean checkClientForExistence(String id) {

        Objects.requireNonNull(id);

        try {
            boolean isIdExistence = false;
            for (Client client : retrieveAllClients())
                if (id.equals(client.getId())) {
                    isIdExistence = true;
                }
            return isIdExistence;
        } catch (Exception e) {
            logger.error("Ошибка при поиске id: ", e);
            throw new OrganizerException("Ошибка при поиске id!");
        }
    }

    /*Загрузка всех клиентов*/
    @Override
    public List<Client> retrieveAllClients() {
        try {
            clients = new ArrayList<>();
            return clients = json.load();

        } catch (Exception e) {
            logger.error("Ошибка при получение списка клиентов: ", e);
            throw new OrganizerException("Ошибка при получение списка клиентов!");
        }
    }

    /*Создание клиента*/
    @Override
    public void createClient(Client newClient) throws OrganizerException {

        Objects.requireNonNull(newClient);

        try {
            boolean clientContains = false;

            if (checkOrganizerIsEmpty()) {
                clients = new ArrayList<>();
                clients.add(newClient);
            } else {
                for (Client client : retrieveAllClients()) {
                    if (client.getId().equals(newClient.getId())) {
                        clientContains = true;
                    }
                }

                if (!clientContains) {
                    clients.add(newClient);
                }
            }
            json.save(clients);
        } catch (Exception e) {
            logger.error("Ошибка при создании пользователя: ", e);
            throw new OrganizerException("Ошибка при создании пользователя!");
        }
    }

    /*Удаление всех клиентов*/
    @Override
    public void deleteAllClients() {
        try {
            clients.clear();
            json.clear();
        } catch (Exception e) {
            logger.error("Ошибка при удалении всех клиентов: ", e);
            throw new OrganizerException("Ошибка при удалении всех клиентов!");
        }
    }

    /*Удаление клиента по ID*/
    @Override
    public void deleteClientById(String clientId) {

        Objects.requireNonNull(clientId);

        try {
            clients = retrieveAllClients();
            clients.removeIf(nextClient -> nextClient.getId().equals(clientId));
            json.save(clients);
        } catch (Exception e) {
            logger.error("Ошибка при удалении клиента: ", e);
            throw new OrganizerException("Ошибка при удалении клиента!");
        }
    }

    /*Редактирование клиента*/
    @Override
    public void updateClient(String updateClientId, Client editedClient) {

        Objects.requireNonNull(updateClientId);
        Objects.requireNonNull(editedClient);

        try {
            for (Client client : retrieveAllClients()) {
                if (updateClientId.equals(client.getId())) {

                    client.setPosition(editedClient.getPosition());
                    client.setEmail(editedClient.getEmail());
                    client.setFio(editedClient.getFio());
                    client.setOrgUnit(editedClient.getOrgUnit());
                    client.setPhoneNumber(editedClient.getPhoneNumber());
                }
            }
            json.save(clients);
        } catch (Exception e) {
            logger.error("Ошибка при редактировании клиента: ", e);
            throw new OrganizerException("Ошибка при редактировании клиента!");
        }
    }

    /*Поиск клиента по номеру телефона*/
    @Override
    public List<Client> getClientByPhoneNumber(String phoneNumber) {

        Objects.requireNonNull(phoneNumber);

        try {
            List<Client> findResult = new ArrayList<>();
            for (Client client : retrieveAllClients()) {
                if (phoneNumber.equals(client.getPhoneNumber())) {
                    findResult.add(client);
                }
            }
            return findResult;
        } catch (Exception e) {
            logger.error("Ошибка при поиске клиента по номеру телефона: ", e);
            throw new OrganizerException("Ошибка при поиске клиента по номеру телефона!");
        }
    }

    /*Поиск клиента по ID*/
    @Override
    public Client getClientById(String id) {

        Objects.requireNonNull(id);

        try {
            Client result = null;
            for (Client client : retrieveAllClients()) {
                if (client.getId().equals(id))
                    result = client;
            }
            return result;
        } catch (Exception e) {
            logger.error("Ошибка при поиске клиента по id: ", e);
            throw new OrganizerException("Ошибка при поиске клиента по id!");
        }
    }
}
