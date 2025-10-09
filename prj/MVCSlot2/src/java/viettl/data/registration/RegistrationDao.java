/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettl.data.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import viettl.data.DatabaseHelper;

/**
 *
 * @author viett
 */
public class RegistrationDao implements Serializable {

    private final String USERNAME = "username";
    private final String PASSWORD = "password";
    private final String LAST_NAME = "lastName";
    private final String ROLE = "isAdmin";

    private List<RegistrationDto> accountList;

    public List<RegistrationDto> getAccountList() {
        return accountList;
    }

    public RegistrationDto checkLogin(String username, String password) throws SQLException, ClassNotFoundException {
        if (username == null || password == null) {
            return null;
        }
        
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        RegistrationDto registrationDto = null;

        try {
            // 1. Get connect database
            connection = DatabaseHelper.getConnection();

            //2. Create SQL String
            if (connection != null) {
                String sql = "SELECT " + String.format("%s, %s ", LAST_NAME, ROLE)
                        + "FROM Registration "
                        + "WHERE " + USERNAME + " = ? "
                        + "AND " + PASSWORD + " = ?";

                // 3. Create Statement object
                preStatement = connection.prepareStatement(sql);
                preStatement.setString(1, username);
                preStatement.setString(2, password);

                //4. Excute Query
                resultSet = preStatement.executeQuery();

                // 5. Process Result
                if (resultSet.next()) {
                    String lastName = resultSet.getString(LAST_NAME);
                    boolean isAdmind = resultSet.getBoolean(ROLE);
                    registrationDto = new RegistrationDto(username, null, lastName, isAdmind);
                } // username and password have bean existed

            }

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preStatement != null) {
                preStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return registrationDto;
    }

    public void searchLastname(String seachValue) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        try {
            // 1. Get connect database
            connection = DatabaseHelper.getConnection();

            //2. Create SQL String
            if (connection != null) {
                String sql = "SELECT " + String.format("%s, %s, %s, %s ", USERNAME, PASSWORD, LAST_NAME, ROLE)
                        + "FROM Registration "
                        + "WHERE " + LAST_NAME + " LIKE ?";

                // 3. Create Statement object
                preStatement = connection.prepareStatement(sql);
                preStatement.setString(1, '%' + seachValue + '%');
                //4. Excute Query
                resultSet = preStatement.executeQuery();
                // 5. Process Result

                // BOF result setss
                while (resultSet.next()) {
                    // 5.1 get data from result set
                    String username = resultSet.getString(USERNAME);
                    String password = resultSet.getString(PASSWORD);
                    String lastName = resultSet.getString(LAST_NAME);
                    boolean role = resultSet.getBoolean(ROLE);

                    RegistrationDto registration = new RegistrationDto(
                            username, password, lastName, role
                    );
                    // 5.2 set data to DTO to properties

                    if (this.accountList == null) {
                        this.accountList = new ArrayList<>();
                    }
                    accountList.add(registration);

                } // connection has been available
            }

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preStatement != null) {
                preStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    public boolean deleteAccount(String userName) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preStatement = null;
        boolean result = false;
        try {
            // 1. Get connect database
            connection = DatabaseHelper.getConnection();

            //2. Create SQL String
            if (connection != null) {
                String sql = "DELETE FROM Registration "
                        + "WHERE " + USERNAME + " =? ";

                // 3. Create Statement object
                preStatement = connection.prepareStatement(sql);
                preStatement.setString(1, userName);

                //4. Excute Query
                int effectRows = preStatement.executeUpdate();

                // 5. Process Result
                if (effectRows > 0) {
                    result = true;
                }
            }

        } finally {
            if (preStatement != null) {
                preStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return result;
    }

    public boolean updateAccount(String username, String password, boolean isAdmin) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preStatement = null;
        boolean result = false;
        System.out.println("RegistrationDao: "+ username + "-" + password + "-" + isAdmin);
        
        try {
            // 1. Get connect database
            connection = DatabaseHelper.getConnection();

            //2. Create SQL String
            if (connection != null) {
                String sql = "UPDATE Registration SET "
                        + PASSWORD + " = ?, "
                        + ROLE + " = ? "
                        + "WHERE " + USERNAME + " = ? ";

                // 3. Create Statement object
                preStatement = connection.prepareStatement(sql);
                preStatement.setString(1, password);
                preStatement.setBoolean(2, isAdmin);
                preStatement.setString(3, username);

                //4. Excute Query
                int effectRows = preStatement.executeUpdate();

                // 5. Process Result
                if (effectRows > 0) {
                    result = true;
                }
            }
        } finally {
            if (preStatement != null) {
                preStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }
    
    public boolean createAccount(RegistrationDto account) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preStatement = null;
        boolean result = false;
        
        try {
            // 1. Get connect database
            connection = DatabaseHelper.getConnection();

            //2. Create SQL String
            if (connection != null) {
                String sql = "INSERT INTO Registration("
                        + String.format("%s, %s, %s, %s", USERNAME, PASSWORD, LAST_NAME, ROLE)
                        + ") VALUES ("
                        + "?, ?, ?, ?"
                        + ")";

                // 3. Create Statement object
                preStatement = connection.prepareStatement(sql);
                preStatement.setString(1, account.getUsername());
                preStatement.setString(2, account.getPassword());
                preStatement.setString(3, account.getFullName());
                preStatement.setBoolean(4, account.isRole());

                //4. Excute Query
                int effectRows = preStatement.executeUpdate();

                // 5. Process Result
                if (effectRows > 0) {
                    result = true;
                }
            }
        } finally {
            if (preStatement != null) {
                preStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        
        return result;
    }
}
