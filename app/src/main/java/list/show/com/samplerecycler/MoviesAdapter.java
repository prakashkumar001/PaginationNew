package list.show.com.samplerecycler;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sab99r
 */
public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {




    List<Product> product;
    Context ctx;
    String listformat;

    Typeface fonts,bold;
    int count=0;


    /*
    * isLoading - to set the remote loading and complete status to fix back to back load more call
    * isMoreDataAvailable - to set whether more data from server available or not.
    * It will prevent useless load more request even after all the server data loaded
    * */


    public MoviesAdapter(Context context, List<Product> movies, String listformat) {
        this.ctx = context;
        this.product = movies;
        this.listformat=listformat;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());



        viewHolder = getViewHolder(parent, inflater);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieVH movieVH=(MovieVH)holder;
        //String seller = ctx.getResources().getString(R.string.Rupees);
        //String offer = ctx.getResources().getString(R.string.seventyrupees);


        movieVH.sellerprice.setText(product.get(position).getSellerprice());
        //offerprice.setText(seller + product.get(position).getOfferprice());
        if(product.get(position).getSellerprice().equals("0"))
        {
            movieVH.outofstock.setText("Out of Stock");
            movieVH.sellerprice.setVisibility(View.GONE);
            movieVH.add.setVisibility(View.GONE);

        }else
        {
            movieVH.outofstock.setVisibility(View.GONE);

        }
        // sellerprice.setPaintFlags(sellerprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        movieVH.productname.setText(product.get(position).getProductname());

        movieVH.sellerprice.setTypeface(fonts);
        //offerprice.setTypeface(fonts);
        movieVH.productname.setTypeface(bold);
        movieVH.add.setTypeface(bold);










    }


    @Override
    public int getItemCount() {
        return product.size();
    }

   






    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder=null;;



        View itemView=null;
        if(listformat.equalsIgnoreCase("list"))
        {
            itemView = inflater.inflate(R.layout.product_list_item, parent, false);
            viewHolder = new MovieVH(itemView);

        }else if(listformat.equalsIgnoreCase("grid"))
        {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid, parent, false);
            viewHolder = new MovieVH(itemView);

        }
        return viewHolder;
    }
    protected class MovieVH extends RecyclerView.ViewHolder {
        public TextView offerprice,productname,sellerprice,outofstock;
        public ImageView image;
        public TextView add;

        public MovieVH(View view) {
            super(view);

            sellerprice = (TextView) view.findViewById(R.id.sellerprice);
            offerprice = (TextView) view.findViewById(R.id.offerprice);
            image = (ImageView) view.findViewById(R.id.image);
            add=(TextView)view.findViewById(R.id.addtocart);
            productname= (TextView) view.findViewById(R.id.product);
            outofstock= (TextView) view.findViewById(R.id.outofstock);
        }



        }
    }


    



