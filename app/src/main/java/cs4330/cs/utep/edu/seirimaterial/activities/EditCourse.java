package cs4330.cs.utep.edu.seirimaterial.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import cs4330.cs.utep.edu.seirimaterial.R;
import petrov.kristiyan.colorpicker.ColorPicker;

public class EditCourse extends AppCompatActivity implements View.OnClickListener {

    public static final String TIME_FORMAT = "h:mm aa";

    private Calendar calendar;
    private TimePickerDialog.OnTimeSetListener startTimePicker;
    private TimePickerDialog.OnTimeSetListener endTimePicker;
    private TimePickerDialog.OnTimeSetListener profStartTimePicker;
    private TimePickerDialog.OnTimeSetListener profEndTimePicker;
    private Date startTime;
    private Date endTime;
    private String courseStartTime;
    private String courseEndTime;
    private String profStartTime;
    private String profEndTime;

    private MaterialButton selectColorButton;
    private int cardColor;

    private String[] courseDays;

    private TextInputEditText editTextName;
    private TextInputEditText editTextBuilding;
    private TextInputEditText editTextRoom;
    private TextInputEditText editTextStart;
    private TextInputEditText editTextEnd;

    private CheckBox courseSun, courseMon, courseTue, courseWed, courseThu, courseFri, courseSat;

    private String[] profDays;

    private TextInputEditText editTextProfName;
    private TextInputEditText editTextProfEmail;
    private TextInputEditText editTextProfBuilding;
    private TextInputEditText editTextProfRoom;
    private TextInputEditText editTextProfStart;
    private TextInputEditText editTextProfEnd;

    private CheckBox profSun, profMon, profTue, profWed, profThu, profFri, profSat;

    MaterialButton saveChangesButton;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setDayArrays();

        calendar = Calendar.getInstance();

        selectColorButton = findViewById(R.id.button_color);
        cardColor = Color.parseColor("#212121");

        editTextName = findViewById(R.id.edit_text_name);
        editTextBuilding = findViewById(R.id.edit_text_building);
        editTextRoom = findViewById(R.id.edit_text_room);

        courseSun = findViewById(R.id.cb_course_sun);
        courseSun.setOnClickListener(this);
        courseMon = findViewById(R.id.cb_course_mon);
        courseMon.setOnClickListener(this);
        courseTue = findViewById(R.id.cb_course_tue);
        courseTue.setOnClickListener(this);
        courseWed = findViewById(R.id.cb_course_wed);
        courseWed.setOnClickListener(this);
        courseThu = findViewById(R.id.cb_course_thu);
        courseThu.setOnClickListener(this);
        courseFri = findViewById(R.id.cb_course_fri);
        courseFri.setOnClickListener(this);
        courseSat = findViewById(R.id.cb_course_sat);
        courseSat.setOnClickListener(this);

        editTextStart = findViewById(R.id.edit_text_course_start);
        editTextStart.setKeyListener(null);
        editTextEnd = findViewById(R.id.edit_text_course_end);
        editTextEnd.setKeyListener(null);

        editTextProfName = findViewById(R.id.edit_text_prof_name);
        editTextProfEmail = findViewById(R.id.edit_text_prof_email);
        editTextProfBuilding = findViewById(R.id.edit_text_prof_building);
        editTextProfRoom = findViewById(R.id.edit_text_prof_room);

        profSun = findViewById(R.id.cb_prof_sun);
        profSun.setOnClickListener(this);
        profMon = findViewById(R.id.cb_prof_mon);
        profMon.setOnClickListener(this);
        profTue = findViewById(R.id.cb_prof_tue);
        profTue.setOnClickListener(this);
        profWed = findViewById(R.id.cb_prof_wed);
        profWed.setOnClickListener(this);
        profThu = findViewById(R.id.cb_prof_thu);
        profThu.setOnClickListener(this);
        profFri = findViewById(R.id.cb_prof_fri);
        profFri.setOnClickListener(this);
        profSat = findViewById(R.id.cb_prof_sat);
        profSat.setOnClickListener(this);

        editTextProfStart = findViewById(R.id.edit_text_prof_start);
        editTextProfStart.setKeyListener(null);
        editTextProfEnd = findViewById(R.id.edit_text_prof_end);
        editTextProfEnd.setKeyListener(null);

        saveChangesButton = findViewById(R.id.save_changes_button);

        Intent intent = getIntent();
        setData(intent);

        startTimePicker = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.US);

            startTime = calendar.getTime();

            courseStartTime = timeFormat.format(startTime);
            editTextStart.setText(courseStartTime);
        };

        endTimePicker = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.US);

            endTime = calendar.getTime();

            courseEndTime = timeFormat.format(endTime);
            editTextEnd.setText(courseEndTime);
        };

        profStartTimePicker = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.US);

            startTime = calendar.getTime();

            profStartTime = timeFormat.format(startTime);
            editTextProfStart.setText(profStartTime);
        };

        profEndTimePicker = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.US);

            endTime = calendar.getTime();

            profEndTime = timeFormat.format(endTime);
            editTextProfEnd.setText(profEndTime);
        };
    }

    public void setData(Intent intent) {
        id = intent.getIntExtra(CourseDetails.EXTRA_ID, -1);
        cardColor = intent.getIntExtra(CourseDetails.EXTRA_COLOR, -1);
        selectColorButton.setTextColor(Color.parseColor("#fafafa"));
        selectColorButton.setBackgroundColor(cardColor);

        editTextName.setText(intent.getStringExtra(CourseDetails.EXTRA_NAME));
        editTextBuilding.setText(intent.getStringExtra(CourseDetails.EXTRA_BUILDING));
        editTextRoom.setText(intent.getStringExtra(CourseDetails.EXTRA_ROOM));
        String days = intent.getStringExtra(CourseDetails.EXTRA_DAYS);
        setCourseDays(days);
        String times = intent.getStringExtra(CourseDetails.EXTRA_TIME);
        String[] timesArray = new String[2];
        if(times.isEmpty()) {
            timesArray[0] = "";
            timesArray[1] = "";
        } else {
            timesArray = times.split("-");
        }
        editTextStart.setText(timesArray[0]);
        courseStartTime = timesArray[0];
        editTextEnd.setText(timesArray[1]);
        courseEndTime = timesArray[1];

        editTextProfName.setText(intent.getStringExtra(CourseDetails.EXTRA_PNAME));
        editTextProfEmail.setText(intent.getStringExtra(CourseDetails.EXTRA_EMAIL));
        editTextProfBuilding.setText(intent.getStringExtra(CourseDetails.EXTRA_PBUILDING));
        editTextProfRoom.setText(intent.getStringExtra(CourseDetails.EXTRA_PROOM));
        days = intent.getStringExtra(CourseDetails.EXTRA_PDAYS);
        setProfDays(days);
        times = intent.getStringExtra(CourseDetails.EXTRA_PTIME);
        if(times.isEmpty()) {
            timesArray[0] = "";
            timesArray[1] = "";
        } else {
            timesArray = times.split("-");
        }
        editTextProfStart.setText(timesArray[0]);
        profStartTime = timesArray[0];
        editTextProfEnd.setText(timesArray[1]);
        profEndTime = timesArray[1];

        setColor(cardColor);
    }

    public void setCourseDays(String days) {
        if(days.contains("Sun")) {
            courseSun.toggle();
            courseDays[0] = "Sun";
        }
        if(days.contains("Mon")) {
            courseMon.toggle();
            courseDays[1] = "Mon";
        }
        if(days.contains("Tue")) {
            courseTue.toggle();
            courseDays[2] = "Tue";
        }
        if(days.contains("Wed")) {
            courseWed.toggle();
            courseDays[3] = "Wed";
        }
        if(days.contains("Thu")) {
            courseThu.toggle();
            courseDays[4] = "Thu";
        }
        if(days.contains("Fri")) {
            courseFri.toggle();
            courseDays[5] = "Fri";
        }
        if(days.contains("Sat")) {
            courseSat.toggle();
            courseDays[6] = "Sat";
        }
    }

    public void setProfDays(String days) {
        if(days.contains("Sun")) {
            profSun.toggle();
            profDays[0] = "Sun";
        }
        if(days.contains("Mon")) {
            profMon.toggle();
            profDays[1] = "Mon";
        }
        if(days.contains("Tue")) {
            profTue.toggle();
            profDays[2] = "Tue";
        }
        if(days.contains("Wed")) {
            profWed.toggle();
            profDays[3] = "Wed";
        }
        if(days.contains("Thu")) {
            profThu.toggle();
            profDays[4] = "Thu";
        }
        if(days.contains("Fri")) {
            profFri.toggle();
            profDays[5] = "Fri";
        }
        if(days.contains("Sat")) {
            profSat.toggle();
            profDays[6] = "Sat";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_course_sun:
                if(courseSun.isChecked()) {
                    courseDays[0] = "Sun";
                } else {
                    courseDays[0] = "";
                }
                break;
            case R.id.cb_course_mon:
                if(courseMon.isChecked()) {
                    courseDays[1] = "Mon";
                } else {
                    courseDays[1] = "";
                }
                break;
            case R.id.cb_course_tue:
                if(courseTue.isChecked()) {
                    courseDays[2] = "Tue";
                } else {
                    courseDays[2] = "";
                }
                break;
            case R.id.cb_course_wed:
                if(courseWed.isChecked()) {
                    courseDays[3] = "Wed";
                } else {
                    courseDays[3] = "";
                }
                break;
            case R.id.cb_course_thu:
                if(courseThu.isChecked()) {
                    courseDays[4] = "Thu";
                } else {
                    courseDays[4] = "";
                }
                break;
            case R.id.cb_course_fri:
                if(courseFri.isChecked()) {
                    courseDays[5] = "Fri";
                } else {
                    courseDays[5] = "";
                }
                break;
            case R.id.cb_course_sat:
                if(courseSat.isChecked()) {
                    courseDays[6] = "Sat";
                } else {
                    courseDays[6] = "";
                }
                break;

            case R.id.cb_prof_sun:
                if(profSun.isChecked()) {
                    profDays[0] = "Sun";
                } else {
                    profDays[0] = "";
                }
                break;
            case R.id.cb_prof_mon:
                if(profMon.isChecked()) {
                    profDays[1] = "Mon";
                } else {
                    profDays[1] = "";
                }
                break;
            case R.id.cb_prof_tue:
                if(profTue.isChecked()) {
                    profDays[2] = "Tue";
                } else {
                    profDays[2] = "";
                }
                break;
            case R.id.cb_prof_wed:
                if(profWed.isChecked()) {
                    profDays[3] = "Wed";
                } else {
                    profDays[3] = "";
                }
                break;
            case R.id.cb_prof_thu:
                if(profThu.isChecked()) {
                    profDays[4] = "Thu";
                } else {
                    profDays[4] = "";
                }
                break;
            case R.id.cb_prof_fri:
                if(profFri.isChecked()) {
                    profDays[5] = "Fri";
                } else {
                    profDays[5] = "";
                }
                break;
            case R.id.cb_prof_sat:
                if(profSat.isChecked()) {
                    profDays[6] = "Sat";
                } else {
                    profDays[6] = "";
                }
                break;
        }
    }

    public void saveChanges(View view) {
        String name = Objects.requireNonNull(editTextName.getText()).toString();
        String building = Objects.requireNonNull(editTextBuilding.getText()).toString();
        String room = Objects.requireNonNull(editTextRoom.getText()).toString();
        String days = getChosenDays(courseDays);
        String profName = Objects.requireNonNull(editTextProfName.getText()).toString();
        String profEmail = Objects.requireNonNull(editTextProfEmail.getText()).toString();
        String profBuilding = Objects.requireNonNull(editTextProfBuilding.getText()).toString();
        String profRoom =  Objects.requireNonNull(editTextProfRoom.getText()).toString();
        String daysProf = getChosenDays(profDays);

        if(Objects.requireNonNull(editTextStart.getText()).toString().isEmpty()) {
            courseStartTime = "";
        }
        if(Objects.requireNonNull(editTextEnd.getText()).toString().isEmpty()) {
            courseEndTime = "";
        }
        if(Objects.requireNonNull(editTextProfStart.getText()).toString().isEmpty()) {
            profStartTime = "";
        }
        if(Objects.requireNonNull(editTextProfEnd.getText()).toString().isEmpty()) {
            profEndTime = "";
        }

        //Course course = new Course(name, building, room, days, courseStartTime, courseEndTime, profName, profEmail, profBuilding, profRoom, daysProf, profStartTime, profEndTime, cardColor);
        //course.setId(id);
        //viewModel.update(course);

        Intent back = new Intent();
        back.putExtra(CourseDetails.EXTRA_ID, id);
        back.putExtra(CourseDetails.EXTRA_NAME, name);
        back.putExtra(CourseDetails.EXTRA_DAYS, days);
        String startTime = courseStartTime;
        String endTime = courseEndTime;
        String time = "";
        if (!startTime.isEmpty() && !endTime.isEmpty()) {
            time = startTime + "-" + endTime;
        }
        back.putExtra(CourseDetails.EXTRA_TIME, time);
        Log.d("WTF", "t: " + time);
        back.putExtra(CourseDetails.EXTRA_BUILDING, building);
        back.putExtra(CourseDetails.EXTRA_ROOM, room);

        back.putExtra(CourseDetails.EXTRA_PNAME, profName);
        back.putExtra(CourseDetails.EXTRA_EMAIL, profEmail);
        back.putExtra(CourseDetails.EXTRA_PDAYS, daysProf);
        startTime = profStartTime;
        endTime = profEndTime;
        time = "";
        if (!startTime.isEmpty() && !endTime.isEmpty()) {
            time = startTime + "-" + endTime;
        }
        back.putExtra(CourseDetails.EXTRA_PTIME, time);
        back.putExtra(CourseDetails.EXTRA_PBUILDING, profBuilding);
        back.putExtra(CourseDetails.EXTRA_PROOM, profRoom);
        back.putExtra(CourseDetails.EXTRA_COLOR, cardColor);
        setResult(RESULT_OK, back);
        finish();
    }

    public String getChosenDays(String[] chosenDays) {
        StringBuilder allDays = new StringBuilder();
        for(String day : chosenDays) {
            if(!day.isEmpty()) {
                allDays.append(day).append(", ");
            }
        }

        if(allDays.length() == 0) {
            return "";
        }
        return allDays.substring(0, allDays.length() - 2);
    }

    public void startTimeClick(View view) {
        new TimePickerDialog(view.getContext(), startTimePicker, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    public void endTimeClick(View view) {
        new TimePickerDialog(view.getContext(), endTimePicker, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    public void profStartTimeClick(View view) {
        new TimePickerDialog(view.getContext(), profStartTimePicker, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    public void profEndTimeClick(View view) {
        new TimePickerDialog(view.getContext(), profEndTimePicker, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    public void setDayArrays() {
        courseDays = new String[7];
        profDays = new String[7];

        for(int i = 0; i < 7; i++) {
            courseDays[i] = "";
            profDays[i] = "";
        }
    }

    public void selectColorClick(View view) {
        final ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position,int color) {

            }

            @Override
            public void onCancel(){
                // put code
            }
        })
                .addListenerButton("Select", (v, position, color) -> {
                    cardColor = color;
                    if(color != 0) {
                       setColor(color);
                    }
                    colorPicker.dismissDialog();
                }).setColors(R.array.colorSelections)
                .setDefaultColorButton(Color.parseColor("#212121"))
                .disableDefaultButtons(true)
                .setRoundColorButton(true)
                .setColumns(3)
                .setTitle("Choose a color")
                .show();
    }

    public void setColor(int color) {
        selectColorButton.setTextColor(Color.parseColor("#fafafa"));
        selectColorButton.setBackgroundColor(color);

        editTextName.setTextColor(color);
        editTextBuilding.setTextColor(color);
        editTextRoom.setTextColor(color);

        courseSun.setTextColor(color);
        courseMon.setTextColor(color);
        courseTue.setTextColor(color);
        courseWed.setTextColor(color);
        courseThu.setTextColor(color);
        courseFri.setTextColor(color);
        courseSat.setTextColor(color);

        editTextStart.setTextColor(color);
        editTextEnd.setTextColor(color);

        editTextProfName.setTextColor(color);
        editTextProfEmail.setTextColor(color);
        editTextProfBuilding.setTextColor(color);
        editTextProfRoom.setTextColor(color);

        profSun.setTextColor(color);
        profMon.setTextColor(color);
        profTue.setTextColor(color);
        profWed.setTextColor(color);
        profThu.setTextColor(color);
        profFri.setTextColor(color);
        profSat.setTextColor(color);

        editTextProfStart.setTextColor(color);
        editTextProfEnd.setTextColor(color);

        saveChangesButton.setBackgroundColor(color);
    }
}
