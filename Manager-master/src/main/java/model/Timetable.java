package model;

public class Timetable {
    private int timetableId;
    private int classId;
    private String week;
    private String first="无课程";
    private String second="无课程";
    private String third="无课程";
    private String fourth="无课程";
    private String fifth="无课程";
    private String sixth="无课程";
    private String seventh="无课程";
    private String eighth="无课程";
    private String ninth="无课程";
    private String tenth="无课程";
    private String eleventh="无课程";
    private String twelfth="无课程";
    private String thirteenth="无课程";

    public int getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(int timetableId) {
        this.timetableId = timetableId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
        if (first .equals("") ) {
            this.first = "无课程";
        }
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
        if (second .equals("") ) {
            this.second = "无课程";
        }
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;  if (third .equals("") ) {
            this.third = "无课程";
        }
    }

    public String getFourth() {
        return fourth;
    }

    public void setFourth(String fourth) {
        this.fourth = fourth;
        if (fourth .equals("") ) {
            this.fourth = "无课程";
        }
    }

    public String getFifth() {
        return fifth;
    }

    public void setFifth(String fifth) {
        this.fifth = fifth;
        if (fifth.equals("") ) {
            this.fifth = "无课程";
        }
    }

    public String getSixth() {
        return sixth;

    }

    public void setSixth(String sixth) {
        this.sixth = sixth;
        if (sixth .equals("") ) {
            this.sixth = "无课程";
        }
    }

    public String getSeventh() {
        return seventh;
    }

    public void setSeventh(String seventh) {
        this.seventh = seventh;if (seventh .equals("") ) {
            this.seventh = "无课程";
        }
    }

    public String getEighth() {
        return eighth;
    }

    public void setEighth(String eighth) {
        this.eighth = eighth;if (eighth .equals("") ) {
            this.eighth = "无课程";
        }
    }

    public String getNinth() {
        return ninth;

    }

    public void setNinth(String ninth) {
        this.ninth = ninth;
        if (ninth .equals("") ) {
            this.ninth = "无课程";
        }

    }

    public String getTenth() {
        return tenth;
    }

    public void setTenth(String tenth) {
        this.tenth = tenth;
        if (tenth .equals("") ) {
            this.tenth = "无课程";
        }
    }

    public String getEleventh() {
        return eleventh;
    }

    public void setEleventh(String eleventh) {
        this.eleventh = eleventh;
        if (eleventh .equals("") ) {
            this.eleventh = "无课程";
        }
    }

    public String getTwelfth() {
        return twelfth;
    }

    public void setTwelfth(String twelfth) {
        this.twelfth = twelfth;
        if (twelfth .equals("") ) {
            this.twelfth = "无课程";
        }
    }

    public String getThirteenth() {
        return thirteenth;
    }

    public void setThirteenth(String thirteenth) {
        this.thirteenth = thirteenth;
        if (thirteenth .equals("") ) {
            this.thirteenth = "无课程";
        }
    }


}
