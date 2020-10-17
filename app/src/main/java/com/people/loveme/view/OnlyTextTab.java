package com.people.loveme.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.people.loveme.R;

import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;

public class OnlyTextTab extends BaseTabItem {

    private TextView tv_title;
    private View view_line;
    private LinearLayout ll_view;
    private Context context;

    public OnlyTextTab(Context context, String title) {
        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.item_only_text, this, true);
        ll_view = findViewById(R.id.ll_view);
        tv_title = findViewById(R.id.tv_title);
        view_line = findViewById(R.id.view_line);

        tv_title.setText(title);
    }

    @Override
    public void setChecked(boolean checked) {
        ll_view.setBackgroundColor(checked ? ContextCompat.getColor(context, R.color.white) : ContextCompat.getColor(context, R.color.space));
        view_line.setVisibility(checked ? VISIBLE : INVISIBLE);
        tv_title.setTextColor(checked ? ContextCompat.getColor(context, R.color.main_color) : ContextCompat.getColor(context, R.color.txt_lv4));
    }

    @Override
    public void setMessageNumber(int number) {

    }

    @Override
    public void setHasMessage(boolean hasMessage) {
    }

    @Override
    public String getTitle() {
        return tv_title.getText().toString();
    }
}
