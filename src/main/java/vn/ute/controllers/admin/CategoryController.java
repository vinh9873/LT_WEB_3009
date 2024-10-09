package vn.ute.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.ute.models.CategoryModel;
import vn.ute.services.ICategoryService;
import vn.ute.services.impl.CategoryServiceImpl;
import vn.ute.utils.Constant;


@MultipartConfig(fileSizeThreshold = 1024 * 1024,
maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = {"/admin/categories","/admin/category/add",
		"/admin/category/insert","/admin/category/edit","/admin/category/update",
		"/admin/category/delete","/admin/category/search"})
public class CategoryController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public ICategoryService cateService = new CategoryServiceImpl();
   
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");;
		
		if (url.contains("categories")) {
		   
			List<CategoryModel> list = cateService.findALL();
			req.setAttribute("listcate",list);
			req.getRequestDispatcher("/views/admin/category-list.jsp").forward(req, resp);
			
		} else if(url.contains("add")) {
			
			req.getRequestDispatcher("/views/admin/category-add.jsp").forward(req, resp);
			
		} else if(url.contains("edit")) {
			
			int id = Integer.parseInt(req.getParameter("id"));
			CategoryModel category = cateService.findById(id);
			
			req.setAttribute("cate", category);
			req.getRequestDispatcher("/views/admin/category-edit.jsp").forward(req, resp);
			
		} else if(url.contains("delete")) {
			
			int id = Integer.parseInt(req.getParameter("id"));
			
			try {
				cateService.delete(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
			
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	    String url = req.getRequestURI();
	    req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");

	    if (url.contains("update")) {
	        int categoryid = Integer.parseInt(req.getParameter("categoryid"));
	        String categoryname = req.getParameter("categoryname");
	        String status = req.getParameter("status");
	        int statuss = Integer.parseInt(status);

	        CategoryModel category = new CategoryModel();
	        category.setCategoryid(categoryid);
	        category.setCategoryname(categoryname);
	        category.setStatus(statuss);

	        // Lưu hình cũ
	        CategoryModel cateold = cateService.findById(categoryid);
	        String fileold = cateold.getImages();

	        // Xử lý images
	        String fname = "";
	        String uploadPath = Constant.UPLOAD_DIRECTORY;
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdir();
	        }

	        try {
	            Part part = req.getPart("images");

	            if (part.getSize() > 0) {
	                String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();

	                // Đổi tên file
	                int index = filename.lastIndexOf(".");
	                String ext = filename.substring(index + 1);
	                fname = System.currentTimeMillis() + "." + ext;

	                // Upload file
	                part.write(uploadPath + "/" + fname); // Sửa lại từ filename thành fname

	                // Ghi tên file vào data
	                category.setImages(fname);
	            } else {
	                category.setImages(fileold); // Giữ lại ảnh cũ nếu không có ảnh mới được upload
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        cateService.update(category);
	        resp.sendRedirect(req.getContextPath() + "/admin/categories");
	        return; // Dừng xử lý tại đây sau khi redirect

	    } else if (url.contains("insert")) {

	        CategoryModel category = new CategoryModel();

	        String categoryname = req.getParameter("categoryname");
	        int statuss = Integer.parseInt(req.getParameter("status"));
	        category.setCategoryname(categoryname);
	        category.setStatus(statuss);

	        // Xử lý images
	        String fname = "";
	        String uploadPath = Constant.UPLOAD_DIRECTORY;
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdir();
	        }

	        try {
	            Part part = req.getPart("images");

	            if (part.getSize() > 0) {
	                String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();

	                // Đổi tên file
	                int index = filename.lastIndexOf(".");
	                String ext = filename.substring(index + 1);
	                fname = System.currentTimeMillis() + "." + ext;

	                // Upload file
	                part.write(uploadPath + "/" + fname); // Sửa lại từ filename thành fname

	                // Ghi tên file vào data
	                category.setImages(fname);
	            } else {
	                category.setImages("avatar.png"); // Sử dụng ảnh mặc định nếu không upload ảnh
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        cateService.insert(category);
	        resp.sendRedirect(req.getContextPath() + "/admin/categories");
	        return; // Dừng xử lý tại đây sau khi redirect
	    }
	}
	
}