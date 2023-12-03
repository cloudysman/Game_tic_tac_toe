package com.example.game_tic_tac_toe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private ArrayList<StudentModel> stdList = new ArrayList<>();
    private OnItemClickListener onClickItem;
    private OnItemClickListener onClickDeleteItem;

    public interface OnItemClickListener {
        void onItemClick(StudentModel student);
    }

    public void addItem(ArrayList<StudentModel> items) {
        this.stdList = items;
        notifyDataSetChanged();
    }


    public void setOnClickItem(OnItemClickListener callback) {
        this.onClickItem = callback;
    }

    public void setOnClickDeleteItem(OnItemClickListener callback) {
        this.onClickDeleteItem = callback;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_player, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        StudentModel std = stdList.get(position);
        holder.bindView(std);
        holder.itemView.setOnClickListener(v -> {
            if (onClickItem != null) {
                onClickItem.onItemClick(std);
            }
        });
        holder.btnDelete.setOnClickListener(v -> {
            if (onClickDeleteItem != null) {
                onClickDeleteItem.onItemClick(std);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stdList.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView email;
        private TextView password;
        private TextView btnDelete;

        public StudentViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvname);
            email = view.findViewById(R.id.tvemail);
            password=view.findViewById(R.id.tvpassword);
            btnDelete = view.findViewById(R.id.btnDelete);
        }

        public void bindView(StudentModel std) {
            name.setText(std.getName());
            email.setText(std.getEmail());
            password.setText(std.getPassword());
        }
    }
}
