package reserve.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;

// TODO LOG EVERYTHING

public class MaterialController {
	
	// FIX make controllers non-static, use a singleton instead
	// that way UTs and dev tests can use mocks

    public static final String KEYWORD_FILTER_PATTERN = ".*";
    
    private static List<Material> materials;
    
    // ------------------------------------------------------------------------------------------- //

    /**
     * @brief Adds a material instance to the whole material list.
     * @params material : Material to be added
     * @throws IllegalArgumentException : {@code material is already in the list}
     */
    public static void addMaterial(Material material)
            throws IllegalArgumentException {

        // this should work i guess, i have to do tests

        // ------ Error checking ----- //
        boolean exists = materials  .stream()
                                    .filter(m -> m.getName() == material.getName())
                                    .anyMatch(m -> m.getNumRef() == material.getNumRef());

        if (exists) throw new IllegalArgumentException("The material is already present in the list");

        // -------------------------//

        materials.add(material);
        // Log
    }

    // TODO : delete this and use the method below
    public static Material getByNameAndRef(String name, Integer numRef) {
    	for (Material m : materials) {
    		if (m.getName() == name && m.getNumRef() == numRef) {
    			return m;
    		}
    	}
    	return null;
    }

    /**
     * @brief Returns the material associated to the parameter ID, null otherwise.
     * @throws NullPointerException {@code id is null}
     */
    public static Material getMaterialById(UUID id) {
        Objects.requireNonNull(id);
        return materials.stream().filter(m -> m.getId() == id).findAny().orElse(null);
    }

    /**
     * @brief Removes a certain material if it's present in the list, does nothing otherwise.
     * @param toRemove
     * @return True if the material was removed successfully, false otherwise.
     * @throws NullPointerException : {@code toRemove is null}
     */
    public static boolean removeMaterial(Material toRemove) {

        Objects.requireNonNull(toRemove); // Make sur the user is coherent

        if (materials.remove(toRemove)) {

            // Log sucessful
            return true;

        } else {

            // Log
            return false;
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
