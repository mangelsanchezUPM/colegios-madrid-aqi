package es.upm.miw.colegiosmadridaqi.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ColegioDAO {
    @Query("SELECT * FROM " + Colegio.TABLA)
    LiveData<List<Colegio>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Colegio colegio);

    @Query("DELETE FROM " + Colegio.TABLA)
    void deleteAll();

    @Delete
    void delete(Colegio puntuacion);
}
