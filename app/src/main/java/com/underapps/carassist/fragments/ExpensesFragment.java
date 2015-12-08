package com.underapps.carassist.fragments;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.underapps.carassist.MainActivity;
import com.underapps.carassist.R;
import com.underapps.carassist.adapters.ExpenseAdapter;
import com.underapps.carassist.models.Expense;
import com.underapps.carassist.views.ExpensesFormManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class ExpensesFragment extends Fragment {

    @Bind(R.id.recycler_view_expenses)
    RecyclerView recyclerViewCards;
    @Bind(R.id.editTextDescription)
    EditText editTextDescription;
    @Bind(R.id.editTextAmount)
    EditText editTextAmount;
    @Bind(R.id.editTextDate)
    EditText editTextDate;
    @Bind(R.id.spinnerCategory)
    Spinner spinnerCategory;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.fabClose)
    FloatingActionButton fabClose;
    @Bind(R.id.form_card)
    CardView cardView;

    private ExpenseAdapter mAdapter;
    private ExpensesFormManager expensesFormManager;
    private SupportAnimator animator;

    public static ExpensesFragment newInstance() {
        ExpensesFragment fragment = new ExpensesFragment();
        return fragment;
    }

    public ExpensesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expensesFormManager = new ExpensesFormManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        ButterKnife.bind(this, view);

        initRecycler(((MainActivity) getActivity()).getHelper().getExpenseDao().queryForAll());
        populateSpinner();

        expensesFormManager.setAmountMask(editTextAmount);
        expensesFormManager.setDateMask(editTextDate);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expensesFormManager.isVisible()){
                    if(expensesFormManager.isValidForm(getActivity(),editTextDescription,editTextAmount )){
                        expensesFormManager.saveData(getActivity(), ((MainActivity) getActivity()).getHelper(),editTextDescription,editTextAmount, spinnerCategory.getSelectedItemPosition());
                        hideForm();
                    }

                } else showForm();
                expensesFormManager.switchVisibility();

            }
        });

        fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expensesFormManager.isVisible()){
                    hideForm();
                }

            }
        });

        return view;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void initRecycler(List<Expense> expenses){
        recyclerViewCards.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCards.setLayoutManager(mLayoutManager);
        mAdapter = new ExpenseAdapter(getContext(), expenses);
        recyclerViewCards.setAdapter(mAdapter);
        recyclerViewCards.setVisibility(View.VISIBLE);
    }

    private void populateSpinner(){
        String[] categories = getResources().getStringArray(R.array.categories);

        ArrayAdapter adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                categories);

        spinnerCategory.setAdapter(adapter);
    }

    private void showForm(){
        fab.setImageDrawable(getActivity().getDrawable(R.drawable.ic_done_white_36dp));
        cardView.setVisibility(View.VISIBLE);
        recyclerViewCards.setVisibility(View.GONE);
        // get the center for the clipping circle
        int cx = (cardView.getLeft() + cardView.getRight()) / 2;
        int cy = (cardView.getTop() + cardView.getBottom()) / 2;

        // get the final radius for the clipping circle
        int dx = Math.max(cx, cardView.getWidth() - cx);
        int dy = Math.max(cy, cardView.getHeight() - cy);
        float finalRadius = (float) Math.hypot(dx, dy);

        animator = ViewAnimationUtils.createCircularReveal(cardView, cx, cy, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
        animator.start();
    }

    private void hideForm(){
        fab.setImageDrawable(getActivity().getDrawable(R.drawable.ic_add_white_36dp));
        animator.reverse();

        // get the center for the clipping circle
        int cx = (cardView.getLeft() + cardView.getRight()) / 2;
        int cy = (cardView.getTop() + cardView.getBottom()) / 2;

        // get the final radius for the clipping circle
        int dx = Math.max(cx, cardView.getWidth() - cx);
        int dy = Math.max(cy, cardView.getHeight() - cy);
        float finalRadius = (float) Math.hypot(dx, dy);

        final SupportAnimator animator =
                ViewAnimationUtils.createCircularReveal(cardView, cx, cy, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
        animator.start();
        cardView.setVisibility(View.INVISIBLE);
        recyclerViewCards.setVisibility(View.VISIBLE);
        mAdapter = new ExpenseAdapter(getContext(), ((MainActivity)getActivity()).getHelper().getExpenseDao().queryForAll());
        recyclerViewCards.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void changeIcon(){
        final int[] ids = {R.drawable.ic_done_white_36dp};
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, ids.length - 1).setDuration(500);
        valueAnimator.setInterpolator( new LinearInterpolator() /*your TimeInterpolator*/ );
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int i = -1;
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                if(i!=animatedValue) {
                    fab.setImageDrawable(getResources().getDrawable(ids[animatedValue]));
                    i = animatedValue;
                }
            }
        });
    }
}
