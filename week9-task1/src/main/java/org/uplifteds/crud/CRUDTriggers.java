package org.uplifteds.crud;

import java.sql.*;

public class CRUDTriggers {
    static String name = "updateproc2";
    static String triggerFuncName = name +"()";
    static String triggerName = "launch" + name;

    public static void createTriggerFuncUpdateUpdatedColumn(Connection conn) throws SQLException {
        String sqlStoredFunc = "create or replace FUNCTION " + triggerFuncName + " returns trigger as $$\n" +
                "begin\n" +
                "IF OLD.* IS DISTINCT FROM NEW.* THEN\n" +
                " UPDATE students \n" +
                " SET updated =current_timestamp;\n" +
                " END IF;\n" +
                "return null;\n" +
                "end;\n" +
                "$$ language plpgsql;";
        execPrepStmt(conn, sqlStoredFunc);
    }

    public static void createSQLTriggerExecFunc(Connection conn) throws SQLException {
        String sqlStoredProc = "CREATE TRIGGER " + triggerName + "\n" +
                "AFTER UPDATE of name,surname,skill,phone,dob,created ON students\n" +
                "FOR EACH ROW\n" +
                "WHEN (OLD.* IS DISTINCT FROM NEW.*)\n" +
                "EXECUTE FUNCTION " + triggerFuncName + ";";
        execPrepStmt(conn, sqlStoredProc);
    }

    public static void dropSQLTriggerExecFunc(Connection conn) throws SQLException {
        String sqlStoredProc = "DROP TRIGGER " + triggerName + "\n" +
                "ON students;";
        execPrepStmt(conn, sqlStoredProc);
    }

    private static void execPrepStmt(Connection conn, String sqlStoredFunc) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sqlStoredFunc);
            pstmt.executeUpdate(); // DB user has permissions to create Stored Procedures
        } finally {
            pstmt.close();
        }
    }

}
