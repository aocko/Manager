package interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import java.util.Map;

public class LoginInterceptor implements Interceptor {
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ActionContext action = invocation.getInvocationContext();
        Map<String, Object> session = action.getSession();
        Object currentUser = session.get("currentUser");
        String result = null;
        if (currentUser != null) {
            invocation.invoke();
        }else {
            result = "login";
        }
        return result;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }


}
