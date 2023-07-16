import jakarta.xml.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "addresses")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressesDto implements Serializable {
    @XmlElementWrapper(name = "wrap")
    @XmlElement(name = "inner")
    private List<AddressDto> addresses;

    private AddressDto mainAddress;

    public AddressesDto() {
    }

    public AddressesDto(List<AddressDto> addresses, AddressDto mainAddress) {
        this.addresses = addresses;
        this.mainAddress = mainAddress;
    }

    @Override
    public String toString() {
        return "AddressesDto{" +
                "addresses=" + addresses +
                ", mainAddress=" + mainAddress +
                '}';
    }
}
