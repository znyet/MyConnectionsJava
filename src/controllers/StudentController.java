package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StudentController {

    @RequestMapping(value = "/student/index")
    public String Index() {
        return "/student/index";
    }
}
