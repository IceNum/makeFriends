package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2019/4/20 0020.
 */
public class IncomeDetailBean {

    /**
     * code : 1
     * data : [{"id":1,"image":"/uploads/20190401/05d0f167f1707fd953c37a6af9fe516d.jpg","name":"穿云箭","money":"100","img_src":"http://39.98.246.254/uploads/20190401/05d0f167f1707fd953c37a6af9fe516d.jpg"}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * image : /uploads/20190401/05d0f167f1707fd953c37a6af9fe516d.jpg
         * name : 穿云箭
         * money : 100
         * img_src : http://39.98.246.254/uploads/20190401/05d0f167f1707fd953c37a6af9fe516d.jpg
         */

        private String get_uid;
        private String gift_id;
        private String give_uid;
        private String id;
        private String time;
        private String type;
        private GiftBean gift;
        private GiveuidBean giveuid;
        private GetuidBean getuid;

        public GiveuidBean getGiveuid() {
            return giveuid;
        }

        public void setGiveuid(GiveuidBean giveuid) {
            this.giveuid = giveuid;
        }

        public GetuidBean getGetuid() {
            return getuid;
        }

        public void setGetuid(GetuidBean getuid) {
            this.getuid = getuid;
        }

        public GiftBean getGift() {
            return gift;
        }

        public void setGift(GiftBean gift) {
            this.gift = gift;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGet_uid() {
            return get_uid;
        }

        public void setGet_uid(String get_uid) {
            this.get_uid = get_uid;
        }

        public String getGift_id() {
            return gift_id;
        }

        public void setGift_id(String gift_id) {
            this.gift_id = gift_id;
        }

        public String getGive_uid() {
            return give_uid;
        }

        public void setGive_uid(String give_uid) {
            this.give_uid = give_uid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public class GiftBean{
            private String id;
            private String image;
            private String img_src;
            private String money;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getImg_src() {
                return img_src;
            }

            public void setImg_src(String img_src) {
                this.img_src = img_src;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public class GiveuidBean{
            private String id;
            private String nickname;
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }

        public class GetuidBean{
            private String id;
            private String nickname;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }
    }
}
