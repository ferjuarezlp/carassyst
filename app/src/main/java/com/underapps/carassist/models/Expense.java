package com.underapps.carassist.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by ferjuarez on 17/11/15.
 */

@DatabaseTable(tableName = "expense")
public class Expense {
    @DatabaseField(generatedId = true)
    public Integer id;
    @DatabaseField
    private String description;
    @DatabaseField
    private int category_id;
    @DatabaseField
    private double amount;
    @DatabaseField
    public Date date;

    public Expense(){
        // needed by ormlite
    }

    private Expense(
            final String newDescription, final int newCategoryId,
            final double newAmount)
    {
        this.description = newDescription;
        this.category_id = newCategoryId;
        this.amount = newAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static class Builder
    {
        private String builderDescription;
        private int builderCategory_id;
        private double builderAmount;


        public Builder description(String newDescription)
        {
            this.builderDescription = newDescription;
            return this;
        }

        public Builder categoryId(int newCategoryId)
        {
            this.builderCategory_id = newCategoryId;
            return this;
        }

        public Builder amount(double newAmount)
        {
            this.builderAmount = newAmount;
            return this;
        }

        public Expense build()
        {
            return new Expense(
                    builderDescription, builderCategory_id, builderAmount);
        }
    }
}

