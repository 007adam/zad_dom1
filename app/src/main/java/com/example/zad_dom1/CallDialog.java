package com.example.zad_dom1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.zad_dom1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallDialog extends DialogFragment {
    OnCallDialogInteractionListener mListener;
    public CallDialog() {
        // Required empty public constructor
    }

    static CallDialog newInstance(){
        return new CallDialog();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ContactFragment.OnListFragmentInteractionListener) {
            mListener = (OnCallDialogInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.calling_question) +"?");
        builder.setPositiveButton(getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogPositiveClick(CallDialog.this);
            }
        });
        builder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogNegativeClick(CallDialog.this);
            }
        });
        return builder.create();

    }

    public interface OnCallDialogInteractionListener {
        void onDialogNegativeClick(DialogFragment dialog);
        void onDialogPositiveClick(DialogFragment dialog);
    }


}
