import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
public class AuthorizationFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        Cookie[] cookies = ((HttpServletRequest)request).getCookies();
        UUID sessionId = null;
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("sessionId".equals(cookie.getName())){
                    sessionId = UUID.fromString(cookie.getValue());
                }
            }
        }
        
        Account account = null;
        try{
            account = Account.get(sessionId);
        } catch(Exception ex){
            if(!"SessionNotExists".equals(ex.getMessage())){
                ((HttpServletResponse)response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
            }
        }
        
        if(account == null){
            ((HttpServletResponse)response).sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        
        request.setAttribute("account", account);
        response.setContentType("application/json; charset=utf-8");
        
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
    
}