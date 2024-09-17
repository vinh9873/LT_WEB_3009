package vn.ute.dao;

import java.util.List;

import vn.ute.models.UserModel;

public interface IUserDao {

	List<UserModel> findAll();
	
	UserModel findById(int id);
	
	void insert(UserModel user);
	
	UserModel findByUserName(String userName);
}
