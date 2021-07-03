package syntax.org.il.gameproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class ScoreAdapter extends BaseAdapter {

    private List<Score> ListOfScore;
    private Context context;

    public ScoreAdapter(List<Score> listOfScore, Context context) {
        ListOfScore = listOfScore;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ListOfScore.size();
    }

    @Override
    public Object getItem(int position) {
        return ListOfScore.get(position);
    }

    @Override //need to look into it its the id of the list
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // if (convertView == null)
        //{
      //      convertView =
      //  }
      return null;
    }
}

