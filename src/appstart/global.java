package appstart;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * web应用的全局事件
 */

@Service
public class global {
    /**
     * 在web启动时执行
     */
    @PostConstruct
    public void applicationStart(){
        System.out.println("application start");
    }

    /**
     * 在web结束时执行
     */
    @PreDestroy
    public void applicationEnd(){
        System.out.println("InskyScheduleCenter application end");

    }
}
