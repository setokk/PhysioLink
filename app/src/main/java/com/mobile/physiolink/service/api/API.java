package com.mobile.physiolink.service.api;

/**
 * @author Kote Kostandin (setokk) <br>
 * <a href="https://www.linkedin.com/in/kostandin-kote-255382223/">LinkedIn</a>
 * <br>
 *
 * This class is used as a simple API endpoints reference.
 * <br> <br>
 * Wherever there is a "/" at the end of an endpoint, it indicates the need
 * for one or more parameters at the end of the request URL.
 * <br> <br>
 * List of REST API methods for each constant:
 * <ul>
 *     <li>{@link API#AUTH_USER}: POST request (username, password)</li>
 *     <li>{@link API#CREATE_DOCTOR}: POST request (username, password, name, surname, email, phoneNumber, afm)</li>
 * </ul>
 */
public final class API
{
    public static final String IP = "http://physiolink.chibo.uk";
    public static final String URL = IP + "/physiolink/api";

    /* User auth related API endpoint */
    public static final String AUTH_USER = API.URL + "/auth/log-in";

    /* Provision related API endpoints */
    public static final String CREATE_SERVICE = API.URL + "/service/create";
    public static final String GET_SERVICE = API.URL + "/service/get/";
    public static final String EDIT_SERVICE = API.URL + "/service/edit";
    public static final String GET_SERVICES = API.URL + "/services/get";
    public static final String GET_SERVICES_OF = API.URL + "/services/of/";

    /* Doctor related API endpoints */
    public static final String CREATE_DOCTOR = API.URL + "/doctor/create";
    public static final String EDIT_DOCTOR = API.URL + "/doctor/edit/";
    public static final String GET_DOCTOR = API.URL + "/doctor/get/";
    public static final String GET_DOCTORS = API.URL + "/doctors/get";


    /* Patient related API endpoints */
    public static final String CREATE_PATIENT = API.URL + "/patient/create";
    public static final String EDIT_PATIENT = API.URL + "/patient/edit/";
    public static final String GET_PATIENT = API.URL + "/patient/get/";
    public static final String GET_PATIENTS = API.URL + "/patients/get";
    public static final String GET_PATIENTS_OF = API.URL + "/patients/of/";

    // For Doctors
    public static final String GET_UNAVAILABLE_HOURS = API.URL + "/unavailable-hours/get";

    public static final String GET_CONFIRMED_APPOINTMENTS = API.URL + "/appointments/confirmed";
    public static final String GET_CONFIRMED_LATEST_APPOINTMENTS = API.URL + "/appointments/confirmed/latest";
    public static final String GET_PENDING_APPOINTMENTS = API.URL + "/appointments/pending/";

    public static final String GET_PATIENT_HISTORY_APPOINTMENTS = API.URL + "/appointments/patient/history/";
    public static final String GET_PATIENT_LATEST_COMPLETED_APPOINTMENT = API.URL + "/appointment/patient/previous?";
    public static final String GET_PATIENT_UPCOMING_APPOINTMENT = API.URL + "/appointment/patient/upcoming?";

    public static final String REQUEST_APPOINTMENT = API.URL + "/appointment/request";
    public static final String ACCEPT_APPOINTMENT = API.URL + "/appointment/accept";
    public static final String ACCEPT_PAYMENT = API.URL + "/appointment/payment/accept";
    public static final String DECLINE_PAYMENT = API.URL + "/appointment/decline";
    public static final String GET_EXCLUDED_DOCTOR_SERVICES = API.URL + "/services/not/of/";
    public static final String DELETE_DOCTOR_SERVICE = API.URL + "/service/delete";
    public static final String LINK_SERVICE_TO_DOCTOR = API.URL + "/service/link";
}