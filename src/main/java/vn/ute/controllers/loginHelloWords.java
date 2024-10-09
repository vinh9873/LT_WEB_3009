package vn.ute.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.ute.models.UserModel;
import vn.ute.services.IUserService;
import vn.ute.services.impl.UserService;
import vn.ute.utils.Constant;

import java.io.IOException;

/**
 * Servlet implementation class loginHelloWords
 */
@WebServlet(urlPatterns =  "/login" )
public class loginHelloWords extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Lấy toàn bộ hàm trong service.
	IUserService service = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("views/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		 resp.setCharacterEncoding("UTF-8");
		 req.setCharacterEncoding("UTF-8");
		 
		 // Lấy tham số từ View
		String username = req.getParameter("username");
		 String password = req.getParameter("password");
		 
		 // Kiểm tra tham số
		 boolean isRememberMe = false;
		 String remember = req.getParameter("remember");

		 if("on".equals(remember)){
		 isRememberMe = true;
		 }
		 String alertMsg="";
		 if(username.isEmpty() || password.isEmpty()){
			 alertMsg = "Tài khoản hoặc mật khẩu không được rỗng";
			  req.setAttribute("alert", alertMsg);
			  req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			  return;
			}
         // Xử lí bài toán
		 UserModel user = service.login(username, password);
		 if(user!=null){
		     HttpSession session = req.getSession(true);
		     session.setAttribute("account", user);
		     
		   if(isRememberMe){
		    saveRemeberMe(resp, username);
		   }
		   
		  resp.sendRedirect(req.getContextPath()+"/waiting");
		  
		 }else{
			 
		     alertMsg = "Tài khoản hoặc mật khẩu không đúng";
		     req.setAttribute("alert", alertMsg);
		     req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
		     
		   }
		}
	
	    private void saveRemeberMe(HttpServletResponse response, String username){
		
			 Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER,username);
			 cookie.setMaxAge(30*60);
			 response.addCookie(cookie);
			 
		}

}