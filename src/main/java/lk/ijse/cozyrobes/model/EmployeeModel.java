package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.EmployeeDto;
import lk.ijse.cozyrobes.dto.UserDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    public String getNextEmployeeId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select employee_id from employee order by employee_id desc limit 1");
        char tableCharacter = 'E';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String latIdNumberString = lastId.substring(1);
            int latIdNumber = Integer.parseInt(latIdNumberString);
            int nextIdNumber = latIdNumber + 1;
            String nextIdString = String.format(tableCharacter +"%03d", nextIdNumber);
            return nextIdString;
        }

        return tableCharacter + "001";
    }

    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException {
        return CrudUtil.execute(
                "insert into employee values(?,?,?,?,?)",
                employeeDto.getEmployee_id(),
                employeeDto.getUser_id(),
                employeeDto.getName(),
                employeeDto.getRole(),
                employeeDto.getSalary()
        );
    }

    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException {
        return CrudUtil.execute(
                "update employee set user_id = ? , name = ? , role = ? , salary = ? where employee_id",
                employeeDto.getUser_id(),
                employeeDto.getName(),
                employeeDto.getRole(),
                employeeDto.getSalary(),
                employeeDto.getEmployee_id()
        );
    }

    public boolean deleteEmployee(String employee_id) throws SQLException {
        return CrudUtil.execute(
                "delete from employee where employee_id",
                employee_id
        );
    }

    public EmployeeDto searchEmployee(String employee_id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "select * from employee where employee_id",
                employee_id
        );

        if (resultSet.next()) {
            EmployeeDto employeeDto = new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            );
            return employeeDto;
        }
        return null;
    }
    public ArrayList<EmployeeDto> getAllEmployee() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from employee");

        ArrayList<EmployeeDto> employeeDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            EmployeeDto employeeDto = new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            );

            employeeDtoArrayList.add(employeeDto);
        }

        return employeeDtoArrayList;
    }


}
