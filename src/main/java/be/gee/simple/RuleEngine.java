package be.gee.simple;

import org.apache.commons.jexl3.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RuleEngine extends HashSet<Rule> implements Set<Rule> {
    // Create or retrieve an engine
    private final JexlEngine jexl = new JexlBuilder().create();

    Set<String> getActions(Map<String, Object> context) {
        JexlContext localContext = new MapContext();
        for (String key : context.keySet()) {
            localContext.set(key, context.get(key));
        }
        Set<String> ret = new HashSet<String>();
        for (Rule rule : this) {
            Object evaluated =
                    ((CompiledRule) rule).expression.evaluate(localContext);
            if (new Boolean(evaluated.toString()).booleanValue()) {
                ret.add(rule.getAction());
            }
        }
        return ret;
    }

    @Override
    public boolean add(Rule rule) {
        return super.add(new CompiledRule(rule));
    }

    private class CompiledRule extends Rule {
        final JexlExpression expression;

        private CompiledRule(Rule rule) {
            super(rule.getCriterion(), rule.getAction());
            this.expression = jexl.createExpression(rule.getCriterion());
        }

        private JexlExpression getExpression() {
            return expression;
        }
    }
}
