package cs4330.cs.utep.edu.seirimaterial;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "course_table")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String building;
    private String room;
    private String days;
    private String startTime;
    private String endTime;
    private String profName;
    private String email;
    private String profBuilding;
    private String profRoom;
    private String profDays;
    private String profStartTime;
    private String profEndTime;
    private int color;

    public Course(String name, String building, String room, String days, String startTime, String endTime, String profName, String email, String profBuilding, String profRoom, String profDays, String profStartTime, String profEndTime, int color) {
        this.name = name;
        this.building = building;
        this.room = room;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.profName = profName;
        this.email = email;
        this.profBuilding = profBuilding;
        this.profRoom = profRoom;
        this.profDays = profDays;
        this.profStartTime = profStartTime;
        this.profEndTime = profEndTime;
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBuilding() {
        return building;
    }

    public String getRoom() {
        return room;
    }

    public String getDays() {
        return days;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getProfName() {
        return profName;
    }

    public String getEmail() {
        return email;
    }

    public String getProfBuilding() {
        return profBuilding;
    }

    public String getProfRoom() {
        return profRoom;
    }

    public String getProfDays() {
        return profDays;
    }

    public String getProfStartTime() {
        return profStartTime;
    }

    public String getProfEndTime() {
        return profEndTime;
    }

    public int getColor() {
        return color;
    }
}
