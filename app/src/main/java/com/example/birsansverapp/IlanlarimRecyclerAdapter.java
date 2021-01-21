package com.example.birsansverapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IlanlarimRecyclerAdapter extends RecyclerView.Adapter<IlanlarimRecyclerAdapter.IlanlarimListeAdapter> {

    private IlanlarimOnClickListener ilanlarimOnClickListener;
    private ArrayList<String> paylasilanIlanBilgisiList;
    private ArrayList<String> paylasilanHastaneList;
    private ArrayList<String> paylasilanIlanID;

    public IlanlarimRecyclerAdapter(ArrayList<String> paylasilanIlanBilgisiList, ArrayList<String> paylasilanHastaneList, ArrayList<String> paylasilanIlanID, IlanlarimOnClickListener ilanlarimOnClickListener) {
        this.paylasilanIlanBilgisiList = paylasilanIlanBilgisiList;
        this.paylasilanHastaneList = paylasilanHastaneList;
        this.paylasilanIlanID = paylasilanIlanID;
        this.ilanlarimOnClickListener = ilanlarimOnClickListener;
    }

    @NonNull
    @Override
    public IlanlarimListeAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ilan_row_design, parent, false);
        return new IlanlarimListeAdapter(view, ilanlarimOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IlanlarimListeAdapter holder, int position) {
        holder.designHastaneBilgisi.setText(paylasilanHastaneList.get(position));
        holder.designIlanTipi.setText(paylasilanIlanBilgisiList.get(position));
    }

    @Override
    public int getItemCount() {
        return paylasilanHastaneList.size();
    }


    class IlanlarimListeAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView designIlanTipi;
        TextView designHastaneBilgisi;
        IlanlarimOnClickListener ilanlarimOnClickListener;

        public IlanlarimListeAdapter(@NonNull View itemView, IlanlarimOnClickListener ilanlarimOnClickListener) {
            super(itemView);

            designIlanTipi = itemView.findViewById(R.id.ilanTipiIlanListesiRow);
            designHastaneBilgisi = itemView.findViewById(R.id.hastaneIlanListesiRow);
            this.ilanlarimOnClickListener = ilanlarimOnClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ilanlarimOnClickListener.clickListItem(getAdapterPosition());
        }
    }

    public interface IlanlarimOnClickListener{
        void clickListItem(int position);
    }

}
