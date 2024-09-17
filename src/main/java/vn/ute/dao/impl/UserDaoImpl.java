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
				list.add( new UserModel(
						rs.getInt("id"),
				  rs.getString("username"),
				  rs.getString("password"),
				  rs.getString("images"),
				  rs.getString("fullname"),
				  rs.getString("email"),
				  rs.getString("phone"),
				  rs.getInt("roleid"),
				  rs.getDate("createDate")));
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
		String sql = "INSERT INTO users (id, username, password, images, fullname, email, phone, roleid, createDate) VALUES(?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, user.getId());
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getImages());
			ps.setString(5, user.getFullname());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
	      try {
	          IUserDao userDao = new UserDaoImpl();
	          System.out.println(userDao.findAll());
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
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
}
