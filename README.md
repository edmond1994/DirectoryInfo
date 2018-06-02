# DirectoryInfo

WORK IN PROGRESS

Используется версия MySQL не ниже 5.5.27 (порт сервера 3306).

При разработке использовался JDK версии 1.8.0_152.

Следует создать схему directoryinfo и для данной схемы выполнить следующий скрипт:

  DROP TABLE IF EXISTS content_records;
  DROP TABLE IF EXISTS directory_records;

  CREATE TABLE directory_records (
    id INT(20) NOT NULL AUTO_INCREMENT,
    path VARCHAR(600) NOT NULL,
    dircount BIGINT NOT NULL,
    filecount BIGINT NOT NULL,
    totalsize BIGINT NOT NULL,
    addtime TIMESTAMP NOT NULL,
    PRIMARY KEY (id))
  DEFAULT CHARACTER SET = utf8;

  CREATE TABLE content_records (
    id INT(20) NOT NULL AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    size BIGINT NOT NULL DEFAULT -1,
    directoryid INT(20) NOT NULL,
    PRIMARY KEY (id),
    INDEX directory_fk_idx (directoryid ASC),
    CONSTRAINT directory_fk
      FOREIGN KEY (directoryid)
      REFERENCES directory_records(id)
      ON DELETE CASCADE)
  DEFAULT CHARACTER SET = utf8;
  
Используется версия Maven не ниже 3 (в процессе разработки использовалась 3.3.9) и Spring Boot 1.5.8
В корневом каталогие приложения (DirectoriesInfo) выполнить команду `mvn spring-boot:run`.
Приложение будет запущено по адресу `localhost:8080/dirs_and_files`
  

