package model.arbre;

import model.Competence;

public class Branche {
	private Competence comp;
	private int i;
	private int n;

	public Branche(Competence competence, int niv,int Nenfant) {
		this.setComp(competence);
		this.i = niv;
		this.i = niv;
		this.n =  Nenfant;
	}

	public int getNiv() {
		
		return i;
	}
	
	public int getNbenfants() {
		
		return n;
		
	}

	public Competence getComp() {
		return comp;
	}

	public void setComp(Competence comp) {
		this.comp = comp;
	}

}
// public Couple(String s) {
// this.s = s;
// this.i = 0;
// }

// public Couple(int i) {
// this.s = null;
// this.i = i;
// }
// public void setInt(int i) {
// this.i = i;
// this.s = null;
// }

// public string getString() {
// return s;
// }

// public void setString(String s) {
// this.s = s;
// this.i = 0;
// }

