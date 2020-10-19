package com.people.loveme.ui.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aesion.snapupdowntimerview.SnapUpCountDownTimerView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.people.loveme.R;
import com.people.loveme.adapter.recyclerview.HomeRecommendAdapter;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.HomeBannerBean;
import com.people.loveme.bean.HomeRecommendBean;
import com.people.loveme.bean.HomeRefreshTimeBean;
import com.people.loveme.bean.HomeTjBean;
import com.people.loveme.bean.UserZytjBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.BaseCallback;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.WebFra;
import com.people.loveme.ui.fragment.other.OtherHomePageFra;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.PicassoUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.TimeUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.AgeSectionDialog;
import com.people.loveme.view.CityChooseDialog;
import com.people.loveme.view.HomePopupWindow;
import com.people.loveme.view.IncomeSectionDialog;
import com.people.loveme.view.PeopleDoDialog;
import com.people.loveme.view.ReasonDialog;
import com.people.loveme.view.SgSectionDialog;
import com.people.loveme.view.SingleChooseDialog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/5 0005.
 * 首页推荐
 */

public class HomeRecommendFra extends CachableFrg implements View.OnClickListener, EventCenter.EventListener {
    Unbinder unbinder;
    ImageView ivMore;
    BGABanner banner;
    SnapUpCountDownTimerView rushBuyCountDownTimerView;
    @BindView(R.id.xRecyclerView)
    XRecyclerView mRecyclerView;
    HomeRecommendAdapter adapter;
    private List<HomeRecommendBean.DataBean> list;
    private List<HomeBannerBean.DataBean> banners;
    private String refrshTime;
    private String uid, dtid;//操作人动态Id
    private String ageShow, heightShow, incomeShow, eduShow, cityShow, carShow, houseShow, lastTimeShow, realNameShow, scoreShow;
    private String ageCondition, heightCondition, incomeCondition, eduCondition, nationCondition,
            carCondition, placeConditon, loginTimeCondition, cityCondition, scoreCondition, houseCondition, realNameCondition;


    private BGABanner.Adapter bannerAdapter = new BGABanner.Adapter() {
        @Override
        public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
            PicassoUtil.setImag(getContext(), ((HomeBannerBean.DataBean) model).getImage(), (ImageView) view);
        }
    };
    private TagFlowLayout flTags;
    private List<String> tags;
    private int selectPosition = -1;
    List<String> ysr;
    private HomeTjBean homeTjBean;

    @Override
    protected int rootLayout() {
        return R.layout.fra_home_recommend;
    }

    @Override
    protected void initView() {
        eventCenter.registEvent(this, EventCenter.EventType.EVT_EDITEZYTJ);
        ysr = Arrays.asList(getResources().getStringArray(R.array.sr));
        View head = LayoutInflater.from(getContext()).inflate(R.layout.layout_head_home_recommend, null);
        flTags = ((TagFlowLayout) head.findViewById(R.id.fl_tags));
        ivMore = ((ImageView) head.findViewById(R.id.iv_more));
        banner = ((BGABanner) head.findViewById(R.id.banner));
        rushBuyCountDownTimerView = ((SnapUpCountDownTimerView) head.findViewById(R.id.rushBuyCountDownTimerView));
        mRecyclerView.addHeaderView(head);
        tags = new ArrayList<>();

        initData();
    }

    protected void initData() {
        list = new ArrayList<>();
        banners = new ArrayList<>();
        adapter = new HomeRecommendAdapter(getContext(), list);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setAdapter(adapter);

        banner.setAdapter(bannerAdapter);
        banner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, @Nullable Object model, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", ((HomeBannerBean.DataBean) model).getUrl());
                ActivitySwitcher.startFragment(getActivity(), WebFra.class, bundle);
            }
        });


        rushBuyCountDownTimerView.setOnFinishListener(new SnapUpCountDownTimerView.OnFinishListener() {
            @Override
            public void onfinish() {
                getData();
                formatTime(Integer.parseInt(refrshTime));
            }
        });
//        getData();
        getBanner();
        getRefeshTime();
        getHomeTj();

        ivMore.setOnClickListener(this);
        adapter.setPeopleClickListener(new HomeRecommendAdapter.PeopleClickListener() {
            @Override
            public void OnItemClick(int position) {
                selectPosition = position;
                if (!ListUtil.isEmpty(list.get(position).getDongtai()))
                    dtid = list.get(position).getDongtai().get(0).getId() + "";
                uid = list.get(position).getId();
                PeopleDoDialog peopleDoDialog = new PeopleDoDialog(getContext(), new PeopleDoDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        if (position == 0)
                            showNoInterest();
                        else
                            showReport();
                    }
                });
                peopleDoDialog.show();
            }
        });

        adapter.setOnItemClickListener(new HomeRecommendAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("uid", list.get(position).getId());
                ActivitySwitcher.startFragment(getActivity(), OtherHomePageFra.class, bundle);
            }
        });
    }

    private void showNoInterest() {
        String[] list = getResources().getStringArray(R.array.bgxq);
        final List<String> reason = Arrays.asList(list);
        ReasonDialog reasonDialog = new ReasonDialog(getContext(), "选择不感兴趣的理由", reason, new ReasonDialog.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                noInterest();

            }
        });
        reasonDialog.show();
    }

    private void showReport() {
        String[] list = getResources().getStringArray(R.array.jblx);
        final List<String> reason = Arrays.asList(list);
        ReasonDialog reasonDialog = new ReasonDialog(getContext(), "选择举报类型", reason, new ReasonDialog.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                report(reason.get(position));
            }
        });
        reasonDialog.show();
    }


    /**
     * 获取首页筛选 条件
     */
    private void getHomeTj() {
        Map<String, String> params = new HashMap<>();
        OkHttpHelper.getInstance().post(getContext(), Url.homeTj, params, new SpotsCallBack<HomeTjBean>(getContext()) {
            @Override
            public void onSuccess(Response response, HomeTjBean bean) {
                homeTjBean = bean;
                homePopupWindow = new HomePopupWindow(getContext(), homeTjBean, HomeRecommendFra.this);
                homePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        setSelectTags();
                    }
                });

                getUserZyInfo();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 获取推荐列表
     */
    private void getData() {
        tags.clear();
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        if (!StringUtil.isEmpty(ageCondition)) {
            params.put("age", ageCondition);
            String showAge = "";
            if (ageCondition.startsWith("0"))
                showAge = ageCondition.replace("0", getString(R.string.wo_buxian));
            else if (ageCondition.endsWith(getString(R.string.wo_buxian)))
                showAge = ageCondition.replace("60", getString(R.string.wo_buxian));
            tags.add(getString(R.string.wo_age) + "：" + showAge);
        } else {
            tags.add(getString(R.string.wo_age) + "：" +  getString(R.string.wo_buxian));
        }

        if (!StringUtil.isEmpty(incomeCondition)) {
            params.put("income", incomeCondition);
            tags.add(incomeShow);
        } else {
            tags.add(getString(R.string.wo_yueshou) + "：" +  getString(R.string.wo_buxian));
        }

        if (!StringUtil.isEmpty(eduCondition)) {
            params.put("edu", eduCondition);
        }
        if (!StringUtil.isEmpty(cityCondition)) {
            params.put("city", cityCondition);
        }
        if (!StringUtil.isEmpty(nationCondition)) {
            params.put("nation", nationCondition);
        }
        if (!StringUtil.isEmpty(carCondition) && !carCondition.equals("0")) {
            params.put("car", carCondition);
        }
        if (!StringUtil.isEmpty(placeConditon)) {
            params.put("na_place", placeConditon);
        }
        if (!StringUtil.isEmpty(loginTimeCondition)) {
            params.put("logintime", loginTimeCondition);
        }
        if (!StringUtil.isEmpty(scoreCondition)) {
            params.put("score", scoreCondition);
        }
        if (!StringUtil.isEmpty(realNameCondition) && !realNameCondition.equals("0")) {
            params.put("is_relaname", realNameCondition);
        }
        if (!StringUtil.isEmpty(houseCondition) && !houseCondition.equals("0")) {
            params.put("is_buyhome", houseCondition);
        }
        if (!StringUtil.isEmpty(heightCondition)) {
            params.put("height", heightCondition);
            tags.add(getString(R.string.height) + "：" + heightCondition);
        } else {
            tags.add(getString(R.string.height) + "：" +  getString(R.string.wo_buxian));
        }
        setTag();

        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.homerecommend, params, new BaseCallback<HomeRecommendBean>() {
            @Override
            public void onBeforeRequest(Request request) {
            }

            @Override
            public void onFailure(Request request, Exception e) {
            }

            @Override
            public void onResponse(Response response) {
            }

            @Override
            public void onSuccess(Response response, HomeRecommendBean bean) {
                if (null != bean.getData()) {
                    list.clear();
                    list.addAll(bean.getData());
                    Collections.reverse(list);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    /**
     * 保存征友条件信息
     */
    private void save() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        if (null != ageCondition)
            params.put("age", ageCondition);//年龄
        else
            params.put("age", "");
        if (null != heightCondition)
            params.put("height", heightCondition);//身高
        else
            params.put("height", "");
        if (null != incomeCondition)
            params.put("income", incomeCondition);//收入
        else
            params.put("income", "");
        if (null != eduCondition)
            params.put("edu", eduCondition);//学历
        else
            params.put("edu", "");//年龄
        if (null != cityCondition)
            params.put("city", cityCondition);//城市
        else
            params.put("city", "");
        if (null != houseCondition)
            params.put("house", houseCondition);//是否有房
        else
            params.put("house", "");

        if (null != nationCondition)
            params.put("nation", nationCondition);//民族
        else
            params.put("nation", "");
        if (null != placeConditon)
            params.put("na_place", placeConditon);//籍贯
        else
            params.put("na_place", "");
        if (null != scoreCondition)
            params.put("score", scoreCondition);//信用分
        else
            params.put("score", "");

        if (null != loginTimeCondition)
            params.put("logintime", loginTimeCondition);//登陆时间
        else
            params.put("logintime", "");

        if (null != realNameCondition)
            params.put("is_relaname", realNameCondition);//是否实名
        else
            params.put("is_relaname", "");

        OkHttpHelper.getInstance().post(getContext(), Url.findTiaoJian, params, new SpotsCallBack<BaseBean>(getContext()) {

            @Override
            public void onSuccess(Response response, BaseBean bean) {
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 获取轮播数据
     */
    private void getBanner() {
        Map<String, String> params = new HashMap<>();
        OkHttpHelper.getInstance().post(getContext(), Url.homebanner, params, new SpotsCallBack<HomeBannerBean>(getContext()) {
            @Override
            public void onSuccess(Response response, HomeBannerBean bean) {
                if (!ListUtil.isEmpty(bean.getData())) {
                    banners.addAll(bean.getData());
                    banner.setData(banners, null);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 获取首页刷新时间
     */
    private void getRefeshTime() {
        Map<String, String> params = new HashMap<>();
        OkHttpHelper.getInstance().post(getContext(), Url.homeRefreshTime, params, new SpotsCallBack<HomeRefreshTimeBean>(getContext()) {
            @Override
            public void onSuccess(Response response, HomeRefreshTimeBean bean) {
                refrshTime = bean.getData();
                if (null != refrshTime) {
                    formatTime(Integer.parseInt(refrshTime));
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 获取用户征友条件
     */
    private void getUserZyInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.getFindTiaoJian, params, new SpotsCallBack<UserZytjBean>(getContext()) {

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                getData();
            }

            @Override
            public void onSuccess(Response response, UserZytjBean bean) {
                if (null != bean.getData()) {
                    if (!StringUtil.isEmpty(bean.getData().getAge())) {
                        ageCondition = bean.getData().getAge();
                        String showAge = "";
                        if (!StringUtil.isEmpty(ageCondition)) {
                            if (ageCondition.startsWith("0")) {
                                showAge = ageCondition.split("-")[1] + "以下";
                            } else if (ageCondition.endsWith("60"))
                                showAge = ageCondition.split("-")[0] + "以上";
                            else
                                showAge = bean.getData().getAge();
                        }
                        ageShow = showAge;
                    }

                    if (!StringUtil.isEmpty(bean.getData().getHeight())) {
                        heightCondition = bean.getData().getHeight();
                        if (heightCondition.startsWith(getString(R.string.wo_buxian)) && heightCondition.endsWith(getString(R.string.wo_buxian))) {
                            heightShow = null;
                        } else {
                            if (heightCondition.endsWith("210"))
                                heightShow = heightCondition.split("-")[0] + "以上";
                            else if (heightCondition.startsWith("150"))
                                heightShow = heightCondition.split("-")[1] + "以下";
                            else
                                heightShow = heightCondition;
                        }
                    }

                    if (!StringUtil.isEmpty(bean.getData().getIncome())) {
                        incomeCondition = bean.getData().getIncome();
                        if (bean.getData().getIncome().startsWith(getString(R.string.wo_buxian)) && bean.getData().getIncome().endsWith(getString(R.string.wo_buxian))) {
                            incomeShow = getString(R.string.wo_buxian);
                        } else {
                            if (bean.getData().getIncome().endsWith("100")) {
                                if (Integer.parseInt(bean.getData().getIncome().split("-")[0]) >= 10)
                                    incomeShow = Integer.parseInt(bean.getData().getIncome().split("-")[0]) / 10 + "万以上";
                                else
                                    incomeShow = bean.getData().getIncome().split("-")[0] + "千以上";
                            } else if (bean.getData().getIncome().startsWith("0")) {
                                if (Integer.parseInt(bean.getData().getIncome().split("-")[1]) >= 10)
                                    incomeShow = Integer.parseInt(bean.getData().getIncome().split("-")[1]) / 10 + "万以下";
                                else
                                    incomeShow = bean.getData().getIncome().split("-")[1] + "千以下";
                            } else {
                                String start = "2千", end = "5万";
                                if (Integer.parseInt(bean.getData().getIncome().split("-")[0]) >= 10)
                                    start = Integer.parseInt(bean.getData().getIncome().split("-")[0]) / 10 + "万";
                                else
                                    start = bean.getData().getIncome().split("-")[0] + "千";

                                if (Integer.parseInt(bean.getData().getIncome().split("-")[1]) >= 10)
                                    end = Integer.parseInt(bean.getData().getIncome().split("-")[1]) / 10 + "万";
                                else
                                    end = bean.getData().getIncome().split("-")[1] + "千";

                                incomeShow = start + "-" + end;
                            }

                        }
                    }

                    setTag();

                    if (!StringUtil.isEmpty(bean.getData().getEdu())) {
                        eduCondition = bean.getData().getEdu();
                        eduShow = bean.getData().getEdu() + "及以上";
                    }

                    if (!StringUtil.isEmpty(bean.getData().getCity())) {
                        cityCondition = bean.getData().getCity();
                        cityShow = bean.getData().getCity();
                    }

                    if (!StringUtil.isEmpty(bean.getData().getHouse())) {
                        houseCondition = bean.getData().getHouse();
                        if (houseCondition.equals("1"))
                            houseShow = getString(R.string.wo_youfang);

                    }

                    if (!StringUtil.isEmpty(bean.getData().getCar())) {
                        carCondition = bean.getData().getCar();
                        if (carCondition.equals("1"))
                            carShow = getString(R.string.wo_youche);
                    }

                    if (!StringUtil.isEmpty(bean.getData().getIs_relaname())) {
                        realNameCondition = bean.getData().getIs_relaname();
                        if (realNameCondition.equals("1"))
                            realNameShow = getString(R.string.wo_yishiming);

                    }

                    if (!StringUtil.isEmpty(bean.getData().getScore())) {
                        scoreShow = bean.getData().getScore();
                        scoreCondition = bean.getData().getScore();
                    }
                    if (!StringUtil.isEmpty(bean.getData().getNation())) {
                        nationCondition = bean.getData().getNation();
                    }


                    if (!StringUtil.isEmpty(bean.getData().getLogintime())) {
                        loginTimeCondition = bean.getData().getLogintime();
                        switch (bean.getData().getLogintime()) {
                            case "3600":
                                lastTimeShow = "一小时";
                                break;
                            case "86400":
                                lastTimeShow = "今天";
                                break;
                            case "259200":
                                lastTimeShow = "三天";
                                break;
                            default:
                                lastTimeShow = getString(R.string.wo_buxian);
                                break;
                        }
                    }
                    if (null != ageShow)
                        homePopupWindow.setAge(ageShow);
                    if (null != incomeShow)
                        homePopupWindow.setYsr(incomeShow);
                    if (null != eduShow)
                        homePopupWindow.setXl(eduShow);
                    if (null != heightShow)
                        homePopupWindow.setSg(heightShow);
                    if (null != cityShow)
                        homePopupWindow.setJzz(cityShow);
                    if (null != nationCondition)
                        homePopupWindow.setMz(nationCondition);
                    if (null != carShow)
                        homePopupWindow.setIsCar(carShow);
                    if (null != placeConditon)
                        homePopupWindow.setJg(placeConditon);
                    if (null != lastTimeShow)
                        homePopupWindow.setlastTime(lastTimeShow);
                    if (null != realNameShow)
                        homePopupWindow.setIsRealName(realNameShow);
                    if (null != houseShow)
                        homePopupWindow.setIsHouse(houseShow);
                    if (null != scoreShow)
                        homePopupWindow.setXy(scoreShow + "以上");
                }
                getData();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                getData();
            }
        });

    }


    /**
     * 不感兴趣
     */

    private void noInterest() {
        Map<String, String> params = new HashMap<>();
        params.put("dtid", dtid);
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.noInterest, params, new SpotsCallBack<BaseBean>(getContext()) {

            @Override
            public void onSuccess(Response response, BaseBean bean) {
                ToastUtil.show(bean.getMsg());
                if (selectPosition >= 0) {
                    list.remove(selectPosition);
                    adapter.notifyDataSetChanged();
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
    private void report(String type) {
        Map<String, String> params = new HashMap<>();
        params.put("jubao_uid", userId);
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

    private void formatTime(int second) {
        String time = TimeUtil.secToTime(second);
        String[] times = time.split(":");
        switch (times.length) {
            case 2:
                rushBuyCountDownTimerView.setTime(0, Integer.parseInt(times[0]), Integer.parseInt(times[1]));
                break;
            case 3:
                rushBuyCountDownTimerView.setTime(Integer.parseInt(times[0]), Integer.parseInt(times[1]), Integer.parseInt(times[2]));
                break;
        }
        rushBuyCountDownTimerView.start();
    }


    HomePopupWindow homePopupWindow;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_more:
                if (null == homePopupWindow) {
                    homePopupWindow = new HomePopupWindow(getContext(), homeTjBean, this);
                }
                homePopupWindow.showAsDropDown(ivMore);
                break;
            case R.id.ll_nl:
                AgeSectionDialog ageSectionDialog = new AgeSectionDialog(getContext(), getString(R.string.wo_buxian), new AgeSectionDialog.OnItemClick() {
                    @Override
                    public void onItemClick(String age) {
                        if (age.startsWith(getString(R.string.wo_buxian))) {
                            homePopupWindow.setAge(age.split("-")[1] + "以下");
                        } else if (age.endsWith(getString(R.string.wo_buxian)) || age.endsWith("60")) {
                            homePopupWindow.setAge(age.split("-")[0] + "以上");
                        } else
                            homePopupWindow.setAge(age);

                        if (age.startsWith(getString(R.string.wo_buxian)) && age.endsWith(getString(R.string.wo_buxian))) {
                            ageShow = null;
                        } else {
                            if (age.startsWith(getString(R.string.wo_buxian)))
                                ageShow = age.replace(getString(R.string.wo_buxian), "0");
                            else if (age.endsWith(getString(R.string.wo_buxian)))
                                ageShow = age.replace(getString(R.string.wo_buxian), "60");
                            else
                                ageShow = age;
                        }
                        ageCondition = ageShow;
                        setTag();
                    }
                });
                ageSectionDialog.show();
                break;
            case R.id.ll_jzz:
                CityChooseDialog cityChooseDialog = new CityChooseDialog(getContext(), getString(R.string.wo_buxian), new CityChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(String province, String city) {
                        homePopupWindow.setJzz(city);
                        cityShow = city;
                        cityCondition = cityShow;
                        setTag();
                    }
                }, true);
                cityChooseDialog.show();
                break;
            case R.id.ll_sg:
                SgSectionDialog sgSectionDialog = new SgSectionDialog(getContext(), getString(R.string.height), new AgeSectionDialog.OnItemClick() {
                    @Override
                    public void onItemClick(String sg) {
                        if (sg.startsWith(getString(R.string.wo_buxian))) {
                            homePopupWindow.setSg(sg.split("-")[1] + "以下");
                        } else if (sg.endsWith(getString(R.string.wo_buxian))) {
                            homePopupWindow.setSg(sg.split("-")[0] + "以上");
                        } else
                            homePopupWindow.setSg(sg);

                        if (sg.startsWith(getString(R.string.wo_buxian)) && sg.endsWith(getString(R.string.wo_buxian))) {
                            heightShow = null;
                            heightCondition = null;
                        } else {
                            if (sg.startsWith(getString(R.string.wo_buxian))) {
                                heightShow = sg.split("-")[1] + "以下";
                                heightCondition = sg.replace(getString(R.string.wo_buxian), "150");
                            } else if (sg.endsWith(getString(R.string.wo_buxian))) {
                                heightShow = sg.split("-")[0] + "以上";
                                heightCondition = sg.replace(getString(R.string.wo_buxian), "210");
                            } else {
                                heightShow = sg;
                                heightCondition = heightShow;
                            }
                        }
                        setTag();
                    }
                });
                sgSectionDialog.show();
                break;
            case R.id.ll_xl:
                final List<String> xl = Arrays.asList(getResources().getStringArray(R.array.xl));
                final List<String> xls = Arrays.asList(getResources().getStringArray(R.array.xls));
                SingleChooseDialog xlDialog = new SingleChooseDialog(getContext(), getString(R.string.wo_xueli), xls, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        homePopupWindow.setXl(xls.get(position));
                        eduCondition = xl.get(position);
                        eduShow = xls.get(position);
                        setTag();
                    }
                });
                xlDialog.show();
                break;
            case R.id.ll_ysr:
                IncomeSectionDialog incomeSectionDialog = new IncomeSectionDialog(getContext(), getString(R.string.wo_yueshou), new IncomeSectionDialog.OnItemClick() {
                    @Override
                    public void onItemClick(String income) {
                        incomeCondition = income;
                        if (income.startsWith("0") && income.endsWith("100")) {
                            incomeShow = getString(R.string.wo_buxian);
                        } else if (income.endsWith("100")) {
                            if (Integer.parseInt(income.split("-")[0]) >= 10)
                                incomeShow = Integer.parseInt(income.split("-")[0]) / 10 + "万以上";
                            else
                                incomeShow = income.split("-")[0] + "千以上";
                        } else if (income.startsWith("0")) {
                            if (Integer.parseInt(income.split("-")[1]) >= 10)
                                incomeShow = Integer.parseInt(income.split("-")[1]) / 10 + "万以下";
                            else
                                incomeShow = income.split("-")[1] + "千以下";
                        } else {
                            String start = "2千", end = "5万";
                            if (Integer.parseInt(income.split("-")[0]) >= 10)
                                start = Integer.parseInt(income.split("-")[0]) / 10 + "万";
                            else
                                start = income.split("-")[0] + "千";

                            if (Integer.parseInt(income.split("-")[1]) >= 10)
                                end = Integer.parseInt(income.split("-")[1]) / 10 + "万";
                            else
                                end = income.split("-")[1] + "千";

                            incomeShow = start + "-" + end;
                        }
                        homePopupWindow.setYsr(incomeShow);
                        setTag();
                    }
                });
                incomeSectionDialog.show();
                break;
            case R.id.ll_mz: //民族
                final List<String> mz = Arrays.asList(getResources().getStringArray(R.array.mz));
                SingleChooseDialog mzChooseDialog = new SingleChooseDialog(getContext(), getString(R.string.wo_minzu), mz, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        nationCondition = mz.get(position);
                        homePopupWindow.setMz(nationCondition);
                    }
                });
                mzChooseDialog.show();
                break;
            case R.id.ll_iscar: //是否购车
                final List<String> gcqk = Arrays.asList(getResources().getStringArray(R.array.gcqk));
                SingleChooseDialog gcqkChooseDialog = new SingleChooseDialog(getContext(), "购车情况", gcqk, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        homePopupWindow.setIsCar(gcqk.get(position));
                        carCondition = position + "";
                    }
                });
                gcqkChooseDialog.show();
                break;
            case R.id.ll_jg: //籍贯
                CityChooseDialog jgDialog = new CityChooseDialog(getContext(), getString(R.string.wo_jiguan), new CityChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(String province, String city) {
                        homePopupWindow.setJg(province + city);
                        placeConditon = province + city;
                    }
                }, false);
                jgDialog.show();
                break;
            case R.id.ll_lastTime://最近活跃
                final List<String> lasttime = Arrays.asList(getResources().getStringArray(R.array.lasttime));
                SingleChooseDialog timeChooseDialog = new SingleChooseDialog(getContext(), "最近活跃", lasttime, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        homePopupWindow.setlastTime(lasttime.get(position));
                        switch (position) {
                            case 0:
                                loginTimeCondition = "3600";
                                break;
                            case 1:
                                loginTimeCondition = "86400";
                                break;
                            case 2:
                                loginTimeCondition = "259200";
                                break;
                            case 3:
                                loginTimeCondition = "";
                                break;
                        }

                    }
                });
                timeChooseDialog.show();
                break;
            case R.id.ll_isHouse:
                final List<String> fc = new ArrayList<>();
                fc.add(getString(R.string.wo_buxian));
                fc.add(getString(R.string.wo_youfang));
                SingleChooseDialog fcChooseDialog = new SingleChooseDialog(getContext(), getString(R.string.wo_fangchan), fc, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        homePopupWindow.setIsHouse(fc.get(position));
                        if (position == 1)
                            houseCondition = position + "";
                        else
                            houseCondition = null;
                    }
                });
                fcChooseDialog.show();
                break;
            case R.id.ll_isRealName:
                final List<String> realName = new ArrayList<>();
                realName.add(getString(R.string.wo_buxian));
                realName.add(getString(R.string.wo_yishiming));
                SingleChooseDialog realNameChooseDialog = new SingleChooseDialog(getContext(), getString(R.string.wo_shiming), realName, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        homePopupWindow.setIsRealName(realName.get(position));
                        if (position == 1)
                            realNameCondition = position + "";
                        else
                            realNameCondition = null;
                    }
                });
                realNameChooseDialog.show();
                break;
            case R.id.ll_xy:
                final List<String> scores = Arrays.asList(getResources().getStringArray(R.array.score));
                SingleChooseDialog scoreChooseDialog = new SingleChooseDialog(getContext(), "信用分", scores, new SingleChooseDialog.OnItemClick() {
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
                        homePopupWindow.setXy(scores.get(position));

                        mRecyclerView.refresh();
                    }
                });
                scoreChooseDialog.show();
                break;
            case R.id.tv_sure:
                homePopupWindow.dismiss();
//                ageCondition = ageShow;
//                incomeCondition = incomeShow;
//                eduCondition = eduShow;
//                heightCondition = heightShow;
//                cityCondition = cityShow;
                getData();
                save();
                break;
            case R.id.tv_clear:
                ageShow = null;
                heightShow = null;
                incomeShow = null;
                eduShow = null;
                cityShow = null;
                ageCondition = null;
                heightCondition = null;
                incomeCondition = null;
                eduCondition = null;
                cityCondition = null;
                realNameCondition = null;
                scoreCondition = null;
                houseCondition = null;
                nationCondition = "";
                carCondition = "";
                placeConditon = "";
                loginTimeCondition = "";

                tags.clear();
                tags.add(getString(R.string.wo_age) + "：" +  getString(R.string.wo_buxian));
                tags.add(getString(R.string.height) + "：" +  getString(R.string.wo_buxian));
                tags.add(getString(R.string.wo_yueshou) + "：" +  getString(R.string.wo_buxian));
                homePopupWindow.setAge(getString(R.string.wo_buxian));
                homePopupWindow.setYsr(getString(R.string.wo_buxian));
                homePopupWindow.setXl(getString(R.string.wo_buxian));
                homePopupWindow.setSg(getString(R.string.wo_buxian));
                homePopupWindow.setJzz(getString(R.string.wo_buxian));
                homePopupWindow.setMz(getString(R.string.wo_buxian));
                homePopupWindow.setIsCar(getString(R.string.wo_buxian));
                homePopupWindow.setJg(getString(R.string.wo_buxian));
                homePopupWindow.setlastTime(getString(R.string.wo_buxian));
                homePopupWindow.setIsRealName(getString(R.string.wo_buxian));
                homePopupWindow.setIsHouse(getString(R.string.wo_buxian));
                homePopupWindow.setXy(getString(R.string.wo_buxian));
//                tags.add("学历:不限");
//                tags.add("居住地:不限");
                setTag();
                save();
                break;
        }
    }

    private void setTag() {
        tags.clear();
        if (!StringUtil.isEmpty(ageShow)) {
            String showAge = "";
            if (ageShow.startsWith("0"))
                showAge = ageShow.split("-")[1] + "以下";
            else if (ageShow.endsWith("60"))
                showAge = ageShow.split("-")[0] + "以上";
            else
                showAge = ageShow;
            tags.add(getString(R.string.wo_age) + "：" + showAge);
        } else {
            tags.add(getString(R.string.wo_age) + "：" +  getString(R.string.wo_buxian));
        }

        if (!StringUtil.isEmpty(incomeShow)) {
            tags.add(getString(R.string.wo_yueshou) + "：" + incomeShow);
        } else {
            tags.add(getString(R.string.wo_yueshou) + "：" +  getString(R.string.wo_buxian));
        }

//        if (!StringUtil.isEmpty(eduShow)) {
//            tags.add("学历：" + eduShow);
//        } else {
//            tags.add("学历：不限");
//        }
//
//        if (!StringUtil.isEmpty(cityShow)) {
//            tags.add("居住地：" + cityShow);
//        } else {
//            tags.add("居住地：不限");
//        }


        if (!StringUtil.isEmpty(heightShow)) {
            tags.add(getString(R.string.height) + "：" + heightShow);
        } else {
            tags.add(getString(R.string.height) + "：" +  getString(R.string.wo_buxian));
        }


        final TagAdapter<String> tagAdapter = new TagAdapter<String>(tags) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.tv_tag_home,
                        flTags, false);
                tv.setText(s);
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
            }
        };

        flTags.setAdapter(tagAdapter);
        flTags.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                return true;
            }
        });
    }

    private void setSelectTags() {
        tags.clear();
        if (!StringUtil.isEmpty(ageCondition)) {
            String showAge = "";
            if (ageCondition.startsWith("0"))
                showAge = ageCondition.replace("0", getString(R.string.wo_buxian));
            else if (ageCondition.endsWith("60"))
                showAge = ageCondition.replace("60", getString(R.string.wo_buxian));
            else
                showAge = ageCondition;
            tags.add(getString(R.string.wo_age) + "：" +  showAge);
        } else {
            tags.add(getString(R.string.wo_age) + "：" +  getString(R.string.wo_buxian));
        }

        if (!StringUtil.isEmpty(incomeCondition)) {
            if (incomeCondition.startsWith(getString(R.string.wo_buxian)) && incomeCondition.endsWith(getString(R.string.wo_buxian))) {
                tags.add(getString(R.string.wo_yueshou) + "："  + getString(R.string.wo_buxian));
            } else {
                if (incomeCondition.endsWith("100")) {
                    if (Integer.parseInt(incomeCondition.split("-")[0]) >= 10)
                        tags.add(getString(R.string.wo_yueshou) + "：" + Integer.parseInt(incomeCondition.split("-")[0]) / 10 + "万以上");
                    else
                        tags.add(getString(R.string.wo_yueshou) + "：" + incomeCondition.split("-")[0] + "千以上");
                } else if (incomeCondition.startsWith("0")) {
                    if (Integer.parseInt(incomeCondition.split("-")[1]) >= 10)
                        tags.add(getString(R.string.wo_yueshou) + "：" + Integer.parseInt(incomeCondition.split("-")[1]) / 10 + "万以下");
                    else
                        tags.add(getString(R.string.wo_yueshou) + "：" + incomeCondition.split("-")[1] + "千以下");
                } else {
                    String start = "2千", end = "5万";
                    if (Integer.parseInt(incomeCondition.split("-")[0]) >= 10)
                        start = Integer.parseInt(incomeCondition.split("-")[0]) / 10 + "万";
                    else
                        start = incomeCondition.split("-")[0] + "千";

                    if (Integer.parseInt(incomeCondition.split("-")[1]) >= 10)
                        end = Integer.parseInt(incomeCondition.split("-")[1]) / 10 + "万";
                    else
                        end = incomeCondition.split("-")[1] + "千";

                    tags.add(getString(R.string.wo_yueshou) + "："  + start + "-" + end);
                }

            }

        } else {
            tags.add(getString(R.string.wo_yueshou) + "：" + getString(R.string.wo_buxian));
        }

//        if (!StringUtil.isEmpty(eduCondition)) {
//            tags.add("学历：" + eduCondition);
//        } else {
//            tags.add("学历：不限");
//        }

//        if (!StringUtil.isEmpty(cityCondition)) {
//            tags.add("居住地：" + cityCondition);
//        } else {
//            tags.add("居住地：不限");
//        }


        if (!StringUtil.isEmpty(heightCondition)) {
            tags.add(getString(R.string.height) + "："  + heightCondition);
        } else {
            tags.add(getString(R.string.height) + "：" + getString(R.string.wo_buxian) );
        }


        final TagAdapter<String> tagAdapter = new TagAdapter<String>(tags) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.tv_tag_home,
                        flTags, false);
                tv.setText(s);
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
            }
        };

        flTags.setAdapter(tagAdapter);
        flTags.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                return true;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != refrshTime)
            rushBuyCountDownTimerView.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != refrshTime)
            rushBuyCountDownTimerView.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventCenter.unregistEvent(this, EventCenter.EventType.EVT_EDITEZYTJ);
//        rushBuyCountDownTimerView.stop();
    }

    @Override
    public void onEvent(EventCenter.HcbEvent e) {
        switch (e.type) {
            case EVT_EDITEZYTJ:
                getHomeTj();
                getData();
                break;
        }
    }
}
