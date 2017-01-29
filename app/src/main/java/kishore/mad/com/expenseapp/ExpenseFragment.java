package kishore.mad.com.expenseapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ExpenseFragment extends Fragment {

    private OnExpenseFragmentInteractionListener mListener;
    private View frag;
    private ExpenseItemAdapter adapter;

    public ExpenseFragment() {
        // Required empty public constructor
    }

    TextView noItems;
    ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        frag = inflater.inflate(R.layout.fragment_expense, container, false);
        return frag;
    }

    ListView lview;
    List<Expense> expenses = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        image = (ImageView) getView().findViewById(R.id.addImage);
        noItems = (TextView) getView().findViewById(R.id.noItems);
        lview = (ListView) getView().findViewById(R.id.listView);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.newExpenseAction();
            }
        });
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.showExpense(adapter.getItem(position));
            }
        });
        lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.deleteExpense(expenses.get(position));
                resetListView();
                Toast.makeText(getActivity(), "Expense Deleted", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        resetListView();
    }

    private void resetListView() {
        ListAdapter adapter1 = lview.getAdapter();
        if (adapter1 == null) {
            this.adapter = new ExpenseItemAdapter(getActivity(), R.layout.list_item, expenses);
            adapter.setNotifyOnChange(true);
            lview.setAdapter(adapter);
        }
        if (adapter.getCount() > 0) {
            lview.setVisibility(View.VISIBLE);
            noItems.setVisibility(View.GONE);
        } else {
            lview.setVisibility(View.GONE);
            noItems.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnExpenseFragmentInteractionListener) {
            mListener = (OnExpenseFragmentInteractionListener) context;

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


    public void displayExpenses(ArrayList<Expense> exps) {
        adapter.clear();
        adapter.addAll(exps);
//        adapter = new ExpenseItemAdapter(getActivity(), R.layout.list_item, expenses);
        resetListView();
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
    public interface OnExpenseFragmentInteractionListener {
        // TODO: Update argument type and name
        void newExpenseAction();

        void deleteExpense(Expense expense);

        void showExpense(Expense expense);
    }
}
