package com.example.tb.tb_lib_test;

import com.tb.baselib.base.BaseBean;

import java.util.List;

/**
 * Created by : tb on 2017/9/22 上午11:22.
 * Description :
 */
public class TestBean extends BaseBean{
    /**
     * publicList : [{"fundCode":"mock","fundName":"mock","monetaryFund":true,"yieldDay":"mock","yieldYear":"mock","fundType":"mock","wanNav":"mock"}]
     * pager : {"pageNo":75,"pageSize":"mock","totalCount":"mock","totalPage":"mock"}
     */
    
    private PagerBean pager;
    private List<PublicListBean> publicList;
    
    public PagerBean getPager() {
        return pager;
    }
    
    public void setPager(PagerBean pager) {
        this.pager = pager;
    }
    
    public List<PublicListBean> getPublicList() {
        return publicList;
    }
    
    public void setPublicList(List<PublicListBean> publicList) {
        this.publicList = publicList;
    }
    
    public static class PagerBean extends BaseBean{
        /**
         * pageNo : 75
         * pageSize : mock
         * totalCount : mock
         * totalPage : mock
         */
        
        private int pageNo;
        private int pageSize;
        private int totalCount;
        private int totalPage;
        
        public int getPageNo() {
            return pageNo;
        }
        
        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }
        
        public int getPageSize() {
            return pageSize;
        }
        
        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
        
        public int getTotalCount() {
            return totalCount;
        }
        
        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
        
        public int getTotalPage() {
            return totalPage;
        }
        
        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }
    
        @Override
        public String toString() {
            return "PagerBean{" +
                    "pageNo=" + pageNo +
                    ", pageSize=" + pageSize +
                    ", totalCount=" + totalCount +
                    ", totalPage=" + totalPage +
                    '}';
        }
    }
    
    public static class PublicListBean extends BaseBean{
        /**
         * fundCode : mock
         * fundName : mock
         * monetaryFund : true
         * yieldDay : mock
         * yieldYear : mock
         * fundType : mock
         * wanNav : mock
         */
        
        private String fundCode;
        private String fundName;
        private boolean monetaryFund;
        private String yieldDay;
        private String yieldYear;
        private String fundType;
        private String wanNav;
        
        public String getFundCode() {
            return fundCode;
        }
        
        public void setFundCode(String fundCode) {
            this.fundCode = fundCode;
        }
        
        public String getFundName() {
            return fundName;
        }
        
        public void setFundName(String fundName) {
            this.fundName = fundName;
        }
        
        public boolean isMonetaryFund() {
            return monetaryFund;
        }
        
        public void setMonetaryFund(boolean monetaryFund) {
            this.monetaryFund = monetaryFund;
        }
        
        public String getYieldDay() {
            return yieldDay;
        }
        
        public void setYieldDay(String yieldDay) {
            this.yieldDay = yieldDay;
        }
        
        public String getYieldYear() {
            return yieldYear;
        }
        
        public void setYieldYear(String yieldYear) {
            this.yieldYear = yieldYear;
        }
        
        public String getFundType() {
            return fundType;
        }
        
        public void setFundType(String fundType) {
            this.fundType = fundType;
        }
        
        public String getWanNav() {
            return wanNav;
        }
        
        public void setWanNav(String wanNav) {
            this.wanNav = wanNav;
        }
    
        @Override
        public String toString() {
            return "PublicListBean{" +
                    "fundCode='" + fundCode + '\'' +
                    ", fundName='" + fundName + '\'' +
                    ", monetaryFund=" + monetaryFund +
                    ", yieldDay='" + yieldDay + '\'' +
                    ", yieldYear='" + yieldYear + '\'' +
                    ", fundType='" + fundType + '\'' +
                    ", wanNav='" + wanNav + '\'' +
                    '}';
        }
    }
    
    @Override
    public String toString() {
        return "TestBean{" +
                "pager=" + pager +
                ", publicList=" + publicList +
                '}';
    }
}
