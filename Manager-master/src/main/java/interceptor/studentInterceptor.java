package interceptor;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import model.User;

import java.util.Map;

public class studentInterceptor implements Interceptor {
    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext action = actionInvocation.getInvocationContext();
        Map<String, Object> session = action.getSession();
        User user = (User) session.get("currentUser");
        String result = null;
        if (user.getUserType().equals("管理员")) {
            actionInvocation.invoke();
        }else {
            result = "login";
        }
        return result;
    }
}
