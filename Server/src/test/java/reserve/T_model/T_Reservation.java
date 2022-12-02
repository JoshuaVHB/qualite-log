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
    public void should_throw_exception_when_owned_is_already_owned() {

        // -- Setup
        Material owned = new Material();

        User admin = new User();
        admin.setAdmin(true);

        Reservation reservation = ReservationController.makeReservation(admin, owned, LocalDate.now(), LocalDate.now().plusDays(3));

        // -- Test
        Assertions.assertThrows(RuntimeException.class, () -> {

            ReservationController.makeReservation(admin,owned, LocalDate.now(), LocalDate.now().plusDays(3));
        });

        // -- Undo
        ReservationController.closeReservation(admin, reservation); // TODO : How to prevent logs ?

    }

    @Test
    public void should_throw_exception_when_from_is_after_to() {


        Assertions.assertThrows(RuntimeException.class, () -> {

            ReservationController.makeReservation(new User(),new Material(), LocalDate.now().plusDays(3), LocalDate.now());
        });


    }

    @Test
    public void should_throw_exception_when_to_is_before_today() {

        Assertions.assertThrows(RuntimeException.class, () -> {

            ReservationController.makeReservation(new User(),new Material(), LocalDate.now().minusDays(3),  LocalDate.now().minusDays(2));
        });


    }

    @Test
    public void should_add_reservation_to_current_when_begin_date_is_today() {

        User admin = new User(); admin.setAdmin(true);

        Reservation reservation =
                ReservationController.makeReservation(
                admin
                ,new Material(),
                LocalDate.now(),
                LocalDate.now().plusDays(2)
                );

        Assertions.assertTrue(ReservationController.getCurrentReservation().contains(reservation));

        ReservationController.closeReservation(admin, reservation);
    }

    @Test
    public void should_add_reservation_to_incoming_when_begin_date_is_after_today() {

        User admin = new User(); admin.setAdmin(true);

        Reservation reservation =
                ReservationController.makeReservation(
                        admin
                        ,new Material(),
                        LocalDate.now().plusDays(1),
                        LocalDate.now().plusDays(2)
                );


        Assertions.assertTrue(ReservationController.getIncomingReservation().contains(reservation));

        ReservationController.closeReservation(admin, reservation);


    }

    @Test
    public void should_not_add_reservation_to_current_when_begin_date_is_after_today() {

        User admin = new User(); admin.setAdmin(true);

        Reservation reservation =
                ReservationController.makeReservation(
                        admin
                        ,new Material(),
                        LocalDate.now().plusDays(1),
                        LocalDate.now().plusDays(2)
                );


        Assertions.assertFalse(ReservationController.getCurrentReservation().contains(reservation));

        ReservationController.closeReservation(admin, reservation);


    }

    @Test
    public void should_not_add_reservation_to_incoming_when_begin_date_is_today() {

        User admin = new User(); admin.setAdmin(true);

        Reservation reservation =
                ReservationController.makeReservation(
                        admin
                        ,new Material(),
                        LocalDate.now(),
                        LocalDate.now().plusDays(2)
                );

        Assertions.assertFalse(ReservationController.getIncomingReservation().contains(reservation));

        ReservationController.closeReservation(admin, reservation);
    }


    // --------- ReservationController.recalculateReservation() --------------- //
    @Test
    public void should_add_incoming_reservation_to_current_when_begin_date_is_today() {

        // -- Setup
        Reservation reservation = ReservationController.makeReservation(new User(), new Material(), LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
        reservation.setBeginning(LocalDate.now());

        // -- Test
        ReservationController.recalculateReservations();
        Assertions.assertTrue(ReservationController.getCurrentReservation().contains(reservation));

        // -- Undo

        ReservationController.closeReservation(new User(true,"na","na","na","na"),reservation);
    }

    @Test
    public void should_remove_incoming_reservation_from_incoming_when_begin_date_is_today() {

        // -- Setup
        Reservation reservation = ReservationController.makeReservation(new User(), new Material(), LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
        reservation.setBeginning(LocalDate.now());

        // -- Test
        ReservationController.recalculateReservations();
        Assertions.assertFalse(ReservationController.getIncomingReservation().contains(reservation));

        // -- Undo

        ReservationController.closeReservation(new User(true,"na","na","na","na"),reservation);
    }


    // --------- ReservationController.closeReservation() --------------- //

    @Test
    public void should_return_true_when_closing_valid_reservation() {

        User admin = new User(); admin.setAdmin(true);
        Reservation reservation = ReservationController.makeReservation(admin, new Material(), LocalDate.now(), LocalDate.now().plusDays(1));
        Assertions.assertTrue(ReservationController.closeReservation(admin, reservation));

    }

    @Test
    public void should_throw_exception_when_user_removing_is_not_admin() {

        User notAdmin = new User();
        Reservation reservation = ReservationController.makeReservation(notAdmin, new Material(), LocalDate.now(), LocalDate.now().plusDays(1));
        Assertions.assertThrows(RuntimeException.class, () -> {
            ReservationController.closeReservation(notAdmin, reservation);
        });

    }

    @Test
    public void should_throw_exception_when_reservation_is_not_in_lists() {

        User admin = new User(); admin.setAdmin(true);
        Reservation notAValidReservation = new Reservation(admin, new Material(), LocalDate.now(), LocalDate.now().plusDays(1));
        Assertions.assertThrows(RuntimeException.class, () -> {
            ReservationController.closeReservation(admin, notAValidReservation);
        });

    }

    @Test
    public void should_return_null_to_object_reservation_after_closing() {

        User admin = new User(); admin.setAdmin(true);
        Material object = new Material();
        Reservation reservation = ReservationController.makeReservation(admin, object, LocalDate.now(), LocalDate.now().plusDays(1));
        ReservationController.closeReservation(admin, reservation);
        Assertions.assertNull(object.getReservation());


    }

    @Test
    public void should_throw_exception_when_admin_field_is_null() {

        User admin = new User(); admin.setAdmin(true);
        Material object = new Material();
        Reservation reservation = ReservationController.makeReservation(admin, object, LocalDate.now(), LocalDate.now().plusDays(1));


        Assertions.assertThrows(NullPointerException.class, () -> {
           ReservationController.closeReservation(null, reservation);
        });

        // -- Undo
        ReservationController.closeReservation(admin, reservation);

    }

    @Test
    public void should_throw_exception_when_reservation_field_is_null() {

        User admin = new User(); admin.setAdmin(true);

        Assertions.assertThrows(NullPointerException.class, () -> {
            ReservationController.closeReservation(admin, null);
        });

    }


}
