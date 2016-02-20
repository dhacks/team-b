package com.ice.creame.lollopop;

import android.app.Application;

import java.util.Vector;

/**
 * Created by hideya on 2016/02/20.
 */
public class Globals extends Application {
    public int indexFlag = -1;
    public Vector<String> nameM = new Vector<>();
    public Vector<String> nameF = new Vector<>();

    public void GlobalsAllInit(){
        indexFlag = -1;
        GlobalsIsNameInit();
    }

    public void GlobalsIsNameInit(){
        nameM.removeAllElements();
        nameF.removeAllElements();
    }


}