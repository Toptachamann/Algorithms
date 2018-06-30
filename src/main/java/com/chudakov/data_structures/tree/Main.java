package com.chudakov.data_structures.tree;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.chudakov.data_structures.tree.Main.Action.ADD;
import static com.chudakov.data_structures.tree.Main.Action.CONTAINS;
import static com.chudakov.data_structures.tree.Main.Action.DELETE;
import static com.chudakov.data_structures.tree.Main.Action.NEXT;
import static com.chudakov.data_structures.tree.Main.Action.ORDER;
import static com.chudakov.data_structures.tree.Main.Action.PREV;

public class Main {

  private static final String TXT_PATH = "C:\\Users\\timof\\Documents\\GSoC 2018\\Tmp\\algo.txt";
  private static final String TEST_CASE_PATH =
      "C:\\Users\\timof\\Documents\\GSoC 2018\\Tmp\\algo_test_case.txt";

  public static void main(String[] args) {
    //testSet(10000, 30, 0, 50);
    convertIntoTest(TXT_PATH, TEST_CASE_PATH );
  }

  private static void testSet(int testCases, int queryNum, int start, int end) {
    for (int i = 0; i < testCases; i++) {
      System.out.println("Test case " + (i + 1));
      List<Pair<Action, Integer>> queries = generateQueries(queryNum, start, end);
      exportQueries(queries, TXT_PATH);
      convertIntoTest(TXT_PATH, TEST_CASE_PATH);
      executeQueries(queries);
    }
  }

  private static void convertIntoTest(String inPath, String outPath) {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File(inPath)));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outPath)))) {
      int n = Integer.valueOf(reader.readLine());
      writer.write("def set = new AvlTreeSet<>()");
      writer.newLine();
      writer.write("Object[][] arr = [");
      for (int i = 0; i < n; i++) {
        String[] tokens = reader.readLine().split("\\s+");
        writer.write("['" + tokens[0] + "', " + tokens[1] + "]");
        if (i < n - 1) {
          writer.write(",");
        }
        if (i % 7 == 0 && i != 0) {
          writer.newLine();
        }
      }
      writer.write("]");
      writer.newLine();
      writer.write("expect:");
      writer.newLine();
      writer.write("isSortedWithoutDuplicates(set.getSorted())");
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void executeQueries(List<Pair<Action, Integer>> queries) {
    AvlTreeSet<Integer> set = new AvlTreeSet<>();
    for (Pair<Action, Integer> query : queries) {
      int i = query.getValue();
      switch (query.getKey()) {
        case ADD:
          set.add(i);
          break;
        case DELETE:
          set.remove(i);
          break;
        case CONTAINS:
          set.contains(i);
          break;
        case ORDER:
          set.orderStatistics(i);
          break;
        case NEXT:
          set.next(i);
          break;
        case PREV:
          set.prev(i);
          break;
      }
    }
    List<Integer> sorted = set.getSorted();
    if (!isSortedWithoutDuplicates(sorted)) {
      throw new RuntimeException();
    }
  }

  private static void exportQueries(List<Pair<Action, Integer>> queries, String path) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)))) {
      writer.write(String.valueOf(queries.size()));
      writer.newLine();
      for (Pair<Action, Integer> query : queries) {
        writer.write(query.getKey().toString());
        writer.write(" ");
        writer.write(query.getValue().toString());
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static List<Pair<Action, Integer>> generateQueries(int queryNum, int start, int end) {
    List<Pair<Action, Integer>> queries = new ArrayList<>();
    Random random = new Random(System.nanoTime());
    for (int i = 0; i < queryNum; i++) {
      int act = random.nextInt(6);
      Action action = null;
      switch (act) {
        case 0:
          action = ADD;
          break;
        case 1:
          action = DELETE;
          break;
        case 2:
          action = CONTAINS;
          break;
        case 3:
          action = NEXT;
          break;
        case 4:
          action = PREV;
          break;
        case 5:
          action = ORDER;
          break;
      }
      int value = random.nextInt(end - start + 1) + start;
      queries.add(new ImmutablePair<>(action, value));
    }
    return queries;
  }

  private static boolean isSortedWithoutDuplicates(List<Integer> list) {
    for (int i = 1; i < list.size(); i++) {
      if (list.get(i) <= list.get(i - 1)) {
        return false;
      }
    }
    return true;
  }

  enum Action {
    ADD {
      @Override
      public String toString() {
        return "a";
      }
    },
    DELETE {
      @Override
      public String toString() {
        return "d";
      }
    },
    CONTAINS {
      @Override
      public String toString() {
        return "c";
      }
    },
    ORDER {
      @Override
      public String toString() {
        return "o";
      }
    },
    NEXT {
      @Override
      public String toString() {
        return "n";
      }
    },
    PREV {
      @Override
      public String toString() {
        return "p";
      }
    };

    public abstract String toString();
  }
}
