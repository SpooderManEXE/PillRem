package com.project.pillrem;
/**
 * Created by Sulthan Nizarudin on 06-12-2020.
 */
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

class MedicineViewHolder extends RecyclerView.ViewHolder {
    TextView medname, medtime,medday;
    ImageView deleteMed;
    ImageView editContact;
    MedicineViewHolder(View itemView) {
        super(itemView);
        medname = itemView.findViewById(R.id.medname);
        medtime = itemView.findViewById(R.id.medtime);
        medday=itemView.findViewById(R.id.medday);
        deleteMed = itemView.findViewById(R.id.deleteContact);
    }
}
