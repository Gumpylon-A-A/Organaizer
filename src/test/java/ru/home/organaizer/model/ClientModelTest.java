package ru.home.organaizer.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ru.home.organaizer.dao.DataSourceDao;
import ru.home.organaizer.dao.daoimpl.JsonFileDataSource;
import ru.home.organaizer.entity.Client;
import ru.home.organaizer.util.SortByField;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gumpylon Arsalan
 */

public class ClientModelTest {

    private File tmpFile;

    @Before
    public void setUp() throws IOException {
        tmpFile = File.createTempFile("data", null);
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        Client client1 = new Client("4e1ed699-ba05-43c9-ba23-09184ae4167d", "Антонов", "Инспектор", "Сбербанк", "iii@sberbank.organizer", "855710101");
        Client client2 = new Client("b36f5fd4-9f39-44b5-9072-df6cb140a909", "Борисов", "Менеджер", "Сбербанк", "ppp@sberbank.organizer", "855710102");
        Client client3 = new Client("06872e64-3599-4602-8f6b-c474de303904", "Сергеев", "Старший менеджер", "a", "ccc@sberbank.organizer", "855710103");
        Client client4 = new Client("19ef8907-2a53-4df9-8e2c-a45b5cc36c50", "Янков", "Директор", "Сбербанк", "iip@sberbank.organizer", "855710104");

        model.createClient(client1);
        model.createClient(client2);
        model.createClient(client3);
        model.createClient(client4);
    }

    @After
    public void tearDown() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        model.deleteAllClients();

        tmpFile.deleteOnExit();
    }

    @Test
    public void checkOrganizerIsEmpty() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        Assert.assertFalse(model.checkOrganizerIsEmpty());
    }

    @Test
    public void checkOrganizerIsEmpty_NOT_NULL() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        Assert.assertTrue(!model.checkOrganizerIsEmpty());
    }

    @Test
    public void sortByField() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);
        List<Client> actual = model.retrieveAllClients();

        List<Client> expected = new ArrayList<>();

        expected.add(new Client("4e1ed699-ba05-43c9-ba23-09184ae4167d", "Антонов", "Инспектор", "Сбербанк", "iii@sberbank.organizer", "855710101"));
        expected.add(new Client("b36f5fd4-9f39-44b5-9072-df6cb140a909", "Борисов", "Менеджер", "Сбербанк", "ppp@sberbank.organizer", "855710102"));
        expected.add(new Client("06872e64-3599-4602-8f6b-c474de303904", "Сергеев", "Старший менеджер", "a", "ccc@sberbank.organizer", "855710103"));
        expected.add(new Client("19ef8907-2a53-4df9-8e2c-a45b5cc36c50", "Янков", "Директор", "Сбербанк", "iip@sberbank.organizer", "855710104"));

        model.sortByField(SortByField.Order.fio, expected);
        model.sortByField(SortByField.Order.fio, actual);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void checkClientForExistence() {

        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        Assert.assertTrue(model.checkClientForExistence("4e1ed699-ba05-43c9-ba23-09184ae4167d"));
    }

    @Test
    public void checkClientForExistence_DALSE() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        Assert.assertFalse(model.checkClientForExistence("4e1ed699-ba05-43c9-ba23-09184ae411231267d"));
    }

    @Test
    public void retrieveAllClients() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);
        List<Client> actual = model.retrieveAllClients();
        List<Client> expected = new ArrayList<>();

        expected.add(new Client("4e1ed699-ba05-43c9-ba23-09184ae4167d", "Антонов", "Инспектор", "Сбербанк", "iii@sberbank.organizer", "855710101"));
        expected.add(new Client("b36f5fd4-9f39-44b5-9072-df6cb140a909", "Борисов", "Менеджер", "Сбербанк", "ppp@sberbank.organizer", "855710102"));
        expected.add(new Client("06872e64-3599-4602-8f6b-c474de303904", "Сергеев", "Старший менеджер", "a", "ccc@sberbank.organizer", "855710103"));
        expected.add(new Client("19ef8907-2a53-4df9-8e2c-a45b5cc36c50", "Янков", "Директор", "Сбербанк", "iip@sberbank.organizer", "855710104"));

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void retrieveAllClients_NULL() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        model.deleteAllClients();
        List<Client> actual = model.retrieveAllClients();

        Assert.assertNull(actual);
    }

    @Test
    public void retrieveAllClients_NOT_NULL() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        List<Client> actual = model.retrieveAllClients();

        Assert.assertNotNull(actual);
    }

    @Test
    public void retrieveAllClients_HOW_MANY_CLIENTS() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        int expected = 4;
        int actual = model.retrieveAllClients().size();

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void createClient() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        model.createClient(new Client("1", "`1", "1", "1", "mail@mail.ru", "01"));

        Assert.assertNotNull(model.getClientById("1"));
    }

    @Test
    public void createClient_MATCH_FIELDS() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);
        Client newClient = new Client("1", "2", "3", "4", "mail@mail.ru", "01");
        model.createClient(newClient);

        Assert.assertEquals("1", newClient.getId());
        Assert.assertEquals("2", newClient.getFio());
        Assert.assertEquals("3", newClient.getPosition());
        Assert.assertEquals("4", newClient.getOrgUnit());
        Assert.assertEquals("mail@mail.ru", newClient.getEmail());
        Assert.assertEquals("01", newClient.getPhoneNumber());
    }

    @Test
    public void createClient_INCREMENT_CLIENTS() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);
        int expected = 5;
        Client newClient = new Client("1", "2", "3", "4", "mail@mail.ru", "01");

        model.createClient(newClient);
        int actual = model.retrieveAllClients().size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createClient_ID_NOT_EMPTY() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);
        Client newClient = new Client("123", "123", "123", "123", "123", "123");

        model.createClient(newClient);
        Assert.assertNotNull(newClient.getId());
    }

    @Test
    public void createClient_FIO_NOT_EMPTY() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);
        Client newClient = new Client("123", "123", "123", "123", "123", "123");

        model.createClient(newClient);
        Assert.assertNotNull(newClient.getFio());
    }

    @Test
    public void createClient_POSITION_NOT_EMPTY() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);
        Client newClient = new Client("123", "123", "123", "123", "123", "123");

        model.createClient(newClient);
        Assert.assertNotNull(newClient.getPosition());
    }

    @Test
    public void createClient_EMAIL_NOT_EMPTY() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);
        Client newClient = new Client("123", "123", "123", "123", "123", "123");

        model.createClient(newClient);
        Assert.assertNotNull(newClient.getEmail());
    }

    @Test
    public void createClient_PHONENUMBER_NOT_EMPTY() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);
        Client newClient = new Client("123", "123", "123", "123", "123", "123");

        model.createClient(newClient);
        Assert.assertNotNull(newClient.getPhoneNumber());
    }

    @Test
    public void deleteAllClients() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);
        model.deleteAllClients();

        Assert.assertNull(model.retrieveAllClients());
    }

    @Test
    public void deleteClientById() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        model.deleteClientById("4e1ed699-ba05-43c9-ba23-09184ae4167d");

        Assert.assertNull(model.getClientById("4e1ed699-ba05-43c9-ba23-09184ae4167d"));
    }

    @Test
    public void deleteClientById_DECREMENT_CLIENTS() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        int expected = 3;
        model.deleteClientById("4e1ed699-ba05-43c9-ba23-09184ae4167d");
        int actual = model.retrieveAllClients().size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateClient() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        Client client = new Client("4e1ed699-ba05-43c9-ba23-09184ae4167d", "1", "2", "3", "iii@sberbank.organizer", "4");
        model.updateClient("4e1ed699-ba05-43c9-ba23-09184ae4167d", new Client("4e1ed699-ba05-43c9-ba23-09184ae4167d", "1", "2", "3", "iii@sberbank.organizer", "4"));
        Assert.assertEquals(client, model.getClientById("4e1ed699-ba05-43c9-ba23-09184ae4167d"));
    }

    @Test
    public void updateClient_VALIDATE_UPDATED_FIO() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        model.updateClient("4e1ed699-ba05-43c9-ba23-09184ae4167d", new Client("4e1ed699-ba05-43c9-ba23-09184ae4167d", "1", "2", "3", "iii@sberbank.organizer", "4"));
        Assert.assertEquals("1", model.getClientById("4e1ed699-ba05-43c9-ba23-09184ae4167d").getFio());
    }

    @Test
    public void updateClient_VALIDATE_UPDATED_POSITION() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        model.updateClient("4e1ed699-ba05-43c9-ba23-09184ae4167d", new Client("4e1ed699-ba05-43c9-ba23-09184ae4167d", "1", "2", "3", "iii@sberbank.organizer", "4"));

        Assert.assertEquals("2", model.getClientById("4e1ed699-ba05-43c9-ba23-09184ae4167d").getPosition());
    }

    @Test
    public void updateClient_VALIDATE_UPDATED_ORGUNIT() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        model.updateClient("4e1ed699-ba05-43c9-ba23-09184ae4167d", new Client("4e1ed699-ba05-43c9-ba23-09184ae4167d", "1", "2", "3", "iii@sberbank.organizer", "4"));
        Assert.assertEquals("3", model.getClientById("4e1ed699-ba05-43c9-ba23-09184ae4167d").getOrgUnit());
    }

    @Test
    public void updateClient_VALIDATE_UPDATED_EMAIL() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        model.updateClient("4e1ed699-ba05-43c9-ba23-09184ae4167d", new Client("4e1ed699-ba05-43c9-ba23-09184ae4167d", "1", "2", "3", "iii@sberbank.organizer", "4"));

        Assert.assertEquals("iii@sberbank.organizer", model.getClientById("4e1ed699-ba05-43c9-ba23-09184ae4167d").getEmail());
    }

    @Test
    public void updateClient_VALIDATE_UPDATED_PHONE_NUMBER() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        model.updateClient("4e1ed699-ba05-43c9-ba23-09184ae4167d", new Client("4e1ed699-ba05-43c9-ba23-09184ae4167d", "1", "2", "3", "iii@sberbank.organizer", "4"));

        Assert.assertEquals("4", model.getClientById("4e1ed699-ba05-43c9-ba23-09184ae4167d").getPhoneNumber());
    }

    @Test
    public void getClientByPhoneNumber() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        Assert.assertNotNull(model.getClientByPhoneNumber("855710101"));
    }

    @Test
    public void getClientByPhoneNumber_NULL() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        Assert.assertNotNull(model.getClientByPhoneNumber("855710101231231"));
    }
}