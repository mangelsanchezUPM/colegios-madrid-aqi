package es.upm.miw.calidadairecolegiosvallecas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.upm.miw.calidadairecolegiosvallecas.models.ColegioContaminacion;
import es.upm.miw.calidadairecolegiosvallecas.room.Colegio;

public class ColegioListAdapter extends RecyclerView.Adapter<ColegioListAdapter.ColegioViewHolder> {

    class ColegioViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombreColegio;
        private final TextView contaminacion;

        private ColegioViewHolder(View itemView) {
            super(itemView);
            nombreColegio = itemView.findViewById(R.id.nombreColegio);
            contaminacion = itemView.findViewById(R.id.contaminacion);
        }
    }

    private final LayoutInflater mInflater;
    private List<ColegioContaminacion> colegioContaminacionList;

    public ColegioListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ColegioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ColegioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ColegioViewHolder holder, int position) {
        if (colegioContaminacionList != null) {
            ColegioContaminacion current = colegioContaminacionList.get(position);
            holder.nombreColegio.setText(current.getNombre());
            holder.contaminacion.setText(current.getAqi().toString());
        }
    }

    @Override
    public int getItemCount() {
        return (colegioContaminacionList == null)
                ? 0
                : colegioContaminacionList.size();
    }

    public void setColegioContaminacionList(List<ColegioContaminacion> colegioContaminacionList) {
        this.colegioContaminacionList = colegioContaminacionList;
        notifyDataSetChanged();
    }
}
