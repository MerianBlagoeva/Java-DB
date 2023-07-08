package bg.softuni.springdataautomappingobjects.dto;

import java.util.List;

public class ManagerDto extends BaseEmployeeDto {
    private List<EmployeeDto> subordinates;

    public List<EmployeeDto> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<EmployeeDto> subordinates) {
        this.subordinates = subordinates;
    }
}
