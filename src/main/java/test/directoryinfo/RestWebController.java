package test.directoryinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.directoryinfo.dao.ContentDAO;
import test.directoryinfo.message.AjaxResponse;


@RestController
public class RestWebController {

    @Autowired
    private ContentDAO contentDAO;

    /**
     * Показ файлов и поддиректорий в директории
     */
    @RequestMapping(value={"/showContents"}, method = RequestMethod.POST)
    public AjaxResponse getResource(long directoryId) { //@RequestBody not needed
        return new AjaxResponse("Done", contentDAO.list(directoryId));
    }
}