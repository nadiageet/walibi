package com.nadia;

public interface Requirement {
    boolean test(User user);
   boolean verifyAccess(User user);
}
