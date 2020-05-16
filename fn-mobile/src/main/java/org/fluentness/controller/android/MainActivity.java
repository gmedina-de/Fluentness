package org.fluentness.controller.android;

import android.content.pm.PackageManager;
import android.os.Bundle;
import org.fluentness.Application;
import org.fluentness.Fluentness;
import org.fluentness.FluentnessException;
import org.fluentness.controller.AbstractMobile;
import org.fluentness.controller.AbstractMobileController;
import org.fluentness.service.log.Log;

import java.util.List;

public class MainActivity extends android.app.Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            String applicationClassName = getPackageManager().getActivityInfo(getComponentName(), 0).nonLocalizedLabel.toString();
            Class<? extends Application> applicationClass = (Class<? extends Application>) Class.forName(applicationClassName);
            Application application = applicationClass.newInstance();
            Fluentness.launch(application);

            AbstractMobile.context = this;
            // todo manage more controllers and views
            List<AbstractMobileController> instances = Fluentness.getInstances(AbstractMobileController.class);
            AndroidView androidView = (AndroidView) instances.get(0).getMobile().render();
            setContentView(androidView.getView());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | FluentnessException | PackageManager.NameNotFoundException e) {
            Log log = Fluentness.getInstance(Log.class);
            if (log != null) {
                log.error(e);
            } else {
                android.util.Log.e(this.getClass().getSimpleName(), "Exception", e);
            }
        }
    }
}
