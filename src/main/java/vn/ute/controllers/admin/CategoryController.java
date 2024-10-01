package vn.ute.controllers.admin;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.ute.models.CategoryModel;
import vn.ute.services.ICategoryService;
import vn.ute.services.impl.CategoryServiceImpl;

@WebServlet(urlPatterns = {"/admin/categories","/admin/category/add",
		"/admin/category/insert","/admin/category/edit","/admin/category/update",
		"/admin/category/delete","/admin/category/seach"})
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
			
			String id = req.getParameter("id");
			cateService.delete(Integer.parseInt(id));
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
			
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");;
		
		if (url.contains("insert")) {
		   
			String categoryname = req.getParameter("categoryname");
			String status = req.getParameter("status");
			int statuss = Integer.parseInt(status);
			String images = "https://cdn11.dienmaycholon.vn/filewebdmclnew/DMCL21/Picture//Apro/Apro_product_33149/oppo-a58_main_172_1020.png.webp";
			
			CategoryModel category = new CategoryModel();
			category.setCategoryname(categoryname);
			category.setImages(images);
			category.setStatus(statuss);
			
			cateService.insert(category);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
			
		} else if (url.contains("update")) {
			
			int categoryid = Integer.parseInt(req.getParameter("categoryid"));
			String categoryname = req.getParameter("categoryname");
			String status = req.getParameter("status");
			int statuss = Integer.parseInt(status);
			String images = "https://cdn11.dienmaycholon.vn/filewebdmclnew/DMCL21/Picture//Apro/Apro_product_33149/oppo-a58_main_172_1020.png.webp";
			
			CategoryModel category = new CategoryModel();
			category.setCategoryid(categoryid);
			category.setCategoryname(categoryname);
			category.setImages(images);
			category.setStatus(statuss);
			
			cateService.update(category);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
			
		}
		
	}
	
}
