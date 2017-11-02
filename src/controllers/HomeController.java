package controllers;

import com.yet.dapper.MyConnection;
import com.yet.dbhelper.DbHelper;
import model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String Index(HttpSession session, ModelMap map) throws SQLException {

        map.put("name", "李四"); //向jsp页面传值，前台使用request.getAttribute("name")来取值
        session.setAttribute("name", "这是session"); //设置session
        return "/home/index";
    }

    @RequestMapping(value = "/home/hello")
    public String Hello(HttpSession session) throws SQLException {
        Object data = session.getAttribute("name"); //获取session
        System.out.println(data);

        MyConnection conn = DbHelper.GetConn();
        Map  mpp = conn.QueryMap("SELECT * FROM people");

        conn.Close();

        return "/home/hello";
    }

    @RequestMapping(value = "/home/say")
    public String Say() throws Exception {
        throw new Exception("错误");
    }

}
