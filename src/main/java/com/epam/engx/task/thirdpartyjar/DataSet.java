package com.epam.engx.task.thirdpartyjar;

import java.util.List;

public interface DataSet {
    void put(String columnName, Object value);

    List<String> getColumnNames();

    List<Object> getValues();

}