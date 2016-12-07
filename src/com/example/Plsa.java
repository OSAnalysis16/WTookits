package com.example;

import com.algorithm.TermWeighting;
import com.conf.Configuration;
import com.input.DataHolder;
import com.main.MainHolder;

import java.util.*;

/**
 * create by qibai
 */
public class Plsa {

    private int topicNum;

    private int docSize;

    private int vocabularySize;

    private int[][] docTermMatrix;

    //p(z|d)
    private double[][] docTopicPros;

    //p(w|z)
    private double[][] topicTermPros;

    //p(z|d,w)
    private double[][][] docTermTopicPros;

    private List<String> allWords;

    public List<String> getOrder() {
        return order;
    }

    private List<String> order;

    public Plsa(int numOfTopic) {
        topicNum = numOfTopic;
        docSize = 0;
    }

    /**
     * train plsa
     *
     * @param docs all documents
     */
    public void train(Map<String, Map<String, Double>> docs, int maxIter) {
        if (docs == null) {
            throw new IllegalArgumentException("The documents set must be not null!");
        }

        //statistics vocabularies
        allWords = statisticsVocabularies(docs);
        order = new ArrayList<>(docs.keySet());
        //element represent times the word appear in this document 
        docTermMatrix = new int[docSize][vocabularySize];
        //init docTermMatrix
        for (int docIndex = 0; docIndex < docSize; docIndex++) {
            Map<String, Double> doc = docs.get(order.get(docIndex));
            for (Map.Entry<String,Double> word : doc.entrySet()) {
                if (allWords.contains(word.getKey())) {
                    int wordIndex = allWords.indexOf(word.getKey());
                    docTermMatrix[docIndex][wordIndex] += word.getValue();
                }
            }

        }

        docTopicPros = new double[docSize][topicNum];
        topicTermPros = new double[topicNum][vocabularySize];
        docTermTopicPros = new double[docSize][vocabularySize][topicNum];

        //init p(z|d),for each document the constraint is sum(p(z|d))=1.0
        for (int i = 0; i < docSize; i++) {
            double[] pros = randomProbilities(topicNum);
            for (int j = 0; j < topicNum; j++) {
                docTopicPros[i][j] = pros[j];
            }
        }
        //init p(w|z),for each topic the constraint is sum(p(w|z))=1.0
        for (int i = 0; i < topicNum; i++) {
            double[] pros = randomProbilities(vocabularySize);
            for (int j = 0; j < vocabularySize; j++) {
                topicTermPros[i][j] = pros[j];
            }
        }


        //use em to estimate params
        for (int i = 0; i < maxIter; i++) {
            em();
            System.out.println("iter---"+i);
        }

        System.out.println("done");
    }

    /**
     * EM algorithm
     */
    private void em() {
        /*
         * E-step,calculate posterior probability p(z|d,w,&),& is
         * model params(p(z|d),p(w|z))
         * 
         * p(z|d,w,&)=p(z|d)*p(w|z)/sum(p(z'|d)*p(w|z'))
         * z' represent all posible topic
         * 
         */
        for (int docIndex = 0; docIndex < docSize; docIndex++) {
            for (int wordIndex = 0; wordIndex < vocabularySize; wordIndex++) {
                double total = 0.0;
                double[] perTopicPro = new double[topicNum];
                for (int topicIndex = 0; topicIndex < topicNum; topicIndex++) {
                    double numerator = docTopicPros[docIndex][topicIndex]
                            * topicTermPros[topicIndex][wordIndex];
                    total += numerator;
                    perTopicPro[topicIndex] = numerator;
                }

                if (total == 0.0) {
                    total = avoidZero(total);
                }

                for (int topicIndex = 0; topicIndex < topicNum; topicIndex++) {
                    docTermTopicPros[docIndex][wordIndex][topicIndex] = perTopicPro[topicIndex]
                            / total;
                }
            }
        }

        //M-step
        /*
         * update p(w|z),p(w|z)=sum(n(d',w)*p(z|d',w,&))/sum(sum(n(d',w')*p(z|d',w',&)))
         * 
         * d' represent all documents
         * w' represent all vocabularies
         * 
         * 
         */
        for (int topicIndex = 0; topicIndex < topicNum; topicIndex++) {
            double totalDenominator = 0.0;
            for (int wordIndex = 0; wordIndex < vocabularySize; wordIndex++) {
                double numerator = 0.0;
                for (int docIndex = 0; docIndex < docSize; docIndex++) {
                    numerator += docTermMatrix[docIndex][wordIndex]
                            * docTermTopicPros[docIndex][wordIndex][topicIndex];
                }

                topicTermPros[topicIndex][wordIndex] = numerator;

                totalDenominator += numerator;
            }

            if (totalDenominator == 0.0) {
                totalDenominator = avoidZero(totalDenominator);
            }

            for (int wordIndex = 0; wordIndex < vocabularySize; wordIndex++) {
                topicTermPros[topicIndex][wordIndex] = topicTermPros[topicIndex][wordIndex]
                        / totalDenominator;
            }
        }
        /*
         * update p(z|d),p(z|d)=sum(n(d,w')*p(z|d,w'&))/sum(sum(n(d,w')*p(z'|d,w',&)))
         * 
         * w' represent all vocabularies
         * z' represnet all topics
         * 
         */
        for (int docIndex = 0; docIndex < docSize; docIndex++) {
            //actually equal sum(w) of this doc
            double totalDenominator = 0.0;
            for (int topicIndex = 0; topicIndex < topicNum; topicIndex++) {
                double numerator = 0.0;
                for (int wordIndex = 0; wordIndex < vocabularySize; wordIndex++) {
                    numerator += docTermMatrix[docIndex][wordIndex]
                            * docTermTopicPros[docIndex][wordIndex][topicIndex];
                }
                docTopicPros[docIndex][topicIndex] = numerator;
                totalDenominator += numerator;
            }

            if (totalDenominator == 0.0) {
                totalDenominator = avoidZero(totalDenominator);
            }

            for (int topicIndex = 0; topicIndex < topicNum; topicIndex++) {
                docTopicPros[docIndex][topicIndex] = docTopicPros[docIndex][topicIndex]
                        / totalDenominator;
            }
        }
    }

    private List<String> statisticsVocabularies(Map<String, Map<String, Double>> docs) {
        Set<String> uniqWords = new HashSet<String>();
        for (Map.Entry<String, Map<String, Double>> doc : docs.entrySet()) {
            uniqWords.addAll(doc.getValue().keySet());
            docSize++;
        }
        vocabularySize = uniqWords.size();

        return new LinkedList<String>(uniqWords);
    }

    /**
     * Get a normalize array
     *
     * @param size
     * @return
     */
    public double[] randomProbilities(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("The size param must be greate than zero");
        }
        double[] pros = new double[size];

        int total = 0;
        Random r = new Random();
        for (int i = 0; i < pros.length; i++) {
            //avoid zero
            pros[i] = r.nextInt(size) + 1;

            total += pros[i];
        }

        //normalize
        for (int i = 0; i < pros.length; i++) {
            pros[i] = pros[i] / total;
        }

        return pros;
    }

    /**
     * @return
     */
    public double[][] getDocTopics() {
        return docTopicPros;
    }

    /**
     * @return
     */
    public double[][] getTopicWordPros() {
        return topicTermPros;
    }

    /**
     * @return
     */
    public List<String> getAllWords() {
        return allWords;
    }

    /**
     * Get topic number
     *
     * @return
     */
    public Integer getTopicNum() {
        return topicNum;
    }

    /**
     * Get p(w|z)
     *
     * @param word
     * @return
     */
    public double[] getTopicWordPros(String word) {
        int index = allWords.indexOf(word);
        if (index != -1) {
            double[] topicWordPros = new double[topicNum];
            for (int i = 0; i < topicNum; i++) {
                topicWordPros[i] = topicTermPros[i][index];
            }
            return topicWordPros;
        }

        return null;
    }

    /**
     * avoid zero number.if input number is zero, we will return a magic
     * number.
     */
    private final static double MAGICNUM = 0.0000000000000001;

    public double avoidZero(double num) {
        if (num == 0.0) {
            return MAGICNUM;
        }

        return num;
    }

    public static void main(String[] args) {

        String termWeightStr = "TF";
        String dirPath = "/home/qibai/Documents/JavaProject/WTookits/data";
        String categoryPath = "";
        String destPath = "/home/qibai/Documents/JavaProject/WTookits/data/result.txt";
        Configuration conf = new Configuration(termWeightStr, dirPath, categoryPath, destPath);

        //  initial mainHolder. One conf match one mainHolder, and then match one termWeighting.
        MainHolder mainHolder = new MainHolder(conf);
        TermWeighting tw = mainHolder.getTermWeighting();
        System.out.println(tw.getClass().getName());

        DataHolder dh = mainHolder.getDataHolder();
        System.out.println(dh.getClass().getName());

        List<Map.Entry<String, String>> data = dh.getData();
        Map<String, String> fileToCate = dh.getFileToCate();
        List<Map.Entry<String, List<String>>> splitData = tw.split(data);
        Map<String, Map<String, Double>> docs = tw.calculate(splitData, fileToCate);
        Plsa plsa = new Plsa(2);
        plsa.train(docs, 50);

        List<String> order = plsa.getOrder();
        double[][] docTopicPros = plsa.getDocTopics();
        for (int i = 0; i < order.size(); i++) {
            String headline = order.get(i);
            System.out.println(headline);
            for (int j = 0; j < docTopicPros[i].length; j++) {
                double pro = docTopicPros[i][j];
                System.out.println(j+" topic pro " + pro);
            }
        }
    }
}
