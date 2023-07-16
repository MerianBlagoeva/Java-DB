import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.util.List;

public class MarshallMain {
    public static void main(String[] args) throws JAXBException {

        AddressDto addressDto = new AddressDto(10000, "BG", "Sofia");
        JAXBContext jaxbContext = JAXBContext.newInstance(AddressDto.class);

        Marshaller marshaller = jaxbContext.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(addressDto, System.out);


        JAXBContext jaxbListContext = JAXBContext.newInstance(AddressesDto.class);

        Marshaller listmarshaller = jaxbListContext.createMarshaller();
        listmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);


        AddressDto mainAddress = new AddressDto(1234545, "Country", "City");
        AddressesDto addressesDto = new AddressesDto(List.of(addressDto, addressDto), mainAddress);
        listmarshaller.marshal(addressesDto, System.out);

    }
}
