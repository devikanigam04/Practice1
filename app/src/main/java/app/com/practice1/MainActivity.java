package app.com.practice1;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fName)
    EditText fName;
    @BindView(R.id.lName)
    EditText lName;
    @BindView(R.id.spJobProf)
    Spinner spJobProf;
    @BindView(R.id.spExperience)
    Spinner spExperience;
    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.lvInfo)
    ListView lvInfo;

    ListAdapter adapter;
    List<Pojo> pojos = new ArrayList<>();
    String jobprof, expnce, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setadapter();

        String[] job = {"Mobile Developer","Software Developer","Web Developer"};
        String[] exp = {"0-1 yr","1-2 yr","2-3 yr"};

        rgGender.clearCheck();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,job);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJobProf.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,exp);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spExperience.setAdapter(adapter2);

        spJobProf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jobprof = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                jobprof = (String) adapterView.getItemAtPosition(0);
            }
        });
        spExperience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                expnce = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                expnce = (String) adapterView.getItemAtPosition(0);
            }
        });

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.male:
                        gender = "Male";
                        break;
                    case R.id.female:
                        gender = "Female";
                        break;
                }
            }
        });

    }

    private void setadapter() {
        if (adapter == null) {
            if (pojos.size() > 0) {
                adapter = new ListAdapter(this, pojos);
                lvInfo.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.btnAdd)
    void addToList(){
        String fname = fName.getText().toString();
        String lname = lName.getText().toString();
        if (rgGender.getCheckedRadioButtonId() != 0 && !fname.isEmpty() && !lname.isEmpty()){
            Pojo pojo = new Pojo(fname,lname,jobprof,expnce,gender);
            pojos.add(pojo);
            setadapter();
            clear();
        }
    }

    void clear(){
        fName.setText("");
        lName.setText("");
        rgGender.clearCheck();
        spJobProf.setSelection(0);
        spExperience.setSelection(0);
    }

    private class Pojo{
        private String fname;
        private String lname;
        private String jobProf;
        private String exp_range;
        private String gender;

        Pojo(String fname, String lname, String jobProf, String exp_range, String gender) {
            this.fname = fname;
            this.lname = lname;
            this.jobProf = jobProf;
            this.exp_range = exp_range;
            this.gender = gender;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getJobProf() {
            return jobProf;
        }

        public String getExp_range() {
            return exp_range;
        }

        public String getGender() {
            return gender;
        }
    }

    private class ListAdapter extends BaseAdapter{

        private List<Pojo> pojoList;

        ListAdapter(Context context, List<Pojo> pojoList) {
            this.pojoList = pojoList;
        }

        @Override
        public int getCount() {
            return pojoList.size();
        }

        @Override
        public Object getItem(int i) {
            return pojoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            Holder holder;
            if (view == null) {
                view = inflater.inflate(R.layout.list_item, viewGroup, false);
                holder = new Holder(view);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            holder.tvfName.setText(pojoList.get(i).getFname());
            holder.tvlName.setText(pojoList.get(i).getLname());
            holder.tvjobprof.setText(pojoList.get(i).getJobProf());
            holder.tvexp.setText(pojoList.get(i).getExp_range());
            holder.tvgender.setText(pojoList.get(i).getGender());

            return view;
        }
    }

    class Holder{

        @BindView(R.id.tvfName)
        TextView tvfName;
        @BindView(R.id.tvlName)
        TextView tvlName;
        @BindView(R.id.tvjobprof)
        TextView tvjobprof;
        @BindView(R.id.tvexp)
        TextView tvexp;
        @BindView(R.id.tvgender)
        TextView tvgender;


        public Holder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
