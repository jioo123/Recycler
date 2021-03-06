package com.study.recycler_view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

//
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<Dairy> mAlbumList = new ArrayList<>();
    private int mItemLayout;
    Change mChange; // mAppleBananaCarrot 이런식으로 단어마다 첫번째를 대문자로


//    .setOnClickListener(new View.OnClickListener(){
//        public void onClick(View v){
//            Intent intent = new Intent();
//        }
//    });




    public MyRecyclerAdapter(){
    }

    /**
     * @see MyRecyclerAdapter onCreateViewHolder
     *  Layout Id 값을 Adapter 를 생성할 때 파라메터 값으로 전달하기보다는 메소드안에서 직접 id값을 넣어주는게
     *  어떤 레이아웃을 사용하였는지 알아보기 쉬움
     */
    public MyRecyclerAdapter(List<Dairy> items){
        // this 를 생략해도 변수명 앞에 m 을 붙였기때문에 멤버변수인걸 한눈에 알수있음
        mAlbumList = items;
    }



   public MyRecyclerAdapter(List<Dairy> items , int mItemLayout){
        this.mAlbumList = items;
//        this.mItemLayout = mItemLayout;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(mItemLayout,viewGroup,false);
        // Layout Id 값을 Adapter 를 생성할 때 파라메터 값으로 전달하기보다는 메소드안에서 직접 id값을 넣어주는게
        // 어떤 레이아웃을 사용하였는지 알아보기 쉬움
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_album, viewGroup,false);
        return new ViewHolder(view);
    }


    @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Dairy item = getItem(position);
        viewHolder.textTitle.setText(item.mDate);

        viewHolder.textArtist.setText(item.mContent);
        viewHolder.itemView.setTag(position+"");
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mChange.click(position);
//              // 인터페이스 연결
//            }});


    }

    public void setList(List<Dairy> arrayList){
        mAlbumList=arrayList;
    }

    Dairy getItem(int position){
        return mAlbumList.size()>position ? mAlbumList.get(position) : new Dairy();
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    /**
     * @param change 메인액티비티에서 만든 인터페이스를 받아옴
     */
    public void setInterface(Change change) {
        mChange = change;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        public ImageView img;
        public TextView textArtist;
        public TextView textTitle;

        public ViewHolder(View itemView){
            super(itemView);

 //           itemView.setOnClickListener(this);
            img = (ImageView) itemView.findViewById(R.id.imgProfile);
            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
            textArtist = (TextView) itemView.findViewById(R.id.textArtist);
        }

        public void onClick(View v){
            System.out.println(getAdapterPosition());
  //         Intent intent=new Intent(v.getContext(),NewPage.class);
   //         v.getContext().startActivity(intent);
        }


    }
}

