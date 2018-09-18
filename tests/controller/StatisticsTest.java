package controller;


import model.helperObjects.MovieRating;
import model.helperObjects.MovieView;
import model.primary.customer.CustomerInfo;
import model.primary.customer.EAgeRange;
import model.primary.movie.MovieInfo;
import model.primary.rating.RatingInfo;
import org.junit.Before;
import org.junit.Test;
import util.FileParsing.FileParser;
import util.mapping.CustomerMapper;
import util.mapping.MovieMapper;
import util.mapping.RatingsMapper;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.*;
public class StatisticsTest {
    private Statistics statistics;

    @Before
    public void setUp() {
        MovieInfo movieInfo = getMovieInfo();
        CustomerInfo customerInfo = getCustomerInfo();
        RatingInfo ratingInfo = getRatingInfoForQuestion1();


        this.statistics = new Statistics(customerInfo,
                movieInfo,
                ratingInfo);
    }

    @Test
    public void movies_topNMostViewed() {
        //setup
        MovieInfo movieInfo = getMovieInfo();
        CustomerInfo customerInfo = getCustomerInfo();
        RatingInfo ratingInfo = getRatingInfoForQuestion1();

       
        Statistics statistics = new Statistics(customerInfo,
                movieInfo,
                ratingInfo);

        List<MovieView> expected = new ArrayList<>();
        expected.add(new MovieView(4, 5));
        expected.add(new MovieView(7, 3));

        assertEquals(expected, statistics.getTopViewedMovies(2));
    }

    private RatingInfo getRatingInfoForQuestion1() {
        FileParser ratingParser = getFileParser("mockRatingsForMoviesNMostViewed.dat", "::");
        RatingsMapper ratingsMapper = new RatingsMapper(ratingParser, 4);
     

        return new RatingInfo(ratingsMapper.getCustomerIDMovieIDRatingAndTimeMap());
    }

    private CustomerInfo getCustomerInfo() {
        FileParser customerParser = getFileParser("mockCustomers.dat", "::");
        CustomerMapper customerMapper = new CustomerMapper(customerParser, 5);
        return new CustomerInfo(customerMapper.getIdCustomerMap());
    }

    private MovieInfo getMovieInfo() {
        FileParser movieParser = getFileParser("mockMovies.dat", "::");
        MovieMapper movieMapper = new MovieMapper(movieParser, 3);
        return new MovieInfo(movieMapper.getIdMovieMap());
    }

//    private List<MovieView> getExpectedTop2MovieList() {
//        List<MovieView> expected = new ArrayList<>();
//        expected.add(new MovieView(4, 5));
//        expected.add(new MovieView(7, 3));
//        return expected;
//    }
//
//    private List<MovieView> getExpectedTop4MovieList() {
//        List<MovieView> expected = new ArrayList<>();
//        expected.add(new MovieView(4, 5));
//        expected.add(new MovieView(7, 3));
//        expected.add(new MovieView(1, 2));
//        return expected;
//    }


    private FileParser getFileParser(String fileName, String parseToken) {
        FileParser fileParser = new FileParser(fileName, parseToken);
        return fileParser;
    }

    @Test
    public void mostViewedMovies_whenCountExceedsLimit() {

        CustomerInfo customerInfo = getCustomerInfo();
        RatingInfo ratingInfo = getRatingInfoForQuestion1();
        MovieInfo movieInfo = getMovieInfo();


        Statistics statistics = new Statistics(customerInfo, movieInfo, ratingInfo);
        List<MovieView> expected = new ArrayList<>();
        expected.add(new MovieView(4, 5));
        expected.add(new MovieView(7, 3));
        expected.add(new MovieView(1, 2));

        assertEquals(expected, statistics.getTopViewedMovies(10));

    }

//    private RatingInfo getRatingInfo() {
//        FileParser ratingParser = getFileParser("C:\\Users\\Chiranjeev\\Desktop\\MyCode\\Competitive\\Fragma  Data 2017 movies pre interview assignment ( Entry Level Java Developer Role ) TDD\\src\\mockObjects\\mockMovies.dat", "::");
//        RatingsMapper ratingsMapper = new RatingsMapper(ratingParser, 4);
//        RatingInfo ratingInfo = new RatingInfo(ratingsMapper.getCustomerIDMovieIDRatingAndTimeMap());
//        return ratingInfo;
//    }

    @Test
    public void ratingList_top3RatedMoviesWithMin2Views() {
        List<MovieRating> expected = new ArrayList<>();
        expected.add(new MovieRating(7, 13.0 / 3, 3));
        expected.add(new MovieRating(1, 8.0 / 2, 2));
        expected.add(new MovieRating(4, 20.0 / 5, 5));


        assertEquals(expected, statistics.getTopRatedMovies(3, 2));
    }




}