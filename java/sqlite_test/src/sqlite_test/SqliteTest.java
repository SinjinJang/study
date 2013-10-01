package sqlite_test;

import java.sql.SQLException;

public class SqliteTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FolderDB fdb = new FolderDB(FolderDB.DATABASE);
		
		fdb.open();
		
		try {
			if(!fdb.checkTableExist()) {
				fdb.createTable();
				System.out.println("Table is just created!!");
			} else {
				System.out.println("Table is already existed!!");
			}
			
			
			fdb.upsert("top", "");
			fdb.upsert("task", "top");
			fdb.upsert("event", "top");
			fdb.upsert("sub-task1", "task");
			fdb.upsert("sub-event1", "event");
			
			fdb.selectAll();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		fdb.close();
		
	}

}
