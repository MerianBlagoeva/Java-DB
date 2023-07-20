package softuni.exam.models.dto.seed;

import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class CountryImportDto implements Serializable {

    @Size(min = 2, max = 60)
    private String countryName;
    @Size(min = 2, max = 20)
    private String currency;

    public CountryImportDto() {
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
