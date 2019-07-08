package cs4330.cs.utep.edu.seirimaterial;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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

        coursesMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCourseActivity = new Intent(v.getContext(), MainActivity.class);
                startActivity(toCourseActivity);
                getActivity().finish();
            }
        });

        assignmentMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAssignmentActivity = new Intent(v.getContext(), AssignmentActivity.class);
                startActivity(toAssignmentActivity);
                getActivity().finish();
            }
        });
    }

    public void toast(String msg) {
        Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG);
        toast.show();
    }
}
