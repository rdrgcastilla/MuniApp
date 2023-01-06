package pe.mpc.muni.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pe.mpc.muni.Model.Evento;
import pe.mpc.muni.ModuloEventos.EventoShowActivity;
import pe.mpc.muni.R;

public class AdapterEvento extends RecyclerView.Adapter<AdapterEvento.MyViewHolder> {

    List<Evento> eventos;
    private Context context;
    private RecyclerViewClickListener mListener;

    public AdapterEvento(List<Evento> eventos, Context context, RecyclerViewClickListener listener) {
        this.eventos = eventos;
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_evento, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.direccion.setText(eventos.get(position).getLugar().toString());
        holder.hora.setText(eventos.get(position).getHora().toString());
        holder.name.setText(eventos.get(position).getName().toString());
        String imgenUrl = context.getResources().getString(R.string.url_eventos)+eventos.get(position).getImagen();
        try {
            Picasso.with(context).load(imgenUrl).into(holder.imageView);
            holder.imageView.setBackground(null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        final String eventID = eventos.get(position).getId();
        holder.flecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, EventoShowActivity.class);
                intent.putExtra("eventID",eventID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        int longitud=eventos.size();
        if (eventos.size()>=50){
            longitud=50;
        }
        return longitud;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;
        private ImageView imageView;
        private TextView direccion, name,hora;
        private ImageButton flecha;
        private RelativeLayout mRowContainer;

        public MyViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.evento_imagen_item);
            direccion = itemView.findViewById(R.id.direccion_evento_item);
            hora = itemView.findViewById(R.id.hora_evento_item);
            name = itemView.findViewById(R.id.name_evento_item);
            flecha = itemView.findViewById(R.id.flecha_evento_item);
            mRowContainer = itemView.findViewById(R.id.evento_item);

            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.row_container:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

}

