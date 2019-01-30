package com.song.framework.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JSDispatcherServlet extends HttpServlet {

    private Properties contextConfig  = new Properties();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch();
    }

    private void doDispatch() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        loadConfig(config.getInitParameter("contextConfigLocation"));
        scanClass();
        initInstance();
        autowiredClass();
        initHandlerMapping();

        System.out.println("JS MVC FRAMEWORK INIT DONE");

    }

    private void initHandlerMapping() {
    }

    private void autowiredClass() {
    }

    private void initInstance() {
    }

    private void scanClass() {
    }

    private void loadConfig(String contextConfigLocation) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
