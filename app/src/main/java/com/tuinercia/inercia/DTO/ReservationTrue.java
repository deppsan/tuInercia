package com.tuinercia.inercia.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ricar on 06/03/2018.
 */

public class ReservationTrue {

    @SerializedName("id")
    int id;
    @SerializedName("clase")
    String clase;
    @SerializedName("sucursal")
    String sucursal;
    @SerializedName("fecha")
    String fecha;
    @SerializedName("hora")
    String hora;
    @SerializedName("confirmacion")
    String confirmacion;
    @SerializedName("instructor")
    String instructor;
    @SerializedName("no_cancel")
    Boolean no_cancel;
    @SerializedName("checking")
    Boolean checking;

    public ReservationTrue(int id, String clase, String sucursal, String fecha, String hora, String confirmacion, String instructor, Boolean no_cancel, Boolean checking) {
        this.id = id;
        this.clase = clase;
        this.sucursal = sucursal;
        this.fecha = fecha;
        this.hora = hora;
        this.confirmacion = confirmacion;
        this.instructor = instructor;
        this.no_cancel = no_cancel;
        this.checking = checking;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Boolean getNo_cancel() {
        return no_cancel;
    }

    public void setNo_cancel(Boolean no_cancel) {
        this.no_cancel = no_cancel;
    }

    public Boolean getChecking() {
        return checking;
    }

    public void setChecking(Boolean checking) {
        this.checking = checking;
    }
}
