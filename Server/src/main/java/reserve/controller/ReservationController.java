package reserve.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import reserve.model.*;


public class ReservationController {
	
	// FIX make controllers non-static, use a singleton instead
	// that way UTs and dev tests can use mocks // how ??? whats the diff

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
     * @returns The newly created reservation
     */
    public static Reservation makeReservation(User owner, Material owned, LocalDate from, LocalDate to)
            throws RuntimeException, NullPointerException {


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
            throw new RuntimeException("Reservation end date is before current day !");
        }

        if ( from.compareTo(to) > 0){ // reservation starts after or at the end ...
            throw new RuntimeException("The reservation starting date must be before the ending date !");
        }

        if (!Objects.isNull(owned.getReservation())) { // The object is already owned
            throw new RuntimeException("The material asked for is already owned.");
        }

        // ------ CODE ------- //

        Reservation reservation = new Reservation(owner, owned, from, to);

        if (from.compareTo(today) <= 0) { // Means that the start day is before or today -> store in current reservation
            owned.setReservation(reservation);
            current.add(reservation);
        } else {
            incoming.add(reservation);
        }

        return reservation;
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
            }
        }

        // Remove all the reservations from incoming
        for (Reservation reservation : current) {
            incoming.remove(reservation);
        }

    }

    /**
     * @brief This method is to be called by an admin to close a reservation.
     * @param admin
     * @param reservation
     * @throws RuntimeException {@code user is not admin OR reservation is not in the lists}
     * @throws NullPointerException {@code admin or reservation is null}
     */
    public static boolean closeReservation(User admin, Reservation reservation)
            throws RuntimeException {

        // -- Error checking

        Objects.requireNonNull(admin);
        Objects.requireNonNull(reservation);

        if (!admin.isAdmin()) throw new RuntimeException("The user is not an admin");

        if (!current.contains(reservation) && !incoming.contains(reservation) ) {
            throw new RuntimeException("How could this possibly happen ?");
        }

        // ------------- //

        reservation.getMaterial().setReservation(null); // Detach the object from the reservation

        if (current.remove(reservation) || incoming.remove(reservation))  {

            // Log admin ... deleted reservation ....
            return true;
        } else {

            // Log erreur
            return false;

        }


    }


    public static List<Reservation> getIncomingReservation() {return incoming;}
    public static List<Reservation> getCurrentReservation() {return current;}

}
