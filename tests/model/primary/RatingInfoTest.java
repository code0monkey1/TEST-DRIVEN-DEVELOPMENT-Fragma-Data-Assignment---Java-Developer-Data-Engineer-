package model.primary;

import model.primary.customer.CustomerInfo;
import model.primary.customer.EAgeRange;
import model.primary.rating.RatingInfo;
import org.junit.Before;
import org.junit.Test;
import util.FileParsing.FileParser;
import util.mapping.CustomerMapper;
import util.mapping.RatingsMapper;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RatingInfoTest {
    private FileParser fileParser;
    private RatingInfo ratingInfo;

    @Before
    public void setUp() {
        fileParser = new FileParser("mockRatingsForMoviesNMostViewed.dat", "::");

        RatingsMapper ratingsMapper = new RatingsMapper(fileParser, 4);
        ratingInfo = new RatingInfo(ratingsMapper.getCustomerIDMovieIDRatingAndTimeMap());
    }

    @Test
    public void movieViewCountMap_isValid() {


        RatingsMapper ratingsMapper = new RatingsMapper(this.fileParser, 4);
        RatingInfo ratingInfo = new RatingInfo(ratingsMapper.getCustomerIDMovieIDRatingAndTimeMap());

        Map<Integer, Integer> expected = new HashMap<>();

        expected.put(4, 5);
        expected.put(7, 3);
        expected.put(1, 2);

//        System.out.println(expected);
//        System.out.println(ratingInfo.getMovieIdViewsMap());
        assertEquals(expected, ratingInfo.getMovieIdViewsMap());

    }

    @Test
    public void movieRatingMap_isValid() {
        Map<Integer, Integer> expectedIdRating = returnMovieRatingMapByParsingFile();


        RatingsMapper ratingsMapper = new RatingsMapper(this.fileParser, 4);
        RatingInfo ratingInfo = new RatingInfo(ratingsMapper.getCustomerIDMovieIDRatingAndTimeMap());

//        System.out.println(ratingInfo.getMovieIdRatingsMap());
//        System.out.println(expectedIdRating);
        assertEquals(expectedIdRating, ratingInfo.getMovieIdRatingsMap());

    }

    private Map<Integer, Integer> returnMovieRatingMapByParsingFile() {
        FileParser ratingFileParser = this.fileParser;
        List<List<String>> lists = ratingFileParser.getRawList();
        Map<Integer, Integer> movieIdRating = new HashMap<>();

        for (List<String> list : lists) {
            int id = Integer.parseInt(list.get(1));
            int rating = Integer.parseInt(list.get(2));

            int previousRating = movieIdRating.getOrDefault(id, 0);
            movieIdRating.put(id, previousRating + rating);
        }
        return movieIdRating;
    }

    @Test
    public void movieViewCountLesserThanMovieRatingCount() {
        RatingsMapper ratingsMapper = new RatingsMapper(this.fileParser, 4);
        RatingInfo ratingInfo = new RatingInfo(ratingsMapper.getCustomerIDMovieIDRatingAndTimeMap());

        int expectedViews = returnMovieViewMapByParsingFile().get(4);
        int expectedRating = returnMovieRatingMapByParsingFile().get(4);

        int resultViews = ratingInfo.getMovieIdViewsMap().get(4);
        int resultRatings = ratingInfo.getMovieIdRatingsMap().get(4);
        System.out.println(expectedViews + "::" + resultViews);
        System.out.println(expectedRating + "::" + resultRatings);
        assertEquals(expectedViews, resultViews);
        assertEquals(expectedRating, resultRatings);
    }

    private FileParser returnFileParser() {

        FileParser fileParser = new FileParser("mockRatings.dat", "::");
        assertNotNull(fileParser);
        return fileParser;
    }

    private Map<Integer, Integer> returnMovieViewMapByParsingFile() {

        List<List<String>> entryLists = fileParser.getRawList();
        Map<Integer, Integer> movieIdViews = new HashMap<>();

        for (List<String> entry : entryLists) {
            if (entry.size() < 4) continue;
            int movieId = Integer.parseInt(entry.get(1));
            int count = movieIdViews.getOrDefault(movieId, 0);
            movieIdViews.put(movieId, count + 1);
        }
        assertNotNull(movieIdViews);
        return movieIdViews;
    }

    @Test
    public void movieIdAgeRangeMap_isValid() {


    }

    @Test
    public void rangeEnumMap_isValid() {

        CustomerInfo customerInfo = new CustomerInfo(new CustomerMapper(new FileParser("users.dat", "::"), 5).getIdCustomerMap());

        Map<Integer, EnumMap<EAgeRange, Integer>> movieIDAgeRange = new HashMap<>();

        List<List<String>> entries = this.fileParser.getRawList();

        for (List<String> entry : entries) {

            int userID = Integer.parseInt(entry.get(0));
            int movieID = Integer.parseInt(entry.get(1));

            EAgeRange userAgeRange = customerInfo.getAgeRange(userID);

            EnumMap<EAgeRange, Integer> ageRangeMap = returnAgeRangeMap(movieIDAgeRange, movieID);

            int presentAgeRangeCount = returnPresentAgeRangeCount(userAgeRange, ageRangeMap);

            ageRangeMap.put(userAgeRange, presentAgeRangeCount + 1);
            movieIDAgeRange.put(movieID, ageRangeMap);

        }
        assertEquals(movieIDAgeRange, ratingInfo.getMovieIdAgeRangeMap(customerInfo));
    }

    private int returnPresentAgeRangeCount(EAgeRange userAgeRange, EnumMap<EAgeRange, Integer> ageRangeMap) {
        return ageRangeMap.getOrDefault(userAgeRange, 0);
    }

    private EnumMap<EAgeRange, Integer> returnAgeRangeMap(Map<Integer, EnumMap<EAgeRange, Integer>> movieIDAgeRange, int movieID) {
        return movieIDAgeRange.getOrDefault(movieID, new EnumMap<>(EAgeRange.class));
    }


}