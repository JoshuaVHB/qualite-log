package reserve.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reserve.Main;
import reserve.model.*;
import reserve.util.AnsiLogger;
import reserve.util.Logger;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class T_Material {

    private MaterialController materials;


    @BeforeAll
    public static void prepare_suite() {
        // keep only error logs
        Main.LOGGER_FACTORY = (name, logLevel) -> new AnsiLogger(name, Logger.LEVEL_ERROR);
    }

    @BeforeEach
    public void prepare_test() {
        this.materials = new MaterialController();
    }


    // --------- MaterialController.addMaterial() --------------- //

    @Test
    public void should_throw_exception_when_material_is_null() {

        Assertions.assertThrows(NullPointerException.class, ()-> {
            materials.addMaterial(null);
        });

    }

    @Test
    public void should_throw_exception_when_material_is_already_in_list() {

        Material dummy = new Material();
        materials.addMaterial(dummy);

        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            materials.addMaterial(dummy);
        });
    }

    @Test
    public void should_return_true_when_adding_material_to_materials_list(){

        Material dummy = new Material();
        Assertions.assertTrue(materials.addMaterial(dummy));

    }

    @Test
    public void should_contain_material_in_list_after_adding_material(){

        Material dummy = new Material();
        materials.addMaterial(dummy);
        Assertions.assertTrue(materials.getAllMaterials().contains(dummy));

    }

    // --------- MaterialController.getMaterialById() --------------- //

    @Test
    public void should_throw_exception_when_id_is_null() {

        Assertions.assertThrows(NullPointerException.class, ()-> {
            materials.getMaterialById(null);
        });

    }
    @Test
    public void should_return_null_when_id_is_not_found() {

        Assertions.assertNull(materials.getMaterialById(UUID.randomUUID()));

    }
    @Test
    public void should_return_material_when_id_exists() {

        Material dummy = new Material();
        materials.addMaterial(dummy);

        Assertions.assertEquals(dummy, materials.getMaterialById(dummy.getId()));

    }

    // --------- MaterialController.removeMaterial() --------------- //

    @Test
    public void should_throw_exception_when_removed_object_is_null() {

        Assertions.assertThrows(NullPointerException.class, ()-> {
            materials.removeMaterial(null);
        });
    }

    @Test
    public void should_return_false_when_removing_non_existent_material() {

        Assertions.assertFalse(materials.removeMaterial(new Material()));

    }

    @Test
    public void should_return_true_when_removing_valid_material() {


        Material dummy = new Material();
        materials.addMaterial(dummy);
        Assertions.assertTrue(materials.removeMaterial(dummy));

    }

    @Test
    public void should_not_contain_material_after_removal() {


        Material dummy = new Material();
        materials.addMaterial(dummy);
        materials.removeMaterial(dummy);


        Assertions.assertFalse(materials.getAllMaterials().contains(dummy));

    }

    // --------- MaterialController.filterByOS() --------------- //

    @Test
    public void should_return_false_when_looking_for_a_filtered_by_wrong_os_material() {

        Material dummy = new Material(); // OS type is XX
        materials.addMaterial(dummy);
        List<Material> allMats = materials.getAllMaterials();

        MaterialController.filterByOS(allMats, OperatingSystem.AP);

        Assertions.assertFalse(allMats.contains(dummy));

    }

    @Test
    public void should_return_true_when_looking_for_a_filtered_by_right_os_material() {

        Material dummy = new Material(); // OS type is XX
        materials.addMaterial(dummy);
        List<Material> allMats = materials.getAllMaterials();

        MaterialController.filterByOS(allMats, OperatingSystem.XX);

        Assertions.assertTrue(allMats.contains(dummy));

    }

    @Test
    public void should_contain_all_materials_of_filter_by_os() {

        Material[] dummies = new Material[] {

          new Material(),
          new Material(),
          new Material(),
          new Material(),
          new Material(),

        };

        Arrays.stream(dummies).forEach(m -> {materials.addMaterial(m);});

        List<Material> allMats = materials.getAllMaterials();
        MaterialController.filterByOS(allMats, OperatingSystem.XX);

        Assertions.assertEquals(allMats.size(), dummies.length);

    }

    @Test
    public void should_return_empty_list_when_no_material_is_found_using_os() {

        Material[] dummies = new Material[] {

                new Material(),
                new Material(),
                new Material(),
                new Material(),
                new Material(),

        };

        Arrays.stream(dummies).forEach(m -> {materials.addMaterial(m);});

        List<Material> allMats = materials.getAllMaterials();
        MaterialController.filterByOS(allMats, OperatingSystem.AP);

        Assertions.assertEquals(0, allMats.size());

    }


    // --------- MaterialController.filterByType() --------------- //

    @Test
    public void should_return_false_when_looking_for_a_filtered_by_wrong_type_material() {

        Material dummy = new Material(); // MaterialType is .OTHER
        materials.addMaterial(dummy);

        List<Material> allMats = materials.getAllMaterials();

        MaterialController.filterByType(allMats, MaterialType.MOBILE);

        Assertions.assertFalse(allMats.contains(dummy));

    }

    @Test
    public void should_return_true_when_looking_for_a_filtered_by_right_type_material() {

        Material dummy = new Material();
        materials.addMaterial(dummy);

        List<Material> allMats = materials.getAllMaterials();

        MaterialController.filterByType(allMats, MaterialType.OTHER);

        Assertions.assertTrue(allMats.contains(dummy));

    }

    @Test
    public void should_contain_all_materials_of_filter_by_type() {

        Material[] dummies = new Material[] {

                new Material(),
                new Material(),
                new Material(),
                new Material(),
                new Material(),

        };

        Arrays.stream(dummies).forEach(m -> {materials.addMaterial(m);});

        List<Material> allMats = materials.getAllMaterials();
        MaterialController.filterByType(allMats, MaterialType.OTHER );

        Assertions.assertEquals(allMats.size(), dummies.length);

    }

    @Test
    public void should_return_empty_list_when_no_material_is_found_using_type() {

        Material[] dummies = new Material[] {

                new Material(),
                new Material(),
                new Material(),
                new Material(),
                new Material(),

        };

        Arrays.stream(dummies).forEach(m -> {materials.addMaterial(m);});

        List<Material> allMats = materials.getAllMaterials();
        MaterialController.filterByType(allMats, MaterialType.TABLET);

        Assertions.assertEquals(0, allMats.size());
    }

    // --------- MaterialController.filterByName() --------------- //

    @Test
    public void should_return_false_when_looking_for_a_filtered_by_wrong_name_material() {

        Material dummy = new Material();
        materials.addMaterial(dummy);

        List<Material> allMats = materials.getAllMaterials();

        MaterialController.filterByName(allMats, "toto");

        Assertions.assertFalse(allMats.contains(dummy));

    }

    @Test
    public void should_return_true_when_looking_for_a_filtered_by_right_name_material() {

        Material dummy = new Material();
        materials.addMaterial(dummy);

        List<Material> allMats = materials.getAllMaterials();

        MaterialController.filterByName(allMats, "N/A");

        Assertions.assertTrue(allMats.contains(dummy));

    }

    @Test
    public void should_contain_all_materials_of_filter_by_name() {

        Material[] dummies = new Material[] {

                new Material(),
                new Material(),
                new Material(),
                new Material(),
                new Material(),

        };

        Arrays.stream(dummies).forEach(
                m -> {
                    m.setName("DummyName");
                    materials.addMaterial(m);
                });

        List<Material> allMats = materials.getAllMaterials();
        MaterialController.filterByName(allMats, "DummyName");


        Assertions.assertEquals(dummies.length, allMats.size() );

    }

    @Test
    public void should_return_empty_list_when_no_material_is_found_having_name() {

        Material[] dummies = new Material[] {

                new Material(),
                new Material(),
                new Material(),
                new Material(),
                new Material(),

        };

        Arrays.stream(dummies).forEach(
                m -> {
                    m.setName("DummyName");
                    materials.addMaterial(m);
                });

        List<Material> allMats = materials.getAllMaterials();
        MaterialController.filterByName(allMats, "toto");

        Assertions.assertEquals(0, allMats.size() );
    }


    // --------- MaterialController.filterByAvailability() --------------- //

    @Test
    public void should_return_empty_list_when_looking_for_an_unavailable_material() {

        Material dummy = new Material();
        materials.addMaterial(dummy);

        Reservation reservation = new Reservation(
                new User(),
                dummy,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1));

        new ReservationController().addReservation(reservation);

        List<Material> allMats = materials.getAllMaterials();

        MaterialController.filterByAvailability(allMats);

        Assertions.assertEquals(0, allMats.size());

    }

    @Test
    public void should_return_available_material_when_no_reservations_has_been_made() {

        Material dummy = new Material();
        materials.addMaterial(dummy);

        List<Material> allMats = materials.getAllMaterials();

        MaterialController.filterByAvailability(allMats);

        Assertions.assertEquals(1, allMats.size());

    }

    @Test
    public void should_return_material_when_current_reservation_is_closed() {

        // -- Create a material whose status is not available
        Material dummy = new Material();
        materials.addMaterial(dummy);

        // -- Create a reservation
        Reservation reservation = new Reservation(
                new User(),
                dummy,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1));


        // -- Add the reservation, and close it right after
        ReservationController rc = new ReservationController();
        rc.addReservation(reservation);

        rc.closeReservation(new User().setAdmin(true), reservation);

        // -- Filter and check if the material is available.
        List<Material> allMats = materials.getAllMaterials();

        MaterialController.filterByAvailability(allMats);

        Assertions.assertEquals(1, allMats.size());

    }

    @Test
    public void should_return_material_when_reservation_has_not_started_yet() {

        // -- Create a material whose status is not available
        Material dummy = new Material();
        materials.addMaterial(dummy);

        // -- Create a reservation
        Reservation reservation = new Reservation(
                new User(),
                dummy,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2));


        // -- Add the reservation, and close it right after
        ReservationController rc = new ReservationController();
        rc.addReservation(reservation);

        rc.closeReservation(new User().setAdmin(true), reservation);

        // -- Filter and check if the material is available.
        List<Material> allMats = materials.getAllMaterials();

        MaterialController.filterByAvailability(allMats);

        Assertions.assertEquals(1, allMats.size());

    }

    @Test
    public void should_return_empty_when_reservation_started_after_recalculating_reservations() {

        // -- Create a material whose status is not available
        Material dummy = new Material();
        materials.addMaterial(dummy);

        // -- Create a reservation
        Reservation reservation = new Reservation(
                new User(),
                dummy,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2));


        // -- Add the reservation, change the beginning date and recalculate
        ReservationController rc = new ReservationController();
        rc.addReservation(reservation);

        reservation.setBeginning(LocalDate.now().minusDays(1));
        rc.recalculateReservations();

        // -- Filter and check if the material is available.
        List<Material> allMats = materials.getAllMaterials();

        MaterialController.filterByAvailability(allMats);

        Assertions.assertEquals(0, allMats.size());

    }


}
