package vn.ute.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.ute.configs.DBConnectSQLServer;
import vn.ute.dao.IUserDao;
import vn.ute.models.UserModel;

public class UserDaoImpl extends DBConnectSQLServer implements IUserDao {

	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;

	@Override
	public List<UserModel> findAll() {
		String sql = "SELECT * FROM users  ";
		try {
			conn = new DBConnectSQLServer().getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				List<UserModel> list = new ArrayList<UserModel>();
				list.add(new UserModel(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("images"), rs.getString("fullname"), rs.getString("email"), rs.getString("phone"),
						rs.getInt("roleid"), rs.getDate("createDate")));
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public UserModel findById(int id) {
		String sql = "SELECT * FROM users WHERE id = ? ";
		try {
			conn = new DBConnectSQLServer().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFullname(rs.getString("fullname"));
				user.setImages(rs.getString("images"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setRoleid(Integer.parseInt(rs.getString("roleid")));
				user.setCreateDate(rs.getDate("createDate"));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insert(UserModel user) {

		String sql = "INSERT INTO users(username, password, images, fullname, email, phone, roleid,createDate) VALUES (?,?,?,?,?,?,?,?)";
		try {

			conn = new DBConnectSQLServer().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getImages());
			ps.setString(4, user.getFullname());
			ps.setString(5, user.getEmail());
			ps.setString(6, user.getPhone());
			ps.setInt(7, user.getRoleid());
			ps.setDate(8, user.getCreateDate());
			ps.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public static void main(String[] args) {
		IUserDao userDao = new UserDaoImpl();
		//Test findAll method
		try {
			System.out.println(userDao.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Test findByUserName method
				try {
					System.out.println(userDao.findByUserName("vinhnq"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				// Test findById method
				try {
					System.out.println(userDao.findById(1));
				} catch (Exception e) {
					e.printStackTrace();
				}
				// Test insert method
				UserModel insertUser = new UserModel();
				insertUser.setUsername("quocvn");
				insertUser.setPassword("12345");
				insertUser.setImages("NULL");
				insertUser.setFullname("Quoc Vinh");
				insertUser.setEmail("vinhnq@gmail.com");
				insertUser.setPhone("0123456789");
				insertUser.setRoleid(3);
				insertUser.setCreateDate(new java.sql.Date(System.currentTimeMillis())); // Sets current date
				userDao.insert(insertUser);
				// Check insert data in SQLServer
				UserModel dataInsertedUser = userDao.findByUserName("quocvn");
				if (dataInsertedUser != null) {
					System.out.println("Chèn thành công!");
					System.out.println(dataInsertedUser);
				} else {
					System.out.println("Chèn thất bại!");
				}
				String emailToCheck = "vinhnq@gmail.com";
				String usernameToCheck = "quocvn";
				String phoneToCheck = "0123456789";
				boolean emailEx = userDao.checkExistEmail(emailToCheck);
				boolean usernameEx = userDao.checkExistUsername(usernameToCheck);
				boolean phoneEx = userDao.checkExistPhone(phoneToCheck);
				System.out.println("Checked Email: " + emailEx);
				System.out.println("Checked Username: " + usernameEx);
				System.out.println("Checked Phone: " + phoneEx);
	}

	@Override
	public UserModel findByUserName(String userName) {
		String sql = "SELECT * FROM users WHERE username = ? ";
		try {
			conn = new DBConnectSQLServer().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFullname(rs.getString("fullname"));
				user.setImages(rs.getString("images"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setRoleid(Integer.parseInt(rs.getString("roleid")));
				user.setCreateDate(rs.getDate("createDate"));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean checkExistEmail(String email) {
		boolean duplicate = false;
		String query = "select * from users where email = ?";
		try {
			conn = new DBConnectSQLServer().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				duplicate = true;
			}
			ps.close();
			conn.close();
		} catch (Exception ex) {
		}
		return duplicate;
	}

	@Override
	public boolean checkExistUsername(String username) {
		boolean duplicate = false;
		String query = "select * from users where username = ?";
		try {
			conn = new DBConnectSQLServer().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				duplicate = true;
			}
			ps.close();
			conn.close();
		} catch (Exception ex) {
		}
		return duplicate;

	}

	@Override
	public boolean checkExistPhone(String phone) {
		boolean exist = false;
		String query = "select * from users where phone = ?";
		try {
			conn = new DBConnectSQLServer().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, phone);
			rs = ps.executeQuery();
			if (rs.next()) {
				exist = true;
			}
			ps.close();
			conn.close();
		} catch (Exception ex) {
		}
		return exist;
	}
}
