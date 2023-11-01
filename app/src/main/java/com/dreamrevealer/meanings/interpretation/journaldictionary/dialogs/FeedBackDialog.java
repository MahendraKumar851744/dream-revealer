package com.dreamrevealer.meanings.interpretation.journaldictionary.dialogs;

import static com.dreamrevealer.meanings.interpretation.journaldictionary.util.Constant.BASE_URL;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dreamrevealer.meanings.interpretation.journaldictionary.R;

import java.util.HashMap;
import java.util.Map;

public class FeedBackDialog extends Dialog {

    public FeedBackDialog(@NonNull Context context) {
        super(context);
    }
    EditText email,feedback;
    TextView pp;
    ImageView iv_close;
    CardView submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.feedback);
        iv_close = findViewById(R.id.iv_close);
        feedback = findViewById(R.id.feedback);
        submit = findViewById(R.id.submit);
        email = findViewById(R.id.email);
        pp = findViewById(R.id.pp);
        iv_close.setOnClickListener(view -> {
            dismiss();

        });
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pp.setOnClickListener(view -> {
            intentToLink("https://sites.google.com/view/dream-revealer-privacy-policy/privacy-policy");
        });
        submit.setOnClickListener(view -> {
            if(email.getText().toString().isEmpty() || feedback.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Please complete the fields!", Toast.LENGTH_SHORT).show();
            }else{
                sendfeedback(getContext(),email.getText().toString(),feedback.getText().toString());
                dismiss();
                Toast.makeText(getContext(), "Feedback Sent SuccessFully!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void sendfeedback(Context context,String email,String feednback){
        String url = BASE_URL+"/feedback.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> jsonBody = new HashMap<String, String>();
                jsonBody.put("email", email);
                jsonBody.put("feedback", feednback);
                return jsonBody;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);
    }
    private void intentToLink(String url){
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            getContext().startActivity(i);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

    }
}
