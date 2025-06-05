package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.dto.OrderDetailsDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsModel {
    private final ProductModel productModel = new ProductModel();
    public String getNextOrderDetailId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select orderDetail_id from order_details order by orderDetail_id desc limit 1");
        String tableCharacter = "OD";

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(tableCharacter.length());
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNumber);
           return nextIdString;
        }

        return tableCharacter + "001";
    }

    public boolean saveOrderDetails(OrderDetailsDto orderDetailsDto) throws SQLException {
        return CrudUtil.execute(
                "insert into order_details values(?,?,?,?,?)",
                orderDetailsDto.getOrderDetailId(),
                orderDetailsDto.getOrderId(),
                orderDetailsDto.getProductId(),
                orderDetailsDto.getQuantity(),
                orderDetailsDto.getPriceAtPurchase()
        );
    }

    public boolean updateOrderDetails(OrderDetailsDto orderDetailsDto) throws SQLException {
        return CrudUtil.execute(
                "update order_details SET order_id = ? , product_id =? , quantity = ? , price_at_purchase = ?  where orderDetail_id = ?",
                orderDetailsDto.getOrderId(),
                orderDetailsDto.getProductId(),
                orderDetailsDto.getQuantity(),
                orderDetailsDto.getPriceAtPurchase(),
                orderDetailsDto.getOrderDetailId()
        );
    }

    public boolean deleteOrderDetails(String orderDetail_id) throws SQLException {
        return CrudUtil.execute(
                "delete from order_details where orderDetail_id = ?",
                orderDetail_id
        );
    }

    public ArrayList<OrderDetailsDto> searchOrderDetails(String search) throws SQLException {
        ArrayList<OrderDetailsDto> dtos = new ArrayList<>();
        String sql = "select * from order_details where orderDetail_id LIKE ? OR order_id LIKE ? OR product_id LIKE ? OR quantity LIKE ? OR price_at_purchase LIKE ? OR update_price LIKE ?";
        String pattern = "%" + search + "%";
        ResultSet resultSet = CrudUtil.execute(sql, pattern,pattern,pattern,pattern,pattern,pattern);
        while (resultSet.next()) {
            OrderDetailsDto dto = new OrderDetailsDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5)

            );
            dtos.add(dto);
        }
        return dtos;
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
                    rst.getDouble(5)
            );

            orderDetailsDtoArrayList.add(orderDetailsDto);
        }
        return orderDetailsDtoArrayList;
    }

    public ArrayList<String> getAllOrderIds() throws ClassNotFoundException, SQLException{
        ResultSet rst = CrudUtil.execute("SELECT order_id from orders");
        ArrayList<String> orderIds = new ArrayList<>();

        while(rst.next()){
            orderIds.add(rst.getString("order_id"));
        }
        return orderIds;
    }

    public ArrayList<String> getAllProductIds() throws ClassNotFoundException, SQLException {
        ResultSet rst = CrudUtil.execute("SELECT product_id FROM product");
        ArrayList<String> productIds = new ArrayList<>();

        while (rst.next()) {
            productIds.add(rst.getString("name"));
        }
        return productIds;
    }

    public String getProductNameById(String productId) throws ClassNotFoundException, SQLException {
        ResultSet rst = CrudUtil.execute("select name from product where product_id = ?", productId);
        if (rst.next()) {
            return rst.getString("name");
        }
        return null;
    }

    public boolean saveNewOrderDetails(String orderDetailId , String orderId , String productId , int cartQty , double priceAtPurchase)  {
        try {
            return CrudUtil.execute(
                    "insert into order_details(orderDetail_id , order_id , product_id , quantity , price_at_purchase) values (?,?,?,?,?)",
                    orderDetailId,
                    orderId,
                    productId,
                    cartQty,
                    priceAtPurchase
            );
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}