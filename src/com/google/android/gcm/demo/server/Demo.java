package com.google.android.gcm.demo.server;
import java.util.ArrayList;

import edu.buffalo.cse.ubicomp.crowdonline.asker.*;
import edu.buffalo.cse.ubicomp.crowdonline.collector.*;
import edu.buffalo.cse.ubicomp.crowdonline.db.*;
import edu.buffalo.cse.ubicomp.crowdonline.decider.*;
import edu.buffalo.cse.ubicomp.crowdonline.user.*;

public class Demo {

	public static void runDemo() {
		DB qdb = new QuestionDB();
		Asker asker = new TwitterAsker();
//		Collector collector = new TwitterCollector();

		ArrayList<Question> questions= new ArrayList<Question>(); 

		Question q = new Question(1, "“Yavuklu” kelimesinin kökünde yer alan “yavuk” ne anlama gelmektedir?");
		q.addChoice("Nişan");
		q.addChoice("Uzun saç");
		q.addChoice("Yemin");
		q.addChoice("Helal");
		q.setCorrectChoice('A');
		questions.add(q);
		//Trial comments
		//System.out.println(qdb.add(q));
		//System.out.println(qdb.getLast());
		//TwitterAsker ta = new TwitterAsker();
		//ta.update("Sent via Java");
		
		q = new Question(2, "“Müruruzaman” ne demektir?","Zaman aşımı","Zaman kaybı","Zamansız","Zamane",'A');
		questions.add(q);
		q = new Question(3, "Türkiye’de, Anayasa Mahkemesi kurulduğundan beri kaç parti kapatılmıştır?","10","15","20","25",'D');
		questions.add(q);
		q = new Question(4, "KKTC’nin milli marşının adı nedir?","Kıbrıs Marşı","Güzel Ada","İstiklal Marşı","Ada Marşı",'C');
		questions.add(q);
		q = new Question(1, "Hangisi balonun mucididir?","Wright Kardesler","Mongolfier Kardesler","Karamazov Kardesler","Uygur Kardesler",'B');
		questions.add(q);
		q = new Question(2, "Eski Roma’da gladyatör dövüşlerinin yapıldığı “arena” nın kelime anlamı nedir?","Kavga","Kum","Kan","Kılıç",'B');
		questions.add(q);
		q = new Question(3, "1972'de Apollo 17 uzay aracı mürettebatınca çekilen ve yerküreyi bütün olarak gösteren fotoğrafın adı nedir?","Yalnız Gezegen","Mavi Bilye","Büyük Ev","Sessiz Cennet",'B');
		questions.add(q);
		q = new Question(4, "Türkiye’de Nisan 1993’te, internet ilk olarak nerede kullanılmaya başlanmıştır?","ODTÜ","TÜBİTAK","Genelkurmay Başkanlığı","MİT",'A');
		questions.add(q);
		q = new Question(5, "Hangisi “porsuk” kelimesinin anlamlarından biri değildir?","Ağaç türü","Hayvan","Akarsu","Müzik aleti",'D');
		questions.add(q);
		q = new Question(6, "Cep telefonuyla iletişim olanağından uzak kalma korkusuna ne ad verilir?","Elektrofobi","Sosyofobi","Agorafobi","Nomofobi",'D');
		questions.add(q);
		q = new Question(7, "”Demir Ökçe”, “Deniz Kurdu”, “Martin Eden” kitaplarının yazarı kimdir?","Mark Twain","Maksim Gorki","Jack London","Ernest Hemingway",'C');
		questions.add(q);
		q = new Question(8, "“Çanakkale içinde aynalı çarşı” şarkısı hangi yöreye aittir?","Yozgat","Çanakkale","Kastamonu","İzmir",'C');
		questions.add(q);
		q = new Question(9, "Elektrikli sandalyeyi keşfeden Alfred Southwick’in mesleği neydi?","Mühendis","Dişçi","Cellat","Cerrah",'B');
		questions.add(q);
		
		for(Question qu:questions){
		
			System.out.println(qu);
			DBHandler.getQuestionDB().add(qu);
			asker.ask(qu);
			try {
				Thread.sleep(45000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		collector.collect();
//		System.out.println(((Question)qdb.getLast()).getChoices());
	}

}
