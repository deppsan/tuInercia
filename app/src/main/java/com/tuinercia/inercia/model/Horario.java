package com.tuinercia.inercia.model;

/**
 * Created by ricar on 03/10/2017.
 */

public class Horario {
    String horario_text;
    boolean isReserver;

    public Horario(String horario_text, boolean isReserver) {
        this.horario_text = horario_text;
        this.isReserver = isReserver;
    }

    public String getHorario_text() {
        return horario_text;
    }

    public void setHorario_text(String horario_text) {
        this.horario_text = horario_text;
    }

    public boolean isReserver() {
        return isReserver;
    }

    public void setReserver(boolean reserver) {
        isReserver = reserver;
    }
}
