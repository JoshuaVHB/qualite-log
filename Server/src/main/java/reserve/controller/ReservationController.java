package reserve.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import reserve.model.*;


public class ReservationController {
	
	// FIX make controllers non-static, use a singleton instead
	// that way UTs and dev tests can use mocks

    private static List<Reservation> incoming = new ArrayList<Reservation>();
    private static List<Reservation> current = new ArrayList<Reservation>();

    // ------------------------------------------------------------------------------------------- //

    /**
     * @brief Creates a new Reservation.
     * This method makes sure that the dates are coherent, and stores it in the right list.
     * 
     * FUTURE these  vvv  may be replaced by "throws a npe unless otherwise specified"
     * @throws NullPointerException {@code owner} is null
     * @throws NullPointerException {@code owner} is null
     * @throws NullPointerException {@code to} is before current day
     */
    public static void makeReservation(User owner, Material owned, LocalDate from, LocalDate to)  {


        // ------ ERROR CHECKING ------ //

        // Makes sure the objects are somewhat coherent
        Objects.requireNonNull(owner);
        Objects.requireNonNull(owned);
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);

        // -- Get the current day to determine where the reservation should be stored
        LocalDate today = LocalDate.now();

        // Makes sure the reservation end date is coherent
        if (to.compareTo(today) < 0) {
            System.out.println("Reservation end date is before current day !");
            return; // TODO throw an error instead of printing one
        }
        
        // TODO check if from>to
        // TODO check that the reservation does not overlap with another

        // ------ CODE ------- //

        Reservation reservation = new Reservation(owner, owned, from, to);

        if (from.compareTo(today) <= 0) { // Means that the start day is before or today -> store in current reservation
        	// TODO set owned.reservation 
            current.add(reservation);
        } else {
            incoming.add(reservation);
        }
    }

    /**
     * @brief Checks that the incoming reservations have not started yet.
     * This method has to be called before any operations on reservations.
     */
    public static void recalculateReservations()
    {
        // Loop through incoming reservation and compare to today's date.
        for (Reservation reservation : incoming) {

            if (reservation.getBeginning().compareTo(LocalDate.now()) <= 0)
            {
                // Move the reservation to the current
                current.add(reservation);
                incoming.remove(reservation);
            }
        }
    }

}
