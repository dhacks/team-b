package com.ice.creame.lollopop;

import android.app.Application;

import java.util.Vector;

/**
 * Created by hideya on 2016/02/20.
 */
public class Globals extends Application {
    public int indexFlag = -1;
    public int node_id = 0;
    public int name_index = 0;
    public int node_index = 0;
    public int ahp_hierarchy = 1; //ahpの階層
    public Vector<String> nameM = new Vector<>();
    public Vector<String> nameF = new Vector<>();

    public void GlobalsAllInit(){
        indexFlag = -1;
        GlobalsNameInit();
        GlobalsAHPnetInit();
    }

    public void GlobalsNameInit(){
        nameM.removeAllElements();
        nameF.removeAllElements();
    }

    public void GlobalsAHPnetInit(){
        node_id = 0;
        name_index = 0;
        node_index = 0;
        ahp_hierarchy = 1;
    }

}