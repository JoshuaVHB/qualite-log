package reserve.controller;

import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;

import java.util.List;
import java.util.Objects;

public class MaterialController {

    private static List<Material> materials;

    static void addMaterial(OperatingSystem os, MaterialType type, String name, String version, Integer numRef) {

        materials.add(new Material(os,type,name,version,numRef));
        // Log new material
    }

    static void removeMaterial(Material toRemove) {

        Objects.requireNonNull(toRemove); // Make sur the user is coherent

        if (materials.remove(toRemove)) {

            // Log sucessful

        } else {

            // Log

        }

    }


}
