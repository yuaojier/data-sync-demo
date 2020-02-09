package aop;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import utils.StringPool;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class MethodLogInterceptor extends AbstractAnnotationInterceptor<MethodLog> {

    private Map<Integer, MethodParam[]> methodLogMap = Maps.newConcurrentMap();

    public MethodLogInterceptor() {
        super(MethodLog.class);
    }

    @Override
    protected void doBefore(MethodInvocation invocation, MethodLog annotation) {
        MethodParam[] methodParams = getMethodLogParams(invocation, annotation);

        if (ArrayUtil.isEmpty(methodParams)) {
            if (log.isDebugEnabled()) {
                log.debug("{} @MethodLog paramNames: {}, paramsIndexs: {}", formatMethodName(invocation), Arrays.toString(annotation.paramNames()), Arrays.toString(annotation.paramIndex()));
            }
        } else {
            if (log.isInfoEnabled()) {
                StringBuilder sb = new StringBuilder(formatMethodName(invocation));
                sb.append(StringPool.SPACE);
                Object[] args = invocation.getArguments();
                for (int i = 0; i < methodParams.length; i++) {
                    MethodParam methodParam = methodParams[i];
                    if (i > 0) {
                        sb.append(StringPool.SPACE)
                                .append(StringPool.COMMA);
                    }
                    sb.append(methodParam.getName())
                            .append(StringPool.COLON)
                            .append(StringPool.SPACE)
                            .append(args[methodParam.getIndex()]);
                }

                log.info(sb.toString());
            }
        }

    }

    private MethodParam[] getMethodLogParams(MethodInvocation invocation, MethodLog annotation) {
        MethodParam[] methodParams = methodLogMap.get(invocation.getMethod().hashCode());
        if (methodParams == null) {
            String[] actParamNames = getParameterNames(invocation);
            int indexCount = annotation.paramIndex().length;
            if (indexCount > 0) {
                int[] paramIndex = annotation.paramIndex();

                methodParams = new MethodParam[Math.min(indexCount, actParamNames.length)];
                for (int i = 0; i < indexCount; i++) {
                    int index = paramIndex[i];
                    methodParams[i] = new MethodParam(actParamNames[index], index);
                }
            } else {
                String[] paramNames = annotation.paramNames();
                if (ArrayUtil.isEmpty(paramNames)) {
                    paramNames = actParamNames;
                }
                List<MethodParam> methodParamList = Lists.newLinkedList();
                for (String paramName : paramNames) {
                    if (!contains(methodParamList, paramName)) {
                        int index = ArrayUtil.indexOf(actParamNames, paramName);
                        if (index >= 0) {
                            methodParamList.add(new MethodParam(paramName, index));
                        }
                    }
                }
                methodParams = methodParamList.stream().toArray(MethodParam[]::new);
            }
            methodLogMap.put(invocation.getMethod().hashCode(), methodParams);
        }
        return methodParams;
    }

    private boolean contains(List<MethodParam> methodParamList, String paramName) {
        for (MethodParam methodParam : methodParamList) {
            if (methodParam.getName().equals(paramName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doAfter(MethodInvocation invocation, MethodLog annotation, Object methodReturn) {
        if (annotation.logReturn()) {
            if (log.isInfoEnabled()) {

                log.info("{} return {}", formatMethodName(invocation), methodReturn);
            }
        }
    }

    private String formatMethodName(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        return StrFormatter.format("{}#{}({})", method.getDeclaringClass().getName(), method.getName(), ArrayUtil.join(getParameterNames(invocation), StringPool.COMMA));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class MethodParam {
        private String name;

        private int index;
    }
}
