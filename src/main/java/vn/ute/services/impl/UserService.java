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
	
	@Override
	public boolean register(String username, String password, String email, String
	fullname, String phone ) {
	if (userDao.checkExistUsername(username)) {
	return false;
	}
	long millis=System.currentTimeMillis();
	java.sql.Date date=new java.sql.Date(millis);
	userDao.insert(new UserModel( username, password, null, fullname, email, phone, 5, date));
	return true;
	}

	@Override
	public void insert(UserModel user) {
		userDao.insert(user);
	}

	@Override
	public boolean checkExistEmail(String email) {
		return userDao.checkExistEmail(email);
	}

	@Override
	public boolean checkExistUsername(String username) {
		return userDao.checkExistUsername(username);
	}

	@Override
	public boolean checkExistPhone(String phone) {
		return userDao.checkExistPhone(phone);
	}

}
