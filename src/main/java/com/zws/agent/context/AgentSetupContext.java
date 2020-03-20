package com.zws.agent.context;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author zhengws
 * @date 2020-03-14 21:57
 */
public class AgentSetupContext implements IAgentContext {

    private Set<String> patterns;

    public boolean isMatch(String className) {
        if (className == null){
            return false;
        }
        className = className.replaceAll("/", ".");
        boolean match = false;
        if (patterns != null) {
            for (String regex : patterns) {
                match = Pattern.matches(regex, className);
                if (match) {
                    break;
                }
            }
        }
        return match;
    }

    public void setPackagePattern(String pattern) {
        String[] split = pattern.split(";");
        this.patterns = new HashSet<String>(Arrays.asList(split));
    }
}
