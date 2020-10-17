package com.people.loveme.http;


/**
 * Created by kxn on 2018/5/17 0017.
 */

public class Url {


    //测试地址
    public static String THE_SERVER_URL = "http://www.clounet.club";
//服务器地址
//    public static String THE_SERVER_URL = "http://39.98.246.254";


    //单文件上传
    public static String upload = THE_SERVER_URL + "/api/common/upload";

    //注册
    public static String register = THE_SERVER_URL + "/api/user/register";

    //获取验证码
    public static String getverify = THE_SERVER_URL + "/api/user/get_verify";

    //登陆
    public static String login = THE_SERVER_URL + "/api/user/login";

    //三方登录
    public static String thirdLogin = THE_SERVER_URL + "/api/user/openid_login";

    //忘记密码修改
    public static String forgetpassword = THE_SERVER_URL + "/api/user/resetPass";

    //首页推荐
    public static String homerecommend = THE_SERVER_URL + "/api/dongtai/index";

    //首页banner
    public static String homebanner = THE_SERVER_URL + "/api/dongtai/get_banner";

    //首页刷新时间
    public static String homeRefreshTime = THE_SERVER_URL + "/api/other/refreshTime";

    //附近的人
    public static String nearbyUser = THE_SERVER_URL + "/api/dongtai/fujin_member";

    //遇见
    public static String userMeet = THE_SERVER_URL + "/api/user/yujian";

    //不感兴趣
    public static String noInterest = THE_SERVER_URL + "/api/dongtai/dt_blacklist";

    //举报
    public static String report = THE_SERVER_URL + "/api/user/jubao";

    //用户中心
    public static String uerIndex = THE_SERVER_URL + "/api/user/ucenter";

    //信用积分  旧
//    public static String creditScore = THE_SERVER_URL + "/api/user/my_credit_score";

    //信用积分 新
    public static String creditScore = THE_SERVER_URL + "/api/user/get_myscore";

    //信用记录 新
    public static String creditRecord = THE_SERVER_URL + "/api/user/credit";


    //用户二维码
    public static String uerEwm = THE_SERVER_URL + "/api/user/user_erweima";

    //修改恋爱状态
    public static String changeLoveStatus = THE_SERVER_URL + "/api/user/profile";


    //获取vip 套餐列表
    public static String vipCardList = THE_SERVER_URL + "/api/user/vip_list";

    //创建订单
    public static String createOrder = THE_SERVER_URL + "/api/user/create_order";

    //支付
    public static String pay = THE_SERVER_URL + "/api/payment/pay";

    //微信支付
    public static String wechatPay = THE_SERVER_URL + "/api/pay/wechat";

    //支付宝支付
    public static String alipay = THE_SERVER_URL + "/api/Alipay.php";

    //会员开通服务
    public static String openvip = THE_SERVER_URL + "/api/user/openvip";

    //提现统计
    public static String myWallet = THE_SERVER_URL + "/api/user/wallet";

    //收支明细
    public static String myOrder = THE_SERVER_URL + "/api/user/get_myorder";


    //申请提现
    public static String sqtx = THE_SERVER_URL + "/api/user/tixian";

    //我邀请的人
    public static String myInvite = THE_SERVER_URL + "/api/user/my_Invitation";

    //黑名单
    public static String blacklist = THE_SERVER_URL + "/api/user/getMyBlacklist";

    public static String delBlacklist = THE_SERVER_URL + "/api/user/del_blacklist";

    //修改密码
    public static String changePassword = THE_SERVER_URL + "/api/user/resetPass";

    //意见反馈
    public static String feedback = THE_SERVER_URL + "/api/other/fankui";

    //联系我们
    public static String coutactUs = THE_SERVER_URL + "/api/other/Contact";


    //查看用户详情信息
    public static String userDetail = THE_SERVER_URL + "/api/index/user_detail";


    //用户动态分页加载
    public static String userMoreDynamic = THE_SERVER_URL + "/api/index/load_user_dynamic";

    //发送喜欢
    public static String sendLove = THE_SERVER_URL + "/api/user/add_like";

    //遇见
    public static String browseyujian = THE_SERVER_URL + "/api/user/browseyujian";

    //取消喜欢
    public static String delLove = THE_SERVER_URL + "/api/user/del_like";

    //拉黑
    public static String addBlack = THE_SERVER_URL + "/api/user/add_blacklist";

    //移出黑名单
    public static String delBlack = THE_SERVER_URL + "/api/index/del_black";


    //获取用户基本资料
    public static String userInfo = THE_SERVER_URL + "/api/user/get_basic_info";

    //获取用户个人信息
    public static String getUserInfo = THE_SERVER_URL + "/api/user/get_user_info";


    //修改用户基本资料
    public static String editBasicInfo = THE_SERVER_URL + "/api/user/edit_basic_info";

    //获取用户详细信息
    public static String userDetailInfo = THE_SERVER_URL + "/api/user/get_detail_info";

    //修改用户详细信息
    public static String editUerDetailInfo = THE_SERVER_URL + "/api/user/edit_detail_info";

    //修改征友条件
    public static String findTiaoJian = THE_SERVER_URL + "/api/user/find_tiaojian";

    //获取征友条件
    public static String getFindTiaoJian = THE_SERVER_URL + "/api/user/get_find_tiaojian";


    //相册列表
    public static String photoList = THE_SERVER_URL + "/api/user/get_photos";

    //上传相册
    public static String addPhoto = THE_SERVER_URL + "/api/user/photos_add";

    //删除相册
    public static String delPhoto = THE_SERVER_URL + "/api/user/photos_del";


    //认证状态
    public static String rzzt = THE_SERVER_URL + "/api/user/auth_center";

    //手机号认证
    public static String mobileRz = THE_SERVER_URL + "/api/user/mobile_attestation";

    //学历信息
    public static String educationInfo = THE_SERVER_URL + "/api/user/get_xueli_auth";

    //学历认证
    public static String educationRz = THE_SERVER_URL + "/api/user/xueli_auth";

    //职业认证信息
    public static String professionRzInfo = THE_SERVER_URL + "/api/user/get_work_auth";

    //职业认证
    public static String professionRz = THE_SERVER_URL + "/api/user/work_auth";

    //身份认证信息
    public static String personRzInfo = THE_SERVER_URL + "/api/user/get_relaname_auth";

    //身份认证
    public static String personRz = THE_SERVER_URL + "/api/user/relaname_auth";

    //查询身份认证
    public static String isPersonRz = THE_SERVER_URL + "/api/other/relaname";

    //车辆认证信息
    public static String carRzInfo = THE_SERVER_URL + "/api/user/get_car_auth";

    //车辆认证
    public static String carRz = THE_SERVER_URL + "/api/user/car_auth";

    //房产认证信息
    public static String hoursRzInfo = THE_SERVER_URL + "/api/user/get_house_auth";

    //房产认证
    public static String hoursRz = THE_SERVER_URL + "/api/user/house_auth";

    //发布动态
    public static String addDynamic = THE_SERVER_URL + "/api/dongtai/create";

    //我的动态
    public static String myDynamic = THE_SERVER_URL + "/api/dongtai/getMyDongtai";

    //删除动态
    public static String delDynamic = THE_SERVER_URL + "/api/dongtai/del_dongtai";

    //我喜欢的
    public static String myLike = THE_SERVER_URL + "/api/user/my_like";

    //取消喜欢
    public static String delLike = THE_SERVER_URL + "/api/user/del_like";

    //谁喜欢我
    public static String myLiked = THE_SERVER_URL + "/api/user/wholikeme";

    //谁看过我
    public static String myLooked = THE_SERVER_URL + "/api/user/get_seenme";

    //清空谁看过我
    public static String delMyLooked = THE_SERVER_URL + "/api/user/show_del_all";


    //服务协议
    public static String agreement = THE_SERVER_URL + "/api/other/xieyi";

    //服务协议详情
    public static String agreementDetail = THE_SERVER_URL + "/api/user/service_agreement_detail";

    //常见问题
    public static String question = THE_SERVER_URL + "/api/other/wenti";

    //常见问题详情
    public static String questionDetail = THE_SERVER_URL + "/api/user/familiar_issue_detail";

    //广场
    public static String guangchang = THE_SERVER_URL + "/api/dongtai/guangchang";

    //获取话题
    public static String getTopic = THE_SERVER_URL + "/api/dongtai/getCates";


    //我关注的
    public static String myAttention = THE_SERVER_URL + "/api/dongtai/guangchangGuanzhu";

    //动态点赞
    public static String zan = THE_SERVER_URL + "/api/dongtai/zan";

    //取消点赞
    public static String cancleZan = THE_SERVER_URL + "/api/dongtai/del_Zan";


    //获取城市ID
    public static String getCityId = THE_SERVER_URL + "/api/common/get_city_id";

    //修改用户信息
    public static String saveUserInfo = THE_SERVER_URL + "/api/user/profile";

    //获取用户标签
    public static String getTags = THE_SERVER_URL + "/api/user/get_biaoqian";


    //注册协议
    public static String REGISTER = THE_SERVER_URL + "/xieyi/2.html";

    //邀请规则
    public static String Inviterules = THE_SERVER_URL + "/wenti/3.html";

    //信用规则
    public static String Creditrules = THE_SERVER_URL + "/wenti/4.html";

    //分享链接
    public static String shareUrl = THE_SERVER_URL + "/html/zhuanti3.html";

    //版本更新
    public static String appUpdate = THE_SERVER_URL + "/api/other/app_update";


    //最后登陆时间
    public static String lastlogin = THE_SERVER_URL + "/api/user/update_lastlogin";


    //完善资料
    public static String ziliao = THE_SERVER_URL + "/api/user/ziliao";

    //是否可以打招呼
    public static String Zhaohu = THE_SERVER_URL + "/api/user/Zhaohu";

    //是否相互喜欢
    public static String xianghulike = THE_SERVER_URL + "/api/user/xianghu_like";

    //终止聊天
    public static String zhongzhi = THE_SERVER_URL + "/api/user/zhongzhi";

    //60天未建立关系
    public static String notfound = THE_SERVER_URL + "/api/user/notfound_60";

    //查看他人
    public static String addViewer = THE_SERVER_URL + "/api/user/Viewer";

    //是否喜欢
    public static String checkLike = THE_SERVER_URL + "/api/user/check_like";

    //清除看过我
    public static String cleanSeeMe = THE_SERVER_URL + "/api/user/clean_seenme";

    //新手引导
    public static String appStartImage = THE_SERVER_URL + "/api/other/app_startimage";

    //通知设置
    public static String tongzhiConf = THE_SERVER_URL + "/api/user/tongzhi_conf";

    //通知设置
    public static String editTongzhiConf = THE_SERVER_URL + "/api/user/edit_tongzhi_conf";

    //获取用户扫脸链接
    public static String getFace = THE_SERVER_URL + "/api/Face.php";

    //是否需要支付身份认证
    public static String needPay = THE_SERVER_URL + "/api/user/isneedpay";

    //首页显示条件
    public static String homeTj = THE_SERVER_URL + "/api/dongtai/tiaojian";

    //获取未读消息
    public static String getTongzhi = THE_SERVER_URL + "/api/other/getTongzhi";

    //头像是否审核中
    public static String isShenhe = THE_SERVER_URL + "/api/user/is_shenhe";

    public static String isHave = THE_SERVER_URL + "/api/user/checkUserInfo/field/phone/value/";

    //获取礼物列表
    public static String getGiftList = THE_SERVER_URL + "/api/gift/get_gift_list";
    //赠送礼物
    public static String giftGiving = THE_SERVER_URL + "/api/gift/gift_giving";
    //我的礼物订单
    public static String myGiftOrder = THE_SERVER_URL + "/api/gift/my_gift_order";
    //我的充值订单
    public static String myChargeOrder = THE_SERVER_URL + "/api/user/get_myorder";
    //我的提现订单
    public static String myWithdrawOrder = THE_SERVER_URL + "/api/user/get_mytiixan_info";
    //获取充值套餐
    public static String getSetmeal = THE_SERVER_URL + "/api/gift/getSetmeal";


}
