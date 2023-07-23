package softuni.exam.instagraphlite.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class UserUsernameDto {
    private String username;

    @NotBlank
    @Size(min = 2, max = 18)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
