package com.people.loveme.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.people.loveme.R;


/**
 * Created by Administrator on 2017/12/16.
 */

public class ActionDialog extends Dialog {

    Context context;//上下文
    private TextView dialog_title,dialog_left,dialog_right;

    public TextView getTitleText(){
        return dialog_title;
    }

    public TextView getLeftText(){
        return dialog_left;
    }

    public TextView getRightText(){
        return dialog_right;
    }

    private OnActionClickListener onActionClickListener;
    public interface OnActionClickListener{
        void onLeftClick();
        void onRightClick();
    }
    public void setOnActionClickListener(OnActionClickListener onActionClickListener){
        this.onActionClickListener = onActionClickListener;
    }


    public ActionDialog(Context context, String title, String left, String right) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_action, null);
        dialog_title = (TextView) view.findViewById(R.id.dialog_action_tip);
        dialog_left = (TextView) view.findViewById(R.id.dialog_action_cancle);
        dialog_right = (TextView) view.findViewById(R.id.dialog_action_confirm);

        dialog_title.setText(title);
        dialog_left.setText(left);
        dialog_right.setText(right);

        dialog_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onActionClickListener!=null){
                    onActionClickListener.onLeftClick();
                }
                dismiss();
            }
        });

        dialog_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onActionClickListener!=null){
                    onActionClickListener.onRightClick();
                }
                dismiss();
            }
        });

        this.setContentView(view);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        //window.setWindowAnimations(R.style.actionDialog);
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }


    public ActionDialog(Context context, String title) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_action, null);
        TextView dialog_title = (TextView) view.findViewById(R.id.dialog_action_tip);
        TextView dialog_left = (TextView) view.findViewById(R.id.dialog_action_cancle);
        TextView dialog_right = (TextView) view.findViewById(R.id.dialog_action_confirm);

        dialog_title.setText(title);
        dialog_left.setText("取消");
        dialog_right.setText(context.getString(R.string.wo_queding));

        dialog_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onActionClickListener!=null){
                    onActionClickListener.onLeftClick();
                }
                dismiss();
            }
        });

        dialog_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onActionClickListener!=null){
                    onActionClickListener.onRightClick();
                }
                dismiss();
            }
        });

        this.setContentView(view);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

}
