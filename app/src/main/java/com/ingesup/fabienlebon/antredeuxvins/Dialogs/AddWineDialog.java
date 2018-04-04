package com.ingesup.fabienlebon.antredeuxvins.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.ColorEnum;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Food;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Wine;
import com.ingesup.fabienlebon.antredeuxvins.R;

import org.w3c.dom.Text;

import java.util.Date;


/**
 * Created by fabienlebon on 04/04/2018.
 */

public class AddWineDialog extends DialogFragment {

    private static String TAG = "addWineFragment";

    private TextInputLayout name,millesime,volume;
    private RadioGroup type;
    private CheckBox viande, fromage, crustace;
    private Food[] foods;

    public static interface addWineDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, Wine n);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    addWineDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout. dialog_addwine, null);

        name        = (TextInputLayout) view.findViewById(R.id.fragment_name_wine);
        volume      = (TextInputLayout) view.findViewById(R.id.fragment_volume);
        millesime   = (TextInputLayout) view.findViewById(R.id.fragment_millesime);
        type        = (RadioGroup) view.findViewById(R.id.fragment_type_wine);
        viande      = (CheckBox) view.findViewById(R.id.fragment_viande);
        fromage     = (CheckBox) view.findViewById(R.id.fragment_fromage);
        crustace    = (CheckBox) view.findViewById(R.id.fragment_crustace);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ColorEnum e = null;

                        if(!name.getEditText().getText().toString().equals("") && !millesime.getEditText().getText().toString().equals("")
                                && !volume.getEditText().getText().toString().equals("")) {
                            if(Integer.valueOf(millesime.getEditText().getText().toString()) > 1900
                                    && Integer.valueOf(millesime.getEditText().getText().toString()) < 2018) {
                                if(type.getCheckedRadioButtonId() > 0) {
                                    switch(type.getCheckedRadioButtonId()) {
                                        case 1:
                                            e = ColorEnum.Rouge;
                                            break;
                                        case 2:
                                            e = ColorEnum.Blanc;
                                            break;
                                        case 3:
                                            e = ColorEnum.Rose;
                                            break;
                                    }
                                    if(viande.isChecked() || fromage.isChecked() || crustace.isChecked()) {
                                        Food[] foodArray = new Food[]{viande.isChecked() ? Food.Viande: null, fromage.isChecked() ? Food.Fromage: null, crustace.isChecked() ? Food.Crustace: null};
                                        Wine wine = new Wine(name.getEditText().getText().toString(),
                                                new Date(Integer.valueOf(millesime.getEditText().getText().toString())),
                                                Float.valueOf(volume.getEditText().getText().toString()),
                                                e,
                                                foodArray);
                                        mListener.onDialogPositiveClick(AddWineDialog.this, wine);
                                    }
                                    else{
                                        Toast.makeText(getContext(),"Choisissez au moins un accompagnement",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(getContext(),"Choisissez un type de vin",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                millesime.setError("Entrez une date comprise entre 1900 et 2018");
                            }

                        }
                        else
                        {
                            Toast.makeText(getContext(),"Veuillez remplir les champs",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(AddWineDialog.this);
                    }
                });
        return builder.create();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (addWineDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement the listener");
        }
    }
}