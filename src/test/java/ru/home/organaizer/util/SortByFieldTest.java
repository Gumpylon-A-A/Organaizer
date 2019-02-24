package ru.home.organaizer.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.home.organaizer.dao.DataSourceDao;
import ru.home.organaizer.dao.daoimpl.JsonFileDataSource;
import ru.home.organaizer.entity.Client;
import ru.home.organaizer.model.ClientModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gumpylon Arsalan
 */

public class SortByFieldTest {
    private File tmpFile;

    @Before
    public void setUp() throws IOException {
        tmpFile = File.createTempFile("data", null);
        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        Client client1 = new Client("1", "Антонов", "Б", "Сбербанк", "iii@sberbank.organizer", "855710101");
        Client client2 = new Client("2", "Борисов", "В", "Сбербанк", "ppp@sberbank.organizer", "855710102");
        Client client3 = new Client("3", "Сергеев", "Я", "a", "ccc@sberbank.organizer", "855710103");
        Client client4 = new Client("4", "Янков", "А", "Сбербанк", "iip@sberbank.organizer", "855710104");

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
    public void sortByField() {
        SortByField comparator = new SortByField();

        DataSourceDao json = new JsonFileDataSource(tmpFile);
        ClientModel model = new ClientModel(json);

        List<Client> actual = model.retrieveAllClients();
        List<Client> expected = new ArrayList<>();

        expected.add(new Client("4", "Янков", "А", "Сбербанк", "iip@sberbank.organizer", "855710104"));
        expected.add(new Client("1", "Антонов", "Б", "Сбербанк", "iii@sberbank.organizer", "855710101"));
        expected.add(new Client("2", "Борисов", "В", "Сбербанк", "ppp@sberbank.organizer", "855710102"));
        expected.add(new Client("3", "Сергеев", "Я", "a", "ccc@sberbank.organizer", "855710103"));

        comparator.setSortingBy(SortByField.Order.position);
        actual.sort(comparator);

        Assert.assertEquals(expected, actual);
    }
}