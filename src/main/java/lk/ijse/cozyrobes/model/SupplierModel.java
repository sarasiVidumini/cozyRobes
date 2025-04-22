package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SupplierModel {
    public String saveSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement save = connection.prepareStatement("INSERT INTO supplier VALUES (?,?,?,?)");
        save.setString(1, supplierDto.getSupplierId());
        save.setString(2, supplierDto.getName());
        save.setString(3,supplierDto.getAddress());
        save.setString(4,supplierDto.getContact());

        return save.executeUpdate() > 0 ? "Successfully saved new supplier" : "Fail";

    }

    public String updateSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update = connection.prepareStatement("UPDATE supplier SET name=?, address=?, contact=? WHERE suppler_Id=?");
        update.setString(1, supplierDto.getName());
        update.setString(2, supplierDto.getAddress());
        update.setString(3, supplierDto.getContact());
        update.setString(4, supplierDto.getSupplierId());

        return update.executeUpdate() > 0 ? "Successfully updated new supplier" : "Fail";
    }

    public String deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update = connection.prepareStatement("DELETE FROM supplier WHERE supplier_Id=?");
        update.setString(1, supplierId);

        return update.executeUpdate() > 0 ? "Successfully deleted new supplier" : "Fail";
    }
}
