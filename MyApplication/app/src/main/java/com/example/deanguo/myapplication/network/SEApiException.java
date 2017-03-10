package com.example.deanguo.myapplication.network;

/**
 * Created by DeanGuo on 3/8/17.
 */

public class SEApiException extends RuntimeException {
    private int errorCode;

    private String errorMessage;

    public SEApiException(int code) {
        this(code, getSysErrorMessage(code));
    }

    public SEApiException(int code, String errorMessage) {
        super(errorMessage);
        this.errorCode = code;
        this.errorMessage = errorMessage;
        handleBaseError(code);
    }

    public void handleBaseError(int code) {

    }

    public int getErrorCode() {
        return errorCode;
    }

    final static public int ERROR_NO_INTERNET = -200;
    final static public int RC_UPLOAD_IMAGE_FAILED = -100;
    final static public int RC_CLIENT_ERROR = -1;

    final static public int RC_INVALID_PARAMETER = 1;
    final static public int RC_INVALID_REQUEST = 2;
    final static public int RC_SERVICE_ERROR = 3;

    // ----- ONLY FOR AUTHORIZE FLOW
    final static public int RC_VERIFY_FAILED = 4;
    final static public int RC_ACCOUNT_EXISTING = 5;
    final static public int RC_ACCOUNT_NOT_EXIST = 6;

    final static public int RC_NOT_EXIST = 7;
    final static public int RC_EXISTING = 10;

    final static public int RC_NO_PERMISSION = 8;

    final static public int RC_UNAUTHORIZED = 9;
    final static public int RC_EXPIRED = 11;
    final static public int RC_UNMATCHED = 12;

    final static public int RC_API_NOT_FOUND = 404;
    final static public int RC_API_INTERNAL_ERROR = 500;
    final static public int RC_API_BAD_GATEWAY = 502;
    final static public int RC_API_UNAVAILABLE = 503;
    final static public int RC_API_TIMEOUT = 504;

    final static public int RC_INVALID_SESSION = 1000;

    final static public int RC_SERVICE_MAINTAINING = 1002;
    final static public int RC_SERVICE_TEMP_ERROR = 1003;
    final static public int RC_UNSUPPORTED_METHOD = 1004;

    final static public int RC_REQUEST_DISABLED = 1006;
    final static public int RC_ACTION_ALREADY_DONE = 1007;

    final static public int RC_UNSUPPORT_CLIENT = 1001;
    final static public int RC_UPGRADE_REQUIRED = 1005;

    final static public int RC_WILL_BE_AVAILABLE_SOON = 1009;

    public String getErrorMessage() {
        return errorMessage;
    }

    public static String getSysErrorMessage(int code) {
        switch (code) {
            case RC_CLIENT_ERROR:
                return "RC_CLIENT_ERROR";
            case RC_INVALID_PARAMETER:
                return "RC_INVALID_PARAMETER";
            case RC_INVALID_REQUEST:
                return "RC_INVALID_REQUEST";
            case RC_VERIFY_FAILED:
                return "RC_VERIFY_FAILED";
            case RC_ACCOUNT_EXISTING:
                return "RC_ACCOUNT_EXISTING";
            case RC_ACCOUNT_NOT_EXIST:
                return "RC_ACCOUNT_NOT_EXIST";
            case RC_NOT_EXIST:
                return "RC_NOT_EXIST";
            case RC_EXISTING:
                return "RC_EXISTING";
            case RC_NO_PERMISSION:
                return "RC_NO_PERMISSION";
            case RC_SERVICE_ERROR:
                return "RC_SERVICE_ERROR";
            case RC_UNAUTHORIZED:
                return "RC_UNAUTHORIZED";
            case RC_API_NOT_FOUND:
                return "RC_API_NOT_FOUND";
            case RC_API_INTERNAL_ERROR:
                return "RC_API_INTERNAL_ERROR";
            case RC_API_BAD_GATEWAY:
                return "RC_API_BAD_GATEWAY";
            case RC_API_UNAVAILABLE:
                return "RC_API_UNAVAILABLE";
            case RC_API_TIMEOUT:
                return "RC_API_TIMEOUT";
            case RC_INVALID_SESSION:
                return "RC_INVALID_SESSION";
            case RC_SERVICE_MAINTAINING:
                return "RC_SERVICE_MAINTAINING";
            case RC_SERVICE_TEMP_ERROR:
                return "RC_SERVICE_TEMP_ERROR";
            case RC_UNSUPPORTED_METHOD:
                return "RC_UNSUPPORTED_METHOD";
            case RC_REQUEST_DISABLED:
                return "RC_REQUEST_DISABLED";
            case RC_ACTION_ALREADY_DONE:
                return "RC_ACTION_ALREADY_DONE";
            case RC_UNSUPPORT_CLIENT:
                return "RC_UNSUPPORT_CLIENT";
            case RC_UPGRADE_REQUIRED:
                return "RC_UPGRADE_REQUIRED";
            case RC_WILL_BE_AVAILABLE_SOON:
                return "RC_WILL_BE_AVAILABLE_SOON";
        }
        return "";
    }
}