import java.util.*;
import java.util.stream.*;

public class WaterFlow {

    public static void main(String [] args) {
        System.out.printf("Hello Water Flow Solution #1%n");
        if (args != null && args.length == 1 && args[0].toLowerCase().equals("-usage")) {
            System.out.printf("java WaterFlow%n");
            return;
        }

        WaterFlow waterFlow = new WaterFlow();

        List<List<Integer>> positions = new ArrayList<>();

        int [] column1 = new int [] {1, 3, 2, 6, 5};
        List<Integer> list1 = Arrays.stream(column1).boxed().collect(Collectors.toList());

        int [] column2 = new int [] {2, 2, 4, 7, 1};
        List<Integer> list2 = Arrays.stream(column2).boxed().collect(Collectors.toList());

        int [] column3 = new int [] {2, 3, 5, 1, 1};
        List<Integer> list3 = Arrays.stream(column3).boxed().collect(Collectors.toList());

        int [] column4 = new int [] {3, 4, 3, 4, 2};
        List<Integer> list4 = Arrays.stream(column4).boxed().collect(Collectors.toList());

        int [] column5 = new int [] {5, 4, 1, 5, 4};
        List<Integer> list5 = Arrays.stream(column5).boxed().collect(Collectors.toList());

        positions.add(list1);

        positions.add(list2);

        positions.add(list3);

        positions.add(list4);

        positions.add(list5);
        
        Set<int[]> result = waterFlow.findPositionsToFlowIntoBothOceans(positions);

        System.out.printf("number of positions = %d%n", result.size());
    }

    public Set<int[]> findPositionsToFlowIntoBothOceans(List<List<Integer>> positions) {
        Set<int[]> result = new HashSet<>();

        return result;
    }

}
