package com.cn.zhaohjf.lucene.test;

import java.io.IOException;
import java.nio.file.Paths;
 
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
 
public class Second {
 
    public static void main(String[] args) throws IOException, ParseException {
        Analyzer a = new StandardAnalyzer();
        Directory dir = FSDirectory.open(Paths.get("./index"));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher is = new IndexSearcher(reader);
        QueryParser parser = new QueryParser("info", a);
        Query query = parser.parse("lucene");
        TopDocs topDocs = is.search(query, 1000);
        System.out.println("�ܹ�ƥ����ٸ���" + topDocs.totalHits);
        ScoreDoc[] hits = topDocs.scoreDocs;
        // Ӧ����topDocs.totalHits��ͬ
        System.out.println("���������ݣ�" + hits.length);
        for (ScoreDoc scoreDoc : hits) {
            System.out.println("ƥ��÷֣�" + scoreDoc.score);
            System.out.println("�ĵ�����ID��" + scoreDoc.doc);
            Document document = is.doc(scoreDoc.doc);
            System.out.println(document.get("info"));
        }
        reader.close();
        dir.close();
    }
 
}
