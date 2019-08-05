package cs4330.cs.utep.edu.seirimaterial.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cs4330.cs.utep.edu.seirimaterial.data.Assignment;

public class AssignmentViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Assignment>> allAssignments;

    public AssignmentViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allAssignments = repository.getAllAssignments();
    }

    public void insert(Assignment assignment) {
        repository.insert(assignment);
    }

    public void update(Assignment assignment) {
        repository.update(assignment);
    }

    public void delete(Assignment assignment) {
        repository.delete(assignment);
    }

    public void deleteAllAssignments() {
        repository.deleteAllAssignments();
    }

    public LiveData<List<Assignment>> getAllAssignments() {
        return allAssignments;
    }
}
