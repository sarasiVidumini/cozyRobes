package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.dto.SupplierDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
   public String getNextSupplierId() throws SQLException {
       ResultSet resultSet = CrudUtil.execute("select supplier_id from supplier order by supplier_id desc limit 1");
       char tableCharacter = 'S';
       if (resultSet.next()) {
           String lastId = resultSet.getString(1);
           String lastIdNumberString= lastId.substring(1);
           int lastIdNumber = Integer.parseInt(lastIdNumberString);
           int nextIdNumber = lastIdNumber + 1;
           String nextIdString = String.format("%03d", nextIdNumber);
           return nextIdString;
       }

       return tableCharacter + "001";
   }

   public boolean saveSupplier(SupplierDto supplierDto) throws SQLException {
       return CrudUtil.execute(
               "insert into supplier values(?,?,?,?)",
               supplierDto.getSupplier_id(),
               supplierDto.getName(),
               supplierDto.getAddress(),
               supplierDto.getContact()
       );
   }

   public ArrayList<SupplierDto> getAllSuppliers() throws SQLException {
       ResultSet resultSet = CrudUtil.execute("select * from supplier");

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
       return CrudUtil.execute(
               "update supplier set name=? , address = ? , contact=? where supplier_id",
               supplierDto.getName(),
               supplierDto.getAddress(),
               supplierDto.getContact(),
               supplierDto.getSupplier_id()
       );
   }

   public boolean deleteSupplier(String supplierId) throws SQLException {
       return CrudUtil.execute(
               "delete from supplier where supplier_id",
               supplierId
       );
   }

   public SupplierDto searchSupplier(String supplier_id) throws SQLException {
       ResultSet resultSet = CrudUtil.execute(
               "select * from supplier where supplier_id = ?",
               supplier_id
       );

       if (resultSet.next()) {
           SupplierDto supplierDto = new SupplierDto(
                   resultSet.getString(1),
                   resultSet.getString(2),
                   resultSet.getString(3),
                   resultSet.getString(4)
           );
           return supplierDto;
       }
       return null;
   }

   public ArrayList<String> getAllSuppliersIds() throws SQLException {
       ResultSet resultSet = CrudUtil.execute("select supplier_id from supplier");
       ArrayList<String> list = new ArrayList<>();
       while (resultSet.next()) {
           String id = resultSet.getString(1);
           list.add(id);
       }

       return list;
   }
}
