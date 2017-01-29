package kishore.mad.com.expenseapp;
/*
* InClass10
* Group 27
* */

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static kishore.mad.com.expenseapp.SignupActivity.REF_USERS_REF;
import static kishore.mad.com.expenseapp.SignupActivity.T_FULL_NAME;

public class ExpenseActivity extends AppCompatActivity implements ExpenseFragment.OnExpenseFragmentInteractionListener,
        AddExpenseFragment.OnFragmentInteractionListener, ShowExpenseFragment.OnFragmentInteractionListener {

    public static final String EXPENSE_FRAG = "expenseFrag";
    public static final String EXPENSE_FIREBASE_NAME = "Expenses";
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mRootRef = db.getReference();
    private ValueEventListener mValueListener;
    DatabaseReference mExpenseRef;
    public static String userId;
    public static String userName;
    ProgressDialog pdialog;
    ArrayList<Expense> expenseList = new ArrayList<>();
    boolean isFirst = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getIntent().getExtras().containsKey(LoginActivity.USER_ID)){
            userId = (String) getIntent().getExtras().get(LoginActivity.USER_ID);
            userName = (String) getIntent().getExtras().get(LoginActivity.FIREBASE_USER);
            TextView loginName = (TextView) findViewById(R.id.loginNameText_view);
            loginName.setText("Logged in as "+userName);
        }
        pdialog = new ProgressDialog(this);
        pdialog.setMessage("Downloading Saved Expenses...");
        pdialog.setIndeterminate(true);
        pdialog.show();
        getFragmentManager().beginTransaction()
                .add(R.id.container, new ExpenseFragment(), EXPENSE_FRAG).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mExpenseRef = mRootRef.child(EXPENSE_FIREBASE_NAME).child(userId);
        mValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                expenseList = new ArrayList<Expense>();
                Log.d("demo", "data received!!");

                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    Log.d("demo", " key is :"+s.getKey());
                    Log.d("demo", "value is " + s.getValue());
                    HashMap<String, Object> value = (HashMap<String, Object>) s.getValue();
                    String name = (String) value.get("name");
                    double amount  =0 ;
                    if(value.get("amount") instanceof Long){
                        amount = (Long) value.get("amount");
                    }else if(value.get("amount") instanceof Double){
                        amount = (Double) value.get("amount");
                    }
                    HashMap<String, Object> dateMap = (HashMap<String, Object>) value.get("dateMade");
                    Date d = new Date();
                    d.setTime((Long) dateMap.get("time"));
                    String categ = (String) value.get("category");
                    Expense exp = new Expense(name, amount,d,categ );
                    exp.set_key((String) value.get("_key"));
                    expenseList.add(exp);
                }
                ExpenseActivity.this.refreshListView();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mExpenseRef.addValueEventListener(mValueListener);
    }

    public void refreshListView(){
        if(isFirst)
            pdialog.dismiss();
        ExpenseFragment expFrag = (ExpenseFragment) getFragmentManager().findFragmentByTag(EXPENSE_FRAG);
        expFrag.displayExpenses(expenseList);
        isFirst= false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mExpenseRef.removeEventListener(mValueListener);
    }
    @Override
    public void newExpenseAction() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new AddExpenseFragment(), "addExpenseFrag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteExpense(Expense expense) {
        mExpenseRef.child(expense.get_key()).setValue(null);
    }

    @Override
    public void showExpense(Expense expense) {
        ShowExpenseFragment frag = new ShowExpenseFragment();
        frag.addExpense(expense);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, frag, "showExpenseFrag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void addExpense(Expense exp) {
        //firebase code
        DatabaseReference ref = mExpenseRef.push();
        exp.set_key(ref.getKey());
        ref.setValue(exp);

        getFragmentManager().popBackStack();

    }

    @Override
    public void cancel() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onFragmentInteraction() {
        getFragmentManager().popBackStack();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout_menu_item:
                mAuth.signOut();
                LoginActivity.userId = "";
                initLoginActivity();
                return true;
        }
        return false;
    }
    private void initLoginActivity() {
        Intent intent = new Intent(ExpenseActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
