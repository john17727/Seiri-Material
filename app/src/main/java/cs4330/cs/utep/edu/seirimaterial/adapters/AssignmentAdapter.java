package cs4330.cs.utep.edu.seirimaterial.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cs4330.cs.utep.edu.seirimaterial.data.Assignment;
import cs4330.cs.utep.edu.seirimaterial.R;

public class AssignmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String DATE_FORMAT = "EEE, MMM dd";
    private static final String TIME_FORMAT = "h:mm aa";

    public static final int FOOTER_VIEW = 1;
    private List<Assignment> assignments = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        if(viewType == FOOTER_VIEW) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false);
            return new FooterHolder(itemView);
        }

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_item, parent, false);
        return new AssignmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  AssignmentHolder) {
            AssignmentHolder assignmentHolder = (AssignmentHolder) holder;

            Assignment currentAssignment = assignments.get(position);

            assignmentHolder.card.setCardBackgroundColor(currentAssignment.getColor());
            assignmentHolder.textViewTitle.setText(currentAssignment.getTitle());

            if(currentAssignment.getDueDate() <= 0) {
                assignmentHolder.textViewDate.setText("");
            } else {
                Date date = new Date(currentAssignment.getDueDate());
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                assignmentHolder.textViewDate.setText(dateFormat.format(date));
            }

            if(currentAssignment.getDueTime() <= 0) {
                assignmentHolder.textViewTime.setText("");
            } else {
                Date time = new Date(currentAssignment.getDueTime());
                SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
                assignmentHolder.textViewTime.setText(timeFormat.format(time));
            }
        } else if (holder instanceof CourseAdapter.FooterHolder) {
            FooterHolder footerHolder = (FooterHolder) holder;
            footerHolder.footer.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if(assignments == null) {
            return 0;
        }
        if(assignments.size() == 0) {
            return 1;
        }

        return assignments.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == assignments.size()) {
            return FOOTER_VIEW;
        }
        return super.getItemViewType(position);
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
        notifyDataSetChanged();
    }

    public Assignment getAssignmentAt(int position) {
        return assignments.get(position);
    }

    class AssignmentHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDate;
        private TextView textViewTime;
        private MaterialCardView card;

        public AssignmentHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewTime = itemView.findViewById(R.id.text_view_time);
            card = itemView.findViewById(R.id.card_view_assignments);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(assignments.get(position), position);
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
        void onItemClick(Assignment assignment, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

