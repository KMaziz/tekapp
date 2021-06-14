package com.college.collegeconnect.utils;

import android.content.Context;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.Builder;
import java.util.concurrent.Executors;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 4, 2},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"},
        d2 = {"Lcom/college/collegeconnect/utils/ImageHandler;", "", "()V", "instance", "Lcom/squareup/picasso/Picasso;", "getSharedInstance", "context", "Landroid/content/Context;", "CollegeConnect.app"}
)
public final class ImageHandler {
    private Picasso instance;

    @Nullable
    public final Picasso getSharedInstance(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Picasso var10000;
        if (this.instance == null) {
            this.instance = (new Builder(context)).executor(Executors.newSingleThreadExecutor()).build();
            var10000 = this.instance;
        } else {
            var10000 = this.instance;
        }

        return var10000;
    }
}
