package cs4330.cs.utep.edu.seirimaterial.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import cs4330.cs.utep.edu.seirimaterial.R;
import cs4330.cs.utep.edu.seirimaterial.data.Assignment;
import cs4330.cs.utep.edu.seirimaterial.models.AssignmentViewModel;

public class EditAssignment extends AppCompatActivity {

    private static final String DATE_FORMAT = "MM/dd/yy";
    private static final String TIME_FORMAT = "h:mm aa";

    SimpleDateFormat dateFormat;
    SimpleDateFormat timeFormat;

    private AssignmentViewModel assignmentViewModel;

    private List<String> courseNames;
    private List<Integer> courseColors;

    private Calendar calendar;
    private TimePickerDialog.OnTimeSetListener timePicker;
    private DatePickerDialog.OnDateSetListener datePicker;
    private Date time;
    private long assignmentTime;
    private Date date;
    private long assignmentDate;
    private int color = -14606047;

    private TextInputEditText editTextTitle;
    private TextInputEditText editTextDueDate;
    private TextInputEditText editTextDueTime;
    private TextInputEditText editTextType;
    private TextInputEditText editTextCourse;
    private TextInputEditText editTextAddiInfo;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        calendar = Calendar.getInstance();

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDueDate = findViewById(R.id.edit_text_due_date);
        editTextDueDate.setKeyListener(null);
        editTextDueTime = findViewById(R.id.edit_text_due_time);
        editTextDueTime.setKeyListener(null);
        editTextType = findViewById(R.id.edit_text_type);
        editTextType.setKeyListener(null);
        editTextCourse = findViewById(R.id.edit_text_course);
        editTextCourse.setKeyListener(null);
        editTextAddiInfo = findViewById(R.id.edit_text_addi_info);

        setData(getIntent());

        assignmentViewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);

        datePicker = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);

            date = calendar.getTime();
            assignmentDate = date.getTime();

            editTextDueDate.setText(dateFormat.format(date));
        };


        timePicker = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            timeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.US);

            time = calendar.getTime();
            assignmentTime = time.getTime();

            editTextDueTime.setText(timeFormat.format(time));
        };
    }

    public void setData(Intent intent) {
        dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        timeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.US);

        courseNames = intent.getStringArrayListExtra(AssignmentActivity.COURSE_NAMES);
        courseColors = intent.getIntegerArrayListExtra(AssignmentActivity.COURSE_COLORS);

        id = intent.getIntExtra(AssignmentDetails.EXTRA_ID, -1);

        editTextTitle.setText(intent.getStringExtra(AssignmentDetails.EXTRA_TITLE));

        assignmentDate = intent.getIntExtra(AssignmentDetails.EXTRA_DUEDATE, -1);
        if(!(assignmentDate <= 0)) {
            editTextDueDate.setText(dateFormat.format(assignmentDate));
        }

        assignmentTime = intent.getLongExtra(AssignmentDetails.EXTRA_DUETIME, -1);
        if(!(assignmentTime <= 0)) {
            editTextDueTime.setText(timeFormat.format(assignmentTime));
        }

        editTextType.setText(intent.getStringExtra(AssignmentDetails.EXTRA_TYPE));
        editTextCourse.setText(intent.getStringExtra(AssignmentDetails.EXTRA_COURSE));
        editTextAddiInfo.setText(intent.getStringExtra(AssignmentDetails.EXTRA_INFO));

        color = intent.getIntExtra(AssignmentDetails.EXTRA_COLOR, -1);

    }

    public void updateAssignmentClick(View view) {

        String title = Objects.requireNonNull(editTextTitle.getText()).toString();
        String type = Objects.requireNonNull(editTextType.getText()).toString();
        String course = Objects.requireNonNull(editTextCourse.getText()).toString();
        String extraInfo = Objects.requireNonNull(editTextAddiInfo.getText()).toString();

        if(Objects.requireNonNull(editTextDueDate.getText()).toString().isEmpty()) {
            assignmentDate = 0;
        }
        if(Objects.requireNonNull(editTextDueTime.getText()).toString().isEmpty()) {
            assignmentTime = 0;
        }


        Intent back = new Intent();
        back.putExtra(AssignmentDetails.EXTRA_ID, id);
        back.putExtra(AssignmentDetails.EXTRA_TITLE, title);
        back.putExtra(AssignmentDetails.EXTRA_DUEDATE, assignmentDate);
        back.putExtra(AssignmentDetails.EXTRA_DUETIME, assignmentTime);
        back.putExtra(AssignmentDetails.EXTRA_TYPE, type);
        back.putExtra(AssignmentDetails.EXTRA_COURSE, course);
        back.putExtra(AssignmentDetails.EXTRA_INFO, extraInfo);
        back.putExtra(AssignmentDetails.EXTRA_COLOR, color);
        setResult(RESULT_OK, back);
        finish();

    }

    public void dateClick(View view) {
        new DatePickerDialog(view.getContext(), datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void timeClick(View view) {
        new TimePickerDialog(view.getContext(), timePicker, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    public void typeClick(View view) {
        final String[] types = getResources().getStringArray(R.array.assignment_types);

        new MaterialAlertDialogBuilder(view.getContext())
                .setTitle("Type")
                .setItems(types, (dialog, which) -> editTextType.setText(types[which]))
                .show();
    }

    public void courseClick(View view) {
        String[] names = new String[courseNames.size() + 1];
        courseNames.add(0, "None");
        courseNames.toArray(names);
        new MaterialAlertDialogBuilder(view.getContext())
                .setTitle("Course")
                .setItems(names, (dialog, which) -> {
                    editTextCourse.setText(names[which]);
                    if(which != 0) {
                        color = courseColors.get(which - 1);
                    } else {
                        color = -14606047;
                    }
                })
                .show();
    }
}
