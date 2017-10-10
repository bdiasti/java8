package br.com.java8.consumers;

import java.util.function.Consumer;

import br.com.java8.model.Usuario;

public class Mostrador implements Consumer<Usuario> {

	public void accept(Usuario t) {
		System.out.println(t.getNome());
	}

}
