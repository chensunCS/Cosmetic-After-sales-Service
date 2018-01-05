package test;

import java.sql.SQLException;

import database.CreateDB;

public class CreateDBTest {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		new CreateDB().createDatabase();
	}

}
