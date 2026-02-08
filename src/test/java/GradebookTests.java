import edu.course.gradebook.Gradebook;
import org.junit.jupiter.api.Test;

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
}
