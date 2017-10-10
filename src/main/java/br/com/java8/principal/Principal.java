package br.com.java8.principal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.java8.consumers.Mostrador;
import br.com.java8.interfaces.Validador;
import br.com.java8.model.Usuario;

public class Principal {
	
	//This  diamond sintaxe works after java 7
	private static List<Usuario> usuarios = new ArrayList<>();
	
	
	public static void main(String[] args) {
		
		//Increment list tests
		usuarios.add(new Usuario("Bernardo 1", 100));
		usuarios.add(new Usuario("Bernardo 2", 200));
		usuarios.add(new Usuario("Bernardo 3", 300));
		usuarios.add(new Usuario("Bernardo 4", 400));
		
		//Tests
		iteraListaDaAntesJava8();
		iteraListaDepoisJava8();
		iteraListaDepoisJava8ComClasseAnonima();
		iteraListaDepoisJava8ComLambda();
		testandoUmaInterfaceFuncionalPropria();
		usandoPredicateParaRemoveIf();
		comparatorComLambda();
		sortComLambda();
		listaDeComparator();
		methodReference();
		testingStreamWithFilter();		
		testingReturnLikeCollect();
		testingBadPracticeGlobalEscope();
		mapExternalListUsingMap();
		averageList();
		usingOptionalForMap();
		throwExceptionIfNothing();
		usingOptionalIfPresent();
		sortingByStream();
		findAnyInList();
		usingReduce();
		usingJoining();
		testingDateJava();
		testingPeriodDateJava();
		testingDuration();
		
		
	}
		
	private static void iteraListaDaAntesJava8(){

		for(Usuario u: usuarios){
			System.out.println(u.getNome());
		}
	}
	
	private static void iteraListaDepoisJava8(){		

		//Using functional interface
		Mostrador mostrador = new Mostrador();

		//Novo metodo na interface List do java
		usuarios.forEach(mostrador);
		
	}
	
	private static void iteraListaDepoisJava8ComClasseAnonima(){
		
		
		//Gera uma classe com nome estranho na execução
		Consumer<Usuario> mostrador = new Consumer<Usuario>(){
			public void accept(Usuario u){
				System.out.println(u.getNome());
			}	
		};
		//Novo metodo na interface List do java
		usuarios.forEach(mostrador);

	}
	
	
	private static void iteraListaDepoisJava8ComLambda(){
			
		//Cria lambda
		Consumer<Usuario> mostrador = (Usuario u) -> {System.out.println(u.getNome());};		
		usuarios.forEach(mostrador);
		
		//Modo compacto
		usuarios.forEach(u -> System.out.println(u.getNome()));
		
		
	}
	
	private static void testandoUmaInterfaceFuncionalPropria(){
		Validador<String> validador = t -> true;
		System.out.println(validador);
	}
	
	private static void usandoPredicateParaRemoveIf(){
				
		Predicate<Usuario> predicado = new Predicate<Usuario>(){
			public boolean test(Usuario str){
				return str.getNome().equals("Bernardo 4");
			}
		};
		
		usuarios.removeIf(predicado);
		usuarios.forEach(u -> u.getNome());
		
	}
	
	private static void comparatorComLambda(){
		
		
		Comparator<Usuario> compare = (u1,u2) -> u1.getNome().compareTo(u2.getNome());
		Collections.sort(usuarios, compare);
		
	}
	
	private static void sortComLambda(){

		usuarios.sort((u1, u2) -> u1.getNome().compareTo(u2.getNome()));
		
	}
	
	private static void listaDeComparator(){
		Comparator.naturalOrder();
		Comparator.reverseOrder();
		
	}
	
	private static void methodReference(){
		
		//Tratado igual lambda
		Consumer<Usuario> tornaModerador = Usuario::getNome;
		usuarios.forEach(tornaModerador);
	}
	
	
	private static void  testingStreamWithFilter(){
		
		usuarios.stream().
		filter(u-> u.getPontos() >= 100).
		forEach(System.out::println);
		

	}
	
	private static void testingReturnLikeCollect() {

		usuarios.stream()
		.filter(u -> u.getPontos() > 100)
		.collect(Collectors.toList());

	}
	
	private static void testingBadPracticeGlobalEscope(){
		
		List<Integer> pontos = new ArrayList<>();

		 // bad practice using external variable
		 usuarios.stream().
				forEach(u -> pontos.add(u.getPontos()));
				
	}
	
	private static void mapExternalListUsingMap() {

		usuarios.stream()
		.map(u -> u.getPontos())
		.collect(Collectors.toList());

	}
	
	private static void averageList() {

		usuarios.stream()
		.mapToInt(Usuario::getPontos)
		.average()
		.getAsDouble();
	}
	
	private static void usingOptionalForMap(){
		
		OptionalDouble media = usuarios.stream()
							   .mapToInt(u -> u.getPontos())
							   .average();
		
		media.orElse(0.0); // if nothing return 0

	}
	
	
	private static void throwExceptionIfNothing() {

		usuarios.stream()
		.mapToInt(u -> u.getPontos()).average()
		.orElseThrow(IllegalStateException::new);

	}

	private static void usingOptionalIfPresent(){
		
		usuarios.stream()
		.mapToInt(u -> u.getPontos())
		.average()
		.ifPresent(valor ->System.out.println("Exists values"));
		
	}
	
	private static void sortingByStream(){
		
		usuarios.stream()
		.filter(u -> u.getPontos() > 100)
		.sorted(Comparator.comparing(Usuario::getNome))
		.collect(Collectors.toList());
		
	}
	
	private static void findAnyInList() {

		usuarios.stream()
		.filter(u -> u.getPontos() > 100)
		.peek(System.out::println) 
		.findAny();

	}
	
	private static void usingReduce() {

		usuarios.stream()
		.mapToInt(Usuario::getPontos)
		.reduce(0, (a, b) -> a + b);

	}
	

	
	
	private static void getNamesFiles() throws IOException{
		
		Files.list(Paths.get("."))
		.filter(p -> p.toString()
		.endsWith(".java"))
		.forEach(System.out::println);
		
	}
	
	private static void usingJoining() {

		usuarios.stream()
		.map(Usuario::getNome)
		.collect(Collectors.joining(","));

	}
	
	private static void testingDateJava(){
		
		//Add month for local date
		LocalDate mesQueVemjava8 = LocalDate.now().plusMonths(1);
		
		
		//Today with hours and minutes
		LocalDateTime agora = LocalDateTime.now();
		
		//Concatenate date with hours
		LocalTime agoraSoData = LocalTime.now();
		LocalDate hoje = LocalDate.now();
		LocalDateTime dataEhora = hoje.atTime(agoraSoData);
		
		//Using timezone
		ZonedDateTime dataComHoraETimezone = dataEhora
				.atZone(ZoneId.of("America/Sao_Paulo"));
		
		//factory method for generate date
		LocalDate date = LocalDate.of(2014, 12, 25);
		LocalDateTime dateTime = LocalDateTime.of(2014, 12, 25, 10, 30);
		
		
		//Generating a localDateTime with a pattern
		LocalDateTime agora3 = LocalDateTime.now();
		agora3.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				
	}
	
	
	private static void testingPeriodDateJava(){
	
		LocalDate agora4 = LocalDate.now();
		LocalDate outraData5 = LocalDate.of(2015, Month.JANUARY, 25);
		
		//Using period because we are using LocalDate
		Period periodo = Period.between(outraData5, agora4);
		
		System.out.printf("%s dias, %s meses e %s anos",
		periodo.getDays(), periodo.getMonths(), periodo.getYears());
		if (periodo.isNegative()) {
			periodo = periodo.negated();
		}
		
		
	}
	
	
	private static void testingDuration(){
		
		LocalDateTime agora2 = LocalDateTime.now();
		LocalDateTime daquiAUmaHora = LocalDateTime.now().plusHours(1);
		
		//Using Duration
		Duration duration = Duration.between(agora2, daquiAUmaHora);
		if (duration.isNegative()) {
			duration = duration.negated();
		}
		
		
	}
	

}
