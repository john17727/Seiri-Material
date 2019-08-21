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

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int FOOTER_VIEW = 1;
    private List<Course> courses = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        if(viewType == FOOTER_VIEW) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false);
            return new FooterHolder(itemView);
        }

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CourseHolder) {
            CourseHolder courseHolder = (CourseHolder) holder;

            Course currentCourse = courses.get(position);

            courseHolder.card.setCardBackgroundColor(currentCourse.getColor());
            courseHolder.textViewName.setText(currentCourse.getName());
            courseHolder.textViewDays.setText(currentCourse.getDays());
            String startTime = currentCourse.getStartTime();
            String endTime = currentCourse.getEndTime();
            if (startTime.isEmpty() && endTime.isEmpty()) {
                courseHolder.textViewTime.setText("");
            } else {
                String fullTime = startTime + "-" + endTime;
                courseHolder.textViewTime.setText(fullTime);
            }
        } else if (holder instanceof FooterHolder) {
            FooterHolder footerHolder = (FooterHolder) holder;
            footerHolder.footer.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if(courses == null) {
            return 0;
        }
        if(courses.size() == 0) {
            return 1;
        }

        return courses.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == courses.size()) {
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
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

        CourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewDays = itemView.findViewById(R.id.text_view_days);
            textViewTime = itemView.findViewById(R.id.text_view_time);
            card = itemView.findViewById(R.id.card_view_courses);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(courses.get(position), position);
                }
            });
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {
        private TextView footer;
        FooterHolder(@NonNull View itemView) {
            super(itemView);
            footer = itemView.findViewById(R.id.footer);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Course course, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
