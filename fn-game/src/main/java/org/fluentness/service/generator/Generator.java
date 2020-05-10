package org.fluentness.service.generator;

import org.fluentness.model.Shape;
import org.fluentness.service.MultiService;
import org.fluentness.service.Service;

@MultiService
public interface Generator extends Service {

    Shape generate(int vertexCount, float size);
}
