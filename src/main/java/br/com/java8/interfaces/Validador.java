package br.com.java8.interfaces;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface Validador<T> {

	boolean valida(T t);

	// Exemplo de default method
	default Consumer<T> andThen(Consumer<? super T> after) {

		Objects.requireNonNull(after);
		return (T t) -> {
			valida(t);
		};
	}
}
