import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;

public class UnmarshallMain {
    public static void main(String[] args) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(AddressDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

//        AddressDto addressDto = (AddressDto) unmarshaller.unmarshal(System.in);
//        System.out.println(addressDto);

        JAXBContext jaxbListContext = JAXBContext.newInstance(AddressesDto.class);
        Unmarshaller listunmarshaller = jaxbListContext.createUnmarshaller();

//        AddressesDto addressesDto = (AddressesDto) listunmarshaller.unmarshal(System.in);
//        System.out.println(addressesDto);

        InputStream resourceAsStream = UnmarshallMain.class.getResourceAsStream("simple.xml");
        AddressesDto fromFile = (AddressesDto) listunmarshaller.unmarshal(resourceAsStream);

        System.out.println(fromFile);

    }
}
