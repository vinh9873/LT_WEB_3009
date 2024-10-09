package vn.ute.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.ute.utils.Constant;

@WebServlet(urlPatterns = { "/image" })
public class DownloadFileController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getParameter("fname");
		File file = new File(Constant.UPLOAD_DIRECTORY + "/" + fileName);
		
		if (!file.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
			return;
		}

		// Thiết lập kiểu MIME từ tệp
		String mimeType = Files.probeContentType(file.toPath());
		if (mimeType == null) {
			// Nếu không xác định được MIME type, mặc định là binary stream
			mimeType = "application/octet-stream";
		}
		resp.setContentType(mimeType);
		
		// Thiết lập Content-Length để truyền kích thước tệp
		resp.setContentLengthLong(file.length());

		// Thêm header hỗ trợ caching
		resp.setHeader("Cache-Control", "public, max-age=3600");  // Caching trong 1 giờ

		// Đảm bảo đóng luồng dữ liệu sau khi sử dụng
		try (FileInputStream fis = new FileInputStream(file)) {
			IOUtils.copy(fis, resp.getOutputStream());
		} catch (IOException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error reading file");
		}
	}
}
