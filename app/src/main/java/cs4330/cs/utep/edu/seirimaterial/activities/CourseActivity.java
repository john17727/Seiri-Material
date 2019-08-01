/*
 *Copyright 2019 Juan Rincon
 */

package cs4330.cs.utep.edu.seirimaterial.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import cs4330.cs.utep.edu.seirimaterial.data.Course;
import cs4330.cs.utep.edu.seirimaterial.adapters.CourseAdapter;
import cs4330.cs.utep.edu.seirimaterial.models.CourseViewModel;
import cs4330.cs.utep.edu.seirimaterial.utils.NavigationBottomSheet;
import cs4330.cs.utep.edu.seirimaterial.R;

public class CourseActivity extends AppCompatActivity {


    private CourseViewModel courseViewModel;

    BottomAppBar bottomAppBar;
    NavigationBottomSheet bottomSheetFragment;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        bottomAppBar = findViewById(R.id.bottomAppBar);
        setSupportActionBar(bottomAppBar);

        //FAB: add a course
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent addCourse = new Intent(v.getContext(), AddCourse.class);
            startActivity(addCourse);
        });

        //BOTTOM APP BAR: set onClick Listener
        bottomAppBar.setNavigationOnClickListener(v -> {
            bottomSheetFragment = new NavigationBottomSheet();
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CourseAdapter adapter;
        adapter = new CourseAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this, adapter::setCourses);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Course course = adapter.getCourseAt(viewHolder.getAdapterPosition());
                courseViewModel.delete(course);
                Snackbar.make(findViewById(R.id.courses), course.getName() + " Deleted", Snackbar.LENGTH_LONG).setAction(R.string.undo, v -> courseViewModel.insert(course)).setActionTextColor(ContextCompat.getColor(CourseActivity.this, R.color.colorOnSecondary)).setAnchorView(R.id.fab).show();
            }
        }).attachToRecyclerView(recyclerView);

        //ADAPTER: set onClick Listener
        adapter.setOnItemClickListener((course, position) -> {

            //Send all information over on an intent
            Intent courseDetails = new Intent(CourseActivity.this, CourseDetails.class);
            courseDetails.putExtra(CourseDetails.EXTRA_ID, course.getId());
            courseDetails.putExtra(CourseDetails.EXTRA_NAME, course.getName());
            courseDetails.putExtra(CourseDetails.EXTRA_DAYS, course.getDays());
            String startTime = course.getStartTime();
            String endTime = course.getEndTime();
            String time = "";
            if (startTime != null && endTime != null) {
                if (!startTime.isEmpty() && !endTime.isEmpty()) {
                    time = startTime + "-" + endTime;
                }
            }
            courseDetails.putExtra(CourseDetails.EXTRA_TIME, time);
            courseDetails.putExtra(CourseDetails.EXTRA_BUILDING, course.getBuilding());
            courseDetails.putExtra(CourseDetails.EXTRA_ROOM, course.getRoom());

            courseDetails.putExtra(CourseDetails.EXTRA_PNAME, course.getProfName());
            courseDetails.putExtra(CourseDetails.EXTRA_EMAIL, course.getEmail());
            courseDetails.putExtra(CourseDetails.EXTRA_PDAYS, course.getProfDays());
            startTime = course.getProfStartTime();
            endTime = course.getProfEndTime();
            String timeP = "";
            if (startTime != null && endTime != null) {
                if (!startTime.isEmpty() && !endTime.isEmpty()) {
                    timeP = startTime + "-" + endTime;
                }
            }
            courseDetails.putExtra(CourseDetails.EXTRA_PTIME, timeP);
            courseDetails.putExtra(CourseDetails.EXTRA_PBUILDING, course.getProfBuilding());
            courseDetails.putExtra(CourseDetails.EXTRA_PROOM, course.getProfRoom());
            courseDetails.putExtra(CourseDetails.EXTRA_COLOR, course.getColor());

            startActivity(courseDetails);
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
                courseViewModel.deleteAllCourses();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void toast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}
