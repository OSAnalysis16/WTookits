package com.conf;

import com.algorithm.TermWeighting;
import com.algorithm.TF_IDF;

/**
 * TF_IDF Factory.
 * 
 * @author chen 2016/12/11
 *
 */

public class TF_IDFFactory implements TermWeightingFactory {
	private static TF_IDFFactory tf_idfFactory = new TF_IDFFactory();
	private TF_IDFFactory(){}
	
	public static TF_IDFFactory GetTf_idfFactory(){
		return tf_idfFactory;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "TF_IDF";
	}

	@Override
	public TermWeighting getTermWeightingInstance() {
		// TODO Auto-generated method stub
		return new TF_IDF();
	}

	@Override
	public Boolean isSupervised() {
		// TODO Auto-generated method stub
		return false;
	}

}
