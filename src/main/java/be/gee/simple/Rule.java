package be.gee.simple;

public class Rule {
    private final String criterion;
    private final String action;

    public Rule(String criterion, String action) {
        this.criterion = criterion;
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        if (!criterion.equals(rule.criterion)) return false;
        return action.equals(rule.action);
    }

    @Override
    public int hashCode() {
        int result = criterion.hashCode();
        result = 31 * result + action.hashCode();
        return result;
    }

    public String getCriterion() {
        return criterion;
    }

    public String getAction() {
        return action;
    }

}
