package com.shopvote;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.*;
@WebServlet("/login") public class LoginServlet extends HttpServlet {
 protected void doPost(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{r.setCharacterEncoding("UTF-8");String e=ShopVoteStore.email(r.getParameter("email")),p=r.getParameter("password");if(p==null||!ShopVoteStore.get().login(e,p)){s.sendRedirect(r.getContextPath()+"/login.jsp?error=invalid");return;}HttpSession old=r.getSession(false);if(old!=null)old.invalidate();r.getSession(true).setAttribute("userEmail",e);s.sendRedirect(r.getContextPath()+"/vote.jsp");}
}