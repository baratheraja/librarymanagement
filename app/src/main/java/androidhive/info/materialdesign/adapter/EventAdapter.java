package androidhive.info.materialdesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.dbconnection.DbOperation;
import androidhive.info.materialdesign.model.Event;

/**
 * Created by baratheraja on 13/8/15.
 */
public class EventAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private static List<Event> eventlists;
    public EventAdapter(Context eventFragment, List<Event> eventlist) {
        mInflater = LayoutInflater.from(eventFragment);
        eventlists = eventlist;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return eventlists.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return eventlists.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.event_item, null);
            holder = new ViewHolder();
            holder.txtname = (TextView) convertView.findViewById(R.id.name);
            holder.txtdate = (TextView) convertView.findViewById(R.id.date);
            holder.ckbox = (CheckBox) convertView.findViewById(R.id.checkBox1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtname.setText(eventlists.get(position).getTitle());
        holder.txtdate.setText(eventlists.get(position).getDate());
        holder.ckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DbOperation.events.get(position).setChecked(true);
                }
                else DbOperation.events.get(position).setChecked(true);
            }
        });
        return convertView;
    }

    static class ViewHolder{
        TextView txtname, txtdate;
        CheckBox ckbox;
    }

}
