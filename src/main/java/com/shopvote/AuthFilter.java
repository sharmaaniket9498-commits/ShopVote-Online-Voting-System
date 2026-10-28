package com.shopvote;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebFilter;import jakarta.servlet.http.*;import java.io.*;
@WebFilter(urlPatterns={"/vote.jsp","/results.jsp"}) public class AuthFilter implements Filter {
 public void doFilter(ServletRequest a,ServletResponse b,FilterChain chain)throws IOException,ServletException{HttpServletRequest r=(HttpServletRequest)a;HttpServletResponse s=(HttpServletResponse)b;HttpSession h=r.getSession(false);if(h==null||h.getAttribute("userEmail")==null){s.sendRedirect(r.getContextPath()+"/login.jsp");return;}chain.doFilter(a,b);}
}