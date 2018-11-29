package ru.qagods.myfirstapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @Override
    public String toString() {
        return "User{" +
                "mData=" + mData +
                '}';
    }

    /**
     * data : {"id":0,"name":"string","email":"string"}
     */

    public User(String login,String name, String password){
        mData=new DataBean();
        mData.setEmail(login);
        mData.setName(name);
        mData.setmPassword(password);
    }

    @SerializedName("data")
    private DataBean mData;

    public DataBean getData() {
        return mData;
    }

    public void setData(DataBean data) {
        mData = data;
    }

    public static class DataBean implements Serializable {
        @Override
        public String toString() {
            return "DataBean{" +
                    "mId=" + mId +
                    ", mName='" + mName + '\'' +
                    ", mEmail='" + mEmail + '\'' +
                    ", mPassword='" + mPassword + '\'' +
                    '}';
        }

        /**
         * id : 0
         * name : string
         * email : string
         */

        @SerializedName("id")
        private int mId;
        @SerializedName("name")
        private String mName;
        @SerializedName("email")
        private String mEmail;
        @SerializedName("password")
        private String mPassword;

        public String getmPassword() {
            return mPassword;
        }

        public void setmPassword(String mPassword) {
            this.mPassword = mPassword;
        }

        public int getId() {
            return mId;

        }

        public void setId(int id) {
            mId = id;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getEmail() {
            return mEmail;
        }

        public void setEmail(String email) {
            mEmail = email;
        }
    }
}
