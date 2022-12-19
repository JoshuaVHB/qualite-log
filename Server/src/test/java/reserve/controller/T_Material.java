package reserve.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reserve.Main;
import reserve.model.Material;
import reserve.util.AnsiLogger;
import reserve.util.Logger;

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
        Material dummy2 = new Material();
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

    // TODO : will the filter functions stay the same ?




}
