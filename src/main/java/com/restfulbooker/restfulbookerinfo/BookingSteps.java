package com.restfulbooker.restfulbookerinfo;

import com.restfulbooker.constants.EndPoints;
import com.restfulbooker.model.BookingDates;
import com.restfulbooker.model.BookingPojo;
import com.restfulbooker.utils.TestUtils;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.ArrayList;
import java.util.HashMap;


public class BookingSteps {

        @Step("Getting All Booking information")
        public ValidatableResponse getAllBookingInfo() {
            return SerenityRest
                    .given()
                    .when()
                    .get(EndPoints.GET_ALL_BOOKINGS)
                    .then();
        }
        @Step("Creating Booking with firstName : {0}, lastName: {1}, totalPrice {2} ,depositPaid {3} ,bookingDate {4}, additionalNeeds {5}")
        public ValidatableResponse createBooking(String firstName, String lastName, int totalPrice, Boolean depositPaid, String checkIn, String checkOut, String additionalNeeds) {
            BookingDates bookingDates = BookingDates.getBookingDates(checkIn, checkOut);
            BookingPojo requestBody = BookingPojo.getBookingPojo(firstName, lastName, totalPrice, depositPaid, bookingDates, additionalNeeds);
            return SerenityRest.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(TestUtils.jsonToString(requestBody))
                    .when()
                    .post()
                    .then();
        }

        @Step("Getting Booking with firstName: {0}")
        public ArrayList<HashMap<String, Object>> getBookingByFirstname(String firstName) {
            String p1 = "findAll{it.firstname='";
            String p2 = "'}";
            return SerenityRest.given().log().all()
                    .when()
                    .get(EndPoints.GET_ALL_BOOKINGS_BY_NAME + firstName)
                    .then()
                    .statusCode(200)
                    .extract()
                    .path(p1 + firstName + p2);
        }

        @Step("Updating Booking information with bookingID: {0}, firstName: {1}, lastName: {2}, totalPrice: {3}, depositPaid: {4} and bookingDate: {5},additionalNeeds: {6}")
        public ValidatableResponse updateBooking(int bookingID, String firstName, String lastName, int totalPrice, Boolean depositPaid, String checkIn, String checkOut, String additionalNeeds) {
            BookingDates bookingDates = BookingDates.getBookingDates(checkIn, checkOut);
            BookingPojo bookingPojo = BookingPojo.getBookingPojo(firstName, lastName, totalPrice, depositPaid, bookingDates, additionalNeeds);

            //To get Token after Authentication
            PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme(); // create object of PreemptiveBasicAuthScheme class
            authScheme.setUserName("admin"); // assign the username to object
            authScheme.setPassword("password123"); // assign the password to object
            RestAssured.authentication=authScheme; // assign the object to RestAssured.authentication class

            return SerenityRest.given().log().all()
                    .header("Content-Type", "application/json")
                    .pathParam("bookingID", bookingID)
                    .body(bookingPojo)
                    .when()
                    .put(EndPoints.UPDATE_BOOKING_BY_ID)
                    .then();
        }

        @Step("Deleting booking information with BookingID: {0}")
        public ValidatableResponse deleteBooking(int bookingID) {
            //To get Token after Authentication
            PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme(); // create object of PreemptiveBasicAuthScheme class
            authScheme.setUserName("admin"); // assign the username to object
            authScheme.setPassword("password123"); // assign the password to object
            RestAssured.authentication=authScheme; // assign the object to RestAssured.authentication class

            return SerenityRest
                    .given()
                    .pathParam("bookingID", bookingID)
                    .when()
                    .delete(EndPoints.DELETE_BOOKING_BY_ID)
                    .then();
        }

        @Step("Getting booking information with BookingId: {0}")
        public ValidatableResponse getBookingById(int bookingID) {
            return SerenityRest
                    .given()
                    .pathParam("bookingID", bookingID)
                    .when()
                    .get(EndPoints.GET_SINGLE_BOOKING_BY_ID)
                    .then();
        }
}
