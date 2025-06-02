package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.OrderDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderModel {
    public String getNextOrderId() throws SQLException {
    ResultSet resultSet = CrudUtil.execute("select order_id from orders order by order_id desc limit 1");
    char tableCharacter = 'O';
    if (resultSet.next()) {
        String lastId = resultSet.getString(1);
        String lastIdNumberString = lastId.substring(1);
        int lastIdNumber = Integer.parseInt(lastIdNumberString);
        int nextIdNumber = lastIdNumber + 1;
        String nextIdString = String.format(tableCharacter +"%03d", nextIdNumber);
        return nextIdString;
    }
    return tableCharacter + "001";
    }

    public boolean saveOrder(OrderDto orderDto) throws SQLException {
        return CrudUtil.execute(
                "insert into orders (?,?,?,?,?)",
                orderDto.getOrderId(),
                orderDto.getCustomerId(),
                orderDto.getOrderDate(),
                orderDto.getStatus(),
                orderDto.getProductId()
        );
    }

    public boolean updateOrder(OrderDto orderDto) throws SQLException {
        return CrudUtil.execute(
                "update orders set customer_id =?, order_date = ?, status = ? , product_id = ? where order_id = ?",
                orderDto.getCustomerId(),
                orderDto.getStatus(),
                orderDto.getOrderDate(),
                orderDto.getProductId(),
                orderDto.getOrderId()
        );
    }

    public boolean deleteOrder(String order_id) throws SQLException {
        return CrudUtil.execute(
                "delete from orders where order_id ?",
                order_id
        );
    }

   public ArrayList<OrderDto> searchOrder(String search) throws SQLException {
        ArrayList<OrderDto> dtos = new ArrayList<>();
        String sql = "select * from orders where order_id like ? OR customer_id like ? OR  order_date like ? OR status like ? OR product_id like ?";
        String pattern = "%" + search + "%";
        ResultSet resultSet = CrudUtil.execute(sql, pattern,pattern,pattern,pattern,pattern);
        while (resultSet.next()) {
            OrderDto orderDto = new OrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            dtos.add(orderDto);
        }
        return dtos;
   }

    public ArrayList<OrderDto> getAllOrder() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from orders");
        ArrayList<OrderDto> orderDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            OrderDto orderDto = new OrderDto(
                   resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );

            orderDtoArrayList.add(orderDto);
        }

        return orderDtoArrayList;

    }
}
