package cs4330.cs.utep.edu.seirimaterial.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import cs4330.cs.utep.edu.seirimaterial.models.CourseViewModel;
import cs4330.cs.utep.edu.seirimaterial.utils.NavigationBottomSheet;
import cs4330.cs.utep.edu.seirimaterial.R;

public class AssignmentActivity extends AppCompatActivity {

    private CourseViewModel courseViewModel;

    private List<String> courseNames;

    BottomAppBar bottomAppBar;
    NavigationBottomSheet bottomSheetFragment;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        bottomAppBar = findViewById(R.id.bottomAppBar);
        setSupportActionBar(bottomAppBar);

        fab = findViewById(R.id.fab);


        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseNames = courseViewModel.getAllCourseNames();

        bottomAppBar.setNavigationOnClickListener(v -> {
            bottomSheetFragment = new NavigationBottomSheet();
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        });

        fab.setOnClickListener(v -> {
            Intent addAssignment = new Intent(v.getContext(), AddAssignment.class);
            startActivity(addAssignment);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.delete_all_courses:
                toast("Filter");
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void toast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}
