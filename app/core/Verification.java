package core;

// Regroupement des divers fonction liée à la vérification, etc.

public class Verification {

	public static String verifParam(String param) {
		
		if(param != null){
	        if(param.equals("")){
	        	param = "%";
	        }
    	}
    	else{
    		param = "%";
    	}
    	return param;
	}

	public static boolean verifBool(String param, boolean launch) {
		
		if(param != "%" || launch){
	        return true;
    	}
    	else{
    		return false;
    	}
	}
}