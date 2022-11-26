/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiladorlucas;

/**
 *
 * @author lucas
 */
public class GRAMATICA {
    
    public int Regra;
    public String Beta;
    public String Alfa;
    public int TamanhoDeBeta;
    
    public GRAMATICA(){
    super();
    }
    
    
    public void GramaticaRegra(int Regra){
        this.Regra = Regra;
        
        switch(Regra){
            
            case 1 -> {
                this.Alfa = "P'";
                this.Beta = "P";
                this.TamanhoDeBeta = 1;
            }
            
            case 2 -> {
                this.Alfa = "P";
                this.Beta = "inicio V A";
                this.TamanhoDeBeta = 3;
            }
            
            case 3 -> {
                this.Alfa = "V";
                this.Beta = "varinicio LV";
                this.TamanhoDeBeta = 2;
            }
            
            case 4 -> {
                this.Alfa = "LV";
                this.Beta = "D LV";
                this.TamanhoDeBeta = 2;
            }
                
            case 5 -> {
                this.Alfa = "LV";
                this.Beta = "varfim pt_v";
                this.TamanhoDeBeta = 2;
            }
            
            case 6 -> {
                this.Alfa = "D";
                this.Beta = "TIPO L pt_v";
                this.TamanhoDeBeta = 3;
            }
            
            case 7 -> {
                this.Alfa = "L";
                this.Beta = "id";
                this.TamanhoDeBeta = 1;
            }
            
            case 8 -> {
                this.Alfa = "TIPO";
                this.Beta = "inteiro";
                this.TamanhoDeBeta = 1;
            }
            
            case 9 -> {
                this.Alfa = "TIPO";
                this.Beta = "real";
                this.TamanhoDeBeta = 1;
            }
            
            case 10 -> {
                this.Alfa = "TIPO";
                this.Beta = "literal";
                this.TamanhoDeBeta = 1;
            }
            
            case 11 -> {
                this.Alfa = "A";
                this.Beta = "ES A";
                this.TamanhoDeBeta = 2;
            }
            
            case 12 -> {
                this.Alfa = "ES";
                this.Beta = "leia id pt_v";
                this.TamanhoDeBeta = 3;
            }
            
            case 13 -> {
                this.Alfa = "ES";
                this.Beta = "escreva ARG pt_v";
                this.TamanhoDeBeta = 3;
            }
            
            case 14 -> {
                this.Alfa = "ARG";
                this.Beta = "lit";
                this.TamanhoDeBeta = 1;
            }
            
            case 15 -> {
                this.Alfa = "ARG";
                this.Beta = "num";
                this.TamanhoDeBeta = 1;
            }
            
            case 16 -> {
                this.Alfa = "ARG";
                this.Beta = "id";
                this.TamanhoDeBeta = 1;
            }
            
            case 17 -> {
                this.Alfa = "A";
                this.Beta = "CMD A";
                this.TamanhoDeBeta = 2;
            }
            
            case 18 -> {
                this.Alfa = "CMD";
                this.Beta = "id rcb LD pt_v";
                this.TamanhoDeBeta = 4;
            }
            
            case 19 -> {
                this.Alfa = "LD";
                this.Beta = "OPRD opm OPRD";
                this.TamanhoDeBeta = 3;
            }
            
            case 20 -> {
                this.Alfa = "LD";
                this.Beta = "OPRD";
                this.TamanhoDeBeta = 1;
            }
            
            case 21 -> {
                this.Alfa = "OPRD";
                this.Beta = "id";
                this.TamanhoDeBeta = 1;
            }
            
            case 22 -> {
                this.Alfa = "OPRD";
                this.Beta = "num";
                this.TamanhoDeBeta = 1;
            }
            
            case 23 -> {
                this.Alfa = "A";
                this.Beta = "COND A";
                this.TamanhoDeBeta = 2;
            }
             
            case 24 -> {
                this.Alfa = "COND";
                this.Beta = "CAB CP";
                this.TamanhoDeBeta = 2;
            }
            
            case 25 -> {
                this.Alfa = "CAB";
                this.Beta = "se ab_p EXP_R fc_p entao";
                this.TamanhoDeBeta = 5;
            }
            
            case 26 -> {
                this.Alfa = "EXP_R";
                this.Beta = "OPRD opr OPRD";
                this.TamanhoDeBeta = 3;
            }
            
            case 27 -> {
                this.Alfa = "CP";
                this.Beta = "ES CP";
                this.TamanhoDeBeta = 2;
            }
            
            case 28 -> {
                this.Alfa = "CP";
                this.Beta = "CMD CP";
                this.TamanhoDeBeta = 2;
            }
            
            case 29 -> {
                this.Alfa = "CP";
                this.Beta = "COND CP";
                this.TamanhoDeBeta = 2;
            }
            
            case 30 -> {
                this.Alfa = "CP";
                this.Beta = "fimse";
                this.TamanhoDeBeta = 1;
            }
            
            case 31 -> {
                this.Alfa = "A";
                this.Beta = "R A";
                this.TamanhoDeBeta = 2;
            }
            
            case 32 -> {
                this.Alfa = "R";
                this.Beta = "CABR CPR";
                this.TamanhoDeBeta = 2;
            }
            
            case 33 -> {
                this.Alfa = "CABR";
                this.Beta = "repita ab_p EXP_R fc_p";
                this.TamanhoDeBeta = 4;
            }
            
            case 34 -> {
                this.Alfa = "CPR";
                this.Beta = "ES CPR";
                this.TamanhoDeBeta = 2;
            }
            
            case 35 -> {
                this.Alfa = "CPR";
                this.Beta = "CMD CPR";
                this.TamanhoDeBeta = 2;
            }
            
            case 36 -> {
                this.Alfa = "CPR";
                this.Beta = "COND CPR";
                this.TamanhoDeBeta = 2;
            }
            
            case 37 -> {
                this.Alfa = "CPR";
                this.Beta = "fimrepita";
                this.TamanhoDeBeta = 1;
            }
            
            case 38 -> {
                this.Alfa = "A";
                this.Beta = "fim";
                this.TamanhoDeBeta = 1;
            }
            
        }
        
        
    }
    
    
    
}
