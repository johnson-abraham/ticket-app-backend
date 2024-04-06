package app.ticket.ticketapp.service;

import app.ticket.ticketapp.bean.Passenger;
import app.ticket.ticketapp.bean.Ticket;
import app.ticket.ticketapp.bean.TicketForm;
import app.ticket.ticketapp.enums.BookingStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PassengerServiceTest {

  @Autowired
  private PassengerService passengerService;

  @Autowired
  private TicketService ticketService;

  @Autowired
  private BookingService bookingService;

  private Passenger passenger;
  private Ticket ticket;

  @BeforeEach
  void init() {
    ticket = ticketService.getOneUnassignedTicket();

    final TicketForm form = new TicketForm();
    form.setEmail("test");
    form.setSourceStation("London");
    form.setDestinationStation("Paris");
    form.setFirstName("Johnson");
    form.setLastName("Abraham");
    form.setPrice(5);
    form.setSeatId(ticket.getId());

    passenger = bookingService.bookTicket(form);
  }

  @Test
  void testRemovePassenger() {
    assertEquals(ticket.getBookingStatus(), BookingStatus.BOOKED);

    passengerService.removePassenger(passenger.getId());

    assertEquals(ticketService.getTicketById(ticket.getId()).getBookingStatus(), BookingStatus.AVAILABLE);
  }

  @Test
  void getPassengersByEmailSuccess() {
    assertEquals(passengerService.getPassengerByEmail("test").getFirst(), passenger);
  }

  @Test
  void getPassengersByEmailNotFound() {
    assertEquals(passengerService.getPassengerByEmail("hello").size(), 0);
  }
}
