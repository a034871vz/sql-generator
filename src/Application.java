import impl.*;
import operation.GifOperations;

public class Application {
    public static void main(String[] args) {
        GifOperations db = new SqlServerOperationsImpl();

        var query = db.Query("Books")
                .Select("User.Id", "Name")
                .OrderBy("PublishDate")
                .WhereNotNull("AuthorId")
                .WhereIn("Lang", new String[] {"en", "fr"})
                .Join("Authors", "Authors.Id", "Book.AuthorId")
                .Limit(5);

        System.out.println(query);
    }
}
