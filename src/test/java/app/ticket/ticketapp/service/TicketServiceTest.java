package app.ticket.ticketapp.service;

import app.ticket.ticketapp.bean.*;
import app.ticket.ticketapp.enums.BookingStatus;
import app.ticket.ticketapp.exception.TicketAlreadyBookedException;
import app.ticket.ticketapp.exception.TicketNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TicketServiceTest {

  @Autowired
  private PassengerService passengerService;
  @Autowired
  private TicketService ticketService;
  @Autowired
  private BookingService bookingService;

  @Test
  void testAvailableTickets() {
    final Ticket ticket = ticketService.getOneUnassignedTicket();
    ticket.setBookingStatus(BookingStatus.BOOKED);

    final List<EmptyTicketResponse> availableTickets = ticketService.getAvailableTickets();
    availableTickets.forEach(t -> assertNotEquals(t.getId(), ticket.getId()));
  }

  @Test
  void getTicketByIdSuccess() {
    final Ticket ticket = ticketService.getOneUnassignedTicket();
    final Ticket foundTicket = ticketService.getTicketById(ticket.getId());

    assertEquals(ticket, foundTicket);
  }

  @Test
  void getTicketByIdNotFound() {
    assertThrows(TicketNotFoundException.class, () -> ticketService.getTicketById("not-found-id"));
  }

  @Test
  void updateTicketSuccess() {
    final Ticket ticket = ticketService.getOneUnassignedTicket();
    ticket.setSection("A");

    ticketService.updateTicket(ticket);

    assertEquals(ticket, ticketService.getTicketById(ticket.getId()));
  }

  @Test
  void updateTicketNotFound() {
    final Ticket ticket = new Ticket("not-found-id");
    ticket.setSection("A");

    assertThrows(TicketNotFoundException.class, () -> ticketService.updateTicket(ticket));
  }

  @Test
  void releaseTicket() {
    final Ticket ticket = ticketService.getOneUnassignedTicket();
    ticket.setBookingStatus(BookingStatus.BOOKED);

    ticketService.releaseTicket(ticket.getId());

    assertEquals(BookingStatus.AVAILABLE, ticketService.getTicketById(ticket.getId()).getBookingStatus());
  }

  @Test
  void releaseTicketNotFound() {
    assertThrows(TicketNotFoundException.class, () -> ticketService.releaseTicket("not-found-id"));
  }

  @Test
  void updateTicketRequest() {
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

    final Ticket newTicket = ticketService.getOneUnassignedTicket();

    final SeatUpdateRequest seatUpdateRequest = new SeatUpdateRequest();
    seatUpdateRequest.setOldSeatId(ticket.getId());
    seatUpdateRequest.setNewSeatId(newTicket.getId());
    seatUpdateRequest.setPassengerId(passenger.getId());

    final Ticket updatedTicket = ticketService.updateTicket(seatUpdateRequest);

    assertEquals(newTicket, updatedTicket);
  }

  @Test
  void addTicketDetails() {
    final Ticket ticket = ticketService.getOneUnassignedTicket();

    final TicketForm form = new TicketForm();
    form.setEmail("test");
    form.setSourceStation("London");
    form.setDestinationStation("Paris");
    form.setFirstName("Johnson");
    form.setLastName("Abraham");
    form.setPrice(5);
    form.setSeatId(ticket.getId());

    final Ticket addedTicket = ticketService.addTicketDetails(form);

    assertEquals(BookingStatus.BOOKED, addedTicket.getBookingStatus());
    assertEquals(5, addedTicket.getPrice());
    assertEquals("London", addedTicket.getSourceStation());
    assertEquals("Paris", addedTicket.getDestinationStation());
  }

  @Test
  void addTicketDetailsAlreadyBooked() {
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

    assertThrows(TicketAlreadyBookedException.class, () -> ticketService.addTicketDetails(form));
  }
}
