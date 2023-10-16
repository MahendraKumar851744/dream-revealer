package com.dreamrevealer.meanings.interpretation.journaldictionary;

import static com.dreamrevealer.meanings.interpretation.journaldictionary.Constant.BASE_URL;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RatingDialog extends Dialog implements View.OnClickListener {
    public RatingDialog(@NonNull Context context) {
        super(context);
    }

    ImageView iv_close;

    EditText email,feedback;
    TextView lay1,later;
    LinearLayout lay2,lay3;
    ImageView star1,star2,star3,star4,star5;
    CardView ok,submit;
    static int playstore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rating_dialog);
        iv_close = findViewById(R.id.iv_close);
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);
        lay1 = findViewById(R.id.lay1);
        lay2 = findViewById(R.id.lay2);
        lay3 = findViewById(R.id.lay3);
        ok = findViewById(R.id.ok);
        later = findViewById(R.id.later);
        feedback = findViewById(R.id.feedback);
        submit = findViewById(R.id.submit);
        email = findViewById(R.id.email);

        later.setOnClickListener(view -> {
            dismiss();
        });

        iv_close.setOnClickListener(view -> {
            dismiss();

        });

        ok.setOnClickListener(view -> {
            if(playstore==0){
                lay2.setVisibility(View.GONE);
                lay3.setVisibility(View.VISIBLE);
            }else{
                try{
                    getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getContext().getPackageName())));
                }
                catch (ActivityNotFoundException e){
                    getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getContext().getPackageName())));
                }
            }
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


        star1.setOnClickListener(this);
        star2.setOnClickListener(this);
        star3.setOnClickListener(this);
        star4.setOnClickListener(this);
        star5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.star1){
            playstore = 0;
            lay1.setText(R.string.please_share_your_suggestions_with_us_to_improve_the_app);
            lay1.setVisibility(View.VISIBLE);
            lay2.setVisibility(View.VISIBLE);
            lay3.setVisibility(View.GONE);
            star1.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            star2.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray), android.graphics.PorterDuff.Mode.SRC_IN);
            star3.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray), android.graphics.PorterDuff.Mode.SRC_IN);
            star4.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray), android.graphics.PorterDuff.Mode.SRC_IN);
            star5.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray), android.graphics.PorterDuff.Mode.SRC_IN);
        }else if(id==R.id.star2){
            playstore = 0;
            lay1.setText(R.string.please_share_your_suggestions_with_us_to_improve_the_app);
            lay1.setVisibility(View.VISIBLE);
            lay2.setVisibility(View.VISIBLE);
            lay3.setVisibility(View.GONE);
            star1.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            star2.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            star3.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray), android.graphics.PorterDuff.Mode.SRC_IN);
            star4.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray), android.graphics.PorterDuff.Mode.SRC_IN);
            star5.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray), android.graphics.PorterDuff.Mode.SRC_IN);
        }else if(id==R.id.star3){
            playstore = 0;
            lay1.setText(R.string.please_share_your_suggestions_with_us_to_improve_the_app);
            lay1.setVisibility(View.VISIBLE);
            lay2.setVisibility(View.VISIBLE);
            lay3.setVisibility(View.GONE);
            star1.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            star2.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            star3.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            star4.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray), android.graphics.PorterDuff.Mode.SRC_IN);
            star5.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray), android.graphics.PorterDuff.Mode.SRC_IN);
        }else if(id==R.id.star4){
            playstore = 0;
            lay1.setText(R.string.please_share_your_suggestions_with_us_to_improve_the_app);
            lay1.setVisibility(View.VISIBLE);
            lay2.setVisibility(View.VISIBLE);
            lay3.setVisibility(View.GONE);
            star1.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            star2.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            star3.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            star4.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            star5.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray), android.graphics.PorterDuff.Mode.SRC_IN);
        }else if(id==R.id.star5){
            playstore = 1;
            lay1.setText(R.string.thanks);
            lay1.setVisibility(View.VISIBLE);
            lay2.setVisibility(View.VISIBLE);
            lay3.setVisibility(View.GONE);
            star1.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            star2.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            star3.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            star4.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            star5.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
        }
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
}
