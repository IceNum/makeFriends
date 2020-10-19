package com.people.loveme.ui.fragment.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.adapter.recyclerview.GcAdapter;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.GcListBean;
import com.people.loveme.bean.TopicBean;
import com.people.loveme.http.BaseCallback;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.LazyFragment;
import com.people.loveme.utils.FormatUtil;
import com.people.loveme.utils.ScreenUtil;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.AgeSectionDialog;
import com.people.loveme.view.CityChooseDialog;
import com.people.loveme.view.GcPopupWindow;
import com.people.loveme.view.ReasonDialog;
import com.people.loveme.view.ReportDialog;
import com.people.loveme.view.SingleChooseDialog;
import com.people.loveme.view.TagPopupWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/11 0011.
 * 广场列表
 */

public class GcListFra extends LazyFragment implements View.OnClickListener, TagPopupWindow.onChooseListener {
    @BindView(R.id.xRecyclerView)
    XRecyclerView mRecyclerView;
    Unbinder unbinder;

    GcAdapter adapter;
    @BindView(R.id.fl_topic)
    FrameLayout flTopic;
    @BindView(R.id.fl_tiaojian)
    FrameLayout flTiaojian;
    @BindView(R.id.tv_topic)
    TextView tvTopic;
    Unbinder unbinder1;
    private List<GcListBean.DataBean> list;
    private List<TopicBean.DataBean> topicList;
    private List<String> topic, conditions, sexs;
    private int page = 1, doPeoplePosition,topicPosition = 0;
    private boolean hasMore = true;
    private TagPopupWindow topicPop, conditionPop;
    private GcPopupWindow gcPopupWindow;
    private String sexCondition, ageCondition, cityCondition, scoreCondition;


    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_gc_list, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        topicList = new ArrayList<>();
        topic = new ArrayList<>();
        conditions = new ArrayList<>();
        conditions.add(getString(R.string.gender));
        conditions.add(getString(R.string.wo_age));
        conditions.add(getString(R.string.wo_adress));
        conditions.add(getString(R.string.wo_xinyong));

        sexs = new ArrayList<>();
        sexs.add(getString(R.string.wo_buxian));
        sexs.add(getString(R.string.male));
        sexs.add(getString(R.string.female));

        adapter = new GcAdapter(getContext(), list);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData();
                mRecyclerView.setNoMore(false);
            }

            @Override
            public void onLoadMore() {
                if (!hasMore) {
                    mRecyclerView.setNoMore(true);
                    return;
                }
                page++;
                getData();
            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.refresh();

        adapter.setPeopleClickListener(new GcAdapter.PeopleClickListener() {
            @Override
            public void OnItemClick(int position) {
                ReportDialog reportDialog = new ReportDialog(getContext(), new ReportDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        doPeoplePosition = position;
                        showReport(list.get(position).getUid());
                    }
                });
                reportDialog.show();
            }
        });
        getTopics();
        flTopic.setOnClickListener(this);
        flTiaojian.setOnClickListener(this);
    }


    private String topicId, region, stature, creditScore, city;

    /**
     * 获取推荐列表
     */
    private void getData() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SharePrefUtil.getString(getContext(), AppConsts.UID, ""));
        if (null != cityCondition)
            params.put("city", cityCondition);//城市
        if (null != topicId)
            params.put("cateid", topicId);//话题id
        if (null != ageCondition)
            params.put("age", ageCondition);//身高 18-19
        if (null != scoreCondition)
            params.put("score", scoreCondition);//信用分;
        if (null != sexCondition)
            params.put("sex", sexCondition);//性别; 1男2女
//        params.put("page", page + "");
        OkHttpHelper.getInstance().post(getContext(), Url.guangchang, params, new BaseCallback<GcListBean>() {
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(Request request, Exception e) {
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, GcListBean bean) {
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
                if (null != bean.getData()) {
                    list.clear();
                    list.addAll(bean.getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
            }
        });
    }


    /**
     * 获取话题列表
     */
    private void getTopics() {
        Map<String, String> params = new HashMap<>();
        OkHttpHelper.getInstance().post(getContext(), Url.getTopic, params, new SpotsCallBack<TopicBean>(getContext()) {
            @Override
            public void onSuccess(Response response, TopicBean topicBean) {
                topic.add(getString(R.string.wo_all_huati));
                if (null != topicBean.getData())
                    topicList.addAll(topicBean.getData());

                if (topicList.size() > 0) {
                    for (int i = 0; i < topicList.size(); i++) {
                        topic.add(topicList.get(i).getName());
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    /**
     * 举报
     */
    private void report(String type, String uid) {
        Map<String, String> params = new HashMap<>();
        params.put("jubao_uid", SharePrefUtil.getString(getContext(), AppConsts.UID, ""));
        params.put("be_jubao_uid", uid);
        params.put("content", type);
        OkHttpHelper.getInstance().post(getContext(), Url.report, params, new SpotsCallBack<BaseBean>(getContext()) {

            @Override
            public void onSuccess(Response response, BaseBean bean) {
                ToastUtil.show(bean.getMsg());
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    private void showReport(final String uid) {
        String[] list = getResources().getStringArray(R.array.jblx);
        final List<String> reason = Arrays.asList(list);
        ReasonDialog reasonDialog = new ReasonDialog(getContext(), getString(R.string.wo_select_jubao_type), reason, new ReasonDialog.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                report(reason.get(position), uid);
            }
        });
        reasonDialog.show();
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_topic:
                if (topic.size() <= 0) {
                    ToastUtil.show(getString(R.string.wo_no_huati));
                    return;
                }

                if (null == topicPop) {
                    topicPop = new TagPopupWindow(getContext());
                    topicPop.setHeight(ScreenUtil.getScreenHeight(getContext()) - FormatUtil.pixOfDip(130));
                    topicPop.setOnChooseListener(this);
                }
                topicPop.setOnChooseListener(new TagPopupWindow.onChooseListener() {
                    @Override
                    public void onChoose(int position) {
                        if (position == TagPopupWindow.NOCHOOSE)
                            return;

                        if (position == 0)
                            topicId = null;
                        else
                            topicId = topicList.get(position - 1).getId();

                        topicPosition = position;

                        tvTopic.setText(topic.get(position));
                        mRecyclerView.refresh();
                    }
                });
                topicPop.setData(topic, topicPosition);

                topicPop.showAsDropDown(flTopic);
                break;
            case R.id.fl_tiaojian:
                if (null == gcPopupWindow) {
                    gcPopupWindow = new GcPopupWindow(getContext(), this);
                }
                gcPopupWindow.showAsDropDown(flTiaojian);
                break;
            case R.id.ll_sex:
                SingleChooseDialog gcqkChooseDialog = new SingleChooseDialog(getContext(), getString(R.string.gender), sexs, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        if (position != 0)
                            sexCondition = position + "";
                        else
                            sexCondition = null;

                        gcPopupWindow.setSex(sexs.get(position));

                        mRecyclerView.refresh();
                    }
                });
                gcqkChooseDialog.show();
                break;
            case R.id.ll_nl:
                AgeSectionDialog ageSectionDialog = new AgeSectionDialog(getContext(), getString(R.string.wo_age), new AgeSectionDialog.OnItemClick() {
                    @Override
                    public void onItemClick(String age) {

                        if (age.startsWith(getString(R.string.wo_buxian)) && age.endsWith(getString(R.string.wo_buxian))) {
                            ageCondition = null;
                        } else {
                            if (age.startsWith(getString(R.string.wo_buxian)))
                                ageCondition = age.replace(getString(R.string.wo_buxian), "0");
                            else if (age.endsWith(getString(R.string.wo_buxian)))
                                ageCondition = age.replace(getString(R.string.wo_buxian), "100");
                            else
                                ageCondition = age;
                        }

                        if (age.startsWith(getString(R.string.wo_buxian))) {
                            gcPopupWindow.setAge(age.split("-")[1] + "以下");
                        } else if (age.endsWith(getString(R.string.wo_buxian))) {
                            gcPopupWindow.setAge(age.split("-")[0] + "以上");
                        } else
                            gcPopupWindow.setAge(age);

                        mRecyclerView.refresh();
                    }
                });
                ageSectionDialog.show();
                break;
            case R.id.ll_jzz:
                CityChooseDialog cityChooseDialog = new CityChooseDialog(getContext(), getString(R.string.wo_adress), new CityChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(String province, String city) {
                        gcPopupWindow.setJzz(city);
                        cityCondition = city;
                        mRecyclerView.refresh();
                    }
                },true);
                cityChooseDialog.show();
                break;
            case R.id.ll_xy:
                final List<String> scores = Arrays.asList(getResources().getStringArray(R.array.score));
                SingleChooseDialog scoreChooseDialog = new SingleChooseDialog(getContext(), getString(R.string.wo_xinyongfen), scores, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        switch (position) {
                            case 0:
                                scoreCondition = 0 + "";
                                break;
                            case 1:
                                scoreCondition = 60 + "";
                                break;
                            case 2:
                                scoreCondition = 70 + "";
                                break;
                            case 3:
                                scoreCondition = 80 + "";
                                break;
                            case 4:
                                scoreCondition = 100 + "";
                                break;
                        }
                        gcPopupWindow.setXy(scores.get(position));

                        mRecyclerView.refresh();
                    }
                });
                scoreChooseDialog.show();
                break;
            case R.id.tv_sure:
                gcPopupWindow.dismiss();
                mRecyclerView.refresh();
                break;
            case R.id.tv_clear:
                cityCondition = null;
                ageCondition = null;
                scoreCondition = null;
                sexCondition = null;
                gcPopupWindow.setAge(getString(R.string.wo_buxian));
                gcPopupWindow.setXy(getString(R.string.wo_buxian));
                gcPopupWindow.setJzz(getString(R.string.wo_buxian));
                gcPopupWindow.setSex(getString(R.string.wo_buxian));
                gcPopupWindow.dismiss();
                mRecyclerView.refresh();
                break;
        }
    }

    @Override
    public void onChoose(int position) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
