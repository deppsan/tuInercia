package com.tuinercia.inercia.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.tuinercia.inercia.DTO.Reservation;
import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.dialogs.ConfirmarCancelarReservacionDialog;
import com.tuinercia.inercia.implementation.DialgoFragmentCancelFromAgendaPasadaListenerImpl;
import com.tuinercia.inercia.implementation.InerciaApiCancelBookingListenerAgendaImpl;
import com.tuinercia.inercia.implementation.InerciaApiCheckInBookingListenerAgendaImpl;
import com.tuinercia.inercia.implementation.InerciaApiGetBookingHistoryListenerImpl;
import com.tuinercia.inercia.implementation.InerciaApiPendingBookingListenerImpl;
import com.tuinercia.inercia.implementation.LoadingViewManagerImpl;
import com.tuinercia.inercia.interfaces.InerciaApiGetBookingHistoryListener;
import com.tuinercia.inercia.interfaces.LoadingViewManager;
import com.tuinercia.inercia.network.InerciaApiClient;
import com.tuinercia.inercia.utils.UtilsSharedPreference;

import java.util.HashMap;

/**
 * Created by ricar on 17/10/2017.
 */

public class AgendaActualFragment extends Fragment {

    ListView list_agenda;
    Button button_selected;
    View view;

    User user;
    Context mContext;

    InerciaApiPendingBookingListenerImpl inerciaApiPendingBookingListener;
    InerciaApiCancelBookingListenerAgendaImpl inerciaApiCancelBookingListenerAgenda;
    DialgoFragmentCancelFromAgendaPasadaListenerImpl dialgoFragmentCancelFromAgendaPasadaListener;
    InerciaApiCheckInBookingListenerAgendaImpl inerciaApiCheckInBookingListenerAgenda;
    LoadingViewManagerImpl loadingViewManager;

    private static AgendaActualFragment instance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mis_clases_agenda, container, false);
        mContext = getActivity();

        list_agenda = (ListView) v.findViewById(R.id.list_general);
        view = v.findViewById(R.id.loading_view);

        user = UtilsSharedPreference.getInstance(getActivity()).getUser();

        inerciaApiPendingBookingListener = new InerciaApiPendingBookingListenerImpl(this);
        inerciaApiCancelBookingListenerAgenda = new InerciaApiCancelBookingListenerAgendaImpl(this);
        dialgoFragmentCancelFromAgendaPasadaListener = new DialgoFragmentCancelFromAgendaPasadaListenerImpl(this);
        inerciaApiCheckInBookingListenerAgenda = new InerciaApiCheckInBookingListenerAgendaImpl(this);
        loadingViewManager = new LoadingViewManagerImpl(view);

        InerciaApiClient.getInstance(getActivity()).pendingBookin(Integer.toString(user.getId()), inerciaApiPendingBookingListener, loadingViewManager);

        return v;
    }

    public AgendaActualFragment(){}

    public static AgendaActualFragment getInstance(){
        if (instance == null){
            instance = new AgendaActualFragment();
        }
        return instance;
    }

    public ListView getList_agenda() {
        return list_agenda;
    }

    public User getUser() {
        return user;
    }

    public InerciaApiPendingBookingListenerImpl getInerciaApiPendingBookingListener() {
        return inerciaApiPendingBookingListener;
    }

    public Context getmContext() {
        return mContext;
    }

    public Button getButton_selected() {
        return button_selected;
    }

    public void setButton_selected(Button button_selected) {
        this.button_selected = button_selected;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setInerciaApiPendingBookingListener(InerciaApiPendingBookingListenerImpl inerciaApiPendingBookingListener) {
        this.inerciaApiPendingBookingListener = inerciaApiPendingBookingListener;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setList_agenda(ListView list_agenda) {
        this.list_agenda = list_agenda;
    }

    public void cancelarReservacion(String idReservacion) {
        ConfirmarCancelarReservacionDialog dialog = ConfirmarCancelarReservacionDialog.newInstance(Integer.parseInt(idReservacion), dialgoFragmentCancelFromAgendaPasadaListener);
        dialog.show(getFragmentManager(), "");
    }

    public void CheckInReservacion(String idReservacion) {
        InerciaApiClient.getInstance(mContext).checkInBooking(Integer.toString(user.getId()),idReservacion, inerciaApiCheckInBookingListenerAgenda, loadingViewManager);
    }


    public InerciaApiCancelBookingListenerAgendaImpl getInerciaApiCancelBookingListenerAgenda() {
        return inerciaApiCancelBookingListenerAgenda;
    }

    public LoadingViewManager getLoadingViewManager(){
        return loadingViewManager;
    }
}
