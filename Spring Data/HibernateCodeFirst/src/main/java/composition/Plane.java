package composition;

import javax.persistence.*;

@Entity
@Table(name = "planes")
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column
    private String airLine;

    @Column(name = "passenger_capacity")
    private int passengerCapacity;

    @ManyToOne
    private Company company;
    public Plane() {}

    public Plane(String airLine, int passengerCapacity) {
        this.airLine = airLine;
        this.passengerCapacity = passengerCapacity;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
