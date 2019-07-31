package cs4330.cs.utep.edu.seirimaterial.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "assignment_table")
public class Assignment {

    @PrimaryKey
    private int id;

    private String title;
    private String dueDate;
    private String dueTime;
    private String type;
    private String course;
    private String addInfo;
    private int color;

    public Assignment(String title, String dueDate, String dueTime, String type, String course, String addInfo, int color) {
        this.title = title;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.type = type;
        this.course = course;
        this.addInfo = addInfo;
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public String getType() {
        return type;
    }

    public String getCourse() {
        return course;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public int getColor() {
        return color;
    }
}
