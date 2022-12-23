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
	
    public static final String KEYWORD_FILTER_PATTERN = ".*";
    
    private final List<Material> materials = new ArrayList<>();
    
    // ------------------------------------------------------------------------------------------- //

    /**
     * @brief Adds a material instance to the whole material list
     * @param material Material to be added
     * @throws NullPointerException {@code material} is null
     * @throws IllegalArgumentException {@code material} is already in the list
     */
    public boolean addMaterial(Material material) throws IllegalArgumentException, NullPointerException {

        // ------ Error checking ----- //
        Objects.requireNonNull(material);

        boolean exists = materials  .stream()
                                    .anyMatch(m -> m.getId().equals(material.getId()));

        if (exists) throw new IllegalArgumentException("The material is already present in the list");

        // -------------------------//

        return (materials.add(material));
    }

    /**
     * @brief Returns the material associated to the parameter ID, null otherwise.
     * @throws NullPointerException {@code id is null}
     */
    public Material getMaterialById(UUID id) {
        Objects.requireNonNull(id);
        return materials.stream().filter(m -> m.getId().equals(id)).findAny().orElse(null);
    }

    /**
     * @brief Removes a certain material if it's present in the list, does nothing otherwise.
     * @param toRemove
     * @return True if the material was removed successfully, false otherwise.
     * @throws NullPointerException {@code} toRemove is null
     */
    public boolean removeMaterial(Material toRemove) {

        Objects.requireNonNull(toRemove); // Make sure the user is coherent

        if (materials.remove(toRemove)) {

            // Log sucessful
            return true;

        } else {

            // Log
            return false;
        }

    }
    
    /**
     * Returns a new <i>copy</i> of the materials list, changes are not
     * reflected back to this instance's list.
     * 
     * @return a <i>copy</i> of the materials list
     */
    public List<Material> getAllMaterials() {
    	return new ArrayList<>(materials);
    }
    
    /**
     * @brief Filter for the first time the list of materials
     * @param os operating system chosen
     */
    public static void filterByOS(List<Material> materials, OperatingSystem os) {
    	materials.removeIf(m -> m.getOs() != os);
    }

    /**
     * @brief Filter the list once more by type
     * @param type type of material chosen
     */
    public static void filterByType(List<Material> materials, MaterialType type) {
    	materials.removeIf(m -> m.getType() != type);
    }

    /**
     * @brief Filter the list by the presence of a key word in the material's name
     * @param materials
     * @param keyword keyword chosen
     */
    public static void filterByName(List<Material> materials, String keyword) {
    	String lcKeyword = keyword.toLowerCase();
		materials.removeIf(m -> !m.getName().toLowerCase().contains(lcKeyword));
    }
    
    /**
     * @brief Filter the list once more by availability, only keeps materials where there is no current reservation
     * @param materials
     */
    public static void filterByAvailability(List<Material> materials) {
    	materials.removeIf(m -> m.getReservation() != null);
    }
}
