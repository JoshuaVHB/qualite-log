package reserve.view.entry;

import org.takes.Response;
import org.takes.facets.fallback.Fallback;
import org.takes.facets.fallback.FbFixed;
import org.takes.facets.fallback.FbWrap;
import org.takes.misc.Opt;
import org.takes.rs.RsText;
import org.takes.rs.RsWithStatus;

public class FbError extends FbWrap {

	public FbError(Class<? extends Throwable> catchable, Response response) {
		this(catchable, new FbFixed(response));
	}
	
	public FbError(Class<? extends Throwable> catchable, Fallback response) {
		super(req -> {
			if(req.throwable() != null && catchable.isAssignableFrom(req.throwable().getClass()))
				return response.route(req);
			return new Opt.Empty<>();
		});
	}
	
	public static FbError withStatus(Class<? extends Throwable> catchable, int status) {
		return new FbError(catchable, req -> new Opt.Single<>(
				new RsWithStatus(new RsText(req.throwable().getMessage()), status))
		);
	}
	
}
