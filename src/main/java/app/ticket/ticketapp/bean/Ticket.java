package app.ticket.ticketapp.bean;

import app.ticket.ticketapp.enums.BookingStatus;

public class Ticket {
  private final String id;
  private String sourceStation;
  private String destinationStation;
  private String section;
  private int seatNumber;
  private double price;
  private BookingStatus bookingStatus;

  public Ticket(String id) {
    this.id = id;
    this.bookingStatus = BookingStatus.AVAILABLE;
  }

  public String getId() {
    return id;
  }

  public String getSourceStation() {
    return sourceStation;
  }

  public void setSourceStation(String sourceStation) {
    this.sourceStation = sourceStation;
  }

  public String getDestinationStation() {
    return destinationStation;
  }

  public void setDestinationStation(String destinationStation) {
    this.destinationStation = destinationStation;
  }

  public String getSection() {
    return section;
  }

  public void setSection(String section) {
    this.section = section;
  }

  public int getSeatNumber() {
    return seatNumber;
  }

  public void setSeatNumber(int seatNumber) {
    this.seatNumber = seatNumber;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public BookingStatus getBookingStatus() {
    return bookingStatus;
  }

  public void setBookingStatus(BookingStatus bookingStatus) {
    this.bookingStatus = bookingStatus;
  }

  public static void copy(Ticket source, Ticket target) {
    target.setSourceStation(source.getSourceStation());
    target.setDestinationStation(source.getDestinationStation());
    target.setPrice(source.getPrice());
    target.setBookingStatus(source.getBookingStatus());
  }
}

