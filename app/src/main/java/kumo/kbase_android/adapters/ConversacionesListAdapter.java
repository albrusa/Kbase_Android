package kumo.kbase_android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import kumo.kbase_android.R;
import kumo.kbase_android.httpRequest.HttpCola;
import kumo.kbase_android.httpRequest.LruBitmapCache;
import kumo.kbase_android.model.Conversacion;
import kumo.kbase_android.utils.Constantes;

/**
 * Created by dev_2 on 09/11/2015.
 */
public class ConversacionesListAdapter extends RecyclerView.Adapter<ConversacionesListAdapter.AdapterElementoViewHolder> implements View.OnClickListener{
    private List<Conversacion> l_conversaciones;
    private View.OnClickListener listener;

    public ConversacionesListAdapter(List<Conversacion> _l_conversaciones) {
        this.l_conversaciones = _l_conversaciones;
    }

    public static class AdapterElementoViewHolder
            extends RecyclerView.ViewHolder {

        private TextView mMensaje;
        private TextView mNombre_Usuario;
        RequestQueue mRequestQueue;
        NetworkImageView mImagen;
        ImageLoader mImageLoader;


        public AdapterElementoViewHolder(View itemView) {
            super(itemView);

            mMensaje = (TextView)itemView.findViewById(R.id.Mensaje);
            mNombre_Usuario = (TextView)itemView.findViewById(R.id.Nombre_Usuario);
            mImagen = (NetworkImageView) itemView.findViewById(R.id.Imagen);

        }

        public void bindTitular(Conversacion t) {
            mNombre_Usuario.setText(t.Nombre);
            mMensaje.setText(t.Mensaje);

            ImageLoader.ImageCache imageCache = new LruBitmapCache(itemView.getContext());

            mImageLoader = HttpCola.getInstance(itemView.getContext()).getImageLoader();

            if(t.Imagen != null && t.Imagen != "")
            {
                mImagen.setImageUrl(Constantes.HTTP_KMED_SERVER+t.Imagen, mImageLoader);
            }
        }
    }

    @Override
    public AdapterElementoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.conversaciones_list_item, viewGroup, false);

        AdapterElementoViewHolder tvh = new AdapterElementoViewHolder(itemView);

        itemView.setOnClickListener(this);

        return tvh;
    }

    @Override
    public void onBindViewHolder(AdapterElementoViewHolder viewHolder, int pos) {
        Conversacion item = l_conversaciones.get(pos);

        viewHolder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return l_conversaciones.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

}
