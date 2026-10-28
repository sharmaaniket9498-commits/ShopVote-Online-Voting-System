package com.shopvote;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.*;
@WebServlet("/register") public class RegisterServlet extends HttpServlet {
 protected void doPost(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{r.setCharacterEncoding("UTF-8");String n=v(r.getParameter("name")),e=ShopVoteStore.email(r.getParameter("email")),p=v(r.getParameter("password")),c=v(r.getParameter("confirmPassword"));if(n.isBlank()||!e.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")||p.length()<8||!p.equals(c)){s.sendRedirect(r.getContextPath()+"/register.jsp?error=invalid");return;}if(!ShopVoteStore.get().register(n,e,PasswordUtil.hash(p))){s.sendRedirect(r.getContextPath()+"/register.jsp?error=exists");return;}s.sendRedirect(r.getContextPath()+"/login.jsp?registered=1");}
 private String v(String s){return s==null?"":s.trim();}
}