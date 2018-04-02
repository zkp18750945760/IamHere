package com.zhoukp.signer.module.functions.sign;


import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ISignApi {

    /**
     * 获取课程
     */
    @POST("android_action?serviceNo=2")
    Observable<SignEventsBean> getSignEvents(
            @Query("userId") int userId,
            @Query("isAll") boolean isAll
    );

    /**
     * 发起签到
     */
    @POST("android_action?serviceNo=3")
    Observable<SponsorSignBean> sponsorSign(
            @Query("scheduleId") int scheduleId,
            @Query("userId") int userId,
            @Query("longitude") double longitude,
            @Query("latitude") double latitude,
            @Query("distance") int distance
    );

    /**
     * 发起签到
     */
    @POST("android_action?serviceNo=3")
    Observable<SponsorSignBean> sponsorSignActivity(
            @Query("activityId") int activityId,
            @Query("userId") int userId,
            @Query("longitude") double longitude,
            @Query("latitude") double latitude,
            @Query("distance") int distance
    );

    /**
     * 签到
     */
    @POST("android_action?serviceNo=1")
    Observable<SponsorSignBean> sign(
            @Query("scheduleId") int scheduleId,
            @Query("userId") int userId,
            @Query("longitude") double longitude,
            @Query("latitude") double latitude
    );

    /**
     * 签到
     */
    @POST("android_action?serviceNo=1")
    Observable<SponsorSignBean> signActivity(
            @Query("activityId") int activityId,
            @Query("userId") int userId,
            @Query("longitude") double longitude,
            @Query("latitude") double latitude
    );

    /**
     * 获取某个事项已签到人的头像
     */
    @POST("android_action?serviceNo=5")
    Observable<SignedHeadIconsBean> getSignedHeadIcons(
            @Query("scheduleId") int scheduleId,
            @Query("userId") int userId
    );

}
