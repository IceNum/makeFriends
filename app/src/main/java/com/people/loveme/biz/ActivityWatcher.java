package com.people.loveme.biz;

import android.support.v4.app.FragmentActivity;

public class ActivityWatcher {
    private static FragmentActivity curAct;

    public static void setCurAct(FragmentActivity act) {
        curAct = act;
    }

    public static FragmentActivity curAct() {
        return curAct;
    }
}
