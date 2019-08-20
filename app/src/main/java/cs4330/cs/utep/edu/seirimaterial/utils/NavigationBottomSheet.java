package cs4330.cs.utep.edu.seirimaterial.utils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

import cs4330.cs.utep.edu.seirimaterial.R;
import cs4330.cs.utep.edu.seirimaterial.activities.AssignmentActivity;
import cs4330.cs.utep.edu.seirimaterial.activities.CourseActivity;

public class NavigationBottomSheet extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.navigation_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout coursesMenu = view.findViewById(R.id.courses_menu);
        LinearLayout assignmentMenu = view.findViewById(R.id.assignments_menu);

        coursesMenu.setOnClickListener(v -> {
            Log.d("WTF", getActivity().getLocalClassName());
            if(getActivity().getLocalClassName().equals("activities.AssignmentActivity")) {
                Intent toCourseActivity = new Intent(v.getContext(), CourseActivity.class);
                startActivity(toCourseActivity);
                Objects.requireNonNull(getActivity()).finish();
            } else {
                dismiss();
            }
        });

        assignmentMenu.setOnClickListener(v -> {
            Log.d("WTF", getActivity().getLocalClassName());
            if(getActivity().getLocalClassName().equals("activities.CourseActivity")) {
                Intent toAssignmentActivity = new Intent(v.getContext(), AssignmentActivity.class);
                startActivity(toAssignmentActivity);
                Objects.requireNonNull(getActivity()).finish();
            } else {
                dismiss();
            }
        });
    }
}
