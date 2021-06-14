package com.college.collegeconnect.datamodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import androidx.preference.PreferenceManager;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SaveSharedPreference {
    private static final String PREF_USER_NAME = "username";
    private static final String USER = "user";
    private static final String CHECKED_ITEM = "checked_item";
    private static final String COURSE = "course";
    private static final String BRANCH = "branch";
    private static final String SEMESTER = "semester";
    private static final String UNIT = "unit";
    private static final String CLEARALL = "clearall";
    private static final String CLEARALL1 = "clearall1";
    private static final String REF = "com.connect.collegeconnect.MyPref";
    private static final float ATTENDANCE_CRITERIA = 75.0f;
    private static final String POP = "pop";
    private static final String DETAILS_UPLOADED = "uploaded";
    private static final String COLLEGE = "college";
    private static final String REVIEW = "review";
    @NotNull
    public static final SaveSharedPreference INSTANCE;

    private static final SharedPreferences getSharedPreferences(Context ctx) {
        SharedPreferences var10000 = PreferenceManager.getDefaultSharedPreferences(ctx);
        Intrinsics.checkNotNullExpressionValue(var10000, "PreferenceManager.getDefaultSharedPreferences(ctx)");
        return var10000;
    }

    @JvmStatic
    public static final void setUploaded(@Nullable Context ctx, @Nullable Boolean var) {
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        Intrinsics.checkNotNull(var);
        editor.putBoolean("uploaded", var);
        editor.apply();
    }

    @JvmStatic
    public static final void setUserName(@Nullable Context ctx, @Nullable String userName) {
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        editor.putString("username", userName);
        editor.apply();
    }

    @JvmStatic
    public static final void setUser(@Nullable Context ctx, @Nullable String user) {
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        editor.putString("user", user);
        editor.apply();
    }

    @JvmStatic
    public static final void setCheckedItem(@Nullable Context ctx, int path) {
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        editor.putInt("checked_item", path);
        editor.apply();
    }

    @JvmStatic
    public static final void setCourse(@Nullable Context ctx, int num) {
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        editor.putInt("course", num);
        editor.apply();
    }

    @JvmStatic
    public static final void setBranch(@Nullable Context ctx, @NotNull String num) {
        Intrinsics.checkNotNullParameter(num, "num");
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        editor.putString("branch", num);
        editor.apply();
    }

    @JvmStatic
    public static final void setSemester(@Nullable Context ctx, int num) {
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        editor.putInt("semester", num);
        editor.apply();
    }

    @JvmStatic
    public static final void setUnit(@Nullable Context ctx, int num) {
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        editor.putInt("unit", num);
        editor.apply();
    }

    @JvmStatic
    public static final void setClearall(@Nullable Context ctx, boolean value) {
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        editor.putBoolean("clearall", value);
        editor.apply();
    }

    @JvmStatic
    public static final void setClearall1(@Nullable Context ctx, boolean value) {
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        editor.putBoolean("clearall1", value);
        editor.apply();
    }

    @JvmStatic
    public static final void setRef(@Nullable Context ctx, boolean value) {
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        editor.putBoolean("com.connect.collegeconnect.MyPref", value);
        editor.apply();
    }

    @JvmStatic
    public static final void setAttendanceCriteria(@Nullable Context ctx, int attendance_criteria) {
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        editor.putInt("attendance_criteria", attendance_criteria);
        editor.apply();
    }

    @JvmStatic
    public static final void setPop(@Nullable Context ctx, int pop) {
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        editor.putInt("pop", pop);
        editor.apply();
    }

    @JvmStatic
    public static final void setCollege(@Nullable Context ctx, @Nullable String college) {
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        editor.putString("college", college);
        editor.apply();
    }

    @JvmStatic
    public static final void setRev(@Nullable Context ctx, boolean value) {
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        editor.putBoolean("review", value);
        editor.apply();
    }

    public static final float getAttendanceCriteria(@Nullable Context ctx) {
        return getSharedPreferences(ctx).getInt("attendance_criteria", 75);
    }

    @JvmStatic
    @Nullable
    public static final String getUserName(@Nullable Context ctx) {
        return INSTANCE.getSharedPreferences(ctx).getString("username", "");
    }

    @JvmStatic
    @Nullable
    public static final String getUser(@Nullable Context ctx) {
        return INSTANCE.getSharedPreferences(ctx).getString("user", "");
    }

    @JvmStatic
    public static final int getCheckedItem(@Nullable Context ctx) {
        return INSTANCE.getSharedPreferences(ctx).getInt("checked_item", 2);
    }

    @JvmStatic
    public static final int getCourse(@Nullable Context ctx) {
        return INSTANCE.getSharedPreferences(ctx).getInt("course", 0);
    }

    @JvmStatic
    @Nullable
    public static final String getBranch(@Nullable Context ctx) {
        return INSTANCE.getSharedPreferences(ctx).getString("branch", "");
    }

    @JvmStatic
    public static final int getSemester(@Nullable Context ctx) {
        return INSTANCE.getSharedPreferences(ctx).getInt("semester", 0);
    }

    @JvmStatic
    public static final int getUnit(@Nullable Context ctx) {
        return INSTANCE.getSharedPreferences(ctx).getInt("unit", 0);
    }

    @JvmStatic
    public static final boolean getClearall(@Nullable Context ctx) {
        return INSTANCE.getSharedPreferences(ctx).getBoolean("clearall", false);
    }

    public final boolean getClearall1(@Nullable Context ctx) {
        return this.getSharedPreferences(ctx).getBoolean("clearall1", false);
    }

    @JvmStatic
    public static final boolean getRef(@Nullable Context ctx) {
        return INSTANCE.getSharedPreferences(ctx).getBoolean("com.connect.collegeconnect.MyPref", false);
    }

    @JvmStatic
    public static final boolean getUpload(@Nullable Context ctx) {
        return INSTANCE.getSharedPreferences(ctx).getBoolean("uploaded", false);
    }

    @JvmStatic
    public static final int getPop(@Nullable Context ctx) {
        return INSTANCE.getSharedPreferences(ctx).getInt("pop", 1);
    }

    @JvmStatic
    @Nullable
    public static final String getCollege(@Nullable Context ctx) {
        return INSTANCE.getSharedPreferences(ctx).getString("college", "other");
    }

    @JvmStatic
    public static final boolean getReview(@Nullable Context ctx) {
        return INSTANCE.getSharedPreferences(ctx).getBoolean("review", false);
    }

    @JvmStatic
    public static final void clearUserName(@Nullable Context ctx) {
        Editor editor = INSTANCE.getSharedPreferences(ctx).edit();
        editor.remove("username");
        editor.remove("user");
        editor.remove("checked_item");
        editor.remove("course");
        editor.remove("branch");
        editor.remove("semester");
        editor.remove("clearall1");
        editor.remove("clearall");
        editor.remove("unit");
        editor.remove("attendance_criteria");
        editor.remove("com.connect.collegeconnect.MyPref");
        editor.remove("college");
        editor.apply();
    }

    private SaveSharedPreference() {
    }

    static {
        SaveSharedPreference var0 = new SaveSharedPreference();
        INSTANCE = var0;
    }
}
