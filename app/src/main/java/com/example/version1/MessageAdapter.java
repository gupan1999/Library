package com.example.version1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<MessageInformation>itemList;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //加载消息通知的布局
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.messageitems,viewGroup,false);
        return new ViewHolder(view);
    }
 
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //得到消息通知，显示出来
        MessageInformation messageinformation =itemList.get(i);
        viewHolder.message.setText(messageinformation.getMessage());
        viewHolder.messageTime.setText(messageinformation.getMessageTime());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public MessageAdapter(List<MessageInformation>itemList){
        this.itemList= itemList;

    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView message;
        TextView messageTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           message=itemView.findViewById(R.id.message);
           messageTime=itemView.findViewById(R.id.messageTime);
        }
    }
}
