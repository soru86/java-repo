import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamOperationsDemo {

    public void runAllDemos() {
        System.out.println("=== Java Streams API Operations Demo ===");
        demonstrateFilter();
        demonstrateMap();
        demonstrateFlatMap();
        demonstrateDistinct();
        demonstrateSorted();
        demonstrateLimit();
        demonstrateSkip();
        demonstrateTakeWhile();
        demonstrateDropWhile();
        demonstratePeek();
        demonstrateForEach();
        demonstrateToArray();
        demonstrateCollectToList();
        demonstrateCollectToSet();
        demonstrateCollectToMap();
        demonstrateCollectGroupingBy();
        demonstrateCollectPartitioningBy();
        demonstrateReduce();
        demonstrateCount();
        demonstrateMin();
        demonstrateMax();
        demonstrateSum();
        demonstrateAverage();
        demonstrateSummaryStatistics();
        demonstrateAnyMatch();
        demonstrateAllMatch();
        demonstrateNoneMatch();
        demonstrateFindFirst();
        demonstrateFindAny();
        demonstrateGenerate();
        demonstrateIterate();
        demonstrateIterateWithPredicate();
        demonstrateFlatMapToInt();
        demonstrateFlatMapToLong();
        demonstrateFlatMapToDouble();
        demonstrateMapToLong();
        demonstrateMapToDouble();
        demonstrateMapMulti();
        demonstrateMapMultiToInt();
        demonstrateMapMultiToLong();
        demonstrateMapMultiToDouble();
        demonstrateStreamOf();
        demonstrateStreamEmpty();
        demonstrateStreamOfNullable();
        demonstrateStreamBuilder();
        demonstrateStreamConcat();
        demonstrateStreamToList();
        demonstrateForEachOrdered();
        demonstrateStreamSequential();
        demonstrateStreamUnordered();
        demonstrateOnClose();
        demonstrateParallel();
    }

    private void demonstrateFilter() {
        System.out.println("\n--- filter ---");
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
        System.out.println("Input: " + numbers);
        List<Integer> evens = numbers.stream()
                .filter(number -> number % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Output: " + evens);
    }

    private void demonstrateMap() {
        System.out.println("\n--- map ---");
        List<String> words = List.of("java", "stream", "api");
        System.out.println("Input: " + words);
        List<Integer> lengths = words.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println("Output: " + lengths);
    }

    private void demonstrateFlatMap() {
        System.out.println("\n--- flatMap ---");
        List<List<String>> teams = List.of(
                List.of("Alice", "Bob"),
                List.of("Carol", "Dave"));
        System.out.println("Input: " + teams);
        List<String> flattened = teams.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println("Output: " + flattened);
    }

    private void demonstrateDistinct() {
        System.out.println("\n--- distinct ---");
        List<Integer> numbers = List.of(1, 2, 2, 3, 3, 3, 4);
        System.out.println("Input: " + numbers);
        List<Integer> uniqueNumbers = numbers.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Output: " + uniqueNumbers);
    }

    private void demonstrateSorted() {
        System.out.println("\n--- sorted ---");
        List<String> words = List.of("banana", "apple", "pear", "orange");
        System.out.println("Input: " + words);
        List<String> sortedWords = words.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Output: " + sortedWords);
    }

    private void demonstrateLimit() {
        System.out.println("\n--- limit ---");
        List<Integer> numbers = List.of(10, 20, 30, 40, 50);
        System.out.println("Input: " + numbers);
        List<Integer> limited = numbers.stream()
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("Output: " + limited);
    }

    private void demonstrateSkip() {
        System.out.println("\n--- skip ---");
        List<Integer> numbers = List.of(5, 10, 15, 20, 25);
        System.out.println("Input: " + numbers);
        List<Integer> skipped = numbers.stream()
                .skip(2)
                .collect(Collectors.toList());
        System.out.println("Output: " + skipped);
    }

    private void demonstrateTakeWhile() {
        System.out.println("\n--- takeWhile ---");
        List<Integer> numbers = List.of(2, 4, 6, 1, 8);
        System.out.println("Input: " + numbers);
        List<Integer> taken = numbers.stream()
                .takeWhile(number -> number % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Output: " + taken);
    }

    private void demonstrateDropWhile() {
        System.out.println("\n--- dropWhile ---");
        List<Integer> numbers = List.of(2, 4, 6, 1, 8);
        System.out.println("Input: " + numbers);
        List<Integer> dropped = numbers.stream()
                .dropWhile(number -> number % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Output: " + dropped);
    }

    private void demonstratePeek() {
        System.out.println("\n--- peek ---");
        List<Integer> numbers = List.of(1, 2, 3);
        System.out.println("Input: " + numbers);
        List<Integer> processed = numbers.stream()
                .peek(number -> System.out.println("Peeked: " + number))
                .map(number -> number * number)
                .collect(Collectors.toList());
        System.out.println("Output: " + processed);
    }

    private void demonstrateForEach() {
        System.out.println("\n--- forEach ---");
        List<String> names = List.of("Alice", "Bob", "Carol");
        System.out.println("Input: " + names);
        System.out.print("Output: ");
        names.stream().forEach(name -> System.out.print(name + " "));
        System.out.println();
    }

    private void demonstrateToArray() {
        System.out.println("\n--- toArray ---");
        List<String> colors = List.of("red", "green", "blue");
        System.out.println("Input: " + colors);
        String[] colorArray = colors.stream().toArray(String[]::new);
        System.out.println("Output: " + Arrays.toString(colorArray));
    }

    private void demonstrateCollectToList() {
        System.out.println("\n--- collect to List ---");
        Stream<String> stream = Stream.of("spring", "summer", "autumn", "winter");
        List<String> seasons = stream.collect(Collectors.toList());
        System.out.println("Input: [spring, summer, autumn, winter]");
        System.out.println("Output: " + seasons);
    }

    private void demonstrateCollectToSet() {
        System.out.println("\n--- collect to Set ---");
        List<String> cities = List.of("Paris", "London", "Paris", "Berlin");
        System.out.println("Input: " + cities);
        Set<String> uniqueCities = cities.stream()
                .collect(Collectors.toSet());
        System.out.println("Output: " + uniqueCities);
    }

    private void demonstrateCollectToMap() {
        System.out.println("\n--- collect to Map ---");
        List<String> fruits = List.of("apple", "banana", "cherry");
        System.out.println("Input: " + fruits);
        Map<String, Integer> fruitLengthMap = fruits.stream()
                .collect(Collectors.toMap(
                        fruit -> fruit,
                        String::length));
        System.out.println("Output: " + fruitLengthMap);
    }

    private void demonstrateCollectGroupingBy() {
        System.out.println("\n--- groupingBy ---");
        List<String> animals = List.of("cat", "cow", "dog", "dove");
        System.out.println("Input: " + animals);
        Map<Integer, List<String>> grouped = animals.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println("Output: " + grouped);
    }

    private void demonstrateCollectPartitioningBy() {
        System.out.println("\n--- partitioningBy ---");
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        System.out.println("Input: " + numbers);
        Map<Boolean, List<Integer>> partitioned = numbers.stream()
                .collect(Collectors.partitioningBy(number -> number % 2 == 0));
        System.out.println("Output: " + partitioned);
    }

    private void demonstrateReduce() {
        System.out.println("\n--- reduce ---");
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        System.out.println("Input: " + numbers);
        Integer sum = numbers.stream()
                .reduce(0, Integer::sum);
        System.out.println("Output: " + sum);
    }

    private void demonstrateCount() {
        System.out.println("\n--- count ---");
        List<String> planets = List.of("Mercury", "Venus", "Earth", "Mars");
        System.out.println("Input: " + planets);
        long count = planets.stream().count();
        System.out.println("Output: " + count);
    }

    private void demonstrateMin() {
        System.out.println("\n--- min ---");
        List<Integer> numbers = List.of(5, 3, 9, 1, 7);
        System.out.println("Input: " + numbers);
        Optional<Integer> min = numbers.stream().min(Integer::compareTo);
        System.out.println("Output: " + min.orElse(null));
    }

    private void demonstrateMax() {
        System.out.println("\n--- max ---");
        List<Integer> numbers = List.of(5, 3, 9, 1, 7);
        System.out.println("Input: " + numbers);
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);
        System.out.println("Output: " + max.orElse(null));
    }

    private void demonstrateSum() {
        System.out.println("\n--- sum (mapToInt) ---");
        List<Integer> numbers = List.of(1, 2, 3);
        System.out.println("Input: " + numbers);
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Output: " + sum);
    }

    private void demonstrateAverage() {
        System.out.println("\n--- average (mapToInt) ---");
        List<Integer> numbers = List.of(2, 4, 6, 8);
        System.out.println("Input: " + numbers);
        OptionalDouble average = numbers.stream()
                .mapToInt(Integer::intValue)
                .average();
        System.out.println("Output: " + (average.isPresent() ? average.getAsDouble() : null));
    }

    private void demonstrateSummaryStatistics() {
        System.out.println("\n--- summaryStatistics ---");
        List<Integer> numbers = List.of(2, 4, 6, 8, 10);
        System.out.println("Input: " + numbers);
        IntSummaryStatistics statistics = numbers.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics();
        System.out.println("Output: " + statistics);
    }

    private void demonstrateAnyMatch() {
        System.out.println("\n--- anyMatch ---");
        List<String> words = List.of("cloud", "rain", "sun");
        System.out.println("Input: " + words);
        boolean anyLongerThanFour = words.stream().anyMatch(word -> word.length() > 4);
        System.out.println("Output: " + anyLongerThanFour);
    }

    private void demonstrateAllMatch() {
        System.out.println("\n--- allMatch ---");
        List<Integer> numbers = List.of(2, 4, 6);
        System.out.println("Input: " + numbers);
        boolean allEven = numbers.stream().allMatch(number -> number % 2 == 0);
        System.out.println("Output: " + allEven);
    }

    private void demonstrateNoneMatch() {
        System.out.println("\n--- noneMatch ---");
        List<Integer> numbers = List.of(1, 3, 5);
        System.out.println("Input: " + numbers);
        boolean noneEven = numbers.stream().noneMatch(number -> number % 2 == 0);
        System.out.println("Output: " + noneEven);
    }

    private void demonstrateFindFirst() {
        System.out.println("\n--- findFirst ---");
        List<Integer> numbers = List.of(5, 10, 15);
        System.out.println("Input: " + numbers);
        Optional<Integer> first = numbers.stream().findFirst();
        System.out.println("Output: " + first.orElse(null));
    }

    private void demonstrateFindAny() {
        System.out.println("\n--- findAny ---");
        List<String> names = List.of("Adam", "Brian", "Charlie");
        System.out.println("Input: " + names);
        Optional<String> any = names.stream().findAny();
        System.out.println("Output: " + any.orElse(null));
    }

    private void demonstrateGenerate() {
        System.out.println("\n--- Stream.generate ---");
        System.out.println("Input: Supplier generating \"Hi\"");
        List<String> greetings = Stream.generate(() -> "Hi")
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("Output: " + greetings);
    }

    private void demonstrateIterate() {
        System.out.println("\n--- Stream.iterate ---");
        System.out.println("Input: seed 1, unary operator n -> n + 2");
        List<Integer> sequence = Stream.iterate(1, n -> n + 2)
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("Output: " + sequence);
    }

    private void demonstrateIterateWithPredicate() {
        System.out.println("\n--- Stream.iterate with predicate ---");
        System.out.println("Input: seed 1, hasNext n < 10, next n + 3");
        List<Integer> sequence = Stream.iterate(1, n -> n < 10, n -> n + 3)
                .collect(Collectors.toList());
        System.out.println("Output: " + sequence);
    }

    private void demonstrateFlatMapToInt() {
        System.out.println("\n--- flatMapToInt ---");
        List<String> numbers = List.of("1 2", "3 4", "5 6");
        System.out.println("Input: " + numbers);
        int total = numbers.stream()
                .flatMapToInt(line -> Arrays.stream(line.split(" "))
                        .mapToInt(Integer::parseInt))
                .sum();
        System.out.println("Output: " + total);
    }

    private void demonstrateFlatMapToLong() {
        System.out.println("\n--- flatMapToLong ---");
        List<String> numbers = List.of("10000000000 20000000000", "30000000000");
        System.out.println("Input: " + numbers);
        long total = numbers.stream()
                .flatMapToLong(line -> Arrays.stream(line.split(" "))
                        .mapToLong(Long::parseLong))
                .sum();
        System.out.println("Output: " + total);
    }

    private void demonstrateFlatMapToDouble() {
        System.out.println("\n--- flatMapToDouble ---");
        List<String> numbers = List.of("1.5,2.5", "3.0");
        System.out.println("Input: " + numbers);
        double total = numbers.stream()
                .flatMapToDouble(line -> Arrays.stream(line.split(","))
                        .mapToDouble(Double::parseDouble))
                .sum();
        System.out.println("Output: " + total);
    }

    private void demonstrateMapToLong() {
        System.out.println("\n--- mapToLong ---");
        List<String> words = List.of("alpha", "beta", "gamma");
        System.out.println("Input: " + words);
        OptionalLong longestLength = words.stream()
                .mapToLong(String::length)
                .max();
        System.out.println("Output: " + (longestLength.isPresent() ? longestLength.getAsLong() : null));
    }

    private void demonstrateMapToDouble() {
        System.out.println("\n--- mapToDouble ---");
        List<Integer> numbers = List.of(1, 2, 3, 4);
        System.out.println("Input: " + numbers);
        double average = numbers.stream()
                .mapToDouble(number -> number * 1.5)
                .average()
                .orElse(0.0);
        System.out.println("Output: " + average);
    }

    private void demonstrateMapMulti() {
        System.out.println("\n--- mapMulti ---");
        List<String> phrases = List.of("java streams", "api demo");
        System.out.println("Input: " + phrases);
        List<String> words = phrases.stream()
                .mapMulti((String phrase, java.util.function.Consumer<String> downstream) -> Arrays
                        .stream(phrase.split(" ")).forEach(downstream))
                .collect(Collectors.toList());
        System.out.println("Output: " + words);
    }

    private void demonstrateMapMultiToInt() {
        System.out.println("\n--- mapMultiToInt ---");
        List<String> numbers = List.of("1 2", "3 4");
        System.out.println("Input: " + numbers);
        int total = numbers.stream()
                .mapMultiToInt((line, downstream) -> Arrays.stream(line.split(" "))
                        .mapToInt(Integer::parseInt)
                        .forEach(downstream))
                .sum();
        System.out.println("Output: " + total);
    }

    private void demonstrateMapMultiToLong() {
        System.out.println("\n--- mapMultiToLong ---");
        List<String> numbers = List.of("100 200", "300");
        System.out.println("Input: " + numbers);
        long total = numbers.stream()
                .mapMultiToLong((line, downstream) -> Arrays.stream(line.split(" "))
                        .mapToLong(Long::parseLong)
                        .forEach(downstream))
                .sum();
        System.out.println("Output: " + total);
    }

    private void demonstrateMapMultiToDouble() {
        System.out.println("\n--- mapMultiToDouble ---");
        List<String> numbers = List.of("1.1;2.2", "3.3");
        System.out.println("Input: " + numbers);
        double total = numbers.stream()
                .mapMultiToDouble((line, downstream) -> Arrays.stream(line.split(";"))
                        .mapToDouble(Double::parseDouble)
                        .forEach(downstream))
                .sum();
        System.out.println("Output: " + total);
    }

    private void demonstrateStreamOf() {
        System.out.println("\n--- Stream.of ---");
        System.out.println("Input: values -> red, green, blue");
        List<String> colors = Stream.of("red", "green", "blue")
                .collect(Collectors.toList());
        System.out.println("Output: " + colors);
    }

    private void demonstrateStreamEmpty() {
        System.out.println("\n--- Stream.empty ---");
        System.out.println("Input: Stream.empty()");
        long count = Stream.empty().count();
        System.out.println("Output: size " + count);
    }

    private void demonstrateStreamOfNullable() {
        System.out.println("\n--- Stream.ofNullable ---");
        String value = null;
        System.out.println("Input: value -> " + value);
        long count = Stream.ofNullable(value).count();
        System.out.println("Output: size " + count);
    }

    private void demonstrateStreamBuilder() {
        System.out.println("\n--- Stream.builder ---");
        System.out.println("Input: builder adds alpha, beta, gamma");
        List<String> values = Stream.<String>builder()
                .add("alpha")
                .add("beta")
                .add("gamma")
                .build()
                .collect(Collectors.toList());
        System.out.println("Output: " + values);
    }

    private void demonstrateStreamConcat() {
        System.out.println("\n--- Stream.concat ---");
        List<String> first = List.of("A", "B");
        List<String> second = List.of("C", "D");
        System.out.println("Input 1: " + first);
        System.out.println("Input 2: " + second);
        List<String> combined = Stream.concat(first.stream(), second.stream())
                .collect(Collectors.toList());
        System.out.println("Output: " + combined);
    }

    private void demonstrateStreamToList() {
        System.out.println("\n--- Stream.toList ---");
        System.out.println("Input: Stream.of 1, 2, 3, 4");
        List<Integer> numbers = Stream.of(1, 2, 3, 4).toList();
        System.out.println("Output: " + numbers);
    }

    private void demonstrateForEachOrdered() {
        System.out.println("\n--- forEachOrdered ---");
        List<Integer> numbers = IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toList());
        System.out.println("Input: " + numbers);
        System.out.print("Output: ");
        numbers.parallelStream().forEachOrdered(number -> System.out.print(number + " "));
        System.out.println();
    }

    private void demonstrateStreamSequential() {
        System.out.println("\n--- sequential ---");
        List<Integer> numbers = IntStream.rangeClosed(1, 4).boxed().collect(Collectors.toList());
        System.out.println("Input: " + numbers);
        boolean isParallel = numbers.parallelStream()
                .sequential()
                .isParallel();
        System.out.println("Output: isParallel -> " + isParallel);
    }

    private void demonstrateStreamUnordered() {
        System.out.println("\n--- unordered ---");
        List<String> items = List.of("one", "two", "three");
        System.out.println("Input: " + items);
        List<String> result = items.stream()
                .unordered()
                .collect(Collectors.toList());
        System.out.println("Output: " + result);
    }

    private void demonstrateOnClose() {
        System.out.println("\n--- onClose ---");
        System.out.println("Input: Stream.of x, y with onClose action");
        try (Stream<String> stream = Stream.of("x", "y")
                .onClose(() -> System.out.println("Stream closed"))) {
            List<String> values = stream.collect(Collectors.toCollection(ArrayList::new));
            System.out.println("Output: " + values);
        }
    }

    private void demonstrateParallel() {
        System.out.println("\n--- parallel ---");
        List<Integer> numbers = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        System.out.println("Input: " + numbers);
        List<Integer> squares = numbers.parallelStream()
                .map(number -> number * number)
                .collect(Collectors.toList());
        System.out.println("Output: " + squares);
    }
}
