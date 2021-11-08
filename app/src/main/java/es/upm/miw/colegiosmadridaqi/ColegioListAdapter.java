package es.upm.miw.colegiosmadridaqi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import colegiosmadridaqi.R;
import es.upm.miw.colegiosmadridaqi.models.ColegioContaminacion;

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
    private FirebaseUser user;

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
            if (current.getAqi() < 33) {
                holder.contaminacionBackground.setBackgroundColor(holder.itemView.getResources().getColor(R.color.very_good));
                emoji = "\uD83D\uDE0A";
            }
            if (current.getAqi() > 33 && current.getAqi() < 66) {
                holder.contaminacionBackground.setBackgroundColor(holder.itemView.getResources().getColor(R.color.good));
                emoji = "\uD83D\uDE00";
            }
            if (current.getAqi() > 66 && current.getAqi() < 100) {
                holder.contaminacionBackground.setBackgroundColor(holder.itemView.getResources().getColor(R.color.fair));
                emoji = "\uD83D\uDE10";
            }
            if (current.getAqi() > 100 && current.getAqi() < 150) {
                holder.contaminacionBackground.setBackgroundColor(holder.itemView.getResources().getColor(R.color.poor));
                emoji = "\uD83D\uDE41";
            }
            if (current.getAqi() > 150 && current.getAqi() < 200) {
                holder.contaminacionBackground.setBackgroundColor(holder.itemView.getResources().getColor(R.color.very_poor));
                emoji = "\uD83D\uDE1F";
            }
            if (current.getAqi() > 200) {
                holder.contaminacionBackground.setBackgroundColor(holder.itemView.getResources().getColor(R.color.hazardous));
                emoji = "\uD83D\uDE31";
            }
            holder.nombreColegio.setText(current.getNombre() + "\n" + emoji);
            holder.contaminacionBackground.setOnClickListener(view -> {
                Intent intent = new Intent(holder.itemView.getContext(), DetallesActivity.class);
                intent.putExtra("aqi", current.getAqi());
                intent.putExtra("nombreColegio", current.getNombre());

                intent.putExtra("h", current.getIaqi().getH().getV());
                intent.putExtra("no2", current.getIaqi().getNo2().getV());
                intent.putExtra("p", current.getIaqi().getP().getV());
                intent.putExtra("t", current.getIaqi().getT().getV());
                intent.putExtra("pm10", current.getIaqi().getPm10().getV());
                intent.putExtra("pm25", current.getIaqi().getPm25().getV());
                intent.putExtra("w", current.getIaqi().getW().getV());
                intent.putExtra("wg", current.getIaqi().getWg().getV());

                intent.putExtra("user", user);
                holder.itemView.getContext().startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return (colegioContaminacionList == null)
                ? 0
                : colegioContaminacionList.size();
    }

    public void setColegioContaminacionList(List<ColegioContaminacion> colegioContaminacionList, FirebaseUser user) {
        this.colegioContaminacionList = colegioContaminacionList;
        this.user = user;
        notifyDataSetChanged();
    }
}
