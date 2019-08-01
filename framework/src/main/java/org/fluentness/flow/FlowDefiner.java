package org.fluentness.flow;

import org.fluentness.base.common.exception.DefinitionException;

@FunctionalInterface
public interface FlowDefiner {
    void define(Flow flow) throws DefinitionException;
}
