package test.directoryinfo.dao;

import test.directoryinfo.model.Directory;

import java.util.List;

/**
 * Интерфейс для работы с добавленной директорией
 */
public interface DirectoryDAO {

    /**
     * Сохранение информации о директории в базу
     */
    public long save(Directory directory);

    /**
     * Получение занесенных в базу директорий
     */
    public List<Directory> list();

}
