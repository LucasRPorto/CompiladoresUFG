package compiladorlucas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;



/*Compilador desenvolvido para a disciplina de Compiladores 1
 ANALISADOR LÉXICO
 EC - Lucas Rodrigues Porto - 201618306 */


public class CompiladorLucas {
    
    
 /*----------------------------VARIÁVEIS IMPORTANTES----------------------------
    Posicao = Variável utilizada para o carro de leitura do arquivo texto
              Servirá como um índice para o arquivo
    Palavra = Variável utilzada dentro da função Scanner, para computar a palavra
              que formará o TOKEN
    Arquivo = Variável utilizada dentro da função SCANNER, armazena o array
              que possui os chars do arquivo texto
    Linha   = Variável utilizada para armazenar a linha atual no arquivo
    
    Coluna  = Variável utilizada para armazenar a coluna atual no arquivo
    
    LinhaInicial e ColunaInicial = Variáveis utilizadas para marcar quando
              um comentário ou literal foi iniciado, pois se caso de erro, irá
              reportar onde começou o literal ou comentário
    
    
 */
    
//----------------------------FUNÇÕES UTILITÁRIAS-----------------------------\\

    public static int Posicao = 0; //Variável Posição do Arquivo, carro de Leitura
    
    public static TabelaDeSimbolos Tabela = new TabelaDeSimbolos(); // Inicializando Tabela de Simbolos
    
    public static int Linha = 1;
    
    public static int Coluna = -1;
    
    public static int LinhaInicial = 0;
    
    public static int ColunaInicial = 0;
    
    public static String[][] TabelaActionGoto = new String[76][44];
    
    public static PILHA Pilha = new PILHA();
    
    public static GRAMATICA Gramatica = new GRAMATICA();
    
    
    //Analisa se é um número
    public static boolean isNumero(char a){
        
        return a>='0' && a<='9';
    }
    //Analisa se é uma letra
    public static boolean isLetra(char a){
        
        return ((a>='a' && a<='z')  ||  ( a>='A' && a<='Z'));
    }
    //Analisa se é um operador <, > ou =
    public static boolean isOperador(char a){
        
        return ((a=='<')|| (a=='>') || (a=='='));
    }
    //Analisa se é espaço, quebra de linha ou tabulação
    public static boolean isEspaco(char a){
        
        return ((a==' ') || (a=='\t') || (a=='\n') || (a=='\r'));
    }
    //Analisa se é palavra reservada
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
    //Incrementa a Posicao ( Carro de Leitura ) e incrementa a Coluna
    public static void NextPosition(){
        Posicao++;
        Coluna++;
    }
 
    
    
//-------------FUNÇÃO PARA RETORNAR O DADO DA TABELA ACTION GOTO--------------\\
    
public static String EstadoTabela(int Estado,String Palavra){
    int i = 1;
    int j = 1;
    int PosX = 0;
    int PosY = 0;
    String Retorno = "";
    int Auxiliar = 0;
    
    //Analisando a posição do estado
        while(i<76 ){
            Auxiliar = Integer.parseInt(TabelaActionGoto[i][0]);
            
            if(Auxiliar == Estado){
                PosY = i ;
                break;
            }
            i++;
        }
        
    //Analisando a posição da palavra
        while(j<44){
            if(TabelaActionGoto[0][j].equals(Palavra)  || Palavra == "EOF"){
                PosX = j;
                
                if(Palavra == "EOF"){
                    PosX = 24;
                }
                break;
            }
            j++;
        }
    // Retornando elemento correspondente
    Retorno =  TabelaActionGoto[PosY][PosX];
    return Retorno;
}
    
    
//--------FUNÇÃO PARA IMPRIMIR A MENSAGEM DE ERRO SINTÁTICO ENCONTRADO--------\\


public static void MensagemErroSintatico(int Estado){
    switch (Estado){
        
        case 0:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Código deve iniciar com a palavra: inicio");
            break;
            
        case 1,5,7,9,11,13,14:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de final de arquivo $: EOF");
            break;
            
        case 2:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de varinicio: varinicio");
            break;
            
        case 3,6,8,10,12,50,52,54,56,57,62,64,66:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de: id, leia, escreva, se, repita ou fim");
            break;
            
        case 4,63,70:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de: varfim, inteiro, real ou literal");
            break;
            
        case 15,67,72,73,74:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de identificador: id");
            break;
            
        case 16,19,21,22,23,26,32,65,68,69:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de ponto e vírgula: pt_v");
            break;
            
        case 33,35,37,39,46:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de: id, leia, escreva, se, ou fimse");
            break;
            
        case 17,20,27,34,36,38,40,41:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de: id, leia, escreva, se, fimse, repita, fimrepita ou fim");
            break;
            
        case 49,51,53,55,61:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de: id, leia, escreva, se, ou fimrepita");
            break;
            
        case 44,60,71:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de fecha parênteses: fc_p");
            break;
            
        case 25,31,43,48,59:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de identificador ou número: id ou num");
            break;
            
        case 28,29:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de: pt_v, opm, fc_p ou opr");
            break;
            
        case 18:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de: id, lit ou num");
            break;
        
        case 24:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de atribuição: rcb");
            break;
            
        case 30:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de ponto e virgula ou operador matemático: pt_v ou opm");
            break;
            
        case 42,58:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de abre parênteses: ab_p");
            break;
            
        case 45:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de entao: entao");
            break;
            
        case 47:
            System.out.println("ERRO SINTÁTICO: Linha: "+Linha +" Coluna: "+Coluna);
            System.out.println("Aguardando a leitura de operador relacional: opr");
            break;
        
    }
}


//------FUNÇÃO PARA RETORNAR O TOKEN DE SINCRONIZAÇÃO PARA O MODO PÂNICO------\\

public static boolean TokenDeSincronizacao(int Estado, String ClasseToken){
    
        boolean achouToken = false;
        switch (Estado){
        
        case 0:
            if(ClasseToken.equals("inicio")){
            System.out.println("TOKEN DE SINCRONIZACAO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 1,5,7,9,11,13,14:
            if(ClasseToken.equals("EOF")){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 2:
            if(ClasseToken.equals("varinicio")){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 3,6,8,10,12,50,52,54,56,57,62,64,66:
            if(ClasseToken.equals("id") || ClasseToken.equals("leia") || ClasseToken.equals("escreva") 
                    || ClasseToken.equals("se") || ClasseToken.equals("repita") || ClasseToken.equals("fim")){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 4,63,70:
            if(ClasseToken.equals("varfim") || ClasseToken.equals("inteiro") || ClasseToken.equals("real") 
                    || ClasseToken.equals("literal") ){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 15,67,72,73,74:
            if(ClasseToken.equals("id")){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 16,19,21,22,23,26,32,65,68,69:
            if(ClasseToken.equals("pt_v")){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 33,35,37,39,46:
            if(ClasseToken.equals("id") || ClasseToken.equals("leia") || ClasseToken.equals("escreva") 
                    || ClasseToken.equals("se") || ClasseToken.equals("fimse") ){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 17,20,27,34,36,38,40,41:
            if(ClasseToken.equals("id") || ClasseToken.equals("leia") || ClasseToken.equals("escreva") 
                    || ClasseToken.equals("se") || ClasseToken.equals("fimse") || ClasseToken.equals("repita")
                    || ClasseToken.equals("fimrepita") || ClasseToken.equals("fim")){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 49,51,53,55,61:
            if(ClasseToken.equals("id") || ClasseToken.equals("leia") || ClasseToken.equals("escreva") 
                    || ClasseToken.equals("se") || ClasseToken.equals("fimrepita") ){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 44,60,71:
            if(ClasseToken.equals("fc_p") ){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 25,31,43,48,59:
            if(ClasseToken.equals("id") || ClasseToken.equals("num") ){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 28,29:
            if(ClasseToken.equals("pt_v") || ClasseToken.equals("opm") || ClasseToken.equals("fc_p") 
                    || ClasseToken.equals("opr") ){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 18:
            if(ClasseToken.equals("id") || ClasseToken.equals("lit") || ClasseToken.equals("num")){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
        
        case 24:
            if(ClasseToken.equals("rcb") ){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 30:
            if(ClasseToken.equals("pt_v") || ClasseToken.equals("opm")){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 42,58:
            if(ClasseToken.equals("ab_p")){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 45:
            if(ClasseToken.equals("entao") ){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
            
        case 47:
            if(ClasseToken.equals("opr")){
            System.out.println("TOKEN DE SINCRONIZAÇÃO RECUPERADO: Linha: "+Linha +" Coluna: "+Coluna);
            achouToken = true;
            }
            break;
        
    }
    return achouToken;
}
    
   

//============================================================================\\   
//-------------------------------FUNCAO SCANNER-------------------------------\\ 
//============================================================================\\
    
    public static TOKEN tokenSCANNER(char[] Arquivo, int Tamanho){
        
        int Estado = 0; // Controla o estado atual
        String Palavra = ""; // Palavra para computar o Lexema
        int Controlador = 0; // Controla o laço de repetição do autômato
        int ControladorDeEspaco = 0;

        
    //-------------------AUTÔMATO FINITO DETERMINÍSTICO-------------------\\      
    
    if(Tamanho > Posicao){
    
        do{
            // Analisando se é espaço no estado q0 e ignorando
            ControladorDeEspaco = Estado;
            while(Tamanho>Posicao && (isEspaco(Arquivo[Posicao]))){
            
            if(Tamanho>Posicao && Arquivo[Posicao] == '\n'){
                //Se for quebra de linha, incrementa LINHA e zera a COLUNA
                Linha++;
                Coluna = -1;
                }
                
            NextPosition();
            
            //Se final de arquivo para
            if(Tamanho == Posicao){
                break;          
            }
            } // Fim do Controle de espaco
            
            
            //Após a nálise do quebra linha, analisa se é fim de arquivo
            //Para caso um código que termina com espaço, por exemplo
            
            if(Tamanho==Posicao && Palavra ==""){
                Estado = 100;
                NextPosition();
                break;
            }
            
            else if((Tamanho==Posicao && Palavra !="")){
            Estado = ControladorDeEspaco;
            Controlador = -1;
            break;
            }
            
            
            //Laço para analisar em qual estado está e através desse para qual irá
            switch(Estado){
                
                case 0:
                    
                //Analisando para Numero, futuro Real ou inteiro
                     if(isNumero(Arquivo[Posicao])){
                        Estado = 1;
                        Palavra = Palavra + Arquivo[Posicao];
                        NextPosition();
                        
                        if(Tamanho>Posicao && isEspaco(Arquivo[Posicao])){
                        Controlador =-1;
                        break;
                    }
                        
                        
                     }
                     
                //Analisando para abre aspas, futuro Literal
                     else if(Arquivo[Posicao]== '"'){
                         Estado = 10;
                         Palavra = Palavra + Arquivo[Posicao];
                         NextPosition();
                         LinhaInicial = Linha;
                         ColunaInicial =Coluna;
                         //Incrementando espaco e tabulação no inicio do literal caso tenha
                         while(Tamanho>Posicao && (Arquivo[Posicao] == '\t'|| Arquivo[Posicao]==' ')){
                             Palavra = Palavra + Arquivo[Posicao];
                             NextPosition();
                         }
                     }
                //Analisando para Letra, futuro ID ou Palavra reservada     
                     else if(isLetra(Arquivo[Posicao])){
                         Estado = 12;
                         Palavra = Palavra + Arquivo[Posicao];
                         NextPosition();
                         
                        if(Tamanho>Posicao && isEspaco(Arquivo[Posicao])){
                        Controlador =-1;
                        break;
                    }
                         
                     }
                //Analisando para o abre parênteses, futuro TOKEN comentario
                     else if(Arquivo[Posicao] == '{'){
                         Estado = 13;
                         Palavra = Palavra + Arquivo[Posicao];
                         NextPosition();
                         LinhaInicial = Linha;
                         ColunaInicial =Coluna;
                         while(Tamanho>Posicao && (Arquivo[Posicao] == '\t'|| Arquivo[Posicao]==' ')){
                             Palavra = Palavra + Arquivo[Posicao];
                             NextPosition();
                         }
                     }
                //Analisando para os operadores Relacionais e Atribuicao
                     else if(Arquivo[Posicao] == '<'){
                         Estado = 16;
                         Controlador = -1;
                         Palavra = Palavra+ Arquivo[Posicao];
                         NextPosition();
                         
                         if(Tamanho>Posicao && Arquivo[Posicao] == '='){
                             Estado = 16;
                             Palavra = Palavra + Arquivo[Posicao];
                             NextPosition();
                             Controlador = -1;
                             //Achamos um <=
                         }
                         else if(Tamanho>Posicao && Arquivo[Posicao] == '-'){
                             Estado = 17;
                             Palavra = Palavra + Arquivo[Posicao];
                             NextPosition();
                             Controlador = -1;
                             //<-
                         }
                         else if(Tamanho>Posicao && Arquivo[Posicao] == '>'){
                             Estado = 16;
                             Palavra = Palavra + Arquivo[Posicao];
                             NextPosition();
                             Controlador = -1;
                             //<>
                         }
                     }
                     
                     else if(Arquivo[Posicao] == '>'){
                         Estado = 16;
                         Palavra = Palavra + Arquivo[Posicao];
                         NextPosition();
                         Controlador = -1;
                         
                         if(Tamanho>Posicao && Arquivo[Posicao] == '='){
                             Estado = 16;
                             Palavra = Palavra + Arquivo[Posicao];
                             NextPosition();
                             Controlador= -1;
                             //>=
                         }
                     }
                     else if(Arquivo[Posicao] == '='){
                         Estado = 16;
                         Palavra = Palavra + Arquivo[Posicao];
                         NextPosition();
                         Controlador = -1;
                         //=
                     }
                     
                     
                //Analisando se é um operador matemático
                     else if(Arquivo[Posicao] == '+' || Arquivo[Posicao] == '-'||
                             Arquivo[Posicao] == '*' || Arquivo[Posicao] == '/'){
                         Estado = 18;
                         Palavra = Palavra + Arquivo[Posicao];
                         NextPosition();
                         //Achamos um TOKEN OPM
                         Controlador = -1;
                     }
                //Analisando se é um abre parênteses
                     else if(Arquivo[Posicao] == '('){
                         Estado = 19;
                         Palavra = Palavra + Arquivo[Posicao];
                         NextPosition();
                         //Achamos um TOKEN AB_P
                         Controlador = -1;
                     }
                //Analisando se é um FC_P
                     else if(Arquivo[Posicao] == ')'){
                         Estado = 20;
                         Palavra = Palavra + Arquivo[Posicao];
                         NextPosition();
                         //Achamos um TOKEN FC_P
                         Controlador = -1;
                     }
                     else if(Arquivo[Posicao] == ';'){
                         Estado = 21;
                         Palavra = Palavra + Arquivo[Posicao];
                         NextPosition();
                         //Achamos um TOKEN PT_V
                         Controlador = -1;
                     }
                     else{
                         Palavra = Palavra + Arquivo[Posicao];
                         Controlador = -1;
                         Estado = 50;
                         NextPosition();
                         //Achamos um ERRO LÉXICO - CARACTERE INVÁLIDO
                        
                     }
                break;
                     
                case 1:
                    Controlador = -1;
                    while(Tamanho > Posicao && isNumero(Arquivo[Posicao])){
                        Estado = 1;
                        Palavra = Palavra + Arquivo[Posicao];
                        NextPosition();
                    } 
                    if(Tamanho > Posicao && Arquivo[Posicao] =='.'){
                        Estado = 2;
                        Palavra = Palavra + Arquivo[Posicao];
                        Controlador = 0;
                        NextPosition();
                        if(Tamanho>Posicao && isEspaco(Arquivo[Posicao])){
                            Estado = 51;
                            Controlador =-1;
                        }
                    }
                    else if(Tamanho>Posicao && ((Arquivo[Posicao]=='e'|| Arquivo[Posicao] == 'E'))){
                        Estado = 4;
                        Palavra = Palavra + Arquivo[Posicao];
                        Controlador = 0;
                        NextPosition();
                        
                        if(Tamanho>Posicao && isEspaco(Arquivo[Posicao])){
                            Estado = 51;
                            Controlador =-1;
                        }
                    }
                    
                    
                     
                    
                break;
                
                case 2:
                    if(isNumero(Arquivo[Posicao])){
                        Estado = 3;
                        Controlador = 0;
                        Palavra = Palavra + Arquivo[Posicao];
                        NextPosition();
                        if(Tamanho>Posicao && isEspaco(Arquivo[Posicao])){
                            Controlador =-1;
                        }
                    }
                    else{
                        Estado = 51; //Palavra não aceita
                        Controlador = -1;
                    }
                break;
                
                case 3: 
                    Controlador = -1;
                    while(Tamanho > Posicao && isNumero(Arquivo[Posicao])){
                        Estado = 3;
                        Palavra = Palavra + Arquivo[Posicao];
                        Controlador = -1;
                        NextPosition();
                    }
                    if(Tamanho> Posicao && (Arquivo[Posicao]=='e' || Arquivo[Posicao] == 'E')){
                        Estado = 7;
                        Palavra = Palavra + Arquivo[Posicao];
                        Controlador = 0;
                        NextPosition();
                        
                        if(Tamanho>Posicao && isEspaco(Arquivo[Posicao])){
                            Estado = 51;
                            Controlador =-1;
                        }
                    }
                    
                
                    
                break;
                
                case 4:
                    if(isNumero(Arquivo[Posicao])){
                        Estado = 6;
                        Palavra = Palavra + Arquivo[Posicao];
                        Controlador =0;
                        NextPosition();
                        
                    }
                    else if(Arquivo[Posicao] == '+' || Arquivo[Posicao] == '-'){
                        Estado = 5;
                        Palavra = Palavra + Arquivo[Posicao];
                        Controlador = 0;
                        NextPosition();
                        if(Tamanho>Posicao && isEspaco(Arquivo[Posicao])){
                            Estado = 51;
                            Controlador =-1;
                        }
                    }
                    else{
                        Estado = 51;
                        Controlador = -1;
                    }
                break;
                
                case 5:
                    if(isNumero(Arquivo[Posicao])){
                        Estado = 6;
                        Palavra = Palavra + Arquivo[Posicao];
                        Controlador = 0;
                        NextPosition();
                    }
                    else{
                        Estado = 51;
                        Controlador = -1;
                    }
                break;
                
                case 6:
                    Controlador = -1;
                    while(Tamanho>Posicao && (isNumero(Arquivo[Posicao]))){
                        Estado = 6;
                        Palavra = Palavra+ Arquivo[Posicao];
                        Controlador = -1;
                        NextPosition();
                    }
                    
                    
                break;
                
                
                case 7:
                    if(isNumero(Arquivo[Posicao])){
                        Estado = 9;
                        Palavra = Palavra + Arquivo[Posicao];
                        Controlador = 0;
                        NextPosition();
                        if(Tamanho>Posicao && isEspaco(Arquivo[Posicao])){
                            Controlador =-1;
                        }
                    }
                    else if(Arquivo[Posicao] == '+' || Arquivo[Posicao] == '-'){
                        Estado = 8;
                        Palavra = Palavra + Arquivo[Posicao];
                        Controlador = 0;
                        NextPosition();
                        if(Tamanho>Posicao && isEspaco(Arquivo[Posicao])){
                            Estado = 51;
                            Controlador =-1;
                        }
                    }
                    else{
                        Estado = 51;
                        Controlador = -1;
                    }
                    
                    
                break;
                
                
                case 8:
                    if(isNumero(Arquivo[Posicao])){
                        Estado = 9; 
                        Palavra = Palavra + Arquivo[Posicao];
                        Controlador = 0;
                        NextPosition();
                        if(Tamanho>Posicao && isEspaco(Arquivo[Posicao])){
                            Controlador =-1;
                        }
                    }
                    else{
                        Estado = 51;
                        Controlador = -1;
                    }
                    
                break;
                
                case 9:
                    
                    while(Tamanho>Posicao && (isNumero(Arquivo[Posicao]))) {
                        Controlador = -1;
                        Palavra = Palavra + Arquivo[Posicao];
                        NextPosition();
                    }
                    Controlador = -1;
                    
                    
                    
                break;
                
                case 10:
                    while((Tamanho>Posicao && Arquivo[Posicao] != '"') && (Tamanho>Posicao && Arquivo[Posicao] != '\n') ){
                        Estado = 10;
                        Palavra = Palavra + Arquivo[Posicao];
                        NextPosition();
                    }
                    
                    if(Tamanho>Posicao && Arquivo[Posicao] == '"'){
                        Estado = 11;
                        Palavra = Palavra + Arquivo[Posicao];
                        Controlador = -1;
                        NextPosition();
                    }
                    
                    else if(Tamanho>Posicao && Arquivo[Posicao] == '\n'){
                        //Literal não finalizado, quebra de linha
                        Posicao--;
                        Estado = 52;
                        Controlador = -1;
                    }
                    else{
                        //Literal não finalizado, fim de arquivo
                        Estado = 52;
                        Controlador = -1;
                    }
                break;
                
                case 12:
                    Controlador = -1;
                    Estado = 12;
                    while(Tamanho > Posicao && (isNumero(Arquivo[Posicao] ) || isLetra(Arquivo[Posicao])||
                            Arquivo[Posicao] == '_') )  {
                        Palavra = Palavra + Arquivo[Posicao];
                        Estado = 12;
                        Controlador = -1;
                        NextPosition();
                    }
                    
                    
                break;
                
                case 13:
                    while((Tamanho>Posicao && Arquivo[Posicao] != '}' ) && (Tamanho>Posicao && Arquivo[Posicao] != '\n' ) ){
                        Palavra = Palavra + Arquivo[Posicao];
                        Estado = 13;
                        NextPosition();
                    }
                    if(Tamanho>Posicao && Arquivo[Posicao]=='}'){
                        //Reconhecemos um comentario e o ignoramos
                        Palavra = "";
                        Estado = 0;
                        NextPosition();
                        Controlador = 0;
                    }
                    else if(Tamanho > Posicao && Arquivo[Posicao] == '\n'){
                        //Comentario nao finalizado, quebra de linha
                        Posicao--;
                        Estado = 53;
                        Controlador = -1;
                    }
                    else{
                        //Comentario nao finalizado, fim de arquivo
                        Estado = 53;
                        Controlador = -1;
                    }
                    
                break;
            }
            
            
        // ANALISANDO SE A ÚLTIMA POSIÇÃO É ALGUM ERRO LÉXICO  //
        
           //Palavra não aceita na linguagem (Erro no autômato)
           if(Tamanho == Posicao && (Estado == 2 || Estado ==4 || Estado == 5 || Estado == 7 || Estado == 8)){
                   Estado =51;
               }
           //Literal não finalizado
           if(Tamanho == Posicao && Estado == 10){
               Estado = 52;
           }
           //Comentário não finalizado
           if(Tamanho == Posicao && Estado == 13){
               Estado = 53;
           }
           //Se final de arquivo, para o laço
           if(Tamanho == Posicao){
               break;
           }
           
        }while(Controlador != -1);
        //Fim da análise dos estados nos autômatos
    }
    
    //Entraremos no ELSE caso seja fim de arquivo
    else{
        Estado = 100;
        Posicao++;
    }
      
        
  //--------------------------ANALISANDO OS ESTADOS---------------------------\\       
         
    String Lexema = String.valueOf(Palavra);
    TOKEN TokenAtual = new TOKEN();
    
          
    if(Estado == 1 || Estado == 6){
        //Temos um TOKEN NUM INTEIRO
        TokenAtual.Classe = "num";
        TokenAtual.Lexema = Lexema;
        TokenAtual.Tipo = "inteiro";
    }
    
    else if(Estado == 3 ||Estado == 9){
        //Temos um TOKEN NUM REAL
        TokenAtual.Classe = "num";
        TokenAtual.Lexema = Lexema;
        TokenAtual.Tipo = "real";
    }
    
    else if(Estado == 11){
        //Temos um TOKEN LITERAL
        TokenAtual.Classe = "lit";
        TokenAtual.Lexema = Lexema;
        TokenAtual.Tipo = "Literal";
    }
 
    else if(Estado == 12){
        TOKEN Auxiliar = new TOKEN();
        
        Auxiliar = Tabela.BuscaPalavra(Lexema);
        
        if(isPalavraReservada(Lexema)){
            TokenAtual = Auxiliar;
            //Temos aqui uma palavra reservada
        }
        else if(Auxiliar == null){
            TokenAtual.Classe = "id";
            TokenAtual.Lexema = Lexema;
            TokenAtual.Tipo = null;
            Tabela.AddTabela(TokenAtual);
            //Temos aqui um TOKEN ID que não estava na Tabela de Símbolos
        }
        else{
            TokenAtual = Auxiliar;
            //Temos aqui um TOKEN ID que estava na Tabela de Símbolos
        }
        
        //Temos um TOKEN IDENTIFICADOR ou PALAVRA RESERVADA
    }
    
    else if(Estado == 14){
        //Temos um TOKEN COMENTARIO
    }
    
    else if(Estado == 16){
        //Temos um TOKEN OPERADOR RELACIONAL
        TokenAtual.Classe = "opr";
        TokenAtual.Lexema = Lexema;
        TokenAtual.Tipo = null;
    }
    
    else if(Estado == 17){
        //Temos um TOKEN ATRIBUICAO
        TokenAtual.Classe = "rcb";
        TokenAtual.Lexema = Lexema;
        TokenAtual.Tipo = null;
    }
    
    else if(Estado == 18){
        //Temos um TOKEN OPERADOR ARITMETICO
        TokenAtual.Classe = "opm";
        TokenAtual.Lexema = Lexema;
        TokenAtual.Tipo = null;
    }
    
    else if(Estado == 19){
       //Temos um TOKEN ABRE PARENTESES
        TokenAtual.Classe = "ab_p";
        TokenAtual.Lexema = Lexema;
        TokenAtual.Tipo = null;
    }
    
    else if(Estado == 20){
        //Temos um TOKEN FECHA PARENTESES
        TokenAtual.Classe = "fc_p";
        TokenAtual.Lexema = Lexema;
        TokenAtual.Tipo = null;
    }
    
    else if(Estado == 21){
        //Temos um TOKEN PONTO E VIRGULA
        TokenAtual.Classe = "pt_v";
        TokenAtual.Lexema = Lexema;
        TokenAtual.Tipo = null;
    }
    
    else if(Estado == 50){
        //ERRO LÉXICO - CARACTERE INVÁLIDO NA LINGUAGEM ( PARA ESTADO 0 )
        System.out.println("ERRO LÉXICO - Caractere invalido na linguagem: "+Lexema);
        System.out.print("Linha: "+Linha);
        System.out.println(", Coluna: " + Coluna);
        TokenAtual.Classe = "ERRO";
        TokenAtual.Lexema = Lexema;
        TokenAtual.Tipo = null;
    }
    
    else if(Estado == 51){
        //ERRO LÉXICO - PALAVRA NÃO ACEITA NA LINGUAGEM (ERRO NO AUTÔMATO)
        System.out.println("ERRO LÉXICO - Palavra não aceita na linguagem: " +Lexema);
        System.out.print("Linha: "+Linha);
        System.out.println(", Coluna: " + Coluna);
        TokenAtual.Classe = "ERRO";
        TokenAtual.Lexema = Lexema;
        TokenAtual.Tipo = null;
    }
    
    else if(Estado == 52){
        //ERRO LÉXICO - PARA LITERAL NÃO FINALIZADO
        System.out.println("ERRO LÉXICO - Literal foi iniciado porém não finalizado ");
        System.out.print("Linha: "+LinhaInicial);
        System.out.println(", Coluna: " + ColunaInicial);
        TokenAtual.Classe = "ERRO";
        TokenAtual.Lexema = null;
        TokenAtual.Tipo = null;
    }
    
    else if( Estado == 53){
        //ERRO LÉXICO - PARA COMENTARIO NÃO FECHADO
        System.out.println("ERRO LÉXICO - Comentário foi iniciado porém não finalizado");
        System.out.print("Linha: "+LinhaInicial);
        System.out.println(", Coluna: " + ColunaInicial);
        TokenAtual.Classe = "ERRO";
        TokenAtual.Lexema = null;
        TokenAtual.Tipo = null;
    }
    

    else if(Estado == 100){
        //Temos um EOF
        TokenAtual.Classe = "EOF";
        TokenAtual.Lexema = "EOF";
        TokenAtual.Tipo = null;
    }
    
        
    return TokenAtual;
        
    }
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
//-----------------------------PROGRAMA PRINCIPAL-----------------------------\\  
    
    public static void main(String[] args) {
        
        //Inserindo palavras reservadas na Tabela de Símbolos
        
        TOKEN inicio = new TOKEN("inicio","inicio","inicio");
        Tabela.AddTabela(inicio);
        TOKEN varinicio = new TOKEN("varinicio","varinicio","varinicio");
        Tabela.AddTabela(varinicio);
        TOKEN varfim = new TOKEN("varfim","varfim","varfim");
        Tabela.AddTabela(varfim);
        TOKEN escreva = new TOKEN("escreva","escreva","escreva");
        Tabela.AddTabela(escreva);
        TOKEN leia = new TOKEN("leia","leia","leia");
        Tabela.AddTabela(leia);
        TOKEN se = new TOKEN("se","se","se");
        Tabela.AddTabela(se);
        TOKEN entao = new TOKEN("entao","entao","entao");
        Tabela.AddTabela(entao);
        TOKEN fimse = new TOKEN("fimse","fimse","fimse");
        Tabela.AddTabela(fimse);
        TOKEN repita = new TOKEN("repita","repita","repita");
        Tabela.AddTabela(repita);
        TOKEN fimrepita = new TOKEN("fimrepita","fimrepita","fimrepita");
        Tabela.AddTabela(fimrepita);
        TOKEN fim = new TOKEN("fim","fim","fim");
        Tabela.AddTabela(fim);
        TOKEN inteiro = new TOKEN("inteiro","inteiro","inteiro");
        Tabela.AddTabela(inteiro);
        TOKEN literal = new TOKEN("literal","literal","literal");
        Tabela.AddTabela(literal);
        TOKEN real = new TOKEN("real","real","real");
        Tabela.AddTabela(real);
        
        
        

        
        
        
//-------------------------------ARQUIVO FONTE--------------------------------//        
                      
        //Realizando a abertura do arquivo fonte e salvando em um ARRAY
        
        Path caminho = Paths.get("C:\\Users\\lucas\\Desktop\\CompiladorLucas\\Arquivo.txt");
        
        /*Inicializando a variável char que será usada para armazenar 
        os chars do arquivo e será passada para a função tokenSCANNER*/
        char[] ConteudoArquivo = null ;
        String leitura;
        
        try {
            //lendo o arquivo como vetor de bytes e criando uma string
            byte[] texto = Files.readAllBytes(caminho);
            
            leitura = new String(texto);
            
            //Criando um array de char com todo o conteúdo do arquivo //
            ConteudoArquivo=  leitura.toCharArray();
            
            
            //Utilizando o Pane para apresentar o codigo em uma tabela
            //JOptionPane.showMessageDialog(null, StringASerLida);
        } catch(Exception erro){
            
        }
        
//-------------------------------ARQUIVO CSV-----------------------------------//         
            //Recebendo arquivo CSV
            File arquivoCSV = new File("C:\\Users\\lucas\\Desktop\\CompiladorLucas\\Tabela.csv");
            
            try{
                
                String linhasDoArquivo = new String();
                
                Scanner leitor = new Scanner(arquivoCSV);
                
                //Ignorando primeira linha
                leitor.nextLine();
                
                //Variáveis auxiliar para a matriz TabelaActionGoto
                int auxiliar = 0;
                int posCSV = 0;
                
                //Lendo arquivo e quebrando strings quando encontrar ";"
                while(leitor.hasNext()){
                    
                    linhasDoArquivo = leitor.nextLine();
                    String[] valores = linhasDoArquivo.split(";", -1);
                    
                    //Preenchendo TABELA ACTION GOTO através do arquivo CSV
                    while(auxiliar<44){
                    TabelaActionGoto[posCSV][auxiliar] = valores[auxiliar].trim();
                    auxiliar++;
                    
                    }
                    //Para matriz
                    auxiliar = 0;
                    posCSV++;
                }
            }catch(Exception erro){
                
            }
//-----------------------------ANALISADOR LÉXICO------------------------------//        
        
        
            //TOKEN utilizado para a função SCANNER
            TOKEN TokenAtual = new TOKEN();
            
           
            int Tamanho = ConteudoArquivo.length  ; 
            
            //Chamando a função SCANNER e percorrendo todo o arquivo
            while( Tamanho >= Posicao){
            
            //Retornando um TOKEN por chamada
            TokenAtual = tokenSCANNER(ConteudoArquivo, Tamanho);
            
            while(TokenAtual.Classe.equals("ERRO")){
                TokenAtual = tokenSCANNER(ConteudoArquivo, Tamanho);
            }
            
            
            //-----------------------ANALISADOR SINTÁTICO---------------------\\
            
            String AuxiliarSintatico = "";
            int EstadoPilha = 0;
            int AuxiliarReduce = 0;
            String AuxiliarGoto="";
            int NovoAuxiliar = 0;
            
            while(true){
                
                AuxiliarSintatico = EstadoTabela(EstadoPilha,TokenAtual.Classe);
                
                //---------------Temos um SHIFT---------------\\
                if(AuxiliarSintatico.contains("S")){
                    //Tirando o S da String
                    AuxiliarSintatico = AuxiliarSintatico.replace("S", "");
                    //Transformando em Inteiro
                    EstadoPilha = Integer.parseInt(AuxiliarSintatico);
                    
                    //Empilhando t na pilha
                    Pilha.empilha(EstadoPilha);
                    
                    //Próximo TOKEN
                    if(Tamanho>=Posicao){
                    TokenAtual = tokenSCANNER(ConteudoArquivo, Tamanho);
                    
                    while(TokenAtual.Classe.equals("ERRO")){
                         TokenAtual = tokenSCANNER(ConteudoArquivo, Tamanho);
                         }
                    
                    }
                    EstadoPilha = Pilha.elementos[Pilha.topo];
                }
                
                //---------------Temos um REDUCE---------------\\
                else if(AuxiliarSintatico.contains("R")){
                    
                    //Tirando o S da String
                    AuxiliarSintatico = AuxiliarSintatico.replace("R", "");
                    //Transformando em Inteiro
                    EstadoPilha = Integer.parseInt(AuxiliarSintatico);
                    
                    Gramatica.GramaticaRegra(EstadoPilha);
                    AuxiliarReduce = Gramatica.TamanhoDeBeta;
                    Pilha.desempilha(AuxiliarReduce);
                    AuxiliarGoto = EstadoTabela(Pilha.elementos[Pilha.topo],Gramatica.Alfa);
                    NovoAuxiliar = Integer.parseInt(AuxiliarGoto); 
                    
                    Pilha.empilha(NovoAuxiliar);
                    System.out.println(Gramatica.Alfa + " -> " + Gramatica.Beta);
                    EstadoPilha = Pilha.elementos[Pilha.topo];
                }
                
                //----------------Temos um ACC----------------\\
                else if(AuxiliarSintatico.equals("ACC")){
                    //System.out.println("Estado de Aceitação !");
                    break;
                }
                //----------------Temos um ERRO----------------\\
                else{
                    //Reportando mensagem de erro que foi encontrada
                    MensagemErroSintatico(EstadoPilha);
                    
                    //Loop para verificar o TOKEN de sincronização
                    while(TokenDeSincronizacao(EstadoPilha,TokenAtual.Classe) == false){
                        
                        TokenAtual = tokenSCANNER(ConteudoArquivo, Tamanho);
                    
                    while(TokenAtual.Classe.equals("ERRO")){
                         TokenAtual = tokenSCANNER(ConteudoArquivo, Tamanho);
                         }
                    //Chegamos no final de arquivo e não encontramos o TOKEN de sincronização
                    if(TokenAtual.Classe.equals("EOF")){
                        System.out.println("Não foi possível recuperar TOKEN de Sincronização: Final de Arquivo");
                        break;
                    }
                  }
                    if(TokenAtual.Classe.equals("EOF")){
                        break;
                    }
                    
                
                }
            }
             
            //------------------FIM FUNÇÃO ANÁLISE SINTÁTICA------------------\\

            
            //Imprimindo TOKENS ANÁLISE LÉXICA
            /*System.out.print("Classe: "+TokenAtual.Classe);
            System.out.print(" || Lexema: "+TokenAtual.Lexema);
            System.out.println(" || Tipo: "+TokenAtual.Tipo);
            System.out.println("");*/
            
            
            }
            
        //Após a leitura de todo o código fonte pela função SCANNER, iremos 
        //imprimir a tabela de símbolos
        //Tabela.ImprimeTabela();
        
        
 //--------------------------FIM DO ANALISADOR LÉXICO--------------------------\\
        
    }   //Final do Main
    
    
}
