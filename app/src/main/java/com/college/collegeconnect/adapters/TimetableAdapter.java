package com.college.collegeconnect.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.college.collegeconnect.R;
import com.college.collegeconnect.database.entity.TimetableEntity;
import com.college.collegeconnect.timetable.NewTimeTableViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public  class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.ViewHolder> {
    private final ArrayList subjects;
    private final Context context;
    private NewTimeTableViewModel newTimeTableViewModel;
    private final int dayOfWeek;

    @NotNull
    public TimetableAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_card, parent, false);
        return new TimetableAdapter.ViewHolder(view);
    }

   

    public void onBindViewHolder(@NotNull TimetableAdapter.ViewHolder holder, final int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        if (position == this.subjects.size() - 1) {
            View var10000 = holder.getBottomDivider();
            var10000.setVisibility(View.GONE);
        }

        label24: {
            holder.getHeading().setText((CharSequence)((TimetableEntity)this.subjects.get(position)).getSubjectName());
            holder.getTime().setText((CharSequence)(((TimetableEntity)this.subjects.get(position)).getStartTimeShow() + " - " + ((TimetableEntity)this.subjects.get(position)).getEndTimeShow()));
            holder.getRoomNumber().setText((CharSequence)((TimetableEntity)this.subjects.get(position)).getRoomNumber());
            String pattern = "dd-MM-yyyy";
            String var11 = (new SimpleDateFormat(pattern, Locale.getDefault())).format(new Date());
            Intrinsics.checkNotNullExpressionValue(var11, "SimpleDateFormat(pattern…Default()).format(Date())");
            String dateInString = var11;
            long startingTime = 0;
            try {
                startingTime = this.getMilli(dateInString + ' ' + ((TimetableEntity)this.subjects.get(position)).getStartTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long endingTime = 0;
            try {
                endingTime = this.getMilli(dateInString + ' ' + ((TimetableEntity)this.subjects.get(position)).getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.d("TimeTableAdapter", this.dayOfWeek + 2 + " Calendar = " + Calendar.getInstance().get(7));
            long var9 = System.currentTimeMillis();
            if (startingTime <= var9) {
                if (endingTime > var9) {
                    if (this.dayOfWeek + 2 == Calendar.getInstance().get(7)) {
                        holder.getState().setVisibility(View.VISIBLE);
                        holder.getStateCircle().setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_ellipse_filled));
                    }

                    if (this.dayOfWeek == 6 && Calendar.getInstance().get(7) == 1) {
                        holder.getState().setVisibility(View.VISIBLE);
                        holder.getStateCircle().setImageDrawable(ContextCompat.getDrawable(this.context,  R.drawable.ic_ellipse_filled));
                    }
                    break label24;
                }
            }

            holder.getState().setVisibility(View.INVISIBLE);
            holder.getStateCircle().setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_ellipse_hollow));
        }

        holder.itemView.setOnLongClickListener((OnLongClickListener)(new OnLongClickListener() {
            public final boolean onLongClick(View it) {
                Builder builder = new Builder(TimetableAdapter.this.context);
                Context var10000 = TimetableAdapter.this.context;
                if (var10000 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type androidx.appcompat.app.AppCompatActivity");
                } else {
                    LayoutInflater var6 = ((AppCompatActivity)var10000).getLayoutInflater();
                    Intrinsics.checkNotNullExpressionValue(var6, "(context as AppCompatActivity).layoutInflater");
                    LayoutInflater inflater = var6;
                    View view = inflater.inflate(R.layout.layout_delete_timetable_class, (ViewGroup)null);
                    View var7 = view.findViewById(R.id.txt_sub_name);
                    Intrinsics.checkNotNullExpressionValue(var7, "view.findViewById<TextView>(R.id.txt_sub_name)");
                    ((TextView)var7).setText((CharSequence)((TimetableEntity)TimetableAdapter.this.subjects.get(position)).getSubjectName());
                    var7 = view.findViewById(R.id.txt_time_schedule);
                    Intrinsics.checkNotNullExpressionValue(var7, "view.findViewById<TextVi…>(R.id.txt_time_schedule)");
                    ((TextView)var7).setText((CharSequence)(((TimetableEntity)TimetableAdapter.this.subjects.get(position)).getStartTimeShow() + " - " + ((TimetableEntity)TimetableAdapter.this.subjects.get(position)).getEndTimeShow()));
                    var7 = view.findViewById(R.id.txt_day);
                    Intrinsics.checkNotNullExpressionValue(var7, "view.findViewById<TextView>(R.id.txt_day)");
                    ((TextView)var7).setText((CharSequence)SectionsPagerAdapter.TAB_TITLES[TimetableAdapter.this.dayOfWeek]);
                    builder.setView(view);
                    AlertDialog var8 = builder.create();
                    Intrinsics.checkNotNullExpressionValue(var8, "builder.create()");
                    final AlertDialog dialog = var8;
                    Window var9 = dialog.getWindow();
                    if (var9 != null) {
                        var9.setBackgroundDrawable((Drawable)(new ColorDrawable(0)));
                    }

                    ((Button)view.findViewById(R.id.btn_delete_class)).setOnClickListener((OnClickListener)(new OnClickListener() {
                        public final void onClick(View it) {
                            Log.d("TimeTableAdapter", "Delete: " + TimetableAdapter.this.dayOfWeek);
                            NewTimeTableViewModel var10000 = TimetableAdapter.this.newTimeTableViewModel;
                            int var10001 = TimetableAdapter.this.dayOfWeek;
                            Object var10002 = TimetableAdapter.this.subjects.get(position);
                            Intrinsics.checkNotNullExpressionValue(var10002, "subjects[position]");
                            var10000.deleteClass(var10001, (TimetableEntity)var10002);
                            dialog.dismiss();
                        }
                    }));
                    dialog.show();
                    return true;
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

    public int getItemCount() {
        return this.subjects.size();
    }

    public TimetableAdapter(@NotNull ArrayList subjects, @NotNull Context context, @NotNull NewTimeTableViewModel newTimeTableViewModel, int dayOfWeek) {
        this.subjects = subjects;
        this.context = context;
        this.newTimeTableViewModel = newTimeTableViewModel;
        this.dayOfWeek = dayOfWeek;
    }


    public static final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private TextView heading;
        private TextView time;
        private TextView state;
        private ImageView stateCircle;
        private TextView roomNumber;
        private View bottomDivider;

        public final TextView getHeading() {
            return this.heading;
        }

        public final TextView getTime() {
            return this.time;
        }

        public final void setTime(@NotNull TextView var1) {
            this.time = var1;
        }

        public final TextView getState() {
            return this.state;
        }

        public final ImageView getStateCircle() {
            return this.stateCircle;
        }
        public final TextView getRoomNumber() {
            return this.roomNumber;
        }

        public final void setRoomNumber(@NotNull TextView var1) {
            this.roomNumber = var1;
        }

        public final View getBottomDivider() {
            return this.bottomDivider;
        }

        public final void setBottomDivider(View var1) {
            this.bottomDivider = var1;
        }

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            View var10001 = itemView.findViewById(R.id.subject_title);
            this.heading = (TextView)var10001;
            var10001 = itemView.findViewById(R.id.time);
            this.time = (TextView)var10001;
            var10001 = itemView.findViewById(R.id.lec_state);
            this.state = (TextView)var10001;
            var10001 = itemView.findViewById(R.id.lec_state_circle);
            this.stateCircle = (ImageView)var10001;
            var10001 = itemView.findViewById(R.id.room_num);
            this.roomNumber = (TextView)var10001;
            this.bottomDivider = itemView.findViewById(R.id.divider_bottom);
        }
    }
}
