package impl;

import operation.AbstractSqlOperations;
import operation.GifOperations;

import java.util.Map;

public class MySqlOperationsImpl extends AbstractSqlOperations {
    @Override
    public GifOperations Limit(int count) {
        var result = " LIMIT " + count;
        Map<String, String> map = getOperationMap();
        map.put("Limit", result);
        return this;
    }
}
