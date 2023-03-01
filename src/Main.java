import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Main m1 = new Main();
        System.out.println("Введите фразу:");
        m1.printWordsStatistics(m1.readString());
    }

    public void printWordsStatistics(String string){
        if(string == null || string.isBlank()){
            System.out.println("Рассматриваемая строка не должна быть пустой.");
            return;
        }
        //Без использования Java Stream API
        //String[] strings = string.replaceAll("[^a-zA-zа-яА-Я ]","")
        //        .trim()
        //        .split("\\s");


        String[] strings = Arrays.stream(string.split("[^a-zA-zа-яА-Я]"))
                .filter(s -> s.trim().length() > 0)
                .toArray(String[]::new);

        System.out.println("Количество слов в тексте: "+strings.length);
        System.out.println("Количество слов в тексте (с помощью Java Stream API): "+Arrays.stream(strings).count());

        AtomicInteger counter = new AtomicInteger(1);

        Map<String,Long> map1 = Arrays.stream(strings)
                .collect(
                        Collectors.groupingBy(
                                s -> s,
                                Collectors.counting()
                        )
                );
        System.out.println("Количество уникальных слов в тексте: "+ map1.entrySet().size());
        System.out.println("Количество уникальных слов в тексте (с помощью Java Stream API): "+ map1.entrySet().stream().count() +"\n");

        System.out.println("TOP10 слов:");
        System.out.printf("%2s %-15s %s","№","Слово","Количество повторов\n");

        map1.entrySet()
                .stream()
                //.sorted((e1, e2) -> e1.getValue() >= e2.getValue() ? (e1.getValue() > e2.getValue() ? -1 : 0): 1)
                //.sorted(Comparator.comparing(Map.Entry::getValue,Comparator.reverseOrder()))
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .forEach(x -> System.out.printf("%2d %-15s %-4d \n",counter.getAndIncrement(),x.getKey(),x.getValue()));
    }

    public String readString(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try{
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}