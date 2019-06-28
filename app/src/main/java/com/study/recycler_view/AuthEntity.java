package com.study.recycler_view;

import android.os.Parcel;
import android.os.Parcelable;


//TODO : Parcelable 의 정의 및 사용법에 대해 설명하기
public class AuthEntity implements Parcelable {

// 초기화
    public AuthEntity(){
    }

// 변수 명을 지정해준다 그 다음 자동완성
    public String id;
    public String password;
    public String name;
    public String phone;
    public String email;
    public int age;

    protected AuthEntity(Parcel in) {
        id = in.readString();
        password = in.readString();
        name = in.readString();
        phone = in.readString();
        email = in.readString();
        age = in.readInt();
    }

    public static final Creator<AuthEntity> CREATOR = new Creator<AuthEntity>() {
        @Override
        public AuthEntity createFromParcel(Parcel in) {
            return new AuthEntity(in);
        }

        @Override
        public AuthEntity[] newArray(int size) {
            return new AuthEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeInt(age);
    }
}
