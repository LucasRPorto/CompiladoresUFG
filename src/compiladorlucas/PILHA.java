/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiladorlucas;

/**
 *
 * @author lucas
 */
public class PILHA {
    
    int elementos[];
    int topo;
    
    public PILHA(){
        super();
        elementos = new int[30];
    }
    
    //Empilhando elementos na pilha
    public void empilha(int a){
        if(pilhaCheia()){
            System.out.println("Empilhando PILHA cheia");
        }
        topo++;
        elementos[topo] = a;
        //System.out.println("Empilhei "+ a);
    }
    
    // Desempilhando a elementos da pilha
    public int desempilha(int a){
        
        int e = 0;
        while(a!=0){
            if(pilhaVazia()){
                System.out.println("Desempilhando PILHA vazia");
            }
            
            e = elementos[topo];
            topo--;
            //System.out.println("Desempilhei "+ a + " O topo agora é: "+topo);
            a--;
            
        }
        return e;
    }
    
    //Verificando se a pilha está vazia
    public boolean pilhaVazia(){
        return (topo == -1);
    }
    
    //Verificando se a pilha está cheia
    public boolean pilhaCheia(){
        return ( topo == 29);
    }
    
    //Retornando elemento que está no topo da pilha
    public int topoDaPilha(){
        return elementos[topo];
    }
}
