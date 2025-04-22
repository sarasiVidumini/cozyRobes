package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.OrderDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderModel {
    public  String saveOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement save = connection.prepareStatement("INSERT INTO orders  VALUES (?,?,?,?)");
        save.setString(1, orderDto.getOrderId());
        save.setString(2, orderDto.getCustomerId());
        save.setString(3, orderDto.getStatus());
        save.setString(4, orderDto.getDate());

        return save.executeUpdate() > 0 ? "Successfully added order" : "Fail";
    }

    public String updateOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update = connection.prepareStatement("UPDATE orders SET customerId=?,status=?,date=? WHERE orderId=?");
        update.setString(1, orderDto.getCustomerId());
        update.setString(2, orderDto.getStatus());
        update.setString(3, orderDto.getDate());
        update.setString(4, orderDto.getOrderId());

        return update.executeUpdate() > 0 ? "Successfully updated order" : "Fail";
    }

    public String deleteOrder(String orderId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update = connection.prepareStatement("DELETE FROM orders WHERE orderId=?");
        update.setString(1, orderId);

        return update.executeUpdate() > 0 ? "Successfully deleted order" : "Fail";
    }

}
