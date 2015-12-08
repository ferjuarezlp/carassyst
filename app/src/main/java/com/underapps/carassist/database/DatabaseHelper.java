package com.underapps.carassist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.underapps.carassist.R;
import com.underapps.carassist.models.Category;
import com.underapps.carassist.models.Expense;
import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private Context context;
	private static final int DATABASE_VERSION = 3;
	public static String database_name = "carassyst_bd";
	private RuntimeExceptionDao<Expense, Integer> expenseDao = null;
	private RuntimeExceptionDao<Category, Integer> categoryDao = null;


	private int max_id = -1;
	
	public DatabaseHelper(Context context) {
		super(context, database_name, null, DATABASE_VERSION);
        this.context = context;
	}


	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Expense.class);
			TableUtils.createTable(connectionSource, Category.class);


            // Add categories
            String[] categories = context.getResources().getStringArray(R.array.categories);

            getCategoryDao().create(new Category.Builder().description(categories[0]).build());
            getCategoryDao().create(new Category.Builder().description(categories[1]).build());
            getCategoryDao().create(new Category.Builder().description(categories[2]).build());
            getCategoryDao().create(new Category.Builder().description(categories[3]).build());
            getCategoryDao().create(new Category.Builder().description(categories[4]).build());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, Expense.class, true);
			TableUtils.dropTable(connectionSource, Category.class, true);

			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public RuntimeExceptionDao<Expense, Integer> getExpenseDao() {
		if (expenseDao == null) {
			expenseDao = getRuntimeExceptionDao(Expense.class);
		}
		return expenseDao;
	}

	public RuntimeExceptionDao<Category, Integer> getCategoryDao() {
		if (categoryDao == null) {
			categoryDao = getRuntimeExceptionDao(Category.class);
		}
		return categoryDao;
	}

	
	public static String getFullPath(Context context) {
		return context.getDatabasePath(database_name).getAbsolutePath();
	}
	

	/*
	 * Close database and null all daos
	 * 
	 * */
	@Override
	public void close() {
		super.close();
		expenseDao = null;
		categoryDao = null;
	}
	
}