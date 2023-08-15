package com.example.attendanceacademia.Student;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceacademia.Models.attendModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.Student.std_dash;
import com.example.attendanceacademia.dashMyapi;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.example.attendanceacademia.dashMyapi.BASE_URL;


public class view_detain extends AppCompatActivity {


    String enrollment;
    int countCMTS=0,countDWPD=0,countCNS=0,countJAVA=0;
    int totalCMTS=0,totalDWPD=0,totalCNS=0,totalJAVA=0;
    float dwpd,cmts,cns,java;
    TextView dwpd_per,dwpd_detain;
    TextView cmts_per,cmts_detain;
    TextView cns_per,cns_detain;
    TextView java_per,java_detain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detain);



        //Reference
        dwpd_per = (TextView) findViewById(R.id.dwpd_attend);
        dwpd_detain = (TextView) findViewById(R.id.dwpd_detain);

        cmts_per = (TextView) findViewById(R.id.cmts_attend);
        cmts_detain = (TextView) findViewById(R.id.cmts_detain);

        cns_per = (TextView) findViewById(R.id.cns_attend);
        cns_detain = (TextView) findViewById(R.id.cns_detain);

        java_per = (TextView) findViewById(R.id.java_attend);
        java_detain = (TextView) findViewById(R.id.java_detain);

        enrollment = std_dash.enrollment;
        displayData();
    }

    private void displayData() {
       /* url = "http://10.0.2.2/AAMS/public/practice/";*/
        //Retrofit Object Creation
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dashMyapi detainapi = retrofit.create(dashMyapi.class);
        Call <List<attendModel>> detaincall = detainapi.attendModel();
        detaincall.enqueue(new Callback<List<attendModel>>() {

            @Override
            public void onResponse(Call<List<attendModel>> call, Response<List<attendModel>> response) {
                List<attendModel> data = response.body();

                for(int i = 0; i < data.size(); i++){
                    String tempEnroll = data.get(i).getEnrollment();
                    if(tempEnroll.equals(enrollment)){
                        String subj = data.get(i).getSubject_name();
                        if(subj.equals("CMTS")){
                            totalCMTS++;
                            if(data.get(i).getAttend()==1){
                                countCMTS++;
                            }
                        }
                        else if (subj.equals("DWPD")){
                            totalDWPD++;
                            if(data.get(i).getAttend()==1){
                                countDWPD++;
                            }
                        }
                        else if (subj.equals("CNS")){
                            totalCNS++;
                            if(data.get(i).getAttend()==1){
                                countCNS++;
                            }
                        }
                        else if (subj.equals("JAVA")){
                            totalJAVA++;
                            if(data.get(i).getAttend()==1){
                                countJAVA++;
                            }
                        }
                    }
                }
                setData();
            }

            @Override
            public void onFailure(Call<List<attendModel>> call, Throwable t) {

            }
        });//Call Bracket
    }

    private void setData() {

        if(totalDWPD==0) {
            totalDWPD=1;
        }
        if(totalCMTS==0){
            totalCMTS=1;
        }
        if(totalCNS==0){
            totalCNS=1;
        }
        if(totalJAVA==0){
            totalJAVA=1;
        }
        dwpd = (countDWPD * 100)/totalDWPD;
        cmts = (countCMTS * 100)/totalCMTS;
        cns = (countCNS * 100)/totalCNS;
        java = (countJAVA * 100)/totalJAVA;
        dwpd_per.setText(String.valueOf(dwpd)+"%");
        cmts_per.setText(String.valueOf(cmts)+"%");
        cns_per.setText(String.valueOf(cns)+"%");
        java_per.setText(String.valueOf(java)+"%");

        if(dwpd == 0){
            dwpd_detain.setText("Nil");
        }else if(dwpd < 50){
            dwpd_detain.setText("Less");
        }else{
            dwpd_detain.setText("Perfect");
        }

        if(cmts == 0){
            cmts_detain.setText("Nil");
        }else if(cmts < 50){
            cmts_detain.setText("Less");
        }else{
            cmts_detain.setText("Perfect");
        }

        if(cns == 0){
            cns_detain.setText("Nil");
        }else if(dwpd < 50){
            cns_detain.setText("Less");
        }else{
            cns_detain.setText("Perfect");
        }

        if(java == 0){
            java_detain.setText("Nil");
        }else if(java < 50){
            java_detain.setText("Less");
        }else{
            java_detain.setText("Perfect");
        }

    }

}