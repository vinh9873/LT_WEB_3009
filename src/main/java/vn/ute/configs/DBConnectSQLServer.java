package vn.ute.configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectSQLServer {


	private final String serverName = "DESKTOP-UDSOMGL";

	private final String dbName = "ltwebconnect";

	private final String portNumber = "1433";
	private final String userID = "sa";
	private final String instance ="";

	 private final String password = "1234567";

	public Connection getConnection() throws Exception {

		String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName;
		if(instance == null || instance.trim().isEmpty())
			
			url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName;

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		return DriverManager.getConnection(url, userID, password);

	}

	// Test chương trình. Kích phải chuột chọn run as->java application

	public static void main(String[] args) {
      try {
          Connection conn = new DBConnectSQLServer().getConnection();
          System.out.println("Kết nối thành công: " + conn);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
}