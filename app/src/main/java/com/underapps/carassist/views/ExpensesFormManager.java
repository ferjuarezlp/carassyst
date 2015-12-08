package com.underapps.carassist.views;

import android.content.Context;
import android.widget.EditText;

import com.underapps.carassist.R;
import com.underapps.carassist.database.DatabaseHelper;
import com.underapps.carassist.models.Expense;
import com.underapps.carassist.utils.AmountTextWatcher;
import com.underapps.carassist.utils.GenericMaskedWatcher;

/**
 * Created by ferjuarez on 02/12/15.
 */
public class ExpensesFormManager {
    private boolean isVisible = false;

    public boolean isValidForm(Context context, EditText editDescription, EditText editAmount){
        if(editDescription.getText().toString().isEmpty()){
            editDescription.setError(context.getString(R.string.msg_error_description));
            return false;
        } else if(editAmount.getText().toString().isEmpty()){
            editDescription.setError(context.getString(R.string.msg_error_amount));
            return false;
        } else return true;
    }

    public void saveData(Context context, DatabaseHelper helper, EditText editDescription, EditText editAmount, int categoryId){
            String amount = editAmount.getText().toString().replace("$","");
            Expense expense = new Expense.Builder()
                    .description(editDescription.getText().toString())
                    .amount(Float.valueOf(amount))
                    .categoryId(categoryId)
                    .build();

            helper.getExpenseDao().create(expense);

    }

    public void showErrorMessage(){

    }

    public void changeVisibility(Boolean visible){
        this.isVisible = visible;
    }

    public void switchVisibility(){
        this.isVisible = !this.isVisible;
    }

    public Boolean isVisible(){
        return this.isVisible;
    }

    public void setAmountMask(EditText editTextAmount){
        editTextAmount.addTextChangedListener(new AmountTextWatcher(editTextAmount));
    }

    public void setDateMask(EditText editTextDate){
        editTextDate.addTextChangedListener(
                new GenericMaskedWatcher("##/##/##")
        );
    }

}
