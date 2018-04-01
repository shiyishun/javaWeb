package com.shi.listener;
import javax.servlet.ServletContextEvent;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.shi.service.DictService;


public class DictCacheListener implements javax.servlet.ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {        
    }
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
        DictService dictService = (DictService) webApplicationContext.getBean("dictService");
        dictService.getCacheDict();   // 调用数据字典Manager的一个方法来缓存
    }

}