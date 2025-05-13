package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.dto.OrderDetailsDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsModel {
    public String getNextOrderDetailId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select orderDetail_id from order_details order by orderDetail_id desc limit 1");
        String tableCharacter = "OD";

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;

            return String.format(tableCharacter + "OD3%d", nextIdNumber);
        }

        return tableCharacter + "001";
    }

    public String saveOrderDetails(OrderDetailsDto orderDetailsDto) throws SQLException {
        return CrudUtil.execute(
                "insert into order_details values(?,?,?,?,?)",
                orderDetailsDto.getOrderDetail_id(),
                orderDetailsDto.getOrder_id(),
                orderDetailsDto.getProduct_id(),
                orderDetailsDto.getQuantity(),
                orderDetailsDto.getPrice_at_purchase()
        );
    }

    public String updateOrderDetails(OrderDetailsDto orderDetailsDto) throws SQLException {
        return CrudUtil.execute(
                "update order_details SET order_id = ? , product_id =? , quantity = ? , price_at_purchase = ?  where orderDetail_id = ?",
                orderDetailsDto.getOrder_id(),
                orderDetailsDto.getProduct_id(),
                orderDetailsDto.getQuantity(),
                orderDetailsDto.getPrice_at_purchase(),
                orderDetailsDto.getOrderDetail_id()
        );
    }

    public String deleteOrderDetails(String orderDetail_id) throws SQLException {
        return CrudUtil.execute(
                "delete from order_details where orderDetail_id = ?",
                orderDetail_id
        );
    }
    public OrderDetailsDto searchOrderDetails(String orderDetail_id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from order_details where orderDetail_id = ?",
                orderDetail_id);

        if (resultSet.next()) {
            OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5)
            );
            return orderDetailsDto;
        }
        return null;
    }


    public ArrayList<OrderDetailsDto> getAllOrderDetails() throws ClassNotFoundException, SQLException{
        ResultSet rst = CrudUtil.execute("SELECT * FROM order_details");
        ArrayList<OrderDetailsDto> orderDetailsDtoArrayList = new ArrayList<>();

        while(rst.next()){
            OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getInt(5)
            );

            orderDetailsDtoArrayList.add(orderDetailsDto);
        }
        return orderDetailsDtoArrayList;
    }

}