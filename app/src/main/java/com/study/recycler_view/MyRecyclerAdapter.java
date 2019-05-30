package com.study.recycler_view;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
//

import java.util.List;
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<Album> albumList;
    private int itemLayout;


    public MyRecyclerAdapter(List<Album> items , int itemLayout){

        this.albumList = items;
        this.itemLayout = itemLayout;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout,viewGroup,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Album item = albumList.get(position);
        viewHolder.textTitle.setText(item.getTitle());
        viewHolder.img.setBackgroundResource(item.getImage());
        viewHolder.itemView.setTag(item);

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public TextView textTitle;

        public ViewHolder(View itemView){
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.imgProfile);
            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
        }

    }
}

