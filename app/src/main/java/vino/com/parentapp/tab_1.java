package vino.com.parentapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class tab_1 extends Fragment  {
    public EditText edittext;
    public Button button;
    public String strtext;
    private ProgressDialog loading;
    private TextView textViewResult;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v=inflater.inflate(R.layout.tab_1, container, false);

        //post datas
        strtext = getArguments().getString("username");
        textViewResult = (TextView) v.findViewById(R.id.tab1_textView);
        getData();
        return v;
}
    private void getData() {
        if (strtext.equals("")) {
            Toast.makeText(getActivity(), "Invalid User Please Logout and Login Again", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching...", false, false);
        String url = Config.DATA_URL_STUDENTDETAILS+strtext.trim();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){

        String regno = "";
        String name="";
        String yop="";
        String course_name = "";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            regno = collegeData.getString(Config.REGNO);
            name = collegeData.getString(Config.NAME);
            yop = collegeData.getString(Config.YOP);
            course_name = collegeData.getString(Config.COURSE_NAME);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        textViewResult.setText("Student Name : \t \t \t " + name + "\n\n" +
                "University Register Number:\t \t" + regno + "\n\n" +
                "Course Name :\t \t \t " + course_name + "\n\n" +
                "Year Of Passing : \t \t " + yop + "\n\n");
    }
}
