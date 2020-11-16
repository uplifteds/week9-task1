package org.uplifteds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Hello world!
 *
 */
public class CDPDatabaseLauncher {
    public static void main( String[] args ) throws SQLException {
        String postgresURL = "jdbc:postgresql://127.0.0.1:5432/postgres"; // localhost for Dev-env
        String user = "javauser"; //non-root user. Granted All permissions on table
        String password = "plaintextshouldbeencrypted"; // in Prod-env should be stored in Encrypted Secret-Vault

        try (Connection conn = DriverManager.getConnection(postgresURL, user, password);
             Statement stmt = conn.createStatement()) {

            //add appropriate constraints (primary keys, foreign keys, indexes, etc)
        }
    }
}
