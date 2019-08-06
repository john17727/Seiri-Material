package cs4330.cs.utep.edu.seirimaterial.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cs4330.cs.utep.edu.seirimaterial.R;

public class AssignmentDetails extends AppCompatActivity {

    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_TITLE = "TITLE";
    public static final String EXTRA_DUEDATE = "DUEDATE";
    public static final String EXTRA_DUETIME = "DUETIME";
    public static final String EXTRA_TYPE = "TYPE";
    public static final String EXTRA_COURSE = "COURSE";
    public static final String EXTRA_INFO = "INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_details);
    }
}
