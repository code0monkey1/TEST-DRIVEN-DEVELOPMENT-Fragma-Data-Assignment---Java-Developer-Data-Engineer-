package model;

import model.primary.customer.AgeRange;
import model.primary.customer.Gender;
import model.primary.customer.Occupation;
import model.primary.customer.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {
    private Customer customer;

    @Before
    public void setUp()  {
        ////UserID::Gender::Age::Occupation::Zip-code
        customer = new Customer("234", "F", "35", "5", "1234");
    }

    @Test(expected = NumberFormatException.class)
    public void id_notNumber() {
        customer = new Customer("234an", "M", "35", "5", "1234");

    }

    @Test(expected = IllegalArgumentException.class)
    public void gender_undefinedGender() {
        customer = new Customer("234", "k", "35", "5", "1234");

    }

    @Test(expected = IllegalArgumentException.class)
    public void id_notInPermissibleRange() {
        // UserIDs range between 1 and 6040
        customer = new Customer("0", "M", "35", "5", "1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ageRange_negativeOrZero() {

        customer = new Customer("0", "M", "0", "5", "1234");
        customer = new Customer("0", "M", "-1", "5", "1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ageRange_undefinedValue() {
        customer = new Customer("1", "M", "15", "5", "1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void occupation_undefinedOccupation() {
        customer = new Customer("1", "M", "15", "-1", "1234");
    }

    @Test
    public void ageRange_assigned() {
        customer = new Customer("1", "M", "18", "12", "1234");
        assertEquals(AgeRange.EIGHTEEN_TO_TWENTY_FOUR, customer.getAgeRange());
        customer = new Customer("1", "M", "1", "12", "1234");
        assertEquals(AgeRange.UNDER_EIGHTEEN, customer.getAgeRange());
    }

    @Test
    public void gender_assigned() {
        customer = new Customer("1", "M", "18", "12", "1234");
        assertEquals(Gender.MALE, customer.getGender());
        customer = new Customer("1", "F", "18", "12", "1234");
        assertEquals(Gender.FEMALE, customer.getGender());
    }

    @Test
    public void occupation_assigned() {
        customer = new Customer("1", "M", "18", "12", "1234");
        assertEquals(Occupation.PROGRAMMER, customer.getOccupation());
        customer = new Customer("1", "M", "18", "16", "1234");
        assertEquals(Occupation.SELF_EMPLOYED, customer.getOccupation());
    }

    @Test
    public void id_assigned() {
        customer = new Customer("1", "M", "18", "12", "1234");
        assertEquals(1, customer.getId());
    }

    @Test
    public void zipCode_assigned(){
        customer = new Customer("1", "M", "18", "12", "1234");
        assertEquals("1234",customer.getZipCode());
    }

}