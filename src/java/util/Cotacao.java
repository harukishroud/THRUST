package util;

import java.net.*;
import java.io.*;
import java.util.regex.*;


public class Cotacao {

private final String  fonte= "https://www5.bcb.gov.br/?id=txdolar";

//A constante fonte é uma string que contém a url onde a cotação é divulgada no site do banco central;

    private CotacaoBean cotacao;//classe bean para a cotação
    
   //método construtor
    public Cotacao() {
        cotacao = new CotacaoBean();
    }

    /**
     * Responsável em extrair as informações: data e taxa de venda.
     * @param texto
     */
    private void Extrai(String texto) {
  
        String data = new String();
        String taxavenda = new String();
        String textocota= new String();
                
        /**
         * Removendo texto que interessa com expressões regulares
         */
        
        Pattern tabelacota = Pattern.compile("<table cellspacing=\"1\" summary=\"Cotação de fechamento do Dólar americano\">(.*?)</table>");
        Matcher m = tabelacota.matcher(texto);
        if(m.find()) {
            //System.out.println(m.group());
            textocota = m.group();
        }
        
        Pattern datacota = Pattern.compile("(\\d\\d)/(\\d\\d)/(\\d\\d\\d\\d)");
        Matcher datam = datacota.matcher(textocota);
        
        if(datam.find()) {
            data = datam.group();
        }
        
        Pattern taxacota = Pattern.compile("(\\d),(\\d+)</td></tr>");
        Matcher taxacotam = taxacota.matcher(textocota);
        
        if(taxacotam.find()) {
            taxavenda = taxacotam.group().replaceAll("</td></tr>", "");
            taxavenda = taxavenda.replace(",", ".");
        }
        
        //System.out.println("Data: " + data);
        //System.out.println("Taxa venda: " + taxavenda);
          
        this.cotacao.setData(data);
        this.cotacao.setTaxavenda(Float.parseFloat(taxavenda));
        
    }

/**
     * Método acessa a página com o conteúdo relevante e salva em uma string
     * o conteúdo.
     */
    public void getPage() {
        try {
            URL url = new URL(fonte);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Request-Method", "GET");
            connection.setDoInput(true);
            connection.setDoOutput(false);
            connection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer newData = new StringBuffer(10000);
            String s = "";
            while (null != ((s = br.readLine()))) {
                newData.append(s+"\n");
                
            }
            br.close();
            //System.out.println(newData.toString());
            //Chamada do método para extrair as informações da página
            this.Extrai(newData.toString());
            
            //PrintWriter out = new PrintWriter(System.out, true);
            //out.println(new String(newData));

        } catch (MalformedURLException e) {
            System.err.println("Erro na URL");
            e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro de I/O");
            e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro geral");
            e.printStackTrace();
            e.getMessage();
        }
    }

//Imprime as informações da classe CotacaoBean
    public void ImprimeCotacaobean() {
        System.out.println("Cotação do Dólar");
        System.out.println("Data: " + this.cotacao.getData());
        System.out.println("Taxa de venda: " + this.cotacao.getTaxavenda());
    }
    
    public static void main(String[] args) {
        Cotacao cotacao = new Cotacao();        
        cotacao.getPage();
       cotacao.ImprimeCotacaobean();
    } 
    
}