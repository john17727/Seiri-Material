package cs4330.cs.utep.edu.seirimaterial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewModel viewModel;

    BottomAppBar bottomAppBar;
    NavigationBottomSheet bottomSheetFragment;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        CourseAdapter adapter = new CourseAdapter();
        recyclerView.setAdapter(adapter);

        bottomAppBar = findViewById(R.id.bottomAppBar);
        setSupportActionBar(bottomAppBar);

        fab = findViewById(R.id.fab);

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetFragment = new NavigationBottomSheet();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        viewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.setCourses(courses);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCourse = new Intent(v.getContext(), AddCourse.class);
                startActivity(addCourse);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Course course = adapter.getCourseAt(viewHolder.getAdapterPosition());
                viewModel.delete(course);
                Snackbar.make(findViewById(R.id.courses), course.getName() + " Deleted", Snackbar.LENGTH_LONG).setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.insert(course);
                    }
                }).setActionTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorOnSecondary)).setAnchorView(R.id.fab).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course, int position, TextView title) {

                Intent courseDetails = new Intent(MainActivity.this, CourseDetails.class);
                courseDetails.putExtra(CourseDetails.EXTRA_ID, course.getId());
                courseDetails.putExtra(CourseDetails.EXTRA_NAME, course.getName());
                courseDetails.putExtra(CourseDetails.EXTRA_DAYS, course.getDays());
                String startTime = course.getStartTime();
                String endTime = course.getEndTime();
                String time = "";
                if (!startTime.isEmpty() && !endTime.isEmpty()) {
                    time = startTime + "-" + endTime;
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
                if (!startTime.isEmpty() && !endTime.isEmpty()) {
                    timeP = startTime + "-" + endTime;
                }
                courseDetails.putExtra(CourseDetails.EXTRA_PTIME, timeP);
                courseDetails.putExtra(CourseDetails.EXTRA_PBUILDING, course.getProfBuilding());
                courseDetails.putExtra(CourseDetails.EXTRA_PROOM, course.getProfRoom());
                courseDetails.putExtra(CourseDetails.EXTRA_COLOR, course.getColor());

                Pair pair = new Pair<View, String>(title, "name_transition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pair);
                startActivity(courseDetails, options.toBundle());
            }
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
                viewModel.deleteAllCourses();
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
