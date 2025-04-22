package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.OrderProductDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderProductModel {
    public String saveOrderProduct(OrderProductDto orderProductDto) throws SQLException, ClassNotFoundException {
        Connection connection = (Connection) DBConnection.getInstance().getConnection();
        PreparedStatement save = connection.prepareStatement("INSERT INTO orderProduct VALUES (?,?,?,?)");
        save.setString(1, orderProductDto.getOrderProductId());
        save.setString(2, orderProductDto.getOrderId());
        save.setString(3,orderProductDto.getProductId());
        save.setInt(4, orderProductDto.getQuantity());

        return save.executeUpdate() > 0 ? "Successfully saved order product" : "Fail";
    }

    public String updateOrderProduct(OrderProductDto orderProductDto) throws SQLException, ClassNotFoundException {
        Connection connection = (Connection) DBConnection.getInstance().getConnection();
        PreparedStatement update = connection.prepareStatement("UPDATE orderProduct SET orderId=?, productId=?, quantity=? WHERE orderProductId=?");
        update.setString(1, orderProductDto.getOrderId());
        update.setString(2, orderProductDto.getProductId());
        update.setInt(3, orderProductDto.getQuantity());
        update.setString(4, orderProductDto.getOrderProductId());

        return update.executeUpdate() > 0 ? "Successfully updated order product" : "Fail";
    }

    public String deleteOrderProduct(String orderProductId) throws SQLException, ClassNotFoundException {
        Connection connection = (Connection) DBConnection.getInstance().getConnection();
        PreparedStatement update = connection.prepareStatement("DELETE FROM orderProduct WHERE orderProductId=?");
        update.setString(1, orderProductId);

        return update.executeUpdate() > 0 ? "Successfully deleted order product" : "Fail";
    }
}
