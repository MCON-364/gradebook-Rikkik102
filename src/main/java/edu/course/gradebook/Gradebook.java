package edu.course.gradebook;

import java.util.*;

public class Gradebook {

    private final Map<String, List<Integer>> gradesByStudent = new HashMap<>();
    private final Deque<UndoAction> undoStack = new ArrayDeque<>();
    private final LinkedList<String> activityLog = new LinkedList<>();

    public Optional<List<Integer>> findStudentGrades(String name) {
        return Optional.ofNullable(gradesByStudent.get(name));
    }

    public boolean addStudent(String name) {
        if (gradesByStudent.containsKey(name)) {
            return false;
        }
        else {
            gradesByStudent.put(name, new ArrayList<Integer>());
            activityLog.add("Added student " + name);
            return true;
        }
    }


    public Map<String, List<Integer>> getGradesByStudent() {
        return gradesByStudent;
    }
    public LinkedList<String> getActivityLog() {
        return activityLog;
    }

    public boolean addGrade(String name, int grade) {
        if (!(gradesByStudent.containsKey(name))) {
            return false;
        }
        else {
            gradesByStudent.get(name).add(grade);
            activityLog.add("Added grade " + grade + " to student " + name);
            undoStack.push(Gradebook -> {
                gradesByStudent.get(name).removeLast();
            });
            return true;
        }
    }

    public boolean removeStudent(String name) {
        if  (!(gradesByStudent.containsKey(name))) {
            return false;
        }
        else {
            List<Integer> removedGrades = gradesByStudent.get(name);
            gradesByStudent.remove(name);
            activityLog.add("Removed student " + name);
            undoStack.push(Gradebook -> {
                gradesByStudent.put(name, removedGrades);
            });
            return true;
        }
    }

    public Optional<Double> averageFor(String name) {
        throw new UnsupportedOperationException();
    }

    public Optional<String> letterGradeFor(String name) {
        throw new UnsupportedOperationException();
    }

    public Optional<Double> classAverage() {
        throw new UnsupportedOperationException();
    }

    public boolean undo() {
        throw new UnsupportedOperationException();
    }

    public List<String> recentLog(int maxItems) {
        throw new UnsupportedOperationException();
    }
}
