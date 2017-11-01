package acoustically.pessenger.clientlist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import acoustically.pessenger.R;

/**
 * Created by acoustically on 17. 11. 1.
 */

public class ClientListAdapter extends BaseAdapter {

  List<ClientListItem> list = new LinkedList<>();

  @Override
  public int getCount() {
    return list.size();
  }

  @Override
  public Object getItem(int i) {
    return list.get(i);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    Context context = viewGroup.getContext();
    ClientListItem item = list.get(i);

    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    view = inflater.inflate(R.layout.client_list_layout, viewGroup, false);

    ImageView connectedImageView = view.findViewById(R.id.client_list_connected_image_view);
    TextView indexTextView = view.findViewById(R.id.client_list_index_text_view);
    TextView nameTextView = view.findViewById(R.id.client_list_name_text_view);
    if (item.getConnected() == 1) {
      connectedImageView.setImageDrawable(context.getDrawable(R.drawable.green_circle_icon));
    } else {
      connectedImageView.setImageDrawable(context.getDrawable(R.drawable.grey_circle_icon));
    }
    indexTextView.setText("test");
    nameTextView.setText(item.getName());
    indexTextView.setText(""+item.getConnected());
    return view;
  }

  public void addItem(ClientListItem item){
    list.add(item);
  }
}
