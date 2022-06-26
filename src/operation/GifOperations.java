package operation;

public interface GifOperations {

    GifOperations Select(String... fields);
    GifOperations Query(String from);
    GifOperations OrderBy(String... params);
    GifOperations WhereNotNull(String fieldNotNull);
    GifOperations WhereIn(String field, String[] params);
    GifOperations Join(String joinTable, String firstTableField, String secondTableField);
    GifOperations Limit(int count);
}
