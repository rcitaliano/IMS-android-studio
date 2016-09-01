package it.missioneims.ims;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 01/09/2016.
 */
public class EventsAdapter extends ArrayAdapter<JSONObject>  {

    private static final String TAG_ID = "Id";
    private static final String TAG_TITLE = "Title";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_IMAGEURL = "ImageURL";
    public JSONObject[] json;
    private final Activity context;
    @LayoutRes int resource;
    public EventsAdapter(Activity context, @LayoutRes int resource, JSONObject[] json){
        super(context, resource, json);
        this.context=context;
        this.json = json;
        this.resource = resource;
    }

    public View getView(int position,View view,ViewGroup parent) {
        View rowView = null;
        try {
            JSONObject jsonObject = json[(position)];

            LayoutInflater inflater=context.getLayoutInflater();

            rowView=inflater.inflate(this.resource, null, true);

            TextView txtTitle = (TextView) rowView.findViewById(R.id.txtTitle);
            TextView txtDescription = (TextView) rowView.findViewById(R.id.txtDescription);
            TextView txtImageUrl = (TextView) rowView.findViewById(R.id.txtImageUrl);

            txtTitle.setText(jsonObject.getString(TAG_TITLE));
            txtDescription.setText(jsonObject.getString(TAG_DESCRIPTION));
            txtImageUrl.setText(jsonObject.getString(TAG_IMAGEURL));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            return rowView;
        }
    }
}
