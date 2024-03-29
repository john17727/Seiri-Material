package cs4330.cs.utep.edu.seirimaterial.models;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import cs4330.cs.utep.edu.seirimaterial.data.Assignment;
import cs4330.cs.utep.edu.seirimaterial.data.Course;

public class Repository {

    private CourseDao courseDao;
    private LiveData<List<Course>> allCourses;

    private AssignmentDao assignmentDao;
    private LiveData<List<Assignment>> allAssignments;

    Repository(Application application) {
        Database database = Database.getInstance(application);
        courseDao = database.courseDao();
        allCourses = courseDao.getAllCourses();
        assignmentDao = database.assignmentDao();
        allAssignments = assignmentDao.getAllAssignments();
    }

    public void insert(Course course) {
        new InsertCourseAsyncTask(courseDao).execute(course);
    }

    void update(Course course) {
        new UpdateCourseAsyncTask(courseDao).execute(course);
    }

    public void delete(Course course) {
        new DeleteCourseAsyncTask(courseDao).execute(course);
    }

    void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(courseDao).execute();
    }

    LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    List<String> getAllCourseNames() throws ExecutionException, InterruptedException {
        return new getAllCourseNamesAsyncTask(courseDao).execute().get();
    }

    List<Integer> getAllColors() throws ExecutionException, InterruptedException {
        return new getAllColorsAsyncTask(courseDao).execute().get();
    }



    public void insert(Assignment assignment) {
        new InsertAssignmentAsyncTask(assignmentDao).execute(assignment);
    }

    void update(Assignment assignment) {
        new UpdateAssignmentAsyncTask(assignmentDao).execute(assignment);
    }

    public void delete(Assignment assignment) {
        new DeleteAssignmentAsyncTask(assignmentDao).execute(assignment);
    }

    void deleteAllAssignments() {
        new DeleteAllAssignmentsAsyncTask(assignmentDao).execute();
    }

    LiveData<List<Assignment>> getAllAssignments() {
        return allAssignments;
    }




    private static class InsertCourseAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDao courseDao;

        private InsertCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.insert(courses[0]);
            return null;
        }
    }

    private static class UpdateCourseAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDao courseDao;

        private UpdateCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.update(courses[0]);
            return null;
        }
    }

    private static class DeleteCourseAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDao courseDao;

        private DeleteCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.delete(courses[0]);
            return null;
        }
    }

    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {

        private CourseDao courseDao;

        private DeleteAllCoursesAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            courseDao.deleteAllCourses();
            return null;
        }
    }

    private static class getAllCourseNamesAsyncTask extends AsyncTask<Void, Void, List<String>> {

        private CourseDao courseDao;

        private getAllCourseNamesAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            return courseDao.getAllCourseNames();
        }
    }

    private static class getAllColorsAsyncTask extends AsyncTask<Void, Void, List<Integer>> {

        private CourseDao courseDao;

        private getAllColorsAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected List<Integer> doInBackground(Void... voids) {
            return courseDao.getAllColors();
        }
    }




    private static class InsertAssignmentAsyncTask extends AsyncTask<Assignment, Void, Void> {

        private AssignmentDao assignmentDao;

        private InsertAssignmentAsyncTask(AssignmentDao assignmentDao) {
            this.assignmentDao = assignmentDao;
        }

        @Override
        protected Void doInBackground(Assignment... assignments) {
            assignmentDao.insert(assignments[0]);
            return null;
        }
    }

    private static class UpdateAssignmentAsyncTask extends AsyncTask<Assignment, Void, Void> {

        private AssignmentDao assignmentDao;

        private UpdateAssignmentAsyncTask(AssignmentDao assignmentDao) {
            this.assignmentDao = assignmentDao;
        }

        @Override
        protected Void doInBackground(Assignment... assignments) {
            assignmentDao.update(assignments[0]);
            return null;
        }
    }

    private static class DeleteAssignmentAsyncTask extends AsyncTask<Assignment, Void, Void> {

        private AssignmentDao assignmentDao;

        private DeleteAssignmentAsyncTask(AssignmentDao assignmentDao) {
            this.assignmentDao = assignmentDao;
        }

        @Override
        protected Void doInBackground(Assignment... assignments) {
            assignmentDao.delete(assignments[0]);
            return null;
        }
    }

    private static class DeleteAllAssignmentsAsyncTask extends AsyncTask<Void, Void, Void> {

        private AssignmentDao assignmentDao;

        private DeleteAllAssignmentsAsyncTask(AssignmentDao assignmentDao) {
            this.assignmentDao = assignmentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            assignmentDao.deleteAllAssignments();
            return null;
        }
    }
}
