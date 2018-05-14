package tech.acodesigner.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import tech.acodesigner.entity.Visitor;
import tech.acodesigner.service.VisitorService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * @program: Blog_SSM
 * @description: web服务启动 销毁监听 就来记录访客数
 * @author: Burton_J
 * @create: 2018-05-14 01:03
 **/

public class MyServletContextListener implements ServletContextListener {

    //得到spring容器 从而得到VisitorService 监听器中不能直接@Autowira service  这个属于spring容器 监听器属于servlet容器（tomcat）


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //WEB服务启动 就是项目启动 初始化
        ServletContext sc = servletContextEvent.getServletContext();
        ApplicationContext  context = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
        VisitorService visitorService = (VisitorService)context.getBean("VisitorService");
        int visitorNum = visitorService.getVisitorNum();
        System.out.println("初始化时候访客人数："+visitorNum);
        System.out.println("初始化时候访客人数："+visitorNum);
        System.out.println("初始化时候访客人数："+visitorNum);
        System.out.println("初始化时候访客人数："+visitorNum);

        sc.setAttribute("visitorNum", visitorNum);


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {


        ApplicationContext  context = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
        VisitorService visitorService = (VisitorService)context.getBean("VisitorService");
        //销毁的时候 将访客写入数据库
        ServletContext sc = servletContextEvent.getServletContext();
        List<Visitor> visitorList = (List<Visitor>) sc.getAttribute("visitorList");
        visitorService.saveVisitors(visitorList);
    }
}
