package com.college.collegeconnect.ui.home;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import com.amulyakhare.textdrawable.TextDrawable;
import com.college.collegeconnect.R;
import com.college.collegeconnect.R.id;
import com.college.collegeconnect.adapters.HomeRecyclerAdapter;
import com.college.collegeconnect.database.entity.TimetableEntity;
import com.college.collegeconnect.datamodels.SaveSharedPreference;
import com.college.collegeconnect.settingsActivity.SettingsActivity;
import com.college.collegeconnect.utils.ImageHandler;
import com.college.collegeconnect.viewmodels.HomeViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import de.hdodenhof.circleimageview.CircleImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.BooleanRef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public final class HomeFragment extends Fragment {
    @Nullable
    private BottomNavigationView bottomNavigationView;
    @Nullable
    private TextDrawable drawable;
    public TextView tv;
    @Nullable
    private TextView totalAttendance;
    @Nullable
    private TextView nameField;
    @Nullable
    private TextView enrollNo;
    @Nullable
    private TextView branch;
    @Nullable
    private CircleImageView prfileImage;
    private final FirebaseStorage storage;
    @Nullable
    private Uri uri;
    private StorageReference storageRef;
    private Context mcontext;
    public HomeViewModel homeViewModel;
    @Nullable
    private ListenerRegistration registered;
    private HashMap _$_findViewCache;

    @Nullable
    public final BottomNavigationView getBottomNavigationView() {
        return this.bottomNavigationView;
    }

    public final void setBottomNavigationView(@Nullable BottomNavigationView var1) {
        this.bottomNavigationView = var1;
    }

    @Nullable
    public final TextDrawable getDrawable() {
        return this.drawable;
    }

    public final void setDrawable(@Nullable TextDrawable var1) {
        this.drawable = var1;
    }

    @NotNull
    public final TextView getTv() {
        TextView var10000 = this.tv;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tv");
        }

        return var10000;
    }

    public final void setTv(@NotNull TextView var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.tv = var1;
    }

    @Nullable
    public final TextView getTotalAttendance() {
        return this.totalAttendance;
    }

    public final void setTotalAttendance(@Nullable TextView var1) {
        this.totalAttendance = var1;
    }

    @Nullable
    public final TextView getNameField() {
        return this.nameField;
    }

    public final void setNameField(@Nullable TextView var1) {
        this.nameField = var1;
    }

    @Nullable
    public final TextView getEnrollNo() {
        return this.enrollNo;
    }

    public final void setEnrollNo(@Nullable TextView var1) {
        this.enrollNo = var1;
    }

    @Nullable
    public final TextView getBranch() {
        return this.branch;
    }

    public final void setBranch(@Nullable TextView var1) {
        this.branch = var1;
    }

    @Nullable
    public final CircleImageView getPrfileImage() {
        return this.prfileImage;
    }

    public final void setPrfileImage(@Nullable CircleImageView var1) {
        this.prfileImage = var1;
    }

    @Nullable
    public final Uri getUri() {
        return this.uri;
    }

    public final void setUri(@Nullable Uri var1) {
        this.uri = var1;
    }

    @NotNull
    public final HomeViewModel getHomeViewModel() {
        HomeViewModel var10000 = this.homeViewModel;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeViewModel");
        }

        return var10000;
    }

    public final void setHomeViewModel(@NotNull HomeViewModel var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.homeViewModel = var1;
    }

    @Nullable
    public final ListenerRegistration getRegistered() {
        return this.registered;
    }

    public final void setRegistered(@Nullable ListenerRegistration var1) {
        this.registered = var1;
    }

    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.prfileImage = (CircleImageView)view.findViewById(R.id.imageView3);
        this.nameField = (TextView)view.findViewById(R.id.nameField);
        this.enrollNo = (TextView)view.findViewById(R.id.textView3);
        this.branch = (TextView)view.findViewById(R.id.textView4);
        this.totalAttendance = (TextView)view.findViewById(R.id.aggregateAttendance);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (this.getActivity() != null) {
            this.bottomNavigationView = (BottomNavigationView)this.requireActivity().findViewById(R.id.bottomNav);
        }

        View var10001 = this.requireActivity().findViewById(R.id.navTitle);
        Intrinsics.checkNotNullExpressionValue(var10001, "requireActivity().findViewById(R.id.navTitle)");
        this.tv = (TextView)var10001;
        TextView var10000 = this.tv;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tv");
        }

        var10000.setText((CharSequence)"HOME");
        this.storageRef = this.storage.getReference();
        var10000 = this.totalAttendance;
        Intrinsics.checkNotNull(var10000);
        var10000.setEnabled(false);
        var10000 = this.nameField;
        Intrinsics.checkNotNull(var10000);
        var10000.setEnabled(false);
        var10000 = this.enrollNo;
        Intrinsics.checkNotNull(var10000);
        var10000.setEnabled(false);
        var10000 = this.branch;
        Intrinsics.checkNotNull(var10000);
        var10000.setEnabled(false);
        ViewModel var10 = (new ViewModelProvider((ViewModelStoreOwner)this)).get(HomeViewModel.class);
        Intrinsics.checkNotNullExpressionValue(var10, "ViewModelProvider(this).…omeViewModel::class.java)");
        this.homeViewModel = (HomeViewModel)var10;
        HomeViewModel var11 = this.homeViewModel;
        if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeViewModel");
        }

        var11.returnName().observe((LifecycleOwner)this.requireActivity(), (Observer)(new Observer() {
            // $FF: synthetic method
            // $FF: bridge method
            public void onChanged(Object var1) {
                this.onChanged((String)var1);
            }

            public final void onChanged(String s) {
                TextView var10000 = HomeFragment.this.getNameField();
                Intrinsics.checkNotNull(var10000);
                var10000.setText((CharSequence)s);
            }
        }));
        var11 = this.homeViewModel;
        if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeViewModel");
        }

        var11.returnRoll().observe((LifecycleOwner)this.requireActivity(), (Observer)(new Observer() {
            // $FF: synthetic method
            // $FF: bridge method
            public void onChanged(Object var1) {
                this.onChanged((String)var1);
            }

            public final void onChanged(String s) {
                TextView var10000 = HomeFragment.this.getEnrollNo();
                Intrinsics.checkNotNull(var10000);
                var10000.setText((CharSequence)s);
            }
        }));
        var11 = this.homeViewModel;
        if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeViewModel");
        }

        var11.returnBranch().observe((LifecycleOwner)this.requireActivity(), (Observer)(new Observer() {
            // $FF: synthetic method
            // $FF: bridge method
            public void onChanged(Object var1) {
                this.onChanged((String)var1);
            }

            public final void onChanged(String s) {
                TextView var10000 = HomeFragment.this.getBranch();
                Intrinsics.checkNotNull(var10000);
                var10000.setText((CharSequence)s);
            }
        }));
        File file = new File("/data/user/0/com.college.collegeconnect/files/dp.jpeg");
        if (file.exists()) {
            this.uri = Uri.fromFile(file);
            Context var12 = this.getContext();
            if (var12 != null) {
                var12 = var12.getApplicationContext();
                if (var12 != null) {
                    Context var3 = var12;
                    boolean var4 = false;
                    boolean var5 = false;
                    Picasso var13 = (new ImageHandler()).getSharedInstance(var3);
                    if (var13 != null) {
                        RequestCreator var14 = var13.load(this.uri);
                        if (var14 != null) {
                            var14.into((ImageView)this.prfileImage);
                        }
                    }
                }
            }

            Log.d("HomeFrag", "onClick: already exists");
        } else {
            StorageReference var15 = this.storageRef;
            Intrinsics.checkNotNull(var15);
            var15 = var15.child("User/" + SaveSharedPreference.getUserName((Context)this.getActivity()) + "/DP.jpeg");
            Intrinsics.checkNotNullExpressionValue(var15, "storageRef!!.child(\"User…e(activity) + \"/DP.jpeg\")");
            Intrinsics.checkNotNullExpressionValue(var15.getDownloadUrl().addOnSuccessListener((OnSuccessListener)(new OnSuccessListener() {
                // $FF: synthetic method
                // $FF: bridge method
                public void onSuccess(Object var1) {
                    this.onSuccess((Uri)var1);
                }

                public final void onSuccess(Uri uri) {
                    HomeFragment.this.setUri(uri);
                    HomeFragment.this.download_dp();
                }
            })).addOnFailureListener((OnFailureListener)null), "storageRef!!.child(\"User….addOnFailureListener { }");
        }

        if (this.uri != null) {
            Picasso.get().load(this.uri).into((ImageView)this.prfileImage);
        }

        var11 = this.homeViewModel;
        if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeViewModel");
        }

        var11.getAttended().observe((LifecycleOwner)this.requireActivity(), (Observer)(new Observer() {
            // $FF: synthetic method
            // $FF: bridge method
            public void onChanged(Object var1) {
                this.onChanged((Integer)var1);
            }

            public final void onChanged(final Integer atten) {
                HomeFragment.this.getHomeViewModel().getMissed().observe((LifecycleOwner)HomeFragment.this.requireActivity(), (Observer)(new Observer() {
                    // $FF: synthetic method
                    // $FF: bridge method
                    public void onChanged(Object var1) {
                        this.onChanged((Integer)var1);
                    }

                    public final void onChanged(Integer miss) {
                        TextView var10000;
                        if (atten != null && miss != null) {
                            float percentage = (float)atten / ((float)atten + (float)miss);
                            boolean var4 = false;
                            if (Float.isNaN(percentage)) {
                                var10000 = HomeFragment.this.getTotalAttendance();
                                Intrinsics.checkNotNull(var10000);
                                var10000.setText((CharSequence)"0%");
                            } else {
                                var10000 = HomeFragment.this.getTotalAttendance();
                                Intrinsics.checkNotNull(var10000);
                                StringBuilder var10001 = new StringBuilder();
                                String var3 = "%.0f";
                                Object[] var6 = new Object[]{percentage * (float)100};
                                boolean var5 = false;
                                String var10002 = String.format(var3, Arrays.copyOf(var6, var6.length));
                                Intrinsics.checkNotNullExpressionValue(var10002, "java.lang.String.format(this, *args)");
                                var10000.setText((CharSequence)var10001.append(var10002).append("%").toString());
                            }
                        } else {
                            var10000 = HomeFragment.this.getTotalAttendance();
                            Intrinsics.checkNotNull(var10000);
                            var10000.setText((CharSequence)"0%");
                        }

                    }
                }));
            }
        }));
        var11 = this.homeViewModel;
        if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeViewModel");
        }

        var11.getMissed().observe((LifecycleOwner)this.requireActivity(), (Observer)(new Observer() {
            // $FF: synthetic method
            // $FF: bridge method
            public void onChanged(Object var1) {
                this.onChanged((Integer)var1);
            }

            public final void onChanged(final Integer miss) {
                HomeFragment.this.getHomeViewModel().getAttended().observe((LifecycleOwner)HomeFragment.this.requireActivity(), (Observer)(new Observer() {
                    // $FF: synthetic method
                    // $FF: bridge method
                    public void onChanged(Object var1) {
                        this.onChanged((Integer)var1);
                    }

                    public final void onChanged(Integer atten) {
                        TextView var10000;
                        if (atten != null && miss != null) {
                            float percentage = (float)atten / ((float)atten + (float)miss);
                            boolean var4 = false;
                            if (Float.isNaN(percentage)) {
                                var10000 = HomeFragment.this.getTotalAttendance();
                                Intrinsics.checkNotNull(var10000);
                                var10000.setText((CharSequence)"0%");
                            } else {
                                var10000 = HomeFragment.this.getTotalAttendance();
                                Intrinsics.checkNotNull(var10000);
                                StringBuilder var10001 = new StringBuilder();
                                String var3 = "%.0f";
                                Object[] var6 = new Object[]{percentage * (float)100};
                                boolean var5 = false;
                                String var10002 = String.format(var3, Arrays.copyOf(var6, var6.length));
                                Intrinsics.checkNotNullExpressionValue(var10002, "java.lang.String.format(this, *args)");
                                var10000.setText((CharSequence)var10001.append(var10002).append("%").toString());
                            }
                        } else {
                            var10000 = HomeFragment.this.getTotalAttendance();
                            Intrinsics.checkNotNull(var10000);
                            var10000.setText((CharSequence)"0%");
                        }

                    }
                }));
            }
        }));
        ((ImageButton)this._$_findCachedViewById(id.settings_btn)).setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                Context var10000 = HomeFragment.this.getContext();
                if (var10000 != null) {
                    var10000.startActivity(new Intent(HomeFragment.this.getContext(), SettingsActivity.class));
                }

            }
        }));
        RecyclerView var8 = (RecyclerView)this._$_findCachedViewById(id.recyclerviewHome);

        var8.setLayoutManager((LayoutManager)(new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false)));
        final BooleanRef now = new BooleanRef();
        now.element = false;
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(R.drawable.ic_time_table_white);
        ArrayList<String> s = new ArrayList<>();
        s.add("Time Table");

        HomeRecyclerAdapter homeRecyclerAdapter= new HomeRecyclerAdapter(this.getContext());
        var8.setAdapter(homeRecyclerAdapter);

        var11 = this.homeViewModel;
        if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeViewModel");
        }

        var11.getHappeningNow().observe((LifecycleOwner)this.requireActivity(), (Observer)(new Observer() {
            // $FF: synthetic method
            // $FF: bridge method
            public void onChanged(Object var1) {
                try {
                    this.onChanged((List)var1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            public final void onChanged(List it) throws ParseException {
                Intrinsics.checkNotNullExpressionValue(it, "it");
                Iterable $this$forEach$iv = (Iterable)it;
                Iterator var4 = $this$forEach$iv.iterator();

                TextView var17;
                while(var4.hasNext()) {
                    Object element$iv = var4.next();
                    TimetableEntity it1 = (TimetableEntity)element$iv;
                    String pattern = "dd-MM-yyyy";
                    String var10000 = (new SimpleDateFormat(pattern, Locale.getDefault())).format(new Date());
                    Intrinsics.checkNotNullExpressionValue(var10000, "SimpleDateFormat(pattern…Default()).format(Date())");
                    String dateInString = var10000;
                    long startingTime = HomeFragment.this.getMilli(dateInString + ' ' + it1.getStartTime());
                    long endingTime = HomeFragment.this.getMilli(dateInString + ' ' + it1.getEndTime());
                    long var14 = System.currentTimeMillis();
                    if (startingTime <= var14) {
                        if (endingTime > var14) {
                            var17 = (TextView)HomeFragment.this._$_findCachedViewById(id.txt_now_subject_title);
                            Intrinsics.checkNotNullExpressionValue(var17, "txt_now_subject_title");
                            var17.setText((CharSequence)it1.getSubjectName());
                            var17 = (TextView)HomeFragment.this._$_findCachedViewById(id.txt_now_room_num);
                            Intrinsics.checkNotNullExpressionValue(var17, "txt_now_room_num");
                            var17.setText((CharSequence)it1.getRoomNumber());
                            var17 = (TextView)HomeFragment.this._$_findCachedViewById(id.txt_now_time);
                            Intrinsics.checkNotNullExpressionValue(var17, "txt_now_time");
                            var17.setText((CharSequence)(it1.getStartTimeShow() + " - " + it1.getEndTimeShow()));
                            now.element = true;
                        }
                    }
                }

                if (!now.element) {
                    try {
                        var17 = (TextView)HomeFragment.this._$_findCachedViewById(id.txt_now_state);
                        Intrinsics.checkNotNullExpressionValue(var17, "txt_now_state");
                        var17.setText((CharSequence)"No class happening currently");
                        var17 = (TextView)HomeFragment.this._$_findCachedViewById(id.txt_now_time);
                        Intrinsics.checkNotNullExpressionValue(var17, "txt_now_time");
                        var17.setVisibility(View.GONE);
                        ConstraintLayout var18 = (ConstraintLayout)HomeFragment.this._$_findCachedViewById(id.card_now_class);
                        Intrinsics.checkNotNullExpressionValue(var18, "card_now_class");
                        var18.setVisibility(View.GONE);
                    } catch (Exception var16) {
                        Log.d("HomeFragment", "onActivityCreated: " + var16.getMessage());
                    }
                }

            }
        }));
    }

    private final long getMilli(String myDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault());
        Date d = format.parse(myDate);
        Intrinsics.checkNotNullExpressionValue(d, "d");
        return d.getTime();
    }

    private final void download_dp() {
        Object var10000 = this.requireContext().getSystemService(Context.DOWNLOAD_SERVICE);
        if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.app.DownloadManager");
        } else {
            final DownloadManager downloadManager = (DownloadManager)var10000;
            Request request = new Request(this.uri);
            request.setDestinationInExternalFilesDir(this.getContext(), "", "dp.jpeg");
            final long id = downloadManager.enqueue(request);
            BroadcastReceiver onComplete = (BroadcastReceiver)(new BroadcastReceiver() {
                public void onReceive(@NotNull Context context, @NotNull Intent intent) {
                    Intrinsics.checkNotNullParameter(context, "context");
                    Intrinsics.checkNotNullParameter(intent, "intent");
                    Cursor c = downloadManager.query((new Query()).setFilterById(new long[]{id}));
                    if (c != null) {
                        c.moveToFirst();

                        try {
                            String fileUri = c.getString(c.getColumnIndex("local_uri"));
                            HomeFragment.this.setUri(Uri.parse(fileUri));
                            Picasso.get().load(HomeFragment.this.getUri()).into((ImageView)HomeFragment.this.getPrfileImage());
                            HomeFragment var10000 = HomeFragment.this;
                            Context var10003 = HomeFragment.this.requireContext();
                            Intrinsics.checkNotNullExpressionValue(var10003, "requireContext()");
                            File var6 = var10003.getFilesDir();
                            Intrinsics.checkNotNullExpressionValue(var6, "requireContext().filesDir");
                            String var7 = var6.getAbsolutePath();
                            Intrinsics.checkNotNullExpressionValue(var7, "requireContext().filesDir.absolutePath");
                            var10000.copyFile("/storage/emulated/0/Android/data/com.college.collegeconnect/files", "/dp.jpeg", var7);
                            (new File("/storage/emulated/0/Android/data/com.college.collegeconnect/files/dp.jpeg")).delete();
                        } catch (Exception var5) {
                            Log.e("error", "Could not open the downloaded file");
                        }
                    }

                }
            });
            this.requireContext().registerReceiver(onComplete, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
        }
    }

    private final void copyFile(String inputPath, String inputFile, String outputPath) {
        InputStream in = (InputStream)null;
        OutputStream out = (OutputStream)null;

        try {
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            in = (InputStream)(new FileInputStream(inputPath + inputFile));
            out = (OutputStream)(new FileOutputStream(outputPath + inputFile));
            byte[] buffer = new byte[1024];
            boolean var8 = false;

            while(true) {
                int var9 = in.read(buffer);
                boolean var10 = false;
                boolean var11 = false;

                if (var9 == -1) {
                    in.close();
                    in = (InputStream)null;
                    out.flush();
                    out.close();
                    out = (OutputStream)null;
                    break;
                }

                out.write(buffer, 0, var9);
            }
        } catch (FileNotFoundException var14) {
            Log.e("tag", String.valueOf(var14.getMessage()));
        } catch (Exception var15) {
            Log.e("tag", String.valueOf(var15.getMessage()));
        }

    }

    public void onStart() {
        super.onStart();
        BottomNavigationView var10000 = this.bottomNavigationView;
        Intrinsics.checkNotNull(var10000);
        MenuItem var1 = var10000.getMenu().findItem(R.id.nav_home);
        var1.setChecked(true);
    }

    public void onAttach(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.onAttach(context);
        this.mcontext = context;
    }

    public void onResume() {
        super.onResume();
        BottomNavigationView var10000 = this.bottomNavigationView;
        Intrinsics.checkNotNull(var10000);
        MenuItem var2 = var10000.getMenu().findItem(R.id.nav_home);
        var2.setChecked(true);
        if (SaveSharedPreference.INSTANCE.getClearall1(this.mcontext)) {
            File file = new File("/data/user/0/com.college.collegeconnect/files/dp.jpeg");
            if (file.exists()) {
                this.uri = Uri.fromFile(file);
                Picasso.get().invalidate(this.uri);
                SaveSharedPreference.setClearall1(this.getContext(), false);
                Picasso.get().load(this.uri).into((ImageView)this.prfileImage);
            }
        }

    }

    public void onDestroyView() {
        if (this.registered != null) {
            ListenerRegistration var10000 = this.registered;
            Intrinsics.checkNotNull(var10000);
            var10000.remove();
        }

        super.onDestroyView();
        this._$_clearFindViewByIdCache();
    }

    public HomeFragment() {
        FirebaseStorage var10001 = FirebaseStorage.getInstance();
        Intrinsics.checkNotNullExpressionValue(var10001, "FirebaseStorage.getInstance()");
        this.storage = var10001;
    }

    public View _$_findCachedViewById(int var1) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }

        View var2 = (View)this._$_findViewCache.get(var1);
        if (var2 == null) {
            View var10000 = this.getView();
            if (var10000 == null) {
                return null;
            }

            var2 = var10000.findViewById(var1);
            this._$_findViewCache.put(var1, var2);
        }

        return var2;
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }

    }
}
