package com.college.collegeconnect.dagger;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder;
import dagger.Module;
import dagger.Provides;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Module
public final class FirebaseModule {
    @Provides
    @NotNull
    public final FirebaseRemoteConfig provideFirebaseRemoteConfig() {
        return  FirebaseRemoteConfig.getInstance();
    }

    @Provides
    @NotNull
    public final FirebaseRemoteConfigSettings provideFirebaseRemoteConfigSettings() {
        return  (new Builder()).setMinimumFetchIntervalInSeconds(10L).build();

    }
}
