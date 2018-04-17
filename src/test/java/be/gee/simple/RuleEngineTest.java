package be.gee.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RuleEngineTest {
    Map<String, Object> context;
    Rule rule;
    User user1;

    @Before
    public void setUpContext() throws Exception {
        user1 = new User();
        user1.name = "James Bond";
        user1.department = "MI-6";
        user1.telephone = "confidential";
        context = new HashMap<>();
        context.put("user", user1);
    }

    @Before
    public void setUpRule() throws Exception {
        rule = new Rule("user.name =~ '.*Bond' and user.department == 'MI-6'", "BOND");
    }

    @Test
    public void findSpy() throws Exception {
        RuleEngine engine = new RuleEngine();
        engine.add(rule);
        Set<String> actions = engine.getActions(context);
        Assert.assertTrue(actions.contains("BOND"));
    }

    @Test
    public void findSpyManyRules() throws Exception {
        RuleEngine engine = new RuleEngine();
        engine.add(rule);
        for (int i = 0; i < 1000; i++) {
            engine.add(new Rule("user.telephone == '" +
                    new Random().nextInt(200_000) + "'", "WHATEVER"));
        }
        Set<String> actions = engine.getActions(context);
        Assert.assertTrue(actions.contains("BOND"));
    }

    @Test
    public void findSpyManyUsers() throws Exception {
        RuleEngine engine = new RuleEngine();
        engine.add(rule);
        for (int i = 0; i < 1000; i++) {
            engine.add(new Rule("user.telephone == '" +
                    i + "'", "PHONE_" + i));
        }
        for (int i = 0; i < 1000; i=i+3) {
            user1.telephone = "" + i;
            Set<String> actions = engine.getActions(context);
            Assert.assertTrue(actions.contains("PHONE_"+i));
        }
    }

    public class User

    {
        String name;
        String department;
        String telephone;

        public String getName() {
            return name;
        }

        public String getDepartment() {
            return department;
        }

        public String getTelephone() {
            return telephone;
        }
    }
}