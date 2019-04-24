package cn.edu.zzti.zut.utils;

import cn.edu.zzti.zut.model.LockKey;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;

public class KeyParser {

    private KeyParser() {
    }

    private static ExpressionParser parser = new SpelExpressionParser();

    private static ParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    public static LockKey getSpelDefinitionKey(String expression, Method method, Object[] parameterValues) {
        if (StringUtils.isNotEmpty(expression)) {
            EvaluationContext context = new MethodBasedEvaluationContext(null, method, parameterValues, nameDiscoverer);
            return new LockKey(parser.parseExpression(expression).getValue(context).toString());
        }
        return null;
    }

}
