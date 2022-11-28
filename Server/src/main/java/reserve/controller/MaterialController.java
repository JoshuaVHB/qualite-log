package reserve.controller;

import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;
import reserve.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


// TODO : Serialize everything
public class MaterialController {

    private static List<Material> materials = new ArrayList<Material>();


    // ------------------------------------------------------------------------------------------- //

    /**
     * @brief Adds a new material instance to the whole material list.
     * @param os
     * @param type
     * @param name
     * @param version
     * @param numRef
     */
    static void addMaterial(OperatingSystem os, MaterialType type, String name, String version, Integer numRef) {

        materials.add(new Material(os,type,name,version,numRef));
        // Log new material
    }

    /**
     * @brief Removes a certain material if it's present in the list, does nothing otherwise.
     * @param toRemove
     */
    static void removeMaterial(Material toRemove) {

        Objects.requireNonNull(toRemove); // Make sur the user is coherent

        if (materials.remove(toRemove)) {

            // Log sucessful

        } else {

            // Log

        }

    }


}
