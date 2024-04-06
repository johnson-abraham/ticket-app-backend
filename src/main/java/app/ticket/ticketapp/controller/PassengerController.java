package app.ticket.ticketapp.controller;

import app.ticket.ticketapp.bean.Passenger;
import app.ticket.ticketapp.bean.PassengerDeleteResponse;
import app.ticket.ticketapp.bean.SeatUpdateRequest;
import app.ticket.ticketapp.exception.ErrorResponse;
import app.ticket.ticketapp.exception.PassengerNotFoundException;
import app.ticket.ticketapp.service.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/passenger")
public class PassengerController {

  private final PassengerService passengerService;

  public PassengerController(PassengerService passengerService) {
    this.passengerService = passengerService;
  }

  @GetMapping("/{email}")
  public List<Passenger> getPassengerByEmail(@PathVariable String email) {
    return passengerService.getPassengerByEmail(email);
  }

  @DeleteMapping("/{id}")
  public PassengerDeleteResponse deletePassenger(@PathVariable String id) {
    return new PassengerDeleteResponse(passengerService.removePassenger(id), id);
  }

  @PatchMapping("/updateSeat")
  public Passenger updateTicket(@RequestBody SeatUpdateRequest seatUpdateRequest) {
    return passengerService.updatePassengerSeatDetails(seatUpdateRequest);
  }

  @ExceptionHandler(PassengerNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(PassengerNotFoundException exception) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setErrorCode(HttpStatus.NOT_FOUND);
    errorResponse.setErrorMessage(exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }
}
