package tech.iosd.benefit;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private FloatingActionButton fab;
    private LinearLayout chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar)findViewById(R.id.chat_toolbar);
        toolbar.setNavigationIcon(android.R.mipmap.sym_def_app_icon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chats = (LinearLayout)findViewById(R.id.chats);
        fab = (FloatingActionButton)findViewById(R.id.fab_send);
        fab.setOnClickListener(this);
        editText = (EditText)findViewById(R.id.message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.home){
            //Navigation Handle
        }else {
            Toast.makeText(ChatActivity.this, "Not Working Yet!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v == fab){
            if(editText.getText().toString().equals("")){
                editText.setError("Empty Message");
            }else {
                send_message();
            }
        }
    }

    private void send_message() {
        TextView textview = new TextView(getApplicationContext());
        textview.setText(editText.getText().toString());
        textview.setTextSize(16);
        textview.setTextColor(Color.BLACK);
        textview.setMaxWidth(500);
        textview.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
        textview.setElevation(5);
        textview.setBackground(getDrawable(R.drawable.sent));

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.gravity = Gravity.RIGHT;
        param.setMargins(5,10,5,0);
        textview.setLayoutParams(param);


        try {
            chats.addView(textview);
            editText.setText("");
            editText.clearFocus();
            textview.requestFocus();
            textview.setFocusableInTouchMode(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
