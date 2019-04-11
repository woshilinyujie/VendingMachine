package com.hangzhou.hezi.vendingmachine.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/3/21.
 */

public class FoodBean {

    /**
     * code : 10000
     * message : 成功
     * data : [{"typeName":"精致生活","vendGoods":[{"id":1,"typeId":1,"imgUrl":"1","linkUrl":null,"name":null,"markPrice":null,"salePrice":null,"qrCode":null,"brand":null,"addTime":null,"updateTime":null}]},{"typeName":"厨房餐具","vendGoods":[{"id":2,"typeId":2,"imgUrl":null,"linkUrl":null,"name":null,"markPrice":null,"salePrice":null,"qrCode":null,"brand":null,"addTime":null,"updateTime":null}]},{"typeName":"零食饮料","vendGoods":[{"id":3,"typeId":3,"imgUrl":null,"linkUrl":null,"name":null,"markPrice":null,"salePrice":null,"qrCode":null,"brand":null,"addTime":null,"updateTime":null}]}]
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
         * typeName : 精致生活
         * vendGoods : [{"id":1,"typeId":1,"imgUrl":"1","linkUrl":null,"name":null,"markPrice":null,"salePrice":null,"qrCode":null,"brand":null,"addTime":null,"updateTime":null}]
         */

        private String typeName;
        private List<VendGoodsBean> vendGoods;

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public List<VendGoodsBean> getVendGoods() {
            return vendGoods;
        }

        public void setVendGoods(List<VendGoodsBean> vendGoods) {
            this.vendGoods = vendGoods;
        }

        public static class VendGoodsBean {
            /**
             * id : 1
             * typeId : 1
             * imgUrl : 1
             * linkUrl : null
             * name : null
             * markPrice : null
             * salePrice : null
             * qrCode : null
             * brand : null
             * addTime : null
             * updateTime : null
             */

            private int id;
            private int typeId;
            private String imgUrl;
            private String linkUrl;
            private String name;
            private String markPrice;
            private String salePrice;
            private String qrCode;
            private String brand;
            private String addTime;
            private String updateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
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

            public String getName() {
                return name;
            }

            public String getMarkPrice() {
                return markPrice;
            }

            public String getSalePrice() {
                return salePrice;
            }

            public String getQrCode() {
                return qrCode;
            }

            public String getBrand() {
                return brand;
            }

            public String getAddTime() {
                return addTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setMarkPrice(String markPrice) {
                this.markPrice = markPrice;
            }

            public void setSalePrice(String salePrice) {
                this.salePrice = salePrice;
            }

            public void setQrCode(String qrCode) {
                this.qrCode = qrCode;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
