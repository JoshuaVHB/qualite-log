package reserve.view.entry;

import java.net.HttpURLConnection;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqForm;
import org.takes.rq.form.RqFormBase;
import org.takes.rs.RsWithStatus;

import reserve.controller.MaterialController;
import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;
import reserve.model.User;

public class TkCreateItem implements Take {

	private final MaterialController materials;
	
	public TkCreateItem(MaterialController materials) {
		this.materials = materials;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		User user = FormUtils.getUserIdentity(req);
		RqForm form = new RqFormBase(req);

		if(user == null || !user.isAdmin())
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);

		OperatingSystem os = FormUtils.getParamEnum(form, "os", OperatingSystem.class, false);
		MaterialType type = FormUtils.getParamEnum(form, "type", MaterialType.class, false);
		String name = FormUtils.getParamString(form, "name", MaterialController.MAT_NAME_FORMAT, false);
		String version = FormUtils.getParamString(form, "version", MaterialController.MAT_VERSION_FORMAT, false);
		int numRef = FormUtils.getParamInt(form, "ref", false);
		
		Material material = new Material(os, type, name, version, numRef);
		materials.addMaterial(material);
		return new RsWithStatus(HttpURLConnection.HTTP_OK);
	}

}
