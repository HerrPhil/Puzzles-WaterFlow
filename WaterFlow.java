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

        List<List<Integer>> elevations = new ArrayList<>();

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

        elevations.add(list1);

        elevations.add(list2);

        elevations.add(list3);

        elevations.add(list4);

        elevations.add(list5);
        
        Set<Position> result = waterFlow.findPositionsToFlowIntoBothOceans(elevations);

        System.out.printf("number of positions = %d%n", result.size());
    }

    public Set<Position> findPositionsToFlowIntoBothOceans(List<List<Integer>> elevations) {

        // Here we leverage that fact that java.util.LinkedList
        // implements java.util.Deque, which gives us the
        // queuing behaviour we want for BFS.

        Deque<Position> pacificQueue = new LinkedList<>();
        Set<Position> pacificSeen = new HashSet<>();

        Deque<Position> atlanticQueue = new LinkedList<>();
        Set<Position> atlanticSeen = new HashSet<>();

        int m = elevations.size();

        int n = elevations.get(0).size();

        // We assume the pacific shore is from the top-left corner
        // to the top-right corner and bottom-left corner.

        // Add all the pacific shoreline positions that flow to the pacific
        // to the the pacific queue and pacific seen set.
        // Sea level elevation is 0, less than all elevations in elevations list.
        for (int j = 0; j < n; j++) {
            Position position = new Position(0, j);
            pacificQueue.addLast(position);
            pacificSeen.add(position);
            System.out.printf("%s added to pacific set%n", position.toString());
        }

        // Start at 1 to avoid duplicating the position at 0 added above.
        for (int i = 1; i < m; i++) {
            Position position = new Position(i, 0);
            pacificQueue.addLast(position);
            pacificSeen.add(position);
        }

        // Add all the atlantic shorelinee positions that flow to the atlantic
        // to the atlantic queue and atlantic set.
        // Sea level elevation is 0, less than all elevations in elevations list.
        for (int i = 0; i < m; i++) {
            Position position = new Position(i, n - 1);
            atlanticQueue.addLast(position);
            atlanticSeen.add(position);
        }

        // Stop before n - 1 to avoid duplicating the position at n - 1 added above.
        for (int j = 0; j < n - 1; j++) {
            Position position = new Position(m - 1, j);
            atlanticQueue.addLast(position);
            atlanticSeen.add(position);
        }

        System.out.printf("Expect 9 queue pacific shoreline positions, actual queue = %d%n", pacificQueue.size());
        System.out.printf("Expect 9 seen pacific shoreline positions, actual seen = %d%n", pacificSeen.size());
                
        System.out.printf("Expect 9 queue atlantic shoreline positions, actual queue = %d%n", atlanticQueue.size());
        System.out.printf("Expect 9 seen atlantic shoreline positions, actual seen = %d%n", atlanticSeen.size());

        System.out.printf("Get Pacific positions%n");
        getPositions(elevations, m, n, pacificQueue, pacificSeen);

        System.out.printf("Get Atlantic positions%n");
        getPositions(elevations, m, n, atlanticQueue, atlanticSeen);

        // This will be the intersection of pacific and atlantic positions.
        Set<Position> result = new HashSet<>();

        return result;
    }

    private void getPositions(List<List<Integer>> elevations, int m, int n, Deque<Position> queue, Set<Position> seen) {

        System.out.printf("Apply the get positions domain logic to these elevations%n");
        System.out.printf("Use this queue for looping%n");
        System.out.printf("Store upstream positions in this set of seen positions%n");

        while (queue.peekFirst() != null) {

            Position position = queue.removeFirst();
            int i = position.rowCoordinate();
            int j = position.columnCoordinate();

            System.out.printf("Popped %s%n", position.toString());

            // Breadth-first search (BFS) for elevation.
            // For every position that is x + 1, x - 1, y + 1 and y - 1,
            // Do the following, ...

            int[][] offsets = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            for (int index = 0; index < offsets.length; index++) {
                int[] offset = offsets[index];
                int rowOffset = offset[0];
                int columnOffset = offset[1];
                System.out.printf("Analyze with position offset (%d, %d)%n", rowOffset, columnOffset);

                // Ensure the i + row offset is in bounds
                int row = i + rowOffset;
                if (!(0 <= row && row < m)) continue;
                System.out.printf("The upstream row coordinate %d is valid%n", row);

                // Ensure the j + column offset is in bounds
                int column = j + columnOffset;
                if (!(0 <= column && column < n)) continue;
                System.out.printf("The upstream column coordinate %d is valid%n", column);

                // Business rule: height at offset position is
                // greater than or equal to this position.
                // When this is the case, then water flows downstream to the current position
                int upstreamElevation = elevations.get(row).get(column);
                int currentElevation = elevations.get(i).get(j);
                if (!(upstreamElevation >= currentElevation)) continue;
                System.out.printf("The upstream elevation %d >= current elevation %d%n", upstreamElevation, currentElevation);

                // Verified the upstream position is valid and upstream
                final Position upstreamPosition = new Position(row, column);

                // Business rule: the offset position has not been seen
                Optional<Position> optionalPosition = seen.stream().filter(check -> check.equals(upstreamPosition)).findAny();
                boolean foundUpstreamPosition = optionalPosition.isPresent();
                if (foundUpstreamPosition) continue;
                System.out.printf("The upstream %s has not been seen%n", upstreamPosition.toString());

                // Therefore, the upstream position should be
                // added to the set of seen positions
                // and the queue for more BFS analysis.
                seen.add(upstreamPosition);
                queue.addLast(upstreamPosition);
                
            }

        }

        System.out.printf("The queue is empty%n");
        System.out.printf("There are %d upstream positions%n", seen.size());
    }

}
