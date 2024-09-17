package vn.ute.services.impl;

import vn.ute.dao.IUserDao;
import vn.ute.dao.impl.UserDaoImpl;
import vn.ute.models.UserModel;
import vn.ute.services.IUserService;

public class UserService implements IUserService {

	IUserDao userDao = new UserDaoImpl();
	@Override
	public UserModel login(String username, String password) {
		UserModel user = this.FindByUserName(username);
		if (user != null && password.equals(user.getPassword())) {
		    return user;
		}
		return null;
	}

	@Override
	public UserModel FindByUserName(String username) {
		
		return userDao.findByUserName(username);
	}

}
