package reserve.view.entry;

import java.io.IOException;
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

public class TkMaterialManager implements Take {

	// TODO test all this
	
	private final MaterialController materials;
	
	public TkMaterialManager(MaterialController controller) {
		this.materials = controller;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		User user = FormUtils.getUserIdentity(req);
		RqForm form = new RqFormBase(req);
		
		if(user == null)
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		
		CRUDAction action = FormUtils.getParamEnum(form, "action", CRUDAction.class, false);
		switch(action) {
		case CREATE: return createMaterial(user, form);
		case DELETE: return deleteMaterial(user, form);
		case UPDATE: return updateMaterial(user, form);
		case READ: return new RsWithStatus(new RsText("No read action on materials"), HttpURLConnection.HTTP_BAD_GATEWAY);
		default: throw new IllegalStateException("unreachable");
		}
	}

	private Response createMaterial(User user, RqForm form) throws IllegalArgumentException, IOException {
		if(!user.isAdmin())
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		
		OperatingSystem os = FormUtils.getParamEnum(form, "os", OperatingSystem.class, false);
		MaterialType type = FormUtils.getParamEnum(form, "type", MaterialType.class, false);
		String name = FormUtils.getParamString(form, "name", ".*", false); // TODO add regex to match specs
		String version = FormUtils.getParamString(form, "version", ".*", false); // TODO add regex to match specs
		int numRef = FormUtils.getParamInt(form, "numref", false);
		
		Material material = new Material(os, type, name, version, numRef);
		try {
			materials.addMaterial(material);
			return new RsWithStatus(HttpURLConnection.HTTP_OK);
		} catch (IllegalArgumentException e) {
			return new RsWithStatus(new RsText(e.getMessage()), HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
	
	private Response deleteMaterial(User user, RqForm form) throws IllegalArgumentException, IOException {
		if(!user.isAdmin())
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		
		UUID materialId = FormUtils.getParamUUID(form, "id", false);
		Material material = materials.getMaterialById(materialId);
		if(material == null)
			return new RsWithStatus(new RsText("This material does not exist"), HttpURLConnection.HTTP_BAD_REQUEST);
		if(!materials.removeMaterial(material)) // TODO FIX removing materials currently *does not* removes associated reservations, this is a bug
			return new RsWithStatus(new RsText("Could not remove material"), HttpURLConnection.HTTP_INTERNAL_ERROR);
		return new RsWithStatus(HttpURLConnection.HTTP_OK);
	}
	
	private Response updateMaterial(User user, RqForm form) throws IllegalArgumentException, IOException {
		if(!user.isAdmin())
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		
		OperatingSystem os = FormUtils.getParamEnum(form, "os", OperatingSystem.class, true);
		MaterialType type = FormUtils.getParamEnum(form, "type", MaterialType.class, true);
		String name = FormUtils.getParamString(form, "name", ".*", true); // TODO add regex to match specs
		String version = FormUtils.getParamString(form, "version", ".*", true); // TODO add regex to match specs
		Integer numRef = FormUtils.getParamInt(form, "numref", true);

		UUID materialId = FormUtils.getParamUUID(form, "id", false);
		Material material = materials.getMaterialById(materialId);
		if(material == null)
			return new RsWithStatus(new RsText("This material does not exist"), HttpURLConnection.HTTP_BAD_REQUEST);
		
		if(os != null) material.setOs(os);
		if(type != null) material.setType(type);
		if(name != null) material.setName(name);
		if(version != null) material.setVersion(version);
		if(numRef != null) material.setNumRef(numRef);
		
		return new RsWithStatus(HttpURLConnection.HTTP_OK);
	}
	
}
