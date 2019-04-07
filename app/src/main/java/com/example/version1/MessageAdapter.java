package com.example.version1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    //    private Context mContext;
    private List<MessageInformation>itemList;
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
        //加载消息通知的布局
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.messageitems,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //得到消息通知，显示出来
        MessageInformation messageinformation =itemList.get(i);
        viewHolder.message.setText(messageinformation.getMessage());
        viewHolder.messageTime.setText(messageinformation.getMessageTime());
//     viewHolder.message.setText(information.getMessage());
//     viewHolder.messageTime.setText(information.getMessageTime());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public MessageAdapter(List<MessageInformation>itemList){
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
//        TextView bookName;
//        TextView lentTime;
                 TextView message;
         TextView messageTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            bookName=(TextView) itemView.findViewById(R.id.textView2);
//            lentTime=(TextView)itemView.findViewById(R.id.textView3);
           message=(TextView)itemView.findViewById(R.id.message);
           messageTime=(TextView)itemView.findViewById(R.id.messageTime);
        }
    }
}
