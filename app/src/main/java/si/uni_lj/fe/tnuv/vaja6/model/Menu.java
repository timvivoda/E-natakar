package si.uni_lj.fe.tnuv.vaja6.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Menu implements Parcelable {

    private String name;
    private float price;
    private int totalInCart;
    private String url;

    protected Menu(Parcel in) {
        name = in.readString();
        price = in.readFloat();
        totalInCart = in.readInt();
        url = in.readString();
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTotalInCart() {
        return totalInCart;
    }

    public void setTotalInCart(int totalInCart) {
        this.totalInCart = totalInCart;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeFloat(price);
        parcel.writeInt(totalInCart);
        parcel.writeString(url);
    }
}
