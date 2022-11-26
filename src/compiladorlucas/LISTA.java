/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiladorlucas;

/**
 *
 * @author lucas
 */
public class LISTA {
    private TOKEN TokenAtual;
    
    
    public TOKEN getToken(){
        return TokenAtual;
    }
    
    public void setToken(TOKEN TokenAtual){
        this.TokenAtual = TokenAtual;
    }
    
    private LISTA Prox;
    
    public LISTA(){
    }
    

    
    public LISTA getProx(){
        return Prox;
    }
    
    public void setProx(LISTA Prox){
        this.Prox = Prox;
    }
}
