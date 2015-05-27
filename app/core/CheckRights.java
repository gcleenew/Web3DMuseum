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

public class CheckRights extends play.mvc.Action.Simple {
	public F.Promise<Result> call(Http.Context ctx) throws Throwable {
		// if(request.cookie("PLAY_SESSION") != null){
		// 	if(ctx.session().get("username").equals("user")){
		// 	System.out.println("You are just a standart user.");
		// 	}
		// 	else if (ctx.session().get("right").equals("mod")) {
		// 		System.out.println("You are a amazing administrator!");
		// 	}
		// }
		
		return delegate.call(ctx);
	}
}