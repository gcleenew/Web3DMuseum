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
		System.out.println(ctx.session());
		if(ctx.session().get("right") != null){
			if(ctx.session().get("right").equals("user")){
				System.out.println("You are just a standart user. Get out.");
				return F.Promise.pure(redirect(controllers.routes.Application.index()));
			}
			else if (ctx.session().get("right").equals("mod")) {
				System.out.println("You are a amazing administrator! Welcome.");
			}
		}
		else{
			System.out.println("You are not even connected ! Go back from where you came !");
			return F.Promise.pure(redirect(controllers.routes.Application.index()));
		}
		
		return delegate.call(ctx);
	}
}