package com.example.helloworld.citylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SimpleAdapter extends BaseAdapter {
    private List<City> itemList;
    private Context context;
    ArrayList<City> arraylist;

    public class ViewHolder {
        TextView txtTitle,txtSubTitle;
    }

    public SimpleAdapter(List<City> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
        arraylist = new ArrayList<City>();
        arraylist.addAll(itemList);
    }

    @Override
    public int getCount() {
        if(itemList!=null)
            return itemList.size();
        return 0;
    }

    @Override
    public City getItem(int position) {
        if(itemList!=null)
            return itemList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(itemList!=null)
            return itemList.get(position).hashCode();
        return 0;
    }

    public List<City> getItemList() {
        return itemList;
    }

    public void setItemList(List<City> itemList) {
        this.itemList = itemList;
        this.arraylist.addAll(itemList);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        ViewHolder viewHolder;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item, null);
            // configure view holder
            viewHolder = new ViewHolder();
            viewHolder.txtTitle = (TextView) rowView.findViewById(R.id.title);
            viewHolder.txtSubTitle = (TextView) rowView.findViewById(R.id.subtitle);
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtTitle.setText(itemList.get(position).city + "");
        viewHolder.txtSubTitle.setText(itemList.get(position).state + "");
        return rowView;


    }
    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        itemList.clear();
        if (charText.length() == 0) {
            itemList.addAll(arraylist);

        } else {
            for (City postDetail : arraylist) {
                if (charText.length() != 0 && postDetail.city.toLowerCase(Locale.getDefault()).contains(charText)) {
                    itemList.add(postDetail);
                }

            }
        }
        notifyDataSetChanged();
    }
}
