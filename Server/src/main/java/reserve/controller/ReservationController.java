package reserve.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import reserve.Main;
import reserve.model.Reservation;
import reserve.model.User;
import reserve.util.Logger;


public class ReservationController {
	
	private static final Logger logger = Main.LOGGER_FACTORY.getLogger("reservations", Logger.LEVEL_DEBUG);
	
    private List<Reservation> incoming = new ArrayList<Reservation>();
    private List<Reservation> current = new ArrayList<Reservation>();

    // ------------------------------------------------------------------------------------------- //

    /**
     * Creates a new Reservation.
     * 
     * This method makes sure that the dates are coherent, and stores it in the right list.
     */
    public void addReservation(Reservation reservation) throws IllegalStateException {

        // ------ ERROR CHECKING ------ //
    	LocalDate from = reservation.getBeginning();
    	LocalDate to = reservation.getEnding();

        // -- Get the current day to determine where the reservation should be stored
        LocalDate today = LocalDate.now();

        // Makes sure the reservation end date is coherent
        if (to.compareTo(today) < 0) {
            throw new IllegalStateException("Reservation end date is before current day !");
        }
        
    	if (reservation.getMaterial().getReservation() != null) { // The object is already owned
    		throw new IllegalStateException("The material asked for is already owned.");
    	}

        // ------ CODE ------- //

    	logger.debug("Created reservation " + reservation);
        if (from.compareTo(today) <= 0) { // Means that the start day is before or today -> store in current reservation
            reservation.getMaterial().setReservation(reservation);
            current.add(reservation);
        } else {
            incoming.add(reservation);
        }
    }

    /**
     * @brief Checks that the incoming reservations have not started yet.
     * This method has to be called before any operations on reservations.
     */
    public void recalculateReservations() {

        // Loop through incoming reservation and compare to today's date.
        for (Reservation reservation : incoming) {

            if (reservation.getBeginning().compareTo(LocalDate.now()) <= 0)
            {
                // Move the reservation to the current
                reservation.getMaterial().setReservation(reservation); // FIX : i forgot this
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
     * // TODO semantically it does not make much sense to call this method w/ a user object
     * // the check should be done by the caller, also currently a user cannot close one of
     * // their reservations
     * @param admin
     * @param reservation
     * @throws IllegalArgumentException {@code user is not admin OR reservation is not in the lists}
     * @throws NullPointerException {@code admin or reservation is null}
     */
    public void closeReservation(User admin, Reservation reservation) throws IllegalArgumentException {

        // -- Error checking

        Objects.requireNonNull(admin);
        Objects.requireNonNull(reservation);

        if (!admin.isAdmin()) throw new RuntimeException("The user is not an admin");

        if (!current.contains(reservation) && !incoming.contains(reservation))
            throw new IllegalArgumentException("Unknown reservation");

        // ------------- //

        reservation.getMaterial().setReservation(null); // Detach the object from the reservation

        current.remove(reservation);
        incoming.remove(reservation);
    	logger.debug("Closed reservation " + reservation);
    }


    public List<Reservation> getIncomingReservation() { return incoming; }
    public List<Reservation> getCurrentReservation() { return current; }
	public List<Reservation> getAllReservations() {
		List<Reservation> all = new ArrayList<>(incoming.size() + current.size());
		all.addAll(current);
		all.addAll(incoming);
		return all;
	}

	public Reservation getReservation(UUID materialID, String userId, LocalDate fromDate) {
		return getAllReservations().stream()
				.filter(r -> r.getMaterial().getId().equals(materialID))
				.filter(r -> r.getOwner().getId().contentEquals(userId))
				.filter(r -> r.getBeginning().equals(fromDate))
				.findAny().orElse(null);
	}

    public int getNumberOfReservations() {
        return current.size() + incoming.size();
    }

}
