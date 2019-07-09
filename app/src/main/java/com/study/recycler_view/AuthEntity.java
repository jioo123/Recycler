package com.study.recycler_view;

import android.os.Parcel;
import android.os.Parcelable;


//TODO : Parcelable 의 정의 및 사용법에 대해 설명하기
/* parcelable은 직렬화를 위한 인터페이스
데이터 객체 전달
데이터 꾸러미가 A Activity에서 B Activity로 한꺼번에 전달되고 받아 볼 수 있도록 해 주는 것
*https://arsviator.blogspot.com/2010/10/parcelable%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%9C-%EC%98%A4%EB%B8%8C%EC%A0%9D%ED%8A%B8-%EC%A0%84%EB%8B%AC-object.html
*
* Parcelable은 직렬화를 위한 또 다른 인터페이스이다. Serializable과는 달리 표준 Java가 아닌 Android SDK의 인터페이스
*  Parcelable은 구현해야 하는 필수 메소드를 포함
*  describeContent()
*  writeToParcel(Parcel out, int flag)


두가지 메소드를 정의해야함과 동시에 CREATOR라는 static field(정적 필드)를 반드시 가지고 있어야 한ek*/
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeInt(age);
    }

    @Override
    public int describeContents() {
        return 0;
    }

//    public static final Creator<AuthEntity> CREATOR = new Creator<AuthEntity>() {
//        @Override
//        public AuthEntity createFromParcel(Parcel in) {
//            return new AuthEntity(in);
//        }
//
//        @Override
//        public AuthEntity[] newArray(int size) {
//            return new AuthEntity[size];
//        }
//    };
}
