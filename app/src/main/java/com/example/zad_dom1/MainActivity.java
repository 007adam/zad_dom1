package com.example.zad_dom1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.zad_dom1.contacts.ContactListContent;
import com.example.zad_dom1.contacts.DeleteDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity  implements ContactFragment.OnListFragmentInteractionListener, CallDialog.OnCallDialogInteractionListener, DeleteDialog.OnDeleteDialogInteractionListener{

    public static final String contactExtra = "contactExtra";
    public static final String contactExtra2 = "contactExtra2";
    private int currentItemPosition = -1;

    @Override
    public void onListFragmentClickInteraction(ContactListContent.Contact contact, int position) {
        Toast.makeText(this,getString(R.string.item_selected_msg) + position,Toast.LENGTH_SHORT).show();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            displayContactInFragment(contact);
        }else{
            startSecondActivity(contact,position);
        }
    }

    @Override
    public void onListFragmentLongClickInteraction(int position) {
        showCallDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.fragment_contact_add);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("ResourceType")
    public void onDeleteClickInteraction(int position) {
        Toast.makeText(this,getString(R.string.long_click_msg) + position, Toast.LENGTH_SHORT).show();
        showDeleteDialog();
        currentItemPosition = position ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   @SuppressLint("ResourceType")
    public void addClick(View view) {
        EditText contactNameEditTxt = findViewById(R.id.ContactName);
        EditText ContactSurnameEditTxt = findViewById(R.id.ContactSurname);
        EditText ContactBirthdayEditTxt = findViewById(R.id.ContactBirthday);
        EditText ContactPhoneEditTxt = findViewById(R.id.ContactPhone);

        String ContactName = contactNameEditTxt.getText().toString();
        String ContactSurname = ContactSurnameEditTxt.getText().toString();
        String ContactBirthday = ContactBirthdayEditTxt.getText().toString();
        String ContactPhone= ContactPhoneEditTxt.getText().toString();


        int selectedImage = (int)(Math.random()*10);

            if(ContactName.isEmpty()){
                ContactName = getString(R.string.default_name);
                return;}
            if(ContactSurname.isEmpty()){
                ContactSurname = getString(R.string.default_surname);
                return;
            }
            if(!checkDate(ContactBirthday)){
                ContactBirthdayEditTxt.setError("The date that you provided is invalid (dd/MM/yyyy)");
                return;
            }
            if(!checkPhoneNumber(ContactPhone)){
                ContactPhoneEditTxt.setError("The number that you provided is invalid");
                return;
            }

            ContactListContent.addItem(new ContactListContent.Contact("Contact." + ContactListContent.ITEMS.size() +1,
                    ContactName,
                    ContactSurname,
                    ContactBirthday,
                    ContactPhone,
                    selectedImage));

        contactNameEditTxt.setText("");
        ContactSurnameEditTxt.setText("");
        ContactBirthdayEditTxt.setText("");
        ContactPhoneEditTxt.setText("");

        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        Intent intent = new Intent(this, MainActivity.class);
       startActivity(intent);
    }

    private void displayContactInFragment(ContactListContent.Contact contact){
        ContactInfoFragment contactInfoFragment = ((ContactInfoFragment) getSupportFragmentManager().findFragmentById(R.id.displayFragment));
        if(contactInfoFragment != null){
            contactInfoFragment.displayContact(contact);
        }
    }

    private void startSecondActivity(ContactListContent.Contact contact, int position){
        Intent intent = new Intent(this,ContactInfoActivity.class);
        intent.putExtra(contactExtra,contact);
        startActivity(intent);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog){

    }
    @Override
    public void onDialogNegativeClick(DialogFragment dialog){

    }
    private void showCallDialog(){
        CallDialog.newInstance().show(getSupportFragmentManager(),getString(R.string.call_dialog_tag));
    }

    private void showDeleteDialog(){
        DeleteDialog.newInstance().show(getSupportFragmentManager(),getString(R.string.delete_dialog_tag));
    }

    @Override
    public void onDeleteDialogPositiveClick(DialogFragment dialog) {
        if(currentItemPosition != -1 && currentItemPosition < ContactListContent.ITEMS.size()){
            ContactListContent.removeItem(currentItemPosition);

        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDeleteDialogNegativeClick(DialogFragment dialog){
        View v = findViewById(R.id.Delete_Button);
        if(v != null){
            Snackbar.make(v,getString(R.string.delete_cancel_msg), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.retry_msg), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDeleteDialog();
                        }
                    }).show();
        }
    }

    public boolean checkDate(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date testDate = null;
        try
        {
            testDate = sdf.parse(date);
        }

        catch (ParseException e)
        {

            return false;
        }

        if (!sdf.format(testDate).equals(date))
        {

            return false;
        }

        return true;

    }

    private boolean checkPhoneNumber(String phone) {
          boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone))
        {
            if(phone.length()==9)
            {
                check = true;
            }
        }
        else
        {
            check=false;
        }
        return check;
        //return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}
