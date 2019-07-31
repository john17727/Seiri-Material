package cs4330.cs.utep.edu.seirimaterial.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import cs4330.cs.utep.edu.seirimaterial.data.Course;
import cs4330.cs.utep.edu.seirimaterial.R;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {

    private List<Course> courses = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Course currentCourse = courses.get(position);

        holder.card.setCardBackgroundColor(currentCourse.getColor());
        holder.textViewName.setText(currentCourse.getName());
        holder.textViewDays.setText(currentCourse.getDays());
        String startTime = currentCourse.getStartTime();
        String endTime = currentCourse.getEndTime();
        if (startTime.isEmpty() && endTime.isEmpty()) {
            holder.textViewTime.setText("");
        } else {
            holder.textViewTime.setText(startTime + "-" + endTime);
        }
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    public Course getCourseAt(int position) {
        return courses.get(position);
    }

    class CourseHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewDays;
        private TextView textViewTime;
        private MaterialCardView card;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewDays = itemView.findViewById(R.id.text_view_days);
            textViewTime = itemView.findViewById(R.id.text_view_time);
            card = itemView.findViewById(R.id.card_view_courses);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(courses.get(position), position, textViewName);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Course course, int position, TextView title);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
