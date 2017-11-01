package acoustically.pessenger;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import acoustically.pessenger.clientlist.ClientListFragment;
import acoustically.pessenger.tools.Environment;

public class MainActivity extends Activity implements ClientListFragment.OnFragmentInteractionListener{

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Environment.setPermission(this, android.Manifest.permission.RECEIVE_SMS);
    setContentView(R.layout.activity_main);
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.add(R.id.main_activity_frame_layout, new ClientListFragment());
    transaction.commit();

    startService(new Intent(this, ReceiveSmsService.class));
  }

  @Override
  public void onFragmentInteraction(Uri uri) {

  }
}
