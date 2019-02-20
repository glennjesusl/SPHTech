package com.sample.sphtech.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sample.sphtech.R;
import com.sample.sphtech.models.MobileDataModel;

import java.util.List;

import static android.text.TextUtils.substring;

public class MobileDataAdapter extends RecyclerView.Adapter<MobileDataAdapter.ViewHolder> {

    public static final String KEY_YEAR = "year";
    public static final String KEY_DATA = "data";

    // we define a list from the MobileDataModel  java class
    private List<MobileDataModel> dataList;
    private Context context;

    public TextView tvQuarter1, tvQuarter2;

    public MobileDataAdapter(List<MobileDataModel> dataList, Context context) {

        // generate constructors to initialise the List and Context objects
        this.dataList = dataList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // this method will be called whenever our ViewHolder is created
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // this method will bind the data to the ViewHolder from whence it'll be shown to other Views

        final MobileDataModel dataModelList = dataList.get(position);

        //--do something

        holder.textViewYear.setText(substring(dataModelList.getQuarter(),0,4));
        holder.textViewConsumeData.setText(dataModelList.getVolumeData());

        if(dataModelList.getId()!="0"){
            holder.buttonViewDown.setVisibility(View.VISIBLE);
        }else{
            holder.buttonViewDown.setVisibility(View.INVISIBLE);
        }

        holder.buttonViewDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                MobileDataModel dataModelList1 = dataList.get(position);

                String first = dataModelList1.getId().split("/")[0];
                String second = dataModelList1.getId().split("/")[1];

                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setTitle("Decrease in Volume Data");
                alertbox.setIcon(R.drawable.trending_down_black);
                alertbox.setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                            }
                        });

                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogLayout = inflater.inflate(R.layout.custom_alert, null);

                tvQuarter1 = (TextView) dialogLayout.findViewById(R.id.tvQuarter1);
                tvQuarter2 = (TextView) dialogLayout.findViewById(R.id.tvQuarter2);

                tvQuarter1.setText(first);
                tvQuarter2.setText(second);

                alertbox.setView(dialogLayout);
                alertbox.show();
            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MobileDataModel dataModelList1 = dataList.get(position);
                //Toast.makeText(v.getContext(), dataModelList1.getQuarter(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override

    //return the size of the listItems (dataList)
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        // define the View objects
        public TextView textViewYear;
        public TextView textViewConsumeData;
        public Button buttonViewDown;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            // initialize the View objects
            textViewYear = (TextView) itemView.findViewById(R.id.textViewYear);
            textViewConsumeData = (TextView) itemView.findViewById(R.id.textViewConsumeData);
            buttonViewDown = (Button) itemView.findViewById(R.id.buttonViewDown);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);

        }
    }
}