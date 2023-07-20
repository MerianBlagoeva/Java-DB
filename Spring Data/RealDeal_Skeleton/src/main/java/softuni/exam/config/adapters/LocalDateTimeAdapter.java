package softuni.exam.config.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    @Override
    public LocalDateTime unmarshal(String s) throws Exception {
        return LocalDateTime
                .parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm ss"));
    }

    @Override
    public String marshal(LocalDateTime localDate) throws Exception {
        return localDate.toString();
    }
}
