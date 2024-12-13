package vttp.batch5.ssf.noticeboard.services;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import vttp.batch5.ssf.noticeboard.models.notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository noticerepo;

	private final String API_URL = "https://publishing-production-d35a.up.railway.app/notice";

	// TODO: Task 3
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	public String[] postToNoticeServer(notice noticeTemp) throws ParseException {

		String postDate = noticeTemp.getPostDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date storeDate = sdf.parse(postDate);
        long dateSeconds = storeDate.getTime();

        JsonObjectBuilder objBuilder = Json.createObjectBuilder();
        objBuilder = objBuilder
			.add("title", (noticeTemp.getTitle()))
            .add("poster", (noticeTemp.getPoster()))
            .add("postDate", (dateSeconds));

        JsonArray categories = Json.createArrayBuilder().add(noticeTemp.getCategories()[0]).build();
    
        for(int i = 1; i < noticeTemp.getCategories().length; i++) {

            categories = Json.createArrayBuilder()
                .add(noticeTemp.getCategories()[i])
                .build();
        }

       		objBuilder = objBuilder
            .add("categories", categories);

			JsonObject noticeJson = objBuilder.add("text", noticeTemp.getText()).build();

		RequestEntity<String> req = RequestEntity
            .post(API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(noticeJson.toString(), String.class);

			RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);
        String payload = resp.getBody();
        JsonReader reader = Json.createReader(new StringReader(payload));

        JsonObject result = reader.readObject();

		String noticeId = null;
		String errorMessage = null;

		if (!result.getString("id").equals(null)) {
			noticeId = result.getString("id");
		} else {
			errorMessage = result.getString("message");
		}

	  if(!noticeId.equals(null)) {
		noticerepo.insertNotices(noticeId, noticeJson);
		String[] message = new String[2];
		message[0] = "0";
		message[1] = noticeId;
		return message;
	  } else {
		String[] message = new String[2];
		message[0] = "1";
		message[1] = errorMessage;
		return message;
	  }
		
	}

	public boolean getRandomKey() {

		Optional<String> key = noticerepo.randomKey();

		if (key.isEmpty()) {

			return false;
		} else {
			return true;
		}
	}
}
