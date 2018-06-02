package test.directoryinfo.dao;

import test.directoryinfo.model.Content;

import java.util.List;

/**
 * Интерфейс для работы с элементом содержимого директории
 */
public interface ContentDAO {

    /**
     * Сохранение элемента в базу
     */
    public void save(Content contact);

    /**
     * Сохранение элемента в базу c id директории = directoryID
     */
    public void save(Content contact, Long directoryId);

    /**
     * Получение элементов у директории с id = directoryID
     */
    public List<Content> list(long directoryID);


}
