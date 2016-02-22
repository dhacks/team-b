package com.ice.creame.lollopop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static com.ice.creame.lollopop.AHPCalculation.getRank;
import static com.ice.creame.lollopop.MethodLibrary.makeButton;
import static com.ice.creame.lollopop.MethodLibrary.makeLinearLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeRelativeLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeScrollView;
import static com.ice.creame.lollopop.MethodLibrary.makeTextView;

import static com.ice.creame.lollopop.AHPCalculation.powerMethod;
import static com.ice.creame.lollopop.AHPCalculation.matrixMultiplication;
import static com.ice.creame.lollopop.AHPCalculation.getFinalResult;

/**
 * Created by hideya on 2016/02/20.
 */
public class QuestionActivity extends BaseActivity implements View.OnClickListener {

    TextView text_name;
    TextView text_about;
    TextView text_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* パラメータ設定 */
        //タイトル用
        RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(WC, WC);
        param1.addRule(RelativeLayout.CENTER_HORIZONTAL);

        //ボタン用
        RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(WC, WC);
        param2.addRule(RelativeLayout.CENTER_HORIZONTAL);

        /* 画面レイアウト */
        LinearLayout li_la_super = makeLinearLayout(COLOR_D, LinearLayout.VERTICAL, null, this);
        li_la_super.setBackgroundResource(BACK_GROUND_IMAGE);

        setContentView(li_la_super);

        if (globals.name_index < globals.nameM.size()) {
            text_name = makeTextView(globals.nameM.elementAt(globals.name_index) + "の番", 40, TITLE_COLOR, NO_ID, makeRelativeLayout(COLOR_3, li_la_super, null, this), param1, this);
        } else {
            text_name = makeTextView(globals.nameF.elementAt(globals.name_index - globals.nameM.size()) + "の番", 40, TITLE_COLOR, NO_ID, makeRelativeLayout(COLOR_3, li_la_super, null, this), param1, this);
        }

        ScrollView sc_vi = makeScrollView(COLOR_1, li_la_super, this);
        LinearLayout li_la = makeLinearLayout(COLOR_1, LinearLayout.VERTICAL, sc_vi, this);

        String comp1;
        String comp2;

        if (NODE.length == 3) {
            comp1 = NODE[combination3[globals.node_index][0]];
            comp2 = NODE[combination3[globals.node_index][1]];
        } else {
            //たぶんこれは使わない
            comp1 = NODE[combination4[globals.node_index][0]];
            comp2 = NODE[combination4[globals.node_index][1]];
        }

        makeTextView(" ", 20, Color.RED, NO_ID, li_la, null, this);
        text_about = makeTextView("", 24, TEXT_COLOR_1, NO_ID, li_la, null, this);
        text_about.setGravity(Gravity.CENTER_HORIZONTAL);
        text_question = makeTextView(comp1 + " VS " + comp2, 32, TEXT_COLOR_1, NO_ID, li_la, null, this);
        text_question.setGravity(Gravity.CENTER_HORIZONTAL);
        makeTextView(" ", 20, Color.RED, NO_ID, li_la, null, this);

        int w = (int) (p.x / 1.3);
        int h = (int) (p.y / 7.9);
        for (int i = 0; i < SELECT.length * 2 - 1; i++) {
            Button button;
            if (i < SELECT.length - 1) {
                button = makeButton(comp1 + SELECT[i], i, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);
            } else if (i == SELECT.length - 1) {
                button = makeButton(SELECT[i], i, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);
            } else {
                button = makeButton(comp2 + SELECT[SELECT.length * 2 - 2 - i], i, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);
            }
            button.setTextColor(TEXT_COLOR_1);
            button.setTextSize(20);
            button.setWidth(w);
            button.setHeight(h);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = new Intent();

        /* ボタンから取得 */
        double temp = pair_comp[id]; // 9から1/9まで

        /* 一対比較行列の作成 */
        //上層
        if (globals.ahp_hierarchy == 1) {
            int x = -1;
            int y = -1;
            //今のところ3以外にするつもりはない
            if (NODE.length == 3) {
                x = combination3[globals.node_index][1];
                y = combination3[globals.node_index][0];
            }
            globals.matrixForWeight[y][x] = temp;
            globals.matrixForWeight[x][y] = 1.0 / temp;

        } else { //下層
            int x;
            int y;
            if (globals.indexFlag == 2) {
                x = combination2[globals.node_index][1];
                y = combination2[globals.node_index][0];
            } else if (globals.indexFlag == 3) {
                x = combination3[globals.node_index][1];
                y = combination3[globals.node_index][0];
            } else {
                x = combination4[globals.node_index][1];
                y = combination4[globals.node_index][0];
            }

            globals.matrixForCombine[y][x] = temp;
            globals.matrixForCombine[x][y] = 1.0 / temp;
        }

        /* べき乗法で固有ベクトルを求める */
        /* ahpの結果を求められるようにそれぞれの行列を形成 */

        //上層
        if (globals.ahp_hierarchy == 1) {
            globals.weight = powerMethod(globals.matrixForWeight);
        } else { //下層
            double eigenvector[] = powerMethod(globals.matrixForCombine);
            for (int i = 0; i < globals.indexFlag; i++) {
                globals.combinedMatrix[i][globals.node_id - 1] = eigenvector[i];
            }
        }

        String comp1;
        String comp2;

        boolean isScreenTransition = false;

        //変数を次の値に
        if (globals.ahp_hierarchy == 1 && globals.node_index == (NODE.length * (NODE.length - 1)) / 2 - 1) {
            globals.node_index = 0;
            globals.ahp_hierarchy++;
            globals.node_id++;
        } else if (globals.ahp_hierarchy == 1) {
            globals.node_index++;
            //ユーザチェンジ
        } else if (globals.ahp_hierarchy == 2 && globals.node_index == (globals.nameF.size() * (globals.nameF.size() - 1)) / 2 - 1 && globals.node_id == NODE.length) {

            /* ===========スタブ=============== */
            globals.personResult = matrixMultiplication(globals.combinedMatrix, globals.weight);

            globals.peopleResult[globals.name_index] = globals.personResult;

            globals.name_index++;
            globals.node_index = 0;
            globals.ahp_hierarchy = 1;
            globals.node_id = 0;
            isScreenTransition = true;

            globals.GlobalsPortmatrixSet(globals.indexFlag, NODE.length);

            //遷移
            if (globals.name_index < globals.nameM.size() + globals.nameF.size()) {
                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.BeforeQuestionActivity");
            } else {
                globals.finalResult = getFinalResult(globals.peopleResult);
                globals.rank = getRank(globals.finalResult);
                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.BeforeResultActivity");
            }
            startActivity(intent);
            QuestionActivity.this.finish();

        } else if (globals.ahp_hierarchy == 2 && globals.node_index == (globals.nameF.size() * (globals.nameF.size() - 1)) / 2 - 1) {
            globals.node_index = 0;
            globals.node_id++;
        } else if (globals.ahp_hierarchy == 2) {
            globals.node_index++;
        }

        if (!isScreenTransition) {

            if (globals.node_id == 0) {
                text_about.setText("");
            } else {
                text_about.setText("「" + NODE[globals.node_id - 1] + "」のみで考えると");
            }

            if (globals.ahp_hierarchy == 1) {
                if (NODE.length == 2) {
                    comp1 = NODE[combination2[globals.node_index][0]];
                    comp2 = NODE[combination2[globals.node_index][1]];
                } else if (NODE.length == 3) {
                    comp1 = NODE[combination3[globals.node_index][0]];
                    comp2 = NODE[combination3[globals.node_index][1]];
                } else {
                    comp1 = NODE[combination4[globals.node_index][0]];
                    comp2 = NODE[combination4[globals.node_index][1]];
                }
            } else {

                if (globals.name_index < globals.nameM.size()) {

                    if (globals.nameF.size() == 2) {
                        comp1 = globals.nameF.elementAt(combination2[globals.node_index][0]);
                        comp2 = globals.nameF.elementAt(combination2[globals.node_index][1]);
                    } else if (globals.nameF.size() == 3) {
                        comp1 = globals.nameF.elementAt(combination3[globals.node_index][0]);
                        comp2 = globals.nameF.elementAt(combination3[globals.node_index][1]);
                    } else {
                        comp1 = globals.nameF.elementAt(combination4[globals.node_index][0]);
                        comp2 = globals.nameF.elementAt(combination4[globals.node_index][1]);
                    }
                } else {
                    if (globals.nameF.size() == 2) {
                        comp1 = globals.nameM.elementAt(combination2[globals.node_index][0]);
                        comp2 = globals.nameM.elementAt(combination2[globals.node_index][1]);
                    } else if (globals.nameM.size() == 3) {
                        comp1 = globals.nameM.elementAt(combination3[globals.node_index][0]);
                        comp2 = globals.nameM.elementAt(combination3[globals.node_index][1]);
                    } else {
                        comp1 = globals.nameM.elementAt(combination4[globals.node_index][0]);
                        comp2 = globals.nameM.elementAt(combination4[globals.node_index][1]);
                    }
                }
            }

            text_question.setText(comp1 + " VS " + comp2);

            //男女の切り替え
            if (globals.name_index < globals.nameM.size()) {
                text_name.setText(globals.nameM.elementAt(globals.name_index) + "の番");
            } else {
                text_name.setText(globals.nameF.elementAt(globals.name_index - globals.nameM.size()) + "の番");
            }

            for (int i = 0; i < SELECT.length * 2 - 1; i++) {
                Button b = (Button) findViewById(i);
                if (i < SELECT.length - 1) {
                    b.setText(comp1 + SELECT[i]);
                } else if (i == SELECT.length - 1) {
                    b.setText(SELECT[i]);
                } else {
                    b.setText(comp2 + SELECT[SELECT.length * 2 - 2 - i]);
                }
            }
        }
    }

    // BACKボタンで終了させる
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this).setTitle("確認").setMessage("途中で終了すると今までに回答したデータが失われますがよろしいですか？")
                    .setPositiveButton("はい", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            QuestionActivity.this.finish();
                        }
                    }).setNegativeButton("いいえ", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();

            return true;
        }
        return false;
    }

}
