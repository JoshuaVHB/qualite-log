package reserve.view.entry;

import java.util.ArrayList;
import java.util.List;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.misc.Href;
import org.takes.rq.RqHref;

import reserve.Main;
import reserve.controller.MaterialController;
import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;
import reserve.util.Logger;

public class ListItems implements Take {
	
	private static final Logger logger = Main.getLogger("list-items", Logger.LEVEL_INFO);

	@Override
	public Response act(Request req) throws Exception {
		Href href = new RqHref.Base(req).href();
		
		// api/list_items?foo=LAPTOP&bar=5
		//MaterialType type = FormUtils.getParamEnum(href, "foo", MaterialType.class, true);
		//Integer truc = FormUtils.getParamInt(href, "bar", false);
		//System.err.println(type + " " + truc);
		MaterialType type = FormUtils.getParamEnum(href, "type", MaterialType.class, true);
		OperatingSystem os = FormUtils.getParamEnum(href, "OS", OperatingSystem.class, true);
		String keyword = FormUtils.getParamString(href, "key",MaterialController.KEYWORD_FILTER, true);
		
		List<Material> res = new ArrayList<>();
		res = MaterialController.filterByOS(os);
		MaterialController.filterByType(res, type);
		
		throw new Exception("not implemented");
	}
	
}
