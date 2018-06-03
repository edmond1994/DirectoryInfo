package test.directoryinfo.model;

import test.directoryinfo.utils.GeneralUtil;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Объект с информацией об элементе содержимого директории
 */
@Entity
@Table(name = "content_records")
public class Content implements Comparable<Content>{


    //Паттерн, выводящий в первой группе нецифровые значения, а во второй - цифровые
    private static final Pattern DIGITSPATTERN = Pattern.compile("(\\D*)(\\d*)");
    //Выражения для отделения расширения файла от его имени
    private static final String fileSplitExpression = "\\.(?=[^\\.]+$)";


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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

        return GeneralUtil.humanReadableByteCount(size);
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

        //Сравнение имен с учетом цифровых значений

        String[] firstNameAndExtension  = this.name.toLowerCase().split(fileSplitExpression);
        String[] secondNameAndExtension = other.name.toLowerCase().split(fileSplitExpression);
        boolean bothFilesHaveExtensions = (firstNameAndExtension.length > 1) && (secondNameAndExtension.length > 1);

        int namesCompare = compareNames(firstNameAndExtension[0], secondNameAndExtension[0]);

        //Если до сих пор имена файлов одинаковы, учитываем расширения
        return (namesCompare != 0) ? namesCompare :
                bothFilesHaveExtensions ? compareNames(firstNameAndExtension[1] , secondNameAndExtension[1]) :
                        compareNames(String.join(".", firstNameAndExtension) , String.join(".", secondNameAndExtension));
    }

    /**
     *  Сравнение строк с учетом цифровых значений
     */
    private static int compareNames(String firstName, String secondName) {
        Matcher thisMatcher = DIGITSPATTERN.matcher(firstName);
        Matcher otherMatcher = DIGITSPATTERN.matcher(secondName);

        //Сравнение идет, пока есть подходящие подстроки
        while (thisMatcher.find() && otherMatcher.find()) {

            //Группы 1 Matcher-ов - нечисловые фрагменты имен
            //сортировка по алфавиту
            int nonDigitComparisonResult = thisMatcher.group(1).compareTo(otherMatcher.group(1));
            if (0 != nonDigitComparisonResult) {
                return nonDigitComparisonResult;
            }

            //Группы 2 Matcher-ов - числовые фрагменты имен
            if (thisMatcher.group(2).isEmpty()) {
                return otherMatcher.group(2).isEmpty() ? 0 : -1;
            } else if (otherMatcher.group(2).isEmpty()) {
                return +1;
            }

            //Большим считается имя с большим значением числового фрагмента
            BigInteger n1 = new BigInteger(thisMatcher.group(2));
            BigInteger n2 = new BigInteger(otherMatcher.group(2));
            int digitComparisonResult = n1.compareTo(n2);
            if (0 != digitComparisonResult) {
                return digitComparisonResult;
            }
        }

        //Если одна строка является подстрокой другой, то первая считается идущей перед второй
        return thisMatcher.hitEnd() && otherMatcher.hitEnd() ? 0 :
                thisMatcher.hitEnd() ? -1 : +1;
    }

}
