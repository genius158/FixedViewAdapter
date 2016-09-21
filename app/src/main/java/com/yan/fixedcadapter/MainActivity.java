package com.yan.fixedcadapter;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.yan.mylibrary.FixedViewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<DataBean> dataBeens;
    FixedViewsAdapter<DataBean> commonFixedViewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(view);
        fixedFieldInit(view.findViewById(R.id.root));

        Button btnHide = (Button) findViewById(R.id.btn_hide);
        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBeens.remove(dataBeens.size() - 1);
                commonFixedViewsAdapter.notifyDataChange();
            }
        });
        Button btnShow = (Button) findViewById(R.id.btn_show);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBeens.add(new DataBean("bbbb", "bbbb", "bbbbb", "bbbb", BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
                commonFixedViewsAdapter.notifyDataChange();
            }
        });


        commonFixedViewsAdapter.setOnAnimationFinish(new FixedViewsAdapter.OnAnimationFinish() {
            @Override
            public void onFinish(int animationType) {
                if (animationType == FixedViewsAdapter.ANIMATIONFINISH_SHOW) {
                    Log.i("ANIMATIONFINISH_SHOW", "ANIMATIONFINISH_SHOWANIMATIONFINISH_SHOWANIMATIONFINISH_SHOW");
                } else {
                    Log.i("ANIMATIONFINISH_HIDE", "ANIMATIONFINISH_HIDEANIMATIONFINISH_HIDEANIMATIONFINISH_HIDE");
                }
            }
        });
    }

    private void fixedFieldInit(View itemMainView) {
        dataBeens = new ArrayList<>();
        dataBeens.add(new DataBean("aaaa", "aaaa", "aaaaa", "aaaa", BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
        dataBeens.add(new DataBean("bbbb", "bbbb", "bbbbb", "bbbb", BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
        dataBeens.add(new DataBean("cccc", "cccc", "cccc", "cccc", BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
        dataBeens.add(new DataBean("dddd", "dddd", "dddd", "ddddd", BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));

        commonFixedViewsAdapter = new FixedViewsAdapter<DataBean>(FixedViewsAdapter.TYPE_GIRED, 2, 4, itemMainView, dataBeens,
                R.id.item_img,
                R.id.item_title,
                R.id.user_name,
                R.id.seed_time
        ) {
            @Override
            public void onBindViewHodler(View[] view, DataBean dataBean) {
                setImage(view[0], dataBean.getBitmap());
                setText(view[1], dataBean.getTitle());
                setText(view[2], dataBean.getContent());
                setText(view[3], dataBean.getName());
            }
        };

    }

}
