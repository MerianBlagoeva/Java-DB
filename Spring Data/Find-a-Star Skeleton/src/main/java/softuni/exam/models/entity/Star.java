package softuni.exam.models.entity;

import softuni.exam.models.entity.enums.StarType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "stars")
public class Star extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Column(name = "light_years", nullable = false)
    private double lightYears;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "star_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StarType starType;

    @ManyToOne
    private Constellation constellation;

    @OneToMany(mappedBy = "observingStar")
    private Set<Astronomer> astronomers;


    public Star() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLightYears() {
        return lightYears;
    }

    public void setLightYears(double lightYears) {
        this.lightYears = lightYears;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StarType getStarType() {
        return starType;
    }

    public void setStarType(StarType starType) {
        this.starType = starType;
    }

    public Constellation getConstellation() {
        return constellation;
    }

    public void setConstellation(Constellation constellation) {
        this.constellation = constellation;
    }
}
