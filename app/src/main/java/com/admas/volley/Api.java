package com.admas.volley;

import org.intellij.lang.annotations.JdkConstants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("tealogin")
    Call<ResponseBody> teacher_login(
            @Field("username") String username,
            @Field("password") String password,
            @Field("table") String table
    );

    @FormUrlEncoded
    @POST("stulogin")
    Call<ResponseBody> student_login(
            @Field("username") String username,
            @Field("password") String password,
            @Field("table") String table
    );

    @FormUrlEncoded
    @POST("studcourselister")
    Call<ResponseBody> fetch_attendance(
            @Field("select") String student
    );

    @FormUrlEncoded
    @POST("courseselector")
    Call<ResponseBody> fetch_course_ins(
            @Field("select") String instructor
    );

    @FormUrlEncoded
    @POST("sectionselector")
    Call<ResponseBody> fetch_section_ins(
            @Field("select") String course
    );
    @FormUrlEncoded
    @POST("studentnamelist")
    Call<ResponseBody> fetch_name_stu(
            @Field("select") String section
    );

    @FormUrlEncoded
    @POST("studentidlist")
    Call<ResponseBody> fetch_id_stu(
            @Field("select") String section
    );

    @FormUrlEncoded
    @POST("saveattendance")
    Call<ResponseBody> save_attendance(
            @Field("session") String rest,
            @Field("present") String res,
            @Field("permission") String reso
    );

    @FormUrlEncoded
    @POST("changepass")
    Call<ResponseBody> change_pass(
            @Field("id") String user_id,
            @Field("table") String table,
            @Field("old") String old_pass,
            @Field("pass") String new_pass
    );
}
