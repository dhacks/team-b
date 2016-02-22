package com.ice.creame.lollopop;

import android.app.Application;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by hideya on 2016/02/20.
 */
public class Globals extends Application {
    public int indexFlag = -1; //２：２の場合２
    public int node_id = 0;
    public int name_index = 0; //ユーザーのインデックス
    public int node_index = 0;
    public int ahp_hierarchy = 1; //ahpの階層
    public Vector<String> nameM = new Vector<>(); //男の名前リスト
    public Vector<String> nameF = new Vector<>(); //女

    //一対比較行列
    public double matrixForWeight[][];
    public double matrixForCombine[][];
    public double combinedMatrix[][];
    public double weight[];
    public double personResult[]; //ahpの結果
    public double peopleResult[][]; //personResultを全員分まとめたもの
    public double finalResult[][];
    public double rank[][];

    public void GlobalsAllInit() {
        indexFlag = -1;
        GlobalsNameInit();
        GlobalsAHPnetInit();
    }

    public void GlobalsNameInit() {
        nameM.removeAllElements();
        nameF.removeAllElements();
    }

    public void GlobalsAHPnetInit() {
        node_id = 0;
        name_index = 0;
        node_index = 0;
        ahp_hierarchy = 1;
        matrixForWeight = null;
        matrixForCombine = null;
        combinedMatrix = null;
        weight = null;
        personResult = null;
        peopleResult = null;
        finalResult = null;
        rank = null;
    }

    public void GlobalsALLmatrixSet(int indexFlag, int node_length) {
        matrixForWeight = new double[node_length][node_length];
        for (int i = 0; i < node_length; i++) {
            Arrays.fill(matrixForWeight[i], 1.0);
        }
        matrixForCombine = new double[indexFlag][indexFlag];
        for (int i = 0; i < indexFlag; i++) {
            Arrays.fill(matrixForCombine[i], 1.0);
        }
        combinedMatrix = new double[indexFlag][node_length];
        weight = new double[node_length];
        personResult = new double[indexFlag];
        peopleResult = new double[indexFlag * 2][indexFlag];
    }

    public void GlobalsPortmatrixSet(int indexFlag, int node_length) {
        matrixForWeight = new double[node_length][node_length];
        for (int i = 0; i < node_length; i++) {
            Arrays.fill(matrixForWeight[i], 1.0);
        }
        matrixForCombine = new double[indexFlag][indexFlag];
        for (int i = 0; i < indexFlag; i++) {
            Arrays.fill(matrixForCombine[i], 1.0);
        }
        combinedMatrix = new double[indexFlag][node_length];
        weight = new double[node_length];
        personResult = new double[indexFlag];
    }
}
