package org.uplifteds.DML_crud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExplainAnalyzeSelectMethods {
    static String explainSQLQuery;

    public static void doExplainAnalyzeSearchQuery(Statement stmt) throws SQLException {
        ResultSet resultSet = stmt.executeQuery(explainSQLQuery);
        findExplainAnalyzeInResultSet(resultSet);
    }

    private static void findExplainAnalyzeInResultSet(ResultSet resultSet) throws SQLException {
        String columnValue = "";
        while (resultSet.next()) {
            columnValue = resultSet.getString(1);
        }
        resultSet.close();
        System.out.println("|| Explain analyze sql query: " + columnValue);
    }
}
