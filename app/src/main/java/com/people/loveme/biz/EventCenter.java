package com.people.loveme.biz;

import android.os.Handler;

import com.people.loveme.utils.ListUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventCenter {


    public interface EventListener {

        public void onEvent(HcbEvent e);
    }

    private void sendEvtWithKey(final EventType evt,
                                final String key, final String value) {
        final Map<String, Object> param = new HashMap<String, Object>(1);
        param.put(key, value);
        send(new HcbEvent(evt, param));
    }

    public void sendType(final EventType evt) {
        send(new HcbEvent(evt));
    }

    public void evtLogin() {
        send(new HcbEvent(EventType.EVT_LOGIN));
    }

    public void evtLogout() {
        send(new HcbEvent(EventType.EVT_LOGOUT));
    }

    public static class HcbEvent {

        public EventType type;
        public Map<String, Object> params;

        public HcbEvent(EventType type) {
            this.type = type;
        }

        public HcbEvent(EventType type, Map<String, Object> params) {
            this.type = type;
            this.params = params;
        }

    }

    public enum EventType {

        EVT_LOGIN, // 登录
        EVT_LOGOUT, // 注销
        EVT_RGIST,//注册成功
        EVT_EDITEINFO,//修改个人信息成功
        EVT_TOHOME,//回到主页
        EVT_TOSHOPCAR,//回到购物车
        EVT_RZZT,//认证状态刷新
        EVT_PAYSUCCESS,//支付成功
        EVT_PUSH,//发布动态
        EVT_EDITEZYTJ,//修改征友条件
        EVT_MESSAGEREAD,//消息已读
        EVT_CHANGENUM,//改变消息已读数量
        EVT_TOALYPAY,//调起支付宝身份认证
        EVT_SENDGIFT, // 送礼物
    }

    private final Handler uiHandler;
    private final Map<EventType, List<EventListener>> center;

    public EventCenter(final Handler handler) {
        uiHandler = handler;
        center = new HashMap<EventType, List<EventListener>>();
    }

    public void registEvent(final EventListener l, final EventType e) {
        List<EventListener> list = center.get(e);
        if (null == list) {
            list = new ArrayList<EventListener>();
            list.add(l);
            center.put(e, list);
        } else if (!list.contains(l)) {
            list.add(l);
        }
    }

    public void unregistEvent(final EventListener l, final EventType e) {
        final List<EventListener> list = center.get(e);
        if (null != list) {
            list.remove(l);
        }
    }

    public void send(final HcbEvent e) {
        final List<EventListener> list = getCopy(e.type);
        if (ListUtil.isEmpty(list)) {
            return;
        }
        uiHandler.post(new Runnable() {

            @Override
            public void run() {
                for (int i = list.size() - 1; i >= 0; i--) {
                    try {
                        list.get(i).onEvent(e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private List<EventListener> getCopy(final EventType et) {
        final List<EventListener> origin = center.get(et);
        if (null == origin || origin.isEmpty()) {
            return null;
        }
        return new ArrayList<EventListener>() {

            private static final long serialVersionUID = 1L;

            {
                addAll(center.get(et));
            }
        };
    }

}
