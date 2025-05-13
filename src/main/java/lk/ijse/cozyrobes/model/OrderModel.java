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
        ResultSet resultSet = CrudUtil.execute("select order_id from orders order by order_id limit 1");
        char tableCharacter = 'O';
        while (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNumber);
            return nextIdString;
        }

        return  tableCharacter + "001";
    }

    public boolean saveOrder(OrderDto orderDto) throws SQLException {
        return CrudUtil.execute(
                "insert into orders (?,?,?,?,?)",
                orderDto.getOrder_id(),
                orderDto.getCustomer_id(),
                orderDto.getDate(),
                orderDto.getStatus(),
                orderDto.getProduct_id()
        );
    }

    public String updateOrder(OrderDto orderDto) throws SQLException {
        return CrudUtil.execute(
                "update orders set customer_id =?, status = ?, date = ?, product_id = ? where order_id = ?",
                orderDto.getCustomer_id(),
                orderDto.getStatus(),
                orderDto.getDate(),
                orderDto.getProduct_id(),
                orderDto.getOrder_id()
        );
    }

    public String deleteOrder(String order_id) throws SQLException {
        return CrudUtil.execute(
                "delete from orders where order_id ?",
                order_id
        );
    }

    public OrderDto SearchOrder(String order_id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "select * from orders where order_id = ?",
                order_id
        );

              if (resultSet.next()) {
                  OrderDto orderDto = new OrderDto(
                          resultSet.getString("order_id"),
                          resultSet.getString("customer_id"),
                          resultSet.getString("status"),
                          resultSet.getString("date"),
                          resultSet.getString("customer_id")
                  );
                  return orderDto;
              }

              return null;
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
