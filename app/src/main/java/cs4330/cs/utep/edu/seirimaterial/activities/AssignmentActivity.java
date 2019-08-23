package cs4330.cs.utep.edu.seirimaterial.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import cs4330.cs.utep.edu.seirimaterial.adapters.AssignmentAdapter;
import cs4330.cs.utep.edu.seirimaterial.data.Assignment;
import cs4330.cs.utep.edu.seirimaterial.models.AssignmentViewModel;
import cs4330.cs.utep.edu.seirimaterial.models.CourseViewModel;
import cs4330.cs.utep.edu.seirimaterial.utils.NavigationBottomSheet;
import cs4330.cs.utep.edu.seirimaterial.R;

public class AssignmentActivity extends AppCompatActivity {

    public static final String COURSE_NAMES = "NAMES";
    public static final String COURSE_COLORS = "COLORS";

    private AssignmentViewModel assignmentViewModel;
    private AssignmentAdapter adapter;

    private List<String> courseNames;
    private List<Integer> courseColors;

    BottomAppBar bottomAppBar;
    NavigationBottomSheet bottomSheetFragment;
    FloatingActionButton fab;

    private Menu hiddenMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        bottomAppBar = findViewById(R.id.bottomAppBar);
        setSupportActionBar(bottomAppBar);

        fab = findViewById(R.id.fabA);
        fab.setOnClickListener(v -> {
            Intent addAssignment = new Intent(v.getContext(), AddAssignment.class);
            addAssignment.putStringArrayListExtra(COURSE_NAMES, (ArrayList<String>) courseNames);
            addAssignment.putIntegerArrayListExtra(COURSE_COLORS, (ArrayList<Integer>) courseColors);
            startActivity(addAssignment);
        });

        bottomAppBar.setNavigationOnClickListener(v -> {
            bottomSheetFragment = new NavigationBottomSheet();
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_assignments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new AssignmentAdapter();
        recyclerView.setAdapter(adapter);

        assignmentViewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);
        assignmentViewModel.getAllAssignments().observe(this, adapter::setAssignments);
        CourseViewModel courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseNames = courseViewModel.getAllCourseNames();
        courseColors = courseViewModel.getAllColors();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Assignment assignment = adapter.getAssignmentAt(viewHolder.getAdapterPosition());
                assignmentViewModel.delete(assignment);
                Snackbar.make(findViewById(R.id.assignments), assignment.getTitle() + " Deleted", Snackbar.LENGTH_LONG).setAction("Undo", v -> assignmentViewModel.insert(assignment))
                        .setActionTextColor(ContextCompat.
                                getColor(AssignmentActivity.this, R.color.colorOnSecondary))
                        .setAnchorView(R.id.fabA)
                        .show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener((assignment, position) -> {

            Intent assignmentDetails = new Intent(AssignmentActivity.this, AssignmentDetails.class);
            assignmentDetails.putStringArrayListExtra(COURSE_NAMES, (ArrayList<String>) courseNames);
            assignmentDetails.putIntegerArrayListExtra(COURSE_COLORS, (ArrayList<Integer>) courseColors);
            assignmentDetails.putExtra(AssignmentDetails.EXTRA_ID, assignment.getId());
            assignmentDetails.putExtra(AssignmentDetails.EXTRA_TITLE, assignment.getTitle());
            assignmentDetails.putExtra(AssignmentDetails.EXTRA_DUEDATE, assignment.getDueDate());
            assignmentDetails.putExtra(AssignmentDetails.EXTRA_DUETIME, assignment.getDueTime());
            assignmentDetails.putExtra(AssignmentDetails.EXTRA_TYPE, assignment.getType());
            assignmentDetails.putExtra(AssignmentDetails.EXTRA_COURSE, assignment.getCourse());
            assignmentDetails.putExtra(AssignmentDetails.EXTRA_INFO, assignment.getAddInfo());
            assignmentDetails.putExtra(AssignmentDetails.EXTRA_COLOR, assignment.getColor());
            startActivity(assignmentDetails);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        hiddenMenu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.assignment_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.filter:
                showPopup(findViewById(R.id.filter));
                break;
            case R.id.clear:
                adapter.getFilter().filter(null);
                hiddenMenu.findItem(R.id.clear).setVisible(false);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void showPopup(View view) {
        List<String> names = new ArrayList<>(courseNames);
        names.add(0, "None");
        PopupMenu popupMenu = new PopupMenu(this, view);
        for(String name:names) {
            popupMenu.getMenu().add(name);
        }
        popupMenu.setOnMenuItemClickListener(item -> {
            adapter.getFilter().filter(item.getTitle());
            hiddenMenu.findItem(R.id.clear).setVisible(true);
            return false;
        });
        popupMenu.show();
    }
}
