package es.upm.miw.calidadairecolegiosvallecas;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.upm.miw.calidadairecolegiosvallecas.models.ColegioContaminacion;

public class ColegioListAdapter extends RecyclerView.Adapter<ColegioListAdapter.ColegioViewHolder> {

    class ColegioViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombreColegio;
        private final LinearLayout contaminacionBackground;

        private ColegioViewHolder(View itemView) {
            super(itemView);
            nombreColegio = itemView.findViewById(R.id.nombreColegio);
            contaminacionBackground = itemView.findViewById(R.id.item_background);
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
            String emoji = "";
            if (current.getAqi() < 2) {
                holder.contaminacionBackground.setBackgroundColor(Color.parseColor("#76DA6B"));
                emoji = "\uD83D\uDE03";
            }
            if (current.getAqi() == 2) {
                holder.contaminacionBackground.setBackgroundColor(Color.parseColor("#DAC26B"));
                emoji = "\uD83D\uDE10";
            }
            if (current.getAqi() > 2) {
                holder.contaminacionBackground.setBackgroundColor(Color.parseColor("#DA6B6B"));
                emoji = "\uD83D\uDE14";
            }
            holder.nombreColegio.setText(current.getNombre() + "\n" + emoji);
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
