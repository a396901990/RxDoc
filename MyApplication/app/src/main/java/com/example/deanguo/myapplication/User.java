package com.example.deanguo.myapplication;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class User implements Parcelable {

    public static final String SELECT_TEAM_USER_ID = "1";

    public final static String CHANNEL_INAPP = "app";
    public final static String CHANNEL_EMAIL = "email";
    public final static String CHANNEL_PHONE = "phone";
    public final static String CHANNEL_FACEBOOK = "facebook";
    public final static String CHANNEL_GOOGLE = "google";

    final static public int GENDER_MALE = 0;
    final static public int GENDER_FEMALE = 1;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    public  String id;
    public  String name;
    public  String phoneNumber;
    public  String email;
    public  Gender gender;
    public  String avatar;
    public  long birthday;
    public  long registration;
    public  String facebook;
    public  double latitude;
    public  double longitude;
    public  int invitedTimes;
    public  int invitesSentNum;

    public User(String name, String phoneNumber, String avatar) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
    }

    public User(Parcel in) {
        id = in.readString();
        name = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        gender = Gender.fromString(in.readString());
        avatar = in.readString();
        birthday = in.readLong();
        registration = in.readLong();
        facebook = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        invitedTimes = in.readInt();
        invitesSentNum = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(name);
        out.writeString(phoneNumber);
        out.writeString(email);
        out.writeString(gender.stringValue());
        out.writeString(avatar);
        out.writeLong(birthday);
        out.writeLong(registration);
        out.writeString(facebook);
        out.writeDouble(latitude);
        out.writeDouble(longitude);
        out.writeInt(invitedTimes);
        out.writeInt(invitedTimes);
        out.writeInt(invitesSentNum);
    }


    public boolean isMocha() {
        return this.id.equals(SELECT_TEAM_USER_ID);
    }

    public enum Gender {
        Male("male"),
        Female("female"),
        Unknown("unknown");

        final String value;

        Gender(String value) {
            this.value = value;
        }

        public static Gender fromString(String value) {
            if (value.equals(Male.value)) {
                return Male;
            } else if (value.equals(Female.value)) {
                return Female;
            } else {
                return Unknown;
            }
        }

        public String stringValue() {
            return value;
        }

        @Override
        public String toString() {
            return stringValue();
        }
    }

    public boolean isFBBound() {
        return !TextUtils.isEmpty(facebook);
    }

    public enum AttrKey {
        ATTR_NAME,
        ATTR_GENDER,
        ATTR_BIRTH,
        ATTR_AVATAR,
        ATTR_LON,
        ATTR_LAT,
        ATTR_INVITED_TIMES,
        ATTR_INVITES_SENT_NUM,
    }

}
