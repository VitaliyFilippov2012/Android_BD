package by.belstu.fit.dblab10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    private LayoutInflater inflater;
    private int layout;
    private List<Student> students;

    public StudentAdapter(Context context, int resource, List<Student> list) {
        super(context, resource, list);
        this.students = list;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView idView = (TextView) view.findViewById(R.id.text1);
        TextView groupidView = (TextView) view.findViewById(R.id.text2);
        TextView nameView = (TextView) view.findViewById(R.id.text3);

        Student state = students.get(position);

        nameView.setText(state.NAME);
        idView.setText("ID студента:"+state.IDSTUDENT);
        groupidView.setText(" ID группы:"+state.IDGROUP);
        return view;
    }
}
