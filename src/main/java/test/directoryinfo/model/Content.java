package test.directoryinfo.model;

import test.directoryinfo.utils.GeneralUtil;

import javax.persistence.*;

/**
 * Объект с информацией об элементе содержимого директории
 */
@Entity
@Table(name = "content_records")
public class Content implements Comparable<Content>{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) //persistence provider should pick an appropriate strategy for the particular database
    @Column(name = "id")
    private long id;

    /**
     * Имя элемента (файла или директории)
     */
    @Column(name = "name")
    private String name;

    /**
     * Собственный размер элемента (-1 у директории)
     */
    @Column(name = "size")
    private long size;


    /**
     * ID записи о директории (Directory)
     */
    private long directoryId;

    public Content() {
    }

    public Content(String name) {
        this.name = name;
        this.size = -1;
    }

    public Content(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public Content(String name, long size, long directoryId) {
        this.name = name;
        this.size = size;
        this.directoryId = directoryId;
    }

    public Content(long id, String name, long size, long directoryId) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.directoryId = directoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {

        return GeneralUtil.humanReadableByteCount(size, true);
    }

    public long getSizeBytes()
    {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }


    public long getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(long directoryId) {
        this.directoryId = directoryId;
    }

    /**
     *  Сравнение названий двух объектов
     */
    @Override
    public int compareTo(Content other) {
        //Если 1 из объектов - элемент-поддиректория, то он смещается назад
        if((this.size < 0L) && (other.size > 0L)){
            return -1;
        }

        if((this.size > 0L) && (other.size < 0L)){
            return 1;
        }

        //TODO: непосредственно сравнение имен

        return 0;
    }
}
