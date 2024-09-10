package com.aitzz.evfinalu1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.NotaViewHolder> {

private ArrayList<NotaModel> listaNotas;

public NotaAdapter(ArrayList<NotaModel> listaNotas) {
    this.listaNotas = listaNotas;
}

@NonNull
@Override
public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota, parent, false);
    return new NotaViewHolder(view);
}

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
        NotaModel nota = listaNotas.get(position);
        holder.tituloNota.setText(nota.getTitulo());
        holder.descripcionNota.setText(nota.getDescripcion());
        holder.fechaHoraNota.setText(nota.getFecha() + " " + nota.getHora());
//        holder.itemView.setBackgroundColor(nota.getColor());
    }

@Override
public int getItemCount() {
    return listaNotas.size();
}

static class NotaViewHolder extends RecyclerView.ViewHolder {
    TextView tituloNota;
    TextView descripcionNota;
    TextView fechaHoraNota;

    NotaViewHolder(@NonNull View itemView) {
        super(itemView);
        tituloNota = itemView.findViewById(R.id.tituloNota);
        descripcionNota = itemView.findViewById(R.id.descripcionNota);
        fechaHoraNota = itemView.findViewById(R.id.fechaHoraNota);
    }
}
}