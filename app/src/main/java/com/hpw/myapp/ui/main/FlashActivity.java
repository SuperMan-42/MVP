package com.hpw.myapp.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothClass;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.util.ArrayMap;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.utils.device.base.BatteryHealth;
import com.hpw.mvpframe.utils.device.base.ChargingVia;
import com.hpw.mvpframe.utils.device.base.DeviceType;
import com.hpw.mvpframe.utils.device.base.EasyAppMod;
import com.hpw.mvpframe.utils.device.base.EasyBatteryMod;
import com.hpw.mvpframe.utils.device.base.EasyBluetoothMod;
import com.hpw.mvpframe.utils.device.base.EasyConfigMod;
import com.hpw.mvpframe.utils.device.base.EasyCpuMod;
import com.hpw.mvpframe.utils.device.base.EasyDeviceMod;
import com.hpw.mvpframe.utils.device.base.EasyDisplayMod;
import com.hpw.mvpframe.utils.device.base.EasyFingerprintMod;
import com.hpw.mvpframe.utils.device.base.EasyIdMod;
import com.hpw.mvpframe.utils.device.base.EasyLocationMod;
import com.hpw.mvpframe.utils.device.base.EasyMemoryMod;
import com.hpw.mvpframe.utils.device.base.EasyNetworkMod;
import com.hpw.mvpframe.utils.device.base.EasyNfcMod;
import com.hpw.mvpframe.utils.device.base.EasySensorMod;
import com.hpw.mvpframe.utils.device.base.EasySimMod;
import com.hpw.mvpframe.utils.device.base.NetworkType;
import com.hpw.mvpframe.utils.device.base.OrientationType;
import com.hpw.mvpframe.utils.device.base.PhoneType;
import com.hpw.mvpframe.utils.device.base.RingerMode;
import com.hpw.mvpframe.utils.helper.RxUtil;
import com.hpw.myapp.R;
import com.hpw.myapp.ui.tv.model.DeviceBean;
import com.hpw.myapp.ui.zhihu.activity.ZhihuMainActivity;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rx.Observable;

/**
 * Created by hpw on 16/10/28.
 */

public class FlashActivity extends CoreBaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_flash;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void initView(Bundle savedInstanceState) {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .compose(RxPermissions.getInstance(this)
                        .ensureEach(Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.GET_ACCOUNTS,
                                Manifest.permission.USE_FINGERPRINT))
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(permission -> {
                    if (permission.granted) {
                        startActivity(ZhihuMainActivity.class);
                        finish();
                    }
                });

        SharedPreferences setting = getSharedPreferences("mvp", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {
            setting.edit().putBoolean("FIRST", false).apply();
            DeviceBean deviceBean = new DeviceBean();
            Map<String, String> map = device();
            deviceBean.setEmulator(map.get("Running on emulator"));
            deviceBean.setModel(map.get("Model"));
            deviceBean.setProduct(map.get("Product"));
            deviceBean.setImei(map.get("IMEI"));
            deviceBean.setDevice(new Gson().toJson(map));
            deviceBean.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        Log.i("device", "添加数据成功，返回objectId为：" + objectId);
                    } else {
                        Log.i("device", "创建数据失败：" + e.getMessage());
                    }
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @SuppressLint("MissingPermission")
    private Map<String, String> device() {
        ArrayMap<String, String> deviceDataMap = new ArrayMap<>();
        EasyIdMod easyIdMod = new EasyIdMod(this);

        String[] emailIds = easyIdMod.getAccounts();
        StringBuilder emailString = new StringBuilder();
        if (emailIds != null && emailIds.length > 0) {
            for (String e : emailIds) {
                emailString.append(e).append("\n");
            }
        } else {
            emailString.append("-");
        }

        // Config Mod
        EasyConfigMod easyConfigMod = new EasyConfigMod(this);
        deviceDataMap.put("Time (ms)", String.valueOf(easyConfigMod.getTime()));
        deviceDataMap.put("Formatted Time (24Hrs)", easyConfigMod.getFormattedTime());
        deviceDataMap.put("UpTime (ms)", String.valueOf(easyConfigMod.getUpTime()));
        deviceDataMap.put("Formatted Up Time (24Hrs)", easyConfigMod.getFormattedUpTime());
        deviceDataMap.put("Date", String.valueOf(easyConfigMod.getCurrentDate()));
        deviceDataMap.put("Formatted Date", easyConfigMod.getFormattedDate());
        deviceDataMap.put("SD Card available", String.valueOf(easyConfigMod.hasSdCard()));
        deviceDataMap.put("Running on emulator", String.valueOf(easyConfigMod.isRunningOnEmulator()));

        @RingerMode int ringermode = easyConfigMod.getDeviceRingerMode();
        switch (ringermode) {
            case RingerMode.NORMAL:
                deviceDataMap.put(getString(R.string.ringer_mode), "normal");
                break;
            case RingerMode.VIBRATE:
                deviceDataMap.put(getString(R.string.ringer_mode), "vibrate");
                break;
            case RingerMode.SILENT:
            default:
                deviceDataMap.put(getString(R.string.ringer_mode), "silent");
                break;
        }

        // Fingerprint Mod
        EasyFingerprintMod easyFingerprintMod = new EasyFingerprintMod(this);
        deviceDataMap.put("Is Fingerprint Sensor present?", String.valueOf(easyFingerprintMod.isFingerprintSensorPresent()));
        deviceDataMap.put("Are fingerprints enrolled", String.valueOf(easyFingerprintMod.areFingerprintsEnrolled()));

        // Sensor Mod
        EasySensorMod easySensorMod = new EasySensorMod(this);
        List<Sensor> list = easySensorMod.getAllSensors();
        for (Sensor s : list) {
            if (s != null) {
                String stringBuilder = "\nVendor : "
                        + s.getVendor()
                        + "\n"
                        + "Version : "
                        + s.getVersion()
                        + "\n"
                        + "Power : "
                        + s.getPower()
                        + "\n"
                        + "Resolution : "
                        + s.getResolution()
                        + "\n"
                        + "Max Range : "
                        + s.getMaximumRange();
                deviceDataMap.put("Sensor Name - " + s.getName(), stringBuilder);
            } else {
                deviceDataMap.put("Sensor", "N/A");
            }
        }

        // SIM Mod
        EasySimMod easySimMod = new EasySimMod(this);
        deviceDataMap.put("IMSI", easySimMod.getIMSI());
        deviceDataMap.put("SIM Serial Number", easySimMod.getSIMSerial());
        deviceDataMap.put("Country", easySimMod.getCountry());
        deviceDataMap.put("Carrier", easySimMod.getCarrier());
        deviceDataMap.put("SIM Network Locked", String.valueOf(easySimMod.isSimNetworkLocked()));
        deviceDataMap.put("Is Multi SIM", String.valueOf(easySimMod.isMultiSim()));
        deviceDataMap.put("Number of active SIM", String.valueOf(easySimMod.getNumberOfActiveSim()));

        if (easySimMod.isMultiSim()) {
            List<SubscriptionInfo> activeMultiSimInfo = easySimMod.getActiveMultiSimInfo();
            if (activeMultiSimInfo != null) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < activeMultiSimInfo.size(); i++) {
                    stringBuilder.append("\nSIM ")
                            .append(i)
                            .append(" Info")
                            .append("\nPhone Number :")
                            .append(activeMultiSimInfo.get(i).getNumber())
                            .append("\n")
                            .append("Carrier Name :")
                            .append(activeMultiSimInfo.get(i).getCarrierName())
                            .append("\n")
                            .append("Country :")
                            .append(activeMultiSimInfo.get(i).getCountryIso())
                            .append("\n")
                            .append("Roaming :")
                            .append(activeMultiSimInfo.get(i).getDataRoaming() == SubscriptionManager.DATA_ROAMING_ENABLE)
                            .append("\n")
                            .append("Display Name :")
                            .append(activeMultiSimInfo.get(i).getDisplayName())
                            .append("\n")
                            .append("Sim Slot  :")
                            .append(activeMultiSimInfo.get(i).getSimSlotIndex())
                            .append("\n");
                }
                deviceDataMap.put("Multi SIM Info", stringBuilder.toString());
            }
        }

        // Device Mod
        EasyDeviceMod easyDeviceMod = new EasyDeviceMod(this);
        deviceDataMap.put("Language", easyDeviceMod.getLanguage());
        deviceDataMap.put("Android ID", easyIdMod.getAndroidID());
        deviceDataMap.put("IMEI", easyDeviceMod.getIMEI());
        deviceDataMap.put("User-Agent", easyIdMod.getUA());
        deviceDataMap.put("GSF ID", easyIdMod.getGSFID());
        deviceDataMap.put("Pseudo ID", easyIdMod.getPseudoUniqueID());
        deviceDataMap.put("Device Serial", easyDeviceMod.getSerial());
        deviceDataMap.put("Manufacturer", easyDeviceMod.getManufacturer());
        deviceDataMap.put("Model", easyDeviceMod.getModel());
        deviceDataMap.put("OS Codename", easyDeviceMod.getOSCodename());
        deviceDataMap.put("OS Version", easyDeviceMod.getOSVersion());
        deviceDataMap.put("Display Version", easyDeviceMod.getDisplayVersion());
        deviceDataMap.put("Phone Number", easyDeviceMod.getPhoneNo());
        deviceDataMap.put("Radio Version", easyDeviceMod.getRadioVer());
        deviceDataMap.put("Product", easyDeviceMod.getProduct());
        deviceDataMap.put("Device", easyDeviceMod.getDevice());
        deviceDataMap.put("Board", easyDeviceMod.getBoard());
        deviceDataMap.put("Hardware", easyDeviceMod.getHardware());
        deviceDataMap.put("BootLoader", easyDeviceMod.getBootloader());
        deviceDataMap.put("Device Rooted", String.valueOf(easyDeviceMod.isDeviceRooted()));
        deviceDataMap.put("Fingerprint", easyDeviceMod.getFingerprint());
        deviceDataMap.put("Build Brand", easyDeviceMod.getBuildBrand());
        deviceDataMap.put("Build Host", easyDeviceMod.getBuildHost());
        deviceDataMap.put("Build Tag", easyDeviceMod.getBuildTags());
        deviceDataMap.put("Build Time", String.valueOf(easyDeviceMod.getBuildTime()));
        deviceDataMap.put("Build User", easyDeviceMod.getBuildUser());
        deviceDataMap.put("Build Version Release", easyDeviceMod.getBuildVersionRelease());
        deviceDataMap.put("Screen Display ID", easyDeviceMod.getScreenDisplayID());
        deviceDataMap.put("Build Version Codename", easyDeviceMod.getBuildVersionCodename());
        deviceDataMap.put("Build Version Increment", easyDeviceMod.getBuildVersionIncremental());
        deviceDataMap.put("Build Version SDK", String.valueOf(easyDeviceMod.getBuildVersionSDK()));
        deviceDataMap.put("Build ID", easyDeviceMod.getBuildID());

        @DeviceType int deviceType = easyDeviceMod.getDeviceType(this);
        switch (deviceType) {
            case DeviceType.WATCH:
                deviceDataMap.put(getString(R.string.device_type), "watch");
                break;
            case DeviceType.PHONE:
                deviceDataMap.put(getString(R.string.device_type), "phone");
                break;
            case DeviceType.PHABLET:
                deviceDataMap.put(getString(R.string.device_type), "phablet");
                break;
            case DeviceType.TABLET:
                deviceDataMap.put(getString(R.string.device_type), "tablet");
                break;
            case DeviceType.TV:
                deviceDataMap.put(getString(R.string.device_type), "tv");
                break;
            default:
                //do nothing
                break;
        }

        @PhoneType int phoneType = easyDeviceMod.getPhoneType();
        switch (phoneType) {
            case PhoneType.CDMA:
                deviceDataMap.put(getString(R.string.phone_type), "CDMA");
                break;
            case PhoneType.GSM:
                deviceDataMap.put(getString(R.string.phone_type), "GSM");
                break;
            case PhoneType.NONE:
                deviceDataMap.put(getString(R.string.phone_type), "None");
                break;
            default:
                deviceDataMap.put(getString(R.string.phone_type), "Unknown");
                break;
        }

        @OrientationType int orientationType = easyDeviceMod.getOrientation(this);
        switch (orientationType) {
            case OrientationType.LANDSCAPE:
                deviceDataMap.put(getString(R.string.orientation), "Landscape");
                break;
            case OrientationType.PORTRAIT:
                deviceDataMap.put(getString(R.string.orientation), "Portrait");
                break;
            case OrientationType.UNKNOWN:
            default:
                deviceDataMap.put(getString(R.string.orientation), "Unknown");
                break;
        }

        // App Mod
        EasyAppMod easyAppMod = new EasyAppMod(this);
        deviceDataMap.put("Installer Store", easyAppMod.getStore());
        deviceDataMap.put("App Name", easyAppMod.getAppName());
        deviceDataMap.put("Package Name", easyAppMod.getPackageName());
        deviceDataMap.put("Activity Name", easyAppMod.getActivityName());
        deviceDataMap.put("App version", easyAppMod.getAppVersion());
        deviceDataMap.put("App versioncode", easyAppMod.getAppVersionCode());
        deviceDataMap.put("Does app have Camera permission?", String.valueOf(easyAppMod.isPermissionGranted(Manifest.permission.CAMERA)));

        //Network Mod
        EasyNetworkMod easyNetworkMod = new EasyNetworkMod(this);
        deviceDataMap.put("WIFI MAC Address", easyNetworkMod.getWifiMAC());
        deviceDataMap.put("WIFI LinkSpeed", easyNetworkMod.getWifiLinkSpeed());
        deviceDataMap.put("WIFI SSID", easyNetworkMod.getWifiSSID());
        deviceDataMap.put("WIFI BSSID", easyNetworkMod.getWifiBSSID());
        deviceDataMap.put("IPv4 Address", easyNetworkMod.getIPv4Address());
        deviceDataMap.put("IPv6 Address", easyNetworkMod.getIPv6Address());
        deviceDataMap.put("Network Available", String.valueOf(easyNetworkMod.isNetworkAvailable()));
        deviceDataMap.put("Wi-Fi enabled", String.valueOf(easyNetworkMod.isWifiEnabled()));

        @NetworkType int networkType = easyNetworkMod.getNetworkType();

        switch (networkType) {
            case NetworkType.CELLULAR_UNKNOWN:
                deviceDataMap.put(getString(R.string.network_type), "Cellular Unknown");
                break;
            case NetworkType.CELLULAR_UNIDENTIFIED_GEN:
                deviceDataMap.put(getString(R.string.network_type), "Cellular Unidentified Generation");
                break;
            case NetworkType.CELLULAR_2G:
                deviceDataMap.put(getString(R.string.network_type), "Cellular 2G");
                break;
            case NetworkType.CELLULAR_3G:
                deviceDataMap.put(getString(R.string.network_type), "Cellular 3G");
                break;
            case NetworkType.CELLULAR_4G:
                deviceDataMap.put(getString(R.string.network_type), "Cellular 4G");
                break;
            case NetworkType.WIFI_WIFIMAX:
                deviceDataMap.put(getString(R.string.network_type), "Wifi/WifiMax");
                break;
            case NetworkType.UNKNOWN:
            default:
                deviceDataMap.put(getString(R.string.network_type), "Unknown");
                break;
        }

        // Battery Mod
        EasyBatteryMod easyBatteryMod = new EasyBatteryMod(this);
        deviceDataMap.put("Battery Percentage",
                String.valueOf(easyBatteryMod.getBatteryPercentage()) + "%");
        deviceDataMap.put("Is device charging", String.valueOf(easyBatteryMod.isDeviceCharging()));
        deviceDataMap.put("Battery present", String.valueOf(easyBatteryMod.isBatteryPresent()));
        deviceDataMap.put("Battery technology", String.valueOf(easyBatteryMod.getBatteryTechnology()));
        deviceDataMap.put("Battery temperature",
                String.valueOf(easyBatteryMod.getBatteryTemperature()) + " deg C");
        deviceDataMap.put("Battery voltage",
                String.valueOf(easyBatteryMod.getBatteryVoltage()) + " mV");

        @BatteryHealth int batteryHealth = easyBatteryMod.getBatteryHealth();
        switch (batteryHealth) {
            case BatteryHealth.GOOD:
                deviceDataMap.put("Battery health", "Good");
                break;
            case BatteryHealth.HAVING_ISSUES:
            default:
                deviceDataMap.put("Battery health", "Having issues");
                break;
        }

        @ChargingVia int isChargingVia = easyBatteryMod.getChargingSource();
        switch (isChargingVia) {
            case ChargingVia.AC:
                deviceDataMap.put(getString(R.string.device_charging_via), "AC");
                break;
            case ChargingVia.USB:
                deviceDataMap.put(getString(R.string.device_charging_via), "USB");
                break;
            case ChargingVia.WIRELESS:
                deviceDataMap.put(getString(R.string.device_charging_via), "Wireless");
                break;
            case ChargingVia.UNKNOWN_SOURCE:
            default:
                deviceDataMap.put(getString(R.string.device_charging_via), "Unknown Source");
                break;
        }

        //Bluetooth Mod
        EasyBluetoothMod easyBluetoothMod = new EasyBluetoothMod(this);
        deviceDataMap.put("BT MAC Address", easyBluetoothMod.getBluetoothMAC());

        // Display Mod
        EasyDisplayMod easyDisplayMod = new EasyDisplayMod(this);
        deviceDataMap.put("Display Resolution", easyDisplayMod.getResolution());
        deviceDataMap.put("Screen Density", easyDisplayMod.getDensity());
        deviceDataMap.put("Screen Size", String.valueOf(easyDisplayMod.getPhysicalSize()));
        deviceDataMap.put("Screen Refresh rate", String.valueOf(easyDisplayMod.getRefreshRate()) + " Hz");

        deviceDataMap.put("Email ID", emailString.toString());

        // Location Mod
        EasyLocationMod easyLocationMod = new EasyLocationMod(this);
        double[] l = easyLocationMod.getLatLong();
        String lat = String.valueOf(l[0]);
        String lon = String.valueOf(l[1]);
        deviceDataMap.put("Latitude", lat);
        deviceDataMap.put("Longitude", lon);

        // Memory Mod
        EasyMemoryMod easyMemoryMod = new EasyMemoryMod(this);
        deviceDataMap.put("Total RAM", String.valueOf(easyMemoryMod.convertToGb(easyMemoryMod.getTotalRAM())) + " Gb");
        deviceDataMap.put("Available Internal Memory", String.valueOf(easyMemoryMod.convertToGb(easyMemoryMod.getAvailableInternalMemorySize())) + " Gb");
        deviceDataMap.put("Available External Memory", String.valueOf(easyMemoryMod.convertToGb(easyMemoryMod.getAvailableExternalMemorySize())) + " Gb");
        deviceDataMap.put("Total Internal Memory", String.valueOf(easyMemoryMod.convertToGb(easyMemoryMod.getTotalInternalMemorySize())) + " Gb");
        deviceDataMap.put("Total External memory", String.valueOf(easyMemoryMod.convertToGb(easyMemoryMod.getTotalExternalMemorySize())) + " Gb");

        // CPU Mod
        EasyCpuMod easyCpuMod = new EasyCpuMod();
        deviceDataMap.put("Supported ABIS", easyCpuMod.getStringSupportedABIS());
        deviceDataMap.put("Supported 32 bit ABIS", easyCpuMod.getStringSupported32bitABIS());
        deviceDataMap.put("Supported 64 bit ABIS", easyCpuMod.getStringSupported64bitABIS());

        // NFC Mod
        EasyNfcMod easyNfcMod = new EasyNfcMod(this);
        deviceDataMap.put("is NFC present", String.valueOf(easyNfcMod.isNfcPresent()));
        deviceDataMap.put("is NFC enabled", String.valueOf(easyNfcMod.isNfcEnabled()));

        return deviceDataMap;
    }
}
