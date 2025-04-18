package com.zapcom.ReviewProcessor.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zapcom.ReviewProcessor.Entity.Review;
import com.zapcom.ReviewProcessor.Repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class ReviewService {

    @Value("${serpapi.key}")
    private String apiKey;

    @Value("${serpapi.base.api.url}")
    private String BASE_API_URL;

    private String apiUrl;
    @Autowired
    private ReviewRepository reviewRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private Logger logger= LoggerFactory.getLogger(ReviewService.class);

    @Scheduled(fixedRate = 12*60 * 60 * 1000) // every 12 hours
    public void scheduledReviewFetch()  {
        logger.info("Scheduler triggered at: {}", LocalDateTime.now());
        try {
            fetchAndSaveReviews();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void fetchAndSaveReviews() throws JsonProcessingException {
        String nextPageToken = null;
        apiUrl = BASE_API_URL + "&api_key=" + apiKey;
        do {
                String response = restTemplate.getForObject(apiUrl, String.class);
                JsonNode root = objectMapper.readTree(response);
                JsonNode reviews = root.path("reviews");
                if (reviews.isArray()) {
                    for (JsonNode reviewNode : reviews) {
                        String review_id= reviewNode.path("review_id").asText();
                        Review review1=reviewRepository.findByReviewId(review_id);
                        if(review1==null){
                            String reviewer = reviewNode.path("user").path("name").asText(); // fixed user.name
                            String content = reviewNode.path("snippet").asText("");
                            int rating = reviewNode.path("rating").asInt();
                            String isoDateStr = reviewNode.path("iso_date").asText();
                            LocalDateTime reviewedAt = LocalDateTime.parse(isoDateStr, DateTimeFormatter.ISO_DATE_TIME);
                            String category;
                            if (rating > 3) {
                                category = "Good";
                            } else if (rating == 3) {
                                category = "Average";
                            } else {
                                category = "Bad";
                            }
                            Review review = new Review(review_id, reviewer, content, rating, reviewedAt,category);
                            reviewRepository.save(review);
                        }
                        else{
                            logger.info("review already saved in database");
                        }
                    }
                }
                JsonNode nextPageNode = root.path("serpapi_pagination").path("next_page_token");
                nextPageToken = nextPageNode.isMissingNode() || nextPageNode.asText().isEmpty() ? null : nextPageNode.asText();
                if (nextPageToken != null) {
                    apiUrl = BASE_API_URL + "&api_key=" + apiKey + "&next_page_token=" + nextPageToken;
                }
                logger.info("Fetched and saved reviews at : {}", LocalDateTime.now());
        } while (nextPageToken != null);
    }

    public List<Review> fecthAllReviews() {
      return reviewRepository.findAll();
    }


    public List<Review> getReviewsByCategory(String type) {
        return switch (type.toLowerCase()) {
            case "good" -> reviewRepository.findByRatingGreaterThanEqual(4);
            case "avg"  -> reviewRepository.findByRating(3);
            case "bad"  -> reviewRepository.findByRatingLessThanEqual(2);
            default     -> List.of(); // empty list for invalid category
        };
    }

    public List<Review> getLatestGoodReviews() {
        return reviewRepository.findTop10ByRatingGreaterThanEqualOrderByReviewedAtDesc(4);
    }

    public List<Review> getLatestBadReviews() {
        return reviewRepository.findTop10ByRatingLessThanEqualOrderByReviewedAtDesc(2);
    }

    public List<Review> getLatestAvgReviews() {
        return reviewRepository.findTop10ByRatingOrderByReviewedAtDesc(3);
    }
}
