package com.tuinercia.inercia.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.tuinercia.inercia.R;
import com.tuinercia.inercia.adapter.EstudioSeleccionSemanaAdapter;
import com.tuinercia.inercia.adapter.HorariosRecycleAdapter;
import com.tuinercia.inercia.model.Horario;
import com.tuinercia.inercia.utils.TypeFaceCustom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ricar on 29/09/2017.
 */

public class EstudioAgenda extends AppCompatActivity implements AdapterView.OnItemClickListener{

    GridView grd_dias_semana;
    RecyclerView rcv_horarios;
    TextView text_estudios_description
            , label_estudio_description
            , label_estudio_agenda_tu_clase
            , label_estudio_direccion;
    Button button_estudio_orientame;
    Toolbar toolbar;



    HorariosRecycleAdapter horariosAdapter;
    List<Horario> horarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_estudio_agenda);

        grd_dias_semana             = (GridView) findViewById(R.id.grid_dias_semana);
        text_estudios_description   = (TextView) findViewById(R.id.text_estudio_description);
        label_estudio_description   = (TextView) findViewById(R.id.lebel_estudio_description);
        label_estudio_agenda_tu_clase = (TextView) findViewById(R.id.label_estudio_agenda_tu_clase);
        label_estudio_direccion       = (TextView) findViewById(R.id.label_estudio_direccion);
        button_estudio_orientame      = (Button) findViewById(R.id.button_estudio_orientame);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);

        label_estudio_description.setTypeface(TypeFaceCustom.getInstance(this).UBUNTU_TYPE_FACE);
        label_estudio_agenda_tu_clase.setTypeface(TypeFaceCustom.getInstance(this).UBUNTU_TYPE_FACE);
        label_estudio_direccion.setTypeface(TypeFaceCustom.getInstance(this).UBUNTU_TYPE_FACE);
        button_estudio_orientame.setTypeface(TypeFaceCustom.getInstance(this).UBUNTU_TYPE_FACE);




        rcv_horarios = (RecyclerView) findViewById(R.id.rcv_horarios);

        horariosAdapter = new HorariosRecycleAdapter(horarios);
        EstudioSeleccionSemanaAdapter adapter = new EstudioSeleccionSemanaAdapter(this);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcv_horarios.setLayoutManager(mLayoutManager);
        rcv_horarios.setItemAnimator(new DefaultItemAnimator());



        rcv_horarios.setAdapter(horariosAdapter);
        grd_dias_semana.setAdapter(adapter);
        grd_dias_semana.setOnItemClickListener(this);

        setCurrentDayofTheWeek();
        prepareMovieData();
    }

    private void prepareMovieData(){
        Horario horario = new Horario("Jue 17 Nov, 6AM, Samantha",false);
        horarios.add(horario);

        horario = new Horario("Jue 8 Nov, 6AM, Ricardo",false);
        horarios.add(horario);
        horario = new Horario("Jue 8 Nov, 10AM, Jesús",false);
        horarios.add(horario);
        horario = new Horario("Jue 8 Nov, 6AM, Samantha",false);
        horarios.add(horario);
        horario = new Horario("Jue 8 Nov, 6AM, Ricardo",false);
        horarios.add(horario);

        horariosAdapter.notifyDataSetChanged();
    }

    private void setCurrentDayofTheWeek(){
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);
        switch (day){
            case Calendar.SUNDAY:
                grd_dias_semana.setItemChecked(6,true);
                break;
            case Calendar.MONDAY:
                grd_dias_semana.setItemChecked(0,true);
                break;
            case Calendar.TUESDAY:
                grd_dias_semana.setItemChecked(1,true);
                break;
            case Calendar.WEDNESDAY:
                grd_dias_semana.setItemChecked(2,true);
                break;
            case Calendar.THURSDAY:
                grd_dias_semana.setItemChecked(3,true);
                break;
            case Calendar.FRIDAY:
                grd_dias_semana.setItemChecked(4,true);
                break;
            case Calendar.SATURDAY:
                grd_dias_semana.setItemChecked(5,true);
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
