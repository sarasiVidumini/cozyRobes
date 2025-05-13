package lk.ijse.cozyrobes.model;


import lk.ijse.cozyrobes.dto.WarehouseDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WarehouseModel {
    public String getNextWarehouseId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select section_id from warehouse order by section_id desc limit 1");
        String tableCharacter = "Section";
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

    public boolean saveWarehouse(WarehouseDto warehouseDto) throws SQLException {
        return CrudUtil.execute(
                "insert into warehouse values (?,?,?,?)",
                    warehouseDto.getSection_id(),
                    warehouseDto.getProduct_id(),
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
                    "update warehouse set product_id = ? , capacity = ? , location = ? where section_id",
                   warehouseDto.getProduct_id(),
                    warehouseDto.getCapacity(),
                    warehouseDto.getLocation(),
                    warehouseDto.getSection_id()
            );
        }

    public boolean deleteWarehouse(String section_id) throws SQLException {
        return CrudUtil.execute(
                "delete from material_inventory where material_id = ?",
                section_id
        );
    }

    public WarehouseDto searchSection(String section_id) throws ClassNotFoundException, SQLException{

        ResultSet rst = CrudUtil.execute("select * from material_inventory where material_id = ?",
                section_id
        );

        if (rst.next()) {
            WarehouseDto warehouseDto = new WarehouseDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4)
            );
            return warehouseDto;
        }
        return null;
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
