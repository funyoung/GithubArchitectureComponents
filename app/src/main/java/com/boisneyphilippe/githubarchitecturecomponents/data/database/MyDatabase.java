package com.boisneyphilippe.githubarchitecturecomponents.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.boisneyphilippe.githubarchitecturecomponents.data.database.converter.DateConverter;
import com.boisneyphilippe.githubarchitecturecomponents.data.database.dao.UserDao;
import com.boisneyphilippe.githubarchitecturecomponents.data.database.entity.User;

/**
 * Created by Philippe on 02/03/2018.
 */

@Database(entities = {User.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class MyDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile MyDatabase INSTANCE;

    // --- DAO ---
    public abstract UserDao userDao();
}
