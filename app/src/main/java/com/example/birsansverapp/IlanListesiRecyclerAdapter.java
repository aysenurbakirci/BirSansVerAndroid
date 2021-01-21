package com.example.birsansverapp;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IlanListesiRecyclerAdapter extends RecyclerView.Adapter<IlanListesiRecyclerAdapter.IlanListesiAdapter> {

    private ArrayList<String> paylasanKisiTelefonList;
    private ArrayList<String> paylasanKisiAdSoyadList;
    private ArrayList<String> paylasilanIlList;
    private ArrayList<String> paylasilanHastaneList;
    private ArrayList<String> paylasilanIlanTipiList;
    private ArrayList<String> paylasilanIlanBilgisiList;
    private IlanListesiOnClickListener ilanListesiOnClickListener;

    public IlanListesiRecyclerAdapter(ArrayList<String> paylasanKisiTelefonList, ArrayList<String> paylasanKisiAdSoyadList, ArrayList<String> paylasilanIlList, ArrayList<String> paylasilanHastaneList, ArrayList<String> paylasilanIlanTipiList, ArrayList<String> paylasilanIlanBilgisiList, IlanListesiOnClickListener ilanListesiOnClickListener) {
        this.paylasanKisiTelefonList = paylasanKisiTelefonList;
        this.paylasanKisiAdSoyadList = paylasanKisiAdSoyadList;
        this.paylasilanIlList = paylasilanIlList;
        this.paylasilanHastaneList = paylasilanHastaneList;
        this.paylasilanIlanTipiList = paylasilanIlanTipiList;
        this.paylasilanIlanBilgisiList = paylasilanIlanBilgisiList;
        this.ilanListesiOnClickListener = ilanListesiOnClickListener;
    }

    @NonNull
    @Override
    public IlanListesiAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ilan_row_design, parent, false);
        return new IlanListesiAdapter(view, ilanListesiOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IlanListesiAdapter holder, int position) {

        holder.designHastaneBilgisi.setText(paylasilanHastaneList.get(position));
        holder.designIlanTipi.setText(paylasilanIlanBilgisiList.get(position));
    }

    @Override
    public int getItemCount() {

        return paylasanKisiAdSoyadList.size();
    }

    class IlanListesiAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView designIlanTipi;
        TextView designHastaneBilgisi;
        IlanListesiOnClickListener ilanListesiOnClickListener;

        public IlanListesiAdapter(@NonNull View itemView, IlanListesiOnClickListener ilanListesiOnClickListener) {
            super(itemView);

            designIlanTipi = itemView.findViewById(R.id.ilanTipiIlanListesiRow);
            designHastaneBilgisi = itemView.findViewById(R.id.hastaneIlanListesiRow);
            itemView.setOnClickListener(this);
            this.ilanListesiOnClickListener = ilanListesiOnClickListener;

        }

        @Override
        public void onClick(View v) {
            ilanListesiOnClickListener.clickListItem(getAdapterPosition());
        }
    }

    public interface IlanListesiOnClickListener{
        void clickListItem(int position);
    }
}
