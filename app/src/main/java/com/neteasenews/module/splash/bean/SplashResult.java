package com.neteasenews.module.splash.bean;

import java.util.List;

/**
 * @author Yuan
 * @time 2016/7/26  10:05
 * @desc ${TODD}
 */
public class SplashResult {

    private int result;
    private int next_req;
    private String error;
    private List<AdsBean> ads;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getNext_req() {
        return next_req;
    }

    public void setNext_req(int next_req) {
        this.next_req = next_req;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

}
