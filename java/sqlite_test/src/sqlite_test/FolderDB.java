/**
 * http://crystalcube.co.kr/92 : SQLite 의 모든 것 (3부) - C++ 에서 사용하기
 * http://crystalcube.co.kr/100 : SQLite 의 모든 것 (4부) - Java 에서 사용하기
 */
package sqlite_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

import sqlite_test.vo.Folder;

public class FolderDB {
	private Connection connection;
	private String dbFileName;
	private boolean isOpened = false;

	public final static String DATABASE = "folder.db";

	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FolderDB(String databaseFileName) {
		this.dbFileName = databaseFileName;
	}

	public boolean open() {
		try {
			SQLiteConfig config = new SQLiteConfig();
			//config.setReadOnly(true); // Read only setting
			this.connection = DriverManager.getConnection("jdbc:sqlite:/" + this.dbFileName, config.toProperties());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		isOpened = true;
		return true;
	}

	public boolean close() {
		if (this.isOpened == false) {
			return true;
		}

		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean createTable() throws SQLException {
		if (this.isOpened == false) {
			return false;
		}
		
		boolean result = false;
		String query = "CREATE TABLE FOLDER (NAME TEXT PRIMARY KEY NOT NULL, PARENT TEXT);";
		PreparedStatement prep = this.connection.prepareStatement(query);

		result = prep.execute();
		if(result) {
			prep.close();
		}
		return result;
	}
	
	public boolean checkTableExist() throws SQLException {
		String tableName = "";
		
		if (this.isOpened == false) {
			return false;
		}
		
		boolean result = false;
		String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='FOLDER';";
		PreparedStatement prep = this.connection.prepareStatement(query);

		ResultSet rs = prep.executeQuery();
		if (rs.next()) {
			tableName = rs.getString(1); // retrieve by index
			result = true;
		}
		rs.close();
		prep.close();
		return result && "FOLDER".equals(tableName);
		
	}

	public boolean readMeta(String name, Folder item)
			throws SQLException {
		if (this.isOpened == false) {
			return false;
		}

		boolean result = false;
		String query = "SELECT * FROM FOLDER WHERE NAME=?";
		PreparedStatement prep = this.connection.prepareStatement(query);
		prep.setString(1, name);

		ResultSet row = prep.executeQuery();
		if (row.next()) {
			item.setName( row.getString(1) ); // index 로 가져오기
			item.setParent( row.getString(2) );  // field 이름으로 가져오기

			result = true;
		}
		row.close();
		prep.close();
		return result;
	}
	
	public boolean upsert(String name, String parent) throws SQLException {
		if (this.isOpened == false) {
			return false;
		}
		
		boolean result = false;
		String query = "INSERT OR REPLACE INTO FOLDER VALUES(?, ?);";
		PreparedStatement prep = this.connection.prepareStatement(query);
		prep.setString(1, name);
		prep.setString(2, parent);

		result = prep.execute();
		prep.close();
		return result;
	}
	
	public boolean selectAll() throws SQLException {
		if (this.isOpened == false) {
			return false;
		}
		
		boolean result = false;
		String query = "SELECT * FROM FOLDER";
		PreparedStatement prep = this.connection.prepareStatement(query);

		ResultSet row = prep.executeQuery();
		while (row.next()) {
			System.out.println("name : " + row.getString(1));
			System.out.println("parent : " + row.getString(2));
		}
		row.close();
		prep.close();
		return result;
	}
}
