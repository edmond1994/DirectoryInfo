package test.directoryinfo.utils;

import org.apache.commons.lang.StringEscapeUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Дополнительные функции для обработки записей из базы
 */
public class GeneralUtil {

    /**
     * Формат выводимого размера файла/директории
     */
    private static DecimalFormat numFormat; //= new DecimalFormat("0,##");
    static {
        //определение формата
        Locale locale = new Locale("ru", "RU");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        symbols.setDecimalSeparator(',');

        String fileNumPattern = "0.##";
        numFormat = new DecimalFormat(fileNumPattern, symbols);
    }

    /**
     * Получение читаемого формата размера файла размером в bytes байтов, при использовании метрической системы internationalSystem=true
     */
    public static String humanReadableByteCount(long bytes, boolean internationalSystem) {
        int measure = internationalSystem ? 1000 : 1024; //Total Commander uses 1024 without i

        //для директорий в Content
        if (bytes < 0L) {
            return StringEscapeUtils.escapeHtml("<DIR>");
        }

        if (bytes < measure) {
            return bytes + "B";
        }

        int exp = (int) (Math.log(bytes) / Math.log(measure));

        String pre = (internationalSystem ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (internationalSystem ? "" : "i") + "B";

        return pringFileLength(bytes / Math.pow(measure, exp))+pre;
    }


    public static String humanReadableByteCount(long bytes) {
        return humanReadableByteCount(bytes, true);
    }

    /**
     * Печать длины num файла
     */
    private static String pringFileLength(double num)
    {
        if(num == (long) num) {
            return String.format("%d", (long) num);
        }
        else {
            return numFormat.format(num);
        }
    }
}
