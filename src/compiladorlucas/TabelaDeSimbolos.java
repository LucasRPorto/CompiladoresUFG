/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package compiladorlucas;

//Implementação da TABELA HASH através da ListaDinamica
public class TabelaDeSimbolos {
    ListaDinamica Vetor[];
    public static String LetraInicializada = ""; // Variável para analisar se a letra da
                                   // tabela está ocupada com uma lista ou vazia
                                   //Será utilizada tbm na ListaDinamica
    
    

    
    /*Construtor que inicia a tabela HASH sendo um vetor de 26 posições, para 
      cada letra do alfabeto onde cada posição aponta para uma lista       */
    public TabelaDeSimbolos(){
        Vetor = new ListaDinamica[26];
        InicializaListas();
    }
    
    
    //Inicializa cada uma das 26 posições do vetor 
    final void InicializaListas(){
        for(int i = 0; i<26; i++){
            Vetor[i] = new ListaDinamica();
        }
    }
    
    
    final int FuncaoHash(String Lexema){
        // Calcular o valor Hash do Lexema que foi passada como parâmetro
        
        Lexema = Lexema.toLowerCase();
        LetraInicializada = LetraInicializada + Lexema.charAt(0);
        int Posicao = Lexema.charAt(0); // Palavra em um vetor de letras, e pega a primeira letra
        
        return Posicao - 97; // Diminuindo 97 devido a tabela ASCII, 97 = a
    }
    
    
    //Função de adicionar na Tabela Hash
    final void AddTabela(TOKEN TokenAtual){
        Vetor[FuncaoHash(TokenAtual.getLexema())].add(TokenAtual);
    }
    
    
    //Função para buscar TOKEN na tabela
    final TOKEN BuscaPalavra(String Palavra){
        TOKEN Auxiliar = new TOKEN();
        
        String VariavelAuxiliar;
        VariavelAuxiliar = Palavra.toLowerCase();
        int InteiroAuxiliar;
        
        //Fazendo uso da tabela ASCII
        InteiroAuxiliar = VariavelAuxiliar.charAt(0) - 97;
        
        if(AcheiLetra(InteiroAuxiliar)){
        
        Auxiliar = Vetor[FuncaoHash(Palavra)].BuscaItem(Palavra);
        }
        
        else{
            Auxiliar = null;
        }
        return Auxiliar;
    }
    

    
    public void ImprimeTabela(){
        System.out.println("");
        System.out.println("============= IMPRIMINDO TABELA DE SÍMBOLOS =============");
        System.out.println("");
        
        //Realizando um for para percorrer todo o vetor principal, se posição do
        //vetor estiver preenchido por uma lista, irá imprimir, caso contrário, 
        //irá ignorar
        for(int i = 0; i<26 ; i++){
            if(AcheiLetra(i) == true){
                Vetor[i].ImprimeLista();
            }
            else{   
            }
        }
    }
    
    //Função para analisar se existe letra preenchida na tabela de símbolos
    public static boolean AcheiLetra(int i){
        int a = i + 97;
        String Auxiliar = Character.toString((char) a); // Convertendo ASCII em char
        
        return LetraInicializada.contains(Auxiliar);
    }
    
    
    
    //Função para excluir determinado TOKEN da Tabela de Símbolos
    final void ExcluiToken(String Lexema){
        Vetor[FuncaoHash(Lexema)].RemoveItem(Lexema);
    }
    
    
    //Função para verificar se é palavra reservada
    public static boolean isPalavraReservada(String Lexema){
        return  ("inicio".equals(Lexema) || "varinicio".equals(Lexema) || 
                "varfim".equals(Lexema) || "escreva".equals(Lexema) || 
                "leia".equals(Lexema) || "se".equals(Lexema) ||
                "entao".equals(Lexema) || "fimse".equals(Lexema) || 
                "repita".equals(Lexema) || "fimrepita".equals(Lexema) ||
                "fim".equals(Lexema) || "inteiro".equals(Lexema)
                || "literal".equals(Lexema) || "real".equals(Lexema) );
            //Palavra reservada
    }    
    
    
    
    //Função para atualizar determinado ID na tabela de símbolos
    final void AtualizaToken(String LexemaAntigo, String LexemaNovo){
        
        //Atualização só pode ser realizada com ID
        if(TabelaDeSimbolos.isPalavraReservada(LexemaAntigo)){
            System.out.println("Palavra reservada não pode ser atualizada");
        }
        else{
            //Se a busca palavra for diferente de NULL é pq temos um TOKEN COM ESSE LEXEMA NA TABELA
            if(this.BuscaPalavra(LexemaAntigo)!=null){
                TOKEN Auxiliar = new TOKEN();
                //Removemos da tabela o TOKEN correspondente
                this.ExcluiToken(LexemaAntigo);

                //Adicionamos na TABELA o TOKEN atualizado
                Auxiliar.Classe = "ID";
                Auxiliar.Tipo = null;
                Auxiliar.Lexema = LexemaNovo;
                this.AddTabela(Auxiliar);
                System.out.println("Atualizamos");
            }
            //Se a busca for igual a NULL é pq não temos o LEXEMA na tabela
            else{
                System.out.println("TOKEN passado como parâmetro para atualização"
                        + " não está presente na TABELA DE SIMBOLOS! ");
            }
        }
    } // Fim do Atualiza TOKEN
    
    
}