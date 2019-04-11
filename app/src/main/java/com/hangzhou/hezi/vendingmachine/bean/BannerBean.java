package com.hangzhou.hezi.vendingmachine.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/3/21.
 */

public class BannerBean
{

    /**
     * code : 10000
     * message : 成功
     * data : {"top":[{"id":1,"localType":"top","machineType":1,"imgUrl":"http","linkUrl":"http://1","sort":1,"addTime":"2019-03-19T08:32:21.000+0000","updateTime":"2019-03-19T08:32:57.000+0000"},{"id":3,"localType":"top","machineType":1,"imgUrl":"h","linkUrl":"h","sort":2,"addTime":null,"updateTime":null}],"bottom":[{"id":2,"localType":"bottom","machineType":1,"imgUrl":"http","linkUrl":"http","sort":1,"addTime":"2019-03-19T08:32:52.000+0000","updateTime":"2019-03-19T08:32:53.000+0000"},{"id":4,"localType":"bottom","machineType":1,"imgUrl":"d","linkUrl":"d","sort":2,"addTime":null,"updateTime":null},{"id":5,"localType":"bottom","machineType":1,"imgUrl":"d","linkUrl":"d","sort":2,"addTime":null,"updateTime":null}]}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<TopBean> top;
        private List<BottomBean> bottom;

        public List<TopBean> getTop() {
            return top;
        }

        public void setTop(List<TopBean> top) {
            this.top = top;
        }

        public List<BottomBean> getBottom() {
            return bottom;
        }

        public void setBottom(List<BottomBean> bottom) {
            this.bottom = bottom;
        }

        public static class TopBean {
            /**
             * id : 1
             * localType : top
             * machineType : 1
             * imgUrl : http
             * linkUrl : http://1
             * sort : 1
             * addTime : 2019-03-19T08:32:21.000+0000
             * updateTime : 2019-03-19T08:32:57.000+0000
             */

            private int id;
            private String localType;
            private int machineType;
            private String imgUrl;
            private String linkUrl;
            private int sort;
            private String addTime;
            private String updateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLocalType() {
                return localType;
            }

            public void setLocalType(String localType) {
                this.localType = localType;
            }

            public int getMachineType() {
                return machineType;
            }

            public void setMachineType(int machineType) {
                this.machineType = machineType;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
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

        public static class BottomBean {
            /**
             * id : 2
             * localType : bottom
             * machineType : 1
             * imgUrl : http
             * linkUrl : http
             * sort : 1
             * addTime : 2019-03-19T08:32:52.000+0000
             * updateTime : 2019-03-19T08:32:53.000+0000
             */

            private int id;
            private String localType;
            private int machineType;
            private String imgUrl;
            private String linkUrl;
            private int sort;
            private String addTime;
            private String updateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLocalType() {
                return localType;
            }

            public void setLocalType(String localType) {
                this.localType = localType;
            }

            public int getMachineType() {
                return machineType;
            }

            public void setMachineType(int machineType) {
                this.machineType = machineType;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
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
}
