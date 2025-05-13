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
               customerDto.getCustomer_id(),
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
                customerDto.getCustomer_id()
        );

    }

    public boolean deleteCustomer(String customerId) throws SQLException {
        return CrudUtil.execute(
                "delete from customer where customer_id = ?",
                customerId
        );
    }

    public CustomerDto searchCustomer(String customer_id) throws ClassNotFoundException, SQLException{

        ResultSet rst = CrudUtil.execute("select * from customer where customer_id = ?",
                customer_id);

        if(rst.next()){
            CustomerDto dto = new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            return dto;
        }
        return null;
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
