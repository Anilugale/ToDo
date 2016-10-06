package com.marocks.todo.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.marocks.todo.MainActivity;
import com.marocks.todo.R;
import com.marocks.todo.Utile;
import com.marocks.todo.api.ApiUtil;
import com.marocks.todo.model.Schedule;
import com.marocks.todo.model.ToDoItem;
import com.marocks.todo.model.ToDoItemUpload;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

public  class CreateTodoDialog {


   static MainActivity mainActivity;

    public static void showDialog(final MainActivity context, final View view){
        final View dialogView = View.inflate(context, R.layout.dialog_view, null);
        mainActivity = context;
        AlertDialog.Builder builder =  new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setCancelable(false);


        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                revealShow(dialogView, true, null,view);
            }
        });
        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealShow(dialogView, false, dialog,view);
            }
        });
        dialogView.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                TextView note= (TextView) dialogView.findViewById(R.id.note);
                EditText date= (EditText) dialogView.findViewById(R.id.date);
                EditText time= (EditText) dialogView.findViewById(R.id.time);
                RadioGroup radioGroup= (RadioGroup) dialogView.findViewById(R.id.radioGrp);

                if(note.getText().toString().trim().length() ==0){
                    note.setError("Please Update note for Todo");
                    note.requestFocus();
                    return;
                }

                Utile.isToDoCreated = true;
                ToDoItemUpload toDoItemUpload = new ToDoItemUpload();
                toDoItemUpload.setSchedule_name(note.getText().toString());
                toDoItemUpload.setDesc(note.getText().toString());
                toDoItemUpload.setState("pending");
                Schedule schedule = new Schedule();
                schedule.setDate(date.getText().toString());
                schedule.setTime(date.getText().toString()+"T0"+time.getText().toString().trim()+":00.007Z");

                if(radioGroup.getCheckedRadioButtonId() == R.id.daily){
                    schedule.setRule("daily");
                }else {
                    schedule.setRule("weekly");
                    schedule.setDay(getSelectDays(dialogView));


                }

                toDoItemUpload.setSchedule_attributes(schedule);
                Log.e( "onClick: ", "{ \"todo\":"+ApiUtil.gson.toJson(toDoItemUpload)+"}");

                toDoItemUpload.setPriority((int) (Math.abs(Math.random()*100)%10));

                try {
                    ApiUtil.jsonRequest(mainActivity,ApiUtil.todos,new JSONObject("{ \"todo\":"+ApiUtil.gson.toJson(toDoItemUpload)+"}"), Request.Method.POST);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(mainActivity!=null){
                    mainActivity.done();
                }
                revealShow(dialogView, false, dialog,view);
            }
        });

        Calendar now = Calendar.getInstance();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        String date = now.get(Calendar.YEAR)+ "-"+(now.get(Calendar.MONTH) + 1)+"-" + now.get(Calendar.DAY_OF_MONTH);
        ((EditText)dialog.findViewById(R.id.date)).setText(date);
        dialog.findViewById(R.id.date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Calendar now = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.d("Date", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);
                        String date =year+ "-"+(month + 1)+"-" + dayOfMonth ;
                        ((EditText)v).setText(date);
                    }
                };

                DatePickerDialog dpd = new DatePickerDialog(
                        mainActivity,listener,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                dpd.show();
            }
        });
        String time =now.get(Calendar.HOUR_OF_DAY)+ ":"+(now.get(Calendar.MINUTE) + 1);
        ((EditText)dialog.findViewById(R.id.time)).setText(time);
        dialog.findViewById(R.id.time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuts) {
                        String AM_PM ;
                        if(hourOfDay < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        String date =hourOfDay+ ":"+(minuts )+":" + AM_PM;
                        ((EditText)v).setText(date);
                    }
                };
                Calendar now = Calendar.getInstance();
                TimePickerDialog dpd = new TimePickerDialog(
                        mainActivity,listener,now.HOUR,now.MINUTE,false);


                dpd.show();
            }
        });

        ((RadioGroup)dialogView.findViewById(R.id.radioGrp)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.daily){
                    dialogView.findViewById(R.id.weekly_layout).setVisibility(View.GONE);
                }else{
                    dialogView.findViewById(R.id.weekly_layout).setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private static String[] getSelectDays(View dialogView) {
        ArrayList<String> days=new ArrayList<>();

        if(((CheckBox)dialogView.findViewById(R.id.sun)).isChecked()){
            days.add("sunday");
        }

        if(((CheckBox)dialogView.findViewById(R.id.mon)).isChecked()){
            days.add("monday");
        }

        if(((CheckBox)dialogView.findViewById(R.id.tus)).isChecked()){
            days.add("tuesday");
        }

        if(((CheckBox)dialogView.findViewById(R.id.wen)).isChecked()){
            days.add("wednesday");
        }

        if(((CheckBox)dialogView.findViewById(R.id.the)).isChecked()){
            days.add("thursday");
        }

        if(((CheckBox)dialogView.findViewById(R.id.fir)).isChecked()){
            days.add("friday");
        }

        if(((CheckBox)dialogView.findViewById(R.id.sat)).isChecked()){
            days.add("saturday");
        }

        return days.toArray(new String[0]);

    }

    private static  void revealShow(View rootView, boolean reveal, final AlertDialog dialog,View fromView){
        final View view = rootView.findViewById(R.id.reveal_view);

        float maxRadius = mainActivity.getWindow().getWindowManager().getDefaultDisplay().getHeight();

        int data[] = new int[2];
        fromView.getLocationOnScreen(data);

        int w1 = data[0];
        int h1 = data[1];

        if(reveal){
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view,
                    w1 , h1, 0, maxRadius);
            revealAnimator.setDuration(500);
            view.setVisibility(View.VISIBLE);
            revealAnimator.start();

        } else {

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, w1+(fromView.getWidth()/2) , h1 , maxRadius, 0);
            anim.setDuration(500);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);
                }
            });

            anim.start();
        }

    }
}