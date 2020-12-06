package com.project.pillrem;
/**
 * Created by Sulthan Nizarudin on 06-12-2020.
 */
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

class MedicineAdapter extends RecyclerView.Adapter<MedicineViewHolder>
        implements Filterable {
    private Context context;
    private List<Medicine> listMeds;
    private List<Medicine> mArrayList;
    private DatabaseHandler mDatabase;
    private String time;

    MedicineAdapter(Context context, List<Medicine> listMeds) {
        this.context = context;
        this.listMeds = listMeds;
        this.mArrayList = listMeds;
        mDatabase = new DatabaseHandler(context);
    }

    @Override
    public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.med_item, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicineViewHolder holder, int position) {
        final Medicine medicine = listMeds.get(position);
        holder.tvName.setText(medicine.getName());

        if(Integer.parseInt(medicine.gettime())<46800000){
            int timeh=(Integer.parseInt(medicine.gettime())/60000)/60;
            int timem=(Integer.parseInt(medicine.gettime())/60000)-(timeh*60);
            if (timeh==0) timeh=12;
             time=timeh+":"+timem+" AM";
        }
        else{
            int timeh=((Integer.parseInt(medicine.gettime())-43200000)/60000)/60;
            int timem=((Integer.parseInt(medicine.gettime())-43200000)/60000)-(timeh*60);
            time=timeh+":"+timem+" PM";
        }


        holder.tvPhoneNum.setText(time);
        holder.tvday.setText(medicine.getday());

        holder.deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.deleteContact(medicine);
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listMeds = mArrayList;
                } else {
                    ArrayList<Medicine> filteredList = new ArrayList<>();
                    for (Medicine med : mArrayList) {
                        if (med.getName().toLowerCase().contains(charString)) {
                            filteredList.add(med);
                        }
                    }
                    listMeds = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listMeds;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listMeds = (ArrayList<Medicine>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return listMeds.size();
    }
}
