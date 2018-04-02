package com.zhoukp.signer.module.activity;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/3/19 15:11
 * @email 275557625@qq.com
 * @function
 */

public interface IActivityApi {
//    /**
//     * 获取所有文体活动信息
//     */
//    @POST("GetActivities?")
//    Observable<ActivityBean> getActivities(
//            @Query("userId") String userId
//    );

    /**
     * 获取所有文体活动信息
     */
    @POST("android_action?serviceNo=6")
    Observable<ActivityBean> getActivities(
            @Query("userId") int userId,
            @Query("type") int type
    );

    /**
     * 报名文体活动
     */
    @POST("android_action?serviceNo=8")
    Observable<ActivityBean> applyActivities(
            @Query("userId") Integer userId,
            @Query("activityId") Integer activityId
    );

    /**
     * 取消报名文体活动
     */
    @POST("android_action?")
    Observable<ActivityBean> cancelApplyActivities(
            @Query("userId") Integer userId,
            @Query("actName") String actName,
            @Query("actTime") String actTime
    );

//    /**
//     * 获取所有志愿活动信息
//     */
//    @POST("GetVolunteers?")
//    Observable<VolunteerBean> getVolunteers(
//            @Query("userId") String userId
//    );

    /**
     * 获取所有文体活动信息
     */
    @POST("android_action?serviceNo=6")
    Observable<ActivityBean> getVolunteers(
            @Query("userId") int userId,
            @Query("type") int type
    );

    /**
     * 报名志愿活动
     */
    @POST("android_action?serviceNo=8")
    Observable<ActivityBean> applyVolunteers(
            @Query("userId") Integer userId,
            @Query("activityId") Integer activityId
    );

    /**
     * 取消报名志愿活动
     */
    @POST("android_action?")
    Observable<ActivityBean> cancelApplyVolunteers(
            @Query("userId") Integer userId,
            @Query("volName") String volName,
            @Query("volTime") String volTime
    );
}
