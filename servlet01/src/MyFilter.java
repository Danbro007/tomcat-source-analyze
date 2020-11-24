import javax.servlet.*;
import java.io.IOException;

/**
 * @Classname MyFilter
 * @Description TODO
 * @Date 2020/11/24 10:39
 * @Author Danrbo
 */
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
        System.out.println(filterConfig.getInitParameter("name"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("doFilter");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
