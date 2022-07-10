package com.javarush.task.task22.task2209;

import java.io.*;
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collections;

/* 
Составить цепочку слов
*/
///Users/admin/Desktop/JavaRushTasks/3.JavaMultithreading/src/com/javarush/task/task22/task2209/txt

public class Solution {
    public static void main(String[] args) {
        String[] words = new String[0];
        try(BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader reader = new BufferedReader(new FileReader(console.readLine()))){
            String txt = "";
            while (reader.ready())
                txt+=" "+reader.readLine();
            words = txt.trim().split(" ");
        }catch (IOException ex){}
        //...

        StringBuilder result = getLine(words);
        System.out.println(result.toString());
    }

    public static Line addition(ArrayList<Word> words, Line line){
        if(words.size()==0){
            line.setFlag(true);
            return line;
        }

        ArrayList<Word> newWords = new ArrayList<>();
        Line newLine = new Line(line.line);
        for (int i = 0;i < words.size()&&line.flag!=true;i++) {
            line = new Line(newLine.line);
            newWords = new ArrayList<>();
            newWords.addAll(words);
            newWords.remove(i);
            if(line.lastLetter.equals(words.get(i).firstLatter)){
                line.addToEnd(words.get(i));
                line = addition(newWords,line);
                if(line.flag==true)
                    break;
            }
            line = new Line(newLine.line);
            if(line.firstLetter.equals(words.get(i).lastLatter)&&!line.flag) {
                line.addToStart(words.get(i));
                line = addition(newWords,line);
                if(line.flag==true)
                    break;
            }
        }
        return line;
    }

    public static StringBuilder getLine(String... words) {
        if(words.length==0)
            return new StringBuilder("");
        ArrayList<Word> wordsList = new ArrayList<>();
        ArrayList<Word> noConnect = new ArrayList<>();
        for (String word : words) {
            wordsList.add(new Word(word));
        }

        for (int i = 0;i<wordsList.size();i++) {
            boolean flag = false;
            for(int j = 0;j<words.length;j++){
                if(j==i) continue;
                if(wordsList.get(i).firstLatter.equals(wordsList.get(j).lastLatter)){
                    flag = true;
                    break;
                }
                if(wordsList.get(i).lastLatter.equals(wordsList.get(j).firstLatter)){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                noConnect.add(wordsList.get(i));
                wordsList.remove(wordsList.get(i));
            }
        }
        Line line = new Line("BAD");
        for (int i = 0; i < words.length; i++) {
            line = new Line(wordsList.get(i).toString());
            ArrayList<Word> newWords = new ArrayList<>();
            newWords = new ArrayList<>();
            newWords.addAll(wordsList);
            newWords.remove(i);
            line = addition(newWords,line);
            if(line.flag==true)
                break;
        }
        for (int i = 0; i < noConnect.size(); i++) {
            line.addToEnd(noConnect.get(i));
        }
        return new StringBuilder(line.line);
    }
        static class Line implements Cloneable{
            boolean flag;
            String line;

            String firstLetter;
            String lastLetter;
            Line(String line){
                this.flag = false;
                this.line = line;
                this.lastLetter = line.toLowerCase().substring(line.length()-1);
                this.firstLetter = line.toLowerCase().substring(0,1);
            }

            public void addToEnd(Word word){
                this.line +=" "+word;
                this.lastLetter = line.toLowerCase().substring(line.length()-1);
            }

            public void addToStart(Word word){
                this.line = word+" "+line;
                this.firstLetter = line.toLowerCase().substring(0,1);
            }

            public void setFlag(boolean value){
                this.flag = value;
            }
        }
        static class Word {
            String word;
            String lastLatter;
            String firstLatter;

            Word(String word) {
                this.word = word;
                lastLatter = word.toLowerCase().substring(word.length() - 1);
                firstLatter = word.toLowerCase().substring(0, 1);
            }

            @Override
            public String toString() {
                return word;
            }
        }
    }
