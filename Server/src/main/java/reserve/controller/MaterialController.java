package reserve.controller;

import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


// TODO : Serialize everything
public class MaterialController {

    private static List<Material> materials;


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
    static List<Material> filterByOS( OperatingSystem os ) {//on va faire une classe enum pour trier , voir si champs = enum param, 
    	List<Material> res = new ArrayList<>();
		for(Material mat :materials) {
			if(mat.getOs().equals(os)) {
				res.add(mat);
			}
		}
		return res;
    }

    static void filterByType(List<Material> alreadyFiltered, MaterialType type ) {//on va faire une classe enum pour trier , voir si champs = enum param, 
		for(Material mat :alreadyFiltered) {
			if(!mat.getType().equals(type)) {
				alreadyFiltered.remove(mat);
			}
		}
    }

    static void filterByName(List<Material> alreadyFiltered, String name ) {//on va faire une classe enum pour trier , voir si champs = enum param, 
		for(Material mat :alreadyFiltered) {
			if(!mat.getName().contains(name)) {
				alreadyFiltered.remove(mat);
			}
		}
    }
}
