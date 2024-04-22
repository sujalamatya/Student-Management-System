
package com.sea.dto;

public class TeacherDTO {

    public int getT_id() {
        return T_id;
    }

    public void setT_id(int T_id) {
        this.T_id = T_id;
    }

    public String getTname() {
        return Tname;
    }

    public void setTname(String Tname) {
        this.Tname = Tname;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    private int T_id;
    private String Tname;
    private String course;
}
