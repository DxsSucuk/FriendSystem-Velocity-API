package me.sk8ingduck.friendsystem.config;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Config {

	private final Path file;
	private final Map<String, Object> data;

	public Config(String name, Path path) {
		file = path.resolve(name);

		if (!Files.exists(file)) {
			try {
				Files.createDirectories(path);
				Files.createFile(file);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		try (InputStream in = Files.newInputStream(file)) {
			Map<String, Object> loaded = new Yaml().load(in);
			data = loaded != null ? loaded : new HashMap<>();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected Path getFile() {
		return file;
	}

	public Object getPathOrSet(String path, Object defaultValue) {
		Object value = get(path);
		if (value == null) {
			set(path, defaultValue);
			save();
			return defaultValue;
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	private Object get(String path) {
		Map<String, Object> current = data;
		String[] keys = path.split("\\.");
		for (int i = 0; i < keys.length - 1; i++) {
			Object next = current.get(keys[i]);
			if (!(next instanceof Map)) {
				return null;
			}
			current = (Map<String, Object>) next;
		}
		return current.get(keys[keys.length - 1]);
	}

	@SuppressWarnings("unchecked")
	private void set(String path, Object value) {
		Map<String, Object> current = data;
		String[] keys = path.split("\\.");
		for (int i = 0; i < keys.length - 1; i++) {
			current = (Map<String, Object>) current.computeIfAbsent(keys[i], k -> new HashMap<String, Object>());
		}
		current.put(keys[keys.length - 1], value);
	}

	private void save() {
		try (Writer writer = Files.newBufferedWriter(file)) {
			new Yaml().dump(data, writer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}