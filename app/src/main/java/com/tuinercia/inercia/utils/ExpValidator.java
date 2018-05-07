package com.tuinercia.inercia.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ricar on 03/01/2017.
 */

public class ExpValidator {

    private Pattern pattern;
    private Matcher matcher;
    private static ExpValidator instance;

    private static final String EMAIL_PATTERN =  "(?!.*\\.\\.)(\"[!-~ ]+\"|[0-9A-Z!#-'*-\\/=?^-~]+)@((?![-])[A-Za-z0-9-]*[A-Za-z-]+[A-Za-z0-9-]*(?![-])\\.*)+\\.[a-z]+";
    private static final String CELL_PHONE_MEX = "[0-9]{10}";

    private ExpValidator() {

    }
    public static synchronized ExpValidator getInstance(){
        if(instance == null){
            instance = new ExpValidator();
        }
        return instance;
    }


    /**
     * Validate hex with regular expression
     *
     * @param hex
     *            hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validateEmail(final String hex) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }

    public boolean validateCellphone(final String hex){
        pattern = Pattern.compile(CELL_PHONE_MEX);
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }
}
