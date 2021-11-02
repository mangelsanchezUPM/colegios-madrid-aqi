package es.upm.miw.calidadairecolegiosvallecas.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ColegioRepository {
    private ColegioDAO mColegioDao;
    private LiveData<List<Colegio>> mColegios;

    public ColegioRepository(Application application) {
        ColegioRoomDatabase db = ColegioRoomDatabase.getDatabase(application);
        mColegioDao = db.colegioDAO();
        mColegios = mColegioDao.getAll();
    }

    public LiveData<List<Colegio>> getAllColegios() {
        return mColegios;
    }

    public long insert(Colegio colegio) {
        return mColegioDao.insert(colegio);
    }

    public void deleteAll() {
        mColegioDao.deleteAll();
    }

    public void deleteColegio(Colegio colegio) {
        mColegioDao.delete(colegio);
    }
}
