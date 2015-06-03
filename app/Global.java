import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.F.*;
import play.libs.F;

import static play.mvc.Results.*;

import play.mvc.Action;
import play.mvc.Http.Request;
import java.lang.reflect.Method;

public class Global extends GlobalSettings {

	public Action onRequest(Request request, Method actionMethod) {
        return super.onRequest(request, actionMethod);
    }
  
    @Override
    public Promise<Result> onError(RequestHeader request, Throwable t) {
        return Promise.<Result>pure(internalServerError(
            views.html.errorPage.render()
        ));
    }

  	@Override
    public Promise<Result> onHandlerNotFound(RequestHeader request) {
        return Promise.<Result>pure(internalServerError(
            views.html.notFoundPage.render(request.uri())
        ));
    }

}