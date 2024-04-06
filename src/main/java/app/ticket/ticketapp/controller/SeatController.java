package app.ticket.ticketapp.controller;

import app.ticket.ticketapp.bean.*;
import app.ticket.ticketapp.exception.ErrorResponse;
import app.ticket.ticketapp.exception.TicketAlreadyBookedException;
import app.ticket.ticketapp.service.BookingService;
import app.ticket.ticketapp.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
@CrossOrigin("*")
public class SeatController {

  private final TicketService ticketService;
  private final BookingService bookingService;

  public SeatController(TicketService ticketService, BookingService bookingService) {
    this.ticketService = ticketService;
    this.bookingService = bookingService;
  }

  @GetMapping("/getAvailableSeats")
  public List<EmptyTicketResponse> getAvailableSeats() {
    return ticketService.getAvailableTickets();
  }

  @PostMapping("/bookTicket")
  public Passenger bookTicket(@RequestBody TicketForm form) {
    return bookingService.bookTicket(form);
  }

  @ExceptionHandler(TicketAlreadyBookedException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(TicketAlreadyBookedException exception) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setErrorCode(HttpStatus.FORBIDDEN);
    errorResponse.setErrorMessage(exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }
}
