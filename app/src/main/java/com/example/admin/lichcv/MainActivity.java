package com.example.admin.lichcv;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btn_Date, btn_Time,btn_themCV;
    EditText edt_CongViec, edt_Noidung;
    TextView tv_ngayHT, tv_gioHT;

    //Khai báo DateSource Lưu trữ danh sách Công việc
    ArrayList<JohnWeek> arrJob = null;
    //Khai báo ArrayAdapter cho ListView
    ArrayAdapter<JohnWeek> arrayAdapter =null;


    ListView lv_CongViec;
    Date dateFinish;
    Date hourFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Date     = (Button) findViewById(R.id.btn_Date);
        btn_Time     = (Button) findViewById(R.id.btn_Time);
        btn_themCV   = (Button) findViewById(R.id.btn_themCV);
        edt_CongViec = (EditText) findViewById(R.id.edt_CongViec);
        edt_Noidung  = (EditText) findViewById(R.id.edt_Noidung);
        tv_ngayHT    = (TextView) findViewById(R.id.tv_ngayHT);
        tv_gioHT     = (TextView) findViewById(R.id.tv_gioHT);
        lv_CongViec  = (ListView) findViewById(R.id.lv_CongViec);

        arrJob = new ArrayList<JohnWeek>();
        //Gán Data Source (ArrayList object) vào ArrayAdapter
        //gán DataSource vào ArrayAdapter(Adapter là 1 cái trung gian lấy dữ liệu danh sách  và hiển thị lên listview)
        arrayAdapter = new ArrayAdapter<JohnWeek>(this,android.R.layout.simple_list_item_1,arrJob);
        //gán arrayAdapter vào ListView Công Việc
        lv_CongViec.setAdapter(arrayAdapter);

        btn_themCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edt_CongViec.getText()+" ";
                String description =  edt_Noidung.getText()+" ";

                JohnWeek job = new JohnWeek(title,description,tv_ngayHT.getText().toString(),tv_gioHT.getText().toString());
                arrJob.add(job);
                Toast.makeText(MainActivity.this, "Bạn đã thêm công việc mới", Toast.LENGTH_SHORT).show();
                //update data to show on listView
                arrayAdapter.notifyDataSetChanged();
                //sau khi thêm thì reset lại dữ liệu và cho focus tới edt_congviec
                edt_CongViec.setText(" ");
                edt_Noidung.setText(" ");
                tv_ngayHT.setText(" ");
                tv_gioHT.setText(" ");
                edt_CongViec.requestFocus();

            }
        });
       // Xử lý sự kiện chọn một phần tử trong ListView
        lv_CongViec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Nội Dung Chi Tiết");
                dialog.setMessage(arrJob.get(position).description);
                dialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.create().show();
            }
        });
        //xử lý sự kiện Long click
        lv_CongViec.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final  Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog);
                dialog.setCancelable(false);
                dialog.setTitle("Lịch Công Việc");
                dialog.show();

                Button btn_xoa = (Button) dialog.findViewById(R.id.btn_xoa);
                Button btn_uchiu = (Button) dialog.findViewById(R.id.btn_uchiu);

                btn_xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrJob.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                btn_uchiu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });


                return false;
            }
        });
    }
    //showDate
    public void showDate(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv_ngayHT.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(this, onDateSetListener,  year, month ,day);
        dialog.setTitle("Chọn ngày hoàn thành");
        dialog.show();

    }

    //show time
    public void showTime(View view){
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tv_gioHT.setText(hourOfDay+" : "+minute);
            }
        };
        TimePickerDialog dialog = new TimePickerDialog(this, onTimeSetListener, hour, min, true);
        dialog.setTitle("Chọn giờ hoàn thành");
        dialog.show();

    }

    //Thêm Công Viêc
    /* public void AddCongViec(View view){
         String title = edt_CongViec.getText()+" ";
         String description =  edt_Noidung.getText()+" ";

         JohnWeek job = new JohnWeek(title,description,dateFinish,hourFinish);
         arrJob.add(job);
         Toast.makeText(MainActivity.this, "Bạn đã thêm công việc mới", Toast.LENGTH_SHORT).show();
         //update data to show on listView
         arrayAdapter.notifyDataSetChanged();
         //sau khi thêm thì reset lại dữ liệu và cho focus tới edt_congviec
         edt_CongViec.setText(" ");
         edt_Noidung.setText(" ");
         edt_CongViec.requestFocus();}
     */

}
