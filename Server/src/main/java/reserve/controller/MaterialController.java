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

    public static final String KEYWORD_FILTER = ".*";
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
    public static void addMaterial(OperatingSystem os, MaterialType type, String name, String version, Integer numRef) {

        materials.add(new Material(os,type,name,version,numRef));
        // Log new material
    }
    
    public static Material getByNameAndRef(String name, Integer numRef) {
    	for (Material m:materials) {
    		if (m.getName() == name && m.getNumRef() == numRef) {
    			return m;
    		}
    	}
    	return null;
    }

    /**
     * @brief Removes a certain material if it's present in the list, does nothing otherwise.
     * @param toRemove
     */
    public static void removeMaterial(Material toRemove) {

        Objects.requireNonNull(toRemove); // Make sur the user is coherent

        if (materials.remove(toRemove)) {

            // Log sucessful

        } else {

            // Log

        }

    }
    /**
     * @brief Filter for the first time the list of materials
     * @param os, operating system chosen
     * @return a new list, with the material corresponding to the filter
     */
    public static List<Material> filterByOS( OperatingSystem os ) {//on va faire une classe enum pour trier , voir si champs = enum param, 
    	List<Material> res = new ArrayList<>();
		for(Material mat :materials) {
			res.add(mat);
		}
		return res;
    }

    /**
     * @brief Filter the list once more by type
     * @param alreadyFiltered,
     * @param type, type of material chosen
     */
    public static void filterByType(List<Material> alreadyFiltered, MaterialType type ) {//on va faire une classe enum pour trier , voir si champs = enum param, 
		for(Material mat :alreadyFiltered) {
			if(!mat.getType().equals(type)) {
				alreadyFiltered.remove(mat);
			}
		}
    }

    /**
     * @brief Filter the list by the presence of a key word in the material's name
     * @param alreadyFiltered,
     * @param keyword, keyword chosen
     */
    public static void filterByName(List<Material> alreadyFiltered, String keyword ) {//on va faire une classe enum pour trier , voir si champs = enum param, 
		for(Material mat :alreadyFiltered) {
			if(!mat.getName().contains(keyword)) {
				alreadyFiltered.remove(mat);
			}
		}
    }
    /**
     * @brief Filter the list once more by availability, only keeps materials where there is no current reservation
     * @param alreadyFiltered,
     */
    public static void filterByAvailability(List<Material> alreadyFiltered) {
    	for(Material mat : alreadyFiltered) {
    		if (!mat.getReservation().equals(null)) {
    			alreadyFiltered.remove(mat);
    		}
    	}
    }
}
