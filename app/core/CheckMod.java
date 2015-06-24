package core;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.F.*;
import play.libs.F;

import static play.mvc.Results.*;

import play.mvc.Action;
import play.mvc.Http.Request;
import java.lang.reflect.Method;

public class CheckMod extends play.mvc.Action.Simple {
	public F.Promise<Result> call(Http.Context ctx) throws Throwable {
		
		if(ctx.session().get("right") != null){
			if(ctx.session().get("right").equals("user")){
				
				return F.Promise.pure(redirect(controllers.routes.Application.index()));
			}
			else if (ctx.session().get("right").equals("mod")) {
				
			}
		}
		else{
			
			return F.Promise.pure(redirect(controllers.routes.Application.index()));
		}
		
		return delegate.call(ctx);
	}
}