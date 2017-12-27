package com.hpw.myapp.ui.tv.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by hpw on 17-12-27.
 */

public class DeviceBean extends BmobObject {
    private String isEmulator;
    private String model;
    private String product;
    private String imei;
    private String device;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getEmulator() {
        return isEmulator;
    }

    public void setEmulator(String emulator) {
        isEmulator = emulator;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
