package com.dee;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;



@WebServlet("/api/getGrade")
public class api implements Servlet {

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/html;charset=UTF-8");
        Map<String, String[]> parameterMap = servletRequest.getParameterMap();
        String params="";
        String username = '"'+parameterMap.get("username")[0]+'"';
        String password = '"'+parameterMap.get("password")[0]+'"';
        String taskType = '"'+parameterMap.get("taskType")[0]+'"';
        String command="python3 /root/course/JHEU.py" + " -username " + username + " -password " + password + " -taskType " + taskType;
//        command="python C:\\Users\\10248\\PycharmProjects\\HEUCoureseGrub\\test.py";
//            command="python3 /root/course/tmp.py";
        Process proc=Runtime.getRuntime().exec(command);
        InputStreamReader inputStreamReader = new InputStreamReader(proc.getInputStream());
        BufferedReader in = new BufferedReader(inputStreamReader);
        String line = null;
        String result="";
        while ((line = in.readLine()) != null){
            System.out.println(line);
            result+=line;
        }
        in.close();
        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        result=result.substring(0,result.length()-1);
        System.out.println(command);
        System.out.println(result);

//        params+=",\""+"command\":"+command;
        params="";
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.getWriter().println(("{\"code\":200"+params+",\"result\":["+result+"]}"));
//        System.out.println("hello user");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

}
