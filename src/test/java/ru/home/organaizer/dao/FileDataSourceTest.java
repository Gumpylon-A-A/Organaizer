package ru.home.organaizer.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.home.organaizer.dao.daoimpl.JsonFileDataSource;
import ru.home.organaizer.entity.Client;
import ru.home.organaizer.model.ClientModel;
import ru.home.organaizer.util.SortByField;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gumpylon Arsalan
 */

public class FileDataSourceTest {

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
    public void load() {

        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        List<Client> actual = json.load();
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
    public void load_NULL() {

        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        model.deleteAllClients();

        List<Client> clients = json.load();

        Assert.assertNull(clients);
    }

    @Test
    public void load_NOT_NULL() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);

        List<Client> clients = json.load();

        Assert.assertNotNull(clients);
    }

    @Test
    public void save() {

        DataSourceDao json = new JsonFileDataSource(tmpFile);

        json.clear();
        Assert.assertNull(json.load());

        List<Client> actual = new ArrayList<>();
        List<Client> expected;

        actual.add(new Client("4e1ed699-ba05-43c9-ba23-09184ae4167d", "Антонов", "Инспектор", "Сбербанк", "iii@sberbank.organizer", "855710101"));
        actual.add(new Client("b36f5fd4-9f39-44b5-9072-df6cb140a909", "Борисов", "Менеджер", "Сбербанк", "ppp@sberbank.organizer", "855710102"));
        actual.add(new Client("06872e64-3599-4602-8f6b-c474de303904", "Сергеев", "Старший менеджер", "a", "ccc@sberbank.organizer", "855710103"));
        actual.add(new Client("19ef8907-2a53-4df9-8e2c-a45b5cc36c50", "Янков", "Директор", "Сбербанк", "iip@sberbank.organizer", "855710104"));

        json.save(actual);
        expected = json.load();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void clear() {
        DataSourceDao json = new JsonFileDataSource(tmpFile);

        json.clear();

        Assert.assertNull(json.load());
    }
}