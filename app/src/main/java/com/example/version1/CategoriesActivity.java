package com.example.version1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class CategoriesActivity extends AppCompatActivity {
//    private List<String> itemList=new ArrayList<>();
//    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_categories);
//        initItems();
        ImageButton Return = (ImageButton) findViewById(R.id.Return);
        TextView t=(TextView)findViewById(R.id.title) ;   //title控件注册
        t.setText("分类");  //title名
//         recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
//
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//线性布局管理Recyclerview
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
//        ItemAdapter adapter=new ItemAdapter(itemList);
//        recyclerView.setAdapter(adapter);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            CategoriesActivity.this.finish();
            }
        });
    }
//    public void initItems(){
//        String item1="电子书刊";
//        String  item2="学位论文";
//        String item3="馆藏书籍";
//        String item4="多媒体";
//        String item5="期刊会议";
//        String item6="有声书";
//        itemList.add(item1);
//        itemList.add(item2);
//        itemList.add(item3);
//        itemList.add(item4);
//        itemList.add(item5);
//        itemList.add(item6);
//    }
}
