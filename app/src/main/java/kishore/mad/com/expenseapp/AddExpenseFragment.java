package kishore.mad.com.expenseapp;
/*
* InClass08
* AddExpenseFragment.java
* Nanda Kishore Kolluru
* */
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class AddExpenseFragment extends Fragment {
    Spinner spinnerCateg;
    List<String> spinnerEntries;
    private OnFragmentInteractionListener mListener;

    public AddExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frag = inflater.inflate(R.layout.fragment_add_expense, container, false);


        return frag;
    }

    Button addExp, cancel;
    EditText amount, expName;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("demo", "Add expense OnActivity called!");
        super.onActivityCreated(savedInstanceState);
        spinnerEntries = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.spinnerEntries)));
        spinnerCateg = (Spinner) getView().findViewById(R.id.spinnerCategory);
        addExp = (Button) getView().findViewById(R.id.btnAddExpense);
        cancel = (Button) getView().findViewById(R.id.btnCancel);
        amount = (EditText) getView().findViewById(R.id.etAmount);
        expName = (EditText) getView().findViewById(R.id.etExpName);
        addExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = expName.getText().toString().trim();
                if (name.length() <= 0) {
                    Toast.makeText(getActivity(), "Enter the name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (amount.getText().toString().length() <= 0) {

                    Toast.makeText(getActivity(), "Enter the Expense Amount", Toast.LENGTH_LONG).show();

                    return;
                }

                Double amt = Double.parseDouble(amount.getText().toString().trim());
                String categ = spinnerEntries.get((int) spinnerCateg.getSelectedItemId());
                Date d = new Date();
                Expense exp = new Expense(name, amt, d, categ);
                mListener.addExpense(exp);


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancel();
            }
        });
        addSpinnerItems();
    }

    private void addSpinnerItems() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.spinnerEntries, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerCateg.setAdapter(adapter);
        spinnerCateg.setPrompt(getString(R.string.textSelectCategorySpinnerPrompt));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnExpenseFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void addExpense(Expense exp);

        void cancel();
    }
}
