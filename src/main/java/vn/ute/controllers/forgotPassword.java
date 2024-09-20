package vn.ute.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.ute.services.IUserService;
import vn.ute.services.impl.UserService;

import java.io.IOException;

@WebServlet(urlPatterns = {"/forgot-password"})
public class forgotPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private IUserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService(); // Tạo một thể hiện của UserService
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/forgotPassword.jsp").forward(req, resp); // Chuyển hướng tới form reset password
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("password-verify");

        // Kiểm tra mật khẩu có trùng khớp không
        if (password == null || !password.equals(confirmPassword)) {
            req.setAttribute("alert", "Passwords do not match!");
            req.getRequestDispatcher("/views/forgotPassword.jsp").forward(req, resp);
            return;
        }

        // Lấy thông tin email hoặc id người dùng (thường được gửi qua URL hoặc phiên)
        String userEmail = (String) req.getSession().getAttribute("userEmail");

        if (userEmail != null) {
            boolean updateSuccess = userService.updatePasswordByEmail(userEmail, password);

            if (updateSuccess) {
                req.setAttribute("alert", "Password reset successfully!");
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                req.setAttribute("alert", "System error, please try again later.");
                req.getRequestDispatcher("/views/forgotPassword.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("alert", "Invalid user session.");
            req.getRequestDispatcher("/views/forgotPassword.jsp").forward(req, resp);
        }
    }

}
