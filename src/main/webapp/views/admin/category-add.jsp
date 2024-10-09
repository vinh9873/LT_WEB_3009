<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri ="jakarta.tags.core" %>
<form action="${pageContext.request.contextPath }/admin/category/insert" method="post" 
enctype="multipart/form-data">
    <label for="fname">Category Name:</label><br>
    <input type="text" id="categoryname" name="categoryname"><br> 
    <label for="lname">Images:</label><br>
    
      <img height="150" width="200" src="" id ="imagess" />
    <input type="file" onchange="chooseFile(this)" id="images" name="images"><br>
    <label for="status">Status:</label><br>
    <input type="text" id="status" name="status"><br><br>
    
    <input type="submit" value="Submit">
</form>
