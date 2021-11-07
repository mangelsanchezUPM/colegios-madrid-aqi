package es.upm.miw.colegiosmadridaqi.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Colegio.class}, version = 1, exportSchema = false)
public abstract class ColegioRoomDatabase extends RoomDatabase {

    public static final String BASE_DATOS = Colegio.TABLA + ".db";

    public abstract ColegioDAO colegioDAO();

    private static volatile ColegioRoomDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ColegioRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ColegioRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ColegioRoomDatabase.class, BASE_DATOS)
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Delete all content and repopulate the database whenever the app is started
    private static Callback sRoomDatabaseCallback =
            new Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);

                    // If you want to keep data through app restarts,
                    // comment out the following block
                    databaseWriteExecutor.execute(() -> {
                        // Populate the database in the background.
                        // If you want to start with more groups, just add them.
                        ColegioDAO dao = INSTANCE.colegioDAO();
                    });
                }
            };
}
