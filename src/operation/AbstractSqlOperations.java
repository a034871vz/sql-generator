package operation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AbstractSqlOperations implements GifOperations {

    private final Map<String, String> operationMap = new HashMap<>();
    private final Map<String, String> whereMap = new LinkedHashMap<>();

    public AbstractSqlOperations() {
        operationMap.put("Select", "SELECT *");
    }

    @Override
    public GifOperations Select(String... fields) {
        var result = "SELECT " + String.join(", ", fields);
        operationMap.put("Select", result);
        return this;
    }

    @Override
    public GifOperations Query(String from) {
        var result = " FROM " + from;
        operationMap.put("From", result);
        return this;
    }

    @Override
    public GifOperations OrderBy(String... params) {
        var result = " ORDER BY " + String.join(", ", params);
        operationMap.put("OrderBy", result);
        return this;
    }

    @Override
    public GifOperations WhereNotNull(String fieldNotNull) {
        String operator = checkWhere(whereMap) ? " AND " : " WHERE ";
        var result = operator + fieldNotNull + " IS NOT NULL";
        whereMap.put("WhereNotNull", result);
        return this;
    }

    @Override
    public GifOperations WhereIn(String field, String[] params) {
        String operator = checkWhere(whereMap) ? " AND " : " WHERE ";
        var result = operator + field + " IN " + whereInFormat(params);
        whereMap.put("WhereIn", result);
        return this;
    }

    @Override
    public GifOperations Join(String joinTable, String firstTableField, String secondTableField) {
        var result = "\nINNER JOIN " + joinTable + " ON "
                + firstTableField + " = " + secondTableField;
        operationMap.put("Join", result);
        return this;
    }

    @Override
    public GifOperations Limit(int count) {
        var result = " TOP (" + count + ") ";
        String selectWithTop = injectTopInSelect(operationMap, result);
        operationMap.put("Select", selectWithTop);
        return this;
    }

    @Override
    public String toString() {
        return build();
    }

    protected boolean checkWhere(Map<String, String> whereMap) {
        return whereMap.values().stream()
                .anyMatch(s -> s.contains("WHERE"));
    }

    protected String whereInFormat(String[] params) {
        String collect = Arrays.stream(params)
                .map(s -> "'" + s + "'")
                .collect(Collectors.joining(", "));
        return "(" + collect + ")";
    }

    protected String injectTopInSelect(Map<String, String> map, String result) {
        String select = "Select";
        String selectQuery = map.get(select);
        return new StringBuilder().append(selectQuery)
                .insert(select.length(), result, 0, result.length()).toString();
    }

    protected String build() {
        StringBuilder sb = new StringBuilder();
        sb.append(operationMap.getOrDefault("Select", ""))
                .append(operationMap.getOrDefault("From", ""))
                .append(operationMap.getOrDefault("Join", ""));
        whereMap.values().forEach(sb::append);
        sb.append(operationMap.getOrDefault("OrderBy", ""))
                .append(operationMap.getOrDefault("Limit", ""));

        return sb.toString();
    }

    public Map<String, String> getOperationMap() {
        return operationMap;
    }

    public Map<String, String> getWhereMap() {
        return whereMap;
    }
}
