package cs4330.cs.utep.edu.seirimaterial.utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import cs4330.cs.utep.edu.seirimaterial.R;
import cs4330.cs.utep.edu.seirimaterial.activities.AssignmentActivity;
import cs4330.cs.utep.edu.seirimaterial.activities.CourseActivity;

public class NavigationBottomSheet extends BottomSheetDialogFragment {

    LinearLayout coursesMenu;
    LinearLayout assignmentMenu;
    ViewGroup container;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.navigation_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        coursesMenu = view.findViewById(R.id.courses);
        assignmentMenu = view.findViewById(R.id.assignments);

        coursesMenu.setOnClickListener(v -> {
            Intent toCourseActivity = new Intent(v.getContext(), CourseActivity.class);
            startActivity(toCourseActivity);
            getActivity().finish();
        });

        assignmentMenu.setOnClickListener(v -> {
            Intent toAssignmentActivity = new Intent(v.getContext(), AssignmentActivity.class);
            startActivity(toAssignmentActivity);
            getActivity().finish();
        });
    }
}
