package com.project.pillrem;
/**
 * Created by Sulthan Nizarudin on 06-12-2020.
 */
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

class MedicineViewHolder extends RecyclerView.ViewHolder {
    TextView tvName, tvPhoneNum;
    ImageView deleteContact;
    ImageView editContact;
    MedicineViewHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.medname);
        tvPhoneNum = itemView.findViewById(R.id.medtime);
        deleteContact = itemView.findViewById(R.id.deleteContact);
    }
}
