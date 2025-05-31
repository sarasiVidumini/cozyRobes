package lk.ijse.cozyrobes.model;


import lk.ijse.cozyrobes.dto.WarehouseDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WarehouseModel {
    public boolean saveWarehouse(WarehouseDto warehouseDto) throws SQLException {
        return CrudUtil.execute(
                "insert into warehouse values (?,?,?,?)",
                    warehouseDto.getSectionId(),
                    warehouseDto.getProductId(),
                    warehouseDto.getCapacity(),
                    warehouseDto.getLocation()
        );
    }

    public ArrayList<WarehouseDto> getAllWarehouses() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from warehouse");
        ArrayList<WarehouseDto> warehouseDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            WarehouseDto warehouseDto = new WarehouseDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4)
            );
            warehouseDtoArrayList.add(warehouseDto);
        }

        return warehouseDtoArrayList;
    }


        public boolean  updateWarehouse(WarehouseDto warehouseDto) throws SQLException {
            return CrudUtil.execute(
                    "update warehouse set product_id = ? , capacity = ? , location = ? where section_id = ?",
                   warehouseDto.getProductId(),
                    warehouseDto.getCapacity(),
                    warehouseDto.getLocation(),
                    warehouseDto.getSectionId()
            );
        }

    public boolean deleteWarehouse(String section_id) throws SQLException {
        return CrudUtil.execute(
                "delete from warehouse where section_id = ?",
                section_id
        );
    }

   public ArrayList<WarehouseDto> searchWarehouse(String search) throws SQLException {
        ArrayList<WarehouseDto> dtos = new ArrayList<>();
        String sql = "select * from warehouse where section_id LIKE ? OR product_id LIKE ? OR capacity LIKE ? OR location LIKE ?";
        String pattern = "%" + search + "%";
        ResultSet resultSet = CrudUtil.execute(sql, pattern,pattern,pattern,pattern);
        while (resultSet.next()) {
            WarehouseDto dto = new WarehouseDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4)
            );
            dtos.add(dto);
        }
        return dtos;
   }

    public ArrayList<String> getAllSectionIds() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select section_id from warehouse");
        ArrayList<String> list = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }
        return list;
    }
}
