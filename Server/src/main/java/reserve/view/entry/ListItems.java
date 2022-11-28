package reserve.view.entry;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.misc.Href;
import org.takes.rq.RqHref;

import reserve.model.MaterialType;
import reserve.util.AnsiLogger;
import reserve.util.Logger;

public class ListItems implements Take {
	
	private static final Logger logger = new AnsiLogger("list-items");

	@Override
	public Response act(Request req) throws Exception {
		Href href = new RqHref.Base(req).href();
		
		// api/list_items?foo=LAPTOP&bar=5
		MaterialType type = FormUtils.getParamEnum(href, "foo", MaterialType.class, true);
		Integer truc = FormUtils.getParamInt(href, "bar", false);
		System.err.println(type + " " + truc);
		
		throw new Exception("not implemented");
	}
	
}
