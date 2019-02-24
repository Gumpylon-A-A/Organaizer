package ru.home.organaizer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.home.organaizer.entity.Client;
import ru.home.organaizer.exceptions.OrganizerException;

import java.util.Comparator;
import java.util.Objects;

/**
 * Created by Gumpylon Arsalan
 */

public class SortByField implements Comparator<Client> {

    /*ENUM полей для сортировки*/
    public enum Order {
        id, fio, position, orgUnit, email
    }

    private static Logger logger = LoggerFactory.getLogger(SortByField.class);
    private Order sortingBy;

    /*Сортировка*/
    @Override
    public int compare(Client firstClient, Client secondClient) {

        Objects.requireNonNull(firstClient);
        Objects.requireNonNull(secondClient);

        try {
            switch (sortingBy) {
                case id:
                    return firstClient.getId().compareTo(secondClient.getId());
                case fio:
                    return firstClient.getFio().compareTo(secondClient.getFio());
                case position:
                    return firstClient.getPosition().compareTo(secondClient.getPosition());
                case orgUnit:
                    return firstClient.getOrgUnit().compareTo(secondClient.getOrgUnit());
                case email:
                    return firstClient.getEmail().compareTo(secondClient.getEmail());
            }
        } catch (Exception e) {
            logger.error("Ошибка при сортировке: ", e);
            throw new OrganizerException("Ошибка при сортировке!");
        }
        return 0;
    }

    public void setSortingBy(Order sortingBy) {
        this.sortingBy = sortingBy;
    }
}
