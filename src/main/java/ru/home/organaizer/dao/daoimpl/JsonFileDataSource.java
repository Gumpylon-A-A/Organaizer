package ru.home.organaizer.dao.daoimpl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.home.organaizer.dao.DataSourceDao;
import ru.home.organaizer.entity.Client;
import ru.home.organaizer.exceptions.OrganizerException;

import java.io.*;
import java.util.List;
import java.util.Objects;

/**
 * Created by Gumpylon Arsalan
 */

public class JsonFileDataSource implements DataSourceDao {
    private static Logger logger = LoggerFactory.getLogger(JsonFileDataSource.class);
    private final Gson gson = new Gson();
    private final File file;

    public JsonFileDataSource(File file) {
        this.file = file;
    }

    /*Проверка на существование файли и кагрузка клиентов из JSON файла*/
    @Override
    public List<Client> load() throws OrganizerException {
        try {
            if (!file.exists()) {
                clear();
            }
        } catch (Exception e) {
            logger.error("Ошибка при создании файла: ", e);
            throw new OrganizerException("Ошибка при создании файла!");
        }

        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String temp;
            while ((temp = br.readLine()) != null) {
                content.append(temp);
            }
        } catch (Exception e) {
            logger.error("Ошибка при загрузке клиентов: ", e);
            throw new OrganizerException("Ошибка при загрузке клиентов!");
        }

        return gson.fromJson(content.toString(), new TypeToken<List<Client>>() {
        }.getType());
    }

    /*Сохранение в JSON Файл*/
    @Override
    public void save(List<Client> clients) {

        Objects.requireNonNull(clients);

        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(gson.toJson(clients));
        } catch (Exception e) {
            logger.error("Ошибка при сохранении: ", e);
            throw new OrganizerException("Ошибка при сохранении!");
        }
    }

    /*Очистка JSON файла*/
    @Override
    public void clear() {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write("");
        } catch (Exception e) {
            logger.error("Ошибка при удалении всех клиентов: ", e);
            throw new OrganizerException("Ошибка при удалении всех клиентов!");
        }
    }
}
