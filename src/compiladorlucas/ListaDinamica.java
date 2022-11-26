/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiladorlucas;

/**
 *
 * @author lucas
 */

// Lista Dinamica para ser utilizada na TABELA HASH
public class ListaDinamica {
    LISTA Primeiro;
    LISTA Ultimo;
    int Tamanho = 0;
    
    
    public ListaDinamica(){
        Primeiro = null;
        Ultimo = Primeiro;
    }
    
    //Metodo ADICIONAR para a lista
    public void add(TOKEN TokenAtual){
        //Se está vazio
        if(Primeiro == null){
            Primeiro  = new LISTA();
            Ultimo = Primeiro;
            LISTA Novo = new LISTA();
            Novo.setToken(TokenAtual);
            Novo.setProx(null);
            Ultimo.setProx(Novo);
            Ultimo = Novo;
            Tamanho++;
        } 
        
        else{
            LISTA Novo = new LISTA();
            Novo.setToken(TokenAtual);
            Novo.setProx(null);
            Ultimo.setProx(Novo);
            Ultimo = Novo;
            Tamanho++;
        }
    }
     
    // Buscando ITEM na lista
    public TOKEN BuscaItem(String Lexema){
        
        TOKEN Auxiliar = new TOKEN();
        
        LISTA Percorre = Primeiro.getProx();
        int find = 0;
         
        while(Percorre != null){
            
            if(Percorre.getToken().Lexema.equals(Lexema)){
                //System.out.println("Palavra encontrada: " + Percorre.getToken().Lexema);
                find = 1;
                Auxiliar = Percorre.getToken();
                break;
            }
            Percorre = Percorre.getProx();
        }
        if (find!=1){
            //System.out.println("Lexema não encontrado ");
            Auxiliar = null;
        }
        
        return Auxiliar;
    }
    
    public void ImprimeLista(){
        LISTA Percorre = Primeiro.getProx();
        
        while(Percorre != null){
            System.out.print("Classe: " + Percorre.getToken().Classe);
            System.out.print(" || Lexema: " + Percorre.getToken().Lexema);
            System.out.println(" || Tipo: " + Percorre.getToken().Tipo);
            Percorre = Percorre.getProx();
        }
    }
    
    
    // Função para remover ITEM da lista
    public void RemoveItem(String Lexema){
        
        
        LISTA Remove = Primeiro.getProx();
        
        LISTA Sentinela = Primeiro;
        
        if(!(Primeiro == null)){
            
            while(Remove != null){
                if (Remove.getToken().Lexema.equals(Lexema)){
                    
                    if(Remove.getProx() == null){
                        Ultimo = Sentinela;
                        Ultimo.setProx(null);
                        Remove = null;
                        Tamanho--;
                        break;
                    }
                    else{
                        Sentinela.setProx(Remove.getProx());
                        Remove.setProx(null);
                        Remove = null;
                        Tamanho--;
                        break;
                    }
                }
                Remove = Remove.getProx();
                Sentinela = Sentinela.getProx();
            }
        }
    }

    
    
}
