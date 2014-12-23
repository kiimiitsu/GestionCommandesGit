package com.afpa59.gc.outils;
/**
* Une interface pour lire des valeurs depuis la console
* @author Cay Horstmann
* (ajouts et modifications Dominique Bouthinon)
*/

public class Clavier
{  

	/**
	* affiche une message sur la console sans retour chariot
	* @param prompt est le message a afficher
	*/
  private static void printPrompt(String prompt)
  {  
  	System.out.print(prompt + " ");
	   System.out.flush();
  }  
   
   
  /**
	*  Affiche le message prompt, saisit et retourne un reel (double)  
	*/	
  public static double readDouble(String prompt)
  {  while(true)
	  {  printPrompt(prompt);
		 try
		 {  return Double.valueOf
			   (readString().trim()).doubleValue();
		 } catch(NumberFormatException e)
		 {  System.out.println
		 ("ce n'est pas un reel. Essayez de nouveau !");
		 }
	  }
  }   
  
  /**
	*  Affiche le message prompt, saisit et retourne un entier long (long)  
	*/	
public static long readLong(String prompt)
{  while(true)
	  {  printPrompt(prompt);
		 try
		 {  return Long.valueOf
			   (readString().trim()).longValue();
		 } catch(NumberFormatException e)
		 {  System.out.println
		 ("ce n'est pas un reel. Essayez de nouveau !");
		 }
	  }
}   
  
   /**
	 *  Affiche le message prompt, saisit et retourne un reel (float) 
	 */
    public static float readFloat(String prompt) 
    {
	  while(true)
	  {  printPrompt(prompt);
		 try
		 {  return Float.valueOf
			   (readString().trim()).floatValue();
		 } catch(NumberFormatException e)
		 {  System.out.println
		 ("Ce n'est pas un reel. Essayez de nouveau !");
		 }
	  }
  }   
	 
	 
  /**
	* Affiche le message prompt, saisit et retourne un entier (int)
	*/
  public static int readInt(String prompt)
  {  while(true)
	  {  printPrompt(prompt);
		 try
		 {  return Integer.valueOf
			   (readString().trim()).intValue();
		 } catch(NumberFormatException e)
		 {  System.out.println
			   ("Ce n'est pas un entier. Essayer de nouveau!");
		 }
	  }
  }   
  
  
  /**
	* Affiche le message prompt, saisit et retourne une valeur booleenne (true ou false)
	*/
  public static boolean readBoolean(String prompt)
  {  
  	  boolean ok = false ;
  	  String s ;
  	  
     do
	  {  
	    printPrompt(prompt);
   	s = readString().trim() ;
		if (!s.equals("true") && !s.equals("false"))   
		   System.out.println("Ce n'est pas un booleen. Essayer de nouveau!");
		else
		   ok = true ;   
	  }
	  while (!ok) ;
	  
	  return s.equals("true") ;
    }   
  
    
  /**
	* read a string from the console. The string is 
	* terminated by a newline
	* @return the input string (without the newline)
	*/
  private static String readString()
  {  int ch;
	  String r = "";
	  boolean done = false;
	  
	  while (!done)
	  {  try
		 {  ch = System.in.read();
			if (ch < 0 || (char)ch == '\n')
			   done = true;
			else
			   r = r + (char) ch;
		 }
		 catch(java.io.IOException e)
		 {  done = true;
		 }
	  }
	  return r.trim() ;
  }   
  
  
  /**
	* affiche le message prompt, saisit et retourne un caractere (pas de retour chariot)
	*/
  public static char readChar(String prompt)
  {  
     int ch = 0;
	  boolean done = false;
	  
	  printPrompt(prompt) ;
	  
	  do
	  { 
	    try
	    {
	       ch = System.in.read();
	       if (ch < 0 || (char) ch == '\n')
		   		System.out.println("pas un caractere autorise, recommencez") ;
		   else
		       done = true ;
	    }
	    catch(java.io.IOException e)
	    {
	       System.out.println(e) ;   	
	    }   
	  }
	  while (!done) ;
	  
	  return (char) ch ;
  }   
  
  
  /**
	* affiche le message prompt, saisit et retourne une chaine 
	*/
	
  public static String readString(String prompt)
  {  printPrompt(prompt);
	  return readString().trim() ;
  }   
  
  
  /**
	* read a word from the console. The word is 
	* any set of characters terminated by whitespace
	* @return the 'word' entered
	*/
	
  private static String readWord()
  {  int ch;
	  String r = "";
	  boolean done = false;
	  while (!done)
	  {  try
		 {  ch = System.in.read();
			if (ch < 0 
			   || java.lang.Character.isWhitespace((char)ch))
			   done = true;
			else
			   r = r + (char) ch;
		 }
		 catch(java.io.IOException e)
		 {  done = true;
		 }
	  }
	  return r;
  }   
     
  
} // fin classe Console2
