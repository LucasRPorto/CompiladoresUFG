
package compiladorlucas;


public class TOKEN {
    
    public String Classe;
    public String Lexema;
    public String Tipo;
    
//--------------------------------CONSTRUTORES--------------------------------\\
    
    public TOKEN(String Classe, String Lexema, String Tipo){
        super();
        this.Classe = Classe;
        this.Lexema = Lexema;
        this.Tipo = Tipo;
    }
    
    public TOKEN(){
        super();
    }
//----------------------------------GET E SET---------------------------------\\    
    
    public void setClasse(String Classe){
        this.Classe = Classe;
    }
    
    public void setLexema(String Lexema){
        this.Lexema = Lexema;
    }
    
    public void setTipo(String Tipo){
        this.Tipo = Tipo;
    }
    
    public String getClasse(){
        return Classe;
    }
    
    public String getLexema(){
        return Lexema;
    }
    
    public String getTipo(){
        return Tipo;
    }
    

    
}
