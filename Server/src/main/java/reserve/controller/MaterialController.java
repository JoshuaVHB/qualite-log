package reserve.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;

public class MaterialController {
	
	// FIX make controllers non-static, use a singleton instead
	// that way UTs and dev tests can use mocks

    public static final String KEYWORD_FILTER_PATTERN = ".*";
    
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
    public static void addMaterial(OperatingSystem os, MaterialType type, String name, String version, Integer numRef) { // TODO is that necessary? why not addMaterial(Material)
    	// TODO check for duplicate ids
        materials.add(new Material(os,type,name,version,numRef));
        // Log new material
    }
    
    public static Material getByNameAndRef(String name, Integer numRef) { // TODO get by id only
    	for (Material m : materials) {
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
    
    public static List<Material> getAllMaterials() {
    	return new ArrayList<>(materials);
    }
    
    /**
     * @brief Filter for the first time the list of materials
     * @param os, operating system chosen
     */
    public static void filterByOS(List<Material> materials, OperatingSystem os) {//on va faire une classe enum pour trier , voir si champs = enum param, TODO
    	materials.removeIf(m -> m.getOs() != os);
    }

    /**
     * @brief Filter the list once more by type
     * @param alreadyFiltered
     * @param type type of material chosen
     */
    public static void filterByType(List<Material> materials, MaterialType type) {//on va faire une classe enum pour trier , voir si champs = enum param, 
    	materials.removeIf(m -> m.getType() != type);
    }

    /**
     * @brief Filter the list by the presence of a key word in the material's name
     * @param materials
     * @param keyword keyword chosen
     */
    public static void filterByName(List<Material> materials, String keyword) {//on va faire une classe enum pour trier , voir si champs = enum param,
    	String lcKeyword = keyword.toLowerCase();
		materials.removeIf(m -> !m.getName().toLowerCase().contains(lcKeyword));
    }
    
    /**
     * @brief Filter the list once more by availability, only keeps materials where there is no current reservation
     * @param materials
     */
    public static void filterByAvailability(List<Material> materials) {
    	materials.removeIf(m -> m.getReservation() == null);
    }
}
