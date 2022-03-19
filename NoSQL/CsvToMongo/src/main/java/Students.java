import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Students implements Serializable {

    private String name;
    private int age;
    private List<String> courses;

    public Students(String name, int age, List<String> courses) {
        this.name = name;
        this.age = age;
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "{" +
                "name = " + name +
                ", age = " + age +
                ", courses = " + courses +
                '}';
    }
}
