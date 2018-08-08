package com.a.election_eec_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Set;

public class FormMainActivity extends AppCompatActivity {

    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;

    private String regnumberstring;

    private View coordinatorLayout;
    private EditText inputRegisternumber;
    TextInputLayout inputLayoutRegisternumber;

    //Radio buttons for all
    private RadioGroup rgpresident, rgvpresident, rgtreasurer, rgsec, rgjs;
    private RadioButton president1rb, president2rb, vpresident1rb, vpresident2rb, treasurer1rb, treasurer2rb, sec1rb, sec2rb, js1rb, js2rb;


    private FloatingActionButton Submit;

    //data base
    HashMap<String, Person> database;
    // Set<Person> personSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_main);

        //variableinitialization
        init_var();
        database = new HashMap<>();

        mPrefs = getSharedPreferences(getString(R.string.dbname),MODE_PRIVATE);
        prefsEditor = mPrefs.edit();


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //1. register number correctness
                //2. register number validity
                //4. create Object person
                //5. save the object
                //3. radio button null check


                boolean check_correctness = check_register_number_correctness();
                if (!check_correctness)
                    return;
                boolean radio_button_null_check = check_radio_button_nullcheck();
                if (!radio_button_null_check)
                    return;
                Person person = getNewPerson();

                boolean entryalreadyexists = check_if_person_already_exists(person);
                if (entryalreadyexists)
                    return;

                //all good to go. Store the persons votes.

                store_details(person);


            }
        });

    }

    private void store_details(Person person) {

        //closing the keyboard
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


        database.put(person.getId(), person);

        //storing in sharedpref
        Gson gson = new Gson();
        String json = gson.toJson(person);
        prefsEditor.putString(person.getId(), json);
        prefsEditor.commit();

        Toast.makeText(this, "Stored in shared Pref", Toast.LENGTH_SHORT).show();


        String pName;

        switch (person.getId().length()) {
            case 1:
                pName = " HOD ";
                break;
            case 2:
                pName = " Staff  ";
                break;
            default:
                pName = " Student ";

        }


        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Success! " + pName +
                        "\n" +"Last Voter ID: "+person.getId() , Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Done", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cleareverything();

            }
        });
        snackbar.show();

    }

    private void cleareverything() {

        inputLayoutRegisternumber.getEditText().setText("");
        rgpresident.clearCheck();
        rgvpresident.clearCheck();
        rgtreasurer.clearCheck();
        rgsec.clearCheck();
        rgjs.clearCheck();

    }


    private boolean check_if_person_already_exists(Person person) {

        boolean result = database.containsKey(person.getId());
        if (result) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Already Voted with ID : "+person.getId() , Snackbar.LENGTH_LONG);

            snackbar.show();

        }

        return result;

    }

    private Person getNewPerson() {

        Person temp = new Person();

        temp.setId(regnumberstring);

        temp.setHasVotedPresident1(president1rb.isChecked());
        temp.setHasVotedVicePresident1(vpresident1rb.isChecked());
        temp.setHasVotedSecretary1(sec1rb.isChecked());
        temp.setHasVotedTreasurer1(treasurer1rb.isChecked());
        temp.setHasVotedJointSecretary1(js1rb.isChecked());

        return temp;

    }

    private boolean check_radio_button_nullcheck() {
        int voteP = rgpresident.getCheckedRadioButtonId();
        int voteVP = rgvpresident.getCheckedRadioButtonId();
        int voteT = rgtreasurer.getCheckedRadioButtonId();
        int voteS = rgsec.getCheckedRadioButtonId();
        int voteJS = rgjs.getCheckedRadioButtonId();

        if (voteP == -1 || voteVP == -1 || voteT == -1 || voteS == -1 || voteJS == -1) {

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Please select any one of the options", Snackbar.LENGTH_LONG);

            snackbar.show();

            return false;
        }
        return true;
    }


    private boolean check_register_number_correctness() {

        regnumberstring = inputRegisternumber.getText().toString();


        boolean digitsOnly = TextUtils.isDigitsOnly(regnumberstring);

        if (!digitsOnly) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Enter a  Number", Snackbar.LENGTH_LONG);

            snackbar.show();

            return false;
        }

        if (regnumberstring.matches("")) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Enter a Register Number", Snackbar.LENGTH_LONG);

            snackbar.show();
            return false;

        } else {
            long regnumber = Long.parseLong(regnumberstring);

        }


        Integer length = regnumberstring.length();
        //310615104013
        if (length == 1 || length == 2 || length == 12) {
            return true;
        } else {
            Snackbar snackbar = Snackbar.make(coordinatorLayout,
                    "Check Register Number Length", Snackbar.LENGTH_LONG);

            snackbar.show();

            return false;
        }


    }

    private void init_var() {
        //Register Number edit text
        inputLayoutRegisternumber = findViewById(R.id.input_layout_registernumber);
        inputRegisternumber = findViewById(R.id.et_registernumber);

        coordinatorLayout = findViewById(R.id.submitvoterl);
        //president
        rgpresident = findViewById(R.id.presidentrg);
        president1rb = findViewById(R.id.president1rb);
        president2rb = findViewById(R.id.president2rb);
        //vp
        rgvpresident = findViewById(R.id.vpresidentrg);
        vpresident1rb = findViewById(R.id.vpresident1rb);
        vpresident2rb = findViewById(R.id.vpresident2rb);

        //Treasurer
        rgtreasurer = findViewById(R.id.treasurerrg);
        treasurer1rb = findViewById(R.id.t1rb);
        treasurer2rb = findViewById(R.id.t2rb);


        //Secretary
        rgsec = findViewById(R.id.secrg);
        sec1rb = findViewById(R.id.s1rb);
        sec2rb = findViewById(R.id.s2rb);

        //js
        rgjs = findViewById(R.id.jsecrg);
        js1rb = findViewById(R.id.js1rb);
        js2rb = findViewById(R.id.js2rb);

        Submit = findViewById(R.id.fab);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.Winners) {
            open_winners_activity();
        } else if (id == R.id.ExtraVotes) {
            startActivity(new Intent(this, TeacherVotesLegend.class));

        } else if (id == R.id.About) {
            dialog_builder();
        }

        return super.onOptionsItemSelected(item);
    }

    private void dialog_builder() {
        new AlertDialog.Builder(this)
                .setTitle("Elections 2018")
                .setMessage("\nApp developed by ArunKumar.M")
                .setPositiveButton("OK", null).show();

    }

    private void open_winners_activity() {
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Check Results")
                .setMessage("Enter Password:(1234)")
                .setView(taskEditText)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (taskEditText.getText().toString().equals("1234")) {

                            Intent i = new Intent(FormMainActivity.this, WinnersActivity.class);
                            i.putExtra(getString(R.string.hashmapname), database);
                            startActivity(i);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

    }


    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }
}
