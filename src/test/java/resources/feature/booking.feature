Feature: Booking Functionality

  Scenario: Verify user should be able to access booking
    When User send GET request to see all booking
    Then User must get back a valid status code 200

  Scenario: Create new booking and verify that booking is done
    When I create new booking by providing firstName, lastName, totalPrice, depositPaid, checkIn,  checkOut, additionalNeeds
    Then I verify that booking is created with firstName, lastName, totalPrice, depositPaid, checkIn,  checkOut, additionalNeeds in database

  Scenario: Update booking and verify booking is updated
    When I update booking by providing firstName, lastName, totalPrice, depositPaid, checkIn,  checkOut, additionalNeeds
    Then I verify that booking is updated with firstName, lastName, additionalNeeds in database

  Scenario: Delete the booking data and verify its delete from database
    When I delete booking data
    Then I verify that same booking data was deleted by getting data by Id