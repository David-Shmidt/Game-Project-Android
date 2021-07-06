package syntax.org.il.gameproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private List<Score> scores;

    public ScoreAdapter(List<Score> scores) {
        this.scores = scores;
    }


    public class ScoreViewHolder extends RecyclerView.ViewHolder{

       TextView nameTv;
       TextView scoreTv;
       ImageView trophyIv;

       public ScoreViewHolder(@NonNull View itemView) {
           super(itemView);

           nameTv = itemView.findViewById(R.id.username);
           scoreTv = itemView.findViewById(R.id.score);
           trophyIv = itemView.findViewById(R.id.trophy);

       }
   }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_layout,parent,false);
        ScoreViewHolder scoreViewHolder = new ScoreViewHolder(view);
        return scoreViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {

        Score score = scores.get(position);
        holder.nameTv.setText(score.getName());
        holder.scoreTv.setText(score.getScore()+"");
        holder.trophyIv.setImageResource(score.getTrophyId());
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    @Override
    public int getItemViewType(int position) { //maybe will use for first place
        return super.getItemViewType(position);
    }
}

