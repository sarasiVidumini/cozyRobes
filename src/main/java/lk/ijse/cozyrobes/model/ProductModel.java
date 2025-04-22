package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.ProductDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductModel {
    public String saveProduct(ProductDto productDto) throws SQLException, ClassNotFoundException {
    Connection connection = DBConnection.getInstance().connection();
    PreparedStatement save = connection.prepareStatement("INSERT INTO product VALUES (?,?,?,?,?)");
        save.setString(1, productDto.getProductId());
        save.setString(2, productDto.getName());
        save.setInt(3, productDto.getQuantity());
        save.setDouble(4, productDto.getUnitPrice());
        save.setString(5, productDto.getCategory());

        return save.executeUpdate() > 0 ? "Product saved successfully" : "Fail";
    }

    public  String updateProduct(ProductDto productDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update = connection.prepareStatement("UPDATE product SET  name = ? , quantity = ? , unitPrice = ? , category = ? WHERE product_Id = ?");
        update.setString(1, productDto.getName());
        update.setInt(2, productDto.getQuantity());
        update.setDouble(3, productDto.getUnitPrice());
        update.setString(4, productDto.getCategory());
        update.setString(5, productDto.getProductId());

        return update.executeUpdate() > 0 ? "Product updated successfully" : "Fail";
    }

    public  String deleteProduct(String productId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update = connection.prepareStatement("DELETE FROM product WHERE product_Id = ?");
        update.setString(1, productId);

        return update.executeUpdate() > 0 ? "Product deleted successfully" : "Fail";
    }

}

