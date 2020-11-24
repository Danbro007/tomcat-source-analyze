import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname BbsServlet
 * @Description TODO
 * @Date 2020/11/23 20:52
 * @Author Danrbo
 */
public class BbsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("------BbsServlet:doGet--------");
//        ServletContext servletContext = req.getServletContext();
//        String myParam = servletContext.getInitParameter("MyParam");
//        System.out.println(myParam);
//        int i = 1/0;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("------BbsServlet:doPost--------");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        String filename = config.getInitParameter("filename");
        System.out.println(filename);
    }
}
