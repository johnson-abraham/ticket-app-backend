package app.ticket.ticketapp.service;

import app.ticket.ticketapp.bean.Passenger;
import app.ticket.ticketapp.bean.Ticket;
import app.ticket.ticketapp.bean.TicketForm;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {

  private final TicketService ticketService;
  private final PassengerService passengerService;

  public BookingService(TicketService ticketService, PassengerService passengerService) {
    this.ticketService = ticketService;
    this.passengerService = passengerService;
  }

  public Passenger bookTicket(TicketForm form) {
    final Ticket ticket = ticketService.addTicketDetails(form);
    final Passenger passenger = new Passenger(UUID.randomUUID().toString(), form.getEmail(), form.getFirstName(), form.getLastName());
    passenger.setTicket(ticket);

    ticketService.updateTicket(ticket);
    passengerService.addPassenger(passenger);

    return passenger;
  }
}
