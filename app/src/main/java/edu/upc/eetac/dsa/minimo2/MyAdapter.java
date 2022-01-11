package edu.upc.eetac.dsa.minimo2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.upc.eetac.dsa.minimo2.models.Repository;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Repository> elementList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in our viewHolder
        public TextView repositoryName;
        public TextView languaje;
        public View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            repositoryName = itemView.findViewById(R.id.repositoryName);
            languaje = itemView.findViewById(R.id.languaje);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from( parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, padding and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    public void setData(List<Repository> myDataSet){
        elementList=myDataSet;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
       // final String name = values.get(position);
        holder.repositoryName.setText(elementList.get(position).getName());
        holder.languaje.setText(elementList.get(position).getLanguage());
    }

    @Override
    public int getItemCount() {
        return elementList.size();
    }
}