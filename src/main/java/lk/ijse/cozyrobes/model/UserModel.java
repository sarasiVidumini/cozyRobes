package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserModel {
    public String saveUser(UserDto user) throws SQLException, ClassNotFoundException {
        Connection connection = (Connection) DBConnection.getInstance().getConnection();
        PreparedStatement save = connection.prepareStatement("INSERT INTO users VALUES (?,?,?,?,?)");
        save.setString(1, user.getUserId());
        save.setString(2, user.getRole());
        save.setString(3, user.getName());
        save.setString(4, user.getContact());

        return save.executeUpdate() > 0 ? "Successfully added an new user" : "Fail";
    }

    public String updateUser(UserDto user) throws SQLException, ClassNotFoundException {
        Connection connection = (Connection) DBConnection.getInstance().getConnection();
        PreparedStatement update = connection.prepareStatement("UPDATE users SET role=?, name=?, contact=? WHERE userId=?");
        update.setString(1, user.getRole());
        update.setString(2, user.getName());
        update.setString(3, user.getContact());
        update.setString(4, user.getUserId());

        return update.executeUpdate() > 0 ? "Successfully updated an existing user" : "Fail";

    }

    public String deleteUser(String userId) throws SQLException, ClassNotFoundException {
        Connection connection = (Connection) DBConnection.getInstance().getConnection();
        PreparedStatement update = connection.prepareStatement("DELETE FROM users WHERE userId=?");
        update.setString(1, userId);

        return update.executeUpdate() > 0 ? "Successfully deleted an existing user" : "Fail";
    }


}
