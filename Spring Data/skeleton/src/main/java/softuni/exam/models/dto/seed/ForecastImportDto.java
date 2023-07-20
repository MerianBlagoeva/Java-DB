package softuni.exam.models.dto.seed;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import softuni.exam.models.entity.DayOfWeek;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalTime;

@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastImportDto {

    @XmlElement(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @XmlElement(name = "max_temperature")
    @Min(-20)
    @Max(60)
    private double maxTemperature;

    @XmlElement(name = "min_temperature")
    @Min(-50)
    @Max(40)
    private double minTemperature;

    @XmlElement(name = "sunrise")
    private LocalTime sunrise;

    @XmlElement(name = "sunset")
    private LocalTime sunset;

    @XmlElement(name = "city")
    private long city;

    public ForecastImportDto() {
    }

    public ForecastImportDto(DayOfWeek dayOfWeek, double maxTemperature, double minTemperature, LocalTime sunrise, LocalTime sunset, long city) {
        this.dayOfWeek = dayOfWeek;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.city = city;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public LocalTime getSunrise() {
        return sunrise;
    }

    public void setSunrise(LocalTime sunrise) {
        this.sunrise = sunrise;
    }

    public LocalTime getSunset() {
        return sunset;
    }

    public void setSunset(LocalTime sunset) {
        this.sunset = sunset;
    }

    public long getCity() {
        return city;
    }

    public void setCity(long city) {
        this.city = city;
    }
}
