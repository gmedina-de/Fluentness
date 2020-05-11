package org.fluentness.service.loader;

import org.fluentness.model.RawShape;
import org.fluentness.model.Shape;
import org.fluentness.model.Texture;
import org.fluentness.service.Service;

public interface Loader extends Service {

    Shape loadShape(String path);

    Shape loadShape(RawShape rawShape);

    Texture loadTexture(String path);

}