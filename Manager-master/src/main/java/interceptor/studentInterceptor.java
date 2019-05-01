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
        Map parameters = action.getParameters();
        String userName = String.valueOf(parameters.get("userName"));
        Map<String, Object> session = action.getSession();
        User user = (User) session.get("currentUser");

        String result = null;
        if (user != null && user.getUserType().equals("学生") && userName.equals("Empty{name='userName'}")) {
            result = "index";
        } else {
            actionInvocation.invoke();
        }
        return result;
    }
}
