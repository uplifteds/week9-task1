package org.uplifteds.DML_crud;

import org.uplifteds.CDPDBLauncher;
import org.uplifteds.DDL.CloneTableDropColumns;
import org.uplifteds.entity.ExamResult;
import org.uplifteds.entity.Student;

import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CRUDSelectMethodsCloneTable {
    public static Student getCloneStudentByIdExact(Statement stmt, int id) throws SQLException {
        // case-sensitive exact search
        String sql = "select * from " + CloneTableDropColumns.cloneTablePrefix + CDPDBLauncher.listOfTables.get(2) +
                " where " + Student.idFieldName + " = '" + id + "'";
        ResultSet resultSet = stmt.executeQuery(sql);

        Student stud = findCloneStudentInResultSet(resultSet);
        return stud;
    }

    private static Student findCloneStudentInResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        System.out.println("\n# Searching clone... ");
        List<String> l = new CopyOnWriteArrayList<>();
        Student stud = new Student();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                String columnValue = resultSet.getString(i);
                l.add(columnValue);
            }
            stud.setId(Integer.parseInt(l.get(0)));
            stud.setName(l.get(1));
            stud.setSurname(l.get(2));
        }
        resultSet.close();

        CRUDSelectMethods.doFoundCondition(stud.getId(),stud.toString());
        System.out.println("Clone Student unchanged");

        return stud;
    }

}
