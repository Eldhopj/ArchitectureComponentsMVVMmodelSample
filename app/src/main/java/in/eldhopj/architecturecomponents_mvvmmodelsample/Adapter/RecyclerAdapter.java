package in.eldhopj.architecturecomponents_mvvmmodelsample.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.eldhopj.architecturecomponents_mvvmmodelsample.R;
import in.eldhopj.architecturecomponents_mvvmmodelsample.RoomDb.DataBase.NoteEntity;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> { //RecyclerAdapter


    private List<NoteEntity> mListItems; // List
    private Context mContext;
    private OnItemClickListener mListener; // Listener for the OnItemClickListener interface

    //constructor
    public RecyclerAdapter( Context context) { // constructor
        this.mContext = context;
    }

    /**
     * interface will forward our click from adapter to our main activity
     */

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, NoteEntity note);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {// this method calls when ever our view method is created , ie; the instance of ViewHolder class is created
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);

//                //NOTE : use this if items height and width not following the match_parent or wrap_content
//                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//                view.setLayoutParams(layoutParams);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {//populate the data into the list_item (View Holder), as we scroll

        NoteEntity listitem = mListItems.get(position);
        if ( listitem != null) {
            holder.headTv.setText(listitem.getTitle());
            holder.descTv.setText(listitem.getDescription());
            holder.priorityTV.setText(Integer.toString(listitem.getPriority()));
        }
    }

    @Override
    public int getItemCount() { // return the size of the list view , NOTE : this must be a fast process
        if (mListItems == null) {
            return 0;
        }
        return mListItems.size();
    }

    //View Holder class caches these references that gonna modify in the adapter
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView headTv;
        TextView descTv;
        TextView priorityTV;

        //create a constructor with itemView as a params
        ViewHolder(View itemView) { // with the help of "itemView" we ge the views from xml
            super(itemView);

            headTv = itemView.findViewById(R.id.title);
            descTv = itemView.findViewById(R.id.description);
            priorityTV = itemView.findViewById(R.id.priority);

            itemView.setOnClickListener(new View.OnClickListener() { // we can handle the click as like we do in normal
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition(); // Get the index of the view holder
                        if (position != RecyclerView.NO_POSITION) { // Makes sure this position is still valid
                            mListener.onItemClick(v,mListItems.get(position)); // we catch the click on the item view then pass it over the interface and then to our activity
                        }
                    }

                }
            });
        }
    }


    public void setData(List<NoteEntity> items) {
        mListItems = items;
        notifyDataSetChanged();
    }

    public NoteEntity getNoteAt(int position){

        return mListItems.get(position);
 }
}
