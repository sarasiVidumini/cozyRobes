package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.CustomerDto;
import lk.ijse.cozyrobes.dto.MaterialInventoryDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialInventoryModel {
    public String getNextInventoryId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select material_id from material_inventory order by material_id desc limit 1");
        char tableCharacter = 'M';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNUmber); // "C002"
            return nextIdString;
        }
        return tableCharacter + "001";
    }

    public boolean saveMaterialInventory(MaterialInventoryDto materialInventoryDto) throws SQLException {
        return CrudUtil.execute(
                "insert into material_inventory values (?,?,?,?)",
                materialInventoryDto.getMaterial_id(),
                materialInventoryDto.getSupplier_id(),
                materialInventoryDto.getMaterial_name(),
                materialInventoryDto.getQuantity()
        );
    }

    public ArrayList<CustomerDto> getAllMaterialInventory() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from material_inventory");
        ArrayList<MaterialInventoryDto> materialInventoryDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            MaterialInventoryDto materialInventoryDto = new MaterialInventoryDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            );
            materialInventoryDtoArrayList.add(materialInventoryDto);
        }
        return null;
    }


    public boolean updateMaterialInventory(MaterialInventoryDto materialInventoryDto) throws SQLException {
        return CrudUtil.execute(
                "update material_inventory set supplier_id=? , material_name =? , quantity=? where material_id= ?",
                    materialInventoryDto.getSupplier_id(),
                    materialInventoryDto.getMaterial_name(),
                    materialInventoryDto.getQuantity(),
                    materialInventoryDto.getMaterial_id()
        );

    }

    public boolean deleteMaterialInventory(String material_id) throws SQLException {
        return CrudUtil.execute(
                "delete from material_inventory where material_id = ?",
                material_id
        );
    }

    public MaterialInventoryDto searchMaterialInventory(String material_id) throws ClassNotFoundException, SQLException{

        ResultSet rst = CrudUtil.execute("select * from material_inventory where material_id = ?",
                material_id);

        if (rst.next()) {
            MaterialInventoryDto materialInventoryDto = new MaterialInventoryDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4)
            );
            return materialInventoryDto;
        }
        return null;
    }

    public ArrayList<String> getAllMaterialInventoryIds() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select material_id from material_inventory");
        ArrayList<String> list = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }
        return list;
    }
}
