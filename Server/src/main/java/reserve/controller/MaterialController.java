package reserve.controller;

import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;

import java.util.List;
import java.util.Objects;


// TODO : Serialize everything
public class MaterialController {

    private static List<Material> materials;
    public enum filterType{
    	BY_OS,
    	BY_TYPE,
    	BY_NAME,
    }

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
    static List<Material> filterBy(List<Material> alreadyFilter, filterType filter, String param) {//on va faire une classe enum pour trier , voir si champs = enum param, 
    	List<Material> res = null;
    		switch(filter){
        	case BY_OS : 
        		for(Material mat :materials) {
        			if(mat.getOs()==param) {
        				res.add(mat);
        			}
        		}
        	case BY_TYPE :
        	case BY_NAME : 
        	}
    	
		return res;
    }


}
