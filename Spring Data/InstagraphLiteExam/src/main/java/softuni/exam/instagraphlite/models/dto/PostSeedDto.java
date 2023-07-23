package softuni.exam.instagraphlite.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class PostSeedDto {

    private String caption;
    private UserUsernameDto user;
    private PicturePathDto picture;

    @Size(min = 21)
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @NotNull
    public UserUsernameDto getUser() {
        return user;
    }

    public void setUser(UserUsernameDto user) {
        this.user = user;
    }

    @NotNull
    public PicturePathDto getPicture() {
        return picture;
    }

    public void setPicture(PicturePathDto picture) {
        this.picture = picture;
    }
}
