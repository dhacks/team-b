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
import android.widget.FrameLayout;
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
    //質問回数のカウント
    private static int count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.question);
        /* パラメータ設定 */

        //ボタン用
        RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(WC, WC);
        param2.addRule(RelativeLayout.CENTER_HORIZONTAL);


        LinearLayout parent = (LinearLayout) findViewById(R.id.parent5);
        parent.setBackgroundResource(BACK_GROUND_IMAGE);

        FrameLayout fl_2 = (FrameLayout) findViewById(R.id.fl5_2);
        fl_2.setBackgroundResource(R.drawable.waku2);

        text_name = (TextView) findViewById(R.id.textView5);


        if (globals.name_index < globals.nameM.size()) {
            text_name.setText(globals.nameM.elementAt(globals.name_index) + "の番");
        } else {
            text_name.setText(globals.nameF.elementAt(globals.name_index - globals.nameM.size()) + "の番");
        }
        text_name.setTextSize(TEXT_SIZE3);
        text_name.setTextColor(TITLE_COLOR);
        text_name.setTypeface(tf);


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


        LinearLayout li_la = (LinearLayout) findViewById(R.id.ll5);
        li_la.bringToFront();

        makeTextView(" ", TEXT_SIZE1, TEXT_COLOR_3, NO_ID, li_la, null, this);
        text_about = makeTextView(" ", 24, TEXT_COLOR_3, NO_ID, li_la, null, this);
        text_about.setGravity(Gravity.CENTER_HORIZONTAL);
        text_question = makeTextView(comp1 + " VS " + comp2, 32, TEXT_COLOR_3, NO_ID, li_la, null, this);
        text_question.setGravity(Gravity.CENTER_HORIZONTAL);

        int w = (int) (p.x / 1.3);
        int h = (int) (p.y / 7.9);
        for (int i = 0; i < SELECT1.length * 2 - 1; i++) {

            RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(w, h);
            param1.addRule(RelativeLayout.CENTER_HORIZONTAL);

            RelativeLayout re = new RelativeLayout(this);
            li_la.addView(re);

            FrameLayout fl = new FrameLayout(this);
            fl.setLayoutParams(param1);
            re.addView(fl);

            Button button = new Button(this);
            if (i < SELECT1.length - 1) {
                button.setText(comp1 + SELECT1[i]);
            } else if (i == SELECT1.length - 1) {
                button.setText(SELECT1[i]);
            } else {
                button.setText(comp2 + SELECT1[SELECT1.length * 2 - 2 - i]);
            }
            button.setId(i);
            button.setBackgroundResource(R.drawable.button);
            button.setTextColor(TEXT_COLOR_3);
            button.setTextSize(TEXT_SIZE2);
            button.setTypeface(tf);
            button.setOnClickListener((View.OnClickListener) this);
            fl.addView(button);
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

            //男女の切り替え
            if (globals.name_index < globals.nameM.size()) {
                text_name.setText(globals.nameM.elementAt(globals.name_index) + "の番");
            } else {
                text_name.setText(globals.nameF.elementAt(globals.name_index - globals.nameM.size()) + "の番");
            }

            text_question.setText(comp1 + " VS " + comp2);

            //回答ボタンの表記変更
            if (count < NODE.length - 1) {
                for (int i = 0; i < SELECT1.length * 2 - 1; i++) {
                    Button b = (Button) findViewById(i);
                    if (i < SELECT1.length - 1) {
                        b.setText(comp1 + SELECT1[i]);
                    } else if (i == SELECT1.length - 1) {
                        b.setText(SELECT1[i]);
                    } else {
                        b.setText(comp2 + SELECT1[SELECT1.length * 2 - 2 - i]);
                    }
                }
                count++;
            } else {
                for (int i = 0; i < SELECT2.length * 2 - 1; i++) {
                    Button b = (Button) findViewById(i);
                    if (i < SELECT2.length - 1) {
                        b.setText(comp1 + SELECT2[i]);
                    } else if (i == SELECT2.length - 1) {
                        b.setText(SELECT2[i]);
                    } else {
                        b.setText(comp2 + SELECT2[SELECT2.length * 2 - 2 - i]);
                    }
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
