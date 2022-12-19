package reserve.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reserve.Main;
import reserve.controller.ReservationController;
import reserve.model.Material;
import reserve.model.Reservation;
import reserve.model.User;
import reserve.util.AnsiLogger;
import reserve.util.Logger;

import java.time.LocalDate;

public class T_Reservation {


    // ---------------------- makeReservation() ------------------------- //
	
	private ReservationController reservations;
	
	@BeforeAll
	public static void prepare_suite() {
		// keep only error logs
		Main.LOGGER_FACTORY = (name, logLevel) -> new AnsiLogger(name, Logger.LEVEL_ERROR);
	}
	
	@BeforeEach
	public void prepare_test() {
		this.reservations = new ReservationController();
	}

    @Test
    public void should_throw_exception_when_field_is_null_owner() {
        Assertions.assertThrows(NullPointerException.class, () -> {
        	reservations.addReservation(new Reservation(null, new Material(), LocalDate.now(), LocalDate.now().plusDays(1)));
        });
    }

    @Test
    public void should_throw_exception_when_field_is_null_owned() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            reservations.addReservation(new Reservation(new User(), null, LocalDate.now(), LocalDate.now().plusDays(1)));
        });
    }

    @Test
    public void should_throw_exception_when_field_is_null_from() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            reservations.addReservation(new Reservation(new User(), new Material(), null, LocalDate.now().plusDays(1)));
        });
    }

    @Test
    public void should_throw_exception_when_field_is_null_to() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            reservations.addReservation(new Reservation(new User(), new Material(), LocalDate.now(), null));
        });
    }

    @Test
    public void should_throw_exception_when_owned_is_already_owned() {

        // -- Setup
        Material owned = new Material();

        User admin = new User();
        admin.setAdmin(true);

        Reservation reservation = new Reservation(admin, owned, LocalDate.now(), LocalDate.now().plusDays(3));
        reservations.addReservation(reservation);

        // -- Test
        Assertions.assertThrows(RuntimeException.class, () -> {

            reservations.addReservation(new Reservation(admin,owned, LocalDate.now(), LocalDate.now().plusDays(3)));
        });

        // -- Undo
        reservations.closeReservation(admin, reservation); // TODO : How to prevent logs ?

    }

    @Test
    public void should_throw_exception_when_from_is_after_to() {


        Assertions.assertThrows(RuntimeException.class, () -> {

            reservations.addReservation(new Reservation(new User(),new Material(), LocalDate.now().plusDays(3), LocalDate.now()));
        });


    }

    @Test
    public void should_throw_exception_when_to_is_before_today() {

        Assertions.assertThrows(RuntimeException.class, () -> {

            reservations.addReservation(new Reservation(new User(),new Material(), LocalDate.now().minusDays(3),  LocalDate.now().minusDays(2)));
        });


    }

    @Test
    public void should_add_reservation_to_current_when_begin_date_is_today() {

        User admin = new User(); admin.setAdmin(true);

        Reservation reservation = new Reservation(
                admin,
                new Material(),
                LocalDate.now(),
                LocalDate.now().plusDays(2)
                );
        reservations.addReservation(reservation);

        Assertions.assertTrue(reservations.getCurrentReservation().contains(reservation));

        reservations.closeReservation(admin, reservation);
    }

    @Test
    public void should_add_reservation_to_incoming_when_begin_date_is_after_today() {

        User admin = new User(); admin.setAdmin(true);

        Reservation reservation = new Reservation(
                admin,
                new Material(),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2)
                );
        reservations.addReservation(reservation);

        Assertions.assertTrue(reservations.getIncomingReservation().contains(reservation));

        reservations.closeReservation(admin, reservation);


    }

    @Test
    public void should_not_add_reservation_to_current_when_begin_date_is_after_today() {

        User admin = new User(); admin.setAdmin(true);

        Reservation reservation = new Reservation(
                admin,
                new Material(),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2)
                );
        reservations.addReservation(reservation);


        Assertions.assertFalse(reservations.getCurrentReservation().contains(reservation));

        reservations.closeReservation(admin, reservation);


    }

    @Test
    public void should_not_add_reservation_to_incoming_when_begin_date_is_today() {

        User admin = new User(); admin.setAdmin(true);

        Reservation reservation = new Reservation(
                admin,
                new Material(),
                LocalDate.now(),
                LocalDate.now().plusDays(2)
                );
        reservations.addReservation(reservation);

        Assertions.assertFalse(reservations.getIncomingReservation().contains(reservation));

        reservations.closeReservation(admin, reservation);
    }


    // --------- ReservationController.recalculateReservation() --------------- //
    @Test
    public void should_add_incoming_reservation_to_current_when_begin_date_is_today() {

        // -- Setup
        Reservation reservation = new Reservation(new User(), new Material(), LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
        reservations.addReservation(reservation);
        reservation.setBeginning(LocalDate.now());

        // -- Test
        reservations.recalculateReservations();
        Assertions.assertTrue(reservations.getCurrentReservation().contains(reservation));

        // -- Undo

        reservations.closeReservation(new User(true,"na","na","na","na"),reservation);
    }

    @Test
    public void should_remove_incoming_reservation_from_incoming_when_begin_date_is_today() {

        // -- Setup
        Reservation reservation = new Reservation(new User(), new Material(), LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
        reservations.addReservation(reservation);
        reservation.setBeginning(LocalDate.now());

        // -- Test
        reservations.recalculateReservations();
        Assertions.assertFalse(reservations.getIncomingReservation().contains(reservation));

        // -- Undo

        reservations.closeReservation(new User(true,"na","na","na","na"),reservation);
    }


    // --------- ReservationController.closeReservation() --------------- //

    @Test
    public void should_return_true_when_closing_valid_reservation() {

        User admin = new User(); admin.setAdmin(true);
        Reservation reservation = new Reservation(admin, new Material(), LocalDate.now(), LocalDate.now().plusDays(1));
        reservations.addReservation(reservation);
        Assertions.assertTrue(reservations.closeReservation(admin, reservation));

    }

    @Test
    public void should_throw_exception_when_user_removing_is_not_admin() {

        User notAdmin = new User();
        Reservation reservation = new Reservation(notAdmin, new Material(), LocalDate.now(), LocalDate.now().plusDays(1));
        reservations.addReservation(reservation);
        Assertions.assertThrows(RuntimeException.class, () -> {
        	reservations.closeReservation(notAdmin, reservation);
        });

    }

    @Test
    public void should_throw_exception_when_reservation_is_not_in_lists() {

        User admin = new User(); admin.setAdmin(true);
        Reservation notAValidReservation = new Reservation(admin, new Material(), LocalDate.now(), LocalDate.now().plusDays(1));
        Assertions.assertThrows(RuntimeException.class, () -> {
        	reservations.closeReservation(admin, notAValidReservation);
        });

    }

    @Test
    public void should_return_null_to_object_reservation_after_closing() {

        User admin = new User(); admin.setAdmin(true);
        Material object = new Material();
        Reservation reservation = new Reservation(admin, object, LocalDate.now(), LocalDate.now().plusDays(1));
        reservations.addReservation(reservation);
        reservations.closeReservation(admin, reservation);
        Assertions.assertNull(object.getReservation());


    }

    @Test
    public void should_throw_exception_when_admin_field_is_null() {

        User admin = new User(); admin.setAdmin(true);
        Material object = new Material();
        Reservation reservation = new Reservation(admin, object, LocalDate.now(), LocalDate.now().plusDays(1));
        reservations.addReservation(reservation);


        Assertions.assertThrows(NullPointerException.class, () -> {
        	reservations.closeReservation(null, reservation);
        });

        // -- Undo
        reservations.closeReservation(admin, reservation);

    }

    @Test
    public void should_throw_exception_when_reservation_field_is_null() {

        User admin = new User(); admin.setAdmin(true);

        Assertions.assertThrows(NullPointerException.class, () -> {
            reservations.closeReservation(admin, null);
        });

    }


}
