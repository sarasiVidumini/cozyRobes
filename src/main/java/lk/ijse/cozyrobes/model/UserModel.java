package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.UserDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {
    public String getNextUserId() throws SQLException {
//        Connection connection = (Connection) DBConnection.getInstance().getConnection();
//        PreparedStatement save = connection.prepareStatement("INSERT INTO users VALUES (?,?,?,?,?)");
//        save.setString(1, user.getUserId());
//        save.setString(2, user.getRole());
//        save.setString(3, user.getName());
//        save.setString(4, user.getContact());

//        return save.executeUpdate() > 0 ? "Successfully added an new user" : "Fail";

        ResultSet resultSet = CrudUtil.execute("select user_id from user order by user_id desc limit 1");
        char tableCharacter = 'U';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int latIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = latIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNumber);
            return nextIdString;
        }

        return tableCharacter + "001";
    }

public boolean saveUser(UserDto userDto) throws SQLException {
        return CrudUtil.execute(
                "insert into user values(?,?,?,?)",
                userDto.getUser_id(),
                userDto.getRole(),
                userDto.getName(),
                userDto.getContact()

        );

    }

    public ArrayList<UserDto> getAllUser() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from user");

        ArrayList<UserDto> userDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            UserDto userDto = new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            );

            userDtoArrayList.add(userDto);
        }

        return userDtoArrayList;

    }

    public  boolean updateUser(UserDto userDto) throws SQLException {
        return CrudUtil.execute(
                "update user set role=? , name = ? , contact =? where user_id",
                userDto.getRole(),
                userDto.getName(),
                userDto.getContact(),
                userDto.getUser_id()
        );
    }

    public boolean deleteUser(String userId) throws SQLException {
        return CrudUtil.execute(
                "delete from user where user_id",
                userId
        );
    }

    public UserDto searchUser(String user_id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from user where user_id=?",
                user_id);

        if (resultSet.next()) {
            UserDto userDto = new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            return userDto;
        }
        return null;
    }

    public ArrayList<String> getAllUserIds() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select user_id from user");
        ArrayList<String> list = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }

        return list;

    }

}
