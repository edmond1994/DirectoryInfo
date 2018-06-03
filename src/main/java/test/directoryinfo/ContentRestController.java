package test.directoryinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import test.directoryinfo.dao.ContentDAO;
import test.directoryinfo.message.AjaxResponse;
import test.directoryinfo.model.Content;

import java.util.Collections;
import java.util.List;


@RestController
public class ContentRestController {

    @Autowired
    private ContentDAO contentDAO;

    /**
     * Показ файлов и поддиректорий в директории
     */
    @RequestMapping(value={"/showContents"}, method = RequestMethod.POST)
    public AjaxResponse getResource(long directoryId) { //@RequestBody not needed
        List<Content> tempList = contentDAO.list(directoryId);
        Collections.sort(tempList);
        return new AjaxResponse("Done", tempList);
    }
}