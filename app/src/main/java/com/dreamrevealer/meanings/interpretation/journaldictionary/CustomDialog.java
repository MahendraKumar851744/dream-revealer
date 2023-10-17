package com.dreamrevealer.meanings.interpretation.journaldictionary;

import static android.content.Context.MODE_PRIVATE;
import static com.dreamrevealer.meanings.interpretation.journaldictionary.Utils.AFFIRMATION;
import static com.dreamrevealer.meanings.interpretation.journaldictionary.Utils.PHYSCOLOGICAL;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.android.billingclient.BuildConfig;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Activities.Act_Affirmation;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Activities.Act_Physcological;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Activities.Act_privacy_policy;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Activities.Ask_Dream;

public class CustomDialog extends Dialog {
    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    ImageView iv_close;
    CardView cv1,cv2,cv3,cv4,cv5,cv6,cv7,cv8,cv10,cv9,cv11;
    TextView version;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_act_settings);
        iv_close = findViewById(R.id.iv_close);
        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        cv1 = findViewById(R.id.cv1);
        cv2 = findViewById(R.id.cv2);
        cv3 = findViewById(R.id.cv3);
        cv4 = findViewById(R.id.cv4);
        cv5 = findViewById(R.id.cv5);
        cv6 = findViewById(R.id.cv6);
        cv7 = findViewById(R.id.cv7);
        cv8 = findViewById(R.id.cv8);
        cv9 = findViewById(R.id.cv9);
        cv10 = findViewById(R.id.cv10);
        cv11 = findViewById(R.id.cv11);
        version = findViewById(R.id.version);
        String versionName = BuildConfig.VERSION_NAME;
        version.setText("version: "+versionName);
        sp = getContext().getSharedPreferences("BASE_APP",MODE_PRIVATE);
        editor = sp.edit();
        cv2.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Your readings are already updated!", Toast.LENGTH_SHORT).show();
        });
        cv3.setOnClickListener(view->{
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_ad_free);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ImageView iv_close = dialog.findViewById(R.id.iv_close);
            iv_close.setOnClickListener(viw->{
                dialog.dismiss();
            });
            dialog.show();
        });
        cv4.setOnClickListener(view -> {
            editor.putString("CONTENT","TODAY'S AFFIRMATION"+"!!");
            editor.putInt("NAVIGATION",AFFIRMATION);
            editor.putBoolean("SHOW_AD",true);
            editor.apply();
            new Utils(getContext()).navigateToLoading();
        });
        cv5.setOnClickListener(view -> {
            editor.putString("CONTENT","FACT OF THE DAY"+"!!");
            editor.putInt("NAVIGATION",PHYSCOLOGICAL);
            editor.putBoolean("SHOW_AD",true);
            editor.apply();
            new Utils(getContext()).navigateToLoading();
        });
        cv6.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), Ask_Dream.class);
            getContext().startActivity(i);
        });
        cv9.setOnClickListener(view -> {
            FeedBackDialog customDialog = new FeedBackDialog(getContext());
            customDialog.show();
        });
        cv10.setOnClickListener(view -> {
            intentToLink("https://sites.google.com/view/dream-revealer-privacy-policy/privacy-policy");
        });
        cv11.setOnClickListener(view -> {
            intentToLink("https://sites.google.com/view/dream-revealer-privacy-policy/terms-of-service");
        });

        cv7.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out this awesome app: https://play.google.com/store/apps/details?id=" + getContext().getPackageName());
            sendIntent.setType("text/plain");
            getContext().startActivity(sendIntent);
        });
        cv8.setOnClickListener(view -> {
            try{
                getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getContext().getPackageName())));
            }
            catch (ActivityNotFoundException e){
                getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getContext().getPackageName())));
            }
        });

        iv_close.setOnClickListener(view -> {
            dismiss();

        });

        cv1.setOnClickListener(view -> {
            intentToLink("https://sites.google.com/view/dream-revealer-privacy-policy/app-permission");
        });
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
