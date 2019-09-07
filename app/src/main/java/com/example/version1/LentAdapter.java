package com.example.version1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.version1.greendao.LentInformation;

import java.util.List;

public class LentAdapter extends RecyclerView.Adapter<LentAdapter.ViewHolder> {

   private List<LentInformation>itemList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //加载借阅信息布局
      View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //显示借阅信息
     LentInformation lentinformation=itemList.get(i);
     viewHolder.bookName.setText(lentinformation.getBookName());
     viewHolder.lentTime.setText(lentinformation.getLentTime());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public LentAdapter(List<LentInformation>itemList){
       this.itemList= itemList;

    }


    static class ViewHolder extends RecyclerView.ViewHolder{
         TextView bookName;
         TextView lentTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           bookName=itemView.findViewById(R.id.bookName);
           lentTime=itemView.findViewById(R.id.lentTime);
        }
    }
}
