package lk.ijse.cozyrobes.util;

import lk.ijse.cozyrobes.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
   public static <T> T execute(String sql , Object... obj) throws SQLException {
       Connection connection = DBConnection.getInstance().getConnection();
       PreparedStatement ps = connection.prepareStatement(sql);

       for (int i = 0; i < obj.length; i++) {
           ps.setObject(i+1, obj[i]);
       }

       if (sql.startsWith("SELECT") || sql.startsWith("select")) {
           ResultSet rs = ps.executeQuery();
           return (T) rs;
       }else {
           int i = ps.executeUpdate();
           boolean isSuccess = i > 0;

           return (T) (Boolean) isSuccess;
       }
   }
}


