package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.PaymentDto;
import lk.ijse.cozyrobes.dto.QuickcheckDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {
    public String getNextPaymentId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select payment_id from payment order by payment_id desc limit 1");
        String tableCharacter = "PA";
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNUmber); // "C002"
            return nextIdString;
        }
        return tableCharacter + "001";
    }

    public boolean savePayment(PaymentDto paymentDto) throws SQLException {
        return CrudUtil.execute(
                "insert into payment values (?,?,?,?)",
                paymentDto.getPayment_id(),
                paymentDto.getOrder_id(),
                paymentDto.getPayment_method(),
                paymentDto.getAmount()
        );
    }

    public ArrayList<PaymentDto> getAllPayment() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from payment");
        ArrayList<PaymentDto> paymentDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            PaymentDto paymentDto = new PaymentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
            paymentDtoArrayList.add(paymentDto);
        }
        return paymentDtoArrayList;
    }


    public boolean  updatePayment(PaymentDto paymentDto) throws SQLException {
        return CrudUtil.execute(
                "update payment set order_id = ? , payment_method = ? , amount = ? where payment_id",
                paymentDto.getOrder_id(),
                paymentDto.getPayment_method(),
                paymentDto.getAmount(),
                paymentDto.getPayment_id()
        );
    }

    public boolean deletePayment(String payment_id) throws SQLException {
        return CrudUtil.execute(
                "delete from payment where payment_id = ?",
                payment_id
        );
    }

    public  PaymentDto searchPayment(String payment_id) throws ClassNotFoundException, SQLException{

        ResultSet rst = CrudUtil.execute("select * from payment where payment_id = ?",
                payment_id
        );

        if (rst.next()) {
            PaymentDto paymentDto = new PaymentDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            );
            return paymentDto;
        }
        return null;
    }

    public ArrayList<String> getAllPaymentIds() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select payment_id from payment");
        ArrayList<String> list = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }
        return list;
    }
}
