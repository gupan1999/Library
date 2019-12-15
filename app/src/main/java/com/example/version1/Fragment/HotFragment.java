package com.example.version1.Fragment;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.version1.Model.Hot;
import com.example.version1.R;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


public class HotFragment extends Fragment {
    private RecyclerView hotitemre;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private List<Hot> hotList = new ArrayList<Hot>();
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hot, container, false);
        super.onCreate(savedInstanceState);

        textView = root.findViewById(R.id.textView11);
        hotitemre = root.findViewById(R.id.hotitemre);
        final Hot hot = new Hot("你认为《三体》中哪个文明最厉害", "质子文明。\n 人类和归零者属于同一个层次，都在一个果壳里苟延残喘。三体人制作智子时发现了质子文明，触及果壳外的世界。\n 地球往事中，三体人在自己的世界被毁灭后，迅速反应，利用太阳光反击，已经超越了一般意义上的神级文明", R.mipmap.hot1);
        Hot hot1 = new Hot("有哪些有趣的外文书籍值得推荐？", "林语堂的《京华烟云》，英文原版《A Moment in Peking》\n 《哈利波特》值得一读的英文童话书\n《Sweetbitter》“你要像喜欢甜一样喜欢苦”，适合大学英语四级水平", R.mipmap.hot3);
        Hot hot2 = new Hot("历史中有哪些听起来轻描淡写但读过之后感到十分难过的句子?","中国制治必须朝廷操利权。利不足操朝廷之权，然后可冀效诚于商贾；使富商大贾视官宦如帝天，偶一盼睐便可以为至荣极宠，斯匍匐以献其财力而惟恐不纳矣。\n  \n ——清，刘锡鸿，《论时事书偶笔》\n \n  这段话之所以让人觉得难过是因为我发现一百多年过去了，知乎很多人的观点和刘锡鸿这个历史书上最反动最保守的晚清官僚的观点一摸一样。\n \n 有些人别看生活在21世纪，可他们心中始终留着一根辫子。",R.mipmap.hot4);
        hotList.add(hot);
        hotList.add(hot1);
        hotList.add(hot2);

        baseRecyclerAdapter = new BaseRecyclerAdapter<Hot>(getContext(), R.layout.hotitem, hotList) {
            @Override
            public void convert(BaseViewHolder holder, Hot hot) {
                holder.setText(R.id.textView12, hot.getBookname());
                holder.setText(R.id.textView15, hot.getDetails());
                holder.setImageResource(R.id.imageView11, hot.getImageid());
            }

            @Override
            public void setting(BaseViewHolder holder) {

            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        hotitemre.setLayoutManager(layoutManager);
        hotitemre.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL)); //item间的分割线
        hotitemre.setAdapter(baseRecyclerAdapter);
        return root;
    }
}