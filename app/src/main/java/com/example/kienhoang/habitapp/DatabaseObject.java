package com.example.kienhoang.habitapp;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by kienhoang on 3/31/15.
 */
public interface DatabaseObject {
    public ContentValues toContentValues();
}
