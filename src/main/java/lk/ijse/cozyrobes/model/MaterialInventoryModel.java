package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.MaterialInventoryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MaterialInventoryModel {
    public String saveMaterial(MaterialInventoryDto materialInventoryDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement save = connection.prepareStatement("INSERT INTO material_inventory VALUES (?,?,?,?)");
        save.setString(1, materialInventoryDto.getMaterialId());
        save.setString(2,materialInventoryDto.getSupplierId());
        save.setString(3,materialInventoryDto.getMaterialName());
        save.setInt(4,materialInventoryDto.getQuantity());

        return save.executeUpdate() > 0 ? "Successfully saveed material" : "Fail";

    }

    public String updateMaterial(MaterialInventoryDto materialInventoryDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update = connection.prepareStatement("UPDATE material_inventory SET supplier_id=?,material_name=?,quantity=? WHERE material_id=?");
        update.setString(1,materialInventoryDto.getSupplierId());
        update.setString(2,materialInventoryDto.getMaterialName());
        update.setInt(3,materialInventoryDto.getQuantity());
        update.setString(4,materialInventoryDto.getMaterialId());

        return update.executeUpdate() > 0 ? "Successfully updated material" : "Fail";
    }

    public String deleteMaterial(String materialId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update = connection.prepareStatement("DELETE FROM material_inventory WHERE material_id=?");
        update.setString(1,materialId);

        return update.executeUpdate() > 0 ? "Successfully deleted material" : "Fail";
    }
}
