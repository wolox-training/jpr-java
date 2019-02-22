package wolox.training.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import wolox.training.models.Book;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class OpenLibraryService {

    private String urlBase = "https://openlibrary.org/api";

    public OpenLibraryService(){}

    public String getResponseExternalApi(String externalApi) throws IOException {
        URL urlPath = new URL(externalApi);
        HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
        connection.setRequestMethod("GET");
        StringBuffer response = new StringBuffer();
        String input;

        BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((input = buffer.readLine()) != null) {
            response.append(input);
        }
        buffer.close();
        return response.toString();
    }

    private Book bookParserFromJson(String rawJsonResponse, String isbn) throws JSONException {
        JSONObject jsonRaw = new JSONObject(rawJsonResponse);
        if (jsonRaw.has("ISBN:" + isbn)) {
            JSONObject bookData = jsonRaw.getJSONObject("ISBN:0385472579");
            Book bookParsed = new Book();
            String allAuthorsConcatenated = "";
            String allPublishersConcatenated = "";
            bookParsed.setPages(bookData.getInt("number_of_pages"));
            bookParsed.setYear(bookData.getString("publish_date"));
            bookParsed.setTitle(bookData.getString("title"));
            bookParsed.setSubtitle(bookData.getString("subtitle"));
            bookParsed.setIsbn(isbn);
            JSONArray authors = bookData.getJSONArray("authors");
            for (int i = 0; i < authors.length(); i++) {
                allAuthorsConcatenated += ((JSONObject)authors.get(i)).getString("name") + ".";
            }
            bookParsed.setAuthor(allAuthorsConcatenated);
            JSONArray publishers = bookData.getJSONArray("publishers");
            for (int i = 0; i < publishers.length(); i++) {
                allPublishersConcatenated += ((JSONObject)publishers.get(i)).getString("name") + ".";
            }
            bookParsed.setPublisher(allPublishersConcatenated);
            return bookParsed;
        } else {
            return null;
        }
    }

    public Book renderBookWithExternalApi(String isbn) {
        try {
            String response = getResponseExternalApi(this.urlBase + "/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data");
            Book bookParsed = bookParserFromJson(response, isbn);
            return bookParsed;
        } catch(Exception e) {
            return null;
        }
    }
}
