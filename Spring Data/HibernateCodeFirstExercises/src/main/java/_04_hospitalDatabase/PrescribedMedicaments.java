package _04_hospitalDatabase;

import utils.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "prescribed_medicaments")
public class PrescribedMedicaments extends BaseEntity {
    private String name;

    public PrescribedMedicaments() {
    }

    public PrescribedMedicaments(String medicaments) {
        this.name = medicaments;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
