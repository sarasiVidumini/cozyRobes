package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.dto.CustomerDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public String getNextCustomerId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select customer_id from customer order by customer_id desc  limit 1");
        char tableCharacter = 'C';
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

public boolean saveCustomer(CustomerDto customerDto) throws SQLException {
       return CrudUtil.execute(
               "insert into customer values (?,?,?,?)",
               customerDto.getCustomerId(),
               customerDto.getName(),
               customerDto.getPhone(),
               customerDto.getEmail()
       );
    }

public ArrayList<CustomerDto> getAllCustomer() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from customer");
    ArrayList<CustomerDto> customerDTOArrayList = new ArrayList<>();
    while (resultSet.next()) {
        CustomerDto customerDTO = new CustomerDto(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4)
        );
        customerDTOArrayList.add(customerDTO);
    }

    return customerDTOArrayList;
    }

    public boolean updateCustomer(CustomerDto customerDto) throws SQLException {
        return CrudUtil.execute(
                "update customer set name=? , phone =? , email=? where customer_id= ?",
                customerDto.getName(),
                customerDto.getPhone(),
                customerDto.getEmail(),
                customerDto.getCustomerId()
        );

    }

    public boolean deleteCustomer(String customerId) throws SQLException {
        return CrudUtil.execute(
                "delete from customer where customer_id = ?",
                customerId
        );
    }

   public ArrayList<CustomerDto> searchCustomer(String search) throws SQLException {
        ArrayList<CustomerDto> dtos = new ArrayList<>();
        String sql = "SELECT  * from customer where customer_id LIKE ? OR name LIKE ? OR phone LIKE ? OR email LIKE ?";
        String pattern = "%" + search + "%";
        ResultSet resultSet = CrudUtil.execute(sql, pattern ,pattern, pattern, pattern);
        while (resultSet.next()) {
            CustomerDto dto = new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            dtos.add(dto);
        }
        return dtos;
   }

    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select customer_id from customer");
        ArrayList<String> list = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }
        return list;
    }


}
