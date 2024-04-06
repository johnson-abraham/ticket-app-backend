package app.ticket.ticketapp.service;

import app.ticket.ticketapp.bean.EmptyTicketResponse;
import app.ticket.ticketapp.bean.SeatUpdateRequest;
import app.ticket.ticketapp.bean.Ticket;
import app.ticket.ticketapp.bean.TicketForm;
import app.ticket.ticketapp.enums.BookingStatus;
import app.ticket.ticketapp.exception.TicketAlreadyBookedException;
import app.ticket.ticketapp.exception.TicketNotFoundException;
import app.ticket.ticketapp.exception.TrainFullyBookedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class TicketService {
  private final Map<String, Ticket> tickets;

  public TicketService() {
    this.tickets = Stream.concat(generateTickets("A"), generateTickets("B")).collect(Collectors.toMap(Ticket::getId, ticket -> ticket));
  }

  public Ticket getOneUnassignedTicket() {
    return tickets.values().stream().filter(ticket -> ticket.getBookingStatus() == BookingStatus.AVAILABLE).findFirst().orElseThrow(TrainFullyBookedException::new);
  }

  public List<EmptyTicketResponse> getAvailableTickets() {
    return tickets.values().stream().filter(ticket -> ticket.getBookingStatus() == BookingStatus.AVAILABLE).map(EmptyTicketResponse::fromTicket).collect(Collectors.toList());
  }

  public Ticket getTicketById(String id) {
    if (tickets.containsKey(id)) {
      return tickets.get(id);
    } else {
      throw new TicketNotFoundException("Ticket with ID %s is not found".formatted(id));
    }
  }

  public Ticket updateTicket(Ticket ticket) {
    if (tickets.containsKey(ticket.getId())) {
      tickets.put(ticket.getId(), ticket);
      return ticket;
    } else {
      throw new TicketNotFoundException("Ticket with ID %s is not found".formatted(ticket.getId()));
    }
  }

  public boolean releaseTicket(String id) {
    if (tickets.containsKey(id)) {
      final Ticket ticket = new Ticket(id);
      tickets.put(id, ticket);
      return true;
    } else {
      throw new TicketNotFoundException("Ticket with ID %s is not found".formatted(id));
    }
  }

  public Ticket updateTicket(SeatUpdateRequest seatUpdateRequest) {
    final Ticket newTicket = getTicketById(seatUpdateRequest.getNewSeatId());
    final Ticket oldTicket = getTicketById(seatUpdateRequest.getOldSeatId());

    Ticket.copy(oldTicket, newTicket);
    releaseTicket(seatUpdateRequest.getOldSeatId());

    return newTicket;
  }

  public Ticket addTicketDetails(TicketForm form) {
    final Ticket ticket = getTicketById(form.getSeatId());

    if (ticket.getBookingStatus() == BookingStatus.BOOKED) {
      throw new TicketAlreadyBookedException("Ticket with ID %s is already booked".formatted(ticket.getId()));
    }

    ticket.setSourceStation(form.getSourceStation());
    ticket.setDestinationStation(form.getDestinationStation());
    ticket.setPrice(form.getPrice());
    ticket.setBookingStatus(BookingStatus.BOOKED);

    return ticket;
  }

  private Stream<Ticket> generateTickets(String section) {
    return IntStream.rangeClosed(1, 30).mapToObj(num -> {
      final Ticket ticket = new Ticket(UUID.randomUUID().toString());
      ticket.setSection(section);
      ticket.setSeatNumber(num);

      return ticket;
    });
  }
}
