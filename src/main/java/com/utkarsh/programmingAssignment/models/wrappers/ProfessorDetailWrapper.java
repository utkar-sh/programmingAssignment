package com.utkarsh.programmingAssignment.models.wrappers;

public class ProfessorDetailWrapper {
    private long professorId;
    private String professorName;
    private String professorEmail;
    private String professorSchoolName;
    private String professorCourseTaught;

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorId(long professorId) {this.professorId = professorId;}

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getProfessorEmail() {
        return professorEmail;
    }

    public void setProfessorEmail(String professorEmail) {
        this.professorEmail = professorEmail;
    }

    public String getProfessorSchoolName() {
        return professorSchoolName;
    }

    public void setProfessorSchoolName(String professorSchoolName) {
        this.professorSchoolName = professorSchoolName;
    }

    public String getProfessorCourseTaught() {
        return professorCourseTaught;
    }

    public void setProfessorCourseTaught(String professorCourseTaught) {
        this.professorCourseTaught = professorCourseTaught;
    }

}