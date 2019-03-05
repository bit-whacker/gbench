package org.khu.benchmark.gbench.similarity;

import info.debatty.java.stringsimilarity.Damerau;
import info.debatty.java.stringsimilarity.Jaccard;
import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.MetricLCS;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

public class Similarity {

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
		return l.distance(s1, s2);
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

	public static void main(String[] args) {
		/*
		 * System.out.println(applyDamerau("ABCDEF", "ABCDEF")); // 1 substitution
		 * System.out.println(applyDamerau("ABCDEF", "ABDCEF"));
		 * 
		 * // 2 substitutions System.out.println(applyDamerau("ABCDEF", "BACDFE"));
		 * 
		 * // 1 deletion System.out.println(applyDamerau("CDEF", "CDE"));
		 * 
		 * System.out.println(applyDamerau("ABCDEF", "BCDEF"));
		 * System.out.println(applyDamerau("ABCDEF", "ABCGDEF"));
		 * 
		 * // All different System.out.println(applyDamerau("ABCDEF", "POMJEU"));
		 * 
		 * System.out.println(applyDamerau("mn", "abcde"));
		 */

		// 776: title:adobe software acrobat pro upgrd pro-pro mac (adbcd01798mc)
		// 1167: title:chronos stickybrain 3.0 (mac)
		String s778 = "micromat techtool pro 4 (mac)";
		String s1169 = "micromat techtool protege with firewire device";
		String s1114 = "railroad tycoon 3 (mac)";
		String s646 = "microspot macdraft pe (mac)";
		String s1207 = "micromat diskstudio ( mac)";
		String s857 = "micromat podlock (mac)";
		String s607 = "microsoft word 2004 (mac)";
		String s783 = "microsoft excel 2004 (mac)";
		String sRand = "micromat techtool pro 4 (macs)";

		System.out.println(applyDamerau(s778, s1169));
		System.out.println(applyDamerau(s778, s1114));
		System.out.println(applyDamerau(s778, s646));
		System.out.println(applyDamerau(s778, s1207));
		System.out.println(applyDamerau(s778, s857));
		System.out.println(applyDamerau(s778, s607));
		System.out.println(applyDamerau(s778, s783));
		System.out.println(applyDamerau(s778, sRand));

		System.out.println("==============NormalizedLevenshtein=============");
		System.out.println(applyNormalizedLevenshtein(s778, s1169));
		System.out.println(applyNormalizedLevenshtein(s778, s1114));
		System.out.println(applyNormalizedLevenshtein(s778, s646));
		System.out.println(applyNormalizedLevenshtein(s778, s1207));
		System.out.println(applyNormalizedLevenshtein(s778, s857));
		System.out.println(applyNormalizedLevenshtein(s778, s607));
		System.out.println(applyNormalizedLevenshtein(s778, s783));
		System.out.println(applyNormalizedLevenshtein(s778, sRand));
		//. simply put software data eliminator
		//. quickbooks point-of-sale pro with hardware bundle 6.0
		

		System.out.println("==============JaroWinkler Similarity Measure=============");
		System.out.println(applyJaroWinkler(s778, s1169));
		System.out.println(applyJaroWinkler(s778, s1114));
		System.out.println(applyJaroWinkler(s778, s646));
		System.out.println(applyJaroWinkler(s778, s1207));
		System.out.println(applyJaroWinkler(s778, s857));
		System.out.println(applyJaroWinkler(s778, s607));
		System.out.println(applyJaroWinkler(s778, s783));
		System.out.println(applyJaroWinkler(s778, sRand));
		

		System.out.println("==============MetricLCS Similarity Measure=============");
		System.out.println(applyMetricLCS(s778, s1169));
		System.out.println(applyMetricLCS(s778, s1114));
		System.out.println(applyMetricLCS(s778, s646));
		System.out.println(applyMetricLCS(s778, s1207));
		System.out.println(applyMetricLCS(s778, s857));
		System.out.println(applyMetricLCS(s778, s607));
		System.out.println(applyMetricLCS(s778, s783));
		System.out.println(applyMetricLCS(s778, sRand));
		

		System.out.println("==============Jaccard Similarity Measure=============");
		System.out.println(applyJaccard(s778, s1169));
		System.out.println(applyJaccard(s778, s1114));
		System.out.println(applyJaccard(s778, s646));
		System.out.println(applyJaccard(s778, s1207));
		System.out.println(applyJaccard(s778, s857));
		System.out.println(applyJaccard(s778, s607));
		System.out.println(applyJaccard(s778, s783));
		System.out.println(applyJaccard(s778, sRand));
	}

}
