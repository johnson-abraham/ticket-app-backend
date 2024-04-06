package app.ticket.ticketapp.service;

import app.ticket.ticketapp.bean.Passenger;
import app.ticket.ticketapp.bean.SeatUpdateRequest;
import app.ticket.ticketapp.bean.Ticket;
import app.ticket.ticketapp.exception.PassengerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerService {
  private final TicketService ticketService;

  private List<Passenger> passengers;

  public PassengerService(TicketService ticketService) {
    this.ticketService = ticketService;
    passengers = new ArrayList<>();
  }

  public boolean addPassenger(Passenger passenger) {
    return passengers.add(passenger);
  }

  public boolean removePassenger(String id) {
    Passenger passenger = passengers.stream().filter(p -> p.getId().equals(id)).findFirst().orElseThrow(() -> new PassengerNotFoundException("Passenger with ID %s is not found".formatted(id)));
    ticketService.releaseTicket(passenger.getTicket().getId());
    return passengers.remove(passenger);
  }

  public List<Passenger> getPassengerByEmail(String email) {
    return passengers.stream().filter(passenger -> passenger.getEmail().equals(email)).collect(Collectors.toList());
  }

  public Passenger updatePassengerSeatDetails(SeatUpdateRequest seatUpdateRequest) {
    final Ticket ticket = ticketService.updateTicket(seatUpdateRequest);
    final Passenger passenger = passengers.stream().filter(p -> p.getId().equals(seatUpdateRequest.getPassengerId())).findFirst().orElseThrow(() -> new PassengerNotFoundException("Passenger with ticket ID %s is not found".formatted(ticket.getId())));

    passenger.setTicket(ticket);

    return passenger;
  }
}
