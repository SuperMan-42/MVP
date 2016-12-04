package com.hpw.myapp.ui.tv.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hpw on 16/12/2.
 */

public class FirstBean {
    private List<RoomBean> room;
    private List<?> ad;

    public List<RoomBean> getRoom() {
        return room;
    }

    public void setRoom(List<RoomBean> room) {
        this.room = room;
    }

    public List<?> getAd() {
        return ad;
    }

    public void setAd(List<?> ad) {
        this.ad = ad;
    }

    public static class RoomBean {

        /**
         * id : 0
         * name : ????
         * is_default : 0
         * icon :
         * slug :
         * category_more :
         * type : 1
         * screen : 0
         * list : [{"beauty_cover":"","first_play_at":"2016-05-20 23:22:16","category_name":"????","thumb":"http://snap.quanmin.tv/3158218-1480820162-142.jpg?imageView2/2/w/390/","last_play_at":"2016-12-03 09:21:28","screen":0,"video":"http://thumb.quanmin.tv/3158218.mp4?t=1480820100","title":"?????????????????","recommend_image":"http://image.quanmin.tv/19d3c4bf4c6f8947c4b7acfd65f7c9c7jpg","is_shield":false,"nick":"???boon","uid":3158218,"view":"193856","category_id":1,"slug":"","love_cover":"","level":0,"like":1,"weight":18024600,"avatar":"http://image.quanmin.tv/avatar/3956c5056ec10e81795329ddae19ddeajpeg?imageView2/2/w/300/","check":true,"follow":57682,"play_count":228,"play_true":4392,"max_view":26086,"grade":0,"default_image":null,"last_end_at":"2016-05-20 23:22:16","create_at":"2016-12-04 08:01:52","category_slug":"lol","last_thumb":"3158218-1480740783-815.jpg","play_status":true,"anniversary":7,"status":1,"coin":306399,"link":"http://www.quanmin.tv/v/3158218"},{"beauty_cover":"","first_play_at":"2016-11-08 20:47:17","category_name":"????","thumb":"http://snap.quanmin.tv/8528095-1480820162-875.jpg?imageView2/2/w/390/","last_play_at":"2016-12-03 20:57:27","screen":0,"video":"http://thumb.quanmin.tv/8528095.mp4?t=1480820100","title":"?????????~???","recommend_image":null,"is_shield":false,"nick":"???","uid":8528095,"view":"16760","category_id":4,"slug":"","love_cover":"","level":0,"like":0,"weight":154720,"avatar":"http://image.quanmin.tv/avatar/7388e912c32ffd3c136a39ae207c66abjpg?imageView2/2/w/300/","check":true,"follow":924,"play_count":50,"play_true":176,"max_view":398,"grade":0,"default_image":null,"last_end_at":"2016-11-08 20:47:17","create_at":"2016-12-04 09:24:47","category_slug":"beauty","last_thumb":"8528095-1480774145-587.jpg","play_status":true,"anniversary":3,"status":1,"coin":26143,"link":"http://www.quanmin.tv/v/8528095"},{"beauty_cover":"","first_play_at":"2016-01-16 17:19:34","category_name":"????","thumb":"http://snap.quanmin.tv/297391-1480820199-807.jpg?imageView2/2/w/390/","last_play_at":"2016-12-03 09:18:07","screen":0,"video":"http://thumb.quanmin.tv/297391.mp4?t=1480820100","title":"???1????","recommend_image":null,"is_shield":false,"nick":"??","uid":297391,"view":"144782","category_id":5,"slug":"laoyang","love_cover":"","level":0,"like":0,"weight":77155220,"avatar":"http://image.quanmin.tv/avatar/ddc21bf3162a44f2ea56a1f44b1e28bbjpg?imageView2/2/w/300/","check":true,"follow":49029,"play_count":392,"play_true":352,"max_view":20922,"grade":0,"default_image":null,"last_end_at":"2016-01-16 17:19:34","create_at":"2016-12-04 08:46:37","category_slug":"tvgame","last_thumb":"297391-1480787723-400.jpg","play_status":true,"anniversary":7,"status":1,"coin":3690868,"link":"http://www.quanmin.tv/v/laoyang"},{"beauty_cover":"","first_play_at":"2016-10-31 13:36:35","category_name":"????","thumb":"http://snap.quanmin.tv/8501992-1480820201-105.jpg?imageView2/2/w/390/","last_play_at":"2016-12-04 10:02:07","screen":0,"video":"http://thumb.quanmin.tv/8501992.mp4?t=1480820100","title":"???? ????","recommend_image":"http://image.quanmin.tv/95c89120dbbd1dfa0539dff4fa0610e7jpg","is_shield":false,"nick":"Sweety??","uid":8501992,"view":"29592","category_id":4,"slug":"","love_cover":"http://image.quanmin.tv/love/cfcc717031628c470963e56effc1d215jpg","level":0,"like":239,"weight":886720,"avatar":"http://image.quanmin.tv/avatar/cb951ceec5381423d131383f44d48a17jpg?imageView2/2/w/300/","check":true,"follow":5588,"play_count":51,"play_true":108,"max_view":706,"grade":0,"default_image":null,"last_end_at":"2016-10-31 13:36:35","create_at":"2016-12-04 10:03:01","category_slug":"love","last_thumb":"8501992-1480743642-910.jpg","play_status":true,"anniversary":4,"status":1,"coin":228992,"link":"http://www.quanmin.tv/v/8501992"},{"beauty_cover":"","first_play_at":"2016-03-26 20:34:00","category_name":"????","thumb":"http://snap.quanmin.tv/1948045-1480820162-836.jpg?imageView2/2/w/390/","last_play_at":"2016-12-03 08:31:55","screen":0,"video":"http://thumb.quanmin.tv/1948045.mp4?t=1480820100","title":"???????","recommend_image":null,"is_shield":false,"nick":"??????","uid":1948045,"view":"4246","category_id":3,"slug":"xiaoshi4928988","love_cover":"","level":0,"like":0,"weight":202300,"avatar":"http://image.quanmin.tv/avatar/1dea9b34f3db4259b8ec4a51984da8fcjpg?imageView2/2/w/300/","check":true,"follow":1325,"play_count":262,"play_true":28,"max_view":281,"grade":0,"default_image":null,"last_end_at":"2016-03-26 20:34:00","create_at":"2016-12-04 08:57:12","category_slug":"heartstone","last_thumb":"1948045-1480739883-90.jpg","play_status":true,"anniversary":0,"status":1,"coin":19710,"link":"http://www.quanmin.tv/v/xiaoshi4928988"},{"beauty_cover":"","first_play_at":"2016-07-30 13:51:18","category_name":"????","thumb":"http://snap.quanmin.tv/4375676-1480820199-34.jpg?imageView2/2/w/390/","last_play_at":"2016-12-04 09:37:36","screen":0,"video":"http://thumb.quanmin.tv/4375676.mp4?t=1480820100","title":"???????","recommend_image":"http://image.quanmin.tv/76fc00742a7185767f7f46ee8204e52bjpg","is_shield":false,"nick":"??","uid":4375676,"view":"3184","category_id":13,"slug":"","love_cover":"","level":0,"like":0,"weight":3564820,"avatar":"http://image.quanmin.tv/avatar/e600012dfe9dc41f9ae1bc05678117cejpg?imageView2/2/w/300/","check":true,"follow":36075,"play_count":252,"play_true":44,"max_view":4864,"grade":0,"default_image":null,"last_end_at":"2016-07-30 13:51:18","create_at":"2016-12-04 10:51:59","category_slug":"huwai","last_thumb":"4375676-1480815279-683.jpg","play_status":true,"anniversary":0,"status":1,"coin":164952,"link":"http://www.quanmin.tv/v/4375676"}]
         */

        private int id;
        private String name;
        private int is_default;
        private String icon;
        private String slug;
        private String category_more;
        private int type;
        private int screen;
        private List<ListBean> list;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getCategory_more() {
            return category_more;
        }

        public void setCategory_more(String category_more) {
            this.category_more = category_more;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getScreen() {
            return screen;
        }

        public void setScreen(int screen) {
            this.screen = screen;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {

            /**
             * beauty_cover :
             * first_play_at : 2016-05-20 23:22:16
             * category_name : ????
             * thumb : http://snap.quanmin.tv/3158218-1480820162-142.jpg?imageView2/2/w/390/
             * last_play_at : 2016-12-03 09:21:28
             * screen : 0
             * video : http://thumb.quanmin.tv/3158218.mp4?t=1480820100
             * title : ?????????????????
             * recommend_image : http://image.quanmin.tv/19d3c4bf4c6f8947c4b7acfd65f7c9c7jpg
             * is_shield : false
             * nick : ???boon
             * uid : 3158218
             * view : 193856
             * category_id : 1
             * slug :
             * love_cover :
             * level : 0
             * like : 1
             * weight : 18024600
             * avatar : http://image.quanmin.tv/avatar/3956c5056ec10e81795329ddae19ddeajpeg?imageView2/2/w/300/
             * check : true
             * follow : 57682
             * play_count : 228
             * play_true : 4392
             * max_view : 26086
             * grade : 0
             * default_image : null
             * last_end_at : 2016-05-20 23:22:16
             * create_at : 2016-12-04 08:01:52
             * category_slug : lol
             * last_thumb : 3158218-1480740783-815.jpg
             * play_status : true
             * anniversary : 7
             * status : 1
             * coin : 306399
             * link : http://www.quanmin.tv/v/3158218
             */

            private String beauty_cover;
            private String first_play_at;
            private String category_name;
            private String thumb;
            private String last_play_at;
            private int screen;
            private String video;
            private String title;
            private String recommend_image;
            private boolean is_shield;
            private String nick;
            private int uid;
            private String view;
            private int category_id;
            private String slug;
            private String love_cover;
            private int level;
            private int like;
            private int weight;
            private String avatar;
            private boolean check;
            private int follow;
            private int play_count;
            private int play_true;
            private int max_view;
            private int grade;
            private Object default_image;
            private String last_end_at;
            private String create_at;
            private String category_slug;
            private String last_thumb;
            private boolean play_status;
            private int anniversary;
            private int status;
            private int coin;
            private String link;

            public String getBeauty_cover() {
                return beauty_cover;
            }

            public void setBeauty_cover(String beauty_cover) {
                this.beauty_cover = beauty_cover;
            }

            public String getFirst_play_at() {
                return first_play_at;
            }

            public void setFirst_play_at(String first_play_at) {
                this.first_play_at = first_play_at;
            }

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getLast_play_at() {
                return last_play_at;
            }

            public void setLast_play_at(String last_play_at) {
                this.last_play_at = last_play_at;
            }

            public int getScreen() {
                return screen;
            }

            public void setScreen(int screen) {
                this.screen = screen;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getRecommend_image() {
                return recommend_image;
            }

            public void setRecommend_image(String recommend_image) {
                this.recommend_image = recommend_image;
            }

            public boolean isIs_shield() {
                return is_shield;
            }

            public void setIs_shield(boolean is_shield) {
                this.is_shield = is_shield;
            }

            public String getNick() {
                return nick;
            }

            public void setNick(String nick) {
                this.nick = nick;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getView() {
                return view;
            }

            public void setView(String view) {
                this.view = view;
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public String getSlug() {
                return slug;
            }

            public void setSlug(String slug) {
                this.slug = slug;
            }

            public String getLove_cover() {
                return love_cover;
            }

            public void setLove_cover(String love_cover) {
                this.love_cover = love_cover;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getLike() {
                return like;
            }

            public void setLike(int like) {
                this.like = like;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

            public int getFollow() {
                return follow;
            }

            public void setFollow(int follow) {
                this.follow = follow;
            }

            public int getPlay_count() {
                return play_count;
            }

            public void setPlay_count(int play_count) {
                this.play_count = play_count;
            }

            public int getPlay_true() {
                return play_true;
            }

            public void setPlay_true(int play_true) {
                this.play_true = play_true;
            }

            public int getMax_view() {
                return max_view;
            }

            public void setMax_view(int max_view) {
                this.max_view = max_view;
            }

            public int getGrade() {
                return grade;
            }

            public void setGrade(int grade) {
                this.grade = grade;
            }

            public Object getDefault_image() {
                return default_image;
            }

            public void setDefault_image(Object default_image) {
                this.default_image = default_image;
            }

            public String getLast_end_at() {
                return last_end_at;
            }

            public void setLast_end_at(String last_end_at) {
                this.last_end_at = last_end_at;
            }

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }

            public String getCategory_slug() {
                return category_slug;
            }

            public void setCategory_slug(String category_slug) {
                this.category_slug = category_slug;
            }

            public String getLast_thumb() {
                return last_thumb;
            }

            public void setLast_thumb(String last_thumb) {
                this.last_thumb = last_thumb;
            }

            public boolean isPlay_status() {
                return play_status;
            }

            public void setPlay_status(boolean play_status) {
                this.play_status = play_status;
            }

            public int getAnniversary() {
                return anniversary;
            }

            public void setAnniversary(int anniversary) {
                this.anniversary = anniversary;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getCoin() {
                return coin;
            }

            public void setCoin(int coin) {
                this.coin = coin;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
    }
}
