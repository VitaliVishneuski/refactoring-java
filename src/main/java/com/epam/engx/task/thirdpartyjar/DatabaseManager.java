package com.epam.engx.task.thirdpartyjar;

import java.util.List;

public interface DatabaseManager {


    List<DataSet> getTableData(String tableName);

}