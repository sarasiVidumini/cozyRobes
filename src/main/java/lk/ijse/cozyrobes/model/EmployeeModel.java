package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.CustomerDto;
import lk.ijse.cozyrobes.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeModel {
    public String saveEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement save = connection.prepareStatement("INSERT INTO employee VALUES (?,?,?,?,?,?,?,?)");
        save.setString(1, employeeDto.getEmployeeId());
        save.setString(2, employeeDto.getUserId());
        save.setString(3, employeeDto.getName());
        save.setString(4, employeeDto.getRole());
        save.setString(5, employeeDto.getSalary());

        return save.executeUpdate() > 0 ? "Successfully saved an employee " : "Fail";
    }

    public String updateEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update = connection.prepareStatement("UPDATE employee SET userId = ?, name = ?, role = ?, salary = ? WHERE employeeId = ?");
        update.setString(1, employeeDto.getUserId());
        update.setString(2, employeeDto.getName());
        update.setString(3, employeeDto.getRole());
        update.setString(4, employeeDto.getSalary());
        update.setString(5, employeeDto.getEmployeeId());

        return update.executeUpdate() > 0 ? "Successfully updated an employee " : "Fail";
    }

    public String deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update = connection.prepareStatement("DELETE FROM employee WHERE employeeId = ?");
        update.setString(1, employeeId);

        return update.executeUpdate() > 0 ? "Successfully deleted an employee " : "Fail";
    }


}
