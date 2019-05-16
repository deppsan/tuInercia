package com.tuinercia.inercia.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tuinercia.inercia.DTO.Disciplines;
import com.tuinercia.inercia.DTO.History;
import com.tuinercia.inercia.DTO.Membership;
import com.tuinercia.inercia.DTO.MembershipProgress;
import com.tuinercia.inercia.DTO.Parlor;
import com.tuinercia.inercia.DTO.Reservation;
import com.tuinercia.inercia.DTO.ReservationTrue;
import com.tuinercia.inercia.DTO.Schedule;
import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.DTO.Zone;
import com.tuinercia.inercia.interfaces.InerciaApiCancelBookingListener;
import com.tuinercia.inercia.interfaces.InerciaApiCheckInBookingListener;
import com.tuinercia.inercia.interfaces.InerciaApiCreateBookingListener;
import com.tuinercia.inercia.interfaces.InerciaApiCreatePaymentListener;
import com.tuinercia.inercia.interfaces.InerciaApiCreateUserListener;
import com.tuinercia.inercia.interfaces.InerciaApiGetBookingHistoryListener;
import com.tuinercia.inercia.interfaces.InerciaApiGetCurrentMembershipListener;
import com.tuinercia.inercia.interfaces.InerciaApiGetDiciplinasListener;
import com.tuinercia.inercia.interfaces.InerciaApiGetParlorsListener;
import com.tuinercia.inercia.interfaces.InerciaApiGetPlanesInerciaListener;
import com.tuinercia.inercia.interfaces.InerciaApiGetScheduleByParlorListener;
import com.tuinercia.inercia.interfaces.InerciaApiGetZonesListener;
import com.tuinercia.inercia.interfaces.InerciaApiPendingBookingListener;
import com.tuinercia.inercia.interfaces.InerciaApiRegistroTokenFirebaseListener;
import com.tuinercia.inercia.interfaces.InerciaApiValidarUsuario;
import com.tuinercia.inercia.interfaces.LoadingViewManager;
import com.tuinercia.inercia.network.conection.conexionHTTP;
import com.tuinercia.inercia.utils.UtilsSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by ricar on 24/10/2017.
 */

public class InerciaApiClient {

    private static InerciaApiClient instance;
    private Context mContext;

    public static synchronized InerciaApiClient getInstance(Context context){
        if (instance == null){
            instance = new InerciaApiClient(context);
        }
        return instance;
    }

    private InerciaApiClient(Context mContext){
        this.mContext = mContext;
    }
    
    public void getCurrentMemberShip(String email, String password, final InerciaApiGetCurrentMembershipListener listener, LoadingViewManager loadingViewManager){
        HashMap<String,String> params = new HashMap<>();
        params.put(PASSWORD_HEAD_PARAM, password);
        params.put(EMAIL_HEAD_PARAM, email);
    
        new conexionHTTP().getInstance().postJsonResponse(mContext, BASE_URL + CURRENT_MEMBERSHIP_URL, params,
                new conexionHTTP.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try{
                            if (response.getBoolean(VALIDATION_RESULT_NAME)){
                                MembershipProgress membershipProgress = null;
                                boolean show_progress = false;
                                Membership membership = gson.fromJson(response.getString(PLAN_RESULT_NAME),Membership.class);
                                if(response.has(PROGRESS_RESULT_NAME)){
                                    membershipProgress = gson.fromJson(response.getString(PROGRESS_RESULT_NAME), MembershipProgress.class);
                                    show_progress = response.getBoolean(SHOW_PROGRESS_RESULT_NAME);
                                }
                                listener.onGetCurrentMembershipSuccess(membership, membershipProgress, show_progress);

                            }else{
                                listener.onGetCurrentMembershipError(response.getString(ERROR_MESSAGE_NAME));
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            listener.onGetCurrentMembershipError(e.getMessage());
                        }
                    }
                    @Override
                    public void onError(int statusCode) {
                        listener.onErrorServer(statusCode);
                    }
                },loadingViewManager);
    }
    
    public void checkInBooking(String userID, String reservationID, final InerciaApiCheckInBookingListener listener, LoadingViewManager loadingViewManager){
        HashMap<String,String> params = new HashMap<>();

        params.put(USER_ID_HEAD_PARAM, userID);
        params.put(RESERVATION_ID_HEAD_PARAM, reservationID);

        

        new conexionHTTP().getInstance().postJsonResponse(mContext, BASE_URL + CHECKIN_BOOKING_URL, params,
                new conexionHTTP.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try{
                            if (response.getBoolean(VALIDATION_RESULT_NAME)){
                                listener.onCheckInBookingSuccess(response.getString(RESPONSE_RESULT_NAME), response.getString(CONFIRMATION_CODE_RESULT_NAME));
                            }else{
                                listener.onCheckInBookingFail(response.getString(ERROR_MESSAGE_NAME));
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            listener.onCheckInBookingFail(e.getMessage());
                        }
                    }
                    @Override
                    public void onError(int statusCode) {
                        listener.onErrorServer(statusCode);
                    }
                }
                ,loadingViewManager);
    }
    
    public void cancelBooking(String userID, String reservationID, final InerciaApiCancelBookingListener listener, LoadingViewManager loadingViewManager){
        HashMap<String,String> params = new HashMap<>();

        params.put(USER_ID_HEAD_PARAM, userID);
        params.put(RESERVATION_ID_HEAD_PARAM, reservationID);
        loadingViewManager.hideLoadingView();
    
        new conexionHTTP().getInstance().postJsonResponse(mContext, BASE_URL + CANCEL_BOOKING_URL, params,
            new conexionHTTP.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try{
                        if (response.getBoolean(VALIDATION_RESULT_NAME)){
                            listener.onCancelBookingSuccess(response.getString(RESPONSE_RESULT_NAME));
                        }else{
                            listener.onCancelBookingFail(response.getString(ERROR_MESSAGE_NAME));
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        listener.onCancelBookingFail(e.getMessage());
                    }
                }
                @Override
                public void onError(int statusCode) {
                    listener.onErrorServer(statusCode);
                }
            }
        ,loadingViewManager);
    }

    public void getScheduleByParlor(int discipline, int parlor, int userID, final InerciaApiGetScheduleByParlorListener listener, LoadingViewManager loadingViewManager){
        HashMap<String,String> params = new HashMap<>();
        params.put(DISCIPLINE_HEAD_PARAM, Integer.toString(discipline));
        params.put(PARLOR_ID_HEAD_PARAM, Integer.toString(parlor));
        params.put(USER_ID_HEAD_PARAM, Integer.toString(userID));
        
        new conexionHTTP().getInstance().postJsonResponse(mContext, BASE_URL + SCHEDULE_BY_PARLOR_URL, params,
                new conexionHTTP.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try{
                            if (response.getBoolean(VALIDATION_RESULT_NAME)){
                                JSONObject hash_schedules = new JSONObject(response.getString(RESPONSE_SCHEDULES_RESULT_NAME));
                                JSONObject hash_reservations = new JSONObject(response.getString(RESPONSE_RESERVATIONS_RESULT_NAME));

                                Schedule[] day_today = gson.fromJson(hash_schedules.getString("day_today"),Schedule[].class);
                                Schedule[] day_two = gson.fromJson(hash_schedules.getString("day_two"),Schedule[].class);
                                Schedule[] day_three = gson.fromJson(hash_schedules.getString("day_three"),Schedule[].class);
                                Schedule[] day_four = gson.fromJson(hash_schedules.getString("day_four"),Schedule[].class);
                                Schedule[] day_five = gson.fromJson(hash_schedules.getString("day_five"),Schedule[].class);
                                Schedule[] day_six = gson.fromJson(hash_schedules.getString("day_six"),Schedule[].class);
                                Schedule[] day_seven = gson.fromJson(hash_schedules.getString("day_seven"),Schedule[].class);

                                Reservation[] res_day_today = gson.fromJson(hash_reservations.getString("res_day_today"),Reservation[].class);
                                Reservation[] res_day_two = gson.fromJson(hash_reservations.getString("res_day_two"),Reservation[].class);
                                Reservation[] res_day_three = gson.fromJson(hash_reservations.getString("res_day_three"),Reservation[].class);
                                Reservation[] res_day_four = gson.fromJson(hash_reservations.getString("res_day_four"),Reservation[].class);
                                Reservation[] res_day_five = gson.fromJson(hash_reservations.getString("res_day_five"),Reservation[].class);
                                Reservation[] res_day_six = gson.fromJson(hash_reservations.getString("res_day_six"),Reservation[].class);
                                Reservation[] res_day_seven = gson.fromJson(hash_reservations.getString("res_day_seven"),Reservation[].class);

                                ArrayList<Schedule[]> schedules = new ArrayList<>();
                                schedules.add(day_today);
                                schedules.add(day_two);
                                schedules.add(day_three);
                                schedules.add(day_four);
                                schedules.add(day_five);
                                schedules.add(day_six);
                                schedules.add(day_seven);

                                ArrayList<Reservation[]> reservations = new ArrayList<>();
                                reservations.add(res_day_today);
                                reservations.add(res_day_two);
                                reservations.add(res_day_three);
                                reservations.add(res_day_four);
                                reservations.add(res_day_five);
                                reservations.add(res_day_six);
                                reservations.add(res_day_seven);

                                for (Schedule[] s : schedules){
                                    for(Schedule ss : s){
                                        ss.setSelected(false);
                                    }
                                }

                                listener.onGetScheduleByParlor(schedules,reservations);

                            }else{
                                listener.onFailGetScheduleByParlor(response.getString("error_message"));
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            listener.onFailGetScheduleByParlor(e.getMessage());
                        }
                    }
                    @Override
                    public void onError(int statusCode) {
                        listener.onErrorServer(statusCode);
                    }
                },loadingViewManager);
    }

    public void createUser(final String email, final String password, String name, String sex, String birthday, boolean terms, final InerciaApiCreateUserListener listener, LoadingViewManager loadingViewManager){
        HashMap<String,String> params = new HashMap<>();
        params.put(NAME_HEAD_PARAM, name);
        params.put(EMAIL_HEAD_PARAM, email);
        params.put(PASSWORD_HEAD_PARAM, password);
        params.put(SEX_HEAD_PARAM, sex);
        params.put(BIRTHDAY_HEAD_PARAM, birthday);
        params.put(TERMINOS_DE_SERVICIO, Boolean.toString(terms));
        params.put(CREATED_FROM_PARAM,"AND");
        
        new conexionHTTP().getInstance().postJsonResponse(mContext, BASE_URL + CREATE_USER_URL, params,
            new conexionHTTP.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try{
                        if (response.getBoolean(VALIDATION_RESULT_NAME)){
                            User user = gson.fromJson(response.getString(USER_RESULT_NAME),User.class);
                            listener.onCreateUserSuccefull( user , email, password );
                        }else{
                            listener.onCreateUserError(response.getString(ERROR_MESSAGE_NAME));
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        listener.onCreateUserError("");
                    }
                }
                @Override
                public void onError(int statusCode) {
                    listener.onErrorServer(statusCode);
                }
            },loadingViewManager);

    }

    public void createBooking(String userID, String scheduleID, final InerciaApiCreateBookingListener listener, LoadingViewManager loadingViewManager){
        HashMap<String,String> params = new HashMap<>();
        params.put(USER_ID_HEAD_PARAM, userID);
        params.put(SCHEDULE_ID_HEAD_PARAM, scheduleID);
        

        new conexionHTTP().getInstance().postJsonResponse(mContext, BASE_URL + CREATE_BOOKING_URL, params,
                new conexionHTTP.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try{
                            if (response.getBoolean(VALIDATION_RESULT_NAME)){
                                listener.onCreateBookingSuccess(response.getString(RESPONSE_RESULT_NAME),response.getInt(RESERVATION_ID_RESULT_NAME));
                            }else{
                                listener.onCreateBookingError(response.getString(ERROR_MESSAGE_NAME), response.getString(ERROR_TYPE));
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            listener.onCreateBookingError(e.getMessage(),"0");
                        }
                    }
                    @Override
                    public void onError(int statusCode) {
                        listener.onErrorServer(statusCode);
                    }
                },loadingViewManager);
    }

    public void getBookingHistory(String userID, final InerciaApiGetBookingHistoryListener listener){
        HashMap<String,String> params = new HashMap<>();
        params.put(USER_ID_HEAD_PARAM, userID);

        new conexionHTTP().getInstance().postJsonResponse(mContext, BASE_URL + HISTORIY_BOOKING_URL_DOS, params,
            new conexionHTTP.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {

                    try{
                        if (response.getBoolean(VALIDATION_RESULT_NAME)){

                            History[] actual_history = gson.fromJson(response.getString(RESPONSE_CURRENT_RESULT_NAME), History[].class);
                            History[] prev_history = gson.fromJson(response.getString(RESPONSE_PREVIOUS_RESULT_NAME), History[].class);
                            History[] ant_prev_history = gson.fromJson(response.getString(RESPONSE_PREVIOUS_RESULT_2_NAME), History[].class);

                            listener.onGetBookingHistorySuccess(actual_history, prev_history, ant_prev_history);

                        }else{
                            listener.onGetBookingHistoryError(response.getString(ERROR_MESSAGE_NAME));
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        listener.onGetBookingHistoryError(e.getMessage());
                    }
                }
                @Override
                public void onError(int statusCode) {
                    listener.onErrorServer(statusCode);
                }
            }
        );
    }

    public void validarUsuario(String correo, String contraseña, final InerciaApiValidarUsuario listener){
        HashMap<String,String> params = new HashMap<>();

        params.put(EMAIL_HEAD_PARAM,correo);
        params.put(PASSWORD_HEAD_PARAM,contraseña);

        new conexionHTTP().getInstance().postJsonResponse(mContext,BASE_URL + VALIDATE_USER_URL, params,
            new conexionHTTP.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        if (response.getBoolean(VALIDATION_RESULT_NAME) == true){
                            User us = gson.fromJson(response.getString(USER_RESULT_NAME),User.class);
                            boolean payment = response.getBoolean(PAYED_PLAN_RESULT_NAME);

                            UtilsSharedPreference.getInstance(mContext).set_type_account(payment);

                            listener.onUsuarioCorrecto(us);

                        }else{
                            listener.onUsuarioIncorrecto(response.getString(ERROR_MESSAGE_NAME));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onErrorServer();
                    }
                }
                @Override
                public void onError(int statusCode) {
                    listener.onErrorServer();
                }
            });
    }

    public void getDiciplinas(final InerciaApiGetDiciplinasListener listener, LoadingViewManager loadingViewManager){
        HashMap<String,String> params = new HashMap<>();

        

        new conexionHTTP().getInstance().postJsonResponse(mContext,BASE_URL + DICIPLINES_URL, params,
                new conexionHTTP.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            if (response.getString(DISCIPLINES_RESULT_NAME) != null){
                                Disciplines[] disciplines = gson.fromJson(response.getString(DISCIPLINES_RESULT_NAME),Disciplines[].class);
                                listener.chargeDiciplinas(disciplines);
                            }else{
                                listener.failChargeDiciplines(response.getString(ERROR_MESSAGE_NAME));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.failChargeDiciplines(e.getMessage());
                        }
                    }
                    @Override
                    public void onError(int statusCode) {
                        listener.onErrorServer(statusCode);
                    }
                }
        ,loadingViewManager);
    }

    public void getParlorsByDicipline(String discipline, final InerciaApiGetParlorsListener listener){
        HashMap<String,String> params = new HashMap<>();
        params.put(DISCIPLINE_HEAD_PARAM,discipline);
        new conexionHTTP().getInstance().postJsonResponse(mContext,BASE_URL + PARLORS_URL ,params, new conexionHTTP.VolleyCallback(){
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if(response.getString(PARLOR_RESULT_NAME) != null){
                        ArrayList<Parlor> arrParlors = new ArrayList<>();
                        Parlor[] parlors = gson.fromJson(response.getString(PARLOR_RESULT_NAME),Parlor[].class);
                        for (Parlor p : parlors){
                                arrParlors.add(p);
                        }
                        listener.onGetParlorsSuccess(arrParlors);
                    }else{
                        listener.onFailChargeParlors(response.getString(ERROR_MESSAGE_NAME));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onFailChargeParlors(e.getMessage());
                }
            }

            @Override
            public void onError(int statusCode) {
                listener.onErrorServer(statusCode);
            }
        });
    }

    public void getAllZones(final InerciaApiGetZonesListener listener){
        HashMap<String,String> params = new HashMap<>();
        new conexionHTTP().getInstance().postJsonResponse(mContext, BASE_URL + ZONES_URL, params, new conexionHTTP.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getString(ZONE_RESULT_NAME)!= null){
                        Zone[] zones = gson.fromJson(response.getString(ZONE_RESULT_NAME),Zone[].class);
                        listener.onZonesReceived(zones);
                    }else{
                        listener.onZonesNoReceived(response.getString(ERROR_MESSAGE_NAME));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    listener.onZonesNoReceived(e.getMessage());
                }
            }

            @Override
            public void onError(int statusCode) {
                listener.onErrorServer(statusCode);
            }
        });
    }

    public void pendingBookin(String user_id, final InerciaApiPendingBookingListener listener, LoadingViewManager loadingViewManager){
        HashMap<String,String> params = new HashMap<>();
        params.put(USER_ID_HEAD_PARAM,user_id);

        

        new conexionHTTP().getInstance().postJsonResponse(mContext, BASE_URL + PENDING_BOOKING_URL, params,
                new conexionHTTP.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try{
                            if (response.getBoolean(VALIDATION_RESULT_NAME)){
                                ReservationTrue[] reservations = gson.fromJson(response.getString(PENDING_RESULT_NAME), ReservationTrue[].class);
                                listener.onGetPenndingSuccess(reservations);
                            }else{
                                listener.onGetPendingError(response.getString(ERROR_MESSAGE_NAME));
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            listener.onGetPendingError(e.getMessage());
                        }
                    }
                    @Override
                    public void onError(int statusCode) {
                        listener.onErrorServer(statusCode);
                    }
                },loadingViewManager);
    }

    public void getPlanesInercia(String email, String password, final InerciaApiGetPlanesInerciaListener listener, LoadingViewManager loadingViewManager){
        HashMap<String,String> params = new HashMap<>();
        params.put(EMAIL_HEAD_PARAM, email);
        params.put(PASSWORD_HEAD_PARAM, password);
        
        new conexionHTTP().getInstance().postJsonResponse(mContext, BASE_URL + MEMBERSHIP_LIST_URL, params,
            new conexionHTTP.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try{
                        if (response.getBoolean(VALIDATION_RESULT_NAME)){
                            Membership[] membership = gson.fromJson(response.getString(MEMBERSHIP_RESULT_NAME),Membership[].class);
                            listener.onGetPlanesListSuccess(membership);
                        }else{
                            listener.onGetPlanesListError(response.getString(ERROR_MESSAGE_NAME));
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        listener.onGetPlanesListError(e.getMessage());
                    }
                }
                @Override
                public void onError(int statusCode) {
                    listener.onErrorServer(statusCode);
                }
            },loadingViewManager);
    }

    public void createPayment(String card_tkn, String user_id, String plan_id, final InerciaApiCreatePaymentListener listener, LoadingViewManager loadingViewManager){
        HashMap<String,String> params = new HashMap<>();
        params.put(TOKEN_CARD_HEAD_PARAM, card_tkn);
        params.put(USER_ID_HEAD_PARAM, user_id);
        params.put(PLAN_ID_HEAD_PARAM, plan_id);
        
        new conexionHTTP().getInstance().postJsonResponse(mContext, BASE_URL + CREATE_PAYMENT_URL, params,
                new conexionHTTP.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try{
                            if (response.getBoolean(VALIDATION_RESULT_NAME)){
                                UtilsSharedPreference.getInstance(mContext).set_type_account(response.getBoolean(VALIDATION_RESULT_NAME));
                                listener.onCreatePaymentSuccess(response.getString(RESPONSE_RESULT_NAME));

                            }else{
                                listener.onCreatePaymentError(response.getString(ERROR_MESSAGE_NAME));
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            listener.onCreatePaymentError(e.getMessage());
                        }
                    }
                    @Override
                    public void onError(int statusCode) {
                        listener.onErrorServer(statusCode);
                    }
                },loadingViewManager);
    }

    public void PruebaTSYS(){
        HashMap<String,String> params = new HashMap<>();

        new conexionHTTP().getInstance().postJsonResponse(mContext, "https://www.tsm-validacion.com.mx/WSIAM/restful/security/tokenRequest" , params,
                new conexionHTTP.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try{
                            if (response.getBoolean(VALIDATION_RESULT_NAME)){
                                Log.d("RESPOSNE",response.toString());
                            }else{
                                Log.d("RESPOSNE",response.toString());
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Log.d("RESPOSNE",response.toString());
                        }
                    }
                    @Override
                    public void onError(int statusCode) {

                        Log.d("RESPOSNE",Integer.toString(statusCode));
                    }
                });
    }

    public void registroTokenFirebase(int userID, String token, final InerciaApiRegistroTokenFirebaseListener listener){
        HashMap<String,String> params = new HashMap<>();
        params.put(TOKEN_FIREBASE_PARAM,token);
        params.put(USER_ID_HEAD_PARAM, Integer.toString(userID));

        new conexionHTTP().getInstance().postJsonResponse(mContext, BASE_URL + SAVE_USER_AND_FIREBASE_TOKEN, params,
                new conexionHTTP.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try{
                            if (response.getBoolean(VALIDATION_RESULT_NAME)){

                            }else{
                                listener.onRegistroError(response.getString(ERROR_MESSAGE_NAME));
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            listener.onRegistroError(e.getMessage());
                        }
                    }
                    @Override
                    public void onError(int statusCode) {
                        listener.onErrorServer(statusCode);
                    }
                });
    }

//    private static final String BASE_URL = "https://inercia-stg.herokuapp.com/inercia_apis/";   //STG
    private static final String BASE_URL = "https://www.tuinercia.com/inercia_apis/";   //PROD
    private static final GsonBuilder builder = new GsonBuilder();
    public static final Gson gson = builder.create();

    private static final String EMAIL_HEAD_PARAM = "email";
    private static final String PASSWORD_HEAD_PARAM = "password";
    private static final String DISCIPLINE_HEAD_PARAM = "discipline";
    private static final String NAME_HEAD_PARAM = "name";
    private static final String SEX_HEAD_PARAM = "sex";
    private static final String BIRTHDAY_HEAD_PARAM = "birthdate";
    private static final String ID_HEAD_PARAM = "id";
    private static final String USER_ID_HEAD_PARAM = "user_id";
    private static final String TOKEN_FIREBASE_PARAM = "token";
    private static final String SCHEDULE_ID_HEAD_PARAM = "schedule_id";
    private static final String RESERVATION_ID_HEAD_PARAM = "reservation_id";
    private static final String PARLOR_ID_HEAD_PARAM = "parlor";
    private static final String TERMINOS_DE_SERVICIO = "terms_of_service";
    private static final String TOKEN_CARD_HEAD_PARAM = "card_tkn";
    private static final String PLAN_ID_HEAD_PARAM = "plan_id";
    private static final String CREATED_FROM_PARAM = "created_from";

    private static final String DICIPLINES_URL = "req_disciplines/";
    private static final String VALIDATE_USER_URL = "validate_user/";
    private static final String PARLORS_URL = "req_parlors_by_discipline/";
    private static final String ZONES_URL = "req_zones/";
    private static final String CREATE_USER_URL = "create_user";
    private static final String SCHEDULE_BY_PARLOR_URL = "req_schedules_by_parlor";
    private static final String CREATE_BOOKING_URL = "create_booking";
    private static final String HISTORIY_BOOKING_URL = "history_booking";
    private static final String HISTORIY_BOOKING_URL_DOS = "history_booking_2";
    private static final String CANCEL_BOOKING_URL = "cancel_booking";
    private static final String CHECKIN_BOOKING_URL = "checkin_booking";
    private static final String PENDING_BOOKING_URL = "pending_booking";
    private static final String CURRENT_MEMBERSHIP_URL = "req_current_membership";
    private static final String MEMBERSHIP_LIST_URL = "req_memberships_list";
    private static final String CREATE_PAYMENT_URL = "create_payment";
    private static final String SAVE_USER_AND_FIREBASE_TOKEN = "registro_token_firebase";



    private static final String DISCIPLINES_RESULT_NAME = "disciplines" ;
    private static final String RESERVATION_ID_RESULT_NAME = "reservation_id";
    private static final String PARLOR_RESULT_NAME = "parlors" ;
    private static final String RESPONSE_RESULT_NAME = "response" ;
    private static final String VALIDATION_RESULT_NAME  = "validation";
    private static final String USER_RESULT_NAME = "user" ;
    private static final String ZONE_RESULT_NAME = "zones" ;
    private static final String RESPONSE_CURRENT_RESULT_NAME = "res_curr";
    private static final String RESPONSE_PREVIOUS_RESULT_NAME = "res_prev";
    private static final String RESPONSE_PREVIOUS_RESULT_2_NAME = "res_prev_2";
    private static final String RESPONSE_SCHEDULES_RESULT_NAME = "schedules";
    private static final String RESPONSE_RESERVATIONS_RESULT_NAME = "reservations";
    private static final String ERROR_MESSAGE_NAME = "error_message";
    private static final String PENDING_RESULT_NAME = "res_pend";
    private static final String CONFIRMATION_CODE_RESULT_NAME ="confirmation_code";
    private static final String PAYED_PLAN_RESULT_NAME = "payed_plan";
    private static final String PLAN_RESULT_NAME = "plan";
    private static final String PROGRESS_RESULT_NAME = "progress";
    private static final String MEMBERSHIP_RESULT_NAME = "memberships";
    private static final String SHOW_PROGRESS_RESULT_NAME = "show_progress";
    private static final String ERROR_TYPE = "error_type";

}


