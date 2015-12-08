package com.underapps.carassist.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.underapps.carassist.R;
import com.underapps.carassist.models.Expense;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Nilesh Jarad on 18-06-2015.
 */
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {


    private final List<Expense> expensesCard;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.textViewDescription)
        TextView textViewDescription;
        @Bind(R.id.textViewAmount)
        TextView textViewAmount;
        @Bind(R.id.textViewCategory)
        TextView textViewCategory;
        @Bind(R.id.card_view)
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }


    public ExpenseAdapter(Context context, List<Expense> expenses) {
        this.context = context;
        this.expensesCard = expenses;
    }


    public long getID(int position) {
        return expensesCard.get(position).getId();
    }

    public void removeItem(int position) {
        expensesCard.remove(position);
        notifyItemRemoved(position);

//        for (int i = 0; i < visitingCards.size(); i++) {
////            if (visitingCards.get(i).getNo() == id) {
//////                visitingCards.remove(i);
////                notifyDataSetChanged();
////                return;
////            }
//        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_card, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String[] categories = context.getResources().getStringArray(R.array.categories);

        String formattedString = String.format( "%.2f", expensesCard.get(position).getAmount());

        holder.textViewAmount.setText("" + formattedString);
        holder.textViewDescription.setText("" + expensesCard.get(position).getDescription());
        holder.textViewCategory.setText("" + categories[expensesCard.get(position).getCategory_id()]);
    }


    @Override
    public int getItemCount() {
        return expensesCard.size();
    }


}
