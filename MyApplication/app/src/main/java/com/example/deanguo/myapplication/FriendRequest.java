package com.example.deanguo.myapplication;

/**
 */
public class FriendRequest {

    final static public String CHANNEL_PHONE = "phone";
    final static public String CHANNEL_INAPP = "inapp";
    final static public String CHANNEL_FACEBOOK = "facebook";

    final static public int TYPE_INAPP = 0; // friend request in app
    final static public int TYPE_PHONE = 1; // NOT USED, legacy support
    final static public int TYPE_SNS = 2; // NOT USED YET
    final static public int TYPE_SMS_INVITATION = 3; // invitation before user sign up
    final static public int TYPE_CONTENT_INVITATION = 4; // invitation before user sign up

    final static public int STATE_NORMAL = 0; // when user was recommended and no action has been performed
    final static public int STATE_REQUEST_SENT = 1;
    final static public int STATE_REQUEST_RECEIVED = 2;
    final static public int STATE_ACCEPTED = 3;
    final static public int STATE_REJECTED = 4;
    final static public int STATE_IGNORED = 5;

    boolean isAdd = false;
    public User target;

    public FriendRequest(User target) {
        this.target = target;
    }

}
