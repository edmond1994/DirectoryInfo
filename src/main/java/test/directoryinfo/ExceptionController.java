package test.directoryinfo;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.net.ConnectException;


@ControllerAdvice
public class ExceptionController implements ErrorController {

    private static final String ERROR_PATH = "error_view";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @ExceptionHandler({CommunicationsException.class, ConnectException.class})
    public ModelAndView getConnectionUnavailable(Exception ex) {

        ModelAndView errorMav = new ModelAndView(ERROR_PATH);
        errorMav.addObject("errorMessage", "Ошибка подключения к серверу MySQL");
        errorMav.addObject("exceptionMessage", ex.getLocalizedMessage());

        return errorMav;
    }
}


