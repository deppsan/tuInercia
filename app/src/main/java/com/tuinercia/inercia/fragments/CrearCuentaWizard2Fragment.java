package com.tuinercia.inercia.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.dialogs.GeneralDialogFragment;
import com.tuinercia.inercia.implementation.InerciaApiCreateUserListenerImpl;
import com.tuinercia.inercia.implementation.LoadingViewManagerImpl;
import com.tuinercia.inercia.network.InerciaApiClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CrearCuentaWizard2Fragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final String FRAGMENT_TAG = "CrearCuentaWizard2Fragment";
    public static final String EMAIL_FRAGMENT_PARAM  = "email";
    public static final String PASSWORD_FRAGMENT_PARAM  = "password";
    final static String EMPTY_STRING = "";
    final static String URL_TERMINOS_CONDICIONES = "https://inercia-stg.herokuapp.com/terminos_miembros";


    EditText textview_nombre, txt_year_birth;
    Switch swt_terminos_condiciones;
    TextView text_terminos_condiciones;
    Button button_crear;
    RadioGroup rdo_sex;
    View view;

    String password, email;
    boolean terms_validation = false;

    CrearCuentaListener2 listener;
    private InerciaApiCreateUserListenerImpl inerciaApiCreateUser;
    LoadingViewManagerImpl loadingViewManager;

    private static CrearCuentaWizard2Fragment instance;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crear_cuenta_paso_2,container,false);
        calendar = Calendar.getInstance();

        view = v.findViewById(R.id.loading_view);
        textview_nombre = (EditText) v.findViewById(R.id.textview_nombre);
        txt_year_birth  = (EditText) v.findViewById(R.id.txt_year_birth);
        swt_terminos_condiciones = (Switch) v.findViewById(R.id.swt_terminos_condiciones);
        text_terminos_condiciones = (TextView) v.findViewById(R.id.text_terminos_condiciones);
        button_crear = (Button) v.findViewById(R.id.button_crear);
        rdo_sex      = (RadioGroup) v.findViewById(R.id.rdo_sex);

        password = getArguments().getString(PASSWORD_FRAGMENT_PARAM);
        email = getArguments().getString(EMAIL_FRAGMENT_PARAM);
        txt_year_birth.setKeyListener(null);

        inerciaApiCreateUser = new InerciaApiCreateUserListenerImpl(this);
        loadingViewManager = new LoadingViewManagerImpl(view);


        text_terminos_condiciones.setOnClickListener(this);
        button_crear.setOnClickListener(this);
        swt_terminos_condiciones.setOnCheckedChangeListener(this);
        txt_year_birth.setOnClickListener(this);

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                updateLabel();
            }
        };

        return v;
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txt_year_birth.setText(sdf.format(calendar.getTime()));
    }

    public static CrearCuentaWizard2Fragment getInstance(){
        if (instance == null){
            instance = new CrearCuentaWizard2Fragment();
        }
        return instance;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_year_birth:
                new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.text_terminos_condiciones:
                Dialog dialog = new Dialog(getActivity());

                dialog.setContentView(R.layout.custom_dialog_terminos_condicones);
                WebView wb = (WebView) dialog.findViewById(R.id.webview);
                wb.getSettings().setJavaScriptEnabled(true);
                wb.loadUrl(URL_TERMINOS_CONDICIONES);
                wb.setWebViewClient(new WebViewClient());
                wb.setWebChromeClient(new WebChromeClient());
                dialog.setCancelable(true);
                dialog.setTitle("WebView");
                dialog.show();
                break;
            case R.id.button_crear:

                String name, birthday, sex, year;
                int optSex = rdo_sex.getCheckedRadioButtonId();

                name = textview_nombre.getText().toString();
                year = txt_year_birth.getText().toString();

                if (validateForm(name,year)){
                    birthday = txt_year_birth.getText().toString();

                    if (optSex == 1){
                        sex = "F";
                    } else {
                        sex = "M";
                    }

                    InerciaApiClient.getInstance(getContext()).createUser(email, password, name, sex, birthday, terms_validation, inerciaApiCreateUser, loadingViewManager);
                }
                break;
        }
    }

    private boolean validateForm(String name, String year){
        boolean validation = true;

        if ( name.trim().equalsIgnoreCase(EMPTY_STRING)){
            textview_nombre.setError(getActivity().getString(R.string.label_campo_obligatorio));
            validation = false;
        }

        if ( year.trim().equalsIgnoreCase(EMPTY_STRING)){
            validation = false;
            txt_year_birth.setError(getActivity().getString(R.string.label_campo_obligatorio));
        }

        if (!terms_validation){
            validation = false;
            GeneralDialogFragment dialog = GeneralDialogFragment.getInstance(getString(R.string.label_error_terminos_condiciones),getString(R.string.btn_crear),null);
            dialog.show(getFragmentManager(), this.FRAGMENT_TAG);
        }

        return validation;
    }

    public void messageSuccessCreation(final String userD, final String password){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("");
        alertDialog.setMessage(getContext().getString(R.string.dialog_meesage_crear_cuenta));
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onCrearCuenta(FRAGMENT_TAG, userD, password);
            }
        });
        alertDialog.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CrearCuentaListener2) {
            listener = (CrearCuentaListener2) context;
        } else {
            throw new IllegalArgumentException(context.toString() + "debe de implementar en onAttach");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        terms_validation = isChecked;
    }

    public interface CrearCuentaListener2{
        void onCrearCuenta(String thisFragmentTag, String user, String password);
    }
}
