package com.mobile.physiolink.model.api;

/**
 * @author Kote Kostandin (setokk) <br>
 * <a href="https://www.linkedin.com/in/kostandin-kote-255382223/">LinkedIn</a>
 * <br>
 *
 * This class is used as a simple API endpoints holder.
 * <br> <br>
 * Wherever there is a "/" at the end of an endpoint, it indicates the need
 * for one or more parameters at the end of the request URL.
 */
public final class API
{
    public static final String URL = "http://192.168.1.8/physiolink/api/";

    /* Provision related API endpoints */
    public static final String CREATE_PROVISION = API.URL + "/provision/create";
    public static final String EDIT_PROVISION = API.URL + "/provision/edit/";
    public static final String SHOW_PROVISIONS = API.URL + "/provisions/show";

    /* Doctor related API endpoints */
    public static final String CREATE_DOCTOR = API.URL + "/doctor/create";
    public static final String EDIT_DOCTOR = API.URL + "/doctor/edit/";
    public static final String SHOW_DOCTOR = API.URL + "/doctor/get/";
    public static final String SHOW_DOCTORS = API.URL + "/doctors/get";

    /* Patient related API endpoints */
    public static final String CREATE_PATIENT = API.URL + "/patient/create";
    public static final String EDIT_PATIENT = API.URL + "/patient/edit/";
    public static final String SHOW_PATIENT = API.URL + "/patient/get/";
    public static final String SHOW_PATIENTS = API.URL + "/patients/show";
}
