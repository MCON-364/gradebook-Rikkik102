import edu.course.gradebook.Gradebook;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GradebookTests {

    @Test
    void testAddStudent() {
    Gradebook gb = new Gradebook();
    gb.addStudent("Harvey");
    assertEquals(1, gb.getGradesByStudent().size());
    assertTrue(gb.getGradesByStudent().containsKey("Harvey"));
    assertEquals(0, gb.getGradesByStudent().get("Harvey").size());
    assertTrue(gb.getActivityLog().contains("Added student Harvey"));
    }

    @Test
    void testAddGrade() {
        Gradebook gb = new Gradebook();
        gb.addStudent("Sharon");
        gb.addGrade("Sharon", 98);
        assertEquals(1, gb.getGradesByStudent().get("Sharon").size());
        assertTrue(gb.getActivityLog().contains("Added grade 98 to student Sharon"));
    }

    @Test
    void testRemoveStudent() {
        Gradebook gb = new Gradebook();
        gb.addStudent("Violet");
        gb.removeStudent("Violet");
        assertEquals(0, gb.getGradesByStudent().size());
        assertTrue(gb.getActivityLog().contains("Removed student Violet"));
    }

    @Test
    void testAverageFor() {
        Gradebook gb = new Gradebook();
        gb.addStudent("Veronica");
        gb.addGrade("Veronica", 98);
        gb.addGrade("Veronica", 100);
        assertEquals(Optional.of(99.0), gb.averageFor("Veronica"));
    }

    @Test
    void testLetterGradeFor() {
        Gradebook gb = new Gradebook();
        gb.addStudent("Julia");
        gb.addGrade("Julia", 98);
        gb.addGrade("Julia", 100);
        assertEquals(Optional.of("A"), gb.letterGradeFor("Julia"));
    }

    @Test
    void testClassAverage() {
        Gradebook gb = new Gradebook();
        gb.addStudent("Harvey");
        gb.addStudent("Violet");
        gb.addStudent("Veronica");
        gb.addGrade("Harvey", 70);
        gb.addGrade("Violet", 90);
        assertEquals(Optional.of(80.0), gb.classAverage());
    }

    @Test
    void testUndo() {
        Gradebook gb = new Gradebook();
        gb.addStudent("Victoria");
        gb.removeStudent("Victoria");
        gb.undo();
        assertEquals(1, gb.getGradesByStudent().size());
    }
    @Test
    void testRecentLog() {
        Gradebook gb = new Gradebook();
        gb.addStudent("Harvey");
        gb.addStudent("Violet");
        gb.addStudent("Veronica");
        List<String> log = gb.recentLog(2);
        assertEquals(2, log.size());
        assert(log.contains("Added student Violet"));
        assert(log.contains("Added student Veronica"));

    }

}
