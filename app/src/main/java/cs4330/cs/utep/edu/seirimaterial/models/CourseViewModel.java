package cs4330.cs.utep.edu.seirimaterial.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import cs4330.cs.utep.edu.seirimaterial.data.Course;

public class CourseViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Course>> allCourses;
    private List<String> courses;
    private List<Integer> colors;

    public CourseViewModel(@NonNull Application application) throws ExecutionException, InterruptedException {
        super(application);
        repository = new Repository(application);
        allCourses = repository.getAllCourses();
        courses = repository.getAllCourseNames();
        colors = repository.getAllColors();
    }

    public void insert(Course course) {
        repository.insert(course);
    }

    public void update(Course course) {
        repository.update(course);
    }

    public void delete(Course course) {
        repository.delete(course);
    }

    public void deleteAllCourses() {
        repository.deleteAllCourses();
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public List<String> getAllCourseNames() {
        return courses;
    }

    public List<Integer> getAllColors() {
        return colors;
    }
}
