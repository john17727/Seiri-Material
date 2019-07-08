package cs4330.cs.utep.edu.seirimaterial;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssignmentDao {

    @Insert
    void insert(Assignment assignment);

    @Update
    void update(Assignment assignment);

    @Delete
    void delete(Assignment assignment);

    @Query("DELETE FROM assignment_table")
    void deleteAllAssignments();

    @Query("SELECT * FROM assignment_table ORDER BY dueDate ASC")
    LiveData<List<Assignment>> getAllAssignments();
}
