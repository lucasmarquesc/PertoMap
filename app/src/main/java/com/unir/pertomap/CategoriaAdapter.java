package com.unir.pertomap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {

    // Interface para clique
    public interface OnCategoriaClickListener {
        void onCategoriaClick(Categoria categoria);
    }

    private List<Categoria> lista;
    private OnCategoriaClickListener listener;

    public CategoriaAdapter(List<Categoria> lista, OnCategoriaClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categoria, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Categoria categoria = lista.get(position);

        holder.txtNome.setText(categoria.nome);
        holder.imgIcon.setImageResource(categoria.icon);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoriaClick(categoria);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    // ===================== VIEW HOLDER =====================
    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgIcon;
        TextView txtNome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtNome = itemView.findViewById(R.id.txtNome);
        }
    }
}
