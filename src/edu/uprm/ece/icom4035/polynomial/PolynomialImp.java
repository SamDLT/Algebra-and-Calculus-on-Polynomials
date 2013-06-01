package edu.uprm.ece.icom4035.polynomial;

import java.util.Iterator;
import java.util.StringTokenizer;

import edu.uprm.ece.icom4035.list.ArrayList;

public class PolynomialImp implements Polynomial {

	ArrayList<Term> terms;

	public PolynomialImp(){
		terms = new ArrayList<Term>(10);		
	}

	public PolynomialImp(String str){
		terms = new ArrayList<Term>(10);
		this.fromString(str);

	}

	@Override
	public Iterator<Term> iterator() {
		// TODO Auto-generated method stub
		return this.terms.iterator();
	}

	@Override
	public Polynomial add(Polynomial P2) {
		// TODO Auto-generated method stub
		PolynomialImp result = new PolynomialImp();
		for(Term t : this){
			result.addTerm(t);
		}
		for(Term t : P2){
			result.addTerm(t);
		}
		return result;
	}

	@Override
	public Polynomial subtract(Polynomial P2) {
		// TODO Auto-generated method stub
		return this.add(P2.multiply(-1));
	}

	@Override
	public Polynomial multiply(Polynomial P2) {
		// TODO Auto-generated method stub
		PolynomialImp result = new PolynomialImp();
		for(Term i : this){
			for(Term j : P2){
				if(i.getCoefficient() * j.getCoefficient() != 0){
					result.addTerm(new TermImp(i.getCoefficient() * j.getCoefficient(), i.getExponent() + j.getExponent()));
				}
			}
		}
		return result;
	}

	@Override
	public Polynomial multiply(double c) {
		// TODO Auto-generated method stub
		PolynomialImp result = new PolynomialImp();
		for(Term t : this){
			if(t.getCoefficient() * c != 0){
			result.addTerm(new TermImp(t.getCoefficient() * c, t.getExponent()));
			}
		}
		return result;
	}

	@Override
	public Polynomial derivative() {
		// TODO Auto-generated method stub
		PolynomialImp result = new PolynomialImp();
		for(Term t : this){
			if(t.getCoefficient() != 0 && t.getExponent() != 0){
				result.addTerm(new TermImp(t.getCoefficient() * t.getExponent(), t.getExponent() - 1));
			}
		}
		return result;
	}

	@Override
	public Polynomial indefiniteIntegral() {
		// TODO Auto-generated method stub
		PolynomialImp result = new PolynomialImp();
		for(Term t : this){
			if(t.getCoefficient() != 0 && t.getExponent() + 1 != 0){
				result.addTerm(new TermImp(t.getCoefficient()/(t.getExponent() + 1), t.getExponent() + 1));
			}
		}
		result.addTerm(new TermImp(1.00, 0));
		return result;
	}

	@Override
	public double definiteIntegral(double a, double b) {
		// TODO Auto-generated method stub
		double acc = 0;
		Polynomial result = this.indefiniteIntegral();
		for(Term t : result){
			acc += t.evaluate(b) - t.evaluate(a);
		}
		return acc;
	}

	@Override
	public int degree() {
		// TODO Auto-generated method stub
		return this.terms.get(0).getExponent();
	}

	@Override
	public double evaluate(double x) {
		// TODO Auto-generated method stub
		double acc = 0;
		for(Term t : this){
			acc += t.evaluate(x);
		}
		return acc;
	}

	@Override
	public boolean equals(Polynomial P) {
		// TODO Auto-generated method stub
		return this.toString().equals(P.toString());
	}

	private void fromString(String str) {
		StringTokenizer strTok = new StringTokenizer(str, "+");
		String nextStr = null;
		Term nextTerm = null;
		this.terms.clear();
		while (strTok.hasMoreElements()){
			nextStr = (String) strTok.nextElement();
			nextTerm = TermImp.fromString(nextStr);
			// private method to store a new term into a polynomial
			this.addTerm(nextTerm);
		}

	}
	public String toString(){	
		String s = "";

		if(this.terms.size()==0){
			return "0.00";
		}

		for(int i = 0; i < this.terms.size(); i++){
			if(i < this.terms.size()-1)
				s += this.terms.get(i).toString()+"+";
			else{
				s += this.terms.get(i).toString();}
		}

		return s;
	}

	private void addTerm(Term nextTerm) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(this.terms.isEmpty()){
			this.terms.add(nextTerm);
		}
		else{
			for(int i = 0; i<this.terms.size(); i++){
				if(this.terms.get(i).getExponent() == nextTerm.getExponent()){
					if(this.terms.get(i).getCoefficient() + nextTerm.getCoefficient() == 0){
						this.terms.remove(i);
					}
					else{
						this.terms.set(i, new TermImp(this.terms.get(i).getCoefficient() + nextTerm.getCoefficient(), nextTerm.getExponent()));
					}
					flag = true;
					break;
				}
				else if(this.terms.get(i).getExponent() < nextTerm.getExponent()){
					this.terms.add(i, nextTerm);
					flag = true;
					break;
				}
			}
			if(!flag){
				this.terms.add(nextTerm);
			}

		}
		//this.terms.add(nextTerm);
	}

}
