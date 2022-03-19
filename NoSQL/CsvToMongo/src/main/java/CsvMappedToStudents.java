import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class CsvMappedToStudents {

    private static List<String> lines;

    static {
        try {
            lines = Files.readAllLines(Paths.get(Main.PATH_TO_CSV));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Students> parsCsv() {
        List<Students> studentsList = new ArrayList<>();

        for (String line : lines) {
            String[] fragments = line.split("[\"]{2,}");
            if (fragments == null || fragments.length != 2) {
                throw new RuntimeException("File parsing error!!");
            }
            List<String> coursesList = addCoursesList(fragments);
            String[] studentData = fragments[0].split(",");
            if (studentData == null || studentData.length != 2) {
                throw new RuntimeException("File parsing error!!");
            }
            String studentName = getStudentName(studentData).replaceAll("\"", "");
            int studentAge = getStudentAge(studentData);
            studentsList.add(new Students(studentName, studentAge, coursesList));
        }
        return studentsList;
    }

    private int getStudentAge(String[] studentData) {
        return Integer.parseInt(studentData[1]);
    }

    private String getStudentName(String[] studentData) {
        return studentData[0];
    }

    private List<String> addCoursesList(String[] fragment) {
        String[] coursesFragment = fragment[1].split(",");
        return Arrays.stream(coursesFragment).collect(Collectors.toList());
    }

}
