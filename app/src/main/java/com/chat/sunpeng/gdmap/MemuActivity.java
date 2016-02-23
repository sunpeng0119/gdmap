package com.chat.sunpeng.gdmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MemuActivity extends Activity implements View.OnClickListener {
    Button one;
    Button more;
    Button edit;
    Button set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memu);
        one=(Button)findViewById(R.id.one);
        one.setOnClickListener(this);
        more=(Button)findViewById(R.id.more);
        more.setOnClickListener(this);
        set=(Button)findViewById(R.id.set_gps);
        set.setOnClickListener(this);
        edit=(Button)findViewById(R.id.edit);
        edit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(MemuActivity.this,ImageActivity.class);;
        switch (v.getId()){
            case R.id.one:
                intent.putExtra("TYPE","ONE");
                startActivity(intent);
                break;
            case R.id.more:
                intent.putExtra("TYPE","MORE");
                startActivity(intent);
                break;
            case R.id.set_gps:break;
            case R.id.edit:break;


        }

    }
}
