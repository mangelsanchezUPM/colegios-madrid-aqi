package es.upm.miw.colegiosmadridaqi.room;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ColegioViewModel extends AndroidViewModel {

    private ColegioRepository mRepository;

    private LiveData<List<Colegio>> mAllColegios;

    public ColegioViewModel(Application application) {
        super(application);
        mRepository = new ColegioRepository(application);
        mAllColegios = mRepository.getAllColegios();
    }

    public LiveData<List<Colegio>> getAllColegios() {
        return mAllColegios;
    }

    public void insert(Colegio colegio) {
        mRepository.insert(colegio);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteColegio(Colegio colegio) {
        mRepository.deleteColegio(colegio);
    }
}
