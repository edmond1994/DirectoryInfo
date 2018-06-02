package test.directoryinfo.utils;

import test.directoryinfo.model.Content;
import test.directoryinfo.model.Directory;

import java.io.File;
import java.util.ArrayList;

/**
 * Получение данных о директории и файлах в ней
 */
public class CheckDirectoryUtil {

    /**
     *  Обработка подкаталогов первого уровня иерархии в директории exploredDirectory
     */
    public static void getDirectoryTopLevelInfo(File directoryFile, Directory exploredDirectory, ArrayList<Content> topLevelContent){
        File[] files = directoryFile.listFiles();

        long fileSize; //переменная хранения длин файлов

        if (files != null) {
            for (File currentFile : files) {
                exploredDirectory.incrementFileCount(1);
                fileSize = currentFile.length();

                topLevelContent.add(new Content(currentFile.getName(), (fileSize > 0 ? fileSize : -1L)));
                exploredDirectory.increaseTotalSize(fileSize);

                //Спуск вниз по директории
                if (currentFile.isDirectory()) {
                    exploredDirectory.incrementDirCount(1);
                    getDirectorySubLevelInfo(currentFile, exploredDirectory);
                }
            }
        }

        //Вычет из общего числа файлов числа директорий
        exploredDirectory.incrementFileCount(-exploredDirectory.getDirCount());
    }

    /**
     *  Обработка подкаталогов второго (и глубже) уровня иерархии в директории exploredDirectory (файл поддиректории directoryFile)
     */
    private static void getDirectorySubLevelInfo(File directoryFile, Directory exploredDirectory){
        File[] files = directoryFile.listFiles();

        long fileSize; //переменная хранения длин файлов

        if (files != null) {
            for (File currentFile : files) {
                exploredDirectory.incrementFileCount(1);
                fileSize = currentFile.length();

                exploredDirectory.increaseTotalSize(fileSize);

                //Спуск вниз по директории
                if (currentFile.isDirectory()) {
                    exploredDirectory.incrementDirCount(1);
                    getDirectorySubLevelInfo(currentFile, exploredDirectory);
                }
            }
        }
    }

}
