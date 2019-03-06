package org.khu.benchmark.gbench.similarity;

import info.debatty.java.stringsimilarity.Damerau;
import info.debatty.java.stringsimilarity.Jaccard;
import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.MetricLCS;
import info.debatty.java.stringsimilarity.NGram;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

public class Similarity {
	public static enum SIMILARITY_MEASURE {Damerau, NormalizedLevenshtein, 
			JaroWinkler, MetricLCS, Jaccard, BiGram2, QuadGram4, NumericExact};
	/**
	 * Good for Manufacturer and Title
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static double applyDamerau(String s1, String s2) {
		Damerau d = new Damerau();
		double dist = d.distance(s1, s2);
		// . System.out.println("Distance:" + dist);
		double norm = s1.length() > s2.length() ? dist / s1.length() : dist / s2.length();
		return 1 - norm;
	}

	public static double applyNormalizedLevenshtein(String s1, String s2) {
		NormalizedLevenshtein l = new NormalizedLevenshtein();
		return l.similarity(s1, s2);
	}

	public static double applyJaroWinkler(String s1, String s2) {
		JaroWinkler jw = new JaroWinkler();
		return jw.similarity(s1, s2);
	}

	public static double applyMetricLCS(String s1, String s2) {
		MetricLCS lcs = new MetricLCS();
		return lcs.distance(s1, s2);
	}

	public static double applyJaccard(String s1, String s2) {
		Jaccard jc = new Jaccard();
		return jc.similarity(s1, s2);
	}
	
	public static double apply2Gram(String s1, String s2) {
		NGram ngram = new NGram(2);
		double dist = ngram.distance(s1, s2);
		return 1-dist;
	}
	
	public static double apply4Gram(String s1, String s2) {
		NGram ngram = new NGram(4);
		double dist = ngram.distance(s1, s2);
		return 1-dist;
	}
	
	public static double applyNumericExact(String s1, String s2) {
		
		if(s1 == null || s2 == null) {
			return 0.0;
		}
		s1 = s1.trim();
		s2 = s2.trim();
		if(s1.length() == 0 || s2.length() == 0) {
			return 0.0;
		}
		
		Double d1 = Double.parseDouble(s1);
		Double d2 = Double.parseDouble(s2);
		
		Double zero = 0.0;
		if(d1.equals(zero) || d2.equals(zero)) {
			return 0.0;
		}
		
		return d1.equals(d2) ? 1.0 : 0.0;
	}
	
	public static double measure(String s1, String s2, SIMILARITY_MEASURE sm) {
		double sim = 0.0;
		switch(sm) {
		case BiGram2:
			sim = apply2Gram(s1, s2);
			break;
		case Damerau:
			sim = applyDamerau(s1, s2);
			break;
		case Jaccard:
			sim = applyJaccard(s1, s2);
			break;
		case JaroWinkler:
			sim = applyJaroWinkler(s1, s2);
			break;
		case MetricLCS:
			sim = applyMetricLCS(s1, s2);
			break;
		case NormalizedLevenshtein:
			sim = applyNormalizedLevenshtein(s1, s2);
			break;
		case QuadGram4:
			sim = apply4Gram(s1, s2);
			break;
		case NumericExact:
			sim = applyNumericExact(s1, s2);
			break;
		default:
			System.err.println("Undefined similarity measure <" + sm.toString() + ">");
			break;
		}
		return sim; 
	}
	public static void main(String[] args) {


	    String s26 = "data protection suite";
	    String s312 = "instant architect design suite";
	    String s384 = "adobe production studio premium";
	    String s992 = "power production storyboard artist 4";
	    String s1133 = "power production storyboard quick";
	    
	    
		System.out.println("==============Apply NormalizedLevinstein=============");
		System.out.println(Similarity.measure(s26, s312, SIMILARITY_MEASURE.NormalizedLevenshtein));
		System.out.println(Similarity.measure(s384, s1133, SIMILARITY_MEASURE.NormalizedLevenshtein));
		System.out.println(Similarity.measure(s992, s1133, SIMILARITY_MEASURE.NormalizedLevenshtein));
        
	}

}
