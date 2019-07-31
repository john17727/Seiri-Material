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

import cs4330.cs.utep.edu.seirimaterial.data.Assignment;
import cs4330.cs.utep.edu.seirimaterial.R;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentHolder> {

    private List<Assignment> assignments = new ArrayList<>();
    private AssignmentAdapter.OnItemClickListener listener;

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
        holder.textViewDate.setText(currentAssignment.getDueDate());
        holder.textViewTime.setText(currentAssignment.getDueTime());

    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
        notifyDataSetChanged();
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(assignments.get(position), position, textViewTitle);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Assignment assignment, int position, TextView title);
    }

    public void setOnItemClickListener(AssignmentAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}

