package interceptor;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@MethodTrace
public class MethodTraceInterceptor {

    @AroundInvoke
    public Object logCall(InvocationContext context) throws Exception {
        Logger log = Logger.getLogger(context.getTarget().getClass().toString());
        log.log(Level.INFO, "{0} ({1})", new Object[]{context.getMethod().getName(), extractParameters(context)});

        return context.proceed();
    }

    private String extractParameters(InvocationContext context) {
        StringBuilder params = new StringBuilder();
        if (context != null && context.getParameters() != null) {
            boolean first = true;
            for (Object param : context.getParameters()) {
                if (param != null) {
                    if (!first) {
                        params.append(", ");
                    }
                    params.append(param.toString());
                    first = false;
                }
            }
        }
        return params.toString();
    }

}
