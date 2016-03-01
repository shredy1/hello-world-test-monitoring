package monitoring;

public class Regulateur {

	
	private static double TC = 18;
	private static String Mode="economique";
	private static double coef = 1/3;
	private static double delta = 1;
	
	
	
	
	//Via une interface , l'utilsateur renseigne la température de consigne et le mode (eco ou confort) 
	public static void setUserPreference(double tc,String mode)
	{
		setTC(tc);
		
		if(mode.equals("eco"))
		{
			setCoef(1/3);
			setDelta(1);
		}
		else
		{
			setCoef(2/3);
			setDelta(0.5);
		}
	}
	
	
	
	//Méthode qui s'exécute chaque seconde
	public void Regulator()
	{
		String etat_fenetre = "" ; // implementer une méthode qui écoute la fenetre communicante en push;
		
		if(etat_fenetre.equals("ouverte"))
		{
			//envoyer requete au radiateur pour le mettre en mode eteint 
		}
		
		else
		{
			double TE = 1 ; // Lire sur  Thermostat externe en push 
			double TI = 4; // Requete au Thermostat interne en pull 
			
			regler(TE,TI);
		}
				
	}
	
	
	public void regler(double TE,double TI)
	{
		if(TI >= TC-delta)
		{
			//envoyer requete au radiateur pour le mettre en mode eteint 
		}
		else if(TE + coef*(TC-TE) <= TI & TI < TC-delta)
		{
			//envoyer requete au radiateur pour le mettre en mode Faible
		}
		else if(TI < TE + coef*(TC-TE))
		{
			//envoyer requete au radiateur pour le mettre en mode Fort
		}
	}
	
	
	
	public static double getTC() {
		return TC;
	}




	public static void setTC(double tC) {
		TC = tC;
	}




	public static String getMode() {
		return Mode;
	}




	public static void setMode(String mode) {
		Mode = mode;
	}




	public static double getCoef() {
		return coef;
	}




	public static void setCoef(double coef) {
		Regulateur.coef = coef;
	}




	public static double getDelta() {
		return delta;
	}




	public static void setDelta(double delta) {
		Regulateur.delta = delta;
	}



}
