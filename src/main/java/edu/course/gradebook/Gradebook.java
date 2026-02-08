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
        if (!(gradesByStudent.containsKey(name)) || gradesByStudent.get(name).isEmpty()) {
            return Optional.empty();
        }
        double total = 0;
        for (Integer grade: gradesByStudent.get(name)) {
           total += grade;
        }
        return Optional.of(total/gradesByStudent.get(name).size());
    }

    public Optional<String> letterGradeFor(String name) {
        Optional<Double> numberAverage = averageFor(name);
        if (numberAverage.isEmpty()) {
            return  Optional.empty();
        }
        else {
            var numberGrade = numberAverage.get();
            int firstNumber = (int)(numberGrade / 10);
            String letterGrade = switch(firstNumber) {
                case 10, 9 ->  { yield "A";}
                case 8 -> { yield "B"; }
                case 7 -> { yield "C"; }
                case 6 -> { yield "D"; }
                default -> { yield "F"; }
            };
            return Optional.of(letterGrade);
        }
    }

    public Optional<Double> classAverage() {
        if (gradesByStudent.isEmpty()) {
            return Optional.empty();
        } else {
            double total = 0;
            int numGrades = 0;
            for (String student : gradesByStudent.keySet()) {
                for (int grade : gradesByStudent.get(student)) {
                    total += grade;
                    numGrades ++;
                }
            }
            if (total == 0) {
                return Optional.empty();
            }
            return Optional.of(total/numGrades);
        }
    }

    public boolean undo() {
        if  (undoStack.isEmpty()) {
            return false;
        }
        UndoAction action = undoStack.pop();
        action.undo(this);
        activityLog.add("Undid action");
        return true;
    }

    public List<String> recentLog(int maxItems) {
        List<String> logs = new ArrayList<>();
        if (activityLog.isEmpty()){
            return logs;
        }
        int start = Math.max(0, activityLog.size()- maxItems);
        return activityLog.subList(start, activityLog.size());
    }
}
