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
                "insert into user values(?,?,?,?,?)",
                userDto.getUserId(),
                userDto.getRole(),
                userDto.getName(),
                userDto.getContact(),
                userDto.getPassword()

        );

    }

    public  boolean updateUser(UserDto userDto) throws SQLException {
        return CrudUtil.execute(
                "update user set role=? , name = ? , contact =? , password = ? where user_id = ?",
                userDto.getRole(),
                userDto.getName(),
                userDto.getContact(),
                userDto.getPassword(),
                userDto.getUserId()

        );
    }

    public boolean deleteUser(String userId) throws SQLException {
        return CrudUtil.execute(
                "delete from user where user_id = ?",
                userId
        );
    }

   public ArrayList<UserDto> searchUser(String search) throws SQLException {
        ArrayList<UserDto> dtos = new ArrayList<>();
        String sql = "select * from user where user_id LIKE ? OR role LIKE ? OR name LIKE ? OR contact LIKE ? password LIKE ?";
        String pattern = "%" + search + "%";
        ResultSet resultSet = CrudUtil.execute(sql, pattern , pattern , pattern , pattern);
        while (resultSet.next()) {
            UserDto userDto = new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            dtos.add(userDto);
        }
        return dtos;
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
    public ArrayList<UserDto> getAllUser() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from user");

        ArrayList<UserDto> userDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            UserDto userDto = new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            );

            userDtoArrayList.add(userDto);
        }

        return userDtoArrayList;

    }

}
