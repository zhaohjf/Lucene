package com.cn.zhaohjf.lucene.test;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class LuceneTest {
	public static void main(String[] args) throws IOException, ParseException {
	    Analyzer analyzer = new StandardAnalyzer();

	    //�������洢���ڴ���
	    Directory directory = new RAMDirectory();
	    //������������洢��Ӳ���ϣ�ʹ������Ĵ������
	    //Directory directory = FSDirectory.open(Paths.get("/tmp/testindex"));
	    IndexWriterConfig config = new IndexWriterConfig(analyzer);
	    IndexWriter iwriter = new IndexWriter(directory, config);

	    String[] texts = new String[]{
	        "Mybatis��ҳ��� - ʾ��",
	        "Mybatis �����ʴ� ��һ��",
	        "Mybatis ʾ��֮ ����(complex)����(property)",
	        "Mybatis����(��)��(��)��(��)��һ����ҳ���",
	        "Mybatis ��Log4j��־������� - �Լ��й���־����������",
	        "Mybatis ʾ��֮ foreach ���£�",
	        "Mybatis ʾ��֮ foreach ���ϣ�",
	        "Mybatis ʾ��֮ SelectKey",
	        "Mybatis ʾ��֮ Association (2)",
	        "Mybatis ʾ��֮ Association"
	    };

	    for (String text : texts) {
	        Document doc = new Document();
	        doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
	        iwriter.addDocument(doc);
	    }
	    iwriter.close();

	    //��ȡ��������ѯ
	    DirectoryReader ireader = DirectoryReader.open(directory);
	    IndexSearcher isearcher = new IndexSearcher(ireader);
	    //����һ���򵥵Ĳ�ѯ
	    QueryParser parser = new QueryParser("fieldname", analyzer);
	    Query query = parser.parse("foreach");
	    ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
	    //����������
	    for (int i = 0; i < hits.length; i++) {
	        Document hitDoc = isearcher.doc(hits[i].doc);
	        System.out.println(hitDoc.get("fieldname"));
	    }
	    ireader.close();
	    directory.close();
	}
}
