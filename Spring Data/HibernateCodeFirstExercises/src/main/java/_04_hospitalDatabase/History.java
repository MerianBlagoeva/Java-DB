package _04_hospitalDatabase;

import utils.BaseEntity;

public class History extends BaseEntity {
    private Visitation visitation;
    private Diagnose diagnose;
    private PrescribedMedicaments prescribedMedicaments;

    public History() {
    }

    public History(Visitation visitation, Diagnose diagnose, PrescribedMedicaments prescribedMedicaments) {
        this.visitation = visitation;
        this.diagnose = diagnose;
        this.prescribedMedicaments = prescribedMedicaments;
    }

    public Visitation getVisitation() {
        return visitation;
    }

    public void setVisitation(Visitation visitation) {
        this.visitation = visitation;
    }

    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }

    public PrescribedMedicaments getPrescribedMedicaments() {
        return prescribedMedicaments;
    }

    public void setPrescribedMedicaments(PrescribedMedicaments prescribedMedicaments) {
        this.prescribedMedicaments = prescribedMedicaments;
    }
}
