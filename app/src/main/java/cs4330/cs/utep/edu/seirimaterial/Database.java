package cs4330.cs.utep.edu.seirimaterial;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Course.class, Assignment.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static Database instance;

    public abstract CourseDao courseDao();
    public abstract AssignmentDao assignmentDao();

    public static synchronized Database getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, "database").fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
