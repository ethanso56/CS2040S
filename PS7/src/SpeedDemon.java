package src;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class SpeedDemon {

    public int processData(String filename){
        // TODO: implement this method
        HashMap<Integer, HashMap<Integer, Integer>> hashMap = new HashMap<>();
        try {
            FileReader dataFile = new FileReader(filename);
            BufferedReader bufferedDataFile = new BufferedReader(dataFile);
            String line = null;
            while ((line = bufferedDataFile.readLine()) != null) {
                char[] a = line.toCharArray();
                Arrays.sort(a);
                String text =  String.valueOf(a);
                int hcOfText = text.hashCode();
                String reversedText = new StringBuilder(text).reverse().toString();
                int hcOfReversedText = reversedText.hashCode();
                if (hashMap.containsKey(hcOfText)) {
                    HashMap<Integer, Integer> inner = hashMap.get(hcOfText);
                    if (inner.containsKey(hcOfReversedText)) {
                        Integer counter = inner.get(hcOfReversedText);
                        counter++;
                        inner.put(hcOfReversedText, counter);
                    } else {
                        inner.put(hcOfReversedText, 0);
                    }
                } else {
                    HashMap<Integer, Integer> inner = new HashMap<>();
                    inner.put(hcOfReversedText, 0);
                    hashMap.put(hcOfText, inner);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int sum = 0;
        for (HashMap<Integer, Integer> hm: hashMap.values()) {
            for (Integer i: hm.values()) {
                if (i > 0) {
                    int x = ((i * (i + 1)) / 2);
                    sum += x;
                }
            }
        }
        return sum;
    }
    public static void main(String[] args){
        SpeedDemon dataProcessor = new SpeedDemon();
        StopWatch watch = new StopWatch();
        watch.start();
        int answer = dataProcessor.processData("databases/sample_C.in");
        watch.stop();
        System.out.println(answer);
        System.out.println(watch.getTime());
    }

}

