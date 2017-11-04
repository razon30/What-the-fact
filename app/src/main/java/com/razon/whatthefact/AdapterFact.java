package com.razon.whatthefact;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 03-Nov-17.
 */

public class AdapterFact extends RecyclerView.Adapter<AdapterFact.MyViewHolder> {

    Context context;
    ArrayList<Response> responseList;
    LayoutInflater inflater;

    public AdapterFact(Context context, ArrayList<Response> responseList) {
        this.context = context;
        this.responseList = responseList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_fact, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Response response = responseList.get(position);
        if (response.isFound()){

            holder.type.setText(response.getType());
            holder.text.setText(response.getText());

        }else {

            holder.type.setText(response.getType());
            holder.text.setText("This number is very much boring for "+response.getType());

        }
        int i = position+1;
      if (i%4==0){
          holder.itemBg.setBackground(context.getResources().getDrawable(R.drawable.pic_one));
      }else  if (i%3==0){
          holder.itemBg.setBackground(context.getResources().getDrawable(R.drawable.pic_five));
      }else  if (i%2==0){
          holder.itemBg.setBackground(context.getResources().getDrawable(R.drawable.pic_three));
      }else {
          holder.itemBg.setBackground(context.getResources().getDrawable(R.drawable.pic_six));
      }


    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView type;
        public TextView text;
        public LinearLayout itemBg;

        public MyViewHolder(View rootView) {
            super(rootView);
            this.type = (TextView) rootView.findViewById(R.id.type);
            this.text = (TextView) rootView.findViewById(R.id.text);
            this.itemBg = (LinearLayout) rootView.findViewById(R.id.item_bg);
        }
    }

}
