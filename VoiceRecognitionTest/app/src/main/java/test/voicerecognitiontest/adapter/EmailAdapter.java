package test.voicerecognitiontest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import test.voicerecognitiontest.R;
import test.voicerecognitiontest.helper.RecyclerViewClickListner;
import test.voicerecognitiontest.pojo.RecieveMailModel;

/**
 * Created by tanmay on 29/10/17.
 */

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.CustomViewHolder> {

    RecyclerViewClickListner clickListner;
    Context context;
    private int lastPosition = -1;
    List<RecieveMailModel> offerModelList;

    public EmailAdapter(Context context, List<RecieveMailModel> offerModelList) {
        this.context = context;
        this.offerModelList = offerModelList;
    }

    @Override
    public EmailAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_inbox, parent, false);

        return new EmailAdapter.CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EmailAdapter.CustomViewHolder holder, final int position) {
        final RecieveMailModel offerModel = offerModelList.get(holder.getAdapterPosition());
        holder.textView_subject.setText(offerModel.getSubject());
        holder.textView_from.setText(offerModel.getFrom());
        holder.textView_body.setText(offerModel.getBody());


        setAnimationBottom(holder.itemView,holder.getAdapterPosition());

        /*holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MaintenanceHistory.class);
                intent.putExtra("wing",offerModel.getResponse().getData().get(position).getWing());
                intent.putExtra("flat",offerModel.getResponse().getData().get(position).getFlat_no());
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return offerModelList.size();
    }
    public void setClickListner(RecyclerViewClickListner clickListner)
    {
        this.clickListner=clickListner;
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView textView_from, textView_body, textView_subject;
        ImageView imageView;
        public CustomViewHolder(View itemView) {
            super(itemView);
            textView_from = (TextView)itemView.findViewById(R.id.textView_from);
            textView_body = (TextView)itemView.findViewById(R.id.textView_body);
            textView_subject = (TextView)itemView.findViewById(R.id.textView_subject);

        }
    }
    public void setAnimationBottom(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.up_from_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
