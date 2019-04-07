package com.example.version1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class LentAdapter extends RecyclerView.Adapter<LentAdapter.ViewHolder> {
//    private Context mContext;
   private List<LentInformation>itemList;
//
//    private ViewHolder getViewHolderByViewType(int viewType) {
//        View view1= View.inflate(mContext,R.layout.items,null);
//        View view2=View.inflate(mContext,R.layout.messageitems,null);
//        ViewHolder holder = null;
//        switch (viewType) {
//            case 1:
//                holder = new ViewHolder(view1);
//                break;
//            case 2:
//                holder = new ViewHolder(view2);
//                break;
//        }
//        return holder;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //加载借阅信息布局
      View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //显示借阅信息
     LentInformation lentinformation=itemList.get(i);
     viewHolder.bookName.setText(lentinformation.getBookName());
     viewHolder.lentTime.setText(lentinformation.getLentTime());
//     viewHolder.message.setText(information.getMessage());
//     viewHolder.messageTime.setText(information.getMessageTime());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public LentAdapter(List<LentInformation>itemList){
       this.itemList= itemList;

    }

//    @Override
//    public int getItemViewType(int position) {
//    if(position==1){
//        return 1;
//    }else if(position==2){
//        return 2;
//    }else{
//        return 0;
//    }
//
//    }

    static class ViewHolder extends RecyclerView.ViewHolder{
         TextView bookName;
         TextView lentTime;
//         TextView message;
//         TextView messageTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           bookName=(TextView) itemView.findViewById(R.id.bookName);
           lentTime=(TextView)itemView.findViewById(R.id.lentTime);
//           message=(TextView)itemView.findViewById(R.id.message);
//           messageTime=(TextView)itemView.findViewById(R.id.messageTime);
        }
    }
}
