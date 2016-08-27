package it.missioneims.ims;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] item_name;
    private final String[] item_description;
    private final Integer[] imgid;
    @LayoutRes int resource;

    public CustomListAdapter(Activity context, @LayoutRes int resource, String[] item_name, String[] item_description, Integer[] imgid) {
        super(context, resource, item_name);
        // TODO Auto-generated constructor stub
        this.resource = resource;
        this.context=context;
        this.item_name=item_name;
        this.item_description = item_description;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(this.resource, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.member_name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.member_image);
        TextView txtDescription = (TextView) rowView.findViewById(R.id.member_role);

        txtTitle.setText(this.item_name[position]);
        imageView.setImageResource(imgid[position]);
        txtDescription.setText(this.item_description[position]);
        return rowView;

    };
}