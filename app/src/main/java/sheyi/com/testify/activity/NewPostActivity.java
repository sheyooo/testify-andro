package sheyi.com.testify.activity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import sheyi.com.testify.R;

public class NewPostActivity extends AppCompatActivity {
    private LinearLayout etHolderLL;
    private EditText postEditText;
    private Switch anonSwitch;
    private ImageView anonymityIcon;

    private ColorStateList textColor, hintColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        setSupportActionBar((Toolbar) findViewById(R.id.new_post_toolbar));
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");

        anonSwitch = (Switch) findViewById(R.id.anonymousSwitch);
        etHolderLL = (LinearLayout) findViewById(R.id.editTextHolderLayout);
        postEditText = (EditText) findViewById(R.id.postEditText);
        anonymityIcon = (ImageView) findViewById(R.id.anonymityIcon);

        textColor = postEditText.getTextColors();
        hintColor = postEditText.getHintTextColors();

        anonSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchOnClick(v);
            }
        });
    }

    public void switchOnClick(View v) {
        if (((Switch) v).isChecked()) {
            etHolderLL.setBackgroundColor(getResources().getColor(R.color.darkAsh));
            postEditText.setTextColor(getResources().getColor(R.color.white));
            postEditText.setHintTextColor(getResources().getColor(R.color.lightAsh));
            anonymityIcon.setVisibility(View.VISIBLE);
        } else {
            etHolderLL.setBackgroundColor(getResources().getColor(R.color.white));
            postEditText.setTextColor(textColor);
            postEditText.setHintTextColor(hintColor);
            anonymityIcon.setVisibility(View.INVISIBLE);
        }
    }



}
