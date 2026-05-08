import java.util.*;

public class PageReplacementAlgorithms {

    // 1. Algoritmo FIFO (First In, First Out)
    public static int runFIFO(int[] pages, int frames) {
        int faults = 0;
        Queue<Integer> memory = new LinkedList<>();
        Set<Integer> set = new HashSet<>();

        for (int page : pages) {
            if (!set.contains(page)) {
                faults++;
                if (memory.size() == frames) {
                    int removed = memory.poll();
                    set.remove(removed);
                }
                memory.add(page);
                set.add(page);
            }
        }
        return faults;
    }

    // 2. Algoritmo LRU (Least Recently Used)
    public static int runLRU(int[] pages, int frames) {
        int faults = 0;
        List<Integer> memory = new LinkedList<>();

        for (int page : pages) {
            if (!memory.contains(page)) {
                faults++;
                if (memory.size() == frames) {
                    memory.remove(0); // O menos recentemente usado fica no início
                }
            } else {
                memory.remove((Integer) page);
            }
            memory.add(page); // O mais recentemente usado vai para o final
        }
        return faults;
    }

    // 3. Algoritmo do Relógio (Clock)
    public static int runClock(int[] pages, int frames) {
        int faults = 0;
        int[] memory = new int[frames];
        int[] useBit = new int[frames];
        Arrays.fill(memory, -1);
        int pointer = 0;

        for (int page : pages) {
            boolean found = false;
            for (int i = 0; i < frames; i++) {
                if (memory[i] == page) {
                    useBit[i] = 1; // Segunda chance
                    found = true;
                    break;
                }
            }

            if (!found) {
                faults++;
                while (true) {
                    if (useBit[pointer] == 0) {
                        memory[pointer] = page;
                        useBit[pointer] = 1;
                        pointer = (pointer + 1) % frames;
                        break;
                    } else {
                        useBit[pointer] = 0; // Tira a segunda chance
                        pointer = (pointer + 1) % frames;
                    }
                }
            }
        }
        return faults;
    }

    // 4. Algoritmo Ótimo (Optimal)
    public static int runOptimal(int[] pages, int frames) {
        int faults = 0;
        List<Integer> memory = new ArrayList<>();

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            if (!memory.contains(page)) {
                faults++;
                if (memory.size() < frames) {
                    memory.add(page);
                } else {
                    int farthest = -1;
                    int replaceIndex = -1;
                    for (int j = 0; j < memory.size(); j++) {
                        int memPage = memory.get(j);
                        int nextUse = Integer.MAX_VALUE;
                        for (int k = i + 1; k < pages.length; k++) {
                            if (pages[k] == memPage) {
                                nextUse = k;
                                break;
                            }
                        }
                        if (nextUse == Integer.MAX_VALUE) {
                            replaceIndex = j;
                            break; // Se não for mais usada, substitui essa
                        }
                        if (nextUse > farthest) {
                            farthest = nextUse;
                            replaceIndex = j;
                        }
                    }
                    if (replaceIndex != -1) {
                        memory.set(replaceIndex, page);
                    }
                }
            }
        }
        return faults;
    }
}
