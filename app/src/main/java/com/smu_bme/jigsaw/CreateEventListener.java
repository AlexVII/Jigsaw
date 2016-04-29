//package com.smu_bme.jigsaw;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.PopupMenu;
//import android.widget.PopupWindow;
//import android.widget.Toast;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
///**
// * Created by bme-lab2 on 4/30/16.
// */
//public class CreateEventListener extends View.OnClickListener {
//
//    private Context context;
//    private Calendar calendar;
//    private PopupMenu popupMenu;
//
//    public CreateEventListener(Context context, Calendar calendar){
//        this.context = context;
//        this.calendar = calendar;
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        LayoutInflater inflater = LayoutInflater.from(context);
//        final View layout = inflater.inflate(R.layout.dialog, null);
//        final EditText name = (EditText) layout.findViewById(R.id.create_name);
//        final EditText remark = (EditText) layout.findViewById(R.id.create_remark);
////        popupMenu = new PopupMenu(context, )
//        new AlertDialog.Builder(context).setTitle(context.getString(R.string.create)).setView(layout).setPositiveButton(context.getString(R.string.yes),
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String nameInput = name.getText().toString();
//                        String remarkInput = remark.getText().toString();
//                        if (nameInput.equals("")){
//                            Toast.makeText(context, context.getString(R.string.noName), Toast.LENGTH_SHORT).show();
//                        } else if (remarkInput.equals("")) {
//                            Toast.makeText(context, context.getString(R.string.noRemark), Toast.LENGTH_SHORT).show();
//                        } else {
////                                    DbData dbData = new DbData(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
//                        }
//                    }
//                }).setNegativeButton(context.getString(R.string.cancel), null).show();
//    }
//
//}
