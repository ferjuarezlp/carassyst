package com.underapps.carassist.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ferjuarez on 08/12/15.
 */
@DatabaseTable(tableName = "category")
public class Category {
    @DatabaseField(generatedId = true)
    public Integer id;
    @DatabaseField
    private String description;

    public Category(){
        // needed by ormlite
    }

    private Category(
            final String newDescription)
    {
        this.description = newDescription;
    }

    public static class Builder
    {
        private String builderDescription;

        public Builder description(String newDescription)
        {
            this.builderDescription = newDescription;
            return this;
        }


        public Category build()
        {
            return new Category(
                    builderDescription);
        }
    }

}
