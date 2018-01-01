package tech.iosd.benefit.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

import tech.iosd.benefit.ListItems.CoachActivityOptionList;
import tech.iosd.benefit.R;

/**
 * Created by kushalgupta on 31/12/17.
 */

public class CoachActivityOptionAdapter extends RecyclerView.Adapter<CoachActivityOptionAdapter.MyViewHolder> {


    private ArrayList<String> obList;
    private LayoutInflater inflater;

    public CoachActivityOptionAdapter(Context c, ArrayList<String> obList) {
        inflater = LayoutInflater.from(c);
        this.obList=obList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_coach_activity, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String coachActivityOptionList=obList.get(position);

        holder.setData(coachActivityOptionList,position);
      //  YoYo.with(Techniques.Landing).duration(3000).playOn(holder.cardView);


    }

    @Override
    public int getItemCount() {
        return obList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
     //  private   CardView cardView;
        private int position;
        private String coachActivityOptionList;
        public MyViewHolder(final View itemView) {
            super(itemView);
//cardView=itemView.findViewById(R.id.cv);
            tv=itemView.findViewById(R.id.tv_coach_card);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "underConstruction", Toast.LENGTH_SHORT).show();
                }
            });
            

        }

        public void setData(String coachActivityOptionList, int position) {

            this.tv.setText(coachActivityOptionList);
            this.coachActivityOptionList=coachActivityOptionList;
            this.position=position;
        }
    }
}
