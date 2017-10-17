package controllers;

import com.yet.dapper.DapperPage;
import com.yet.dapper.MyConnection;
import com.yet.dbhelper.DbHelper;
import model.peopleT;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Console;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String Index() throws SQLException {

        MyConnection conn = DbHelper.GetConn();

        String sql = "SELECT * FROM people WHERE Id=?";
        Map mp = conn.QueryMap(sql,1);

        System.out.print(mp.get("Name"));

        conn.Close();
        return "/home/index";
    }

    @RequestMapping(value = "/home/hello")
    public String Hello() {
        return "/home/hello";
    }

}
