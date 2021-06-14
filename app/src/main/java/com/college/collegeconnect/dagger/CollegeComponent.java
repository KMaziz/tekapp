package com.college.collegeconnect.dagger;

import com.college.collegeconnect.activities.Navigation;
import dagger.Component;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Component(
        modules = {FirebaseModule.class}
)

public interface CollegeComponent {
    void injectNavigation( Navigation var1);
}
