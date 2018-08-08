package com.a.election_eec_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class WinnersActivity extends AppCompatActivity {


    SharedPreferences mPrefs ;

    private TextView tv_totalvotescount, tv_presidentname, tv_presidentwincount;
    private TextView tv_vicepresidentname, tv_vicepresidentwincount;
    private TextView tv_treasurername, tv_treasurerwincount;
    private TextView tv_secretaryname, tv_secretarywincount;
    private TextView tv_jointsecretaryname, tv_jointsecretarywincount;

    private ImageView president, vicepresident, treasurer, secretary, jointsecretary;
    private int tempp1, tempp2, tempvp1, tempvp2, tempt1, tempt2, temps1, temps2, tempjs1, tempjs2, totalvotes;
    int dummy = 0;


  //  HashMap<String, Person> database;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winners);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // Intent intent = getIntent();
        //database = (HashMap<String, Person>) intent.getSerializableExtra(getString(R.string.hashmapname));


        init_variables();

        mPrefs = getSharedPreferences(getString(R.string.dbname),MODE_PRIVATE);

        //read data from hashmap
        read_data();

        computewinners();
    }

    private void read_data() {

        totalvotes = mPrefs.getAll().size();


        //sharedpref get All method
        Map<String, ?> allEntries = mPrefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

            //********************
            // printing values
            //String s = entry.getKey() + ": " + entry.getValue().toString();
            //Person p = (Person) entry.getValue();
            //Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            //*****************

            String registerNumber = entry.getKey();
            //the hashmap contains string value of object
            // use gson dont panic

            Gson gson = new Gson();
            Person currPerson = gson.fromJson(entry.getValue().toString(), Person.class);

            //Person currPerson = (Person) entry.getValue();

            switch (currPerson.getId().length()) {
                case 1:
                    increment(currPerson, 10);
                    break;
                case 2:
                    increment(currPerson, 5);

                    break;
                default:
                    increment(currPerson, 1);
                    break;
            }


        }


        //original hash table
       /* for (Map.Entry<String, Person> entry : database.entrySet()) {
            String registerNumber = entry.getKey();
            Person currPerson = entry.getValue();

            switch (currPerson.getId().length()) {
                case 1:
                    increment(currPerson, 10);
                    break;
                case 2:
                    increment(currPerson, 5);

                    break;
                default:
                    increment(currPerson, 1);
                    break;
            }


        }*/


    }

    private void increment(Person currPerson, int votes) {

        //increment votes for president

        if (currPerson.getHasVotedPresident1()) {
            tempp1 += votes;
        } else {
            tempp2 += votes;
        }


        //increment votes for vice president

        if (currPerson.getHasVotedVicePresident1()) {
            tempvp1 += votes;
        } else {
            tempvp2 += votes;
        }

        //increment votes for treasurer

        if (currPerson.getHasVotedTreasurer1()) {
            tempt1 += votes;
        } else {
            tempt2 += votes;
        }

        //increment votes for secretary

        if (currPerson.getHasVotedSecretary1()) {
            temps1 += votes;
        } else {
            temps2 += votes;
        }


        //increment votes for joint secretary

        if (currPerson.getHasVotedJointSecretary1()) {
            tempjs1 += votes;
        } else {
            tempjs2 += votes;
        }


    }


    @SuppressLint("SetTextI18n")
    private void computewinners() {
//calculating winners........

        tv_totalvotescount.setText(String.valueOf(totalvotes));

        //a for 1 b for 2 percentage =a/tv*100;

        //president
        if (tempp1 == tempp2) {
            tv_presidentname.setText(getString(R.string.Winner) + getString(R.string.draw));
            dummy = 0;
            tv_presidentwincount.setText(getString(R.string.Winsby) + String.valueOf(dummy));


        } else if (tempp1 > tempp2) {

            tv_presidentname.setText(getString(R.string.Winner) + getString(R.string.presidentname1cv));
            dummy = tempp1 - tempp2;
            tv_presidentwincount.setText(getString(R.string.Winsby) + String.valueOf(dummy));
            president.setImageResource(R.drawable.ic_launcher_background);
        } else {

            tv_presidentname.setText(getString(R.string.Winner) + getString(R.string.presidentname2cv));
            dummy = tempp2 - tempp1;
            tv_presidentwincount.setText(getString(R.string.Winsby) + String.valueOf(dummy));
            president.setImageResource(R.drawable.ic_launcher_background);
        }

//vice president


        if (tempvp1 == tempvp2) {
            //  a = tempvp1;
            //b = tempvp2;

            tv_vicepresidentname.setText(getString(R.string.Winner) + getString(R.string.draw));
            dummy = 0;
            tv_vicepresidentwincount.setText(getString(R.string.Winsby) + String.valueOf(dummy));

        } else if (tempvp1 > tempvp2) {
            tv_vicepresidentname.setText(getString(R.string.Winner) + getString(R.string.vpname1cv));
            dummy = tempvp1 - tempvp2;
            tv_vicepresidentwincount.setText(getString(R.string.Winsby) + String.valueOf(dummy));

            vicepresident.setImageResource(R.drawable.ic_launcher_background);

        } else {

            tv_vicepresidentname.setText(getString(R.string.Winner) + getString(R.string.vpname2cv));
            dummy = tempvp2 - tempvp1;
            tv_vicepresidentwincount.setText(getString(R.string.Winsby) + String.valueOf(dummy));

            vicepresident.setImageResource(R.drawable.ic_launcher_background);

        }

        //Treasurer

        if (tempt1 == tempt2) {

            tv_treasurername.setText(getString(R.string.Winner) + getString(R.string.draw));
            dummy = 0;
            tv_treasurerwincount.setText(getString(R.string.Winsby) + String.valueOf(dummy));
        } else if (tempt1 > tempt2) {

            tv_treasurername.setText(getString(R.string.Winner) + getString(R.string.treasurername1cv));
            dummy = tempt1 - tempt2;
            tv_treasurerwincount.setText(getString(R.string.Winsby) + String.valueOf(dummy));
            treasurer.setImageResource(R.drawable.ic_launcher_background);
        } else {

            tv_treasurername.setText(getString(R.string.Winner) + getString(R.string.treasurername2cv));
            dummy = tempjs2 - tempjs1;
            tv_treasurerwincount.setText(getString(R.string.Winsby) + String.valueOf(dummy));
            treasurer.setImageResource(R.drawable.ic_launcher_background);

        }

        //secretary

        if (temps1 == temps2) {
            tv_secretaryname.setText(getString(R.string.Winner) + getString(R.string.draw));
            dummy = 0;
            tv_secretarywincount.setText(getString(R.string.Winsby) + String.valueOf(dummy));
        } else if (temps1 > temps2) {

            tv_secretaryname.setText(getString(R.string.Winner) + getString(R.string.secretary_name1cv));
            dummy = temps1 - temps2;
            tv_secretarywincount.setText(getString(R.string.Winsby) + String.valueOf(dummy));
            secretary.setImageResource(R.drawable.ic_launcher_background);

        } else {


            tv_secretaryname.setText(getString(R.string.Winner) + getString(R.string.secretary_name2cv));
            dummy = temps2 - temps1;
            tv_secretarywincount.setText(getString(R.string.Winsby) + String.valueOf(dummy));
            secretary.setImageResource(R.drawable.ic_launcher_background);

        }

        //joint secretary

        if (tempjs1 == tempjs2) {
            tv_jointsecretaryname.setText(getString(R.string.Winner) + getString(R.string.draw));
            dummy = 0;
            tv_jointsecretarywincount.setText(getString(R.string.Winsby) + String.valueOf(dummy));

        } else if (tempjs1 > tempjs2) {


            tv_jointsecretaryname.setText(getString(R.string.Winner) + getString(R.string.joint_secretary_name1cv));
            dummy = tempjs1 - tempjs2;
            tv_jointsecretarywincount.setText(getString(R.string.Winsby) + String.valueOf(dummy));
            jointsecretary.setImageResource(R.drawable.ic_launcher_background);

        } else {
            tv_jointsecretaryname.setText(getString(R.string.Winner) + getString(R.string.joint_secretary_name2cv));
            dummy = tempjs2 - tempjs1;
            tv_jointsecretarywincount.setText(getString(R.string.Winsby) + String.valueOf(dummy));
            jointsecretary.setImageResource(R.drawable.ic_launcher_background);

        }


    }

    private void init_variables() {


        tv_totalvotescount = findViewById(R.id.tv_totalVotescount);

        tv_presidentname = findViewById(R.id.tv_president);
        tv_presidentwincount = findViewById(R.id.tv_presidentcount);


        tv_vicepresidentname = findViewById(R.id.tv_vicepresident);
        tv_vicepresidentwincount = findViewById(R.id.tv_vicepresidentcount);

        tv_treasurername = findViewById(R.id.tv_treasurer);
        tv_treasurerwincount = findViewById(R.id.tv_treasurercount);

        tv_secretaryname = findViewById(R.id.tv_Secretary);
        tv_secretarywincount = findViewById(R.id.tv_Secretarycount);

        tv_jointsecretaryname = findViewById(R.id.tv_JointSecretary);
        tv_jointsecretarywincount = findViewById(R.id.tv_JointSecretarycount);

        president = findViewById(R.id.presidentimage);
        vicepresident = findViewById(R.id.vpimage);
        treasurer = findViewById(R.id.treasurerimage);
        secretary = findViewById(R.id.secimage);
        jointsecretary = findViewById(R.id.jsimage);


    }
}
