package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.PaymentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentModel {
    public String savePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        Connection connection = (Connection) DBConnection.getInstance().getConnection();
        PreparedStatement save = connection.prepareStatement("INSERT INTO payment VALUES (?,?,?,?)");
        save.setString(1, paymentDto.getPaymentId());
        save.setString(2,paymentDto.getOrderId());
        save.setString(3,paymentDto.getPaymentMethod());
        save.setDouble(4,paymentDto.getAmount());

        return save.executeUpdate() > 0 ? "Successfully added payment" : "Fail";

    }

    public String updatePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        Connection connection = (Connection) DBConnection.getInstance().getConnection();
        PreparedStatement update = connection.prepareStatement("UPDATE payment SET order_id=?, payment_method=?, amount=? WHERE payment_id=?");
        update.setString(1,paymentDto.getOrderId());
        update.setString(2,paymentDto.getPaymentMethod());
        update.setDouble(3,paymentDto.getAmount());
        update.setString(4,paymentDto.getPaymentId());

        return update.executeUpdate() > 0 ? "Successfully updated payment" : "Fail";
    }

    public String deletePayment(String paymentId) throws SQLException, ClassNotFoundException {
        Connection connection = (Connection) DBConnection.getInstance().getConnection();
        PreparedStatement update = connection.prepareStatement("DELETE FROM payment WHERE payment_id=?");
        update.setString(1,paymentId);

        return update.executeUpdate() > 0 ? "Successfully deleted payment" : "Fail";
    }
}
