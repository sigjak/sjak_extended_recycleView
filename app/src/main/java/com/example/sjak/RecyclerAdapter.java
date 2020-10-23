package com.example.sjak;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {
    private ArrayList<String> movieList;
   private ArrayList<String> moviesFull;


    public RecyclerAdapter(ArrayList<String> myMovies) {
        this.movieList = myMovies;
      moviesFull = new ArrayList<>(myMovies);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rowCountTextView.setText(String.valueOf(position));
        holder.textView.setText(movieList.get(position));

    }

    @Override
    public int getItemCount() {
        System.out.println( "MOVIES:"+ moviesFull);

        return movieList.size();
    }

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(moviesFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(String item: moviesFull){
                    if( item.toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
    FilterResults results =  new FilterResults();
            results.values = filteredList;


            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
        movieList.clear();
        movieList.addAll((Collection<? extends String>) results.values);
        notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView, rowCountTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            rowCountTextView = itemView.findViewById(R.id.rowCountTextView);
            itemView.setOnClickListener(this);

   /*         itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    movieList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    return true;
                }
            });*/
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(imageView.getContext(), movieList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }
}
