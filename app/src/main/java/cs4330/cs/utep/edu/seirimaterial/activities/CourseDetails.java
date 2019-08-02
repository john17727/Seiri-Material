package cs4330.cs.utep.edu.seirimaterial.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;

import cs4330.cs.utep.edu.seirimaterial.R;
import cs4330.cs.utep.edu.seirimaterial.data.Course;
import cs4330.cs.utep.edu.seirimaterial.models.CourseViewModel;

public class CourseDetails extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_NAME = "NAME";
    public static final String EXTRA_DAYS = "DAYS";
    public static final String EXTRA_TIME = "TIME";
    public static final String EXTRA_BUILDING = "BUILDING";
    public static final String EXTRA_ROOM = "ROOM";
    public static final String EXTRA_PNAME = "PNAME";
    public static final String EXTRA_EMAIL = "EMAIL";
    public static final String EXTRA_PDAYS = "PDAYS";
    public static final String EXTRA_PTIME = "PTIME";
    public static final String EXTRA_PBUILDING = "PBUILDING";
    public static final String EXTRA_PROOM = "PROOM";
    public static final String EXTRA_COLOR = "COLOR";

    private CourseViewModel courseViewModel;

    private TextView courseName;
    private TextView courseDays;
    private TextView courseTime;
    private TextView courseBuilding;
    private TextView courseRoom;

    private TextView profName;
    private TextView profEmail;
    private TextView profDays;
    private TextView profTime;
    private TextView profBuilding;
    private TextView profRoom;


    private int id;
    private String courseNameS;
    private String courseDaysS;
    private String courseTimeS;
    private String courseBuildingS;
    private String courseRoomS;

    private String profNameS;
    private String profEmailS;
    private String profDaysS;
    private String profTimeS;
    private String profBuildingS;
    private String profRoomS;

    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        courseName = findViewById(R.id.name_detail);
        courseDays = findViewById(R.id.days_detail);
        courseTime = findViewById(R.id.time_detail);
        courseBuilding = findViewById(R.id.building_detail);
        courseRoom = findViewById(R.id.room_detail);

        profName = findViewById(R.id.prof_name_detail);
        profEmail = findViewById(R.id.email_detail);
        profDays = findViewById(R.id.prof_days_detail);
        profTime = findViewById(R.id.prof_time_detail);
        profBuilding = findViewById(R.id.prof_building_detail);
        profRoom = findViewById(R.id.prof_room_detail);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        Intent intent = getIntent();

        color = intent.getIntExtra(EXTRA_COLOR, -1);

        getWindow().setStatusBarColor(color);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        courseName.setBackgroundColor(color);

        setTexts(intent);
    }

    public void setTexts(Intent intent) {
        setData(intent);
        courseName.setText(courseNameS);
        courseDays.setText(courseDaysS);
        courseTime.setText(courseTimeS);
        courseBuilding.setText(courseBuildingS);
        courseRoom.setText(courseRoomS);

        profName.setText(profNameS);
        profEmail.setText(profEmailS);
        profDays.setText(profDaysS);
        profTime.setText(profTimeS);
        profBuilding.setText(profBuildingS);
        profRoom.setText(profRoomS);
    }

    public void setData(Intent intent) {
        id = intent.getIntExtra(EXTRA_ID, -1);
        courseNameS = intent.getStringExtra(EXTRA_NAME);
        courseDaysS = intent.getStringExtra(EXTRA_DAYS);
        courseTimeS = intent.getStringExtra(EXTRA_TIME);
        courseBuildingS = intent.getStringExtra(EXTRA_BUILDING);
        courseRoomS = intent.getStringExtra(EXTRA_ROOM);

        profNameS = intent.getStringExtra(EXTRA_PNAME);
        profEmailS = intent.getStringExtra(EXTRA_EMAIL);
        profDaysS = intent.getStringExtra(EXTRA_PDAYS);
        profTimeS = intent.getStringExtra(EXTRA_PTIME);
        profBuildingS = intent.getStringExtra(EXTRA_PBUILDING);
        profRoomS = intent.getStringExtra(EXTRA_PROOM);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                break;
            case R.id.edit:
                openEditActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openEditActivity() {

        Intent editCourse = new Intent(CourseDetails.this, EditCourse.class);
        editCourse.putExtra(EXTRA_ID, id);
        editCourse.putExtra(EXTRA_NAME, courseNameS);
        editCourse.putExtra(EXTRA_DAYS, courseDaysS);
        editCourse.putExtra(EXTRA_TIME, courseTimeS);
        editCourse.putExtra(EXTRA_BUILDING, courseBuildingS);
        editCourse.putExtra(EXTRA_ROOM, courseRoomS);
        editCourse.putExtra(EXTRA_PNAME, profNameS);
        editCourse.putExtra(EXTRA_EMAIL, profEmailS);
        editCourse.putExtra(EXTRA_PDAYS, profDaysS);
        editCourse.putExtra(EXTRA_PTIME, profTimeS);
        editCourse.putExtra(EXTRA_PBUILDING, profBuildingS);
        editCourse.putExtra(EXTRA_PROOM, profRoomS);
        editCourse.putExtra(EXTRA_COLOR, color);

        startActivityForResult(editCourse, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            color = Objects.requireNonNull(data).getIntExtra(EXTRA_COLOR, -1);
            getWindow().setStatusBarColor(color);
            Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(color));
            courseName.setBackgroundColor(color);

            setTexts(data);

            String times = data.getStringExtra(EXTRA_TIME);
            String[] timesArray = new String[2];
            if(times.isEmpty()) {
                timesArray[0] = "";
                timesArray[1] = "";
            } else {
                timesArray = times.split("-");
            }


            times = data.getStringExtra(EXTRA_PTIME);
            String[] timesArrayP = new String[2];
            if(times.isEmpty()) {
                timesArrayP[0] = "";
                timesArrayP[1] = "";
            } else {
                timesArrayP = times.split("-");
            }

            Course course = new Course(courseNameS, courseBuildingS, courseRoomS, courseDaysS, timesArray[0], timesArray[1], profNameS, profEmailS, profBuildingS, profRoomS, profDaysS, timesArrayP[0], timesArrayP[1], color);
            course.setId(id);
            courseViewModel.update(course);
        }
    }
}
