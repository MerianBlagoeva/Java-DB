package softuni.exam.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ConstellationSeedDto {
    private String name;
    private String description;


    @NotBlank
    @Size(min = 3, max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank
    @Size(min = 5)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
