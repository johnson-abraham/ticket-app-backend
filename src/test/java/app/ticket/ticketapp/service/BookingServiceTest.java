package app.ticket.ticketapp.service;

import app.ticket.ticketapp.bean.Passenger;
import app.ticket.ticketapp.bean.Ticket;
import app.ticket.ticketapp.bean.TicketForm;
import app.ticket.ticketapp.enums.BookingStatus;
import app.ticket.ticketapp.exception.TicketAlreadyBookedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BookingServiceTest {

  @Autowired
  private BookingService bookingService;

  @Autowired
  private TicketService ticketService;

  @Test
  void testTicketBooking() {
    final Ticket ticket = ticketService.getOneUnassignedTicket();

    final TicketForm form = new TicketForm();
    form.setEmail("test");
    form.setSourceStation("London");
    form.setDestinationStation("Paris");
    form.setFirstName("Johnson");
    form.setLastName("Abraham");
    form.setPrice(5);
    form.setSeatId(ticket.getId());

    final Passenger passenger = bookingService.bookTicket(form);

    assertEquals(passenger.getTicket().getId(), ticket.getId());
  }

  @Test
  void testBookingWithBookedTicket() {
    final Ticket ticket = ticketService.getOneUnassignedTicket();
    ticket.setBookingStatus(BookingStatus.BOOKED);

    final TicketForm form = new TicketForm();
    form.setEmail("test");
    form.setSourceStation("London");
    form.setDestinationStation("Paris");
    form.setFirstName("Johnson");
    form.setLastName("Abraham");
    form.setPrice(5);
    form.setSeatId(ticket.getId());

    assertThrows(TicketAlreadyBookedException.class, () -> bookingService.bookTicket(form));
  }
}
