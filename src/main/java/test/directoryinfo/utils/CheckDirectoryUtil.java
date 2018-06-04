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
        boolean isDirectory; //переменная для проверки, работаем ли мы с директорией
        boolean fileIsNotHidden; //текущий файл не скрыт

        if (files != null) {
            for (File currentFile : files) {
                fileIsNotHidden = !currentFile.isHidden();
                fileSize = currentFile.length();
                isDirectory = currentFile.isDirectory();

                if (fileIsNotHidden) {
                    exploredDirectory.incrementFileCount(1);
                    topLevelContent.add(new Content(currentFile.getName(), (isDirectory ? -1L : fileSize)));
                }
                exploredDirectory.increaseTotalSize(fileSize);

                //Спуск вниз по директории
                if (isDirectory) {
                    if(fileIsNotHidden) {
                        exploredDirectory.incrementDirCount(1);
                    }
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
        boolean fileIsNotHidden; //текущий файл не скрыт

        if (files != null) {
            for (File currentFile : files) {
                fileIsNotHidden = !currentFile.isHidden();
                fileSize = currentFile.length();
                if (fileIsNotHidden) {
                    exploredDirectory.incrementFileCount(1);
                }

                exploredDirectory.increaseTotalSize(fileSize);

                //Спуск вниз по директории
                if (currentFile.isDirectory()) {
                    if (fileIsNotHidden) {
                        exploredDirectory.incrementDirCount(1);
                    }
                    getDirectorySubLevelInfo(currentFile, exploredDirectory);
                }
            }
        }
    }

}
