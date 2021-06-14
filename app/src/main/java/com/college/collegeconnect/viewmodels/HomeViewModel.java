package com.college.collegeconnect.viewmodels;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.college.collegeconnect.database.AttendanceDatabase;
import com.college.collegeconnect.database.TimeTableDatabase;
import com.college.collegeconnect.datamodels.SaveSharedPreference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.FirebaseFirestoreSettings.Builder;
import java.util.Calendar;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public final class HomeViewModel extends AndroidViewModel {
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference;
    private ListenerRegistration registered;
    private MutableLiveData nameLive;
    private MutableLiveData rollNoLive;
    private MutableLiveData branchLive;
    private MutableLiveData classList;



    @Nullable
    public final MutableLiveData getNameLive() {
        return this.nameLive;
    }

    @Nullable
    public final MutableLiveData getRollNoLive() {
        return this.rollNoLive;
    }


    @Nullable
    public final MutableLiveData getBranchLive() {
        return this.branchLive;
    }

    @NotNull
    public final LiveData returnName() {
        if (this.nameLive == null) {
            this.nameLive = new MutableLiveData();
            this.loadData();
        }

        MutableLiveData var10000 = this.nameLive;
        return (LiveData)var10000;
    }

    @NotNull
    public final LiveData returnNow() {
        if (this.classList == null) {
            this.classList = new MutableLiveData();
            Log.d("TAG", "returnNow: called");
            this.getHappeningNow();
        }

        StringBuilder var10001 = (new StringBuilder()).append("returnNow: ");
        MutableLiveData var10002 = this.classList;
        Intrinsics.checkNotNull(var10002);
        Log.d("TAG", var10001.append(String.valueOf(var10002.getValue())).toString());
        MutableLiveData var10000 = this.classList;
        Intrinsics.checkNotNull(var10000);
        return (LiveData)var10000;
    }

    @NotNull
    public final LiveData returnRoll() {
        if (this.rollNoLive == null) {
            this.rollNoLive = new MutableLiveData();
            this.loadData();
        }

        MutableLiveData var10000 = this.rollNoLive;
        Intrinsics.checkNotNull(var10000);
        return (LiveData)var10000;
    }

    @NotNull
    public final LiveData returnBranch() {
        if (this.branchLive == null) {
            this.branchLive = new MutableLiveData();
            this.loadData();
        }

        MutableLiveData var10000 = this.branchLive;
        Intrinsics.checkNotNull(var10000);
        return (LiveData)var10000;
    }

    public final void loadData() {
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestore var10001 = this.firebaseFirestore;
        Intrinsics.checkNotNull(var10001);
        CollectionReference var3 = var10001.collection("users");
        FirebaseAuth var10002 = FirebaseAuth.getInstance();
        Intrinsics.checkNotNullExpressionValue(var10002, "FirebaseAuth.getInstance()");
        FirebaseUser var5 = var10002.getCurrentUser();
        this.documentReference = var3.document(String.valueOf(var5 != null ? var5.getUid() : null));
        FirebaseFirestoreSettings var10000 = (new Builder()).setPersistenceEnabled(true).build();
        Intrinsics.checkNotNullExpressionValue(var10000, "FirebaseFirestoreSetting…\n                .build()");
        FirebaseFirestoreSettings settings = var10000;
        FirebaseFirestore var2 = this.firebaseFirestore;
        Intrinsics.checkNotNull(var2);
        var2.setFirestoreSettings(settings);
        DocumentReference var4 = this.documentReference;
        Intrinsics.checkNotNull(var4);
        this.registered = var4.addSnapshotListener(MetadataChanges.INCLUDE, (EventListener)(new EventListener() {

            public void onEvent(Object var1, FirebaseFirestoreException var2) {
                this.onEvent((DocumentSnapshot)var1, var2);
            }

            public final void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                try {
                    Intrinsics.checkNotNull(documentSnapshot);
                    String name = documentSnapshot.getString("name");
                    String rollNo = documentSnapshot.getString("rollno");
                    String strbranch = documentSnapshot.getString("branch");
                    String college = documentSnapshot.getString("college");
                    SaveSharedPreference.setCollege((Context)HomeViewModel.this.getApplication(), college);
                    SaveSharedPreference.setUser((Context)HomeViewModel.this.getApplication(), name);
                    MutableLiveData var10000 = HomeViewModel.this.getNameLive();
                    if (var10000 != null) {
                        var10000.postValue(name);
                    }

                    var10000 = HomeViewModel.this.getRollNoLive();
                    if (var10000 != null) {
                        var10000.postValue(rollNo);
                    }

                    var10000 = HomeViewModel.this.getBranchLive();
                    if (var10000 != null) {
                        var10000.postValue(strbranch);
                    }
                } catch (Exception var7) {
                    Log.d("Home", "onEvent: " + var7.getMessage());
                }

            }
        }));
    }

    @NotNull
    public final LiveData getHappeningNow() {
        TimeTableDatabase.Companion var10000;
        Application var10001;
        LiveData var2;
        switch(Calendar.getInstance().get(7)) {
            case 1:
                var10000 = TimeTableDatabase.Companion;
                var10001 = this.getApplication();
                Intrinsics.checkNotNullExpressionValue(var10001, "getApplication()");
                var2 = var10000.invoke((Context)var10001).getSundayDao().getSunClasses();
                break;
            case 2:
                var10000 = TimeTableDatabase.Companion;
                var10001 = this.getApplication();
                Intrinsics.checkNotNullExpressionValue(var10001, "getApplication()");
                var2 = var10000.invoke((Context)var10001).getMondayDao().getMonClasses();
                break;
            case 3:
                var10000 = TimeTableDatabase.Companion;
                var10001 = this.getApplication();
                Intrinsics.checkNotNullExpressionValue(var10001, "getApplication()");
                var2 = var10000.invoke((Context)var10001).getTuesdayDao().getTuesClasses();
                break;
            case 4:
                var10000 = TimeTableDatabase.Companion;
                var10001 = this.getApplication();
                Intrinsics.checkNotNullExpressionValue(var10001, "getApplication()");
                var2 = var10000.invoke((Context)var10001).getWednesdayDao().getWedClasses();
                break;
            case 5:
                var10000 = TimeTableDatabase.Companion;
                var10001 = this.getApplication();
                Intrinsics.checkNotNullExpressionValue(var10001, "getApplication()");
                var2 = var10000.invoke((Context)var10001).getThursdayDao().getThursClasses();
                break;
            case 6:
                var10000 = TimeTableDatabase.Companion;
                var10001 = this.getApplication();
                Intrinsics.checkNotNullExpressionValue(var10001, "getApplication()");
                var2 = var10000.invoke((Context)var10001).getFridayDao().getFriClasses();
                break;
            case 7:
                var10000 = TimeTableDatabase.Companion;
                var10001 = this.getApplication();
                Intrinsics.checkNotNullExpressionValue(var10001, "getApplication()");
                var2 = var10000.invoke((Context)var10001).getSaturdayDao().getSatClasses();
                break;
            default:
                var2 = null;
        }

        LiveData classlist = var2;
        Intrinsics.checkNotNull(classlist);
        return classlist;
    }

    @NotNull
    public final LiveData getAttended() {
        com.college.collegeconnect.database.AttendanceDatabase.Companion var10000 = AttendanceDatabase.Companion;
        Application var10001 = this.getApplication();
        return var10000.invoke((Context)var10001).getAttendanceDao().getAttended();
    }

    @NotNull
    public final LiveData getMissed() {
        com.college.collegeconnect.database.AttendanceDatabase.Companion var10000 = AttendanceDatabase.Companion;
        Application var10001 = this.getApplication();
        return var10000.invoke((Context)var10001).getAttendanceDao().getMissed();
    }

    public HomeViewModel(@NotNull Application application) {
        super(application);
    }
}
