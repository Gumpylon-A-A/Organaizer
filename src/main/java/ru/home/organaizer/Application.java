package ru.home.organaizer;


import ru.home.organaizer.controller.Controller;
import ru.home.organaizer.dao.daoimpl.JsonFileDataSource;
import ru.home.organaizer.entity.Client;
import ru.home.organaizer.exceptions.OrganizerException;
import ru.home.organaizer.model.ClientModel;
import ru.home.organaizer.view.ClientsView;

import java.io.File;

/**
 * Created by Gumpylon Arsalan
 */

public class Application {
    public static void main(String[] args) throws OrganizerException {

        final String filePath;

        if (args.length < 1) {
            filePath = "./src/main/resources/clients.json";
        } else {
            filePath = args[1];
        }

        ClientModel clientModel = new ClientModel(new JsonFileDataSource(new File(filePath)));
        Controller controller = new Controller(clientModel);
        ClientsView clientsView = new ClientsView(controller);


        Client client1 = new Client("4e1ed699-ba05-43c9-ba23-09184ae4167d", "Антонов", "Инспектор", "Сбербанк", "iii@sberbank.organizer", "855710101");
        Client client2 = new Client("b36f5fd4-9f39-44b5-9072-df6cb140a909", "Борисов", "Менеджер", "Сбербанк", "ppp@sberbank.organizer", "855710102");
        Client client3 = new Client("06872e64-3599-4602-8f6b-c474de303904", "Сергеев", "Старший менеджер", "a", "ccc@sberbank.organizer", "855710103");
        Client client4 = new Client("19ef8907-2a53-4df9-8e2c-a45b5cc36c50", "Янков", "Директор", "Сбербанк", "iip@sberbank.organizer", "855710104");

        clientModel.createClient(client1);
        clientModel.createClient(client2);
        clientModel.createClient(client3);
        clientModel.createClient(client4);


        clientsView.menu();


    }
}
