package reserve.view.entry;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
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
	
	private final MaterialController materials;
	
	public TkListItems(MaterialController materials) {
		this.materials = materials;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Response act(Request req) throws Exception {
		Href href = new RqHref.Base(req).href();
		
		MaterialType type = FormUtils.getParamEnum(href, "type", MaterialType.class, true);
		OperatingSystem os = FormUtils.getParamEnum(href, "OS", OperatingSystem.class, true);
		String keyword = FormUtils.getParamString(href, "key", MaterialController.KEYWORD_FILTER_PATTERN, true);
		boolean showUnavailable = FormUtils.hasParam(href, "show-unavailable");
		
		List<Material> res = materials.getAllMaterials();
		if(os != null)       MaterialController.filterByOS(res, os);
		if(type != null)     MaterialController.filterByType(res, type);
		if(keyword != null)  MaterialController.filterByName(res, keyword);
		if(!showUnavailable) MaterialController.filterByAvailability(res);
		
		JSONObject serializedMaterials = JsonSerializer.serializeMaterialList(res);
		JSONArray serializedTypesList = new JSONArray();
		serializedTypesList.addAll(Arrays.asList(MaterialType.values()).stream().map(Object::toString).collect(Collectors.toList()));
		JSONArray serializedOSList = new JSONArray();
		serializedOSList.addAll(Arrays.asList(OperatingSystem.values()).stream().map(Object::toString).collect(Collectors.toList()));
		JSONObject response = new JSONObject();
		response.put("items", serializedMaterials);
		response.put("type-list",  serializedTypesList);
		response.put("os-list",  serializedOSList);
		return new RsText(response.toJSONString());
	}
	
}
