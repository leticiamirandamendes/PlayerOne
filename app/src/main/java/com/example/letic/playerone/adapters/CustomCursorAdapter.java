package com.example.letic.playerone.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.letic.playerone.R;
import com.example.letic.playerone.data.TaskContract;

public class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.TaskViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public CustomCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.favs, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        int idIndex = mCursor.getColumnIndex(TaskContract.TaskEntry._ID);
        int nameIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME);
        int authorIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_AUTHOR);

        mCursor.moveToPosition(position);

        final String id = mCursor.getString(idIndex);
        String name = mCursor.getString(nameIndex);
        String author = mCursor.getString(authorIndex);

        holder.itemView.setTag(id);
        holder.name.setText(name);
        holder.autor.setText(author);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        if (mCursor == c) {
            return null;
        }
        Cursor temp = mCursor;
        this.mCursor = c;

        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView autor;

        public TaskViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.fav_name);
            autor = itemView.findViewById(R.id.fav_artist);
        }
    }
}