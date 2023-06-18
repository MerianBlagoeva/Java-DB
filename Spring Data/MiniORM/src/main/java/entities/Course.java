package entities;

import orm.annoations.Column;
import orm.annoations.Entity;
import orm.annoations.Id;

@Entity(name = "courses")
public class Course {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "length_in_weeks")
    private int weekLength;

    public Course() {}
    public Course(String name, int weekLength) {
        this.name = name;
        this.weekLength = weekLength;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weekLength=" + weekLength +
                '}';
    }
}
