package org.talentboost.finaltask.backend.util;

import org.springframework.stereotype.Service;

@Service
public class Env {
    public String get(String env) {
        return System.getenv(env);
    }
}
