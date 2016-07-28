package com.neteasenews.module.splash.bean;

import java.util.List;

/**
 * @author Yuan
 * @time 2016/7/26  10:09
 * @desc ${TODD}
 */
public class AdsBean {

    private ActionParamsBean action_params;
    private List<String> res_url;

    public ActionParamsBean getAction_params() {
        return action_params;
    }

    public void setAction_params(ActionParamsBean action_params) {
        this.action_params = action_params;
    }

    public List<String> getRes_url() {
        return res_url;
    }

    public void setRes_url(List<String> res_url) {
        this.res_url = res_url;
    }
}
