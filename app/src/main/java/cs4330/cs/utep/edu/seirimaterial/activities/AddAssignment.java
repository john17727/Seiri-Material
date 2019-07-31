package cs4330.cs.utep.edu.seirimaterial.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cs4330.cs.utep.edu.seirimaterial.R;
import cs4330.cs.utep.edu.seirimaterial.models.AssignmentViewModel;

public class AddAssignment extends AppCompatActivity {


    private static final String DATE_FORMAT = "MM/dd/yy";
    public static final String TIME_FORMAT = "h:mm aa";

    private AssignmentViewModel assignmentViewModel;

    private Calendar calendar;
    private TimePickerDialog.OnTimeSetListener timePicker;
    private DatePickerDialog.OnDateSetListener datePicker;
    private Date time;
    private String assignmentTime;
    private Date date;
    private long assignmentDate;

    private TextInputEditText editTextTitle;
    private TextInputEditText editTextDueDate;
    private TextInputEditText editTextDueTime;
    private TextInputEditText editTextType;
    private TextInputEditText editTextCourse;
    private TextInputEditText editTextAddiInfo;

    private MaterialButton addAssignmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        addAssignmentButton = findViewById(R.id.add_assignment_button);

        datePicker = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

            date = calendar.getTime();
            assignmentDate = date.getTime();

            editTextDueDate.setText(dateFormat.format(date));
        };


        timePicker = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);

            time = calendar.getTime();
            assignmentTime = timeFormat.format(time);
            editTextDueTime.setText(assignmentTime);
        };
    }

    public void addAssignmentClick(View view) {
        String title = editTextTitle.getText().toString();
    }

    public void dateClick(View view) {
        new DatePickerDialog(view.getContext(), datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void timeClick(View view) {
        new TimePickerDialog(view.getContext(), timePicker, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    public void typeClick(View view) {
        new MaterialAlertDialogBuilder(view.getContext())
                .setTitle("Type")
                .setMessage("Message")
                .setPositiveButton("Ok", null)
                .show();
    }

    public void courseClick(View view) {

    }

}
