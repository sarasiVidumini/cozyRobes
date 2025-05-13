package lk.ijse.cozyrobes.util;

import lk.ijse.cozyrobes.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    public static <T> T execute(String sql , Object... obj) throws SQLException {
        Connection connectin = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connectin.prepareStatement(sql);
        for (int i = 0; i < obj.length; i++) {
            preparedStatement.setObject(i+1, obj[i]);
        }

        if (sql.startsWith("select") || sql.startsWith("SELECT")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            return (T) resultSet;
        }else {
            int i = preparedStatement.executeUpdate();

            boolean isSuccess = i > 0;

            return (T) (Boolean) isSuccess;
        }
    }
}
