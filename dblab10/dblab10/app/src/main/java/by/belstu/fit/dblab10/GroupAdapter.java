package by.belstu.fit.dblab10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class GroupAdapter extends ArrayAdapter<Group> {
    private LayoutInflater inflater;
    private int layout;
    private List<Group> groups;

    public GroupAdapter(Context context, int resource, List<Group> list) {
        super(context, resource, list);
        this.groups = list;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView facView = (TextView) view.findViewById(R.id.text1);
        TextView groupidView = (TextView) view.findViewById(R.id.text2);
        TextView headView = (TextView) view.findViewById(R.id.text3);
        TextView nameView = (TextView) view.findViewById(R.id.text4);

        Group state = groups.get(position);

        nameView.setText(state.NAME);
        headView.setText("Староста:"+state.HEAD);
        facView.setText("Факультет:"+state.FACULTY+" Курс:"+state.COURSE);
        groupidView.setText(" ID группы:"+state.IDGROUP);
        return view;
    }
}
