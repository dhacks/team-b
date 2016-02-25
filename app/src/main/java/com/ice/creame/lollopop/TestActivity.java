package com.ice.creame.lollopop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Calendar;

import static com.ice.creame.lollopop.AHPCalculation.getFinalResult;
import static com.ice.creame.lollopop.AHPCalculation.getRank;
import static com.ice.creame.lollopop.AHPCalculation.matrixMultiplication;
import static com.ice.creame.lollopop.AHPCalculation.powerMethod;
import static com.ice.creame.lollopop.DBHelper.readDB;
import static com.ice.creame.lollopop.DBHelper.writeDB;
import static com.ice.creame.lollopop.MethodLibrary.makeButton;
import static com.ice.creame.lollopop.MethodLibrary.makeEditText;
import static com.ice.creame.lollopop.MethodLibrary.makeRelativeLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeTextView;

/**
 * Created by hideya on 2016/02/23.
 */
public class TestActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



    }


}
