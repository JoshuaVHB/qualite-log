package reserve.T_model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import reserve.controller.ReservationController;
import reserve.model.Material;
import reserve.model.Reservation;
import reserve.model.User;

import java.time.LocalDate;

public class T_Reservation {


    // ---------------------- makeReservation() ------------------------- //

    @Test
    public void should_throw_exception_when_field_is_null_owner() {
        Assertions.assertThrows(NullPointerException.class, () -> {
           ReservationController.makeReservation(null, new Material(), LocalDate.now(), LocalDate.now().plusDays(1));
        });
    }

    @Test
    public void should_throw_exception_when_field_is_null_owned() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            ReservationController.makeReservation(new User(), null, LocalDate.now(), LocalDate.now().plusDays(1));
        });
    }

    @Test
    public void should_throw_exception_when_field_is_null_from() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            ReservationController.makeReservation(new User(), new Material(), null, LocalDate.now().plusDays(1));
        });
    }

    @Test
    public void should_throw_exception_when_field_is_null_to() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            ReservationController.makeReservation(new User(), new Material(), LocalDate.now(), null);
        });
    }

    @Test
    public void should_throw_exception_when_owned_is_already_owned() {}

    @Test
    public void should_throw_exception_when_from_is_after_to() {}

    @Test
    public void should_throw_exception_when_to_is_after_today() {}

    @Test
    public void should_add_incoming_reservation_to_current_when_begin_date_is_today() {}

    @Test
    public void should_remove_reservation_from_incoming_when_begin_date_is_today() {}


}
