package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.ProductDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductModel {

    public String getNextProductId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select product_id from product order by product_id desc limit 1");
        char tableCharacter = 'P';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdNumberString = String.format(tableCharacter+"%03d", nextIdNumber);
            return nextIdNumberString;
        }
        return tableCharacter +"001";
    }

    public boolean saveProduct(ProductDto productDto) throws SQLException {
        return CrudUtil.execute(
                "insert into customer values(?,?,?,?,?)",
                productDto.getProduct_id(),
                productDto.getName(),
                productDto.getQuantity(),
                productDto.getCategory(),
                productDto.getUnit_price()
        );
    }

    public ArrayList<ProductDto> getAllProduct() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from product");
        ArrayList<ProductDto> productDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            ProductDto productDto = new ProductDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            );
            productDtoArrayList.add(productDto);
        }

        return productDtoArrayList;
    }

    public boolean updateProduct(ProductDto productDto) throws SQLException {
        return CrudUtil.execute(
                "update product set name = ? , quantity = ? , category = ? , unitPrice = ? where product_id = ?",
                productDto.getName(),
                productDto.getQuantity(),
                productDto.getCategory(),
                productDto.getUnit_price(),
                productDto.getProduct_id()
        );
    }

    public boolean deleteProduct(String product_id) throws SQLException {
        return CrudUtil.execute(
                "delete from product where product_id = ?" ,
                product_id

        );

    }

    public ProductDto searchProduct(String product_id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "select * from product where product_id",
                product_id
        );

        if (resultSet.next()) {
            ProductDto productDto = new ProductDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            );
            return productDto;
        }
        return null;
    }

    public ArrayList<String> getAllProductIds() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select product_id from product");
        ArrayList<String> list = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }

        return list;
    }

    public boolean reduceQty(int qty, String product_id) throws SQLException {
        return CrudUtil.execute("UPDATE product SET quantity = quantity - ? WHERE product_id = ?", qty, product_id);
    }


}

