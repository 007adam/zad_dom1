package com.example.zad_dom1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zad_dom1.contacts.ContactListContent;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactInfoFragment extends Fragment {

    public ContactInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if(intent !=null){
            ContactListContent.Contact receivedContact = intent.getParcelableExtra(MainActivity.contactExtra);
            if(receivedContact != null){
                displayContact(receivedContact);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_info, container, false);
    }
    public void displayContact(ContactListContent.Contact contact) {
        FragmentActivity activity = getActivity();
        TextView ContactInfoName = activity.findViewById(R.id.ContactInfoName);
        TextView ContactInfoPhone = activity.findViewById(R.id.ContactInfoPhone);
        TextView ContactInfoBirthday = activity.findViewById(R.id.ContactInfoBirthday);
        ImageView ContactInfoImage = activity.findViewById(R.id.ContactInfoImage);

        ContactInfoName.setText(contact.name +" " +contact.surname);
        ContactInfoPhone.setText("Phone Number: "+contact.phone_number);
        ContactInfoBirthday.setText("Birthday: "+contact.birthday);
        Drawable contactDrawable;
        switch(contact.picPath){
            case 0:
                contactDrawable = activity.getResources().getDrawable(R.drawable.avatar_1);
                break;
            case 1:
                contactDrawable = activity.getResources().getDrawable(R.drawable.avatar_2);
                break;
            case 2:
                contactDrawable = activity.getResources().getDrawable(R.drawable.avatar_3);
                break;
            case 3:
                contactDrawable =activity.getResources().getDrawable(R.drawable.avatar_4);
                break;
            case 4:
                contactDrawable =activity.getResources().getDrawable(R.drawable.avatar_5);
                break;
            case 5:
                contactDrawable = activity.getResources().getDrawable(R.drawable.avatar_6);
                break;
            case 6:
                contactDrawable = activity.getResources().getDrawable(R.drawable.avatar_7);
                break;
            case 7:
                contactDrawable = activity.getResources().getDrawable(R.drawable.avatar_8);
                break;
            default:
                contactDrawable = activity.getResources().getDrawable(R.drawable.avatar_9);
        }
        ContactInfoImage.setImageDrawable(contactDrawable);
    }

}
