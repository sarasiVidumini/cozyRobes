package lk.ijse.cozyrobes.model;

import javafx.scene.control.Alert;
import lk.ijse.cozyrobes.dto.ProductDto;
import lk.ijse.cozyrobes.util.CrudUtil;

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
                productDto.getProductId(),
                productDto.getName(),
                productDto.getQuantity(),
                productDto.getCategory(),
                productDto.getUnitPrice()
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
                "update product set name = ? , quantity = ? , category = ? , unit_price = ? where product_id = ?",
                productDto.getName(),
                productDto.getQuantity(),
                productDto.getCategory(),
                productDto.getUnitPrice(),
                productDto.getProductId()
        );
    }

    public boolean deleteProduct(String product_id) throws SQLException {
        return CrudUtil.execute(
                "delete from product where product_id = ?" ,
                product_id

        );

    }

    public ArrayList<ProductDto> searchProduct(String search) throws SQLException {
        ArrayList<ProductDto> dtos = new ArrayList<>();
        String sql = "select * from product where product_id Like ? OR name LIKE ? OR quantity LIKE ? OR category LIKE ? OR unit_price LIKE ?";
        String pattern = "%" + search + "%";
        ResultSet resultSet = CrudUtil.execute(sql, pattern , pattern, pattern, pattern , pattern);
        while (resultSet.next()) {
            ProductDto dto = new ProductDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            );
            dtos.add(dto);
        }
        return dtos;
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

    public ProductDto getProductByIds(String productId){
        try {
            ResultSet resultSet = CrudUtil.execute("select * from product where product_id = ?", productId);
            if (resultSet.next()) {
                return new ProductDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load products by Product ID...").show();
        }
        return null;
    }


    public boolean reduceQty(String product_id , int cartQty) throws SQLException {
       try {
           ResultSet resultSet = CrudUtil.execute(
                   "select quantity from product where product_id = ?",
                   product_id
           );
           if (resultSet.next()) {
               int currentQty = resultSet.getInt("quantity");
               if (currentQty >= cartQty) {
                   int newQty = currentQty - cartQty;
                   return CrudUtil.execute(
                           "update product set quantity = ? where product_id=?",
                           newQty,
                           product_id
                   );
               }else {
                   new Alert(Alert.AlertType.ERROR, "Insufficient stock for product id :  " + product_id).show();
                   return false;
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
           new Alert(Alert.AlertType.ERROR, "Error reducing product quantity...").show();
       }
       return false;
    }

    public String getProductNameById(String newVal) throws SQLException {
        try {
           ResultSet resultSet = CrudUtil.execute("select name from product where product_id = ?", newVal);
           if (resultSet.next()) {
               return resultSet.getString("name");
           }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load product name by ID...").show();
        }

        return null;
    }

    public boolean increaseQty(String productId , int quantity) throws SQLException {
        try {
            ResultSet resultSet = CrudUtil.execute(
                    "select quantity from product where product_id = ?",
                    productId
            );
            if (resultSet.next()) {
                int currentQty = resultSet.getInt("quantity");
                int newQty = currentQty + quantity;
                return CrudUtil.execute(
                        "update product set quantity = ? where product_id = ?",
                        newQty,
                        productId
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error increasing product quantity...").show();
        }
        return false;
    }
}

