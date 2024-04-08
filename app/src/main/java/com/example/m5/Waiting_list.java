package com.example.m5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import com.example.m5.R;
import com.example.m5.database.Students;
import androidx.recyclerview.widget.RecyclerView;

/************************************************************************************************
 * Class Waiting_list to render the RecyclerView with defined layout and data set
 ************************************************************************************************/
public class Waiting_list extends RecyclerView.Adapter<Waiting_list.MyViewHolder> {

    // Private members of class
    private Context context;
    private List<Students> student;

    // Nested class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView priority;

        // Constructor of MyViewHolder
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            priority = view.findViewById(R.id.priority);
        }
    }

    // Constructor of WaitingListAdapter
    public Waiting_list(Context context, List<Students> student) {
        this.context = context;
        this.student = student;
    }

    /*******************************************************************************************
     * Overridden method for onCreateViewHolder
     *****************************************************************************************/
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Students item = student.get(position);
        holder.name.setText(item.getName());
        holder.priority.setText(item.getPriority());
    }

    /******************************************************************************************
     * Return the count of items in the Waiting list
     *****************************************************************************************/
    @Override
    public int getItemCount() {
        return student.size();
    }
}