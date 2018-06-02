package test.directoryinfo.model;

import test.directoryinfo.utils.GeneralUtil;

import javax.persistence.*;
import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Объект с информацией о добавленной директории
 */
@Entity
@Table(name = "directory_records")
public class Directory {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) //persistence provider should pick an appropriate strategy for the particular database
    @Column(name = "id")
    private long id;

    /**
     * Путь к директории (базовая директория)
     */
    @Column(name = "path")
    @NotEmpty
    private String path;

    /**
     * Число поддиректорий
     */
    @Column(name = "dircount")
    private int dirCount;

    /**
     * Число файлов
     */
    @Column(name = "filecount")
    private int fileCount;

    /**
     * Общий размер директории
     */
    @Column(name = "totalsize")
    private long totalSize;

    /**
     * Дата добавления
     */
    @Column(name = "addtime")
    private Timestamp addTime;

    public Directory() {
    }

    public Directory(String path) {
        this.path = path;
        this.dirCount = 0;
        this.fileCount = 0;
        this.totalSize = 0L;
    }

    public Directory(String path, int dirCount, int fileCount, long totalSize) {
        this.path = path;
        this.dirCount = dirCount;
        this.fileCount = fileCount;
        this.totalSize = totalSize;
        this.addTime = new Timestamp(System.currentTimeMillis());
    }

    public Directory(String path, int dirCount, int fileCount, long totalSize, Timestamp dateAdded) {
        this.path = path;
        this.dirCount = dirCount;
        this.fileCount = fileCount;
        this.totalSize = totalSize;
        this.addTime = dateAdded;
    }

    public Directory(long id, String path, int dirCount, int fileCount, long totalSize, Timestamp dateAdded) {
        this.id = id;
        this.path = path;
        this.dirCount = dirCount;
        this.fileCount = fileCount;
        this.totalSize = totalSize;
        this.addTime = dateAdded;
    }

    public String getPath() {
        return path;
    }

    /**
     * Увеличение указанного числа директорий
     */
    public void incrementDirCount(int increase)
    {
        this.dirCount += increase;
    }

    /**
     * Увеличение указанного числа файлов
     */
    public void incrementFileCount(int increase)
    {
        this.fileCount += increase;
    }

    /**
     * Увеличение указанного общего размера директории
     */
    public void increaseTotalSize(long increaseSize)
    {
        this.totalSize += increaseSize;
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDirCount() {
        return dirCount;
    }

    public void setDirCount(int dirCount) {
        this.dirCount = dirCount;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public String getTotalSize() {

        return GeneralUtil.humanReadableByteCount(totalSize, true);

    }

    public long getTotalSizeBytes() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

}
