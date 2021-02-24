package com.malicia.mrg.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * The type Sq lite jdbc driver connection.
 *
 * @author sqlitetutorial.net
 */
public class SQLiteJDBCDriverConnection {


    private static final Logger LOGGER = LogManager.getLogger(SQLiteJDBCDriverConnection.class);

    /**
     * The constant conn.
     */
    private Connection conn;

    /**
     * Instantiates a new Sq lite jdbc driver connection.
     *
     * @param catalogLrcat the catalog lrcat
     */
    public SQLiteJDBCDriverConnection(String catalogLrcat) {
        connect(catalogLrcat);
    }

    /**
     * Connect to a sample database
     *
     * @param sqlliteDatabase the sqllite database
     */
    public void connect(String sqlliteDatabase) {
        LOGGER.debug(() -> "connect to database : " + sqlliteDatabase);
        conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:" + sqlliteDatabase;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            LOGGER.info("Connection to SQLite has been established.");

        } catch (SQLException e) {
            LOGGER.fatal(e.getMessage());
        }

    }

    /**
     * Disconnect.
     */
    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            LOGGER.fatal(ex.getMessage());
        }
    }

    /**
     * select all rows in the warehouses table
     *
     * @param sql the sql
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean execute(String sql) throws SQLException {

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            LOGGER.debug(sql);
            return stmt.execute(sql);
        } finally {
            conn.close();
        }

    }

    /**
     * select all rows in the warehouses table
     *
     * @param sql the sql
     * @return the result set
     * @throws SQLException the sql exception
     */
    public ResultSet select(String sql) throws SQLException {


        Statement stmt = null;

        stmt = conn.createStatement();
        LOGGER.debug(sql);

        // forcage display du resultset
        ResultSet resultSet = stmt.executeQuery(sql);
        displayResultset(resultSet);

        return stmt.executeQuery(sql);


    }

    /**
     * select all rows in the warehouses table
     *
     * @param sql the sql
     * @return the int
     * @throws SQLException the sql exception
     */
    public int executeUpdate(String sql) throws SQLException {


        Statement stmt = null;

        stmt = conn.createStatement();
        LOGGER.debug(sql);
        int ret = stmt.executeUpdate(sql);
        LOGGER.debug(stmt.toString());
        LOGGER.debug("ret=>" + ret);
        return ret;


    }


    private void displayResultset(ResultSet resultSet) throws SQLException {
        if (LOGGER.isTraceEnabled()) {

            ResultSet resultSetAff = resultSet;

            ResultSetMetaData rsmd = resultSetAff.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            StringBuilder colname = new StringBuilder();
            colname.append(" + ");
            for (int i = 1; i <= columnsNumber; i++) {
                colname.append(String.format("%-20s", rsmd.getColumnName(i)) + " + ");
            }
            LOGGER.trace(() -> "" + colname.toString());


            while (resultSetAff.next()) {
                StringBuilder columnValue = new StringBuilder();
                columnValue.append(" + ");
                for (int i = 1; i <= columnsNumber; i++) {
                    columnValue.append(String.format("%-20s", resultSetAff.getString(i)) + " + ");
                }
                LOGGER.trace(() -> "" + columnValue.toString());
            }

        }
    }

}