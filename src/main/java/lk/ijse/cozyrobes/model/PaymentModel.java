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
   public String getNextPaymentId() throws ClassNotFoundException, SQLException {
       ResultSet resultSet = CrudUtil.execute("select payment_id from payment order by payment_id desc limit 1");
       String tableCharacter = "PA";
       if (resultSet.next()) {
           String lastId = resultSet.getString(1);
           String lastNumberString  = lastId.substring(tableCharacter.length());
           int lastIdNumber = Integer.parseInt(lastNumberString);
           int nextIdNumber = lastIdNumber + 1;
           String nextNumberString = String.format(tableCharacter + "%03d" ,nextIdNumber);
           return nextNumberString;
       }
       return tableCharacter + "001";
   }

    public boolean savePayment(PaymentDto paymentDto) throws SQLException {
        return CrudUtil.execute(
                "insert into payment values (?,?,?,?)",
                paymentDto.getPaymentId(),
                paymentDto.getOrderId(),
                paymentDto.getPaymentMethod(),
                paymentDto.getTotalAmount()
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
                "update payment set order_id = ? , payment_method = ? , total_amount = ? where payment_id = ?",
                paymentDto.getOrderId(),
                paymentDto.getPaymentMethod(),
                paymentDto.getTotalAmount(),
                paymentDto.getPaymentId()
        );
    }

    public boolean deletePayment(String payment_id) throws SQLException {
        return CrudUtil.execute(
                "delete from payment where payment_id = ?",
                payment_id
        );
    }

   public ArrayList<PaymentDto> searchPayment(String search) throws SQLException {
       ArrayList<PaymentDto> dtos = new ArrayList<>();
       String sql = "select * from payment where payment_id LIKE ? OR order_id LIKE ? OR payment_method LIKE ? OR total_amount LIKE ?";
       String pattern = "%" + search + "%";
       ResultSet resultSet = CrudUtil.execute(sql, pattern,pattern,pattern,pattern);
       while (resultSet.next()) {
           PaymentDto paymentDto = new PaymentDto(
                   resultSet.getString(1),
                   resultSet.getString(2),
                   resultSet.getString(3),
                   resultSet.getDouble(4)
           );
           dtos.add(paymentDto);
       }
       return dtos;
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
