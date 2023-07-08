package bg.softuni.springdataautomappingobjects.dto;

import java.math.BigDecimal;

public class EmployeeDto extends BaseEmployeeDto {
    private BigDecimal income;

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}
