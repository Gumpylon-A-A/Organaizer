package ru.home.organaizer.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.home.organaizer.controller.Controller;
import ru.home.organaizer.entity.Client;
import ru.home.organaizer.exceptions.OrganizerException;
import ru.home.organaizer.util.SortByField;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by Gumpylon Arsalan
 */

public class ClientsView implements View {

    private Controller controller;
    private static Logger logger = LoggerFactory.getLogger(ClientsView.class);
    private static final Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("\\d+", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public ClientsView(Controller controller) {
        this.controller = controller;
    }

    /*Меню - основной интерфейс*/
    public void menu() {

        StringBuilder commandLine;
        Scanner sc;

        while (true) {
            try {
                sc = new Scanner(System.in);
                System.out.println("Введите help для получения списка команд!");

                commandLine = new StringBuilder();
                commandLine.append(sc.next());

                if (Stream.of("help", "list", "sortedlist", "delete", "clear", "insert", "find", "update", "findid", "exit").noneMatch(commandLine.toString()::equalsIgnoreCase)) {
                    System.out.println("Ошибка, такой команды нет!");
                } else if ((controller.onCheckOrganizerIsEmpty()) && Stream.of("list", "insert", "help", "exit").noneMatch(commandLine.toString()::equalsIgnoreCase)) {
                    showOrganizerIsEmpty();
                } else {
                    switch (commandLine.toString().toLowerCase()) {

                        case "help":
                            showCommands();
                            break;
                        case "list":
                            showAllClients(controller.onShowAllClients());
                            break;
                        case "sortedlist":
                            SortByField.Order order = readOrderFromConsole(sc);
                            if (order != null)
                                showAllClients(controller.onShowSortedAllClients(order));
                            break;
                        case "delete":
                            String deleteId = readAndCheckIdFromConsole(sc);
                            if (deleteId != null) {
                                controller.onClientDelete(deleteId);
                            } else {
                                System.out.println("Такого ID не существует!");
                            }
                            break;
                        case "clear":
                            System.out.println("Удаление всех пользователей");
                            controller.onDeleteAllClients();
                            break;
                        case "insert":
                            controller.onCreateClient(readClientFromConsole(sc));
                            showSuccessResponse();
                            break;
                        case "find":
                            System.out.println("Введите номер телефона для поиска: ");
                            showFindResult(controller.onFindByPhoneNumber(readPhoneNumberOrEmail(sc, VALID_PHONE_NUMBER_REGEX)));
                            break;
                        case "update":
                            System.out.println("Редактирование клиента:");
                            String updateClientId = readAndCheckIdFromConsole(sc);
                            if (updateClientId != null) {
                                controller.onUpdateClient(updateClientId, readClientFromConsole(sc));
                                showSuccessResponse();
                                showClient(controller.onFindClient(updateClientId));
                            } else {
                                System.out.println("Такого ID не существует!");
                                break;
                            }
                            break;
                        case "findid":
                            System.out.println("Введите id для поиска: ");
                            String findId = readAndCheckIdFromConsole(sc);
                            if (findId != null) {
                                showClient(controller.onFindClient(findId));
                            } else {
                                System.out.println("Такого ID не существует!");
                                break;
                            }
                            break;
                        case "exit":
                            System.exit(0);
                            break;

                        default:
                            System.out.println("Ошибка, такой команды нет!");
                            break;
                    }
                }
            } catch (OrganizerException e) {
                showException(e);
            }
        }
    }

    /*Чтение ID с консоли и проверка на существование*/
    private String readAndCheckIdFromConsole(Scanner scanner) throws OrganizerException {
        try {
            System.out.println("Введите ID:");
            String id = scanner.next();

            return controller.onCheckIdForExistence(id) ? id : null;
        } catch (Exception e) {
            logger.error("Ошибка при чтении и проверке ID: ", e);
            throw new OrganizerException("Ошибка при вводе ID");
        }
    }

    /*Чтение клиениа с консоли*/
    private Client readClientFromConsole(Scanner scanner) throws OrganizerException {
        try {
            String id = UUID.randomUUID().toString();
            System.out.println("Введите ФИО:");
            String fio = scanner.next();
            System.out.println("Введите должность:");
            String position = scanner.next();
            System.out.println("Введите наименование организации:");
            String orgUnit = scanner.next();
            System.out.println("Введите e-mail:");
            String email = readPhoneNumberOrEmail(scanner, VALID_EMAIL_ADDRESS_REGEX);
            System.out.println("Введите телефон:");
            return new Client(id, fio, position, orgUnit, email, readPhoneNumberOrEmail(scanner, VALID_PHONE_NUMBER_REGEX));
        } catch (Exception e) {
            logger.error("Ошибка при вводе клиента: ", e);
            throw new OrganizerException("Ошибка при вводе клиента!");
        }
    }

    /*Чтение номера телефона или email с консоли и проверка в завиимости от regex*/
    private String readPhoneNumberOrEmail(Scanner scanner, Pattern regex) throws OrganizerException {
        try {
            StringBuilder read = new StringBuilder();

            while (!regex.matcher(read.append(scanner.next()).toString()).matches()) {

                if (regex.equals(VALID_EMAIL_ADDRESS_REGEX)) {
                    System.out.println("Ошибка ввода: Введите корректный email!");
                }
                if (regex.equals(VALID_PHONE_NUMBER_REGEX)) {
                    System.out.println("Ошибка ввода: Введите корректный номер телефона!");
                }
                read = new StringBuilder();
            }
            return read.toString();

        } catch (Exception e) {
            logger.error("Ошибка при вводе email или номера телефона: ", e);
            if (regex.equals(VALID_EMAIL_ADDRESS_REGEX)) {
                throw new OrganizerException("Ошибка ввода: Введите корректный email!");
            } else {
                throw new OrganizerException("Ошибка ввода: Введите корректный номер телефона!");
            }
        }
    }

    /*Вывод результата*/
    @Override
    public void showFindResult(List<Client> clients) {
        if (clients != null && clients.size() > 0) {
            for (Client client : clients) {
                System.out.println("===================================================================================================================================================================================================================================================================");
                System.out.println(client);
                System.out.println("===================================================================================================================================================================================================================================================================");
            }
        } else {
            System.out.println("Пользователи не найден!");
        }
    }

    /*Вывод команд*/
    @Override
    public void showCommands() {
        System.out.println("list: Отобразить клиентов.");
        System.out.println("sortedlist: Отсортировать и отобразить клиентов.");
        System.out.println("insert: Добавление клиента.");
        System.out.println("delete: Удаление клиента по id.");
        System.out.println("clear: Удаление всех клиентов.");
        System.out.println("update: Редактирование клиента.");
        System.out.println("find: Поиск клиента по номеру телефона.");
        System.out.println("findid: Поиск клиента по id.");
        System.out.println("exit : Выход.");
    }

    /**/
    private SortByField.Order readOrderFromConsole(Scanner scanner) throws OrganizerException {
        try {
            SortByField.Order order = null;
            System.out.println("1 - сотировать по ID");
            System.out.println("2 - сотировать по ФИО");
            System.out.println("3 - сотировать по должности");
            System.out.println("4 - сотировать по организации");
            System.out.println("5 - сотировать по почте");
            System.out.println("6 - сотировать по номеру телефона");
            String commandLine = scanner.next();
            switch (commandLine) {
                case "1":
                    order = SortByField.Order.id;
                    break;
                case "2":
                    order = SortByField.Order.fio;
                    break;
                case "3":
                    order = SortByField.Order.position;
                    break;
                case "4":
                    order = SortByField.Order.orgUnit;
                    break;
                case "5":
                    order = SortByField.Order.email;
                    break;
                default:
                    System.out.println("Ошибка, неверный ввод!");
                    break;
            }
            return order;
        } catch (Exception e) {
            logger.error("Ошибка при сортировке: ", e);
            throw new OrganizerException("Ошибка при сортировке!");
        }
    }

    /*Вывод если органайзер пуст*/
    @Override
    public void showOrganizerIsEmpty() {
        System.out.println("Органайзер пуст! Добавьте клиентов!");
    }

    /*Вывод если операция успешна*/
    @Override
    public void showSuccessResponse() {
        System.out.println("Успешно выполнено!");
    }

    /*Вывод ошибки пользователю*/
    @Override
    public void showException(OrganizerException e) {
        System.out.println(e.toString());
    }

    /*Вывод всех клиентов*/
    @Override
    public void showAllClients(List<Client> clients) {
        if (clients != null) {
            showSuccessResponse();
            System.out.println("Список всех клиентов: ");
            for (Client client : clients) {
                System.out.println("===================================================================================================================================================================================================================================================================");
                System.out.println(client);
                System.out.println("===================================================================================================================================================================================================================================================================");
            }
        } else {
            showOrganizerIsEmpty();
        }
    }

    /*Вывод результата поиска по ID*/
    @Override
    public void showClient(Client client) {
        if (client != null) {
            showSuccessResponse();
            System.out.println("Результат: ");
            System.out.println("===================================================================================================================================================================================================================================================================");
            System.out.println(client);
            System.out.println("===================================================================================================================================================================================================================================================================");
        } else {
            System.out.println("Клиент не найден!");
        }
    }
}

