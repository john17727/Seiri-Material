package cs4330.cs.utep.edu.seirimaterial.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import cs4330.cs.utep.edu.seirimaterial.R;
import cs4330.cs.utep.edu.seirimaterial.models.AssignmentViewModel;

public class AssignmentDetails extends AppCompatActivity {

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

        assignmentTitle = findViewById(R.id.title_detail);
        assignmentDue = findViewById(R.id.due_detail);
        assignmentType = findViewById(R.id.type_detail);
        assignmentCourse = findViewById(R.id.course_detail);
        assignmentInfo = findViewById(R.id.info_detail);

        assignmentViewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);

        Intent intent = getIntent();

        color = intent.getIntExtra(EXTRA_COLOR, -1);

        getWindow().setStatusBarColor(color);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        assignmentTitle.setBackgroundColor(color);

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
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
