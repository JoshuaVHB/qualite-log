package reserve.view.entry;

import java.util.List;

import org.json.simple.JSONObject;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.misc.Href;
import org.takes.rq.RqHref;
import org.takes.rs.RsText;

import reserve.controller.MaterialController;
import reserve.controller.io.JsonSerializer;
import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;

public class TkListItems implements Take {
	
	@Override
	public Response act(Request req) throws Exception {
		Href href = new RqHref.Base(req).href();
		
		MaterialType type = FormUtils.getParamEnum(href, "type", MaterialType.class, true);
		OperatingSystem os = FormUtils.getParamEnum(href, "OS", OperatingSystem.class, true);
		String keyword = FormUtils.getParamString(href, "key", MaterialController.KEYWORD_FILTER_PATTERN, true);
		boolean showUnavailable = FormUtils.hasParam(href, "show-unavailable");
		
		List<Material> res = MaterialController.getAllMaterials();
		if(os != null)       MaterialController.filterByOS(res, os);
		if(type != null)     MaterialController.filterByType(res, type);
		if(keyword != null)  MaterialController.filterByName(res, keyword);
		if(!showUnavailable) MaterialController.filterByAvailability(res);
		
		JSONObject serialized = JsonSerializer.serializeMaterialList(res);
		return new RsText(serialized.toJSONString());
	}
	
}
