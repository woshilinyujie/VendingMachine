package com.hangzhou.hezi.vendingmachine.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/3/23.
 */

public class AdBean {

    /**
     * code : 10000
     * message : 成功
     * data : [{"id":1,"name":"图1 ","ivUrl":"http://","type":1,"playTime":5,"sort":1,"addTime":"2019-03-19T08:27:38.000+0000","updateTime":"2019-03-19T08:27:42.000+0000"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
         * name : 图1
         * ivUrl : http://
         * type : 1
         * playTime : 5
         * sort : 1
         * addTime : 2019-03-19T08:27:38.000+0000
         * updateTime : 2019-03-19T08:27:42.000+0000
         */

        private int id;
        private String name;
        private String ivUrl;
        private int type;
        private int playTime;
        private int sort;
        private String addTime;
        private String updateTime;

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

        public String getIvUrl() {
            return ivUrl;
        }

        public void setIvUrl(String ivUrl) {
            this.ivUrl = ivUrl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPlayTime() {
            return playTime;
        }

        public void setPlayTime(int playTime) {
            this.playTime = playTime;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
