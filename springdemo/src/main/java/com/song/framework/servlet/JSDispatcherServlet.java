package com.song.framework.servlet;

import com.song.framework.annotation.JSAutowired;
import com.song.framework.annotation.JSController;
import com.song.framework.annotation.JSRequestMapping;
import com.song.framework.annotation.JSService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class JSDispatcherServlet extends HttpServlet {

    private Properties contextConfig  = new Properties();

    private List<String> classNames = new ArrayList<>();

    private Map<String, Object> ioc = new HashMap<>();

    private Map<String, Method> handlerMapping = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 : " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (this.handlerMapping.isEmpty()) {
            return;
        }

        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        if (!this.handlerMapping.containsKey(url)) {
            resp.getWriter().write("<p>404 Not Found!</p>");
            return;
        }

        Method method = this.handlerMapping.get(url);
        System.out.println(method);

        //TODO  反射调用
        Object obj = this.ioc.get(toLowerFirstCase(method.getDeclaringClass().getSimpleName()));
        Map<String, String[]> params = req.getParameterMap();

        /*Annotation[][] annotations = method.getParameterAnnotations();
        for (Annotation a[] : annotations) {

        }*/
        // HardCode 实现
        method.invoke(obj, req, resp, params.get("name")[0]);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1 . 加载配置文件
        loadConfig(config.getInitParameter("contextConfigLocation"));
        //2. 扫描包
        scanClass(contextConfig.getProperty("scanPackage"));
        //3. 初始化
        initInstance();
        //4. DI注入类
        autowiredClass();
        //5. 路由
        initHandlerMapping();

        System.out.println("JS MVC FRAMEWORK INIT DONE");

    }

    private void initHandlerMapping() {
        if (ioc.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();

            if (!clazz.isAnnotationPresent(JSController.class)) {
                continue;
            }

            String baseUrl = "";
            if (clazz.isAnnotationPresent(JSRequestMapping.class)) {
                JSRequestMapping j = clazz.getAnnotation(JSRequestMapping.class);
                baseUrl = j.value();
            }

            // 扫描所有公共方法
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (!clazz.isAnnotationPresent(JSRequestMapping.class)) {
                    continue;
                }
                String url = ("/" + baseUrl + "/" + method.getAnnotation(JSRequestMapping.class).value())
                        .replaceAll("/+", "/");
                handlerMapping.put(url, method);

                System.out.println("Mapped: " + url + "," + method);
            }
        }
    }

    private void autowiredClass() {
        if (ioc.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(JSAutowired.class)) {
                    continue;
                }
                JSAutowired autowired = field.getAnnotation(JSAutowired.class);
                String beanName = "".equals(autowired.value()) ? toLowerFirstCase(field.getType().getSimpleName()) : autowired.value();

                field.setAccessible(true);

                try {
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void initInstance() {
        if (classNames.isEmpty()) {
            return;
        }
        for (String className : classNames) {
            try {
                Class clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(JSController.class)) {
                    Object obj = clazz.newInstance();

                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName, obj);
                } else if (clazz.isAnnotationPresent(JSService.class)) {
                    JSService jsService = (JSService) clazz.getAnnotation(JSService.class);
                    String beanName = "".equals(jsService.value()) ? toLowerFirstCase(clazz.getSimpleName()) : jsService.value();

                    Object obj = clazz.newInstance();
                    ioc.put(beanName, obj);

                    for (Class i : clazz.getInterfaces()) {
                        if (ioc.containsKey(i.getName())) {
                            throw new Exception("The bean name " + i.getName() + " is exists!");
                        }
                        ioc.put(i.getName(), obj);
                    }
                } else {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 首字母小写
    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private void scanClass(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource(scanPackage.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());

        for (File file : classDir.listFiles()) {
            if (file.isDirectory()) {
                // 递归
                scanClass(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String classname = scanPackage + "." + file.getName().replace(".class", "");
                classNames.add(classname);
            }
        }
    }

    private void loadConfig(String contextConfigLocation) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation.split(":")[1]);
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
