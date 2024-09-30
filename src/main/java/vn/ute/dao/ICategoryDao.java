package vn.ute.dao;

import java.util.List;

import vn.ute.models.CategoryModel;

public interface ICategoryDao {
	
	List<CategoryModel> findALL();
	CategoryModel findById(int id);
	void insert(CategoryModel category);
	void update(CategoryModel category);
	void delete(int id);
	List<CategoryModel> findName(String keyword);
	

}
