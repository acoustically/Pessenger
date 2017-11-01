package acoustically.pessenger.tools;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by acoustically on 17. 10. 26.
 */

public class ActivityNavigator {
  private Intent intent;
  private Activity activity;

  public ActivityNavigator(Activity activity, Class class_) {
    this.activity = activity;
    intent = new Intent(activity, class_);
  }

  public void setExtra(String key, String value) {
    intent.putExtra(key, value);
  }

  public void navigate() {
    activity.startActivity(intent);
    activity.finish();
  }
}
