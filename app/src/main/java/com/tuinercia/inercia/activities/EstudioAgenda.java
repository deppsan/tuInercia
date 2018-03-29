package com.tuinercia.inercia.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tuinercia.inercia.DTO.Parlor;
import com.tuinercia.inercia.DTO.Reservation;
import com.tuinercia.inercia.DTO.Schedule;
import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.adapter.EstudioSeleccionSemanaAdapter;
import com.tuinercia.inercia.adapter.HorariosRecycleAdapter;
import com.tuinercia.inercia.adapter.ReservationRecycleAdapter;
import com.tuinercia.inercia.fragments.dialogs.ConfirmarCancelarReservacionDialog;
import com.tuinercia.inercia.fragments.dialogs.ConfirmarReservarScheduleDialog;
import com.tuinercia.inercia.implementation.DialgoFragmentCancelFromAgendaListenerImpl;
import com.tuinercia.inercia.implementation.HorarioRecycleAdapterListenerImpl;
import com.tuinercia.inercia.implementation.InerciaApiCancelBookingListenerImpl;
import com.tuinercia.inercia.implementation.InerciaApiCreateBookingListenerImpl;
import com.tuinercia.inercia.implementation.InerciaApiGetScheduleByParlorListenerImpl;
import com.tuinercia.inercia.implementation.ReservationRecycleAdapterListenerImpl;
import com.tuinercia.inercia.interfaces.InerciaApiCreateBookingListener;
import com.tuinercia.inercia.network.InerciaApiClient;
import com.tuinercia.inercia.utils.TypeFaceCustom;
import com.tuinercia.inercia.utils.UtilsSharedPreference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.tuinercia.inercia.activities.MainPage.INTENT_EXTRA_HEADER_DISCIPLINE;

/**
 * Created by ricar on 29/09/2017.
 */

public class EstudioAgenda extends AppCompatActivity implements AdapterView.OnItemClickListener
                                                    , ConfirmarReservarScheduleDialog.ConfirmarReservarScheduleDialogListener
                                                    , View.OnClickListener{

    GridView grd_dias_semana;
    RecyclerView rcv_horarios, rcv_reservaciones;
    TextView text_estudios_description
            , label_estudio_description
            , label_estudio_agenda_tu_clase
            , label_estudio_direccion
            , text_estudios_direccion;
    ImageView img_toolbar_studio;
    Button button_estudio_orientame;
    Toolbar toolbar;

    Button btn_seleccion = null;

    static final String INTENT_EXTRA_HEADER = "parlor";

    HorariosRecycleAdapter horariosAdapter;
    ReservationRecycleAdapter reservacionAdapter;
    List<Schedule> horarios = new ArrayList<>();
    ArrayList<Schedule[]> schedules;
    List<Reservation> reservaciones = new ArrayList<>();
    ArrayList<Reservation[]> reservaciones_array;
    Parlor parlor;
    String discipline;

    User user;

    HorarioRecycleAdapterListenerImpl horariosListener;
    InerciaApiGetScheduleByParlorListenerImpl inerciaApiGetScheduleByParlorListener;
    InerciaApiCreateBookingListener inerciaApiCreateBookingListener;
    InerciaApiCancelBookingListenerImpl inerciaApiCancelBookingListener;
    ReservationRecycleAdapterListenerImpl reservationRecycleAdapterListener;
    DialgoFragmentCancelFromAgendaListenerImpl dialgoFragmentCancelFromAgendaListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UtilsSharedPreference.getInstance(getApplicationContext()).checkLogin();

        String json_parlor = getIntent().getStringExtra(INTENT_EXTRA_HEADER);
        discipline = getIntent().getStringExtra(INTENT_EXTRA_HEADER_DISCIPLINE);
        parlor = InerciaApiClient.getInstance(this).gson.fromJson(json_parlor,Parlor.class);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_estudio_agenda);

        grd_dias_semana             = (GridView) findViewById(R.id.grid_dias_semana);
        text_estudios_description   = (TextView) findViewById(R.id.text_estudio_description);
        label_estudio_description   = (TextView) findViewById(R.id.lebel_estudio_description);
        label_estudio_agenda_tu_clase = (TextView) findViewById(R.id.label_estudio_agenda_tu_clase);
        label_estudio_direccion       = (TextView) findViewById(R.id.label_estudio_direccion);
        button_estudio_orientame      = (Button) findViewById(R.id.button_estudio_orientame);
        text_estudios_direccion       = (TextView) findViewById(R.id.text_estudio_direccion);
        img_toolbar_studio            = (ImageView) findViewById(R.id.img_toolbar_studio);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);

        label_estudio_description.setTypeface(TypeFaceCustom.getInstance(this).UBUNTU_TYPE_FACE);
        label_estudio_agenda_tu_clase.setTypeface(TypeFaceCustom.getInstance(this).UBUNTU_TYPE_FACE);
        label_estudio_direccion.setTypeface(TypeFaceCustom.getInstance(this).UBUNTU_TYPE_FACE);
        button_estudio_orientame.setTypeface(TypeFaceCustom.getInstance(this).UBUNTU_TYPE_FACE);

        text_estudios_description.setText(parlor.getDescription());
        text_estudios_direccion.setText(parlor.getAddress());
        toolbar.setTitle(parlor.getName());
        Picasso.with(this).load(parlor.getPic1_url()).into(img_toolbar_studio);

        inerciaApiGetScheduleByParlorListener = new InerciaApiGetScheduleByParlorListenerImpl(this);
        EstudioSeleccionSemanaAdapter adapter = new EstudioSeleccionSemanaAdapter(this, setCurrentDayofTheWeek().toArray(new String[0]));

        /*Seccion para instanciar y hacer funcionar el recycler view de Horarios
        * */
        horariosListener = new HorarioRecycleAdapterListenerImpl(this);
        rcv_horarios = (RecyclerView) findViewById(R.id.rcv_horarios);
        horariosAdapter = new HorariosRecycleAdapter(horarios, this, horariosListener );

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcv_horarios.setLayoutManager(mLayoutManager);
        rcv_horarios.setItemAnimator(new DefaultItemAnimator());

        rcv_horarios.setAdapter(horariosAdapter);
        /**/

        /*Seccion para instanciar y hacer funcionar el recycler view de Reservaciones
        * */
        reservationRecycleAdapterListener = new ReservationRecycleAdapterListenerImpl(this);
        rcv_reservaciones = (RecyclerView) findViewById(R.id.rcv_reservations);
        reservacionAdapter = new ReservationRecycleAdapter(reservaciones,this, reservationRecycleAdapterListener);

        RecyclerView.LayoutManager res_mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcv_reservaciones.setLayoutManager(res_mLayoutManager);
        rcv_reservaciones.setItemAnimator(new DefaultItemAnimator());
        rcv_reservaciones.setAdapter(reservacionAdapter);



        grd_dias_semana.setAdapter(adapter);
        grd_dias_semana.setOnItemClickListener(this);

        user = UtilsSharedPreference.getInstance(this).getUser();
        inerciaApiCreateBookingListener = new InerciaApiCreateBookingListenerImpl(this);
        inerciaApiCancelBookingListener = new InerciaApiCancelBookingListenerImpl(this);
        dialgoFragmentCancelFromAgendaListener = new DialgoFragmentCancelFromAgendaListenerImpl(this);

        button_estudio_orientame.setOnClickListener(this);


        InerciaApiClient.getInstance(this).getScheduleByParlor(Integer.parseInt(discipline), parlor.getId(), user.getId(),inerciaApiGetScheduleByParlorListener);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(schedules.size() > 0){
            this.chargeScheduleList(schedules.get(position));
        }
        if (reservaciones_array.size() > 0){
            this.chargeReservationList(reservaciones_array.get(position));
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        UtilsSharedPreference.getInstance(getApplicationContext()).checkLogin();
    }
    @Override
    public void onCancelarConfirmacion() {
        btn_seleccion = null;
    }
    @Override
    public void onAceptarConfirmacion(int idSchedule) {
        InerciaApiClient.getInstance(this).createBooking(Integer.toString(UtilsSharedPreference.getInstance(this).getUser().getId()),Integer.toString(idSchedule),inerciaApiCreateBookingListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_estudio_orientame:
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + parlor.getCoord_x() + "," + parlor.getCoord_y());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(mapIntent);
                }
                break;
        }
    }

    public void chargeScheduleList(Schedule[] schedules) {
        horarios.clear();
        for (Schedule s : schedules){
            horarios.add(s);
        }
        horariosAdapter.notifyDataSetChanged();
    }
    public void chargeReservationList(Reservation[] reservations) {
        reservaciones.clear();
        for (Reservation s : reservations){
            reservaciones.add(s);
        }
        reservacionAdapter.notifyDataSetChanged();
    }
    public void setSchedules(ArrayList<Schedule[]> schedules) {
        this.schedules = schedules;
    }
    public void setReservaciones_array(ArrayList<Reservation[]> reservaciones_array) {
        this.reservaciones_array = reservaciones_array;
    }
    public void setBtn_seleccion(@Nullable View btn_seleccion){
        if (btn_seleccion != null){
            this.btn_seleccion = (Button) btn_seleccion;
        }else{
            this.btn_seleccion = null;
        }
    }
    private ArrayList<String> setCurrentDayofTheWeek(){
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);
        day = day - 1;

        ArrayList<String> arr_day = new ArrayList<>();
        String[] days = getResources().getStringArray(R.array.array_letra_semana);

        while(arr_day.size() != 7){

            arr_day.add(days[day]);

            if(day == 6){
                day = 0;
            }else{
                day++;
            }
        }
        return arr_day;
    }
    public List<Schedule> getHorarios() {
        return horarios;
    }
    public Button getBtn_seleccion(){
        return this.btn_seleccion;
    }

    public List<Reservation> getReservaciones() {
        return reservaciones;
    }

    public void cancelarReservacion(String reservation){
        ConfirmarCancelarReservacionDialog dialog = ConfirmarCancelarReservacionDialog.newInstance(Integer.parseInt(reservation), dialgoFragmentCancelFromAgendaListener);
        dialog.show(getSupportFragmentManager(),"");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public InerciaApiCancelBookingListenerImpl getInerciaApiCancelBookingListener() {
        return inerciaApiCancelBookingListener;
    }
}
