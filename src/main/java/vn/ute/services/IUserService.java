package vn.ute.services;

import vn.ute.models.UserModel;

public interface IUserService {
	UserModel login(String username, String password);
	UserModel FindByUserName(String username);

}
