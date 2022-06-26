package impl;

import operation.AbstractSqlOperations;
import operation.GifOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SqlServerOperationsImpl extends AbstractSqlOperations {
    @Override
    public GifOperations Select(String... fields) {
        var result = "SELECT " + sqlServerFormat(fields);
        getOperationMap().put("Select", result);
        return this;
    }

    @Override
    public GifOperations Query(String from) {
        var result = " FROM " + sqlServerFormat(from);
        getOperationMap().put("From", result);
        return this;
    }
    @Override
    public GifOperations OrderBy(String... params) {
        var result = " ORDER BY " + sqlServerFormat(params);
        getOperationMap().put("OrderBy", result);
        return this;
    }

    @Override
    public GifOperations WhereNotNull(String fieldNotNull) {
        String operator = checkWhere(getWhereMap()) ? " AND " : " WHERE ";
        var result = operator + sqlServerFormat(fieldNotNull)
                + " IS NOT NULL";
        getWhereMap().put("WhereNotNull", result);
        return this;
    }

    @Override
    public GifOperations WhereIn(String field, String[] params) {
        String operator = checkWhere(getWhereMap()) ? " AND " : " WHERE ";
        var result = operator + sqlServerFormat(field) + " IN " + whereInFormat(params);
        getWhereMap().put("WhereIn", result);
        return this;
    }

    @Override
    public GifOperations Join(String joinTable, String firstTableField, String secondTableField) {
        var result = "\nINNER JOIN " + sqlServerFormat(joinTable) + " ON "
                + sqlServerFormat(firstTableField) + " = " + sqlServerFormat(secondTableField);
        getOperationMap().put("Join", result);
        return this;
    }

    private String sqlServerFormat(String[] params) {
        List<String> collect = new ArrayList<>();
        for(String str : params){
            collect.add(sqlServerFormat(str));
        }
        return String.join(", ", collect);
    }
    private String sqlServerFormat(String field) {
        return Arrays.stream(field.split("\\."))
                .map(s -> "[" + s + "]")
                .collect(Collectors.joining("."));
    }
}
