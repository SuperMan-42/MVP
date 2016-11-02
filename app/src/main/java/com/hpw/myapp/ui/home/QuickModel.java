package com.hpw.myapp.ui.home;

import com.hpw.myapp.DataServer;

import java.util.List;

/**
 * Created by hpw on 16/11/1.
 */

public class QuickModel implements QuickContract.Model {
    @Override
    public List<Status> getData() {
        return DataServer.getSampleData(6);
    }
}
