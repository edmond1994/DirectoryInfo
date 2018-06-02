package test.directoryinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

import test.directoryinfo.dao.ContentDAO;
import test.directoryinfo.dao.DirectoryDAO;
import test.directoryinfo.model.Content;
import test.directoryinfo.model.Directory;
import test.directoryinfo.utils.CheckDirectoryUtil;

import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class DirectoryController {

    @Autowired
    private ContentDAO contentDAO;

    @Autowired
    private DirectoryDAO directoryDAO;

    private List<Content> listOfContent;

    /**
     * Главная страница (список добавленных директорий)
     */
    @RequestMapping(value={"/", "/dirs_and_files"}, method = RequestMethod.GET)
    public String showList(Model model) {
        List<Directory> listOfDirectories = directoryDAO.list();
        Collections.reverse(listOfDirectories);

        //Для формы
        model.addAttribute("newDir", new Directory());

        //Список директорий
        model.addAttribute("directories", listOfDirectories);
        return "dirs_and_files";
    }

    /**
     * Добавление директории
     */
    @RequestMapping(value={"/addDirectory"}, method = RequestMethod.POST)
    public String addDirectory(@Valid Directory newDir, BindingResult bindingResult, Model model) {
        String pathInput = newDir.getPath().trim();

        if(bindingResult.hasErrors() || pathInput.isEmpty()){
            model.addAttribute("errorMessage", "Не заполнен путь!");
            return showList(model);
        }

        pathInput = pathInput + (!pathInput.endsWith(File.separator) ? File.separator : "");
        File f = new File(pathInput);

        if(f.exists() && f.isDirectory()) {
            Directory dirAdd = new Directory(pathInput, 0, 0, 0L);            //информация о добавляемой директории
            ArrayList<Content> topLevelContent = new ArrayList<Content>(); //информация о содержании директории первого уровня иерархии
            CheckDirectoryUtil.getDirectoryTopLevelInfo(f, dirAdd, topLevelContent);

            long returnDirId = directoryDAO.save(dirAdd);

            if(topLevelContent.size()> 0) {
                topLevelContent.forEach(item->{contentDAO.save(item, returnDirId);});
            }
            return "redirect:/dirs_and_files";
        } else {
            model.addAttribute("errorMessage", newDir.getPath() + ": "+ "указан файл или несуществующая директория!");
            return showList(model);
        }


    }


}