package org.uplifteds.DDL;

import org.uplifteds.CDPDBLauncher;

import java.sql.*;

public class CloneTableDropColumns {
    public static String cloneTablePrefix = "snap_";
    public static void doCreateTable(Statement stmt, String table_name, String dropColList) throws SQLException {

        String sql =  "DROP TABLE IF EXISTS " + cloneTablePrefix + table_name + ";\n" +
                "CREATE TABLE " + cloneTablePrefix + table_name +
                " AS TABLE " + table_name + " WITH DATA;" + // clone values
                "ALTER TABLE " + cloneTablePrefix + table_name + dropColList + " CASCADE;";
        stmt.executeUpdate(sql);
    }
}
