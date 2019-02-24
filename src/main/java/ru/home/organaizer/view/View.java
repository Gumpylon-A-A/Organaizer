package ru.home.organaizer.view;

import ru.home.organaizer.entity.Client;
import ru.home.organaizer.exceptions.OrganizerException;

import java.util.List;

/**
 * Created by Gumpylon Arsalan
 */

public interface View {

    /**
     * @param clients - List<Client>
     */
    void showAllClients(List<Client> clients);

    /**
     * Show commands
     */
    void showCommands();

    /**
     * @param clients - List<Client>
     */
    void showFindResult(List<Client> clients);

    /**
     * Show message
     */
    void showOrganizerIsEmpty();

    /**
     * Show message
     */
    void showSuccessResponse();

    /**
     * Custom exception e - String message
     */
    void showException(OrganizerException e);

    /**
     * @param client - show Client
     */
    void showClient(Client client);
}

