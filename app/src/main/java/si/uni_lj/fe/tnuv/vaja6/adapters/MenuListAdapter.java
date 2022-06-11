package si.uni_lj.fe.tnuv.vaja6.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import si.uni_lj.fe.tnuv.vaja6.R;
import si.uni_lj.fe.tnuv.vaja6.model.Menu;
import si.uni_lj.fe.tnuv.vaja6.model.RestaurantModel;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {

    private List<Menu> menuList;
    private MenuListClickListener clickListener;

    public MenuListAdapter(List<Menu> menuList, MenuListClickListener clickListener){
        this.menuList = menuList;
        this.clickListener = clickListener;

    }
    public void updateData(List<Menu> menuList){
        this.menuList = menuList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MenuListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuListAdapter.MyViewHolder holder, int position) {
        holder.menuName.setText(menuList.get(position).getName());
        holder.menuPrice.setText("Cena: €"+menuList.get(position).getPrice());
        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onAddToCartClick(menuList.get(position));
            }
        });

        Glide.with(holder.thumbImage)
                .load(menuList.get(position).getUrl())
                .into(holder.thumbImage);



    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView menuName;
        TextView menuPrice;
        TextView addToCartButton;
        ImageView thumbImage;

        public MyViewHolder(View view) {
            super(view);

            menuName = view.findViewById(R.id.menuName);
            menuPrice = view.findViewById(R.id.menuPrice);
            addToCartButton = view.findViewById(R.id.addToCartButton);
            thumbImage = view.findViewById(R.id.thumbImage);

        }
    }
    public interface MenuListClickListener{
        public void onAddToCartClick(Menu menu);
    }
}
