package reserve.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import reserve.model.*;



// TODO : Serialize everything
public class ReservationController {

    static List<Reservation> incoming = new ArrayList<Reservation>();
    static List<Reservation> current = new ArrayList<Reservation>();

    // ------------------------------------------------------------------------------------------- //

    /**
     * @brief Creates a new Reservation.
     * This method makes sure that the dates are coherent, and stores it in the right list.
     * @throws NullPointerException {@code owner} is null
     * @throws NullPointerException {@code owner} is null
     * @throws NullPointerException {@code to} is before current day
     */
    static void makeReservation(User owner, Material owned, LocalDate from, LocalDate to)  {


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
            return;
        }

        // ------ CODE ------- //

        Reservation reservation = new Reservation(owner, owned, from, to);

        if (from.compareTo(today) <= 0) { // Means that the start day is before or today -> store in current reservation
            current.add(reservation);
        } else {
            incoming.add(reservation);
        }
    }

    /**
     * @brief Checks that the incoming reservations have not started yet.
     * This method has to be called before any operations on reservations.
     */
    static void recalculateReservations()
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
