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

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentHolder> {

    private static final String DATE_FORMAT = "EEE, MMM dd";
    private static final String TIME_FORMAT = "h:mm aa";

    private List<Assignment> assignments = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public AssignmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_item, parent, false);
        return new AssignmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentHolder holder, int position) {
        Assignment currentAssignment = assignments.get(position);

        holder.card.setCardBackgroundColor(currentAssignment.getColor());
        holder.textViewTitle.setText(currentAssignment.getTitle());

        if(currentAssignment.getDueDate() <= 0) {
            holder.textViewDate.setText("");
        } else {
            Date date = new Date(currentAssignment.getDueDate());
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            holder.textViewDate.setText(dateFormat.format(date));
        }

        if(currentAssignment.getDueTime() <= 0) {
            holder.textViewTime.setText("");
        } else {
            Date time = new Date(currentAssignment.getDueTime());
            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
            holder.textViewTime.setText(timeFormat.format(time));
        }
    }

    @Override
    public int getItemCount() {
        return assignments.size();
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

    public interface OnItemClickListener {
        void onItemClick(Assignment assignment, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

