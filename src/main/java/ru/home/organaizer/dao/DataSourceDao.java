package ru.home.organaizer.dao;

import ru.home.organaizer.entity.Client;

import java.util.List;

/**
 * Created by Gumpylon Arsalan
 */

public interface DataSourceDao {

    /**
     * @return - List<Client>
     */
    List<Client> load();

    /**
     * @param clients - List<Client>
     */
    void save(List<Client> clients);

    /**
     * Clear data
     */
    void clear();
}
