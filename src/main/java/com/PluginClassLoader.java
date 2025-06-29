package com;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Loads classes from ./plugins at runtime.
 */
public class PluginClassLoader extends ClassLoader {

    private final Path root;

    public PluginClassLoader(String rootDir) {
        super(Main.class.getClassLoader());
        this.root = Paths.get(rootDir).toAbsolutePath();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Path classFile = root.resolve(name.replace('.', '/') + ".class");
        if (!Files.exists(classFile)) {
            throw new ClassNotFoundException(name);
        }
        try {
            byte[] bytes = Files.readAllBytes(classFile);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException ex) {
            throw new ClassNotFoundException("Cannot load " + name, ex);
        }
    }
}
