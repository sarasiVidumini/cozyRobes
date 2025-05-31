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
                materialInventoryDto.getMaterialId(),
                materialInventoryDto.getSupplierId(),
                materialInventoryDto.getMaterialName(),
                materialInventoryDto.getQuantity()
        );
    }

    public ArrayList<MaterialInventoryDto> getAllMaterialInventory() throws SQLException {
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
        return materialInventoryDtoArrayList;
    }


    public boolean updateMaterialInventory(MaterialInventoryDto materialInventoryDto) throws SQLException {
        return CrudUtil.execute(
                "update material_inventory set supplier_id=? , material_name =? , quantity=? where material_id= ?",
                    materialInventoryDto.getSupplierId(),
                    materialInventoryDto.getMaterialName(),
                    materialInventoryDto.getQuantity(),
                    materialInventoryDto.getMaterialId()
        );

    }

    public boolean deleteMaterialInventory(String material_id) throws SQLException {
        return CrudUtil.execute(
                "delete from material_inventory where material_id = ?",
                material_id
        );
    }

   public ArrayList<MaterialInventoryDto> searchMaterialInventory(String search) throws SQLException {
        ArrayList<MaterialInventoryDto> dtos = new ArrayList<>();
        String sql = "select * from material_inventory where material_id LIKE ? OR supplier_id LIKE ? OR material_name LIKE ? OR quantity LIKE ?";
        String pattern = "%" + search + "%";
        ResultSet resultSet = CrudUtil.execute(sql, pattern , pattern, pattern , pattern);
        while (resultSet.next()) {
            MaterialInventoryDto dto = new MaterialInventoryDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            );
            dtos.add(dto);
        }
        return dtos;
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
