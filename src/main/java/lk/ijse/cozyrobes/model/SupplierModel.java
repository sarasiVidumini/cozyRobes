package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.dto.SupplierDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {

    public String getNextSupplierId() throws SQLException {
        ResultSet resultSet = (ResultSet) CrudUtil.execute("select supplier_id from supplier order by supplier_id desc limit 1");

        char prefix = 'S';

        if (resultSet.next()) {
            String lastId = resultSet.getString(1); // e.g., "S017"
            String numberPart = lastId.substring(1); // remove 'S'
            int nextNumber = Integer.parseInt(numberPart) + 1;
            return String.format("%c%03d", prefix, nextNumber); // e.g., "S018"
        }

        return prefix + "001";
    }

    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException {
        return (Boolean) CrudUtil.execute(
                "INSERT INTO supplier VALUES (?, ?, ?, ?)",
                supplierDto.getSupplierId(),
                supplierDto.getName(),
                supplierDto.getAddress(),
                supplierDto.getContact()
        );
    }

    public ArrayList<SupplierDto> getAllSuppliers() throws SQLException {
        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT * FROM supplier");

        ArrayList<SupplierDto> supplierDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            SupplierDto supplierDto = new SupplierDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            supplierDtoArrayList.add(supplierDto);
        }

        return supplierDtoArrayList;
    }

    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException {
        return (Boolean) CrudUtil.execute(
                "update supplier set name = ?, address = ?, contact = ? WHERE supplier_id = ?",
                supplierDto.getName(),
                supplierDto.getAddress(),
                supplierDto.getContact(),
                supplierDto.getSupplierId()
        );
    }

    public boolean deleteSupplier(String supplier_id) throws SQLException {
        return (Boolean) CrudUtil.execute(
                "DELETE FROM supplier WHERE supplier_id = ?",
                supplier_id
        );
    }

    public ArrayList<SupplierDto> searchSupplier(String search) throws SQLException {
        ArrayList<SupplierDto> dtos = new ArrayList<>();
        String sql = "SELECT * FROM supplier WHERE supplier_id LIKE ? OR name LIKE ? OR address LIKE ? OR contact LIKE ?";
        String pattern = "%" + search + "%";
        ResultSet resultSet = (ResultSet) CrudUtil.execute(sql, pattern, pattern, pattern, pattern);

        while (resultSet.next()) {
            SupplierDto dto = new SupplierDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            dtos.add(dto);
        }
        return dtos;
    }

    public ArrayList<String> getAllSuppliersIds() throws SQLException {
        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT supplier_id FROM supplier");

        ArrayList<String> list = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }

        return list;
    }
}
