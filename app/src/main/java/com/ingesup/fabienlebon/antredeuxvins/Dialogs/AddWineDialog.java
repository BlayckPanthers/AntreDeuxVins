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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.ColorEnum;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Country;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Food;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Wine;
import com.ingesup.fabienlebon.antredeuxvins.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by fabienlebon on 04/04/2018.
 */

public class AddWineDialog extends DialogFragment {

    private static final String TAG = "addWineFragment";


    private TextInputLayout name, millesime, volume;
    private RadioGroup type;
    private CheckBox viande, fromage, crustace;
    private RadioButton rouge, blanc, rose;

    private static final int ROUGE_ID = 1000;
    private static final int BLANC_ID = 1001;
    private static final int ROSE_ID = 1002;


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
        View view = inflater.inflate(R.layout.dialog_addwine, null);

        name = (TextInputLayout) view.findViewById(R.id.fragment_name_wine);
        volume = (TextInputLayout) view.findViewById(R.id.fragment_volume);
        millesime = (TextInputLayout) view.findViewById(R.id.fragment_millesime);

        type = (RadioGroup) view.findViewById(R.id.fragment_type_wine);

        viande = (CheckBox) view.findViewById(R.id.fragment_viande);
        fromage = (CheckBox) view.findViewById(R.id.fragment_fromage);
        crustace = (CheckBox) view.findViewById(R.id.fragment_crustace);

        rouge = (RadioButton) view.findViewById(R.id.rouge1);
        blanc = (RadioButton) view.findViewById(R.id.blanc1);
        rose = (RadioButton) view.findViewById(R.id.rose1);

        rouge.setId(ROUGE_ID);
        blanc.setId(BLANC_ID);
        rose.setId(ROSE_ID);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.addwine_dialog_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ColorEnum e = null;
                        Log.i(TAG, "type vin : " + type.getCheckedRadioButtonId());

                        if (!name.getEditText().getText().toString().equals("") && !millesime.getEditText().getText().toString().equals("")
                                && !volume.getEditText().getText().toString().equals("")) {
                            if (Integer.valueOf(millesime.getEditText().getText().toString()) > 1900
                                    && Integer.valueOf(millesime.getEditText().getText().toString()) < 2018) {
                                if (type.getCheckedRadioButtonId() > 0) {
                                    switch (type.getCheckedRadioButtonId()) {
                                        case ROUGE_ID:
                                            e = ColorEnum.Rouge;
                                            break;
                                        case BLANC_ID:
                                            e = ColorEnum.Blanc;
                                            break;
                                        case ROSE_ID:
                                            e = ColorEnum.Rose;
                                            break;
                                    }
                                    if (viande.isChecked() || fromage.isChecked() || crustace.isChecked()) {
                                        List<Food> foodList = new ArrayList<Food>();
                                        if (viande.isChecked())
                                            foodList.add(Food.Viande);
                                        if (fromage.isChecked())
                                            foodList.add(Food.Fromage);
                                        if (crustace.isChecked())
                                            foodList.add(Food.Crustace);


                                        Wine wine = new Wine(13, name.getEditText().getText().toString(),
                                                new Date(Integer.valueOf(millesime.getEditText().getText().toString())),
                                                Float.valueOf(volume.getEditText().getText().toString()),
                                                e,
                                                foodList, Country.France);
                                        mListener.onDialogPositiveClick(AddWineDialog.this, wine);
                                    } else {
                                        Toast.makeText(getContext(), getText(R.string.addwine_error_food_choice), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), getText(R.string.addwine_error_type_wine), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                millesime.setError(getText(R.string.addwine_error_millesime_year));
                            }

                        } else {
                            Toast.makeText(getContext(), getText(R.string.login_error_empty_fields), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.user_dialog_cancel, new DialogInterface.OnClickListener() {
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
