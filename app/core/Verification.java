package core;

// Regroupement des divers fonction liée à la vérification, etc.

public class Verification {

	// Transformer une chaine vide en caractère "%"

	public static String verifParam(String param) {
		
		if(param != null){
	        if(param.equals("")){
	        	param = "%";
	        }
    	}
    	else{
    		param = "%";
    	}
    	System.out.println(param);
    	return param;
	}

	// Passer le booleen launch à true si les conditions sont atteintes.

	public static boolean verifBool(String param, boolean launch) {
		
		if(param != "%" || launch){
	        return true;
    	}
    	else{
    		return false;
    	}
	}

	// Remplaçer le caractère '%' par une chaine vide.

	public static String eraser(String param){
		if(param.equals("%")){
			param = "";
		}
		return param;
	}
}