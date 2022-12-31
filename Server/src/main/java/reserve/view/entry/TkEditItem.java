package reserve.view.entry;

import java.net.HttpURLConnection;
import java.util.UUID;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqForm;
import org.takes.rq.form.RqFormBase;
import org.takes.rs.RsText;
import org.takes.rs.RsWithStatus;

import reserve.controller.MaterialController;
import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;
import reserve.model.User;

public class TkEditItem implements Take {

	private final MaterialController materials;
	
	public TkEditItem(MaterialController materials) {
		this.materials = materials;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		User user = FormUtils.getUserIdentity(req);
		RqForm form = new RqFormBase(req);

		if(user == null || !user.isAdmin())
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		
		UUID editedMaterialID = FormUtils.getParamUUID(form, "materialId", true);
		System.out.println(editedMaterialID);
		if(FormUtils.getParamBool(form, "delete", true) != null) {
			// delete the material
			Material material = materials.getMaterialById(editedMaterialID);
			if(material == null)
				return new RsWithStatus(new RsText("Unknown material"), HttpURLConnection.HTTP_BAD_REQUEST);
			materials.removeMaterial(material);
			return new RsWithStatus(HttpURLConnection.HTTP_OK);
		}

		OperatingSystem os = FormUtils.getParamEnum(form, "os", OperatingSystem.class, false);
		MaterialType type = FormUtils.getParamEnum(form, "type", MaterialType.class, false);
		String name = FormUtils.getParamString(form, "name", MaterialController.MAT_NAME_FORMAT, false);
		String version = FormUtils.getParamString(form, "version", MaterialController.MAT_VERSION_FORMAT, false);
		int numRef = FormUtils.getParamInt(form, "ref", false);
		version = "V"+version;
		if(numRef>999)
			throw new IllegalArgumentException("Invalid value for 'numRef', expected an int smaller than 1000");
		
		if(editedMaterialID == null) {
			// create a new material
			Material material = new Material(os, type, name, version, numRef);
			materials.addMaterial(material);
			return new RsWithStatus(new RsText(material.getId().toString()), HttpURLConnection.HTTP_OK);
		} else {
			// edit the material
			Material material = materials.getMaterialById(editedMaterialID);
			material.setName(name);
			material.setNumRef(numRef);
			material.setOs(os);
			material.setType(type);
			material.setVersion(version);
			MaterialController.logger.debug("Edited material " + material);
			return new RsWithStatus(HttpURLConnection.HTTP_OK);
		}
	}

}
