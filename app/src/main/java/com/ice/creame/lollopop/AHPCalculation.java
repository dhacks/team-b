package com.ice.creame.lollopop;

import java.lang.*;
import java.util.Arrays;

/**
 * Created by hideya on 2016/02/21.
 */
public class AHPCalculation {

    public static double[] powerMethod(double[][] matrix) {

        //幾何平均法
        int n = matrix.length;
        double result[] = new double[n];
        Arrays.fill(result, 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i] *= matrix[i][j];
            }
            result[i] = Math.pow(result[i], (double) 1 / n);
        }

        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            sum += result[i];
        }
        for (int i = 0; i < n; i++) {
            result[i] = result[i] / sum;
        }

        //固有値法
        int iter = 0;
        int flag = 0;
        double w;
        double eigenvalue_old = 0; //固有値
        double eigenvalue_new = 0;
        double eigenvector[] = new double[n]; //固有ベクトル
        while (flag == 0) {
            iter = iter + 1;
            for (int i = 0; i < n; i++) {

                for (int j = 0; j < n; j++) {
                    eigenvector[i] += matrix[i][j] * result[j];
                }

            }
            sum = 0;
            for (int i = 0; i < n; i++) {
                sum += eigenvector[i];
            }
            eigenvalue_new = sum;
            for (int i = 0; i < n; i++) {
                eigenvector[i] /= sum;
            }

            flag = 1;

            //初回は古い固有値がないため別処理
            if (iter != 1) {

                if (Math.abs(eigenvalue_new - eigenvalue_old) > eigenvalue_old / 1000) {
                    eigenvalue_old = eigenvalue_new;
                    for (int k = 0; k < n; k++) {
                        result[k] = eigenvector[k];
                    }
                    flag = 0;
                }

                for (int i = 0; i < n; i++) {
                    if (Math.abs(result[i] - eigenvector[i]) > result[i] / 1000) {
                        eigenvalue_old = eigenvalue_new;
                        for (int k = 0; k < n; k++) {
                            result[k] = eigenvector[k];
                        }
                        flag = 0;
                    }
                }

            } else {
                eigenvalue_old = eigenvalue_new;
                for (int k = 0; k < n; k++) {
                    result[k] = eigenvector[k];
                }
            }
        }
        return result;
    }

    //行列の掛け算（スタブ）
    public static double [] matrixMultiplication(double a[][], double b[]){

        int row = a.length;
        double result[];
        if(row == 2){
            result = new double[2];
        }else if(row == 3){
            result = new double[3];
        }else{
            result = new double[4];
        }

        //1,2,3,…
        for(int i = 0; i < result.length; i++){
            result[i] = (double)i + 1;
        }

        return result;
    }

}
