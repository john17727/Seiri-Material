package cs4330.cs.utep.edu.seirimaterial.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import cs4330.cs.utep.edu.seirimaterial.R;
import cs4330.cs.utep.edu.seirimaterial.data.Assignment;
import cs4330.cs.utep.edu.seirimaterial.models.AssignmentViewModel;

public class AssignmentDetails extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_TITLE = "TITLE";
    public static final String EXTRA_DUEDATE = "DUEDATE";
    public static final String EXTRA_DUETIME = "DUETIME";
    public static final String EXTRA_TYPE = "TYPE";
    public static final String EXTRA_COURSE = "COURSE";
    public static final String EXTRA_INFO = "INFO";
    public static final String EXTRA_COLOR = "COLOR";

    public static final String DATE_FORMAT = "EEE, MMM dd";
    private static final String TIME_FORMAT = "h:mm aa";

    private AssignmentViewModel assignmentViewModel;

    private List<String> courseNames;
    private List<Integer> courseColors;

    private ConstraintLayout background;

    private TextView assignmentTitle;
    private TextView assignmentDue;
    private TextView assignmentType;
    private TextView assignmentCourse;
    private TextView assignmentInfo;

    private int id;
    private String title;
    private long dueDate;
    private long dueTime;
    private String type;
    private String course;
    private String info;

    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        background = findViewById(R.id.assignments_activity);

        assignmentTitle = findViewById(R.id.title_detail);
        assignmentDue = findViewById(R.id.due_detail);
        assignmentType = findViewById(R.id.type_detail);
        assignmentCourse = findViewById(R.id.course_detail);
        assignmentInfo = findViewById(R.id.info_detail);

        assignmentViewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);

        Intent intent = getIntent();
        courseNames = intent.getStringArrayListExtra(AssignmentActivity.COURSE_NAMES);
        courseColors = intent.getIntegerArrayListExtra(AssignmentActivity.COURSE_COLORS);

        color = intent.getIntExtra(EXTRA_COLOR, -1);

        getWindow().setStatusBarColor(color);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        background.setBackgroundColor(color);

        setTexts(intent);
    }

    void setTexts(Intent intent) {
        setData(intent);
        assignmentTitle.setText(title);

        String due = "";
        if(dueDate != 0) {
            Date date = new Date(dueDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
            due += dateFormat.format(date);
        }
        if(dueTime != 0) {
            Date time = new Date(dueTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT, Locale.US);
            due += " at " + dateFormat.format(time);
        }
        assignmentDue.setText(due);
        assignmentType.setText(type);
        assignmentCourse.setText(course);
        assignmentInfo.setText(info);
    }

    void setData(Intent intent) {
        id = intent.getIntExtra(EXTRA_ID, -1);
        title = intent.getStringExtra(EXTRA_TITLE);
        dueDate = intent.getLongExtra(EXTRA_DUEDATE, -1);
        dueTime = intent.getLongExtra(EXTRA_DUETIME, -1);
        type = intent.getStringExtra(EXTRA_TYPE);
        course = intent.getStringExtra(EXTRA_COURSE);
        info = intent.getStringExtra(EXTRA_INFO);
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
                startEditActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startEditActivity() {

        Intent editAssignment = new Intent(AssignmentDetails.this, EditAssignment.class);
        editAssignment.putStringArrayListExtra(AssignmentActivity.COURSE_NAMES, (ArrayList<String>) courseNames);
        editAssignment.putIntegerArrayListExtra(AssignmentActivity.COURSE_COLORS, (ArrayList<Integer>) courseColors);
        editAssignment.putExtra(EXTRA_ID, id);
        editAssignment.putExtra(EXTRA_TITLE, title);
        editAssignment.putExtra(EXTRA_DUEDATE, dueDate);
        editAssignment.putExtra(EXTRA_DUETIME, dueTime);
        editAssignment.putExtra(EXTRA_TYPE, type);
        editAssignment.putExtra(EXTRA_COURSE, course);
        editAssignment.putExtra(EXTRA_INFO, info);
        editAssignment.putExtra(EXTRA_COLOR, color);

        startActivityForResult(editAssignment,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            color = Objects.requireNonNull(data).getIntExtra(EXTRA_COLOR, -1);
            getWindow().setStatusBarColor(color);
            Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(color));
            background.setBackgroundColor(color);

            setTexts(data);

            Assignment assignment = new Assignment(title, dueDate, dueTime, type, course, info, color);
            assignment.setId(id);
            assignmentViewModel.update(assignment);
        }
    }
}
